package cn.paohe.components.run;

import cn.paohe.components.utils.RedisUtil;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

/**
 * @Copyright (c) by paohe information technology Co., Ltd.
 * @All right reserved.
 * @Project: sync_center
 * @File: StartupCanal.java
 * @Description: 启动springboot时启动canal类
 * @Author: yuanzhenhui
 * @Date: 2020/06/30
 */
@Component
public class StartupCanal implements ApplicationRunner {

    public static final Logger logger = LoggerFactory.getLogger(StartupCanal.class);

    @Value("${canal.server.ip}")
    private String canalServerIp;

    @Value("${canal.server.port}")
    private int canalServerPort;

    @Value("${canal.batch.size}")
    private int canalBatchSize;

    @Value("${canal.white.list}")
    private String canalWhiteList;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        syncDataByCanalConnetion();
    }

    /**
     * 通过canal连接同步mysql数据到redis
     */
    private void syncDataByCanalConnetion() {
        CanalConnector connector = CanalConnectors
                .newSingleConnector(new InetSocketAddress(canalServerIp, canalServerPort), "example", "", "");
        try {
            connector.connect();
            connector.subscribe(canalWhiteList);
            connector.rollback();

            long batchId = -1L;
            int size = 0;
            while (true) {
                // 获取指定数量的数据
                Message message = connector.getWithoutAck(canalBatchSize);
                batchId = message.getId();
                size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    // 线程停止1秒
                    Thread.sleep(1000);
                } else {
                    printEntry(message.getEntries());
                }
                // 提交确认
                connector.ack(batchId);
            }
        } catch (InterruptedException e) {
            logger.error("func[syncDataByCanalConnetion] Exception [{} - {}] stackTrace[{}]",
                    new Object[]{e.getCause(), e.getMessage(), Arrays.deepToString(e.getStackTrace())});
            Thread.currentThread().interrupt();
        } finally {
            connector.disconnect();
        }
    }

    /**
     * 将数传送到redis
     *
     * @param entrys
     */
    private void printEntry(List<Entry> entrys) {
        RowChange rowChage = null;
        String tableName = null;
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN
                    || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            } else {
                tableName = entry.getHeader().getTableName();
            }
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                logger.error("func[printEntry] Exception [{} - {}] stackTrace[{}]",
                        new Object[]{e.getCause(), e.getMessage(), Arrays.deepToString(e.getStackTrace())});
            }
            EventType eventType = rowChage.getEventType();
            logger.debug(String.format(" --------- binlog[%s:%s] , name[%s,%s] , eventType : %s --------- ",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType));

            List<RowData> rowDataList = rowChage.getRowDatasList();
            if (null != rowDataList && !rowDataList.isEmpty()) {
                for (RowData rowData : rowDataList) {
                    if (eventType == EventType.DELETE) {
                        redisUtil.redisDelete(tableName, rowData.getBeforeColumnsList());
                    } else if (eventType == EventType.INSERT) {
                        redisUtil.redisInsert(tableName, rowData.getAfterColumnsList());
                    } else {
                        redisUtil.redisUpdate(tableName, rowData.getAfterColumnsList());
                    }
                }
            }
        }
    }
}

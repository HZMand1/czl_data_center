package cn.paohe.components.task;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.paohe.components.constants.CommonConstant;
import cn.paohe.framework.utils.RedisClient;
import cn.paohe.framework.utils.base.ObjectUtils;
import cn.paohe.framework.utils.base.StringUtil;

@Component
public class SyncConfigTask {

    public static final Logger logger = LoggerFactory.getLogger(SyncConfigTask.class);

    @Autowired
    private RedisClient redisClient;

    @SuppressWarnings("unchecked")
    @Scheduled(cron = "10 * * * * ?")
    public void getRedisConfig() throws Exception {
        logger.debug(" --------- sync has began --------- ");
        Map<String, HashMap<String, Object>> map = redisClient.hgetAllHash(CommonConstant.ROUTER_KEY);
        if (!ObjectUtils.isNullObj(map)) {
            map.forEach((k, v) -> {
                HashMap<String, Object> valMap = new HashMap<>();
                for (Map.Entry<String, Object> entry : v.entrySet()) {
                    valMap.put(StringUtil.underline2Camel(entry.getKey(), true), entry.getValue());
                }
                CommonConstant.configMap.put(k, valMap);
            });
        }
    }

}

package cn.paohe.components.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.otter.canal.protocol.CanalEntry.Column;

import cn.paohe.framework.utils.RedisClient;

/**
 * 
 * @Copyright (c) by paohe information technology Co., Ltd.
 * @All right reserved.
 * @Project: sync_center
 * @File: RedisUtil.java
 * @Description: canal redis工具类
 *
 * @Author: yuanzhenhui
 * @Date: 2020/06/30
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisClient redisClient;

    /**
     * 插入redis
     * 
     * @param tableName
     * @param columns
     */
    public void redisInsert(String tableName, List<Column> columns) {
        Map<String, String> json = new HashMap<>();
        if (!columns.isEmpty()) {
            columns.stream().forEach(column -> json.put(column.getName(), column.getValue()));
            redisClient.hSetHash(tableName, columns.get(0).getValue(), json);
        }
    }

    /**
     * 更新redis
     * 
     * @param tableName
     * @param columns
     */
    public void redisUpdate(String tableName, List<Column> columns) {
        Map<String, String> json = new HashMap<>();
        if (!columns.isEmpty()) {
            columns.stream().forEach(column -> json.put(column.getName(), column.getValue()));
            redisClient.hSetHash(tableName, columns.get(0).getValue(), json);
        }
    }

    /**
     * 删除redis
     * 
     * @param tableName
     * @param columns
     */
    public void redisDelete(String tableName, List<Column> columns) {
        if (!columns.isEmpty()) {
            redisClient.hDelHash(tableName, columns.get(0).getValue());
        }
    }
}

package cn.paohe.user.dao;

import cn.paohe.entity.vo.data_statistics.DataStatisticsVo;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/24 13:45
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IDataStatisticsMapper {

    /**
     * TODO 获取角色是开发的用户数据
     *
     * @Param: userEntity
     * @return: List
     * @author: 黄芝民
     * @Date: 2020/9/22 14:05
     * @throws:
     */
    public List<Map<Object, Object>> queryNewInterfaceByType(DataStatisticsVo dataStatisticsVo);
}

package cn.paohe.user.service;

import cn.paohe.entity.vo.data_statistics.DataStatisticsVo;
import cn.paohe.entity.vo.interfaceMag.ESInterfaceVo;
import cn.paohe.framework.utils.page.PageAjax;
import cn.paohe.vo.framework.AjaxResult;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/24 12:12
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IDataStatisticsService {
    /**
     * TODO 统计本周、月、季、年,分类标签下接口新增的情况
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/12/24 13:41 
     * @throws:  
     */
    AjaxResult queryNewInterfaceByType(DataStatisticsVo dataStatisticsVo);

    /**
     * TODO 统计本周、月、季、年,分类标签下接口调用的情况
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/12/24 13:42
     * @throws:
     */
    AjaxResult queryInterfaceConnectByType(DataStatisticsVo dataStatisticsVo);

    /**
     * TODO 统计本周、月、季、年,分类标签下接口调用的情况
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/12/24 13:42
     * @throws:
     */
    PageAjax<ESInterfaceVo> queryInterfaceConnectLog(DataStatisticsVo dataStatisticsVo);
}

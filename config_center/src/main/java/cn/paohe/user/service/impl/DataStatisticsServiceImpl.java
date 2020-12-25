package cn.paohe.user.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.entity.vo.data_statistics.DataStatisticsVo;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.framework.utils.ESUtil;
import cn.paohe.user.dao.IDataStatisticsMapper;
import cn.paohe.user.service.IDataStatisticsService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/24 13:42
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class DataStatisticsServiceImpl implements IDataStatisticsService {

    @Resource
    private IDataStatisticsMapper dataStatisticsMapper;
    @Autowired
    private ESUtil esUtil;

    @TargetDataSource(value = "center-r")
    @Override
    public AjaxResult queryNewInterfaceByType(DataStatisticsVo dataStatisticsVo) {
        Long loginUserId = UserUtil.getUserEntity().getUserId();
        if (ObjectUtils.isNullObj(loginUserId)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "用户ID不能为空");
        }
        dataStatisticsVo.setAddUserId(loginUserId);
        List<Map<Object, Object>> list = dataStatisticsMapper.queryNewInterfaceByType(dataStatisticsVo);
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"获取数据成功",list);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public AjaxResult queryInterfaceConnectByType(DataStatisticsVo dataStatisticsVo) {
        return null;
    }

    @TargetDataSource(value = "center-r")
    @Override
    public AjaxResult queryInterfaceConnectLog(DataStatisticsVo dataStatisticsVo) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termsQuery("typeId",dataStatisticsVo.getTypeId()))
                .must(QueryBuilders.rangeQuery("addTime").gte(dataStatisticsVo.getStartAddDate()))
                .must(QueryBuilders.rangeQuery("addTime").lte(dataStatisticsVo.getEndAddDate()));

        List<InterfaceInfoVo> interfaceInfoVos = esUtil.search4NoPagination(DataCenterCollections.CONNECTION_INTERFACE_LIST,queryBuilder,InterfaceInfoVo.class);
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"获取数据成功",interfaceInfoVos);
    }
}

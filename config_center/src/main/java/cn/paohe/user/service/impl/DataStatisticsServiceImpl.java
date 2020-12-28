package cn.paohe.user.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.entity.vo.data_statistics.DataStatisticsVo;
import cn.paohe.entity.vo.interfaceMag.ESInterfaceVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.framework.utils.ESUtil;
import cn.paohe.framework.utils.page.PageAjax;
import cn.paohe.user.dao.IDataStatisticsMapper;
import cn.paohe.user.service.IDataStatisticsService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.CollectionUtil;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    @Qualifier("highLevelClient")
    private RestHighLevelClient client;

    @TargetDataSource(value = "center-r")
    @Override
    public AjaxResult queryNewInterfaceByType(DataStatisticsVo dataStatisticsVo) {
        Long loginUserId = UserUtil.getUserEntity().getUserId();
        if (ObjectUtils.isNullObj(loginUserId)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "用户ID不能为空");
        }
        dataStatisticsVo.setAddUserId(loginUserId);
        List<Map<Object, Object>> list = dataStatisticsMapper.queryNewInterfaceByType(dataStatisticsVo);
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取数据成功", list);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public AjaxResult queryInterfaceConnectByType(DataStatisticsVo dataStatisticsVo) {
        Long loginUserId = UserUtil.getUserEntity().getUserId();
        if (ObjectUtils.isNullObj(loginUserId)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "用户ID不能为空");
        }
        dataStatisticsVo.setAddUserId(loginUserId);
        List<Map<Object, Object>> list = dataStatisticsMapper.queryNewInterfaceByType(dataStatisticsVo);
        if (CollectionUtil.isEmpty(list)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取数据成功", list);
        }
        // 分组 参考http://www.zxzulu.com/article/1591578939674
        List<JSONObject> jsonObjectList = new ArrayList<>();
        JSONObject jsonObject = null;
        for (Map<Object, Object> map : list) {
            jsonObject = new JSONObject();
            String labelName = String.valueOf(map.get("labelName"));
            Long labelId = Long.valueOf(String.valueOf(map.get("labelId")));

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.termQuery("addUserId", loginUserId));
            boolQueryBuilder.must(QueryBuilders.termQuery("labelId", labelId));
            List<ESInterfaceVo> interfaceInfoVos = esUtil.search4NoPagination(DataCenterCollections.CONNECTION_INTERFACE_LIST, boolQueryBuilder, ESInterfaceVo.class);

            jsonObject.put("labelName", labelName);
            jsonObject.put("labelId", labelId);
            jsonObject.put("list", interfaceInfoVos);
            jsonObjectList.add(jsonObject);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取数据成功", jsonObjectList);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public PageAjax<ESInterfaceVo> queryInterfaceConnectLog(DataStatisticsVo dataStatisticsVo) {
        Long loginUserId = UserUtil.getUserEntity().getUserId();
        if (ObjectUtils.isNullObj(loginUserId)) {
            return new PageAjax<ESInterfaceVo>(Collections.EMPTY_LIST, DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "用户ID不能为空");
        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("addUserId", loginUserId));

        if (!ObjectUtils.isNullObj(dataStatisticsVo.getTypeId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("typeId", dataStatisticsVo.getTypeId()));
        }
        if (!ObjectUtils.isNullObj(dataStatisticsVo.getStartAddDate())) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("addTime").gte(dataStatisticsVo.getStartAddDate()));
        }
        if (!ObjectUtils.isNullObj(dataStatisticsVo.getEndAddDate())) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("addTime").lte(dataStatisticsVo.getEndAddDate()));
        }
        PageAjax<ESInterfaceVo> pageAjax = new PageAjax<>();
        pageAjax.setPageNo(dataStatisticsVo.getStart() - 1);
        pageAjax.setPageSize(dataStatisticsVo.getPageSize());
        PageAjax<ESInterfaceVo> result = esUtil.search4Pagination(DataCenterCollections.CONNECTION_INTERFACE_LIST, boolQueryBuilder, ESInterfaceVo.class, pageAjax);
        return result;
    }
}

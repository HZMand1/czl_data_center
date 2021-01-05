package cn.paohe.user.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.BeanCopy;
import cn.paohe.entity.model.InterfaceMag.InterfaceLabelInfo;
import cn.paohe.entity.vo.data_statistics.DataStatisticsVo;
import cn.paohe.entity.vo.interfaceMag.ESInterfaceVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.framework.utils.page.PageAjax;
import cn.paohe.interface_management.dao.IInterfaceLabelMapper;
import cn.paohe.user.dao.IDataStatisticsMapper;
import cn.paohe.user.service.IDataStatisticsService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.CollectionUtil;
import cn.paohe.utils.ESUtil;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import org.apache.http.Header;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

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
    @Resource
    private IInterfaceLabelMapper iInterfaceLabelMapper;

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
        DataStatisticsVo dataStatisticsVo1 = BeanCopy.objectCopy(dataStatisticsVo,DataStatisticsVo.class);
        dataStatisticsVo1.setStartAddDate(null);
        dataStatisticsVo1.setEndAddDate(null);
        List<Map<Object, Object>> list = dataStatisticsMapper.queryNewInterfaceByType(dataStatisticsVo1);
        if (CollectionUtil.isEmpty(list)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取数据成功", list);
        }
        // 查询条件
        BoolQueryBuilder boolQueryBuilder = getBoolQueryBuilder(dataStatisticsVo, loginUserId);
        // 分组
        TermsAggregationBuilder labelIdGroup = AggregationBuilders.terms("connectionTimeGroup").field("connectTimeStr")
                .subAggregation(AggregationBuilders.terms("labelIds").field("labelId"));

        // 分组排序
        labelIdGroup.order(Terms.Order.term(true));

        List<Map<String, Object>> dateMap = new ArrayList();
        try {
            Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));

            SearchRequest searchRequest = new SearchRequest(new String[]{DataCenterCollections.CONNECTION_INTERFACE_LIST.toLowerCase()});
            searchRequest.scroll(scroll);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(100);
            searchSourceBuilder.query(boolQueryBuilder);
            searchSourceBuilder.aggregation(labelIdGroup);
            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = this.client.search(searchRequest, new Header[0]);
            Aggregations aggregations = searchResponse.getAggregations();
            //获取主分组信息
            Terms sourceType = aggregations.get("connectionTimeGroup");
            Map<String, Object> result = null;
            //遍历主分组获取媒体名称信息以及文章个数
            for (Terms.Bucket bucket : sourceType.getBuckets()) {
                result = new HashMap<>();

                Aggregations temps = bucket.getAggregations();
                Map<String, Aggregation> source = temps.getAsMap();
                List<Map<String, Object>> mapList = new ArrayList();
                for (Map.Entry<String, Aggregation> aggregationEntry : source.entrySet()) {
                    ParsedLongTerms parsedLongTerms = (ParsedLongTerms) aggregationEntry.getValue();
                    List<ParsedLongTerms.ParsedBucket> parsedBuckets = (List<ParsedLongTerms.ParsedBucket>) parsedLongTerms.getBuckets();
                    for (ParsedLongTerms.ParsedBucket parsedBucket : parsedBuckets) {
                        Map<String, Object> map = new HashMap<>();
                        // label Id
                        map.put("labelId", parsedBucket.getKeyAsString());
                        // label name
                        InterfaceLabelInfo interfaceLabelInfo = iInterfaceLabelMapper.selectByPrimaryKey(parsedBucket.getKeyAsString());
                        String labelName = ObjectUtils.isNullObj(interfaceLabelInfo) ? "" : interfaceLabelInfo.getLabelName();
                        map.put("labelName", labelName);
                        // label count
                        map.put("labelCount", parsedBucket.getDocCount());
                        //加入到集合
                        mapList.add(map);
                    }
                }
                // 日期
                result.put("connectionStr", bucket.getKeyAsString());
                // 当天每个节点的统计 根据labelId
                result.put("mapList", mapList);

                dateMap.add(result);
            }
        } catch (IOException ioException) {

        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取数据成功", dateMap);
    }

    private BoolQueryBuilder getBoolQueryBuilder(DataStatisticsVo dataStatisticsVo, Long loginUserId) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("addUserId", loginUserId));
        if (!ObjectUtils.isNullObj(dataStatisticsVo.getTypeId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("typeId", dataStatisticsVo.getTypeId()));
        }
        if (!ObjectUtils.isNullObj(dataStatisticsVo.getStartAddDate())) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("connectTime").gte(dataStatisticsVo.getStartAddDate().getTime()));
        }
        if (!ObjectUtils.isNullObj(dataStatisticsVo.getEndAddDate())) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("connectTime").lte(dataStatisticsVo.getEndAddDate().getTime()));
        }
        return boolQueryBuilder;
    }

    @TargetDataSource(value = "center-r")
    @Override
    public PageAjax<ESInterfaceVo> queryInterfaceConnectLog(DataStatisticsVo dataStatisticsVo) {
        Long loginUserId = UserUtil.getUserEntity().getUserId();
        if (ObjectUtils.isNullObj(loginUserId)) {
            return new PageAjax<ESInterfaceVo>(Collections.EMPTY_LIST, DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "用户ID不能为空");
        }
        BoolQueryBuilder boolQueryBuilder = getBoolQueryBuilder(dataStatisticsVo, loginUserId);
        PageAjax<ESInterfaceVo> pageAjax = new PageAjax<>();
        pageAjax.setPageNo(dataStatisticsVo.getStart() - 1);
        pageAjax.setPageSize(dataStatisticsVo.getPageSize());
        PageAjax<ESInterfaceVo> result = esUtil.search4Pagination(DataCenterCollections.CONNECTION_INTERFACE_LIST, boolQueryBuilder, ESInterfaceVo.class, pageAjax);
        return result;
    }
}

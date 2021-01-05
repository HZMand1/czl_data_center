package cn.paohe.utils;

import cn.paohe.framework.utils.base.JsonUtil;
import cn.paohe.framework.utils.base.ObjectUtils;
import cn.paohe.framework.utils.base.StringUtil;
import cn.paohe.framework.utils.page.AppUtil;
import cn.paohe.framework.utils.page.PageAjax;
import cn.paohe.framework.utils.ref.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2021/1/4 15:53
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Component
public class ESUtil {
    private static final Logger logger = LogManager.getLogger(cn.paohe.framework.utils.ESUtil.class);
    @Autowired
    @Qualifier("highLevelClient")
    private RestHighLevelClient client;

    public ESUtil() {
    }

    private <T> String save(String indexName, String typeName, String id, T t) {
        String esId = null;
        if (null != t) {
            try {
                IndexRequest indexRequest = (new IndexRequest(indexName.toLowerCase(), typeName.toLowerCase(), id.toLowerCase())).source(JsonUtil.objToJson(t), XContentType.JSON);
                IndexResponse indexResponse = this.client.index(indexRequest, new Header[0]);
                int counter = indexResponse.getShardInfo().getSuccessful();
                if (counter > 0) {
                    esId = id;
                }
            } catch (IOException var9) {
                logger.error("func[ESUtil.save] Exception [{} - {}] stackTrace[{}] ", new Object[]{var9.getCause(), var9.getMessage(), Arrays.deepToString(var9.getStackTrace())});
            }
        }

        return esId;
    }

    public <T> String save(String indexName, String typeName, T t) {
        String esId = null;
        if (null != t) {
            Field[] fields = t.getClass().getDeclaredFields();
            String id = null;
            Field[] var7 = fields;
            int var8 = fields.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                Field field = var7[var9];
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    id = ReflectUtil.getStringValueByASM(t, field.getName());
                    break;
                }
            }

            if (StringUtil.isNotEmpty(id)) {
                esId = this.save(indexName, typeName, id, t);
            }
        }

        return esId;
    }

    public <T> String save(String indexName, T t) {
        return this.save(indexName, StringUtil.camel2Underline(t.getClass().getSimpleName()).toLowerCase(), t);
    }

    private <T> List<String> bulkSave(String indexName, String typeName, List<T> list) {
        List<String> idList = new ArrayList();
        BulkRequest request = new BulkRequest();
        if (null != list && !list.isEmpty()) {
            try {
                Field[] fields = list.stream().findFirst().get().getClass().getDeclaredFields();
                String fieldName = null;
                Field[] var8 = fields;
                int var9 = fields.length;

                int var10;
                for(var10 = 0; var10 < var9; ++var10) {
                    Field field = var8[var10];
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Id.class)) {
                        fieldName = field.getName();
                        break;
                    }
                }

                if (StringUtil.isNotEmpty(fieldName)) {
                    Iterator var15 = list.iterator();

                    while(var15.hasNext()) {
                        T entity = (T) var15.next();
                        String id = ReflectUtil.getStringValueByASM(entity, fieldName);
                        if (StringUtil.isNotEmpty(id)) {
                            request.add((new IndexRequest(indexName.toLowerCase(), typeName.toLowerCase(), id)).source(JsonUtil.objToJson(entity), XContentType.JSON));
                        }
                    }
                }

                if (!ObjectUtils.isNullObj(request)) {
                    BulkItemResponse[] birs = this.client.bulk(request, new Header[0]).getItems();
                    BulkItemResponse[] var18 = birs;
                    var10 = birs.length;

                    for(int var20 = 0; var20 < var10; ++var20) {
                        BulkItemResponse bir = var18[var20];
                        if (!bir.isFailed()) {
                            idList.add(bir.getId());
                        }
                    }
                }
            } catch (JsonProcessingException var13) {
                logger.error("func[ESUtil.bulkSave] Exception [{} - {}] stackTrace[{}] ", new Object[]{var13.getCause(), var13.getMessage(), Arrays.deepToString(var13.getStackTrace())});
            } catch (IOException var14) {
                logger.error("func[ESUtil.bulkSave] Exception [{} - {}] stackTrace[{}] ", new Object[]{var14.getCause(), var14.getMessage(), Arrays.deepToString(var14.getStackTrace())});
            }
        }

        return idList;
    }

    public <T> List<String> bulkSave(String indexName, List<T> list) {
        return this.bulkSave(indexName, StringUtil.camel2Underline(list.stream().findFirst().get().getClass().getSimpleName()).toLowerCase(), list);
    }

    private int delete(String indexName, String typeName, String id) {
        int counter = 0;

        try {
            DeleteRequest deleteRequest = new DeleteRequest(indexName.toLowerCase(), typeName.toLowerCase(), id);
            DeleteResponse deleteResponse = this.client.delete(deleteRequest, new Header[0]);
            counter = deleteResponse.getShardInfo().getSuccessful();
        } catch (IOException var7) {
            logger.error("func[ESUtil.delete] Exception [{} - {}] stackTrace[{}] ", new Object[]{var7.getCause(), var7.getMessage(), Arrays.deepToString(var7.getStackTrace())});
        }

        return counter;
    }

    public <T> int delete(String indexName, T t) {
        int counter = 0;
        if (null != t) {
            Class<?> clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            String id = null;
            Field[] var7 = fields;
            int var8 = fields.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                Field field = var7[var9];
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    id = ReflectUtil.getStringValueByASM(t, field.getName());
                    break;
                }
            }

            if (StringUtil.isNotEmpty(id)) {
                counter = this.delete(indexName, StringUtil.camel2Underline(clazz.getSimpleName()).toLowerCase(), id);
            }
        }

        return counter;
    }

    private <T> int bulkDelete(String indexName, String typeName, List<T> list) {
        int counter = 0;
        BulkRequest request = new BulkRequest();
        if (null != list && !list.isEmpty()) {
            Field[] fields = list.stream().findFirst().get().getClass().getDeclaredFields();
            String fieldName = null;
            Field[] var8 = fields;
            int var9 = fields.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                Field field = var8[var10];
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    fieldName = field.getName();
                    break;
                }
            }

            if (StringUtil.isNotEmpty(fieldName)) {
                Iterator var15 = list.iterator();

                while(var15.hasNext()) {
                    T entity = (T) var15.next();
                    String id = ReflectUtil.getStringValueByASM(entity, fieldName);
                    if (StringUtil.isNotEmpty(id)) {
                        request.add(new DeleteRequest(indexName.toLowerCase(), typeName.toLowerCase(), id));
                    }
                }
            }

            if (!ObjectUtils.isNullObj(request)) {
                try {
                    BulkResponse br = this.client.bulk(request, new Header[0]);
                    BulkItemResponse[] birs = br.getItems();
                    BulkItemResponse[] var20 = birs;
                    int var21 = birs.length;

                    for(int var12 = 0; var12 < var21; ++var12) {
                        BulkItemResponse bir = var20[var12];
                        if (!bir.isFailed()) {
                            ++counter;
                        }
                    }
                } catch (IOException var14) {
                    logger.error("func[ESUtil.bulkDelete] Exception [{} - {}] stackTrace[{}] ", new Object[]{var14.getCause(), var14.getMessage(), Arrays.deepToString(var14.getStackTrace())});
                }
            }
        }

        return counter;
    }

    public <T> int bulkDelete(String indexName, List<T> list) {
        return this.bulkDelete(indexName, StringUtil.camel2Underline(list.stream().findFirst().get().getClass().getSimpleName()).toLowerCase(), list);
    }

    public <T> List<T> search4NoPagination(String indexName, QueryBuilder queryBuilder, Class<T> clazz) {
        ArrayList list = new ArrayList();

        try {
            List<SearchHit> shList = new ArrayList();
            Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
            SearchRequest searchRequest = new SearchRequest(new String[]{indexName.toLowerCase()});
            searchRequest.scroll(scroll);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(100);
            searchSourceBuilder.query(queryBuilder);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = this.client.search(searchRequest, new Header[0]);
            String scrollId = searchResponse.getScrollId();
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            shList.addAll(Arrays.asList(searchHits));

            while(searchHits != null && searchHits.length > 0) {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                searchResponse = this.client.searchScroll(scrollRequest, new Header[0]);
                scrollId = searchResponse.getScrollId();
                searchHits = searchResponse.getHits().getHits();
                shList.addAll(Arrays.asList(searchHits));
            }

            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            ClearScrollResponse clearScrollResponse = this.client.clearScroll(clearScrollRequest, new Header[0]);
            boolean succeeded = clearScrollResponse.isSucceeded();
            if (succeeded && !ObjectUtils.isNullObj(shList)) {
                Iterator var15 = shList.iterator();

                while(var15.hasNext()) {
                    SearchHit sh = (SearchHit)var15.next();
                    T t = JsonUtil.jsonToBean(sh.getSourceAsString(), clazz);
                    list.add(t);
                }
            }
        } catch (IOException var18) {
            logger.error("func[ESUtil.search4NoPagination] Exception [{} - {}] stackTrace[{}] ", new Object[]{var18.getCause(), var18.getMessage(), Arrays.deepToString(var18.getStackTrace())});
        }

        return list;
    }

    public <T> PageAjax<T> search4Pagination(String indexName, QueryBuilder queryBuilder, Class<T> clazz, PageAjax<T> page) {
        ArrayList list = new ArrayList();

        try {
            SearchRequest searchRequest = new SearchRequest(new String[]{indexName.toLowerCase()});
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(queryBuilder);
            sourceBuilder.from(page.getPageNo() * page.getPageSize());
            sourceBuilder.size(page.getPageSize());
            sourceBuilder.sort("connectTime", SortOrder.DESC);
            searchRequest.source(sourceBuilder);
            SearchResponse searchResponse = this.client.search(searchRequest, new Header[0]);
            SearchHits searchHits = searchResponse.getHits();
            SearchHit[] var10 = searchHits.getHits();
            int var11 = var10.length;

            for(int var12 = 0; var12 < var11; ++var12) {
                SearchHit sh = var10[var12];
                T t = JSON.parseObject(sh.getSourceAsString(), clazz);
                list.add(t);
            }

            page.setTotal(searchHits.getTotalHits());
            page.setRows(list);
        } catch (IOException var15) {
            logger.error("func[ESUtil.search4Pagination] Exception [{} - {}] stackTrace[{}] ", new Object[]{var15.getCause(), var15.getMessage(), Arrays.deepToString(var15.getStackTrace())});
        }
        return page;
    }
}

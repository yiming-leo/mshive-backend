package com.yimingliao.mshivebackend.service.elastic.impl;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.elastic.ESStuff;
import com.yimingliao.mshivebackend.mapper.elastic.ESStuffRepository;
import com.yimingliao.mshivebackend.service.elastic.IESStuffService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description Elasticsearch Stuff Impl
 * @date 2023/10/19 20:44
 */
@Service
@Slf4j
public class ESStuffServiceImpl implements IESStuffService {

    @Autowired
    private ESStuffRepository esStuffRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    //SEARCH <ESStuff> BY <Name> OR <Description>, NEED <String Keyword>
    @Override
    public List<ESStuff> searchESStuffByNameOrDescription(String keyword) {
        return esStuffRepository.findByNameOrDescription(keyword, keyword);
    }

    //SEARCH <ESStuff> BY <String keyword>, intro searching <Name> AND <Description>
    @Override
    public SearchHits searchESStuffSimple(String keyword) {
        return esStuffRepository.findAndHighlight(keyword);
    }

    //由于ES无法添加自增ID，需要使用search after来进行新增、分页查询，下面为解决方案
    //https://juejin.cn/post/6947521240416567303
    //使用search scroll技术解决
    @Override
    public R searchPageESStuffByUserUUId(String userUUId, Integer searchSize, Integer pageNumber) {
        //构造查询顺序器
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withSorts(new FieldSortBuilder("modify_count")
                        .order(SortOrder.DESC)).build();
        //设置每页多少数据，pageNumber是第几页
        nativeSearchQuery.setMaxResults(searchSize);
        //设置缓存内数据的保留时间
        long scrollTimeInMillis = 60 * 1000;
        //第一次查询，组装快照
        SearchScrollHits<ESStuff> searchScrollHits = elasticsearchRestTemplate
                .searchScrollStart(scrollTimeInMillis, nativeSearchQuery, ESStuff.class, IndexCoordinates
                        .of("stuff"));
        String scrollId = searchScrollHits.getScrollId();

        //建造数据返回对象
        ArrayList<ESStuff> esStuffList = new ArrayList<>();

        // 清除 scroll
        List<String> scrollIds = new ArrayList<>();

        //判断是否为第一次
        Integer scrollTime = 1;
        //2、判断searchScrollHits中是否有命中数据，如果为空，则表示已将符合查询条件的数据全部遍历完毕
        while (searchScrollHits.hasSearchHits() && scrollTime < pageNumber) {
            log.info("Stuff List ES Search Query Hints Size: " + searchScrollHits.getSearchHits());
            //根据上次搜索结果scroll_id进入下一页数据搜索
            searchScrollHits = elasticsearchRestTemplate
                    .searchScrollContinue(scrollId, scrollTimeInMillis, ESStuff.class, IndexCoordinates
                            .of("stuff"));
            scrollId = searchScrollHits.getScrollId();

            scrollIds.add(scrollId);
            scrollTime++;

        }
        elasticsearchRestTemplate.searchScrollClear(scrollIds);


        //从缓存中读取数据
        for (SearchHit<ESStuff> searchHit : searchScrollHits.getSearchHits()) {
            ESStuff esStuff = searchHit.getContent();
            esStuffList.add(esStuff);
            log.info("esStuff: " + esStuff);
        }

        return R.success(200, "Page Search Success", new Date(), esStuffList);
    }

    @Override
    public R searchESStuffAllByUserUUId(String userUUId) {
        Criteria criteria = new Criteria("ref_user_id").is(userUUId);
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        log.info("Stuff List ES Search Query: " + criteria);

        org.springframework.data.elasticsearch.core.SearchHits<ESStuff> searchResult =
                elasticsearchRestTemplate.search(criteriaQuery, ESStuff.class);
        log.info("searchESStuffAllByUserUUId: " + searchResult);
        return R.success(200, "Search All By UserUUId Success", new Date(), searchResult);
    }

    //由于ES无法添加自增ID，需要使用search after来进行新增、分页查询，下面为解决方案
    //https://juejin.cn/post/6947521240416567303
    @Override
    public R insertOneESStuff(ESStuff esStuff) {
        esStuff.setModifyCount(1);
        esStuff.setModifyTime(new Date().toString());
        ESStuff save = esStuffRepository.save(esStuff);
        return R.success(200, "Insert Success", new Date(), save);
    }

    @Override
    public R updateOneESStuff(ESStuff esStuff) {
        esStuff.setModifyCount(esStuff.getModifyCount() + 1);
        esStuff.setModifyTime(new Date().toString());
        ESStuff save = esStuffRepository.save(esStuff);
        if ("".equals(save.getId())) {
            return R.error(404, "Update Failed", new Date(), esStuff.getId());
        }
        return R.success(200, "Update Success", new Date(), esStuff.getId());
    }

    @Override
    public R deleteOneESStuff(String esstuffUUId) {
        esStuffRepository.deleteById(esstuffUUId);
        return R.success(200, "Delete Success", new Date(), esstuffUUId);
    }


}

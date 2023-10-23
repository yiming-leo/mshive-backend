package com.yimingliao.mshivebackend.service.elastic.impl;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.elastic.ESStuff;
import com.yimingliao.mshivebackend.mapper.elastic.ESStuffRepository;
import com.yimingliao.mshivebackend.service.elastic.IESStuffService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

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
    @Override
    public R searchPageESStuffByUserUUId(String userUUId, Long lastSeenStuffId, Integer searchSize) {
        Criteria criteria = new
                Criteria("ref_user_id").is(userUUId)
                .and("stuff_id").greaterThan(lastSeenStuffId)
                .matches(searchSize);

        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        log.info("Stuff List ES Search Query: " + criteria);

        org.springframework.data.elasticsearch.core.SearchHits<ESStuff> searchResult =
                elasticsearchRestTemplate.search(criteriaQuery, ESStuff.class);
        log.info("searchESStuffListByUserUUId: " + searchResult);

        return R.success(200, "Page Search Success", new Date(), searchResult);
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

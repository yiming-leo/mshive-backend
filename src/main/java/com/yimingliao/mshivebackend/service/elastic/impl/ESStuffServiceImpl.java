package com.yimingliao.mshivebackend.service.elastic.impl;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.elastic.ESStuff;
import com.yimingliao.mshivebackend.mapper.elastic.ESStuffRepository;
import com.yimingliao.mshivebackend.service.elastic.IESStuffService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
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

    //SEARCH <ESStuff> BY <Name> OR <Description>, NEED <String Keyword>
    @Override
    public List<ESStuff> searchESStuffByNameOrDescription(String keyword) {
        return esStuffRepository.findByNameOrDescription(keyword, keyword);
    }

    //SEARCH <ESStuff> BY <String keyword>, intro searching <Name> AND <Description>
    @Override
    public SearchHits searchESStuffSimple(String keyword) {
        return esStuffRepository.find(keyword);
    }

    @Override
    public R insertOneESStuff(ESStuff esStuff) {
        esStuff.setModifyCount(1);
        esStuff.setModifyTime(new Date().toString());
        ESStuff save = esStuffRepository.save(esStuff);
        return R.success(200, "Insert Success", new Date(), save);
    }


}

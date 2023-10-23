package com.yimingliao.mshivebackend.service.elastic;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.elastic.ESStuff;
import org.elasticsearch.search.SearchHits;

import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description Elasticsearch Stuff Interface
 * @date 2023/10/19 20:36
 */
public interface IESStuffService {

    //SEARCH <ESStuff> BY <Name> OR <Description>
    List<ESStuff> searchESStuffByNameOrDescription(String keyword);

    //SEARCH <ESStuff> BY <String keyword>, intro searching <Name> AND <Description>
    SearchHits searchESStuffSimple(String keyword);

    //PAGE SEARCH <ESStuff> BY <USER UUID>
    R searchPageESStuffByUserUUId(String userUUId, Long lastSeenStuffId, Integer searchSize);

    R searchESStuffAllByUserUUId(String userUUId);

    //INSERT ONE <ESStuff> BY <ESStuff>
    R insertOneESStuff(ESStuff esStuff);

    //UPDATE ONE <ESStuff> BY <ESStuff>
    R updateOneESStuff(ESStuff esStuff);

    //DELETE ONE <ESStuff> BY <ESStuff>
    R deleteOneESStuff(String esstuffUUId);
}

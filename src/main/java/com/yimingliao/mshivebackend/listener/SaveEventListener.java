package com.yimingliao.mshivebackend.listener;

import com.yimingliao.mshivebackend.config.AutoIncKey;
import com.yimingliao.mshivebackend.entity.SeqInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author Calendo
 * @version 1.0
 * @description 自增监听器，监听mongodb里Room, User, Furniture, Stuff的CRUD情况以拦截插入自增ID
 * @date 2023/10/18 13:36
 */

@Component
public class SaveEventListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {

            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                ReflectionUtils.makeAccessible(field);
                // 如果字段添加了我们自定义的AutoIncKey注解
                if (field.isAnnotationPresent(AutoIncKey.class)) {
                    // 设置自增ID
                    field.set(source, getNextUserId(source.getClass().getSimpleName()));
                    field.set(source, getNextRoomId(source.getClass().getSimpleName()));
                    field.set(source, getNextFurnitureId(source.getClass().getSimpleName()));
                    field.set(source, getNextStuffId(source.getClass().getSimpleName()));
                }
            }
        });
    }

    private Long getNextUserId(String collName) {
        Query query = new Query(Criteria.where("user_id").is(collName));
        return getaLong(query);
    }

    private Long getNextRoomId(String collName) {
        Query query = new Query(Criteria.where("room_id").is(collName));
        return getaLong(query);
    }

    private Long getNextFurnitureId(String collName) {
        Query query = new Query(Criteria.where("furniture_id").is(collName));
        return getaLong(query);
    }

    private Long getNextStuffId(String collName) {
        Query query = new Query(Criteria.where("stuff_id").is(collName));
        return getaLong(query);
    }

    private Long getaLong(Query query) {
        Update update = new Update();
        update.inc("seqId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        SeqInfo seq = mongoTemplate.findAndModify(query, update, options, SeqInfo.class);
        System.out.println("seq: " + seq);
        return seq.getSeqId();
    }

}

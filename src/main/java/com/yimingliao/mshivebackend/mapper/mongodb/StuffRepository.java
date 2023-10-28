package com.yimingliao.mshivebackend.mapper.mongodb;

import com.yimingliao.mshivebackend.entity.mongodb.Stuff;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Calendo
 * @version 1.0
 * @description 物品实体类的持久层接口
 * @date 2023/10/28 18:44
 */
public interface StuffRepository  extends MongoRepository<Stuff, String> {
}

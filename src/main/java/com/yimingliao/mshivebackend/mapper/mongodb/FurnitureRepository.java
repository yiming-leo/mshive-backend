package com.yimingliao.mshivebackend.mapper.mongodb;

import com.yimingliao.mshivebackend.entity.mongodb.Furniture;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Calendo
 * @version 1.0
 * @description 家具实体类的持久层接口
 * @date 2023/10/16 20:35
 */
public interface FurnitureRepository extends MongoRepository<Furniture, String> {

}

package com.yimingliao.mshivebackend.mapper.mongodb;

import com.yimingliao.mshivebackend.entity.mongodb.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Calendo
 * @version 1.0
 * @description 用户实体类的持久层接口
 * @date 2023/10/17 21:37
 */
public interface UserRepository extends MongoRepository<User, String> {

}

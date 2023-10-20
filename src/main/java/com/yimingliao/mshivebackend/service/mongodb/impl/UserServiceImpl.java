package com.yimingliao.mshivebackend.service.mongodb.impl;

import com.mongodb.client.result.UpdateResult;
import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.User;
import com.yimingliao.mshivebackend.mapper.mongodb.UserRepository;
import com.yimingliao.mshivebackend.service.mongodb.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Calendo
 * @version 1.0
 * @description TODO
 * @date 2023/10/20 16:44
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    //Insert One User
    @Override
    public R insertOneUser(User user) {
        //保存新增的用户
        User saveUser = userRepository.save(user);
        log.info("insertOneUser: " + user);
        //判定是否保存成功
        if ("".equals(saveUser.getId())) {
            return R.error(404, "Failed", new Date());
        }
        return R.success(200, "Success", new Date());
    }

    //Update One Room
    @Override
    public R updateOneUser(User user) {
        //新建Query
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(user.getId()));
        Update update = new Update()
                .set("username", user.getUsername())
                .set("password", user.getPassword())
                .set("email", user.getEmail())
                .set("telephone", user.getTelephone())
                .set("avatarUrl", user.getAvatarUrl())
                .set("isAdmin", user.getIsAdmin())
                .set("accountStatus", user.getAccountStatus())
                .set("modifyTime", new Date().toString())//处理时间：后端插入时间
                .set("isDeleted", user.getIsDeleted())
                .set("lastLoginTime", user.getLastLoginTime());
        //保存新增的房间
        log.info("updateOneUser: " + update);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
        long modifiedCount = updateResult.getModifiedCount();
        //判定是否保存成功
        if (updateResult.getModifiedCount() == 0) {
            return R.error(404, "Update Failed", new Date(), modifiedCount);
        }
        return R.success(200, "Update Success", new Date(), modifiedCount);
    }

    //Delete One User, need userUUId
    @Override
    public R deleteOneUser(String userUUId) {
        userRepository.deleteById(userUUId);
        log.info("deleteOneFurnitureByUserUUId success");
        return R.success(200, "Success", new Date());
    }

    //Find One User, need userUUId
    @Override
    public R searchOneUserByUserUUId(String userUUId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userUUId));
        User user = mongoTemplate.findOne(query, User.class);
        if (user == null) {
            return R.error(404, "Failed", new Date());
        }
        return R.success(200, "Success", new Date(), user);
    }
}

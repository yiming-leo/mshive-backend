package com.yimingliao.mshivebackend.service.mongodb;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Room;
import com.yimingliao.mshivebackend.entity.mongodb.User;

/**
 * @author Calendo
 * @version 1.0
 * @description 业务层接口
 * @date 2023/10/17 21:39
 */
public interface IUserService {

    R addUser(User user);

    R updateUser(User user);

    R deleteUser(String userId);

    R findUserList(String startId, String endId);

    R findUserAll();

    R findUserById(String userId);

}

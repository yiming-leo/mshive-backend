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

    //-----------用户端操作----------

    R insertOneUser(User user);

    R updateOneUser(User user);

    R deleteOneUser(String userUUId);

    R searchOneUserByUserUUId(String userUUId);

    //-----------管理端操作----------

    //R findUserListByUserUUId(String startId, String endId);

    //R findUserAllIfActiveOrNot(Integer status);

}

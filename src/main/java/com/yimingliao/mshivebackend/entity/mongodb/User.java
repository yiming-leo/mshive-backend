package com.yimingliao.mshivebackend.entity.mongodb;

import com.yimingliao.mshivebackend.config.AutoIncKey;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Calendo
 * @version 1.0
 * @description MongoDB User Entity
 * @date 2023/10/17 21:24
 */
@Document(collection = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    //mongodb自动生成的主键，与_id对应
    @Id
    private String id;

    //增量ID，加快索引
    @Indexed
    @AutoIncKey
    @Field("user_id")
    private String userId;

    //账户状态：“1”:在用/”2”:封禁/”0”:注销
    @Field("account_status")
    private String accountStatus;

    //用户名：^[a-zA-Z][a-zA-Z0-9_]{4,15}$
    @Field("username")
    private String username;

    //密码：^[a-zA-Z]\w{5,17}$
    @Field("password")
    private String password;

    //邮箱：^\w+([-+.]\w+)@\w+([-.]\w+)\.\w+([-.]\w+)*$
    @Field("email")
    private String email;

    //电话：^(\(\d{3,4}-)|\d{3.4}-)?\d{7,8}$
    @Field("telephone")
    private String telephone;

    //头像图片地址：不超过2m
    @Field("avatar_url")
    private String avatarUrl;

    //是否为管理员：True/False
    @Field("is_admin")
    private Boolean isAdmin;

    //账号是否已删除：True/False
    @Field("is_deleted")
    private Boolean isDeleted;

    //账号修改时间
    @Field("modify_time")
    private LocalDateTime modifyTime;

    //上次登录时间，以判断账号是否可删除
    @Field("last_login_time")
    private LocalDateTime lastLoginTime;
}

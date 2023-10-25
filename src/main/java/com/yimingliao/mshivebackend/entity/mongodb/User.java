package com.yimingliao.mshivebackend.entity.mongodb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yimingliao.mshivebackend.config.AutoIncKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author Calendo
 * @version 1.0
 * @description MongoDB User Entity
 * @date 2023/10/17 21:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    //mongodb自动生成的主键，与_id对应
    @Id
    private String id;

    //增量ID，加快索引，由于此处为监听器自增，因此无法设置@JsonProperty类型转换
    @Indexed
    @AutoIncKey
    @Field("user_id")
    private Long userId = 0L;

    //账户状态：“1”:在用/”2”:封禁/”0”:注销
    @Field("account_status")
    @JsonProperty("account_status")
    private String accountStatus;

    //用户名：^[a-zA-Z][a-zA-Z0-9_]{4,15}$
    @Field("username")
    @JsonProperty("username")
    private String username;

    //密码：^[a-zA-Z]\w{5,17}$
    @Field("password")
    @JsonProperty("password")
    private String password;

    //邮箱：^\w+([-+.]\w+)@\w+([-.]\w+)\.\w+([-.]\w+)*$
    @Field("email")
    @JsonProperty("email")
    private String email;

    //头像图片地址：不超过2m
    @Field("avatar_url")
    @JsonProperty("avatar_url")
    private String avatarUrl;

    //是否为管理员：True/False
    @Field("is_admin")
    @JsonProperty("is_admin")
    private Boolean isAdmin;

    //账号是否已删除：True/False
    @Field("is_deleted")
    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    //账号修改时间
    @Field("modify_time")
    @JsonProperty("modify_time")
    private String modifyTime;

    //上次登录时间，以判断账号是否可删除
    @Field("last_login_time")
    @JsonProperty("last_login_time")
    private String lastLoginTime;
}

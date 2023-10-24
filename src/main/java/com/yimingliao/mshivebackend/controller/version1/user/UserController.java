package com.yimingliao.mshivebackend.controller.version1.user;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.service.mongodb.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Calendo
 * @version 1.0
 * @description TODO
 * @date 2023/10/25 0:15
 */
@RestController
@Slf4j
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/{user_uuid}/search_one")
    public R deleteOneUser(@PathVariable("user_uuid") String userUUId, @RequestParam("query_user_uuid") String queryUserUUId) {
        if (userService.searchOneUserByUserUUId(userUUId).getStatus() != 200) {
            return R.error(403, "Delete Failed", new Date(), "无权限删除");
        }
        return userService.deleteOneUser(queryUserUUId);
    }

}

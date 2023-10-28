package com.yimingliao.mshivebackend.service.mongodb;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.mongodb.Stuff;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Calendo
 * @version 1.0
 * @description Stuff 业务层接口
 * @date 2023/10/28 18:46
 */
public interface IStuffService {

    //Insert One Stuff
    R insertOneStuff(Stuff stuff);

    //Update One Stuff
    R updateOneStuff(Stuff stuff);

    //Delete One User's Stuff, need stuffUUId
    R deleteOneStuffByStuffUUId(String stuffUUId);

    //Find One User's Some Stuff, need lastSeenStuffId & searchSize
    R searchStuffListByUserUUId(String userUUId, Long lastSeenStuffId, Integer searchSize);

    //Find One User's All Stuff
    R searchStuffAllByUserUUId(String userUUId);

    //Find One User's One Stuff, need userUUId & stuffId
    R searchOneStuffByUserUUId(String userUUId, Long stuffId);

    //Download One User's Optional Rom Report Form, need userUUId & JSON:RoomReportForm
    R downloadOneUserStuffReportForm(HttpServletResponse response,
                                         String userUUId, String startDate,
                                         String endDate, Boolean onlyBookmarks,
                                         Boolean needAll) throws IOException;

}

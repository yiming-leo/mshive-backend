package com.yimingliao.mshivebackend.service.mongodb.impl;

import com.mongodb.client.result.UpdateResult;
import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.dto.StuffScrollListDTO;
import com.yimingliao.mshivebackend.entity.mongodb.Stuff;
import com.yimingliao.mshivebackend.entity.report.FurnitureReportForm;
import com.yimingliao.mshivebackend.entity.report.StuffReportForm;
import com.yimingliao.mshivebackend.mapper.mongodb.StuffRepository;
import com.yimingliao.mshivebackend.service.mongodb.IStuffService;
import com.yimingliao.mshivebackend.utils.ReportFormWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description Stuff 业务层实现类
 * @date 2023/10/28 18:47
 */
@Service
@Slf4j
public class StuffServiceImpl implements IStuffService {

    @Autowired
    private StuffRepository stuffRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ReportFormWriter reportFormWriter;

    //----------------------------------INSERT----------------------------------
    //Insert One Stuff
    @Override
    public R insertOneStuff(Stuff stuff) {
        //处理时间：后端插入时间
        stuff.setModifyTime(new Date().toString());
        //处理修改次数：新增数据的修改次数为0
        stuff.setModifyCount(0);
        //保存新增的物品
        Stuff saveStuff = stuffRepository.save(stuff);
        log.info("insertOneStuff: " + stuff);
        //判定是否保存成功
        if ("".equals(saveStuff.getId())) {
            return R.error(404, "Insert Failed", new Date());
        }
        return R.success(200, "Insert Success", new Date());
    }

    //----------------------------------UPDATE----------------------------------
    //Update One Stuff
    // TODO 按着FurnitureServiceImpl.java 来把这个接口统一规范
    @Override
    public R updateOneStuff(Stuff stuff) {
        System.out.println("stuff: " + stuff);
        //新建query
        Query query = new Query();
        //根据uuid查找stuff，这里where随便是id还是_id都可以，但是前端返回来的JSON必须和后端的Entity类相符合！
        query.addCriteria(Criteria.where("id").is(stuff.getId()));
        //更新stuff
        Update update = new Update().set("name", stuff.getName())
                .set("attribute", stuff.getAttribute())
                .set("mainColor", stuff.getMainColor())
                .set("minorColor", stuff.getMinorColor())
                .set("imgUrl", stuff.getImgUrl())
                .set("description", stuff.getDescription())
                .set("isBookmarks", stuff.getIsBookmarks())
                .set("modifyTime", new Date().toString())//处理时间：后端插入时间
                .set("modifyCount", stuff.getModifyCount() + 1)//处理修改次数：+1
                .set("refUserId", stuff.getRefUserId())
                .set("refFurnitureId", stuff.getRefFurnitureId())
                .set("refRoomId", stuff.getRefRoomId());//多了refRoomId可变基
        //保存新增的物品
        log.info("updateOneStuff: " + update);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Stuff.class);
        long modifiedCount = updateResult.getModifiedCount();
        //判定是否保存成功
        if (updateResult.getModifiedCount() == 0) {
            return R.error(404, "Update Failed", new Date(), modifiedCount);
        }
        return R.success(200, "Update Success", new Date(), modifiedCount);
    }

    //----------------------------------DELETE----------------------------------
    //Delete One User's Stuff, need stuffUUId
    @Override
    public R deleteOneStuffByStuffUUId(String stuffUUId) {
        stuffRepository.deleteById(stuffUUId);
        log.info("deleteOneStuffByStuffUUId success" + stuffUUId);
        return R.success(200, "Delete Success", new Date(), stuffUUId);
    }

    //----------------------------------SEARCH----------------------------------
    //Find One User's Some Stuff, need lastSeenStuffId & searchSize
    // TODO 按着FurnitureServiceImpl.java 来把这个接口统一规范
    @Override
    public R searchStuffListByUserUUId(String userUUId, Long lastSeenStuffId, Integer searchSize) {
        Query query = new Query();
        //define the start ID using addCriteria
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("room_id").gt(lastSeenStuffId));
        query.limit(searchSize);
        log.info("Room List Search Query: " + query);
        //Sort
        //Sort sort = Sort.by(order);
        //query.with(Sort.by(Sort.Direction.DESC))
        List<Stuff> stuffList = mongoTemplate.find(query, Stuff.class);
        //装载到RoomScrollListDTO内
        StuffScrollListDTO stuffScrollListDTO = new StuffScrollListDTO();
        stuffScrollListDTO.setLastSeenStuffId(lastSeenStuffId);
        stuffScrollListDTO.setStuffList(stuffList);
        stuffScrollListDTO.setSearchSize(searchSize);
        log.info("searchRoomListByUserUUId: " + stuffScrollListDTO);
        return R.success(200, "Search Success", new Date(), stuffScrollListDTO);
    }

    //Find One User's All Stuff
    @Override
    public R searchStuffAllByUserUUId(String userUUId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        List<Stuff> stuffList = mongoTemplate.find(query, Stuff.class);
        if (stuffList.isEmpty()) {
            return R.error(404, "Search Failed", new Date());
        }
        return R.success(200, "Search Success", new Date(), stuffList);
    }

    //Find One User's One Stuff, need userUUId & stuffId
    @Override
    public R searchOneStuffByUserUUId(String userUUId, Long stuffId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("stuff_id").is(stuffId));
        List<Stuff> stuffList = mongoTemplate.find(query, Stuff.class);
        if (stuffList.isEmpty()) {
            return R.error(404, "Search Failed", new Date());
        }
        return R.success(200, "Search Success", new Date(), stuffList);
    }

    //----------------------------------DOWNLOAD----------------------------------
    //Download One User's Optional Rom Report Form, need userUUId & JSON:RoomReportForm
    @Override
    public ResponseEntity downloadOneUserStuffReportForm(String userUUId, String startDate,
                                                         String endDate, Boolean onlyBookmarks,
                                                         Boolean needAll) throws IOException {
        //查询条件
        Query query = new Query();
        query.addCriteria(Criteria.where("ref_user_id").is(userUUId));
        query.addCriteria(Criteria.where("is_bookmarks").is(onlyBookmarks));
        if (!needAll) {
            query.addCriteria(Criteria.where("modify_time").gte(startDate).lte(endDate));
        }
        List<Stuff> stuffList = mongoTemplate.find(query, Stuff.class);
        log.info(stuffList.toString());
        //空则404
        if (stuffList.isEmpty()) {
            return null;
        }

        //交给报表生成工具类处理
        ResponseEntity responseEntity = reportFormWriter.excelReportForm(stuffList, FurnitureReportForm.class);
        log.info(responseEntity.toString());
        return responseEntity;
    }
}

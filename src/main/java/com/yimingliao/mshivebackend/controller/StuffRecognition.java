package com.yimingliao.mshivebackend.controller;

import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.ImageInfo;
import com.yimingliao.mshivebackend.entity.TagResult;
import com.yimingliao.mshivebackend.huaweicloud.RunImageMediaTaggingSolution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description AI物品识别模块接口，只有一个函数：imageMediaTagging，拥有1.分类贴标签返回；2.分类贴标签定位返回
 * @date 2023/10/14 20:30
 */
@Controller
@RequestMapping("/ai_recognize")
@Slf4j
public class StuffRecognition {

    @Autowired
    RunImageMediaTaggingSolution runImageMediaTaggingSolution;

    //分类、贴标签或定位
    @PostMapping("/tagging")
    @ResponseBody//自动序列化，自动传回响应体标识，JSON文件返回不加会404找Bug到死
    public R imageMediaTagging(@RequestBody ImageInfo imageInfo) {//RB专门针对JSON返回
        //读取图像的url
        String imgUrl = imageInfo.getImgUrl();
        //读取图像是否需要返回坐标
        String needDet = imageInfo.getNeedDet();
        //读取图像需要哪种语言
        String language = imageInfo.getLanguage();
        //读取图像检测置信度
        String threshold = imageInfo.getThreshold();
        //读取图像物体识别数量
        Integer limit = imageInfo.getLimit();
        if ("true".equals(needDet)) {//需要定位
            try {
                List<TagResult> tagResults = runImageMediaTaggingSolution.ImageMediaTaggingDetFunction(imgUrl, language, threshold, limit);
                if (tagResults==null) {
                    log.info("ImageMediaTaggingDetFunction Fail");
                    return R.error(404, "识别失败", new Date(), "未检测到物品");
                } else {
                    log.info("ImageMediaTaggingDetFunction Success");
                    return R.success(200, "识别成功", new Date(), tagResults);
                }
            } catch (ConnectionException | RequestTimeoutException e) {
                log.error(Arrays.toString(e.getStackTrace()));
                return R.error(404, e.getMessage(), new Date(), e.getStackTrace());
            } catch (ServiceResponseException e) {
                log.error(String.valueOf(e.getHttpStatusCode()));
                log.error(String.valueOf(e.getErrorCode()));
                log.error(String.valueOf(e.getErrorMsg()));
                return R.error(e.getHttpStatusCode(), "识别失败", new Date(), e.getErrorMsg());
            }
        } else {//不需要定位
            try {
                List<TagResult> tagResults = runImageMediaTaggingSolution.ImageMediaTaggingFunction(imgUrl, language, threshold, limit);
                if (tagResults==null) {
                    log.info("ImageMediaTaggingFunction Fail");
                    return R.error(404, "识别失败", new Date(), "未检测到物品");
                } else {
                    log.info("ImageMediaTaggingFunction Success");
                    return R.success(200, "识别成功", new Date(), tagResults);
                }
            } catch (ConnectionException | RequestTimeoutException e) {
                log.error(Arrays.toString(e.getStackTrace()));
                return R.error(404, e.getMessage(), new Date(), e.getStackTrace());
            } catch (ServiceResponseException e) {
                log.error(String.valueOf(e.getHttpStatusCode()));
                log.error(String.valueOf(e.getErrorCode()));
                log.error(String.valueOf(e.getErrorMsg()));
                return R.error(e.getHttpStatusCode(), "识别失败", new Date(), e.getErrorMsg());
            }
        }

    }

    //人脸识别
}

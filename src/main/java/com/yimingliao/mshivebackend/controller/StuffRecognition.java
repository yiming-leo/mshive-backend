package com.yimingliao.mshivebackend.controller;

import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.ImageInfo;
import com.yimingliao.mshivebackend.entity.TagResult;
import com.yimingliao.mshivebackend.huaweicloud.RunImageMediaTaggingSolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description AI识别模块接口
 * @date 2023/10/14 20:30
 */
@Controller
@RequestMapping("/ai_recognize")
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
                return R.success(200, "识别成功", new Date(), tagResults);
            } catch (ConnectionException | RequestTimeoutException e) {
                e.printStackTrace();
                return R.error(404, e.getMessage(), new Date(), e.getStackTrace());
            } catch (ServiceResponseException e) {
                System.out.println(e.getHttpStatusCode());
                System.out.println(e.getErrorCode());
                System.out.println(e.getErrorMsg());
                return R.error(e.getHttpStatusCode(), "识别失败", new Date(), e.getErrorMsg());
            }
        } else {//不需要定位
            try {
                List<TagResult> tagResults = runImageMediaTaggingSolution.ImageMediaTaggingFunction(imgUrl, language, threshold, limit);
                return R.success(200, "识别成功", new Date(), tagResults);
            } catch (ConnectionException | RequestTimeoutException e) {
                e.printStackTrace();
                return R.error(404, e.getMessage(), new Date(), e.getStackTrace());
            } catch (ServiceResponseException e) {
                System.out.println(e.getHttpStatusCode());
                System.out.println(e.getErrorCode());
                System.out.println(e.getErrorMsg());
                return R.error(e.getHttpStatusCode(), "识别失败", new Date(), e.getErrorMsg());
            }
        }

    }

    //人脸识别
}

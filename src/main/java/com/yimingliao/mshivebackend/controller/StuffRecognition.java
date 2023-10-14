package com.yimingliao.mshivebackend.controller;

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
 * @description TODO
 * @date 2023/10/14 20:30
 */
@Controller
@RequestMapping("/recognize")
public class StuffRecognition {

    @Autowired
    RunImageMediaTaggingSolution runImageMediaTaggingSolution;

    @PostMapping("/ai_model_tagging")
    @ResponseBody//自动序列化，自动传回响应体标识，JSON文件返回不加会404找Bug到死
    public R imageMediaTagging(@RequestBody ImageInfo imgUrl) {//RB专门针对JSON返回
        //读取图像的url
        String url = imgUrl.getImgUrl();
        List<TagResult> tagResults = runImageMediaTaggingSolution.ImageMediaTaggingFunction(url);
        return R.success(200, new Date(), tagResults);
    }

}

package com.yimingliao.mshivebackend.Controller;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.ImgUrl;
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
public class ThirdPartAPI {

    @Autowired
    RunImageMediaTaggingSolution runImageMediaTaggingSolution;

    //"https://liaoyiming.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fe8717364-cff0-4ad6-9e26-fe4373ff41b6%2F0821f13b-f951-4deb-87bb-1b2bc0d37791%2F%25E5%25BE%25AE%25E4%25BF%25A1%25E5%259B%25BE%25E7%2589%2587_20231014174535.jpg?id=02986b01-f490-45ad-a996-f7e99f8c4287&table=block&spaceId=e8717364-cff0-4ad6-9e26-fe4373ff41b6&width=600&userId=&cache=v2";
    @PostMapping("/ai_model_tagging")
    @ResponseBody//不加会404找Bug到死
    public R imageMediaTagging(@RequestBody ImgUrl imgUrl) {//RB专门针对JSON返回
        //读取图像的url
        String url = imgUrl.getImgUrl();
        List<TagResult> tagResults = runImageMediaTaggingSolution.ImageMediaTaggingFunction(url);
        return R.success(200, new Date(), tagResults);
    }

}

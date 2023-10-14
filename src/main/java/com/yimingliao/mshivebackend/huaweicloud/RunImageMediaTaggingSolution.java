package com.yimingliao.mshivebackend.huaweicloud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.image.v2.ImageClient;
import com.huaweicloud.sdk.image.v2.model.*;
import com.huaweicloud.sdk.image.v2.region.ImageRegion;
import com.yimingliao.mshivebackend.entity.TagResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description Huawei AI Function 物体识别并打上标记返回
 * @date 2023/10/14 17:17
 */

@Component
public class RunImageMediaTaggingSolution {
    public List<TagResult> ImageMediaTaggingFunction(String imgUrl) {
        //此处需要输入您的AK/SK信息
        String ak = "ZSG8S9FUVEGYEWW8ACLI";
        String sk = "BATI8FwgJVlVr1SdrZ3rWWAfQtDf6BobNKLoIVUV";
        //读入AK与SK
        ICredential auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk);
        //此处替换为您开通服务的区域
        ImageClient client = ImageClient.newBuilder()
                .withCredential(auth)
                .withRegion(ImageRegion.valueOf("cn-north-4"))
                .build();
        //创建Request请求
        RunImageMediaTaggingRequest request = new RunImageMediaTaggingRequest();
        //创建Request请求体
        ImageMediaTaggingReq body = new ImageMediaTaggingReq();
        //置信区间，越大越苛刻
        body.withThreshold(20f);
        //zh或者en
        body.withLanguage("zh");
        //检测物体数量限制
        body.withLimit(15);
        //使用默认Tag
        body.withUseDefaultTags("true");
        //载入图片URL
        body.withUrl(imgUrl);
        //写入请求体
        request.withBody(body);
        //执行时抛异常
        try {
            //创建响应载体并返回
            RunImageMediaTaggingResponse response = client.runImageMediaTagging(request);
            //去壳result
            ImageMediaTaggingResponseResult responseResult = response.getResult();
            //去壳tags，得到confidence,type,tag,i18nTag,i18nType,instance的List
            List<ImageMediaTaggingItemBody> responseResultTags = responseResult.getTags();
            System.out.println(responseResultTags);
            //转储部分数据到新的对象中
            List<TagResult> tagResults = new ArrayList<>();
            for (int i = 0; i < responseResultTags.size(); i++) {
                TagResult tagResult = new TagResult();
                ImageMediaTaggingItemBody imageMediaTaggingItem = responseResultTags.get(i);
                ImageMediaTaggingItemBodyI18nTag i18nTag = imageMediaTaggingItem.getI18nTag();
                ImageMediaTaggingItemBodyI18nType i18nType = imageMediaTaggingItem.getI18nType();
                tagResult.setType(i18nType.getZh());
                tagResult.setTag(i18nTag.getZh());
                tagResult.setTypeEng(i18nType.getEn());
                tagResult.setTagEng(i18nTag.getEn());
                tagResults.add(tagResult);
            }
            System.out.println(tagResults);
            return tagResults;
        } catch (ConnectionException | RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }
        return null;
    }
}

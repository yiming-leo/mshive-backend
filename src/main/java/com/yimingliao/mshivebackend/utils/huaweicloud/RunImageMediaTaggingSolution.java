package com.yimingliao.mshivebackend.utils.huaweicloud;

import cn.hutool.core.bean.BeanUtil;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.image.v2.ImageClient;
import com.huaweicloud.sdk.image.v2.model.*;
import com.huaweicloud.sdk.image.v2.region.ImageRegion;
import com.yimingliao.mshivebackend.entity.BoundingBox;
import com.yimingliao.mshivebackend.entity.TagResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description Huawei AI Function 媒资图像识别，贴标签，分类，返回
 * @date 2023/10/14 17:17
 */

@Component
@Slf4j
public class RunImageMediaTaggingSolution {

    @Value("${constants.huawei-cloud.ak}")
    private String MY_AK;

    @Value("${constants.huawei-cloud.sk}")
    private String MY_SK;

    //仅进行分类和标签
    public List<TagResult> ImageMediaTaggingFunction(
            String imgUrl, String language,
            String threshold, Integer limit) throws ConnectionException, RequestTimeoutException, ServiceResponseException {
        //读入AK与SK
        ICredential auth = new BasicCredentials()
                .withAk(MY_AK)
                .withSk(MY_SK);
        //此处替换为您开通服务的区域
        ImageClient client = ImageClient.newBuilder()
                .withCredential(auth)
                .withRegion(ImageRegion.valueOf("cn-north-4"))
                .build();
        //创建Request请求
        RunImageMediaTaggingRequest request = new RunImageMediaTaggingRequest();
        //创建Request请求体
        ImageMediaTaggingReq body = new ImageMediaTaggingReq();
        //参数配置
        body.withThreshold(Float.valueOf(threshold))//置信区间，越大越苛刻
                .withLanguage(language)//zh或者en
                .withLimit(limit)//检测物体数量限制
                .withUseDefaultTags("true")//使用默认Tag
                .withUrl(imgUrl);//载入图片URL
        //写入请求体
        request.withBody(body);
        //创建响应载体并返回
        RunImageMediaTaggingResponse response = client.runImageMediaTagging(request);
        //去壳result
        ImageMediaTaggingResponseResult responseResult = response.getResult();
        //去壳tags，得到confidence,type,tag,i18nTag,i18nType,instance的List
        List<ImageMediaTaggingItemBody> responseResultTags = responseResult.getTags();
        //判断是否为空
        if (responseResultTags != null) {
            //转储部分数据到新的对象中
            List<TagResult> tagResults = new ArrayList<>();
            for (ImageMediaTaggingItemBody responseResultTag : responseResultTags) {
                TagResult tagResult = new TagResult();
                String type = responseResultTag.getType();
                String tag = responseResultTag.getTag();
                tagResult.setType(type);
                tagResult.setTag(tag);
                tagResults.add(tagResult);
            }
            log.debug(tagResults.toString());
            return tagResults;
        } else {
            return null;
        }
    }

    //分类、标签和定位（有Det）
    public List<TagResult> ImageMediaTaggingDetFunction(
            String imgUrl, String language,
            String threshold, Integer limit) throws ConnectionException, RequestTimeoutException, ServiceResponseException {
        //读入AK与SK
        ICredential auth = new BasicCredentials()
                .withAk(MY_AK)
                .withSk(MY_SK);
        //此处替换为您开通服务的区域
        ImageClient client = ImageClient.newBuilder()
                .withCredential(auth)
                .withRegion(ImageRegion.valueOf("cn-north-4"))
                .build();
        //创建Request请求
        RunImageMediaTaggingDetRequest request = new RunImageMediaTaggingDetRequest();
        //创建Request请求体
        ImageMediaTaggingDetReq body = new ImageMediaTaggingDetReq();
        //参数配置
        body.withThreshold(Float.valueOf(threshold))//置信区间，越大越苛刻
                .withLanguage(language)//zh或者en
                .withLimit(limit)//检测物体数量限制
                .withUrl(imgUrl);//载入图片URL
        //写入请求体
        request.withBody(body);
        //创建响应载体并返回
        RunImageMediaTaggingDetResponse response = client.runImageMediaTaggingDet(request);
        //去壳result
        ImageMediaTaggingDetResponseResult responseResult = response.getResult();
        //去壳tags，得到confidence,type,tag,i18nTag,i18nType,instance的List
        List<ImageMediaTaggingDetItemBody> responseResultTags = responseResult.getTags();
        //判断是否为空
        if (responseResultTags != null) {
            //转储部分数据到新的对象中
            List<TagResult> tagResults = new ArrayList<>();
            for (ImageMediaTaggingDetItemBody responseResultTag : responseResultTags) {
                TagResult tagResult = new TagResult();
                List<ImageMediaTaggingDetInstance> instances = responseResultTag.getInstances();
                //处理instances里面的det参数
                for (ImageMediaTaggingDetInstance imageMediaTaggingDetInstance : instances) {
                    //创建boundingBox来把原bean拷贝到新bean内
                    BoundingBox boundingBox = new BoundingBox();
                    BeanUtil.copyProperties(imageMediaTaggingDetInstance.getBoundingBox(), boundingBox);
                    tagResult.setBoundingBox(boundingBox);
                }
                String tag = responseResultTag.getTag();
                String type = responseResultTag.getType();
                tagResult.setType(type);
                tagResult.setTag(tag);
                tagResults.add(tagResult);
            }
            log.debug(tagResults.toString());
            return tagResults;
        } else {
            return null;
        }
    }
}

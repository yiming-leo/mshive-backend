package com.yimingliao.mshivebackend.utils.tencentcloud;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tiia.v20190529.TiiaClient;
import com.tencentcloudapi.tiia.v20190529.models.DetectProductRequest;
import com.tencentcloudapi.tiia.v20190529.models.DetectProductResponse;
import com.tencentcloudapi.tiia.v20190529.models.Product;
import com.yimingliao.mshivebackend.entity.image.BoundingBox;
import com.yimingliao.mshivebackend.entity.SecretKey;
import com.yimingliao.mshivebackend.entity.image.TagResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description Tencent AI Function 媒资图像识别，贴标签，分类，，定位返回
 * @date 2023/10/16 14:32
 */
@Component
@Slf4j
public class DetectProduct {

    public List<TagResult> detectProductFunction(String imgUrl, String threshold, SecretKey secretKey) throws TencentCloudSDKException {
        // 密钥可前往官网控制台 https://console.cloud.tencent.com/cam/capi 进行获取
        //读入AK与SK
        String ak = secretKey.getAk();
        String sk = secretKey.getSk();
        Credential cred = new Credential(ak, sk);
        // 选择用户接入服务器终端
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("tiia.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 选择AI运行终端
        TiiaClient client = new TiiaClient(cred, "ap-shanghai", clientProfile);
        // 实例化一个请求对象,每个接口都会对应一个request对象
        DetectProductRequest req = new DetectProductRequest();
        req.setImageUrl(imgUrl);
        // 返回的resp是一个DetectProductResponse的实例，与请求对象对应
        DetectProductResponse resp = client.DetectProduct(req);
        //拆壳
        Product[] products = resp.getProducts();
        //创建容器接收
        if (products != null) {
            List<TagResult> tagResults = new ArrayList<>();
            //将产品导入tagResult，再将tagResult导入tagResults
            for (Product product : products) {
                //去除置信度要求以下的数据
                if (product.getConfidence() < Long.parseLong(threshold)) {
                    continue;
                }
                //开始装载tagResult
                TagResult tagResult = new TagResult();
                tagResult.setTag(product.getName());
                tagResult.setType(product.getParents());
                BoundingBox boundingBox = new BoundingBox();
                boundingBox.setTopLeftX(product.getXMin().intValue());
                boundingBox.setTopLeftY(product.getYMin().intValue());
                boundingBox.setWidth(product.getXMax().intValue() - product.getXMin().intValue());
                boundingBox.setHeight(product.getYMax().intValue() - product.getYMin().intValue());
                tagResult.setBoundingBox(boundingBox);
                log.debug(boundingBox.toString());
                tagResults.add(tagResult);
            }
            log.debug(tagResults.toString());
            return tagResults;
        }
        return null;
    }

}

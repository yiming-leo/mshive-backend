package com.yimingliao.mshivebackend.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description TODO
 * @date 2023/10/29 13:00
 */
@Component
public class ReportFormWriter {

    public ResponseEntity excelReportForm(List<?> rawEntityList,
                                          Class<?> toEntityClassName) throws IOException {
        //写文件名
        String fileName = toEntityClassName.getSimpleName() + System.currentTimeMillis() + ".xlsx";

        //写文件流响应
        String filePath = "/" + fileName;
        File file = new File(filePath);

        //写响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Headers", "*");
        headers.add("Access-Control-Allow-Methods", "*");
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Max-Age", "3600");
        headers.add("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" +
                URLEncoder.encode(fileName, "UTF-8"));

        //将List<stuff> bean转为List<stuffReportForm> bean
        List<?> resultList = BeanUtil.copyToList(rawEntityList, toEntityClassName);
        //装入easy excel
        EasyExcel.write(file, toEntityClassName)
                .autoCloseStream(true).sheet("sheet1").doWrite(resultList);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(Files.newInputStream(file.toPath())));
    }

}

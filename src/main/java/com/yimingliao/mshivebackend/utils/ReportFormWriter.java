package com.yimingliao.mshivebackend.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.entity.report.StuffReportForm;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description TODO
 * @date 2023/10/29 13:00
 */
@Component
public class ReportFormWriter {

    public List<?> excelReportForm(HttpServletResponse response,
                                   List<?> rawEntityList,
                                   Class<?> toEntityClassName) throws IOException {
        //写文件名
        String fileName = toEntityClassName.getSimpleName() + System.currentTimeMillis() + ".xlsx";

        //写文件流响应
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" +
                URLEncoder.encode(fileName, "UTF-8"));

        //将List<stuff> bean转为List<stuffReportForm> bean
        List<?> resultList = BeanUtil.copyToList(rawEntityList, toEntityClassName);
        //装入easy excel
        EasyExcel.write(out, toEntityClassName)
                .autoCloseStream(true).sheet("sheet1").doWrite(resultList);
        //清空文件流残存
        out.flush();
        return resultList;
    }

}

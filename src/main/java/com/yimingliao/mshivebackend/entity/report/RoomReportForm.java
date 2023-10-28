package com.yimingliao.mshivebackend.entity.report;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yimingliao.mshivebackend.config.AutoIncKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author Calendo
 * @version 1.0
 * @description Room报表类，动态列可参考
 * https://cloud.tencent.com/developer/article/2064827
 * https://blog.csdn.net/qq_42623400/article/details/130295292
 * @date 2023/10/26 22:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ExcelIgnoreUnannotated//EasyExcel:不加ExcelProperty注解的字段就不会参与
public class RoomReportForm {

    //房间名称
    @ExcelProperty("房间名称")
    private String name;

    //房间属性
    @ExcelProperty("属性")
    private String attribute;

    //主颜色
    @ExcelProperty("主色")
    private String mainColor;

    //副颜色
    @ExcelProperty("副色")
    private String minorColor;

    //描述
    @ExcelProperty("描述")
    private String description;

    //标记
    @ExcelProperty("是否星标")
    private Boolean isBookmarks;

    //修改次数
    @ExcelProperty("修改总次数")
    private Integer modifyCount;

    //上次修改时间
    @ExcelProperty("上次修改时间")
    private String modifyTime;

    //用户归属指向
    @ExcelProperty("所属用户")
    private String refUserId;

}

package com.yimingliao.mshivebackend.entity.report;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Calendo
 * @version 1.0
 * @description Stuff报表类，动态列可参考
 *  * https://cloud.tencent.com/developer/article/2064827
 *  * https://blog.csdn.net/qq_42623400/article/details/130295292
 * @date 2023/10/28 12:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ExcelIgnoreUnannotated//EasyExcel:不加ExcelProperty注解的字段就不会参与
public class StuffReportForm {

    //家具名称
    @ExcelProperty("家具名称")
    private String name;

    //家具属性
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

}

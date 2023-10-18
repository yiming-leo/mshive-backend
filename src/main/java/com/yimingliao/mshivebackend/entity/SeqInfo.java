package com.yimingliao.mshivebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Calendo
 * @version 1.0
 * @description 记录每个集合的ID自增到了多少，是一个虚拟类
 * @date 2023/10/18 13:19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "sequence")
public class SeqInfo {

    //主键
    @Id
    private String id;

    //集合名称
    @Field
    private String collName;

    //序列值
    @Field
    private Long seqId;

}

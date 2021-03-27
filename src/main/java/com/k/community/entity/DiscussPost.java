package com.k.community.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*@ClassName: DiscussPost
*@Description: DiscussPost
*@Author 77166
*@Date 2021/3/26
*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussPost {
    private Integer id;

    private Integer userId;

    private String title;

    private String content;

    /**
    * 0-普通; 1-置顶;
    */
    private Integer type;

    /**
    * 0-正常; 1-精华; 2-拉黑;
    */
    private Integer status;

    private Date createTime;

    private Integer commentCount;

    private Double score;
}
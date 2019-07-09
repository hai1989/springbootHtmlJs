package com.renren.jinkong.user_center.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Program:       user_center
 * package        com.renren.jinkong.user_center.entity
 * ClassName:     MyPage
 * Description:   This is  a  class!
 * Date:          Created in  2019/7/9 13:09
 * Author         gaohaijiang
 * Version        V1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPage {

    private String order;
    private Integer offset;
    private Integer limit;
    private Integer size;
    private Integer current;
}

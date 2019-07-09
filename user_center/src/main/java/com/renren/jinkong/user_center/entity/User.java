package com.renren.jinkong.user_center.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -4559363429512816773L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("username")
    private String  userName;
    private String  password;
    @TableField("createTime")
    private String  createTime;
    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;

}

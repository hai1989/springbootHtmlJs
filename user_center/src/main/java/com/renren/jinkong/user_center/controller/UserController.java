package com.renren.jinkong.user_center.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.renren.jinkong.user_center.config.Result;
import com.renren.jinkong.user_center.entity.MyPage;
import com.renren.jinkong.user_center.entity.User;
import com.renren.jinkong.user_center.service.IUserService;
import com.renren.jinkong.user_center.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService iUserService;

    @PostMapping("/register")
    public Result addUser(@ModelAttribute User user){

        if(user.getUserName()==null||user.getUserName()==""||user.getPassword()==null||user.getPassword()==""){
            return ResultUtil.error("100","用户名和密码不能为空");
        }

        boolean findFlag =iUserService.list(new QueryWrapper<User>().eq("username",user.getUserName())).size()>0?true:false;
        log.info("----"+findFlag);
        if(findFlag){
            return  ResultUtil.error("100","用户名"+user.getUserName()+"已经存在");
        }
        user.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        boolean insertFlag = iUserService.save(user);
        if(insertFlag){
            return ResultUtil.success(user.getUserName()+"注册成功");
        }
        else{
            return ResultUtil.success(user.getUserName()+"注册失败");
        }
    }

    //分页
    @PostMapping("/getAllByPage")
    public Map<String,Object>  getAllByPage(@RequestBody   MyPage myPage){//json
       //order=asc&offset=0&limit=10&size=10&current=1
//        IPage<User>  page = new MyPage<>(current,size);
        log.info("======="+myPage.getSize()+"\t"+myPage.getCurrent()+"\t"+myPage.getOrder()+"\t"+myPage.getLimit());
        int sizes = myPage.getSize();
        int sizep = myPage.getSize().intValue();
        Map<String,Object> map = new HashMap<>();
        IPage<User> iPage =  iUserService.page(new Page<>(myPage.getCurrent(),sizes),new QueryWrapper<User>().select("username","password","createTime").orderByDesc("createTime"));
        map.put("code",200);
        map.put("message","sucess");
        map.put("total",iPage.getTotal());
        List<User>  userList = iPage.getRecords();
        for(User user:userList){
//            log.info("--------------------"+user);
            user.setId((int)(sizes-(--sizep)));
        }
        map.put("size",iPage.getSize());
        map.put("current",iPage.getCurrent());
        map.put("pages",iPage.getPages());
        map.put("rows",JSONObject.toJSON(userList));
        return map ;
    }

    //批量插入
    @GetMapping("/addBatch")
    public String insertList(int count,int perBatchSize){

        List<User> userList =  new ArrayList<>();
        for(int i=1;i<=count;i++){
            userList.add(new User(0,"AutoName"+i,"123456",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()),0));
        }
        return iUserService.saveBatch(userList,perBatchSize)==true?"批量插入成功":"批量插入失败";

    }

    @PostMapping("/login")
    public Result findUser(@ModelAttribute  User user){//form

        if(user.getUserName()==null||user.getUserName()==""||user.getPassword()==null||user.getPassword()==""){
            return ResultUtil.error("100","用户名和密码不能为空");
        }
        Map<String,Object>  userInfoMap =  new HashMap<>();
        userInfoMap.put("username",user.getUserName());
        userInfoMap.put("password",user.getPassword());
        List<User> userList = iUserService.list(new QueryWrapper<User>().allEq(userInfoMap));
        if(userList==null||userList.size()==0){
            return  ResultUtil.error("100","用户名或是密码错误");
        }

        if(userList.get(0).getIsDelete()==1){
            return  ResultUtil.error("100",user.getUserName()+"用户已经失效");//mybatis-plus逻辑删除时，该处无用
        }
        else{
            return ResultUtil.success(user);
        }
    }

    @GetMapping("/logout")
    public Result deleteUser(@ModelAttribute User user){

        boolean isDelete = iUserService.remove(new QueryWrapper<User>().eq("username",user.getUserName()));
        if(isDelete){
           return ResultUtil.success(user.getUserName()+"删除成功");
        }
        else {
          return   ResultUtil.success(user.getUserName()+"删除失败");
        }
    }

}

package com.renren.jinkong.user_center.util;

import com.renren.jinkong.user_center.config.Result;

public class ResultUtil {

    public   static Result success(Object  data){

        Result  result =  new Result();
        result.setCode("200");
        result.setMessage("success");
        result.setData(data);
        return   result;
    }

//    public   static Result success(String  message){
//
//        Result  result =  new Result();
//        result.setCode("200");
//        result.setMessage(message);
////        result.setData(data);
//        return   result;
//    }

    public static   Result  error(String code ,String  message){
        Result result  = new Result();
        result.setCode(code);
        result.setMessage(message);
        return  result;
    }
}

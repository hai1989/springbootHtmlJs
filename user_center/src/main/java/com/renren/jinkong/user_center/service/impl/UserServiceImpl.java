package com.renren.jinkong.user_center.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renren.jinkong.user_center.entity.User;
import com.renren.jinkong.user_center.mapper.UserMapper;
import com.renren.jinkong.user_center.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}

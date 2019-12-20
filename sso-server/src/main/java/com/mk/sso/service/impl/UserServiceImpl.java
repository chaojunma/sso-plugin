package com.mk.sso.service.impl;

import com.mk.sso.entity.User;
import com.mk.sso.mapper.UserMapper;
import com.mk.sso.service.UserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}

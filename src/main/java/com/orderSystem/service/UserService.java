package com.orderSystem.service;

import com.orderSystem.DTO.UserDTO;
import com.orderSystem.common.JwtUtils;
import com.orderSystem.entity.User;
import com.orderSystem.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.orderSystem.common.BaseContext;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    private final RedisTemplate<String,String> redisTemplate;
    public UserService(UserMapper userMapper, RedisTemplate<String,String> redisTemplate){
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
    }

    public String userRegister(UserDTO userDTO){
        User new_user = new User();
        new_user.setUserName(userDTO.getUserName());
        new_user.setPassword(userDTO.getPassword());
        new_user.setBalance(0);
        new_user.setCreatedAt(LocalDateTime.now());
        userMapper.addUser(new_user);
        log.info("注册添加用户: {}", userDTO.getUserName());
        String token = JwtUtils.jwtCreate(userMapper.getUserId(userDTO.getUserName()));
        redisTemplate.opsForValue().set(userDTO.getUserName(), token,10, TimeUnit.HOURS);
        return token;
    }

    public String userLogin(UserDTO userDTO){
        if (redisTemplate.opsForValue().get(userDTO.getUserName()) !=null){
            return redisTemplate.opsForValue().get(userDTO.getUserName());
        }
        if (userDTO.getPassword().equals(userMapper.getUserPassword(userDTO.getUserName()))){
            BaseContext.setCurrentUser(userMapper.getUserId(userDTO.getUserName()));
            String token = JwtUtils.jwtCreate(userMapper.getUserId(userDTO.getUserName()));
            redisTemplate.opsForValue().set(userDTO.getUserName(), token,10, TimeUnit.HOURS);
            log.info("用户 {} 登录",userDTO.getUserName());
            return token;
        }else {
            return "登录失败，请检查账号密码";
        }
    }
}

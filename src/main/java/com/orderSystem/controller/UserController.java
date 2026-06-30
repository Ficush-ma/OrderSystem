package com.orderSystem.controller;

import com.orderSystem.DTO.UserDTO;
import com.orderSystem.entity.Result;
import com.orderSystem.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result<String> userRegister(@RequestBody UserDTO userDTO){
        return Result.success(userService.userRegister(userDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<String> userLogin(@RequestBody UserDTO userDTO){
        return Result.success(userService.userLogin(userDTO));

    }
}

package com.etshn.stock.service;

import java.util.List;

import com.etshn.stock.entity.User;
import com.etshn.stock.payload.LoginDto;
import com.etshn.stock.payload.RegisterDto;
import com.etshn.stock.payload.UserDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
    
    List<User> allUsers();
    
    UserDto findByUsername(String username);

}

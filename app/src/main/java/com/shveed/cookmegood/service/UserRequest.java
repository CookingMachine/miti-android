package com.shveed.cookmegood.service;

import com.shveed.cookmegood.entity.User;

public class UserRequest {
    public static User getUserById(Long id){
        User user = new User();
        return user;
    }
}

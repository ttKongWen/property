package com.ilovecl.demo.service;

import com.ilovecl.demo.dto.LoginResult;
import org.springframework.stereotype.Repository;

/**
 * 登录的Service层
 *
 */

@Repository
public interface LoginService {

    //    学生登录的接口
    LoginResult studentLogin(String id, String password);

    //    管理员登录的接口
    LoginResult adminLogin(String email, String password);
}

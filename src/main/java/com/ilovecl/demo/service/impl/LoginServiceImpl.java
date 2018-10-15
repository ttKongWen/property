package com.ilovecl.demo.service.impl;

import com.ilovecl.demo.dao.AdminDao;
import com.ilovecl.demo.dao.StudentDao;
import com.ilovecl.demo.dto.LoginResult;
import com.ilovecl.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录Service的实现
 *
 */

//使用Spring注解的方式
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private AdminDao adminDao;

    @Override
    public LoginResult studentLogin(String name, String password) {
        // 用户存在，而且密码正确，才允许登录
        if (studentDao.queryByName(name) != null &&
                studentDao.queryByName(name).getPassword().equals(password)) {
            return new LoginResult(true);
        }

        return new LoginResult(false);
    }

    @Override
    public LoginResult adminLogin(String email, String password) {
        // 用户存在，而且密码正确，才允许登录
        if (adminDao.queryByEmail(email) != null &&
                adminDao.queryByEmail(email).getPassword().equals(password)) {
            return new LoginResult(true);
        }

        return new LoginResult(false);
    }
}

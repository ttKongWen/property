package com.ilovecl.demo.entity;

import org.springframework.context.annotation.Bean;

/**
 * 管理员
 *
 */

public class Admin {
    private int id; // 编号
    private String userName; // 用户名
    private String password; // 密码
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


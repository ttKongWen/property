package com.ilovecl.demo.dto;

/**
 * 管理学生的返回结果
 *
 */
public class StudentResult {
    private String id; // 学生编号
    private String name; // 姓名
    private String password; // 密码
    private int sexual; // 性别
    private String sexualInfo; // 性别的字符串
    private String email; // 邮箱
    private String phone; // 电话

    public StudentResult(String id, String name, String password, int sexual, String sexualInfo, String email, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.sexual = sexual;
        this.sexualInfo = sexualInfo;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSexual() {
        return sexual;
    }

    public void setSexual(int sexual) {
        this.sexual = sexual;
    }

    public String getSexualInfo() {
        return sexualInfo;
    }

    public void setSexualInfo(String sexualInfo) {
        this.sexualInfo = sexualInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

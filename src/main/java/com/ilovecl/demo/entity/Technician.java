package com.ilovecl.demo.entity;

/**
 * 维修人员
 *
 */
public class Technician {
    private int id; // 编号
    private String name; // 姓名
    private String number;
    private String phone;

    public Technician(String name, String number, String phone) {
        this.name = name;
        this.number = number;
        this.phone = phone;
    }

    public Technician(Integer id, String name, String number, String phone) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.phone = phone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

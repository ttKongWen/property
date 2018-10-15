package com.ilovecl.demo.service;

import com.ilovecl.demo.dto.RegisterResult;
import org.springframework.stereotype.Repository;

/**
 * 注册的Service层
 *
 */

@Repository
public interface RegisterService {

    //    新用户注册
    RegisterResult register(String name, String password, int sexual, String email, String phone);

}

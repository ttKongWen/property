package com.ilovecl.demo.service.impl;

import com.ilovecl.demo.dao.StudentDao;
import com.ilovecl.demo.dto.RegisterResult;
import com.ilovecl.demo.entity.Student;
import com.ilovecl.demo.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 注册Service的实现
 *
 */

@Service("registerService")
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private StudentDao studentDao;
    @Override
    public RegisterResult register(String name, String password, int sexual, String email, String phone) {

        // 做数据的合法性检查
        if (name == null || password == null
                || name.equals("") || password.equals(""))
        {
            return new RegisterResult(false, "用户名或密码为空");
        }

        // 已经存在该用户名
        if (studentDao.queryByName(name) != null)
        {
            return new RegisterResult(false, "用户名已存在");
        }

        studentDao.add(new Student(name, password, sexual, email, phone));

        // 插入后才算成功注册
        if (studentDao.queryByName(name) != null)
        {
            return new RegisterResult(true);
        }

        return new RegisterResult(false, "注册失败");
    }
}

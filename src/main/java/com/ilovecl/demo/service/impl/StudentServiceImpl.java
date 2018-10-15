package com.ilovecl.demo.service.impl;

import com.ilovecl.demo.dao.StudentDao;
import com.ilovecl.demo.entity.Student;
import com.ilovecl.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生Service层的实现
 *
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    /**
     * 根据id查询学生
     *
     * @param id
     * @return
     */
    @Override
    public Student getStudentById(String id) {
        return studentDao.queryById(id);
    }

    /**
     * 根据email查询学生
     *
     * @param email
     * @return
     */
    @Override
    public Student getStudentByEmail(String email) {
        return studentDao.queryByEmail(email);
    }

    /**
     * 修改密码
     *
     * @param studentId 对应的学生的编号ID
     * @param password  新密码
     * @return 是否成功修改密码
     */
    @Override
    public boolean changePassword(String studentId, String password) {
        Student student = studentDao.queryById(studentId);

        // 根本不存在该用户
        if (student == null)
        {
            return false;
        }

        student.setPassword(password);

        studentDao.update(student);

        return true;
    }

    /**
     * 修改除了名字和密码之外的信息
     *
     * @param sexual
     * @param name
     * @param phone
     * @return
     */
    @Override
    public boolean changeOtherInfo(String studentId, int sexual, String name, String phone) {
        Student student = studentDao.queryById(studentId);

        // 根本不存在该用户
        if (student == null)
        {
            return false;
        }

        student.setSexual(sexual);
        student.setName(name);
        student.setPhone(phone);

        studentDao.update(student);

        return true;

    }

    /**
     * 增加一个学生
     *
     * @param name
     * @param password
     * @param sexual
     * @param email
     * @param phone
     * @return
     */
    @Override
    public boolean addStudent(String id, String name, String password, int sexual, String email, String phone) {
        studentDao.add(new Student(id, name, password, sexual, email, phone));

        if (studentDao.queryByName(name) != null)
            return true;

        return false;
    }

    /**
     * 删除一个学生
     *
     * @param studentId
     * @return
     */
    @Override
    public boolean deleteStudent(String studentId) {
        studentDao.delete(new Student(studentId));

        if (studentDao.queryById(studentId) == null)
            return true;

        return false;
    }

    /**
     * 获取所有学生
     *
     * @return
     */
    @Override
    public List<Student> getAll() {
        return studentDao.queryAll();
    }

    /**
     * 修改学生的信息
     *
     * @param id       该学生的信息
     * @param name
     * @param password
     * @param sexual
     * @param email
     * @param phone
     * @return
     */
    @Override
    public boolean updateStudent(String id, String name, String password, int sexual, String email, String phone) {
        Student student = studentDao.queryById(id);

        // 根本不存在该用户
        if (student == null)
        {
            return false;
        }

        studentDao.update(new Student(id, name, password, sexual, email, phone));

        return true;
    }
}

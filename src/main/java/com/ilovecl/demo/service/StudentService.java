package com.ilovecl.demo.service;

import com.ilovecl.demo.entity.Student;
import com.ilovecl.demo.entity.Student;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * 学生管理的Service层
 *
 */

@Repository
public interface StudentService {

    /**
     * 根据id查询学生
     * @param id
     * @return
     */
    Student getStudentById(String id);

    /**
     * 根据email查询学生
     * @param email
     * @return
     */
    Student getStudentByEmail(String email);

    /**
     * 修改密码
     *
     * @param studentId 对应的学生的编号ID
     * @param password  新密码
     * @return 是否成功修改密码
     */
    boolean changePassword(String studentId, String password);

    /**
     * 修改除了邮箱和密码之外的信息
     *
     * @param sexual
     * @param name
     * @param phone
     * @return
     */
    boolean changeOtherInfo(String studentId, int sexual, String name, String phone);

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
    boolean addStudent(String id, String name, String password, int sexual, String email, String phone);

    /**
     * 删除一个学生
     *
     * @param studentId
     * @return
     */
    boolean deleteStudent(String studentId);

    /**
     * 获取所有学生
     *
     * @return
     */
    List<Student> getAll();

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
    boolean updateStudent(String id, String name, String password, int sexual, String email, String phone);

    /**
     * 含有非常丰富的关于报修单的信息
     *
     */
    class RepairDisplayer {
        private int id; // 报修单编号
        private int status; // 报修单状态
        private String statusInfo; // 报修单状态的字符串
        private String detail; // 故障详情
        private String place; // 故障发生的地点
        private String picMD5; // 故障现场照片的MD5值
        private Timestamp submitTime; // 提交报修单的时间
        private String studentId; // 提交报修单的学生的编号
        private String studentName; // 提交报修单的学生的名字
        private String studentEmail; // 提交报修单的学生的email
        private Student student; // 提交报修单的学生

        public RepairDisplayer(int id, int status, String statusInfo, String detail, String place,
                               String picMD5, Timestamp submitTime, String studentId, String studentName,
                               String studentEmail, Student student) {
            this.id = id;
            this.status = status;
            this.statusInfo = statusInfo;
            this.detail = detail;
            this.place = place;
            this.picMD5 = picMD5;
            this.submitTime = submitTime;
            this.studentId = studentId;
            this.studentName = studentName;
            this.studentEmail = studentEmail;
            this.student = student;
        }

        public RepairDisplayer(int id, int status, String s, String detail, String place, String picMD5, Timestamp submitTime) {
            this.id = id;
            this.status = status;
            this.statusInfo = statusInfo;
            this.detail = detail;
            this.place = place;
            this.picMD5 = picMD5;
            this.submitTime = submitTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusInfo() {
            return statusInfo;
        }

        public void setStatusInfo(String statusInfo) {
            this.statusInfo = statusInfo;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getPicMD5() {
            return picMD5;
        }

        public void setPicMD5(String picMD5) {
            this.picMD5 = picMD5;
        }

        public Timestamp getSubmitTime() {
            return submitTime;
        }

        public void setSubmitTime(Timestamp submitTime) {
            this.submitTime = submitTime;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getStudentEmail() {
            return studentEmail;
        }

        public void setStudentEmail(String studentEmail) {
            this.studentEmail = studentEmail;
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }
    }
}

package com.ilovecl.demo.service;

import com.ilovecl.demo.dto.ModifyRepairResult;
import com.ilovecl.demo.entity.Repair;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报修单的Service层
 *
 */

@Repository
public interface RepairService {

    Repair getRepairById(int repairId);

    /*****************************学生端的接口*****************************************/

    /**
     * 提交报修单的接口
     *
     * @param detail    问题详情
     * @param place     故障地点
     * @param picMD5    现场照片MD5
     * @param studentId 提交者ID
     * @return 提交是否成功
     */
    boolean submitRepair(String detail, String place, String picMD5, String studentId);

    /**
     * 查询某个学生提交的报修单的接口
     *
     * @param studentId 该学生编号ID
     * @return 提交的报修单的列表
     */
    List<Repair> getRepqirByStudentId(String studentId);

    /**
     * 修改某条报修单记录的问题详情、地点、现场照片
     *
     * @param id        该报修单的编号ID
     * @param newDetail 新的问题详情
     * @param newPlace  新的地点
     * @param newPicMD5 新的现场照片MD5
     * @return 修改结果
     */
    ModifyRepairResult changeRepair(int id, String newDetail, String newPlace, String newPicMD5);

    /**
     * 删除某条报修单
     *
     * @param id 该报修单的编号ID
     * @return 删除结果
     */
    ModifyRepairResult deleteRepair(int id);

    /**
     * 验收某条保修单
     *
     * @param id 该报修单的编号ID
     * @return 验收结果
     */
    ModifyRepairResult Acceptance(int id);

    /**
     * 获取所有属于该学生的待取消报修单
     *
     * @param studentId 该学生的编号ID
     * @return 待取消报修单
     */
    List<Repair> getAllToBeCanceledById(String studentId);

    /**
     * 同意取消某条报修单
     *
     * @param id 该报修单的编号ID
     */
    void agreeToBeCanceled(int id);

    /**
     * 拒绝取消某条报修单
     *
     * @param id 该报修单的编号ID
     */
    void rejectToBeCanceled(int id);

    /*****************************管理员端的接口*****************************************/

    /**
     * 获取所有未完成的报修单
     *
     * @return 未完成的报修单
     */
    List<Repair> getAllUnFinish();

    /**
     * 获取所有已经完成的报修单
     *
     * @return 已完成的报修单
     */
    List<Repair> getAllFinish();

    /**
     * 由管理员调用，取消某条报修单
     *
     * @param id 该报修单的编号ID
     */
    void cancelRepair(int id);

    /**
     * 安排某位维修人员去检修报修单上的故障物业
     *
     * @param repairId     对应的报修单的编号ID
     * @param technicianId 维修人员的编号ID
     */
    void arrangeRepair(int repairId, int technicianId);

    void unArrangeRepair(int repairId);

    /**
     * 确认某报修单上的故障物业已经被维修人员修好
     *
     * @param rapairId 对应的报修单
     */
    void confirmRepair(int rapairId);


}

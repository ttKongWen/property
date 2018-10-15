package com.ilovecl.demo.service.impl;

import com.ilovecl.demo._const.RepairEnum;
import com.ilovecl.demo.dao.MaintenanceDao;
import com.ilovecl.demo.dao.RepairDao;
import com.ilovecl.demo.dto.ModifyRepairResult;
import com.ilovecl.demo.entity.Maintenance;
import com.ilovecl.demo.entity.Repair;
import com.ilovecl.demo.service.RepairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 报修单Service层的实现
 *
 */
@Service("repairService")
public class RepairServiceImpl implements RepairService {

    @Autowired
    public RepairDao repairDao;
    @Autowired
    public MaintenanceDao maintenanceDao;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Repair getRepairById(int repairId) {
        return repairDao.queryById(repairId);
    }

    /**
     * 提交报修单的接口
     *
     * @param detail    问题详情
     * @param place     故障地点
     * @param picMD5    现场照片MD5
     * @param studentId 提交者ID
     * @return 提交是否成功
     */
    @Override
    public boolean submitRepair(String detail, String place, String picMD5, String studentId) {
        repairDao.add(
                new Repair(RepairEnum.REPAIR_UN_ARRANGED.getState(), detail, place, picMD5,
                        new Timestamp(System.currentTimeMillis()), studentId));
        return true;
    }

    /**
     * 查询某个学生提交的报修单的接口
     *
     * @param studentId 该学生编号ID
     * @return 提交的报修单的列表
     */
    @Override
    public List<Repair> getRepqirByStudentId(String studentId) {

        List<Repair> repairs = repairDao.queryByStudentId(studentId);

        List<Repair> list = new ArrayList<Repair>();

        for (Repair repair : repairs) {
            if (repair.getStatus() != RepairEnum.DELETED_BY_STUDENT.getState())
            {
                list.add(repair);
            }
        }

        return list;
    }

    /**
     * 修改某条报修单记录的问题详情、地点、现场照片
     *
     * @param id        该报修单的编号ID
     * @param newDetail 新的问题详情
     * @param newPlace  新的地点
     * @param newPicMD5 新的现场照片MD5
     * @return 修改结果
     */
    @Override
    public ModifyRepairResult changeRepair(int id, String newDetail, String newPlace, String newPicMD5) {

        Repair repair = repairDao.queryById(id);

        repair.setDetail(newDetail);
        repair.setPlace(newPlace);
        repair.setPicMD5(newPicMD5);

        repairDao.update(repair);

        return new ModifyRepairResult(true);
    }

    /**
     * 删除某条报修单
     *
     * @param id 该报修单的编号ID
     * @return 删除结果
     */
    @Override
    public ModifyRepairResult deleteRepair(int id) {
        Repair repair = repairDao.queryById(id);

        if (repair.getStatus() == RepairEnum.DELETED_BY_STUDENT.getState()) {
            return new ModifyRepairResult(false, "已删除的报修单不能再次被删除");
        }

        if (repair.getStatus() == RepairEnum.CONFIRM_WAIT.getState()) {
            return new ModifyRepairResult(false, "处于待验收的状态，必须先验收才能删除");
        }

        repair.setStatus(RepairEnum.DELETED_BY_STUDENT.getState());

        repairDao.update(repair);

        return new ModifyRepairResult(true);
    }

    /**
     * 验收某条保修单
     *
     * @param id 该报修单的编号ID
     * @return 验收结果
     */
    @Override
    public ModifyRepairResult Acceptance(int id) {
        Repair repair = repairDao.queryById(id);

        if (repair.getStatus() != RepairEnum.CONFIRM_WAIT.getState()) {
            return new ModifyRepairResult(false, "未处于待验收的状态，不能验收");
        }

        repair.setStatus(RepairEnum.CONFIRM.getState());

        repairDao.update(repair);

        return new ModifyRepairResult(true);
    }

    /**
     * 获取所有属于该学生的待取消报修单
     *
     * @param studentId 该学生的编号ID
     * @return 待取消报修单
     */
    @Override
    public List<Repair> getAllToBeCanceledById(String studentId) {
        List<Repair> repairs = repairDao.queryByStudentId(studentId);

        List<Repair> list = new ArrayList<Repair>();

        for (Repair repair : repairs) {
            if (repair.getStatus() == RepairEnum.CANCELED_AGREE_WAIT.getState())
            {
                list.add(repair);
            }
        }

        return list;
    }

    /**
     * 同意取消某条报修单
     *
     * @param id 该报修单的编号ID
     */
    @Override
    public void agreeToBeCanceled(int id) {
        Repair repair = repairDao.queryById(id);

        if (repair.getStatus() != RepairEnum.CANCELED_AGREE_WAIT.getState()) {
            logger.info("该报修单并未“等待同意取消”，所以不能同意取消");
            return;
        }

        repair.setStatus(RepairEnum.CANCELED_AGREE.getState());

        repairDao.update(repair);

    }

    /**
     * 拒绝取消某条报修单
     *
     * @param id 该报修单的编号ID
     */
    @Override
    public void rejectToBeCanceled(int id) {
        Repair repair = repairDao.queryById(id);

        if (repair.getStatus() != RepairEnum.CANCELED_AGREE_WAIT.getState()) {
            logger.info("该报修单并未“等待同意取消”，所以不能拒绝取消");
            return;
        }

        repair.setStatus(RepairEnum.CANCELED_REJECT.getState());

        repairDao.update(repair);
    }

    /**
     * 获取所有未完成的报修单
     *
     * @return 未完成的报修单
     */
    @Override
    public List<Repair> getAllUnFinish() {
        List<Repair> repairs = repairDao.queryAll();

        List<Repair> list = new ArrayList<Repair>();

        // 1. 没被删除
        // 2. 没验收
        // 3. 没同意取消
        for (Repair repair : repairs) {
            if (repair.getStatus() != RepairEnum.DELETED_BY_STUDENT.getState()
                    && repair.getStatus() != RepairEnum.CONFIRM.getState()
                    && repair.getStatus() != RepairEnum.CANCELED_AGREE.getState())
            {
                list.add(repair);
            }
        }

        return list;
    }

    /**
     * 获取所有已经完成的报修单
     *
     * @return 已完成的报修单
     */
    @Override
    public List<Repair> getAllFinish() {
        List<Repair> repairs = repairDao.queryAll();

        List<Repair> list = new ArrayList<Repair>();

        for (Repair repair : repairs) {
            if (repair.getStatus() == RepairEnum.CONFIRM.getState())
            {
                list.add(repair);
            }
        }

        return list;
    }

    /**
     * 由管理员调用，取消某条报修单
     *
     * @param id 该报修单的编号ID
     */
    @Override
    public void cancelRepair(int id) {
        Repair repair = repairDao.queryById(id);

        if (repair.getStatus() != RepairEnum.DELETED_BY_STUDENT.getState()
                && repair.getStatus() != RepairEnum.CONFIRM.getState()
                && repair.getStatus() != RepairEnum.CANCELED_AGREE.getState()) {

            repair.setStatus(RepairEnum.CANCELED_AGREE_WAIT.getState());

            repairDao.update(repair);
        }
    }

    /**
     * 安排某位维修人员去检修报修单上的故障物业
     *
     * @param repairId     对应的报修单的编号ID
     * @param technicianId 维修人员的编号ID
     */
    @Override
    public void arrangeRepair(int repairId, int technicianId) {
        Repair repair = repairDao.queryById(repairId);

        if (maintenanceDao.queryByRepairId(repairId) != null) {
            logger.info("已安排过，故需先删除之前的安排记录");
            return;
        }

        maintenanceDao.add(new Maintenance(repairId, technicianId, new Timestamp(System.currentTimeMillis())));

        repair.setStatus(RepairEnum.REPAIR_ARRANGED.getState());

        repairDao.update(repair);
    }

    @Override
    public void unArrangeRepair(int repairId) {
        Repair repair = repairDao.queryById(repairId);

        // 未处在“已安排检修”的状态，不能取消安排检修
        if (repair.getStatus() != RepairEnum.REPAIR_ARRANGED.getState()) {
            return;
        }

        repair.setStatus(RepairEnum.REPAIR_UN_ARRANGED.getState());

        repairDao.update(repair);

    }

    /**
     * 确认某报修单上的故障物业已经被维修人员修好
     *
     * @param rapairId 对应的报修单
     */
    @Override
    public void confirmRepair(int rapairId) {
        Repair repair = repairDao.queryById(rapairId);

        if (repair.getStatus() != RepairEnum.DELETED_BY_STUDENT.getState()
                && repair.getStatus() != RepairEnum.CONFIRM.getState()
                && repair.getStatus() != RepairEnum.CONFIRM.getState()) {
            repair.setStatus(RepairEnum.CONFIRM_WAIT.getState());

            repairDao.update(repair);
        }
    }
}

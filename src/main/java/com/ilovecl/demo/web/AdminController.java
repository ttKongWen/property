package com.ilovecl.demo.web;

import com.ilovecl.demo._const.RepairEnum;
import com.ilovecl.demo._const.RepairEnumCN;
import com.ilovecl.demo._const.UrgentRepairEnum;
import com.ilovecl.demo.dto.*;
import com.ilovecl.demo.entity.*;
import com.ilovecl.demo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统管理员端的控制器
 *
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    private RepairService repairService;

    @Resource
    private StudentService studentService;

    @Resource
    private AdminService adminService;

    @Resource
    private UrgentRepairService urgentRepairService;

    @Resource
    private MaintenanceService maintenanceService;

    @Resource
    private TechnicianService technicianService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "redirect:../student/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public LoginResult login(int id, String password, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        logger.info("================admin login===================");
        logger.info("admin id: "+id);
        logger.info("admin password: "+password);
        logger.info("================admin login===================");

        Admin admin = adminService.getAdminById(id);

        LoginResult loginResult = null;

        if(admin==null){
            logger.info("admin null");
            loginResult = new LoginResult(false);
        }else{
            String psw = admin.getPassword();
            logger.info("admin password:"+psw);
            //密码正确
            if(psw.equals(password)){
                loginResult = new LoginResult(true);

                httpServletRequest.getSession().setAttribute("ADMIN_ID", id);
                httpServletResponse.addCookie(new Cookie("ADMIN_ID", String.valueOf(id)));
            }else{
                loginResult = new LoginResult(false);
            }
        }

        if(!loginResult.isSuccess()){
            loginResult.setReason("invalid user");
        }

        logger.info("LoginResult:"+loginResult);
        return loginResult;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession httpSession) {
        logger.info("退出要移除的sessionId是:"+httpSession.getId());
        httpSession.removeAttribute("ADMIN_ID");
        return "redirect:../student/login";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model, HttpServletRequest httpServletRequest) {
        List<Repair> repairs = repairService.getAllUnFinish();

        Student student;
        List<RepairInfoVo> repairInfoVos = new ArrayList<>();
        for(Repair repair:repairs){
            student = studentService.getStudentById(repair.getStudentId());
            RepairInfoVo repairInfoVo = new RepairInfoVo(student, repair);
            repairInfoVo.setStatesInfo(RepairEnumCN.stateOf(repair.getStatus()).getStateInfo());
            repairInfoVos.add(repairInfoVo);
        }

        model.addAttribute("repairInfoVos", repairInfoVos);

        return "/admin/dashboard";
    }

    @RequestMapping(value = "/repair/{repairId}/detail", method = RequestMethod.GET)
    public String detailRepair(@PathVariable("repairId") int repairId, Model model) {
        Repair repair;

        repair = repairService.getRepairById(repairId);
        repair.setPicMD5("/" + repair.getPicMD5());

        RepairInfoVo repairInfoVo = new RepairInfoVo(repair);
        repairInfoVo.setStatesInfo(RepairEnumCN.stateOf(repair.getStatus()).getStateInfo());

        model.addAttribute("repairInfoVo", repairInfoVo);

        return "admin/detail";
    }

    @RequestMapping(value = "/repair/{repairId}/cancel", method = RequestMethod.GET)
    public String cancelRepair(@PathVariable("repairId") int repairId) {
        repairService.cancelRepair(repairId);
        return "redirect:/admin/dashboard";
    }

    @RequestMapping(value = "/repair/{repairId}/confirm", method = RequestMethod.GET)
    public String confirmRepair(@PathVariable("repairId") int repairId) {
        repairService.confirmRepair(repairId);

        return "redirect:/admin/dashboard";
    }

    @RequestMapping(value = "/repair/{repairId}/arrange", method = RequestMethod.GET)
    public String arrangeRepair(@PathVariable("repairId") int repairId, Model model) {
        Repair repair = repairService.getRepairById(repairId);

        if (repair.getStatus() == RepairEnum.REPAIR_ARRANGED.getState())
        {
            return "redirect:/admin/repair/" + String.valueOf(repairId) + "/detail";
        }

        List<Technician> techniciens = technicianService.getAllTechnician();

        model.addAttribute("repair", repair);
        model.addAttribute("techniciens", techniciens);

        return "/admin/addArrange";
    }

    @RequestMapping(value = "/maintenance/{repairId}/add", method = RequestMethod.POST)
    public String submitArrange(@PathVariable("repairId") int repairId, @RequestParam("technicianId") String technicianId) {

        // 解决表单提交时乱码的问题(JSP在表单提交时默认采用ISO-8859-1编码)
        try {
            technicianId = new String(technicianId.getBytes("ISO-8859-1"), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        repairService.arrangeRepair(repairId, Integer.valueOf(technicianId));

        return "redirect:/admin/dashboard";
    }

    @RequestMapping(value = "/finish", method = RequestMethod.GET)
    public String finish(Model model) {
        List<Repair> repairs = repairService.getAllFinish();

        Student student;
        List<RepairInfoVo> repairInfoVos = new ArrayList<>();
        for(Repair repair: repairs){
            student = studentService.getStudentById(repair.getStudentId());
            RepairInfoVo repairInfoVo = new RepairInfoVo(student, repair);
            repairInfoVo.setStatesInfo(RepairEnumCN.stateOf(repair.getStatus()).getStateInfo());
            repairInfoVos.add(repairInfoVo);
        }
        model.addAttribute("repairInfoVos", repairInfoVos);

        return "/admin/finish";
    }

    @RequestMapping(value = "/urgent", method = RequestMethod.GET)
    public String urgent(Model model) {

        List<UrgentRepair> urgentRepairs = urgentRepairService.getAllUrgentRepair();

        List<UrgentRepairResult> urgentRepairResults = new ArrayList<UrgentRepairResult>();

        Student student;
        Repair repair;

        for (UrgentRepair urgentRepair : urgentRepairs) {
            repair = repairService.getRepairById(urgentRepair.getRepairId());
            student = studentService.getStudentById(urgentRepair.getStudentId());

            urgentRepairResults.add(
                    new UrgentRepairResult
                            (urgentRepair.getId(),
                                    urgentRepair.getStatus(), UrgentRepairEnum.stateOf(urgentRepair.getStatus()).getStateInfo(),
                                    urgentRepair.getRepairId(), repair.getDetail(),
                                    urgentRepair.getStudentId(), student.getName(),
                                    new Timestamp(System.currentTimeMillis())));
        }

        model.addAttribute("list", urgentRepairResults);

        return "/admin/urgent";
    }

    @RequestMapping(value = "/urgent/{repairId}/delete", method = RequestMethod.GET)
    public String deleteUrgent(@PathVariable("repairId") int repairId) {
        urgentRepairService.checkUrgentRepair(repairId);
        return "redirect:/admin/urgent";
    }

    @RequestMapping(value = "/arrange", method = RequestMethod.GET)
    public String arrange(Model model) {
        List<Maintenance> maintenances = maintenanceService.getAll();

        List<MaintenanceResult> maintenanceResults = new ArrayList<MaintenanceResult>();

        Repair repair;
        Technician technician;

        for (Maintenance maintenance : maintenances) {

            repair = repairService.getRepairById(maintenance.getRepairId());

            // 以下状态的报修单对应的维修记录就没必要显示了
            // 1. 被学生删除
            // 2. 学生同意取消
            // 3. 已经验收
            if (repair.getStatus() == RepairEnum.DELETED_BY_STUDENT.getState()
                    || repair.getStatus() == RepairEnum.CANCELED_AGREE.getState()
                    || repair.getStatus() == RepairEnum.CONFIRM.getState())
            {
                continue;
            }

            technician = technicianService.getById(maintenance.getTechnicianId());
            maintenanceResults.add(
                    new MaintenanceResult(
                            maintenance.getId(),
                            maintenance.getRepairId(), repair.getDetail(),
                            maintenance.getTechnicianId(), technician.getName(),
                            new Timestamp(System.currentTimeMillis())
                    )
            );
        }

        model.addAttribute("list", maintenanceResults);

        return "/admin/arrange";
    }

    @RequestMapping(value = "/arrange/{maintenanceId}/cancel", method = RequestMethod.GET)
    public String cancelArrange(@PathVariable("maintenanceId") int maintenanceId) {
        Maintenance maintenance = maintenanceService.getById(maintenanceId);

        // 删除检修安排记录
        maintenanceService.cancelMaintenance(maintenanceId);

        // 同时也修改相应的维修单的状态
        repairService.unArrangeRepair(maintenance.getRepairId());

        return "redirect:/admin/arrange";
    }

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String student(Model model) {
        List<Student> students = studentService.getAll();

        List<StudentResult> studentResults = new ArrayList<StudentResult>();

        for (Student student : students) {
            studentResults.add(new StudentResult(
                    student.getId(), student.getName(), student.getPassword(),
                    student.getSexual(), student.getSexual() == 0 ? "男" : "女",
                    student.getEmail(), student.getPhone())
            );
        }
        model.addAttribute("students", studentResults);

        return "admin/student";
    }

    @RequestMapping(value = "/addstudent", method = RequestMethod.GET)
    public String addStudent(Model model) {
        return "/admin/addStudent";
    }

    @RequestMapping(value = "/addstudent", method = RequestMethod.POST)
    public String addStudent(@RequestParam("name") String name, @RequestParam("email") String email,
                             @RequestParam("password") String password, @RequestParam("sexual") String sexual,
                             @RequestParam("phone") String phone, Model model, HttpServletRequest httpServletRequest) {

        int sex = sexual.equals("男") ? 0 : 1;

        studentService.addStudent(email, name, password, sex, email, phone);

        return "redirect:/admin/student";
    }

    @RequestMapping(value = "/technician", method = RequestMethod.GET)
    public String technician(Model model) {

        List<Technician> techniciens = technicianService.getAllTechnician();

        model.addAttribute("techniciens", techniciens);

        return "admin/technician";
    }

    /**
     * 添加维修人员 GET方法
     * @param model
     * @return
     */
    @RequestMapping(value = "/addtechnician", method = RequestMethod.GET)
    public String addTechnician(Model model) {
        return "/admin/addTechnician";
    }

    /**
     * 添加维修人员 POST方法
     * @param model
     * @return
     */
    @RequestMapping(value = "/addtechnician", method = RequestMethod.POST)
    public String addTechnician(@RequestParam("name") String name,@RequestParam("number") String number,@RequestParam("phone") String phone, Model model, HttpServletRequest httpServletRequest) {

        technicianService.addTechnician(name,number,phone);

        return "redirect:/admin/technician";
    }
}

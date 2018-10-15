package com.ilovecl.demo.web;

import com.ilovecl.demo._const.RepairEnumCN;
import com.ilovecl.demo._const.StudentConst;
import com.ilovecl.demo._const.UrgentRepairEnum;
import com.ilovecl.demo.dto.LoginResult;
import com.ilovecl.demo.dto.ModifyRepairResult;
import com.ilovecl.demo.dto.StudentResult;
import com.ilovecl.demo.dto.StudentUrgentResult;
import com.ilovecl.demo.entity.Repair;
import com.ilovecl.demo.entity.RepairInfoVo;
import com.ilovecl.demo.entity.Student;
import com.ilovecl.demo.entity.UrgentRepair;
import com.ilovecl.demo.service.RepairService;
import com.ilovecl.demo.service.StudentService;
import com.ilovecl.demo.service.UrgentRepairService;
import com.ilovecl.demo.util.MD5;
import com.ilovecl.demo.util.Spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户的web层
 *
 */

@Controller
@CrossOrigin(origins = {"http://localhost:8080", "null"})
@RequestMapping("/student")
public class StudentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private StudentService studentService;

    @Resource
    private RepairService repairService;

    @Resource
    private UrgentRepairService urgentRepairService;

    @RequestMapping(value = "/introduce", method = RequestMethod.GET)
    public String index() {
        return "student/introduce";
    }

    /**
     * 登录的GET方法
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "student/login";
    }

    /**
     * 登录的POST方法
     *
     * @param id
     * @param password
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    LoginResult login(String id, String password, HttpSession httpSession, HttpServletResponse httpServletResponse) {


        LoginResult loginResult=null;
        String stuName = null;

        Student student = studentService.getStudentById(id);

        Spider spider = new Spider();
        try {
            //调用service将数据存入数据库中
            if(student==null){
                stuName = spider.getStudentName(id, password);
                student = new Student(id, stuName, password);
                System.out.println(stuName);
                studentService.addStudent(id, stuName, password, 0, "", "");

                loginResult = new LoginResult(true);
            }else{
                stuName = student.getName();

                // 密码正确
                if (student.getPassword().equals(password)) {
                    loginResult = new LoginResult(true);
                } else {
                    loginResult = new LoginResult(false);
                    loginResult.setReason("invalid user");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(loginResult.isSuccess()){
            // 登录成功后，为该学生生成session
            httpSession.setAttribute(StudentConst.STUDENT_ID, id);

            httpServletResponse.addCookie(new Cookie(StudentConst.STUDENT_ID, id));
            System.out.println("StudentName:"+StudentConst.STUDENT_NAME);
            httpServletResponse.addCookie(new Cookie(StudentConst.STUDENT_NAME, stuName));
        }

        logger.info("***************************************************************************");
        logger.info("登录: " + String.valueOf(loginResult.isSuccess()) + " id : " + id + " password : " + password);
        logger.info("***************************************************************************");

        return loginResult;
    }

    /**
     * 退出的控制
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(StudentConst.STUDENT_EMAIL);
        return "redirect:/student/login";
    }

    /**
     * 提交报修单的POST方法
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/commit", method = RequestMethod.GET)
    public String commit(Model model) {
        return "/student/commit";
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public String commit(@RequestParam("detail") String detail, @RequestParam("place") String place,
                         @RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {

        String id = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_ID).toString();

        Student student = studentService.getStudentById(id);

        String picMD5 = "";

        logger.info(detail);
        logger.info(place);
        logger.info(picMD5);

        try {
            logger.info(file.getInputStream().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file != null) {
            try {
                picMD5 = MD5.getMD5(id + String.valueOf(System.currentTimeMillis()) + file.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 往数据库中插入维修单记录
            repairService.submitRepair(detail, place, picMD5, student.getId());

            // 保存现场照片
            String path = httpServletRequest.getSession().getServletContext().getRealPath("/");
            System.out.println("图片路径：" + path);
            String fileName = picMD5;
            File targetFile = new File(path, fileName);
            try {
                InputStream inputStream = file.getInputStream();
                OutputStream outputStream = new FileOutputStream(targetFile);
                byte[] buffer = new byte[2048];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 往数据库中插入维修单记录
            repairService.submitRepair(detail, place, picMD5, student.getId());
        }

        return "redirect:/student/dashboard";
    }

    /**
     * 获取显示的主页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String board(Model model, HttpServletRequest httpServletRequest) {
        String id = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_ID).toString();

        List<Repair> repairs = repairService.getRepqirByStudentId(id);

        List<RepairInfoVo> repairInfoVos = new ArrayList<>();
        for(Repair repair: repairs){
            repair.setPicMD5("/"+repair.getPicMD5());
            RepairInfoVo repairInfoVo = new RepairInfoVo(repair);
            repairInfoVo.setStatesInfo(RepairEnumCN.stateOf(repair.getStatus()).getStateInfo());
            repairInfoVos.add(repairInfoVo);
        }
        model.addAttribute("repairInfoVos", repairInfoVos);

        return "/student/dashboard";
    }

    /**
     * 报修单详情
     *
     * @param repairId
     * @param model
     * @return
     */
    @RequestMapping(value = "/repair/{repairId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("repairId") int repairId, Model model) {
        Repair repair = repairService.getRepairById(repairId);
        repair.setPicMD5("/" + repair.getPicMD5());

        RepairInfoVo repairInfoVo = new RepairInfoVo(repair);
        repairInfoVo.setStatesInfo(RepairEnumCN.stateOf(repair.getStatus()).getStateInfo());
        model.addAttribute("repairInfoVo", repairInfoVo);

        return "student/detail";
    }

    /**
     * 删除报修单
     *
     * @param repairId
     * @return
     */
    @RequestMapping(value = "/repair/{repairId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("repairId") int repairId) {
        repairService.deleteRepair(repairId);

        return "redirect:/student/dashboard";
    }

    /**
     * 修改报修单
     *
     * @param repairId
     * @param model
     * @return
     */
    @RequestMapping(value = "/repair/{repairId}/update", method = RequestMethod.GET)
    public String update(@PathVariable("repairId") int repairId, Model model) {
        Repair repair = repairService.getRepairById(repairId);
        model.addAttribute("repair", repair);
        return "/student/update";
    }

    /**
     * 修改报修单
     *
     * @return
     */
    @RequestMapping(value = "/repair/{repairId}/update", method = RequestMethod.POST)
    public String update(@PathVariable("repairId") int repairId, @RequestParam("detail") String detail, @RequestParam("place") String place,
                         @RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {

        String sno = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_ID).toString();

        int id = repairId;

        String picMD5 = "";

        logger.info(detail);
        logger.info(place);
        logger.info(picMD5);

        if (file != null) {
            try {
                picMD5 = MD5.getMD5(sno + String.valueOf(System.currentTimeMillis()) + file.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("*************************************************");
            }

            // 保存现场照片
            String path = httpServletRequest.getSession().getServletContext().getRealPath("/");
            System.out.println("图片路径：" + path);
            String fileName = picMD5;
            File targetFile = new File(path, fileName);
            try {
                OutputStream outputStream = new FileOutputStream(targetFile);
                InputStream inputStream = file.getInputStream();
                byte[] buffer = new byte[2048];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 调用修改报修单接口
        repairService.changeRepair(id, detail, place, picMD5);

        return "redirect:/student/repair/" + String.valueOf(id) + "/detail";
    }

    /**
     * 验收报修单
     *
     * @param repairId
     * @return
     */
    @RequestMapping(value = "/repair/{repairId}/acceptance", method = RequestMethod.GET)
    public String acceptance(@PathVariable("repairId") int repairId) {
        repairService.Acceptance(repairId);
        return "redirect:/student/dashboard";
    }

    /**
     * 将报修单标记为催单
     *
     * @param repairId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/repair/{repairId}/urgent", method = RequestMethod.GET)
    public String urgent(@PathVariable("repairId") int repairId, HttpServletRequest httpServletRequest) {
        String id = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_ID).toString();

        Student student = studentService.getStudentById(id);

        urgentRepairService.submitUrgentRepair(repairId, student.getId());

        return "redirect:/student/urgent";
    }

    /**
     * 查看所有的催单
     *
     * @param httpServletRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "/urgent", method = RequestMethod.GET)
    public String showUrgent(HttpServletRequest httpServletRequest, Model model) {
        String id = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_ID).toString();

        Student student = studentService.getStudentById(id);

        List<UrgentRepair> urgentRepairs = urgentRepairService.getAllUrgentRepairByStudentId(student.getId());

        List<StudentUrgentResult> studentUrgentResults = new ArrayList<StudentUrgentResult>();

        String detail = "";
        for (UrgentRepair urgentRepair : urgentRepairs) {
            detail = repairService.getRepairById(urgentRepair.getRepairId()).getDetail();
            studentUrgentResults.add(new StudentUrgentResult(
                    urgentRepair.getId(), urgentRepair.getStatus(), UrgentRepairEnum.stateOf(urgentRepair.getStatus()).getStateInfo(),
                    urgentRepair.getRepairId(), detail, urgentRepair.getStudentId(), urgentRepair.getCreateTime()));
        }
        model.addAttribute("studentUrgentResults", studentUrgentResults);

        return "student/urgent";
    }

    /**
     * 删除某条催单
     *
     * @param repairId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/urgent/{repairId}/delete", method = RequestMethod.GET)
    public String deleteUrgent(@PathVariable("repairId") int repairId, HttpServletRequest httpServletRequest) {
        String id = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_ID).toString();

        Student student = studentService.getStudentById(id);

        urgentRepairService.deleteUrgentRepair(repairId);

        return "redirect:/student/urgent";
    }

    /**
     * 重新提交某条催单
     *
     * @param repairId
     * @return
     */
    @RequestMapping(value = "/urgent/{repairId}/resubmit", method = RequestMethod.GET)
    public String reSubmitUrgent(@PathVariable("repairId") int repairId) {

        urgentRepairService.reSubmit(repairId);

        return "redirect:/student/urgent";
    }

    /**
     * 获取所有待取消的报修单
     *
     * @param httpServletRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "/tobecanceled", method = RequestMethod.GET)
    public String toBeCanceled(HttpServletRequest httpServletRequest, Model model) {
        String id = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_ID).toString();

        List<Repair> repairs = repairService.getAllToBeCanceledById(id);

        List<RepairInfoVo> repairInfoVos = new ArrayList<>();
        for(Repair repair:repairs){
            repair.setPicMD5("/"+repair.getPicMD5());
            RepairInfoVo repairInfoVo = new RepairInfoVo(repair);
            repairInfoVo.setStatesInfo(RepairEnumCN.stateOf(repair.getStatus()).getStateInfo());
            repairInfoVos.add(repairInfoVo);
        }
        model.addAttribute("repairInfoVos", repairInfoVos);

        return "student/tobecanceled";

    }

    /**
     * 同意取消报修单
     *
     * @param repairId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/tobecanceled/{repairId}/agree", method = RequestMethod.GET)
    public String agreeCanceled(@PathVariable("repairId") int repairId, HttpServletRequest httpServletRequest) {
        String id = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_ID).toString();

        Student student = studentService.getStudentByEmail(id);

        repairService.agreeToBeCanceled(repairId);

        return "redirect:/student/tobecanceled";
    }

    /**
     * 拒绝取消报修单
     *
     * @param repairId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/tobecanceled/{repairId}/reject", method = RequestMethod.GET)
    public String rejectCanceled(@PathVariable("repairId") int repairId, HttpServletRequest httpServletRequest) {
        String id = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_ID).toString();

        repairService.rejectToBeCanceled(repairId);

        return "redirect:/student/tobecanceled";
    }

    /**
     * 获取个人信息的控制器
     *
     * @param httpServletRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String infomation(HttpServletRequest httpServletRequest, Model model) {
        String id = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_ID).toString();

        Student student = studentService.getStudentById(id);

        StudentResult studentResult = new StudentResult(
                student.getId(), student.getName(), student.getPassword(),
                student.getSexual(), student.getSexual() == 0 ? "男" : "女",
                student.getEmail(), student.getPhone());
        model.addAttribute("student", studentResult);

        return "student/info";
    }

    /**
     * 修改密码的控制器
     *
     * @param password
     * @param httpSession
     * @param httpServletResponse
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public
    @ResponseBody
    LoginResult changePassword(String password, HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        String id = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_ID).toString();

        Student student = studentService.getStudentByEmail(id);

        studentService.changePassword(student.getId(), password);

        return new LoginResult(true);
    }

    /**
     * 修改其它资料
     *
     * @param name
     * @param phone
     * @param sexual
     * @param httpSession
     * @param httpServletResponse
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/changeinfo", method = RequestMethod.POST)
    public
    @ResponseBody
    LoginResult changeInfo(String name, String phone, String sexual, HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        String id = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_ID).toString();
        System.out.println("name:"+name+";phone:"+phone+";sexual:"+sexual);
        Student student = studentService.getStudentByEmail(id);

        if(sexual.equals("男"))
        {
            sexual="0";
        }else{
            sexual="1";
        }
        studentService.changeOtherInfo(student.getId(), Integer.valueOf(sexual), name, phone);

        httpServletResponse.addCookie(new Cookie(StudentConst.STUDENT_NAME, student.getName()));

        return new LoginResult(true);
    }
}

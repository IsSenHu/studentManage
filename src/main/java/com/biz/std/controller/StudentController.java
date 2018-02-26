package com.biz.std.controller;

import com.aliyun.oss.OSSClient;
import com.biz.std.model.StudentPO;
import com.biz.std.service.GradeService;
import com.biz.std.service.StudentService;
import com.biz.std.vo.Gender;
import com.biz.std.vo.GradeVO;
import com.biz.std.vo.StudentVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 11785
 */
@Controller
public class StudentController {
    private static final String BUCKET_NAMW = "oss-husen-test";
    private static final String END_POINT = "oss-cn-shenzhen.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAIwfCaedv20UgG";
    private static final String SECRET_ACCESS_KEY = "viZURAskaBPvxeN8BC1jrrohxP8znf";
    @Autowired
    private StudentService studentService;
    @Autowired
    private GradeService gradeService;

    /**
     * @param studentVO
     * @return
     */
    @RequestMapping(value = "/saveStudent.action", method = RequestMethod.POST)
    public ModelAndView saveStudent(MultipartFile pic, StudentVO studentVO, Integer sex) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> errors = new HashMap<>();
        System.out.println(studentVO.getGradeVO().getGradeId());
        if(StringUtils.isBlank(studentVO.getStudentName())){
            errors.put("nameError", "姓名不能为空");
        }
        if(studentVO.getBirthday() == null){
            errors.put("birthdayError", "生日不能为空");
        }
        if (errors.size() > 0){
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("views/jsp/addStudent");
            return modelAndView;
        }
        for (Gender gender : Gender.values()){
            if(gender.getValue().equals(sex)){
                studentVO.setGender(gender);
            }
        }
        //生成学号
        Integer gradeId = studentVO.getGradeVO().getGradeId();
        Integer studentIdOffset = gradeService.getOne(studentVO.getGradeVO().getGradeId()).getStudentIdOffset();
        String studentId = null;
        if(gradeId < 10){
            if(studentIdOffset < 10){
                 studentId= LocalDateTime.now().getYear() + "0" + gradeId + "0" + (studentIdOffset + 1);
            }else {
                ++ studentIdOffset;
                studentId = LocalDateTime.now().getYear() + "0" + gradeId + (studentIdOffset + 1);
            }
        }else {
            if(studentIdOffset < 10){
                ++ studentIdOffset;
                studentId = LocalDateTime.now().getYear() + gradeId + "0" + (studentIdOffset + 1);
            }else {
                ++ studentIdOffset;
                studentId = LocalDateTime.now().getYear() + "" + gradeId + (studentIdOffset + 1);
            }
        }
        studentVO.setStudentId(studentId);
        //上传照片
        if(pic != null){
            //原始名称
            String originalFilename = pic.getOriginalFilename();
            //上传图片
            String newFileName = null;
            if(pic != null && StringUtils.isNotBlank(originalFilename)){
                //新的图片名称
                newFileName = UUID.randomUUID() +
                        originalFilename.substring(originalFilename.lastIndexOf("."));
                String filePath = LocalDateTime.now().getMonth().toString() + "/" + newFileName;
                //在OSS上存储图片的地址
                String picPath = "http://" + BUCKET_NAMW + "." + END_POINT + "/" + filePath;
                studentVO.setPicPath(picPath);
                OSSClient ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, SECRET_ACCESS_KEY);
                ossClient.putObject(BUCKET_NAMW, filePath, pic.getInputStream());
                ossClient.shutdown();
            }
        }
        studentService.saveStudent(studentVO);
        modelAndView.addObject("msg", "添加成功");
        modelAndView.setViewName("views/jsp/msg");
        return modelAndView;
    }
    @RequestMapping("/toAddStudent.action")
    public ModelAndView toAddStudent(){
        ModelAndView modelAndView = new ModelAndView();
        List<GradeVO> gradeVOS = gradeService.findAll();
        modelAndView.addObject("grades", gradeVOS);
        modelAndView.setViewName("views/jsp/addStudent");
        return modelAndView;
    }
    @RequestMapping("/pageStudent.action")
    public ModelAndView pageStudent(@RequestParam(required = true, defaultValue = "1") Integer currentPage){
        ModelAndView modelAndView = new ModelAndView();
        Page<StudentPO> page = studentService.pageStudent(currentPage);
        modelAndView.setViewName("views/jsp/listStudent");
        modelAndView.addObject("page", page);
        return modelAndView;
    }
    @RequestMapping("/deleteStudentById.action")
    public String deleteStudentById(Integer id, @RequestParam(required = true, defaultValue = "1") Integer currentPage){
        studentService.deleteStudentById(id);
        return "redirect:/pageStudent.action?currentPage=" + currentPage;
    }
    @RequestMapping("/showStudent.action")
    public ModelAndView showStudent(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/jsp/updateStudent");
        StudentVO studentVO = studentService.findById(id);
        List<GradeVO> gradeVOS = gradeService.findAll();
        modelAndView.addObject("student", studentVO);
        modelAndView.addObject("grades", gradeVOS);
        return modelAndView;
    }
    @RequestMapping("/updateStudent.action")
    public ModelAndView updateStudent(StudentVO studentVO, MultipartFile pic, Integer sex) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> errors = new HashMap<>();
        if(StringUtils.isBlank(studentVO.getStudentName())){
            errors.put("nameError", "姓名不能为空");
        }
        if(studentVO.getBirthday() == null){
            errors.put("birthdayError", "生日不能为空");
        }
        if (errors.size() > 0){
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("views/jsp/updateStudent");
            return modelAndView;
        }
        for (Gender gender : Gender.values()){
            if(gender.getValue().equals(sex)){
                studentVO.setGender(gender);
            }
        }
        //看是否有更新照片
        if(pic != null){
            //原始名称
            String originalFilename = pic.getOriginalFilename();
            //上传图片
            String newFileName = null;
            if(pic != null && StringUtils.isNotBlank(originalFilename)){
                //新的图片名称
                newFileName = UUID.randomUUID() +
                        originalFilename.substring(originalFilename.lastIndexOf("."));
                String filePath = LocalDateTime.now().getMonth().toString() + "/" + newFileName;
                //在OSS上存储图片的地址
                String picPath = "http://" + BUCKET_NAMW + "." + END_POINT + "/" + filePath;
                studentVO.setPicPath(picPath);
                OSSClient ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, SECRET_ACCESS_KEY);
                ossClient.putObject(BUCKET_NAMW, filePath, pic.getInputStream());
                ossClient.shutdown();
            }
        }
        studentService.updateStudent(studentVO);
        modelAndView.setViewName("views/jsp/msg");
        modelAndView.addObject("msg", "保存成功");
        return modelAndView;
    }
}

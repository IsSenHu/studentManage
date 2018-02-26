package com.biz.std.controller;

import com.aliyun.oss.OSSClient;
import com.biz.std.model.ScorePO;
import com.biz.std.model.StudentPO;
import com.biz.std.model.SubjectPO;
import com.biz.std.service.StudentService;
import com.biz.std.service.SubjectService;
import com.biz.std.vo.StudentVO;
import com.biz.std.vo.SubjectVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class SubjectController {
    private static final String BUCKET_NAMW = "oss-husen-test";
    private static final String END_POINT = "oss-cn-shenzhen.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAIwfCaedv20UgG";
    private static final String SECRET_ACCESS_KEY = "viZURAskaBPvxeN8BC1jrrohxP8znf";
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private StudentService studentService;
    @RequestMapping("/addSubject.action")
    public ModelAndView addSubject(SubjectVO subjectVO, MultipartFile pic) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> errors = new HashMap<>();
        if(StringUtils.isBlank(subjectVO.getSubjectName())){
            errors.put("subjectNameError", "学科名不能为空");
        }
        if(errors.size() > 0){
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("views/jsp/addSubject");
            return modelAndView;
        }
        //上传图片
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
                subjectVO.setPicPath(picPath);
                OSSClient ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, SECRET_ACCESS_KEY);
                ossClient.putObject(BUCKET_NAMW, filePath, pic.getInputStream());
                ossClient.shutdown();
            }
        }
        //添加学科
        subjectService.saveSubject(subjectVO);
        modelAndView.addObject("msg", "添加成功");
        modelAndView.setViewName("views/jsp/msg3");
        return modelAndView;
    }
    @RequestMapping("/pageSubject.action")
    public ModelAndView pageSubject(@RequestParam(required = true, defaultValue = "1") Integer currentPage){
        ModelAndView modelAndView = new ModelAndView();
        Page<SubjectPO> page = subjectService.pageSubject(currentPage);
        modelAndView.setViewName("views/jsp/listSubject");
        modelAndView.addObject("page", page);
        return modelAndView;
    }
    @RequestMapping("/deleteSubjectById.action")
    public ModelAndView deleteById(Integer subjectId){
        ModelAndView modelAndView = new ModelAndView();
        subjectService.deleteById(subjectId);
        modelAndView.addObject("msg", "删除成功");
        modelAndView.setViewName("views/jsp/msg3");
        return modelAndView;
    }
    @RequestMapping("/showSubjectById.action")
    public ModelAndView showSubjectById(Integer subjectId){
        ModelAndView modelAndView = new ModelAndView();
        SubjectVO subjectVO = subjectService.findOneById(subjectId);
        modelAndView.addObject("subject", subjectVO);
        modelAndView.setViewName("views/jsp/updateSubject");
        return modelAndView;
    }
    @RequestMapping("/updateSubject.action")
    public ModelAndView updateSubject(SubjectVO subjectVO, MultipartFile pic) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> errors = new HashMap<>();
        if(StringUtils.isBlank(subjectVO.getSubjectName())){
            errors.put("subjectNameError", "学科名不能为空");
        }
        if(errors.size() > 0){
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("views/jsp/updateSubject");
            return modelAndView;
        }
        //上传图片
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
                subjectVO.setPicPath(picPath);
                OSSClient ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, SECRET_ACCESS_KEY);
                ossClient.putObject(BUCKET_NAMW, filePath, pic.getInputStream());
                ossClient.shutdown();
            }
        }
        //修改学科
        subjectService.updateSubject(subjectVO);
        modelAndView.addObject("msg", "修改成功");
        modelAndView.setViewName("views/jsp/msg3");
        return modelAndView;
    }
    @RequestMapping("/toSelectSubject.action")
    public ModelAndView toSelectSubject(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        StudentVO studentVO = studentService.findById(id);
        List<SubjectPO> subjectPOS = subjectService.findAll();
        modelAndView.addObject("student", studentVO);
        modelAndView.addObject("subjects", subjectPOS);
        modelAndView.setViewName("views/jsp/selectSubject");
        return modelAndView;
    }
    @RequestMapping("/selectSubject.action")
    public ModelAndView selectSubject(ScorePO scorePO){
        ModelAndView modelAndView = new ModelAndView();
        boolean reslut = subjectService.SelectSubject(scorePO);
        if(reslut){
            modelAndView.setViewName("views/jsp/msg");
            modelAndView.addObject("msg", "选课成功");
        }else {
            modelAndView.setViewName("views/jsp/selectSubject");
            modelAndView.addObject("msg", "不可重复选课");
            StudentVO studentVO = studentService.findById(scorePO.getStudentPO().getId());
            List<SubjectPO> subjectPOS = subjectService.findAll();
            modelAndView.addObject("student", studentVO);
            modelAndView.addObject("subjects", subjectPOS);
        }
        return modelAndView;
    }
}

package com.biz.std.controller;

import com.biz.std.model.ScorePO;
import com.biz.std.model.StudentPO;
import com.biz.std.service.ScoreService;
import com.biz.std.service.StudentService;
import com.biz.std.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 11785
 */
@Controller
public class ScoreController {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private StudentService studentService;
    @RequestMapping("/toInputScore.action")
    public ModelAndView toInputScore(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        //查询出该学生
        StudentVO studentVO = studentService.findById(id);
        modelAndView.addObject("student", studentVO);
        modelAndView.setViewName("views/jsp/inputScore");
        return modelAndView;
    }
    @RequestMapping("/inputScore.action")
    public ModelAndView inputScore(ScorePO scorePO){
        ModelAndView modelAndView = new ModelAndView();
        scoreService.inputScore(scorePO);
        modelAndView.setViewName("views/jsp/msg");
        modelAndView.addObject("msg", "分数录入成功");
        return modelAndView;
    }
}

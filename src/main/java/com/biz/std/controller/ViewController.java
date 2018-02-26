package com.biz.std.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 11785
 */
@Controller
public class ViewController {
    @RequestMapping("/listStudent.action")
    public ModelAndView listStudent(){
        return new ModelAndView("views/jsp/listStudent");
    }
    @RequestMapping("/listGrade.action")
    public ModelAndView listGrade(){
        return new ModelAndView("views/jsp/listGrade");
    }
    @RequestMapping("/listSubject.action")
    public ModelAndView listSubject(){
        return new ModelAndView("views/jsp/listSubject");
    }
    @RequestMapping("/toAddGrade.action")
    public ModelAndView toAddGrade(){
        return new ModelAndView("views/jsp/addGrade");
    }
    @RequestMapping("/toAddSubject.action")
    public ModelAndView toAddSubject(){
        return new ModelAndView("views/jsp/addSubject");
    }
}

package com.biz.std.controller;

import com.biz.std.model.GradePO;
import com.biz.std.service.GradeService;
import com.biz.std.vo.GradeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 11785
 */
@Controller
public class GradeController {
    @Autowired
    private GradeService gradeService;
    @RequestMapping(value = "/saveGrade.action", method = RequestMethod.POST)
    public ModelAndView saveGrade(GradeVO gradeVO){
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> errors = new HashMap<>();
        if(StringUtils.isBlank(gradeVO.getGradeName())){
            errors.put("msg", "班级名不能为空");
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("views/jsp/addGrade");
            return modelAndView;
        }
        gradeService.saveGrade(gradeVO);
        modelAndView.addObject("msg", "添加成功");
        modelAndView.setViewName("views/jsp/addGrade");
        return modelAndView;
    }
    @RequestMapping("/pageGrade.action")
    public ModelAndView pageGrade(@RequestParam(required = true, defaultValue = "1") Integer currentPage){
        ModelAndView modelAndView = new ModelAndView();
        Page<GradePO> page = gradeService.pageGrade(currentPage);
        modelAndView.addObject("page", page);
        modelAndView.setViewName("views/jsp/listGrade");
        return modelAndView;
    }
    @RequestMapping("/findGradeById.action")
    public ModelAndView findGradeById(Integer gradeId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/jsp/updateGrade");
        modelAndView.addObject("grade", gradeService.getOne(gradeId));
        return modelAndView;
    }
    @RequestMapping("/updateGrade.action")
    public ModelAndView updateGrade(GradeVO gradeVO){
        ModelAndView modelAndView = new ModelAndView();
        gradeService.updateGrade(gradeVO);
        modelAndView.addObject("msg", "修改成功");
        modelAndView.setViewName("views/jsp/msg2");
        return modelAndView;
    }
    @RequestMapping("/deleteGradeById.action")
    public String deleteGradeById(Integer gradeId, @RequestParam(required = true, defaultValue = "1") Integer currentPage){
        gradeService.deleteGradeById(gradeId);
        return "redirect:/pageGrade.action?currentPage=" + currentPage;
    }
}

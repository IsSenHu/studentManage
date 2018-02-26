package com.biz.std.service;

import com.biz.std.model.GradePO;
import com.biz.std.vo.GradeVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 11785
 */
public interface GradeService {
    void saveGrade(GradeVO gradeVO);
    List<GradeVO> findAll();
    GradeVO getOne(Integer id);
    Page<GradePO> pageGrade(Integer currentPage);
    void deleteGradeById(Integer gradeId);

    void updateGrade(GradeVO gradeVO);
}

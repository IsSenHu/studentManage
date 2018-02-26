package com.biz.std.service;

import com.biz.std.model.ScorePO;
import com.biz.std.model.SubjectPO;
import com.biz.std.vo.SubjectVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 11785
 */
public interface SubjectService {
    void saveSubject(SubjectVO subjectVO);

    Page<SubjectPO> pageSubject(Integer currentPage);

    void deleteById(Integer subjectId);

    void updateSubject(SubjectVO subjectVO);

    SubjectVO findOneById(Integer subjectId);

    List<SubjectPO> findAll();

    boolean SelectSubject(ScorePO scorePO);
}

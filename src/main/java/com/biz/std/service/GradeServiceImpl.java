package com.biz.std.service;

import com.biz.std.model.GradePO;
import com.biz.std.model.ScorePO;
import com.biz.std.model.StudentPO;
import com.biz.std.model.SubjectPO;
import com.biz.std.repository.GradeRepository;
import com.biz.std.repository.SubjectRepository;
import com.biz.std.vo.GradeVO;
import javafx.scene.Scene;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author 11785
 */
@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Override
    @Transactional
    public void saveGrade(GradeVO gradeVO) {
        GradePO gradePO = new GradePO();
        BeanUtils.copyProperties(gradeVO, gradePO);
        gradePO.setAvgScore(0.00);
        gradePO.setStudentNumber(0);
        gradePO.setStudentIdOffset(0);
        gradeRepository.save(gradePO);
    }

    @Override
    public List<GradeVO> findAll() {
        List<GradePO> gradePOS = gradeRepository.findAll();
        List<GradeVO> gradeVOS = new ArrayList<>();
        for (GradePO gradePO : gradePOS){
            GradeVO gradeVO = new GradeVO();
            BeanUtils.copyProperties(gradePO, gradeVO);
            gradeVOS.add(gradeVO);
        }
        return gradeVOS;
    }

    @Override
    public GradeVO getOne(Integer id) {
        GradeVO gradeVO = new GradeVO();
        Optional<GradePO> gradePOOptional = gradeRepository.findById(id);
        BeanUtils.copyProperties(gradePOOptional.get(), gradeVO);
        return gradeVO;
    }

    @Override
    public Page<GradePO> pageGrade(Integer currentPage){
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "gradeId"));
        Pageable pageable = new PageRequest(currentPage - 1, 6, sort);
        Page<GradePO> page = gradeRepository.findAll(pageable);
        return page;
    }

    @Override
    @Transactional
    public void deleteGradeById(Integer gradeId) {
        /*
        * 1，删除一个班会删除该班的所有学生
        * 2，删除一个学生会删除学生所有选的选课和分数
        * 所以该班所有的学生所选的学科的平均分和人数都要改变
        * a，先得到该班所有的学生，遍历学生，得到每个学生所选的学科，对相应的学科进行修改
        * */
        GradePO gradePO = gradeRepository.findById(gradeId).get();
        Set<StudentPO> studentPOS = gradePO.getStudents();
        gradeRepository.deleteById(gradeId);
        if(studentPOS.size() > 0){
            for (StudentPO studentPO : studentPOS){
                Set<SubjectPO> subjectPOS = studentPO.getSubjects();
                if(subjectPOS.size() > 0){
                    for (SubjectPO subjectPO : subjectPOS){
                        Integer studentNumberAfter = subjectPO.getStudentNumber() - 1;
                        List<ScorePO> scorePOS = subjectPO.getScorePOS();
                        if(studentNumberAfter > 0){
                            BigDecimal avgScore = new BigDecimal(0.00);
                            BigDecimal totalScore = new BigDecimal(0.00);
                            for (ScorePO scorePO : scorePOS){
                                totalScore = totalScore.add(new BigDecimal(scorePO.getScore()));
                            }
                            avgScore = totalScore.divide(new BigDecimal(studentNumberAfter), 2);
                            subjectPO.setAvgScore(avgScore.doubleValue());
                            subjectRepository.save(subjectPO);
                        }else {
                            subjectPO.setAvgScore(0.00);
                            subjectRepository.save(subjectPO);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateGrade(GradeVO gradeVO) {
        GradePO gradePO = new GradePO();
        BeanUtils.copyProperties(gradeVO, gradePO);
        gradeRepository.save(gradePO);
    }
}

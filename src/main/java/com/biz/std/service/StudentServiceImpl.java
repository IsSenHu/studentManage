package com.biz.std.service;

import com.biz.std.model.GradePO;
import com.biz.std.model.ScorePO;
import com.biz.std.model.StudentPO;
import com.biz.std.model.SubjectPO;
import com.biz.std.repository.GradeRepository;
import com.biz.std.repository.ScoreRepository;
import com.biz.std.repository.StudentRepository;
import com.biz.std.repository.SubjectRepository;
import com.biz.std.vo.GradeVO;
import com.biz.std.vo.StudentVO;
import com.biz.std.vo.SubjectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.enterprise.inject.spi.Bean;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author 11785
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ScoreRepository scoreRepository;
    @Override
    @Transactional
    public void saveStudent(StudentVO studentVO) {
        StudentPO studentPO = new StudentPO();
        studentPO.setGradePO(new GradePO());
        BeanUtils.copyProperties(studentVO, studentPO);
        BeanUtils.copyProperties(studentVO.getGradeVO(), studentPO.getGradePO());
        studentPO.setAvgScore(0.00);
        studentPO.setSubjectNumber(0);
        StudentPO studentPO1 = studentRepository.save(studentPO);
        //添加成功后增加该班级的人数
        Optional<GradePO> gradePO = gradeRepository.findById(studentVO.getGradeVO().getGradeId());
        GradePO gradePO1 = gradePO.get();
        gradePO1.setStudentNumber(gradePO1.getStudentNumber() + 1);
        //学号序列加1
        Integer studentIdOffset = gradePO1.getStudentIdOffset() + 1;
        gradePO1.setStudentIdOffset(studentIdOffset);
        //重新计算平均分
        BigDecimal avgScore = new BigDecimal(0);
        BigDecimal totalScore = new BigDecimal(0);
        for (StudentPO studentPO2 : gradePO1.getStudents()){
            totalScore = totalScore.add(new BigDecimal(studentPO2.getAvgScore()));
        }
        avgScore = totalScore.divide(new BigDecimal(gradePO1.getStudentNumber()), 2);
        gradePO1.setAvgScore(avgScore.doubleValue());
        /*
        * 由于在添加学生的时候没有进行选课，所以并不影响学科的选课人数和平均分
        * */
        gradeRepository.save(gradePO1);
    }

    @Override
    public Page<StudentPO> pageStudent(Integer currentPage) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "studentId"));
        Pageable pageable = new PageRequest(currentPage - 1, 6, sort);
        Page<StudentPO> page = studentRepository.findAll(pageable);
        return page;
    }

    @Override
    @Transactional
    public void deleteStudentById(Integer id) {
        StudentPO studentPO = studentRepository.findStudentPO(id);
        //得到该学生所在的班级
        GradePO gradePO = studentPO.getGradePO();
        //得到该学生所选的学科
        Set<SubjectPO> subjectPOS = studentPO.getSubjects();
        studentRepository.deleteById(id);
        //减少一个人
        Integer studentNumber = gradePO.getStudentNumber() - 1;
        gradePO.setStudentNumber(studentNumber);
        //重新计算班级平均分
        if(gradePO.getStudentNumber() == 0){
            gradePO.setAvgScore(0.00);
        }else {
            BigDecimal avgScore = new BigDecimal(0.00);
            BigDecimal totalScore = new BigDecimal(0.00);
            Set<StudentPO> studentPOS = gradePO.getStudents();
            studentPOS.remove(studentPO);
            for (StudentPO studentPO1 : studentPOS){
                totalScore = totalScore.add(new BigDecimal(studentPO1.getAvgScore()));
            }
            avgScore = totalScore.divide(new BigDecimal(gradePO.getStudentNumber()), 2);
            gradePO.setAvgScore(avgScore.doubleValue());
        }
        //保存
        gradeRepository.save(gradePO);
        //重新计算学科平均分
        if(subjectPOS.size() > 0){
            //如果有选课
            for(SubjectPO subjectPO : subjectPOS){
                //将该学科的选课人数减1
                Integer studentNumber2 = subjectPO.getStudentNumber() - 1;
                subjectPO.setStudentNumber(studentNumber2);
                if(studentNumber2 > 0){
                    BigDecimal avgScore = new BigDecimal(0.00);
                    BigDecimal totalScore = new BigDecimal(0.00);
                    List<ScorePO> scorePOS = subjectPO.getScorePOS();
                    ScorePO scorePO1 = scoreRepository.findAllBySubjectIdAndId(subjectPO.getSubjectId(), studentPO.getId());
                    scorePOS.remove(scorePO1);
                    for (ScorePO scorePO : scorePOS){
                        totalScore = totalScore.add(new BigDecimal(scorePO.getScore()));
                    }
                    avgScore = totalScore.divide(new BigDecimal(subjectPO.getStudentNumber()), 2);
                    subjectPO.setAvgScore(avgScore.doubleValue());
                    subjectRepository.save(subjectPO);
                }else {
                    subjectPO.setAvgScore(0.00);
                    subjectRepository.save(subjectPO);
                }
            }
        }
    }

    @Override
    @Transactional
    public StudentVO findById(Integer id) {
        StudentVO studentVO = new StudentVO();
        StudentPO studentPO = studentRepository.findStudentPO(id);
        BeanUtils.copyProperties(studentPO, studentVO);
        studentVO.setGradeVO(new GradeVO());
        BeanUtils.copyProperties(studentPO.getGradePO(), studentVO.getGradeVO());
        return studentVO;
    }

    @Override
    @Transactional
    public void updateStudent(StudentVO studentVO) {
        StudentPO studentPO = new StudentPO();
        BeanUtils.copyProperties(studentVO, studentPO);
        studentPO.setGradePO(new GradePO());
        BeanUtils.copyProperties(studentVO.getGradeVO(), studentPO.getGradePO());
        //查询出该学生的所有的学科
        Set<SubjectPO> subjectPOS = studentRepository.findStudentPO(studentPO.getId()).getSubjects();
        studentPO.setSubjects(subjectPOS);
        /*
        * 怎样知道该学生的班级是否发生了改变？
        * 答：先不保存该学生，先根据Id获得以前学生所在的班级编号，看是否和现在的一样
        * 1，如果一样，则不做修改
        * 2，如果不一样，则首先要将原先的班级人数减1，再在现在的班级人数加1
        * 3，然后还要重新计算两个班的平均分
        * 4，新的班级的学号序列要加1
        * */
        //获得该先前学生所在的班级
        GradePO gradePOBefore = studentRepository.findStudentPO(studentPO.getId()).getGradePO();
        //获得现在所在的班级
        GradePO gradePOAfter = gradeRepository.findById(studentPO.getGradePO().getGradeId()).get();
        //如果两个不相同
        if(!gradePOBefore.getGradeId().equals(gradePOAfter.getGradeId())){
            //重新计算学号
            Integer gradeId = studentPO.getGradePO().getGradeId();
            Integer studentIdOffset = gradePOAfter.getStudentIdOffset();
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
            //设置学生学号
            studentPO.setStudentId(studentId);
            /*
            * 保存该学生后，学生的班级自然就发生了变化
            * 这个时候就可以对班级进行一些操作，保证班级最新的状态是对的
            * */
            studentRepository.save(studentPO);
            //将以前班级的人数减1
            Integer studentNumberBefore = gradePOBefore.getStudentNumber() - 1;
            gradePOBefore.setStudentNumber(studentNumberBefore);
            //将现在所在的班级人数加1
            Integer studentNumberAfter = gradePOAfter.getStudentNumber() + 1;
            gradePOAfter.setStudentNumber(studentNumberAfter);
            //将现在班级的学号序号加1
            Integer studentIdOffsetAfter = studentIdOffset + 1;
            gradePOAfter.setStudentIdOffset(studentIdOffsetAfter);
            /*
            * 然后重新计算两个班的平均分
            * 1，先计算先前的班级
            * 2，在计算现在的班级
            * */
            Set<StudentPO> studentPOSBefore = gradePOBefore.getStudents();
            if(studentPOSBefore.size() > 0){
                BigDecimal avgScore = new BigDecimal(0.00);
                BigDecimal totalScore = new BigDecimal(0.00);
                for (StudentPO studentPO1 : studentPOSBefore){
                    totalScore = totalScore.add(new BigDecimal(studentPO1.getAvgScore()));
                }
                avgScore = totalScore.divide(new BigDecimal(gradePOBefore.getStudentNumber()), 2);
                gradePOBefore.setAvgScore(avgScore.doubleValue());
                //保存
                gradeRepository.save(gradePOBefore);
            }

            Set<StudentPO> studentPOSAfter = gradePOAfter.getStudents();
            if(studentPOSAfter.size() > 0){
                BigDecimal avgScore1 = new BigDecimal(0.00);
                BigDecimal totalScore1 = new BigDecimal(0.00);
                for (StudentPO studentPO2 : studentPOSAfter){
                    totalScore1 = totalScore1.add(new BigDecimal(studentPO2.getAvgScore()));
                }
                avgScore1 = totalScore1.divide(new BigDecimal(gradePOAfter.getStudentNumber()), 2);
                gradePOAfter.setAvgScore(avgScore1.doubleValue());
                //保存
                gradeRepository.save(gradePOAfter);
            }
        }
    }
}

package com.biz.std.service;

import com.biz.std.model.GradePO;
import com.biz.std.model.ScorePO;
import com.biz.std.model.StudentPO;
import com.biz.std.model.SubjectPO;
import com.biz.std.repository.GradeRepository;
import com.biz.std.repository.ScoreRepository;
import com.biz.std.repository.StudentRepository;
import com.biz.std.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author 11785
 */
@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Override
    @Transactional
    public void inputScore(ScorePO scorePO) {
        /*
        * 怎样进行分数录入
        * 1，分数录入后，要修改学生的平均分
        * 2，学生的平均分改变后要修改班级的平均分
        * 3，要修改学科的平均分
        *
        * a，先保存分数
        * b，获得学生的所有学科分数，然后计算学生的平均分并保存
        * c，获得学生所在的班级，然后计算班级的平均分并保存
        * d，获得选修了该学科的学生，然后开始计算该学科的平均分并保存
        * */
        ScorePO score = scoreRepository.findAllBySubjectIdAndId(scorePO.getSubjectPO().getSubjectId(), scorePO.getStudentPO().getId());
        score.setSubjectPO(subjectRepository.findById(scorePO.getSubjectPO().getSubjectId()).get());
        score.setStudentPO(studentRepository.findStudentPO(scorePO.getStudentPO().getId()));
        score.setScore(scorePO.getScore());
        scoreRepository.save(score);
        StudentPO studentPO = studentRepository.findStudentPO(scorePO.getStudentPO().getId());
        List<ScorePO> scorePOS = studentPO.getScorePOS();
        if(scorePOS.size() > 0){
            BigDecimal avgScore = new BigDecimal(0.00);
            Integer subjectNmuber = studentPO.getSubjectNumber();
            if (subjectNmuber > 0){
                BigDecimal totalScore = new BigDecimal(0.00);
                for(ScorePO scorePO1 : scorePOS){
                    totalScore = totalScore.add(new BigDecimal(scorePO1.getScore()));
                }
                avgScore = totalScore.divide(new BigDecimal(subjectNmuber), 2);
                studentPO.setAvgScore(avgScore.doubleValue());
            }else {
                studentPO.setAvgScore(avgScore.doubleValue());
            }
        }
        studentRepository.save(studentPO);
        GradePO gradePO = studentPO.getGradePO();
        Set<StudentPO> studentPOS = gradePO.getStudents();
        if(studentPOS.size() > 0){
            BigDecimal avgScore = new BigDecimal(0.00);
            Integer studentNumber = gradePO.getStudentNumber();
            if(studentNumber > 0){
                BigDecimal totalScore = new BigDecimal(0.00);
                for (StudentPO studentPO1 : studentPOS){
                    totalScore = totalScore.add(new BigDecimal(studentPO1.getAvgScore()));
                }
                avgScore = totalScore.divide(new BigDecimal(studentNumber), 2);
                gradePO.setAvgScore(avgScore.doubleValue());
            }else {
                gradePO.setAvgScore(avgScore.doubleValue());
            }
            gradeRepository.save(gradePO);
        }

        SubjectPO subjectPO = subjectRepository.findById(score.getSubjectPO().getSubjectId()).get();
        List<ScorePO> scorePOS1 = subjectPO.getScorePOS();
        if(scorePOS1.size() > 0){
            BigDecimal avgScore = new BigDecimal(0.00);
            Integer studentNumber = subjectPO.getStudentNumber();
            if(studentNumber > 0){
                BigDecimal totalScore = new BigDecimal(0.00);
                for (ScorePO scorePO1 : scorePOS1){
                    totalScore = totalScore.add(new BigDecimal(scorePO1.getScore()));
                }
                avgScore = totalScore.divide(new BigDecimal(studentNumber), 2);
                subjectPO.setAvgScore(avgScore.doubleValue());
            }else {
                subjectPO.setAvgScore(avgScore.doubleValue());
            }
            subjectRepository.save(subjectPO);
        }
    }
}

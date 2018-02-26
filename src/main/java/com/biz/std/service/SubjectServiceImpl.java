package com.biz.std.service;

import com.biz.std.model.GradePO;
import com.biz.std.model.ScorePO;
import com.biz.std.model.StudentPO;
import com.biz.std.model.SubjectPO;
import com.biz.std.repository.GradeRepository;
import com.biz.std.repository.ScoreRepository;
import com.biz.std.repository.StudentRepository;
import com.biz.std.repository.SubjectRepository;
import com.biz.std.vo.SubjectVO;
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
import java.util.Set;

/**
 * @author 11785
 */
@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    protected SubjectRepository subjectRepository;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Override
    public void saveSubject(SubjectVO subjectVO) {
        SubjectPO subjectPO = new SubjectPO();
        BeanUtils.copyProperties(subjectVO, subjectPO);
        subjectPO.setAvgScore(0.00);
        subjectPO.setStudentNumber(0);
        subjectRepository.save(subjectPO);
    }

    @Override
    public Page<SubjectPO> pageSubject(Integer currentPage) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "subjectId"));
        Pageable pageable = new PageRequest(currentPage -1, 6, sort);
        Page<SubjectPO> page = subjectRepository.findAll(pageable);
        return page;
    }

    @Override
    @Transactional
    public void deleteById(Integer subjectId) {
        /*
        * 删除之后要做什么事情呢
        * 1,删除之后要重新计算学生的平均分(先查出选修了该科目的学生，重新计算这些学生的平均分)
        * 2,重新计算班级的平均分(计算完学生的平均分后就可以计算班级的平均分)
        * */
        /*
        * 1，先获得选修了该学科的学生
        * 2，再开始删除该学科，删除该学科的时候会把选了该学科的学生记录删除，并且把该学科的分数集合删除
        * 3，删除之后重新计算选修了该学科的平均分，选修该学科的学生已经查出，并且顺便记录这些学生所在的班级（我们认为只要名字和ID相同，就是同一个班级）
        * */
        List<ScorePO> scorePOS = scoreRepository.findAllBySubjectId(subjectId);
        //这个集合用来存学生
        if(scorePOS.size() > 0){
            List<StudentPO> studentPOS = new ArrayList<>();
            for(ScorePO scorePO : scorePOS){
                studentPOS.add(scorePO.getStudentPO());
            }
            //删除该学科
            subjectRepository.deleteById(subjectId);
            //这个集合用来存班级
            List<GradePO> gradePOS = new ArrayList<>();
            //计算该学生的平均分
            for(StudentPO studentPO : studentPOS){
                GradePO gradePO = studentPO.getGradePO();
                if(!gradePOS.contains(gradePO)){
                    gradePOS.add(gradePO);
                }
                BigDecimal avgScore = new BigDecimal(0.00);
                BigDecimal totalScore = new BigDecimal(0.00);
                for(ScorePO scorePO : studentPO.getScorePOS()){
                    totalScore = totalScore.add(new BigDecimal(scorePO.getScore()));
                }
                avgScore = totalScore.divide(new BigDecimal(studentPO.getScorePOS().size()), 2);
                //设置平均分
                studentPO.setAvgScore(avgScore.doubleValue());
                //设置所选修的学科数
                studentPO.setSubjectNumber(studentPO.getScorePOS().size());
                //保存该学生
                studentRepository.save(studentPO);
            }
            //计算该班级的平均分
            for(GradePO gradePO : gradePOS){
                BigDecimal avgSocore = new BigDecimal(0.00);
                BigDecimal totalScore = new BigDecimal(0.00);
                for (StudentPO studentPO : gradePO.getStudents()){
                    totalScore = totalScore.add(new BigDecimal(studentPO.getAvgScore()));
                }
                //计算平均分
                avgSocore = totalScore.divide(new BigDecimal(gradePO.getStudentNumber()), 2);
                gradePO.setAvgScore(avgSocore.doubleValue());
                //保存
                gradeRepository.save(gradePO);
            }
        }else {
            //如果还没有人选该学科,就不存在平均分的和选课数目的问题直接删除即可
            subjectRepository.deleteById(subjectId);
        }
    }

    @Override
    public void updateSubject(SubjectVO subjectVO) {
        SubjectPO subjectPO = new SubjectPO();
        BeanUtils.copyProperties(subjectVO, subjectPO);
        subjectRepository.save(subjectPO);
    }

    @Override
    public SubjectVO findOneById(Integer subjectId) {
        SubjectPO subjectPO = subjectRepository.findById(subjectId).get();
        SubjectVO subjectVO = new SubjectVO();
        BeanUtils.copyProperties(subjectPO, subjectVO);
        return subjectVO;
    }

    @Override
    public List<SubjectPO> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    @Transactional
    public boolean SelectSubject(ScorePO scorePO) {
        /*
        * 怎样进行选课呢？
        * 1，在刚选课的时候学生的成绩是没有的（先用0.00表示）
        * 2，选课完后要重新计算该学科的平均分，(avg * studentNumberBefore / studentNumberAfter)
        * 3，选课完后要重新计算该学生的平均分(avg * subjectNumberBefore / subjectNumberAfter)
        * 4，选课完后要重新计算该学生所在班级的平均分
        * */
        scorePO.setScore(0.00);
        /*
        * 怎样保存选课呢
        * 1，获得选课的studentPO，选修学科数目加1
        * 2，获得选课的subjectPO，选课人数也要加1
        * 3，将
        * */
        StudentPO studentPO = studentRepository.findStudentPO(scorePO.getStudentPO().getId());
        //判断是否进行了重复的选课
        Set<SubjectPO> subjects = studentPO.getSubjects();
        for (SubjectPO subjectPO : subjects){
            if(subjectPO.equals(scorePO.getSubjectPO())){
                return false;
            }
        }
        Integer subjectNumberBefore = studentPO.getSubjectNumber();
        Integer subjectNumberAfter = subjectNumberBefore + 1;
        studentPO.setSubjectNumber(subjectNumberAfter);
        BigDecimal studentAvgScore = new BigDecimal(studentPO.getAvgScore()).multiply(new BigDecimal(subjectNumberBefore)).divide(new BigDecimal(subjectNumberAfter), 2);
        studentPO.setAvgScore(studentAvgScore.doubleValue());

        SubjectPO subjectPO = subjectRepository.findById(scorePO.getSubjectPO().getSubjectId()).get();
        Integer studentNumberBefore = subjectPO.getStudentNumber();
        Integer studentNumberAfter = studentNumberBefore + 1;
        subjectPO.setStudentNumber(studentNumberAfter);
        BigDecimal subjectAvgScore = new BigDecimal(subjectPO.getAvgScore()).multiply(new BigDecimal(subjectNumberBefore)).divide(new BigDecimal(subjectNumberAfter), 2);
        subjectPO.setAvgScore(subjectAvgScore.doubleValue());

        scorePO.setStudentPO(studentPO);
        scorePO.setSubjectPO(subjectPO);
        //将选修的学科保存
        studentPO.getSubjects().add(subjectPO);
        scoreRepository.save(scorePO);
        //重新计算班级平均分
        GradePO gradePO = gradeRepository.findById(studentPO.getGradePO().getGradeId()).get();
        Set<StudentPO> studentPOS = gradePO.getStudents();
        if(studentPOS.size() > 0){
            BigDecimal avgScore = new BigDecimal(0.00);
            BigDecimal totalScore = new BigDecimal(0.00);
            for (StudentPO studentPO1 : studentPOS){
                totalScore = totalScore.add(new BigDecimal(studentPO1.getAvgScore()));
            }
            avgScore = totalScore.divide(new BigDecimal(gradePO.getStudentNumber()), 2);
            gradePO.setAvgScore(avgScore.doubleValue());
            gradeRepository.save(gradePO);
        }
        return true;
    }
}

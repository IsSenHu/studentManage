package com.biz.std.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author 11785
 */
@Entity(name = "t_score")
public class ScorePO implements Serializable{
    //序号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scoreId;
    //所属的学生
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "studentId")
    private StudentPO studentPO;
    //所属科目
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "subjectId")
    private SubjectPO subjectPO;
    //分数
    @Column
    private Double score;

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public StudentPO getStudentPO() {
        return studentPO;
    }

    public void setStudentPO(StudentPO studentPO) {
        this.studentPO = studentPO;
    }

    public SubjectPO getSubjectPO() {
        return subjectPO;
    }

    public void setSubjectPO(SubjectPO subjectPO) {
        this.subjectPO = subjectPO;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ScorePO{" +
                "scoreId=" + scoreId +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        ScorePO scorePO = (ScorePO) o;
        return Objects.equals(scoreId, scorePO.scoreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scoreId);
    }
}

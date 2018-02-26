package com.biz.std.vo;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 11785
 * 班级的
 */
public class GradeVO implements Serializable {
    //主键，班级序号
    private Integer gradeId;
    //班级名
    private String gradeName;
    //人数
    private Integer studentNumber;
    //平均分
    private Double avgScore;
    //该班中的学生
    private Set<StudentVO> students = new HashSet<>();
    //记录该班的学生排到哪里
    private Integer studentIdOffset;
    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    public Set<StudentVO> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentVO> students) {
        this.students = students;
    }

    public Integer getStudentIdOffset() {
        return studentIdOffset;
    }

    public void setStudentIdOffset(Integer studentIdOffset) {
        this.studentIdOffset = studentIdOffset;
    }

    @Override
    public String toString() {
        return "GradeVO{" +
                "gradeId=" + gradeId +
                ", gradeName='" + gradeName + '\'' +
                ", studentNumber=" + studentNumber +
                ", avgScore=" + avgScore +
                ", studentIdOffset=" + studentIdOffset +
                '}';
    }
}

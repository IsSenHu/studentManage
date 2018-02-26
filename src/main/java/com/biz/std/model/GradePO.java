package com.biz.std.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author 11785
 */
@Entity(name = "t_grade")
public class GradePO implements Serializable {
    //主键，班级序号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gradeId;
    //班级名
    @Column(nullable = false, unique = true)
    private String gradeName;
    //人数
    @Column
    private Integer studentNumber;
    //平均分
    @Column
    private Double avgScore;
    //该班中的学生
    @OneToMany(mappedBy = "gradePO", cascade = {CascadeType.REMOVE, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<StudentPO> students = new HashSet<>();
    //记录该班的学生排到哪里
    @Column
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

    public Set<StudentPO> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentPO> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "GradePO{" +
                "gradeId=" + gradeId +
                ", gradeName='" + gradeName + '\'' +
                ", studentNumber=" + studentNumber +
                ", avgScore=" + avgScore +
                ", studentIdOffset=" + studentIdOffset +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GradePO gradePO = (GradePO) o;
        return Objects.equals(gradeId, gradePO.gradeId) &&
                Objects.equals(gradeName, gradePO.gradeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gradeId, gradeName);
    }

    public Integer getStudentIdOffset() {
        return studentIdOffset;
    }

    public void setStudentIdOffset(Integer studentIdOffset) {
        this.studentIdOffset = studentIdOffset;
    }
}

package com.biz.std.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "t_subject")
public class SubjectPO implements Serializable {
    //主键，学科序号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subjectId;
    //学科名
    @Column(nullable = false, unique = true)
    private String subjectName;
    //选修人数
    @Column
    private Integer studentNumber;
    //平均分
    @Column
    private Double avgScore;
    //照片路径
    @Column
    private String picPath;
    //选修该学科的学生
    @ManyToMany(cascade = {CascadeType.REMOVE})
    private Set<StudentPO> studentPOS = new HashSet<>();
    //该学科的分数集合
    @OneToMany(mappedBy = "subjectPO", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<ScorePO> scorePOS = new ArrayList<>();
    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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

    @Override
    public String toString() {
        return "SubjectPO{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", studentNumber=" + studentNumber +
                ", avgScore=" + avgScore +
                ", picPath='" + picPath + '\'' +
                '}';
    }

    public List<ScorePO> getScorePOS() {
        return scorePOS;
    }

    public void setScorePOS(List<ScorePO> scorePOS) {
        this.scorePOS = scorePOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;}
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubjectPO subjectPO = (SubjectPO) o;
        return Objects.equals(subjectId, subjectPO.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId);
    }

    public Set<StudentPO> getStudentPOS() {
        return studentPOS;
    }

    public void setStudentPOS(Set<StudentPO> studentPOS) {
        this.studentPOS = studentPOS;
    }
}

package com.biz.std.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 11785
 * 学生的VO
 */
public class StudentVO implements Serializable {
    //学生主键，序号
    private Integer Id;
    //学生逻辑主键
    private String studentId;
    //学生姓名
    private String studentName;
    //学生性别
    private Gender gender;
    //出生日期
    private Date birthday;
    //所在班级
    private GradeVO gradeVO;
    //选修科目数
    private Integer subjectNumber;
    //学生平均分
    private Double avgScore;
    //学生照片
    private String picPath;
    //学生所选的科目
    private Set<SubjectVO> subjects = new HashSet<>();

    /*getter and setter*/
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public GradeVO getGradeVO() {
        return gradeVO;
    }

    public void setGradeVO(GradeVO gradeVO) {
        this.gradeVO = gradeVO;
    }

    public Integer getSubjectNumber() {
        return subjectNumber;
    }

    public void setSubjectNumber(Integer subjectNumber) {
        this.subjectNumber = subjectNumber;
    }

    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    public Set<SubjectVO> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectVO> subjects) {
        this.subjects = subjects;
    }
    @Override
    public String toString() {
        return "StudentVO{" +
                "Id=" + Id +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", subjectNumber=" + subjectNumber +
                ", avgScore=" + avgScore +
                '}';
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}

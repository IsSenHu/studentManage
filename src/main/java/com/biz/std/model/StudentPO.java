package com.biz.std.model;

import com.biz.std.converter.GenderConverter;
import com.biz.std.vo.Gender;
import com.biz.std.vo.GradeVO;
import com.biz.std.vo.SubjectVO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "t_student")
public class StudentPO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //学生逻辑主键
    @Column(unique = true)
    private String studentId;
    //学生姓名
    @Column
    private String studentName;
    //学生性别
    @Column(name = "gender", columnDefinition = "int not null COMMENT '" + Gender.DOC +"'")
    @Convert(converter = GenderConverter.class)
    private Gender gender;
    //出生日期
    @Temporal(TemporalType.DATE)
    private Date birthday;
    //所在班级
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gradeId")
    private GradePO gradePO;
    //选修科目数
    @Column
    private Integer subjectNumber;
    //学生平均分
    @Column
    private Double avgScore;
    //学生照片
    @Column
    private String picPath;
    //学生所选的科目
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "t_student_subject",
                joinColumns = @JoinColumn(name = "studentId", referencedColumnName = "studentId"),
                inverseJoinColumns = @JoinColumn(name = "subjectId", referencedColumnName = "subjectId"))
    private Set<SubjectPO> subjects = new HashSet<>();

    //学生所选的科目的平均分
    @OneToMany(mappedBy = "studentPO", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<ScorePO> scorePOS = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public GradePO getGradePO() {
        return gradePO;
    }

    public void setGradePO(GradePO gradePO) {
        this.gradePO = gradePO;
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

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Set<SubjectPO> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectPO> subjects) {
        this.subjects = subjects;
    }

    public List<ScorePO> getScorePOS() {
        return scorePOS;
    }

    public void setScorePOS(List<ScorePO> scorePOS) {
        this.scorePOS = scorePOS;
    }

    @Override
    public String toString() {
        return "StudentPO{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", subjectNumber=" + subjectNumber +
                ", avgScore=" + avgScore +
                ", picPath='" + picPath + '\'' +
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
        StudentPO studentPO = (StudentPO) o;
        return Objects.equals(id, studentPO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

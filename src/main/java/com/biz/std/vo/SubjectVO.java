package com.biz.std.vo;

import java.io.Serializable;

/**
 * @author 11785
 * 学科的VO
 */
public class SubjectVO implements Serializable {
    //主键，学科序号
    private Integer subjectId;
    //学科名
    private String subjectName;
    //选修人数
    private Integer studentNumber;
    //平均分
    private Double avgScore;
    //照片路径
    private String picPath;

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
        return "SubjectVO{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", studentNumber=" + studentNumber +
                ", avgScore=" + avgScore +
                ", picPath='" + picPath + '\'' +
                '}';
    }
}

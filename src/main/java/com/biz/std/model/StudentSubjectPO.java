package com.biz.std.model;

import javax.persistence.*;

/**
 * @author 11785
 */
@Entity(name = "t_subject_student")
public class StudentSubjectPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private StudentPO studentPO;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "subjectId", referencedColumnName = "subjectId")
    private SubjectPO subjectPO;
    @Column
    private Double subjectScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getSubjectScore() {
        return subjectScore;
    }

    public void setSubjectScore(Double subjectScore) {
        this.subjectScore = subjectScore;
    }

    @Override
    public String toString() {
        return "StudentSubjectPO{" +
                "id=" + id +
                ", subjectScore=" + subjectScore +
                '}';
    }
}

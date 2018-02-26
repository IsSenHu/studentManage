package com.biz.std.repository;

import com.biz.std.model.StudentPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 11785
 */
@Repository
public interface StudentRepository extends JpaRepository<StudentPO, Integer> {
    @Query("select o from t_student o where id = ?1")
    StudentPO findStudentPO(Integer id);
}

package com.biz.std.service;

import com.biz.std.model.StudentPO;
import com.biz.std.vo.StudentVO;
import org.springframework.data.domain.Page;

/**
 * @author 11785
 */
public interface StudentService {
    void saveStudent(StudentVO studentVO);

    Page<StudentPO> pageStudent(Integer currentPage);

    void deleteStudentById(Integer id);

    StudentVO findById(Integer id);

    void updateStudent(StudentVO studentVO);
}

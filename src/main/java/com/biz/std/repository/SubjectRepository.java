package com.biz.std.repository;

import com.biz.std.model.SubjectPO;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author 11785
 */
@Repository
public interface SubjectRepository extends JpaRepository<SubjectPO, Integer> {
}

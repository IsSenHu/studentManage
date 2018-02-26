package com.biz.std.repository;

import com.biz.std.model.GradePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 11785
 */
@Repository
public interface GradeRepository extends JpaRepository<GradePO, Integer> {
    @Override
    GradePO save(GradePO gradePO);

    @Override
    List<GradePO> findAll();

    @Override
    GradePO getOne(Integer integer);
}

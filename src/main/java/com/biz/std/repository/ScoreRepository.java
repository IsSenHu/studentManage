package com.biz.std.repository;

import com.biz.std.model.ScorePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 11785
 */
@Repository
public interface ScoreRepository extends JpaRepository<ScorePO, Integer> {
    @Query("select o from t_score o where subjectId = ?1")
    List<ScorePO> findAllBySubjectId(Integer subjectId);
    @Query("select o from t_score o where subjectId = ?1 and studentId = ?2")
    ScorePO findAllBySubjectIdAndId(Integer subjectId, Integer studentId);
}

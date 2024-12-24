package com.mockcote.problem.info.repository;

import com.mockcote.problem.info.entity.ProblemInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemInfoRepository extends JpaRepository<ProblemInfo, Integer> {
}

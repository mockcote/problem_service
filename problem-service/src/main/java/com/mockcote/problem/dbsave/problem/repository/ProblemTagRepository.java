package com.mockcote.problem.dbsave.problem.repository;

import com.mockcote.problem.dbsave.problem.entity.ProblemTag;
import com.mockcote.problem.dbsave.problem.entity.ProblemTagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTagRepository extends JpaRepository<ProblemTag, ProblemTagId> {
}

package com.mockcote.problem.dbsave.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockcote.problem.dbsave.problem.entity.ProblemTag;
import com.mockcote.problem.dbsave.problem.entity.ProblemTagId;

@Repository("dbSaveProblemTagRepository")
public interface ProblemTagRepository extends JpaRepository<ProblemTag, ProblemTagId> {
}

package com.mockcote.problem.dbsave.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockcote.problem.dbsave.problem.entity.Problem;

@Repository("dbSaveProblemRepository")
public interface ProblemRepository extends JpaRepository<Problem, Integer> {
}

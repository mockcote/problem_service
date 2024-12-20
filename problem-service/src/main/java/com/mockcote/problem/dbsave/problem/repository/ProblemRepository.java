package com.mockcote.problem.dbsave.problem.repository;

import com.mockcote.problem.dbsave.problem.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Integer> {
}

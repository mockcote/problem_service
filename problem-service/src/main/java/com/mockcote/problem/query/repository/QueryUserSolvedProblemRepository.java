package com.mockcote.problem.query.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mockcote.problem.query.entity.QueryUserSolvedProblem;
import com.mockcote.problem.query.entity.QueryUserSolvedProblemId;

public interface QueryUserSolvedProblemRepository 
       extends JpaRepository<QueryUserSolvedProblem, QueryUserSolvedProblemId> {

}

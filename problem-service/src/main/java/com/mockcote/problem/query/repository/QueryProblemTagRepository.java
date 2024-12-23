package com.mockcote.problem.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mockcote.problem.query.entity.QueryProblemTag;
import com.mockcote.problem.query.entity.QueryProblemTagId;

public interface QueryProblemTagRepository extends JpaRepository<QueryProblemTag, QueryProblemTagId> {
}

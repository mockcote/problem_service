package com.mockcote.problem.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mockcote.problem.query.entity.QueryProblem;

import java.util.List;

public interface QueryProblemRepository extends JpaRepository<QueryProblem, Integer> {

    /**
     * user_solved_problem 테이블과 LEFT JOIN 하여,
     * userId가 이미 푼 문제는 제외하고 조건을 만족하는 문제 1개를 찾는다.
     */
	@Query(value = """
		    SELECT p.*
		      FROM problem p
		      LEFT JOIN user_solved_problem usp
		             ON p.problem_id = usp.problem_id
		            AND usp.user_id = ?1
		     WHERE usp.problem_id IS NULL
		       AND p.difficulty BETWEEN ?2 AND ?3
		       AND p.acceptable_user_count BETWEEN ?4 AND ?5
		       AND (
		           COALESCE(?6, NULL) IS NULL OR EXISTS (
		               SELECT 1
		               FROM problem_tag pt
		               WHERE pt.problem_id = p.problem_id
		                 AND pt.tag_id IN (?6)
		           )
		       )
		       AND (
		           COALESCE(?7, NULL) IS NULL OR NOT EXISTS (
		               SELECT 1
		               FROM problem_tag pt
		               WHERE pt.problem_id = p.problem_id
		                 AND pt.tag_id IN (?7)
		           )
		       )
		     LIMIT ?8
		    """, nativeQuery = true)
		List<QueryProblem> findCandidateProblemsExcludingSolved(
		    String userId,
		    int minDifficulty,
		    int maxDifficulty,
		    int minAcceptableUserCount,
		    int maxAcceptableUserCount,
		    List<Integer> desiredTags,
		    List<Integer> undesiredTags,
		    int limitSize
		);

	
	@Query(value = """
	        SELECT * 
	        FROM problem 
	        WHERE difficulty BETWEEN 1 AND 5
	        ORDER BY RAND() 
	        LIMIT 1
	        """, nativeQuery = true)
	    QueryProblem findRandomProblemWithinDifficulty();



}

package com.mockcote.problem.query.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_solved_problem")
public class QueryUserSolvedProblem {

    @EmbeddedId
    private QueryUserSolvedProblemId id;

    /**
     * problem_id를 외래키로 맵핑 (선택사항)
     * 꼭 필요한 건 아니지만, 양방향 참조나 CASCADE가 필요하면 추가
     */
    @MapsId("problemId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private QueryProblem problem;

    protected QueryUserSolvedProblem() {}

    public QueryUserSolvedProblem(String userId, QueryProblem problem) {
        this.id = new QueryUserSolvedProblemId(userId, problem.getProblemId());
        this.problem = problem;
    }

    public QueryUserSolvedProblemId getId() {
        return id;
    }

    public QueryProblem getProblem() {
        return problem;
    }
}

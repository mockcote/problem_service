package com.mockcote.problem.query.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QueryUserSolvedProblemId implements Serializable {

    private String userId;
    private Integer problemId;

    public QueryUserSolvedProblemId() {}

    public QueryUserSolvedProblemId(String userId, Integer problemId) {
        this.userId = userId;
        this.problemId = problemId;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryUserSolvedProblemId)) return false;
        QueryUserSolvedProblemId that = (QueryUserSolvedProblemId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(problemId, that.problemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, problemId);
    }
}

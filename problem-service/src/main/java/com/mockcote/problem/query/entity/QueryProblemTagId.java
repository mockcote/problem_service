package com.mockcote.problem.query.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class QueryProblemTagId implements Serializable {

    private Integer problemId;
    private Integer tagId;

    public QueryProblemTagId() {}

    public QueryProblemTagId(Integer problemId, Integer tagId) {
        this.problemId = problemId;
        this.tagId = tagId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public Integer getTagId() {
        return tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryProblemTagId)) return false;
        QueryProblemTagId that = (QueryProblemTagId) o;
        return Objects.equals(problemId, that.problemId) &&
               Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(problemId, tagId);
    }
}

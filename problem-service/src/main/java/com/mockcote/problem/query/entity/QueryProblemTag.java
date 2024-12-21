package com.mockcote.problem.query.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "problem_tag")
public class QueryProblemTag {

    @EmbeddedId
    private QueryProblemTagId id;

    @MapsId("problemId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private QueryProblem problem;

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private QueryTagLabel tagLabel;

    protected QueryProblemTag() {}

    public QueryProblemTag(QueryProblemTagId id, QueryProblem problem, QueryTagLabel tagLabel) {
        this.id = id;
        this.problem = problem;
        this.tagLabel = tagLabel;
    }

    public QueryProblemTagId getId() {
        return id;
    }

    public QueryProblem getProblem() {
        return problem;
    }

    public QueryTagLabel getTagLabel() {
        return tagLabel;
    }
}

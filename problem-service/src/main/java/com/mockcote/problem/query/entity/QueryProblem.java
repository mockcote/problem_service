package com.mockcote.problem.query.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "problem")
public class QueryProblem {

    @Id
    @Column(name = "problem_id")
    private Integer problemId;

    @Column(name = "title")
    private String title;

    @Column(name = "difficulty", nullable = false)
    private Integer difficulty;

    @Column(name = "acceptable_user_count", nullable = false)
    private Integer acceptableUserCount;

    // 기본 생성자
    public QueryProblem() {}

    // getter / setter
    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getAcceptableUserCount() {
        return acceptableUserCount;
    }

    public void setAcceptableUserCount(Integer acceptableUserCount) {
        this.acceptableUserCount = acceptableUserCount;
    }
}

package com.mockcote.problem.query.dto;

public class QueryProblemResponseDto {

    private Integer problemId;
    private String title;
    private Integer difficulty;
    private Integer acceptableUserCount;

    public QueryProblemResponseDto(Integer problemId, String title, Integer difficulty, Integer acceptableUserCount) {
        this.problemId = problemId;
        this.title = title;
        this.difficulty = difficulty;
        this.acceptableUserCount = acceptableUserCount;
    }

    // getter
    public Integer getProblemId() {
        return problemId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public Integer getAcceptableUserCount() {
        return acceptableUserCount;
    }
}

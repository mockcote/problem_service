package com.mockcote.problem.info.dto;

import java.util.List;

public class ProblemListResponse {

    private List<ProblemSummary> problems;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    public ProblemListResponse(List<ProblemSummary> problems, int currentPage, int totalPages, long totalElements) {
        this.problems = problems;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public static class ProblemSummary {
        private Integer problemId;
        private String title;
        private Integer difficulty;

        public ProblemSummary(Integer problemId, String title, Integer difficulty) {
            this.problemId = problemId;
            this.title = title;
            this.difficulty = difficulty;
        }

        public Integer getProblemId() {
            return problemId;
        }

        public String getTitle() {
            return title;
        }

        public Integer getDifficulty() {
            return difficulty;
        }
    }

    public List<ProblemSummary> getProblems() {
        return problems;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }
}

package com.mockcote.problem.info.dto;

import java.util.List;

public class ProblemInfoResponse {

    private Integer problemId;
    private String title;
    private Integer difficulty;
    private Integer acceptableUserCount;
    private List<Tag> tags;

    public ProblemInfoResponse(Integer problemId, String title, Integer difficulty, Integer acceptableUserCount, List<Tag> tags) {
        this.problemId = problemId;
        this.title = title;
        this.difficulty = difficulty;
        this.acceptableUserCount = acceptableUserCount;
        this.tags = tags;
    }

    public static class Tag {
        private Integer tagId;
        private String tagName;

        public Tag(Integer tagId, String tagName) {
            this.tagId = tagId;
            this.tagName = tagName;
        }

        public Integer getTagId() {
            return tagId;
        }

        public String getTagName() {
            return tagName;
        }
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

    public Integer getAcceptableUserCount() {
        return acceptableUserCount;
    }

    public List<Tag> getTags() {
        return tags;
    }
}

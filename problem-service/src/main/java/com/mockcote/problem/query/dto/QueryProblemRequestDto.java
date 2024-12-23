package com.mockcote.problem.query.dto;

import java.util.List;

public class QueryProblemRequestDto {

    private String handle;

    private int minDifficulty;
    private int maxDifficulty;

    private int minAcceptableUserCount;
    private int maxAcceptableUserCount;

    private List<Integer> desiredTags;
    private List<Integer> undesiredTags;

    // 기본 생성자 (JSON 역직렬화용)
    public QueryProblemRequestDto() {}

    // getter / setter
    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public int getMinDifficulty() {
        return minDifficulty;
    }

    public void setMinDifficulty(int minDifficulty) {
        this.minDifficulty = minDifficulty;
    }

    public int getMaxDifficulty() {
        return maxDifficulty;
    }

    public void setMaxDifficulty(int maxDifficulty) {
        this.maxDifficulty = maxDifficulty;
    }

    public int getMinAcceptableUserCount() {
        return minAcceptableUserCount;
    }

    public void setMinAcceptableUserCount(int minAcceptableUserCount) {
        this.minAcceptableUserCount = minAcceptableUserCount;
    }

    public int getMaxAcceptableUserCount() {
        return maxAcceptableUserCount;
    }

    public void setMaxAcceptableUserCount(int maxAcceptableUserCount) {
        this.maxAcceptableUserCount = maxAcceptableUserCount;
    }

    public List<Integer> getDesiredTags() {
        return desiredTags;
    }

    public void setDesiredTags(List<Integer> desiredTags) {
        this.desiredTags = desiredTags;
    }

    public List<Integer> getUndesiredTags() {
        return undesiredTags;
    }

    public void setUndesiredTags(List<Integer> undesiredTags) {
        this.undesiredTags = undesiredTags;
    }
}

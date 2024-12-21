package com.mockcote.problem.query.dto;

public class SolvedAcProblem {

    private int problemId;

    // 문제 제목, 태그 등 더 파싱 가능하지만 여기서는 problemId만 주로 사용
    // 필요시 titleKo, tags 등 필드/Getter/Setter를 추가

    public SolvedAcProblem() {}

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }
}

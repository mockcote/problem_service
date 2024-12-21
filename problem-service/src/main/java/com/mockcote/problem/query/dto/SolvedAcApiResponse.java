package com.mockcote.problem.query.dto;

import java.util.List;

public class SolvedAcApiResponse {

    private int count;           // 전체 결과 수
    private List<SolvedAcProblem> items;  // 실제 문제 목록

    public SolvedAcApiResponse() {}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SolvedAcProblem> getItems() {
        return items;
    }

    public void setItems(List<SolvedAcProblem> items) {
        this.items = items;
    }
}

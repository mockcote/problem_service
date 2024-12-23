package com.mockcote.problem.query.controller;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mockcote.problem.query.dto.QueryProblemRequestDto;
import com.mockcote.problem.query.dto.QueryProblemResponseDto;
import com.mockcote.problem.query.entity.QueryProblem;
import com.mockcote.problem.query.service.QueryProblemService;
import com.mockcote.problem.query.service.SolvedAcService;

@RestController
@RequestMapping("/problem")
public class QueryProblemController {

    private final QueryProblemService queryProblemService;
    private final SolvedAcService solvedAcService;

    public QueryProblemController(QueryProblemService queryProblemService,
                                  SolvedAcService solvedAcService) {
        this.queryProblemService = queryProblemService;
        this.solvedAcService = solvedAcService;
    }

    @PostMapping("/query")
    public ResponseEntity<?> queryOneProblem(@RequestBody QueryProblemRequestDto requestDto) {

        // 1) solved.ac API 통해 푼 문제 ID 수집
        Set<Integer> solvedProblemIds = solvedAcService.getSolvedProblemIds(requestDto.getHandle());

        // 2) DB에서 "아직 풀지 않은 문제"를 조건 필터링하여 하나 찾기
        QueryProblem found = queryProblemService.findOneProblemForUser(
                requestDto.getHandle(),
                requestDto,
                solvedProblemIds
        );

        if (found == null) {
            return ResponseEntity.notFound().build();
        }

        // 3) 응답 DTO
        QueryProblemResponseDto responseDto = new QueryProblemResponseDto(
                found.getProblemId(),
                found.getTitle(),
                found.getDifficulty(),
                found.getAcceptableUserCount()
        );
        return ResponseEntity.ok(responseDto);
    }
}
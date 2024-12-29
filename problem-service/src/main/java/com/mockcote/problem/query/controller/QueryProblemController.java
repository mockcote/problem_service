package com.mockcote.problem.query.controller;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mockcote.problem.query.dto.HasSolvedRequestDto;
import com.mockcote.problem.query.dto.HasSolvedResponseDto;
import com.mockcote.problem.query.dto.QueryProblemRequestDto;
import com.mockcote.problem.query.dto.QueryProblemResponseDto;
import com.mockcote.problem.query.entity.QueryProblem;
import com.mockcote.problem.query.service.QueryProblemService;
import com.mockcote.problem.query.service.SolvedAcService;

import jakarta.validation.Valid;

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
    
    @GetMapping("/random")
    public ResponseEntity<?> getRandomProblem() {
        QueryProblem randomProblem = queryProblemService.getRandomProblemWithinDifficulty();
        if (randomProblem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(randomProblem.getProblemId());
    }
    
    /**
     * 특정 사용자가 특정 문제를 해결했는지 확인하는 API
     * @param requestDto 요청 DTO (handle, problemId)
     * @return 해결 여부
     */
    @PostMapping("/has-solved")
    public ResponseEntity<HasSolvedResponseDto> hasSolvedProblem(@Valid @RequestBody HasSolvedRequestDto requestDto) {
        try {
            // 1) solved.ac API 통해 푼 문제 ID 수집
            Set<Integer> solvedProblemIds = solvedAcService.getSolvedProblemIds(requestDto.getHandle());

            // 2) Set을 이용해 problemId가 있는지 확인
            boolean hasSolved = queryProblemService.hasUserSolvedProblem(
                    requestDto.getHandle(),
                    requestDto.getProblemId(),
                    solvedProblemIds
            );

            // 3) 응답 DTO 생성
            HasSolvedResponseDto responseDto = new HasSolvedResponseDto(hasSolved);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            // 클라이언트에 오류 응답
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new HasSolvedResponseDto(false));
        }
    }
}

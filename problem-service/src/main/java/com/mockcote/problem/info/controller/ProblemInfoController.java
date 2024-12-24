package com.mockcote.problem.info.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mockcote.problem.info.dto.ProblemInfoResponse;
import com.mockcote.problem.info.dto.ProblemListResponse;
import com.mockcote.problem.info.service.ProblemInfoService;

@RestController
@RequestMapping("/problem")
public class ProblemInfoController {

    private final ProblemInfoService problemInfoService;

    public ProblemInfoController(ProblemInfoService problemInfoService) {
        this.problemInfoService = problemInfoService;
    }

    @GetMapping("/info")
    public ProblemInfoResponse getProblemInfo(@RequestParam Integer problemId) {
        return problemInfoService.getProblemInfoById(problemId);
    }
    
    @GetMapping("/list")
    public ProblemListResponse getAllProblems(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return problemInfoService.getAllProblems(pageable);
    }
}

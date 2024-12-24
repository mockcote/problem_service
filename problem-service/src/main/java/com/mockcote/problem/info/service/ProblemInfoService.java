package com.mockcote.problem.info.service;

import org.springframework.data.domain.Pageable;

import com.mockcote.problem.info.dto.ProblemInfoResponse;
import com.mockcote.problem.info.dto.ProblemListResponse;

public interface ProblemInfoService {
    ProblemInfoResponse getProblemInfoById(Integer problemId);
    
    ProblemListResponse getAllProblems(Pageable pageable);
}

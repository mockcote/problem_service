package com.mockcote.problem.info.service;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mockcote.problem.info.dto.ProblemInfoResponse;
import com.mockcote.problem.info.dto.ProblemListResponse;
import com.mockcote.problem.info.dto.ProblemListResponse.ProblemSummary;
import com.mockcote.problem.info.entity.ProblemInfo;
import com.mockcote.problem.info.repository.ProblemInfoRepository;

@Service
public class ProblemInfoServiceImpl implements ProblemInfoService {

    private final ProblemInfoRepository problemInfoRepository;

    public ProblemInfoServiceImpl(ProblemInfoRepository problemInfoRepository) {
        this.problemInfoRepository = problemInfoRepository;
    }

    @Override
    public ProblemInfoResponse getProblemInfoById(Integer problemId) {
        ProblemInfo problemInfo = problemInfoRepository.findById(problemId)
            .orElseThrow(() -> new RuntimeException("Problem not found with ID: " + problemId));

        return new ProblemInfoResponse(
            problemInfo.getProblemId(),
            problemInfo.getTitle(),
            problemInfo.getDifficulty(),
            problemInfo.getAcceptableUserCount(),
            problemInfo.getTags().stream()
                .map(tag -> new ProblemInfoResponse.Tag(tag.getTagId(), tag.getTagName()))
                .collect(Collectors.toList())
        );
    }
    
    @Override
    public ProblemListResponse getAllProblems(Pageable pageable) {
        Page<ProblemInfo> page = problemInfoRepository.findAll(pageable);

        return new ProblemListResponse(
            page.getContent().stream()
                .map(problem -> new ProblemSummary(problem.getProblemId(), problem.getTitle(), problem.getDifficulty()))
                .collect(Collectors.toList()),
            page.getNumber(),
            page.getTotalPages(),
            page.getTotalElements()
        );
    }
}

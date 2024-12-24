package com.mockcote.problem.query.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mockcote.problem.query.dto.QueryProblemRequestDto;
import com.mockcote.problem.query.entity.QueryProblem;
import com.mockcote.problem.query.entity.QueryUserSolvedProblem;
import com.mockcote.problem.query.entity.QueryUserSolvedProblemId;
import com.mockcote.problem.query.repository.QueryProblemRepository;
import com.mockcote.problem.query.repository.QueryUserSolvedProblemRepository;

@Service
public class QueryProblemService {

    private final QueryProblemRepository problemRepository;
    private final QueryUserSolvedProblemRepository userSolvedRepo;

    public QueryProblemService(QueryProblemRepository problemRepository,
                               QueryUserSolvedProblemRepository userSolvedRepo) {
        this.problemRepository = problemRepository;
        this.userSolvedRepo = userSolvedRepo;
    }

    @Transactional
    public QueryProblem findOneProblemForUser(String userId,
                                              QueryProblemRequestDto requestDto,
                                              Set<Integer> solvedProblemIds) {
        // 1) 이미 푼 문제 저장
        storeUserSolvedProblems(userId, solvedProblemIds);

        // 2) 조건 필터링 및 candidate 조회
        List<Integer> desiredTags = (requestDto.getDesiredTags() == null || requestDto.getDesiredTags().isEmpty())
                ? null : requestDto.getDesiredTags();
        List<Integer> undesiredTags = (requestDto.getUndesiredTags() == null || requestDto.getUndesiredTags().isEmpty())
                ? null : requestDto.getUndesiredTags();

        List<QueryProblem> candidates = problemRepository.findCandidateProblemsExcludingSolved(
                userId,
                requestDto.getMinDifficulty(),
                requestDto.getMaxDifficulty(),
                requestDto.getMinAcceptableUserCount(),
                requestDto.getMaxAcceptableUserCount(),
                desiredTags,
                undesiredTags,
                1000
        );

        if (candidates.isEmpty()) {
            return null; // 조건에 맞는 문제가 전혀 없다!
        }

        // 3) 랜덤 선택
        int randomIndex = new java.util.Random().nextInt(candidates.size());
        return candidates.get(randomIndex);
    }


    private void storeUserSolvedProblems(String userId, Set<Integer> solvedProblemIds) {
        // 예시: 대량 처리는 Native Insert Ignore or Batch Insert 고려
        if (solvedProblemIds == null || solvedProblemIds.isEmpty()) return;

        for (Integer pid : solvedProblemIds) {
            QueryUserSolvedProblemId key = new QueryUserSolvedProblemId(userId, pid);
            if (!userSolvedRepo.existsById(key)) {
                // 기존 방식 (단건 save) - 반복 쿼리 많아질 수 있음
                QueryProblem problem = new QueryProblem();
                problem.setProblemId(pid);

                QueryUserSolvedProblem entity = new QueryUserSolvedProblem(userId, problem);
                userSolvedRepo.save(entity);
            }
        }
    }
    
    public QueryProblem getRandomProblemWithinDifficulty() {
        return problemRepository.findRandomProblemWithinDifficulty();
    }
}

	
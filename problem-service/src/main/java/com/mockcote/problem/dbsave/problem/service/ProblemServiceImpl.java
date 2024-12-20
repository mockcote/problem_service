package com.mockcote.problem.dbsave.problem.service;

import com.mockcote.problem.dbsave.problem.entity.Problem;
import com.mockcote.problem.dbsave.problem.entity.ProblemTag;
import com.mockcote.problem.dbsave.problem.entity.ProblemTagId;
import com.mockcote.problem.dbsave.problem.repository.ProblemRepository;
import com.mockcote.problem.dbsave.problem.repository.ProblemTagRepository;
import com.mockcote.problem.dbsave.tag.entity.TagLabel;
import com.mockcote.problem.dbsave.tag.repository.TagLabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProblemTagRepository problemTagRepository;

    @Autowired
    private TagLabelRepository tagLabelRepository;

    @Override
    @Transactional
    public void fetchAndSaveAllProblems() {
        RestTemplate restTemplate = new RestTemplate();
        int batchSize = 50; // 한 번에 가져올 문제 수
        int startId = 1000; // 시작 ID
        int endId = 32974;   // 종료 ID

        for (int i = startId; i <= endId; i += batchSize) {
            String problemIds = generateProblemIdsParam(i, Math.min(i + batchSize - 1, endId));
            String apiUrl = "https://solved.ac/api/v3/problem/lookup?problemIds=" + problemIds;

            try {
                ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                        apiUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

                List<Map<String, Object>> problems = response.getBody();
                if (problems != null) {
                    problems.forEach(this::saveProblem);
                }
            } catch (Exception e) {
                System.err.println("Error fetching problems for IDs: " + problemIds);
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public void saveProblem(Map<String, Object> item) {
        Integer problemId = (Integer) item.get("problemId");
        String title = (String) item.get("titleKo");
        Integer difficulty = (Integer) item.get("level");
        Integer acceptedUserCount = (Integer) item.get("acceptedUserCount");

        // Problem 저장
        Problem problem = problemRepository.findById(problemId).orElse(new Problem());
        problem.setProblemId(problemId);
        problem.setTitle(title);
        problem.setDifficulty(difficulty);
        problem.setAcceptableUserCount(acceptedUserCount);

        problemRepository.save(problem);

        // 태그 처리
        List<Map<String, Object>> tags = (List<Map<String, Object>>) item.get("tags");
        if (tags != null) {
            for (Map<String, Object> tag : tags) {
                Integer tagId = (Integer) tag.get("bojTagId");
                String tagName = (String) ((List<Map<String, Object>>) tag.get("displayNames")).get(0).get("name");

                // TagLabel 저장
                TagLabel tagLabel = tagLabelRepository.findById(tagId).orElse(new TagLabel());
                tagLabel.setTagId(tagId);
                tagLabel.setTagName(tagName);
                tagLabelRepository.save(tagLabel);

                // ProblemTag 저장
                ProblemTag problemTag = new ProblemTag();
                problemTag.setId(new ProblemTagId(problemId, tagId));
                problemTag.setProblem(problem);
                problemTag.setTagLabel(tagLabel);

                problemTagRepository.save(problemTag);
            }
        }
    }

    private String generateProblemIdsParam(int startId, int endId) {
        StringBuilder sb = new StringBuilder();
        for (int i = startId; i <= endId; i++) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(i);
        }
        return sb.toString();
    }
}

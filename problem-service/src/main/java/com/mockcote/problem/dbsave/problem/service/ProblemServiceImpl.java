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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class ProblemServiceImpl implements ProblemService {

    private static final Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);

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
        int currentPage = 1;
        int totalCount = 0;
        int processedCount = 0;

        try {
            while (true) {
                // UriComponentsBuilder를 사용하여 URL 구성
                String apiUrl = UriComponentsBuilder.fromHttpUrl("https://solved.ac/api/v3/search/problem")
                        .queryParam("query", "lang:ko")
                        .queryParam("page", currentPage)
                        .toUriString();

                logger.info("Fetching page {} from API: {}", currentPage, apiUrl);

                // HTTP 헤더 설정
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(List.of(MediaType.APPLICATION_JSON));
                headers.set("User-Agent", "Mozilla/5.0"); // 필요 시 User-Agent 설정

                HttpEntity<String> entity = new HttpEntity<>(headers);

                // API 요청
                ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                        apiUrl,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<>() {}
                );

                Map<String, Object> responseBody = response.getBody();
                if (responseBody == null) {
                    logger.warn("Response body is null for page {}", currentPage);
                    break;
                }

                // 응답 본문 로그 출력 (디버깅 용도)
                logger.debug("Response Body: {}", responseBody);

                if (currentPage == 1) {
                    // 최초 페이지에서 전체 개수 확인
                    Object countObj = responseBody.get("count");
                    if (countObj instanceof Integer) {
                        totalCount = (Integer) countObj;
                        logger.info("Total problems to process: {}", totalCount);
                    } else if (countObj instanceof Number) {
                        totalCount = ((Number) countObj).intValue();
                        logger.info("Total problems to process: {}", totalCount);
                    } else {
                        logger.warn("Unexpected type for 'count': {}", countObj != null ? countObj.getClass().getName() : "null");
                    }
                }

                List<Map<String, Object>> items = (List<Map<String, Object>>) responseBody.get("items");
                if (items == null || items.isEmpty()) {
                    logger.info("No more items found. Ending pagination.");
                    break;
                }

                items.forEach(this::saveProblem);
                processedCount += items.size();
                logger.info("Processed {}/{} problems from page {}", processedCount, totalCount, currentPage);

                currentPage++;

                // Optional: API 호출 간 간격을 두어 Rate Limiting 방지
                // Thread.sleep(100); // 100ms 대기
            }

            logger.info("Completed fetching and saving all problems. Total processed: {}", processedCount);
        } catch (Exception e) {
            logger.error("Error fetching problems from the new API.", e);
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
                // displayNames는 리스트이므로 "ko" 언어의 name을 찾아야 함
                List<Map<String, Object>> displayNames = (List<Map<String, Object>>) tag.get("displayNames");
                String tagName = displayNames.stream()
                        .filter(dn -> "ko".equals(dn.get("language")))
                        .map(dn -> (String) dn.get("name"))
                        .findFirst()
                        .orElse("Unknown Tag");

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
}

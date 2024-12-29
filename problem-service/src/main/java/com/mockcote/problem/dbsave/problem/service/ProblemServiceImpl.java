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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final WebClient webClient;

    public ProblemServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://solved.ac/api/v3").build();
    }

    @Override
    @Transactional
    public void fetchAndSaveAllProblems() {
    	int[] currentPage = {1}; // 배열로 선언
        int totalCount = 0;
        int processedCount = 0;

        try {
            while (true) {
                // WebClient로 API 호출
                String apiUrl = "/search/problem";

                Map<String, Object> responseBody = webClient.get()
                	    .uri(uriBuilder -> uriBuilder.path(apiUrl)
                	            .queryParam("query", "lang:ko")
                	            .queryParam("page", String.valueOf(currentPage[0])) // currentPage를 String으로 변환
                	            .build())
                	    .header("User-Agent", "Mozilla/5.0")
                	    .retrieve()
                	    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                	    .block();


                if (responseBody == null) {
                    logger.warn("Response body is null for page {}", currentPage);
                    break;
                }

                if (currentPage[0] == 1) {
                    // 최초 페이지에서 전체 개수 확인
                    Object countObj = responseBody.get("count");
                    if (countObj instanceof Number) {
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

                currentPage[0]++;
            }

            logger.info("Completed fetching and saving all problems. Total processed: {}", processedCount);
        } catch (Exception e) {
            logger.error("Error fetching problems from the API.", e);
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

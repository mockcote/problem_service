package com.mockcote.problem.dbsave.tag.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.mockcote.problem.dbsave.tag.entity.TagLabel;
import com.mockcote.problem.dbsave.tag.repository.TagLabelRepository;
import com.mockcote.problem.query.service.SolvedAcService.SolvedAcItem;
import com.mockcote.problem.query.service.SolvedAcService.SolvedAcResponse;

import reactor.core.publisher.Mono;

@Service
public class TagLabelServiceImpl implements TagLabelService {

	private final TagLabelRepository tagLabelRepository;
	private final WebClient webClient; // WebClient 사용

	public TagLabelServiceImpl(TagLabelRepository tagLabelRepository) {
		this.tagLabelRepository = tagLabelRepository;
		this.webClient = WebClient.create();
	}

	@Override
	public void saveTagLabel(TagLabel tagLabel) {
		tagLabelRepository.save(tagLabel);
	}

	@Override
	public void fetchAndSaveTags() {
		RestTemplate restTemplate = new RestTemplate();

		for (int page = 1; page <= 7; page++) {
			String apiUrl = "https://solved.ac/api/v3/tag/list?page=" + page;

			// WebClient로 호출
			Map<String, Object> response = webClient.get().uri(apiUrl).retrieve().bodyToMono(Map.class).block(); // 여기서는
																													// 동기
																													// 처리를
																													// 위해
																													// block()
//			Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

			if (response != null && response.containsKey("items")) {
				List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

				for (Map<String, Object> item : items) {
					Integer bojTagId = (Integer) item.get("bojTagId");
					List<Map<String, String>> displayNames = (List<Map<String, String>>) item.get("displayNames");

					if (bojTagId != null && displayNames != null) {
						displayNames.stream().filter(displayName -> "ko".equals(displayName.get("language")))
								.findFirst().ifPresent(displayName -> {
									TagLabel tagLabel = new TagLabel();
									tagLabel.setTagId(bojTagId);
									tagLabel.setTagName(displayName.get("name"));
									saveTagLabel(tagLabel);
								});
					}
				}
			}
		}
	}

	/**
	 * 새로 추가하는 메서드
	 */
	@Override
	public Integer findRandomUnsolvedProblem(String handle, int level) {

//		RestTemplate restTemplate = new RestTemplate();
//		List<Integer> vulnerableTagIds = restTemplate
//				.getForObject("http://localhost:8082/stats/tags/lowest?handle=" + handle, List.class);

		List<Integer> vulnerableTagIds = webClient.get()
				.uri("http://localhost:8082/stats/tags/lowest?handle={handle}", handle).retrieve()
				.bodyToFlux(Integer.class) // 응답을 각 Integer로 받음
				.collectList() // Flux -> List 로 변환
				.block(); // 동기 처리

		System.out.println("API에서 받아온 취약 태그 ID들 >> " + vulnerableTagIds);

		// 1) solved.ac API로 풀었던 문제 ID 추출
		Set<Integer> solvedProblemIds = fetchSolvedProblemIdsFromSolvedAc(handle);
		System.out.println("handle이 푼 문제 >> " + solvedProblemIds);
		System.out.println("푼 문제 갯수 : " + solvedProblemIds.size());

		List<Integer> levels = new ArrayList<>();
		if (level >= 3) {
			for (int i = 0; i <= 3; i++) {
				levels.add(level - i);
			}
		} else {
			for (int i = 0; i <= level; i++) {
				levels.add(i);
			}
		}

		// 2) 해당 취약 태그가 달린 모든 문제 ID 조회
		System.out.println("취약 태그 >> " + vulnerableTagIds);
		Set<Integer> candidateProblemIds = tagLabelRepository.findProblemIdsByTagNamesIn(vulnerableTagIds, levels);

		// 3) "이미 푼 문제"는 제외
		candidateProblemIds.removeAll(solvedProblemIds);
		System.out.println("문제 리스트 >> " + candidateProblemIds);

		// 4) 남은 문제들 중 하나 무작위로 뽑기
		if (candidateProblemIds.isEmpty()) {
			// 남은 문제가 없으면 null 리턴 (혹은 예외)
			System.out.println("취약문제 없음");
			return null;
		}

		List<Integer> unsolvedList = new ArrayList<>(candidateProblemIds);
		Random random = new Random();
		int index = random.nextInt(unsolvedList.size());
		System.out.println("뽑힌 문제 : " + unsolvedList.get(index));
		return unsolvedList.get(index);
	}

	/**
	 * solved.ac API를 호출해서, 특정 handle(백준 아이디)이 푼 문제들의 problemId를 Set<Integer>로 리턴.
	 *
	 * 예)
	 * https://solved.ac/api/v3/search/problem?query=solved_by:rlaekwjd6545&page=1
	 * 응답 본문(body) 중 "items" 배열에 "problemId" 속성이 들어 있음
	 */
	private Set<Integer> fetchSolvedProblemIdsFromSolvedAc(String handle) {
		Set<Integer> solvedSet = new HashSet<>();
		try {
			int page = 1;
			while (true) {
//				String url = "https://solved.ac/api/v3/search/problem?query=solved_by:" + handle + "&page=" + page;
//				RestTemplate restTemplate = new RestTemplate();
				
				

				// 응답을 Jackson의 JsonNode로 매핑
//				ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
//				if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
//					// 정상 응답이 아니면 반복 중단 (또는 throw)
//					break;
//				}
//				JsonNode items = response.getBody().get("items");
//				if (items == null || !items.isArray() || items.size() == 0) {
//					break;
//				}
//				for (JsonNode item : items) {
//					// "problemId" 필드 추출
//					int problemId = item.get("problemId").asInt();
//					solvedSet.add(problemId);
//				}
				
				 SolvedAcResponse response = webClient.get()
		                    .uri("https://solved.ac/api/v3/search/problem?query=solved_by:{handle}&page={page}",
		                            handle, page)
		                    .retrieve()
		                    .onStatus(
		                    	    status -> status.is4xxClientError() || status.is5xxServerError(),
		                    	    clientResponse -> Mono.error(
		                    	        new RuntimeException("solved.ac API 에러 응답: " + clientResponse.statusCode())
		                    	    )
		                    	)
		                    .bodyToMono(SolvedAcResponse.class)
		                    .block(); // 동기처리

		            // 응답이 null 이거나 items가 없으면 중단
		            if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
		                break;
		            }

		            // items에서 problemId 추출
		            for (SolvedAcItem item : response.getItems()) {
		                solvedSet.add(item.getProblemId());
		            }

		            // 이미 가져온 문제 수 >= API에서 제공하는 count (전체 문제 수) 이면 중단
		            if (solvedSet.size() >= response.getCount()) {
		                break;
		            }
				
				page++;
			}
		} catch (Exception e) {
			// 예외 발생 시, 로그 찍거나 적절히 처리
			e.printStackTrace();
		}
		return solvedSet;
	}
}

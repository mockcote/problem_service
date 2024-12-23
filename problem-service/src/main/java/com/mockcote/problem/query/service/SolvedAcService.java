package com.mockcote.problem.query.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SolvedAcService {

    private final WebClient webClient;

    // 생성자 주입
    public SolvedAcService() {
        // WebClient.Builder를 사용해도 되고, 그냥 기본 builder 사용해도 됩니다.
        this.webClient = WebClient.builder()
            .baseUrl("https://solved.ac/api/v3/search/problem")
            .build();
    }

    /**
     * solved.ac API를 페이징하면서, 특정 handle(예: "rlaekwjd6545")이 푼 모든 문제 목록을 가져온다.
     * - 한 페이지당 items 50개 (기본 가정)
     * - 더 이상 새로운 문제Id가 없을 때까지 반복
     */
    public Set<Integer> getSolvedProblemIds(String handle) {
        Set<Integer> solvedProblemIds = new HashSet<>();
        int currentPage = 1;

        while (true) {
            // 1) API 호출
            SolvedAcResponse response = fetchSolvedAcPage(handle, currentPage);
            if (response == null) {
                // API 호출 에러 등으로 null 반환 시 그냥 break
                break;
            }

            // 2) 응답에서 problemId 추출 후 Set에 추가
            List<SolvedAcItem> items = response.getItems();
            if (items == null || items.isEmpty()) {
                // 더 이상 문제가 없으므로 종료
                break;
            }

            for (SolvedAcItem item : items) {
                solvedProblemIds.add(item.getProblemId());
            }

            // 3) 다음 페이지로
            currentPage++;

            // 만약 전체 count보다 이미 다 가져왔다면 멈춰도 됨
            // (예: items 총 개수가 count와 일치하면 다 불러온 것)
            if (solvedProblemIds.size() >= response.getCount()) {
                break;
            }
        }

        return solvedProblemIds;
    }

    /**
     * 해당 handle, page로 solved.ac API 호출 -> JSON 응답을 SolvedAcResponse로 매핑
     */
    private SolvedAcResponse fetchSolvedAcPage(String handle, int page) {
        try {
            // 쿼리 파라미터 query=solved_by:{handle}, page={page}
            return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("query", "solved_by:" + handle)
                    .queryParam("page", page)
                    .build()
                )
                .retrieve()
                .bodyToMono(SolvedAcResponse.class) // 비동기로 Mono<SolvedAcResponse> 반환
                .block(); // 여기서는 간단히 block()으로 동기화. (단, 프로젝트에 따라 비동기 처리도 가능)
        } catch (Exception e) {
            log.error("solved.ac API 호출 실패. handle={}, page={}, err={}", handle, page, e.getMessage());
            return null;
        }
    }

    /**
     * solved.ac API 전체 응답을 매핑할 DTO
     */
    public static class SolvedAcResponse {
        @JsonProperty("count")
        private int count;

        @JsonProperty("items")
        private List<SolvedAcItem> items;

        public int getCount() {
            return count;
        }

        public List<SolvedAcItem> getItems() {
            return items;
        }
    }

    /**
     * solved.ac API 응답 items 배열 내 각 문제 객체
     */
    public static class SolvedAcItem {
        @JsonProperty("problemId")
        private int problemId;

        public int getProblemId() {
            return problemId;
        }
    }
}

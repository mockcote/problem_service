package com.mockcote.problem.dbsave.tag.service;

import java.util.List;

import com.mockcote.problem.dbsave.tag.entity.TagLabel;

public interface TagLabelService {
	void saveTagLabel(TagLabel tagLabel);

	void fetchAndSaveTags();

	/**
	 * 기능: 1) solved.ac API로 해당 handle이 푼 문제를 모두 가져와 Set에 저장 
	 * 2) vulnerableTagNames에 해당하는 문제 목록을 DB에서 조회
	 * 3) 풀었던 문제는 빼고, 남은 문제들 중 무작위로 1개 리턴 (문제 ID)
	 * @param handle             사용자 백준 아이디 (예: rlaekwjd6545)
	 * @param vulnerableTagNames 취약 태그 이름 리스트
	 * @return 문제 ID (없으면 null 혹은 Optional)
	 */
	Integer findRandomUnsolvedProblem(String handle, int level, List<String> vulnerableTagNames);

}

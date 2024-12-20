package com.mockcote.problem.dbsave.tag.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mockcote.problem.dbsave.tag.entity.TagLabel;
import com.mockcote.problem.dbsave.tag.repository.TagLabelRepository;

@Service
public class TagLabelServiceImpl implements TagLabelService {

    private final TagLabelRepository tagLabelRepository;

    public TagLabelServiceImpl(TagLabelRepository tagLabelRepository) {
        this.tagLabelRepository = tagLabelRepository;
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

            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

            if (response != null && response.containsKey("items")) {
                List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

                for (Map<String, Object> item : items) {
                    Integer bojTagId = (Integer) item.get("bojTagId");
                    List<Map<String, String>> displayNames = (List<Map<String, String>>) item.get("displayNames");

                    if (bojTagId != null && displayNames != null) {
                        displayNames.stream()
                                .filter(displayName -> "ko".equals(displayName.get("language")))
                                .findFirst()
                                .ifPresent(displayName -> {
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
}

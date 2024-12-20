package com.mockcote.problem.dbsave.tag.service;

import com.mockcote.problem.dbsave.tag.entity.TagLabel;

public interface TagLabelService {
    void saveTagLabel(TagLabel tagLabel);
    void fetchAndSaveTags();
}

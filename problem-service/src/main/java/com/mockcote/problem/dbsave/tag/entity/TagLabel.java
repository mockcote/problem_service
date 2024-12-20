package com.mockcote.problem.dbsave.tag.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tag_label")
public class TagLabel {
    @Id
    private Integer tagId; // bojTagId에 해당

    private String tagName; // displayNames.language == "ko"의 name에 해당

    // Getters and Setters
    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}

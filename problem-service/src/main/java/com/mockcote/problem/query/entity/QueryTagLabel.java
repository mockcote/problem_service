package com.mockcote.problem.query.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tag_label")
public class QueryTagLabel {

    @Id
    @Column(name = "tag_id")
    private Integer tagId;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    protected QueryTagLabel() {}

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

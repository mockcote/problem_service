package com.mockcote.problem.dbsave.tag.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tag_label")
@Getter
@Setter
public class TagLabel {

    @Id
    @Column(name = "tag_id")
    private Integer tagId;

    @Column(name = "tag_name", nullable = false)
    private String tagName;
    
 // 기본 생성자
    public TagLabel() {
    }

    // 필요하면 편의상 사용할 수 있는 생성자
    public TagLabel(Integer tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    // Getter/Setter
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
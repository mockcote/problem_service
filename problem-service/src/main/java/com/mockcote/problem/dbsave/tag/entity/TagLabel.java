package com.mockcote.problem.dbsave.tag.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "DbSaveTagLabel")
@Table(name = "tag_label")
@Getter
@Setter
public class TagLabel {

    @Id
    @Column(name = "tag_id")
    private Integer tagId;

    @Column(name = "tag_name", nullable = false)
    private String tagName;
}
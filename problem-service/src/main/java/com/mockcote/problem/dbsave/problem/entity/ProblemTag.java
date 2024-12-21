package com.mockcote.problem.dbsave.problem.entity;

import com.mockcote.problem.dbsave.tag.entity.TagLabel;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "DbSaveProblemTag")
@Table(name = "problem_tag")
@Getter
@Setter
public class ProblemTag {

    @EmbeddedId
    private ProblemTagId id;

    @ManyToOne
    @JoinColumn(name = "problem_id", insertable = false, updatable = false)
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private TagLabel tagLabel;

    // Getters and Setters
}
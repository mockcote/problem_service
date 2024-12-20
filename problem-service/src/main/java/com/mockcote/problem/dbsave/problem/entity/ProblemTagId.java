package com.mockcote.problem.dbsave.problem.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProblemTagId implements Serializable {

    @Column(name = "problem_id")
    private Integer problemId;

    @Column(name = "tag_id")
    private Integer tagId;
}
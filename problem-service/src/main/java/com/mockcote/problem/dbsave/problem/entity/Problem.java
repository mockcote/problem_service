package com.mockcote.problem.dbsave.problem.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "DbSaveProblem")
@Table(name = "problem")
@Getter
@Setter
public class Problem {

    @Id
    @Column(name = "problem_id")
    private Integer problemId;

    @Column(name = "title")
    private String title;

    @Column(name = "difficulty", nullable = false)
    private Integer difficulty;

    @Column(name = "acceptable_user_count", nullable = false)
    private Integer acceptableUserCount;
}

package com.mockcote.problem.info.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "problem")
@Getter
@Setter
@NoArgsConstructor
public class ProblemInfo {

    @Id
    @Column(name = "problem_id") 
    private Integer problemId;

    private String title;

    private Integer difficulty;
    
    @Column(name = "acceptable_user_count")
    private Integer acceptableUserCount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "problem_tag",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagInfo> tags;

}

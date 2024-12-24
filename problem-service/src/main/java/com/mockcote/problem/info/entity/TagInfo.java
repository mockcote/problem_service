package com.mockcote.problem.info.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tag_label")
@Getter
@Setter
@NoArgsConstructor
public class TagInfo {

    @Id
    @Column(name = "tag_id")
    private Integer tagId;
    
    @Column(name = "tag_name")
    private String tagName;
    
    @ManyToMany(mappedBy = "tags")
    private Set<ProblemInfo> problems; // ProblemInfo와 양방향 매핑
}

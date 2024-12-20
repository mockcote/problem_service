package com.mockcote.problem.dbsave.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockcote.problem.dbsave.tag.entity.TagLabel;

@Repository
public interface TagLabelRepository extends JpaRepository<TagLabel, Integer> {
}

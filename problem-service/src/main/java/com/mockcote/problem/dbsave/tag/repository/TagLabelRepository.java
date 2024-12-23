package com.mockcote.problem.dbsave.tag.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mockcote.problem.dbsave.tag.entity.TagLabel;

@Repository
public interface TagLabelRepository extends JpaRepository<TagLabel, Integer> {
    boolean existsById(Integer id);
    
    @Query(value = """
    	    SELECT DISTINCT pt.problem_id
    	    FROM problem_tag pt
    	    JOIN tag_label tl ON pt.tag_id = tl.tag_id
    	    JOIN problem p ON pt.problem_id = p.problem_id
    	    WHERE tl.tag_id IN (:tagNames)
    	    AND p.difficulty IN (:level)
    	""", nativeQuery = true)
    	Set<Integer> findProblemIdsByTagNamesIn(@Param("tagNames") List<String> tagNames, @Param("level") List<Integer> level);


}
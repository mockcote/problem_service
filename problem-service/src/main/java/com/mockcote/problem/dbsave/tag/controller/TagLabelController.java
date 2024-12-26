package com.mockcote.problem.dbsave.tag.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mockcote.problem.dbsave.tag.service.TagLabelService;

@RestController
@RequestMapping("/dbsave/tag")
public class TagLabelController {

    private final TagLabelService tagLabelService;

    @Autowired
    public TagLabelController(TagLabelService tagLabelService) {
        this.tagLabelService = tagLabelService;
    }

    @PostMapping("/fetch-all")
    public String fetchAndSaveAllTags() {
        tagLabelService.fetchAndSaveTags();
        return "All tags fetched and saved successfully.";
    }
    
    
    
    /**
     * [GET] /dbsave/tag/random-problem?handle=rlaekwjd6545&tag=math&tag=dp&tag=greedy
     */
    @GetMapping("/random-problem")
    public Integer getRandomUnsolvedProblem(
            @RequestParam String handle,
            @RequestParam("level") int level
    ) {
        return tagLabelService.findRandomUnsolvedProblem(handle, level);
    }
}

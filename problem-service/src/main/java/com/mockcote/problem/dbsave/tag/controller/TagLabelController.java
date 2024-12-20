package com.mockcote.problem.dbsave.tag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}

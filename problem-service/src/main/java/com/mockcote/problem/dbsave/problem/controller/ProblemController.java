package com.mockcote.problem.dbsave.problem.controller;

import com.mockcote.problem.dbsave.problem.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("problemDbSaveController")
@RequestMapping("/dbsave/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @PostMapping("/fetch-all")
    public String fetchAndSaveAllProblems() {
        problemService.fetchAndSaveAllProblems();
        return "All problems fetched and saved successfully.";
    }
}

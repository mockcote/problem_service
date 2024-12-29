package com.mockcote.problem.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HasSolvedRequestDto {
    private String handle;
    private int problemId;
}

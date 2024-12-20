package com.mockcote.problem.dbsave.problem.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProblemResponse {
    private int count;

    @JsonProperty("items")
    private List<ProblemItem> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProblemItem {
        @JsonProperty("problemId")
        private int problemId;

        @JsonProperty("titleKo")
        private String title;

        @JsonProperty("level")
        private int difficulty;

        @JsonProperty("acceptedUserCount")
        private int acceptedUserCount;

        @JsonProperty("tags")
        private List<Tag> tags;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Tag {
            @JsonProperty("bojTagId")
            private int tagId;
        }
    }
}

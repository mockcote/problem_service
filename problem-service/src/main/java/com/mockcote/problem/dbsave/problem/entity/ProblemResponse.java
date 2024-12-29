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
        private String titleKo;

        @JsonProperty("titles")
        private List<Title> titles;

        @JsonProperty("level")
        private int level;

        @JsonProperty("acceptedUserCount")
        private int acceptedUserCount;

        @JsonProperty("tags")
        private List<Tag> tags;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Title {
            @JsonProperty("language")
            private String language;

            @JsonProperty("languageDisplayName")
            private String languageDisplayName;

            @JsonProperty("title")
            private String title;

            @JsonProperty("isOriginal")
            private boolean isOriginal;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Tag {
            @JsonProperty("key")
            private String key;

            @JsonProperty("isMeta")
            private boolean isMeta;

            @JsonProperty("bojTagId")
            private int bojTagId;

            @JsonProperty("problemCount")
            private int problemCount;

            @JsonProperty("displayNames")
            private List<DisplayName> displayNames;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class DisplayName {
                @JsonProperty("language")
                private String language;

                @JsonProperty("name")
                private String name;

                @JsonProperty("short")
                private String shortName;
            }

            /**
             * 한국어 태그 이름을 반환하는 메서드
             */
            public String getKoreanName() {
                if (displayNames != null) {
                    for (DisplayName dn : displayNames) {
                        if ("ko".equalsIgnoreCase(dn.getLanguage())) {
                            return dn.getName();
                        }
                    }
                }
                return null;
            }
        }
    }
}

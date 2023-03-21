package com.github.antksk.blog.search.service.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface BlogSearchResult {
    String getName();
    String getTitle();
    @JsonProperty("contents")
    String getContents();
    @JsonProperty("url")
    String getUrl();
    @JsonProperty("postDate")
    String getPostDate();
}

package com.github.antksk.blog.search.service.external.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.antksk.blog.search.service.external.BlogSearchResult;
import com.github.antksk.blog.search.service.external.BlogSearchResults;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Set;

@Slf4j
@ToString
final class NaverBlogSearchResults implements BlogSearchResults {

    private final Meta meta;
    private final Set<? extends BlogSearchResult> results;

    private NaverBlogSearchResults() {
        meta = Meta.empty();
        results = Collections.emptySet();
    }

    private NaverBlogSearchResults(
            @JsonProperty("start") long start,
            @JsonProperty("total") long totalCount,
            @JsonProperty("display") long display,
            @JsonProperty("items") Set<Item> results
    ) {
        this.meta = Meta.fromJson(start, totalCount, display);
        this.results = results;
    }

    @Override
    public Set<? extends BlogSearchResult> getResults() {
        return results;
    }

    @Override
    public long totalCount() {
        return meta.getTotalCount();
    }
}

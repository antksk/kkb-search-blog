package com.github.antksk.blog.search.service.external.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.antksk.blog.search.service.external.BlogSearchResult;
import com.github.antksk.blog.search.service.external.BlogSearchResults;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Set;

@Slf4j
@ToString
final class KakaoBlogSearchResults implements BlogSearchResults {

    private final Meta meta;

    @Getter
    private final Set<? extends BlogSearchResult> results;

    private KakaoBlogSearchResults() {
        meta = Meta.empty();
        results = Collections.emptySet();
    }

    private KakaoBlogSearchResults(
            @JsonProperty("meta") Meta meta,
            @JsonProperty("documents") Set<Document> results
    ) {
        this.meta = meta;
        this.results = results;
    }

    @Override
    public long totalCount() {
        return meta.getTotalCount();
    }
}

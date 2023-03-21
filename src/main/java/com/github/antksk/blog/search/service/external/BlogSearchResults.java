package com.github.antksk.blog.search.service.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

import static java.util.stream.Collectors.toUnmodifiableList;

public interface BlogSearchResults {
    @JsonProperty("results")
    Collection<? extends BlogSearchResult> getResults();

    long totalCount();

    default Page<? extends BlogSearchResult> getPageableResults(Pageable pageable){
        var contents = getResults()
                .stream()
                .collect(toUnmodifiableList());
        return new PageImpl<>(contents, pageable, totalCount());
    }
}

package com.github.antksk.blog.search.service.external.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.checkerframework.checker.fenum.qual.SwingTextOrientation;

@ToString
final class Meta {
    private final boolean isEnd;
    @Getter
    private final long totalCount;
    private final long pageableCount;

    static final Meta empty = new Meta(false, -1, -1);

    static Meta empty() {
        return empty;
    }

    private Meta(
            @JsonProperty("is_end") boolean isEnd,
            @JsonProperty("total_count") long totalCount,
            @JsonProperty("pageable_count") long pageableCount
    ) {
        this.isEnd = isEnd;
        this.totalCount = totalCount;
        this.pageableCount = pageableCount;
    }
}

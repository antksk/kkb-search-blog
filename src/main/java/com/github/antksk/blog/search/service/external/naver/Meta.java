package com.github.antksk.blog.search.service.external.naver;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PACKAGE, staticName = "fromJson")
final class Meta {
    private final long start;
    @Getter
    private final long totalCount;
    private final long display;

    static final Meta empty = new Meta(-1, -1, -1);

    static Meta empty() {
        return empty;
    }
}

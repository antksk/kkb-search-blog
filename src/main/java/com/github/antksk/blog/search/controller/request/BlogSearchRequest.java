package com.github.antksk.blog.search.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

import static com.github.antksk.blog.search.controller.request.BlogSearchSortMode.accuracy;
import static com.github.antksk.blog.search.controller.request.BlogSource.kakao;

@Slf4j
@ToString
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class BlogSearchRequest {
    public static final String DEFAULT_BLOG_SOURCE = "kakao";
    public static final String DEFAULT_BLOG_SEARCH_SORT_MODE = "accuracy";

    @Getter
    private final String query;

    private final int currentPage;
    private final int displayResultSize;

    @Getter(AccessLevel.PACKAGE)
    private final BlogSource blogSource;
    private final BlogSearchSortMode blogSearchSortMode;

    public boolean hasBlogSearchApiClientPackageName(String packageName){
        return this.blogSource.hasBlogSearchApiClientPackageName(packageName);
    }

    static BlogSearchRequest ofDefault(String query) {
        return new BlogSearchRequest(query, 1, 10, kakao, accuracy);
    }

    public static BlogSearchRequest fromRequest(String query, String source, Pageable pageable){
        var blogSource = BlogSource.fromString(source);
        var sortMode = getAccuracy(pageable);
        var blogSearchSortMode = BlogSearchSortMode.fromString(sortMode);

        return of(query, pageable.getPageNumber(), pageable.getPageSize(),
                blogSource,
                blogSearchSortMode);
    }

    private static String getAccuracy(Pageable pageable) {
        return pageable.getSort().stream()
                .map(Sort.Order::getProperty)
                .findFirst()
                .orElse(DEFAULT_BLOG_SEARCH_SORT_MODE);
    }

    static BlogSearchRequest of(String query, int page, int size, BlogSource source, BlogSearchSortMode sortMode){
        return new BlogSearchRequest(query, page, size, source, sortMode);
    }

    public Map<String, Object> toParam() {
        return blogSource.toParam(this);
    }

    Map<String, Object> toParam(String currentPageKey, String displayResultSizeKey) {
        return Map.of(
                "query", query,
                currentPageKey, currentPage,
                displayResultSizeKey, displayResultSize,
                "sort", blogSearchSortMode.getBlogSourceToSortMode(blogSource)
        );
    }

}

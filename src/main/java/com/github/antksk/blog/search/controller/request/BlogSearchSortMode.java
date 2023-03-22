package com.github.antksk.blog.search.controller.request;

import com.github.antksk.config.KKB_Global_Constants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static com.github.antksk.blog.search.controller.request.BlogSource.kakao;
import static com.github.antksk.blog.search.controller.request.BlogSource.naver;
import static com.github.antksk.blog.search.controller.request.GeneralEnum.enumsToMap;
import static com.github.antksk.config.KKB_Global_Constants.DEFAULT_BLOG_SEARCH_SORT_MODE;

@Getter
@RequiredArgsConstructor
enum BlogSearchSortMode implements GeneralEnum {
    accuracy(DEFAULT_BLOG_SEARCH_SORT_MODE, Map.of(kakao, DEFAULT_BLOG_SEARCH_SORT_MODE, naver, "sim")),
    recency("recency", Map.of(kakao, "recency", naver, "date"));

    @Getter
    private final String value;
    private final Map<BlogSource, String> sourceMode;
    private static final Map<String, BlogSearchSortMode> mapped = enumsToMap(values(), (t) -> true);

    static BlogSearchSortMode fromString(String sort) {
        return mapped.getOrDefault(sort, accuracy);
    }

    static boolean contains(String blogSearchSortMode) {
        return mapped.containsKey(blogSearchSortMode);
    }

    String getBlogSourceToSortMode(BlogSource source) {
        return sourceMode.get(source);
    }

}

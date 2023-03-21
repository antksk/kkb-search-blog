package com.github.antksk.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {
    blogPopularSearchWord(Constants.BLOG_POPULAR_SEARCH_WORD, 3, 1_000);
    private final String cacheName;
    private final int duration;
    private final int maximumSize;

    public static class Constants {
        public static final String BLOG_POPULAR_SEARCH_WORD = "blogPopularSearchWord";
    }
}

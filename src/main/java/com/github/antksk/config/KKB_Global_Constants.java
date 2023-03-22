package com.github.antksk.config;

import java.time.format.DateTimeFormatter;

public final class KKB_Global_Constants {

    public static final String TABLE_NAME_BLOG_POPULAR_SEARCH_WORD = "t_blog_search_word";

    public static final String BLOG_POPULAR_SEARCH_WORD = "blogPopularSearchWord";
    public static final String DEFAULT_BLOG_SOURCE = "kakao";
    public static final String DEFAULT_BLOG_SEARCH_SORT_MODE = "accuracy";

    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
}

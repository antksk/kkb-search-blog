package com.github.antksk.blog.search.service.external.naver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.antksk.blog.search.service.external.BlogSearchResult;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(exclude = {"name", "contents", "title", "postDate"})
final class Item implements BlogSearchResult {
    private final String name;
    private final String contents;
    private final String title;
    private final String url;
    private final String postDate;


    private static final DateTimeFormatter toFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    @JsonCreator
    static Item fromJson(
            @JsonProperty("bloggername") String blogName,
            @JsonProperty("description") String contents,
            @JsonProperty("title") String title,
            @JsonProperty("link") String url,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
            @JsonProperty("postdate") LocalDate postDate

    ) {
        return new Item(blogName, contents, title, url, postDate.format(toFormatter));
    }
}

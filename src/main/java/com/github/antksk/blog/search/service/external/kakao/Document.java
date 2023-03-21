package com.github.antksk.blog.search.service.external.kakao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.antksk.blog.search.service.external.BlogSearchResult;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(exclude = {"name", "contents", "title", "postDate"})
final class Document implements BlogSearchResult {
    private final String name;
    private final String contents;
    private final String title;
    private final String url;
    private final String postDate;

    @JsonCreator
    static Document fromJson(
            @JsonProperty("blogname") String blogName,
            @JsonProperty("contents") String contents,
            @JsonProperty("title") String title,
            @JsonProperty("url") String url,
            @JsonProperty("datetime") String postDate
    ) {
        return new Document(blogName, contents, title, url, postDate);
    }

    private static final DateTimeFormatter fromFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final DateTimeFormatter toFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    public String getPostDate(){
            return LocalDate.parse(postDate, fromFormatter)
                    .format(toFormatter);
    }

}

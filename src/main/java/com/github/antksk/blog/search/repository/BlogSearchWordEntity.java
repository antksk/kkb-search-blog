package com.github.antksk.blog.search.repository;

import lombok.*;


import javax.persistence.*;

import static com.github.antksk.config.KKB_Global_Constants.TABLE_NAME_BLOG_POPULAR_SEARCH_WORD;


@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = TABLE_NAME_BLOG_POPULAR_SEARCH_WORD)
public class BlogSearchWordEntity extends CreateAndModifyDateTime {
    @Id
    @Column(nullable = false, length = 30)
    private String word;

    private Long count;

    @Version
    private Long version;

    public void modifyCount(long count){
        if(this.count < count) {
            this.count = count;
        }
    }

    @Builder
    BlogSearchWordEntity(String word, Long count, Long version) {
//        this.id = id;
        this.word = word;
        this.count = count;
        this.version = version;
    }
}

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long id;

    @Column(name = "word", nullable = false, length = 50)
    private String word;

    private Long count;

    public void modifyCount(long count){
        if(this.count < count) {
            this.count = count;
        }
    }

    @Builder
    BlogSearchWordEntity(Long id, String word, Long count) {
        this.id = id;
        this.word = word;
        this.count = count;
    }
}

package com.github.antksk.blog.search.service;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Optional;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = "count")
@ToString
public final class RankingWord {
    private final String word;
    private final long count;

    private static RankingWord empty = new RankingWord("", 0L);


    static RankingWord from(String word, long count) {
        return new RankingWord(word, count);
    }

    public static RankingWord fromCache(Object key, Object value){
        String word = objToStringKey(key);
        long count = objToLongValue(value);
        if("".equals(word)){
            return empty;
        }
        return new RankingWord(word, count);
    }

    private static Long objToLongValue(Object value) {
        return Optional.ofNullable(value)
                .filter(o -> o instanceof Long)
                .map(o -> ((Long) o).longValue())
                .orElse(0L);
    }

    private static String objToStringKey(Object key) {
        return Optional.ofNullable(key)
                .filter(o -> o instanceof String)
                .map(Object::toString)
                .orElse("");
    }

    boolean isNotEmpty(){
        return empty != this;
    }

    public RankingWord newRankingWord(long newCount){
        if(this.count > newCount){
            return new RankingWord(word, this.count);
        }
        return new RankingWord(word, newCount);
    }

}

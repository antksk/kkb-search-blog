package com.github.antksk.blog.search.service;

import com.github.antksk.blog.search.repository.BlogSearchKeywordRepository;
import com.github.antksk.blog.search.repository.BlogSearchWordEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.github.antksk.config.KKB_Global_Constants.BLOG_POPULAR_SEARCH_WORD;
import static java.util.stream.Collectors.toUnmodifiableList;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogPopularSearchRankingService {
    private final CacheManager cacheManager;

    private final BlogSearchKeywordRepository blogSearchKeywordRepository;

    private Cache getWordCache(){
        return cacheManager.getCache(BLOG_POPULAR_SEARCH_WORD);
    }

    long getCurrentWordCount(String word){
        Cache cacheWord = getWordCache();
        return Optional.ofNullable(cacheWord.get(word, Long.class))
                .orElse(0L);
    }

    long incrementWordCount(String word){
        long count = getCurrentWordCount(word);
        getWordCache().put(word, ++count);
        log.info("## cache time => word : {}, count: {}", word, count);
        return count;
    }

    @Transactional
    void storedRanking(RankingWord newRankingWord) {

        try {
            String word = newRankingWord.getWord();
            long count = newRankingWord.getCount();

            BlogSearchWordEntity wordEntity = Optional.ofNullable(blogSearchKeywordRepository.findByWord(word))
                    .orElse(BlogSearchWordEntity.builder()
                            .word(word)
                            .count(count)
                            .build());

            wordEntity.modifyCount(count);

            blogSearchKeywordRepository.save(wordEntity);
        }catch (Exception e){
            log.error("count concurrency : ", e);
        }
    }

    @Transactional(readOnly = true)
    public List<RankingWord> getRankingWords(){
        return blogSearchKeywordRepository.findTop10By(Sort.by(Sort.Order.desc("count"), Sort.Order.desc("word")))
                .stream()
                .peek(e->log.debug("{}", e))
                .map(e->RankingWord.from(e.getWord(), e.getCount()))
                .collect(toUnmodifiableList());
    }




}

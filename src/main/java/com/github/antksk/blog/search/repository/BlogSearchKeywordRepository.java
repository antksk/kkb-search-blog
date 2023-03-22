package com.github.antksk.blog.search.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface BlogSearchKeywordRepository extends JpaRepository<BlogSearchWordEntity, Long> {
    @Lock(LockModeType.NONE)
    BlogSearchWordEntity findByWord(String word);
    List<BlogSearchWordEntity> findTop10By(Sort sort);
}

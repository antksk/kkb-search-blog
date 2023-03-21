package com.github.antksk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.github.antksk.blog.search.repository")
//@EnableJpaAuditing
@Configuration
class JpaConfig {
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Bean
//    public JPAQueryFactory jpaQueryFactory() {
//        return new JPAQueryFactory(entityManager);
//    }
}

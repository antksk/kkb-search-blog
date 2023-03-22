package com.github.antksk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.github.antksk.blog.search.repository")
@Configuration
class JpaConfig {

}

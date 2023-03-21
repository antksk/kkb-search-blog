package com.github.antksk.blog.search.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestControllerAdvice
class BlogSearchControllerAdvice {
    // FeignException
    @ExceptionHandler(Exception.class)
    public RepresentationModel<?> globalError(Exception e) {
        log.error("ExceptionHandler -> Exception", e);
        Link link = linkTo(methodOn(BlogSearchController.class)
                .search("", "kakao",  PageRequest.of(1,10))).withSelfRel();
        return RepresentationModel.of(Map.of("error", e.getLocalizedMessage()), Set.of(link));
    }
}

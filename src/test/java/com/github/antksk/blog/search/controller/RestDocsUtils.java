package com.github.antksk.blog.search.controller;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

final class RestDocsUtils {
    static ParameterDescriptor parameter(String name, String description){
        return parameter(false, name,description);
    }

    static ParameterDescriptor parameter(boolean optional, String name, String description){
        if(optional) {
            return parameterWithName(name).optional().description(description);
        }
        return parameterWithName(name).description(description);
    }

    static List<FieldDescriptor> fields(List<FieldDescriptor>... a){
        return Stream.of(a)
                .flatMap(Collection::stream)
                .collect(toUnmodifiableList());
    }

    static FieldDescriptor json(JsonFieldType type, String path, String description) {
        return fieldWithPath(path).type(type).description(description);
    }
}

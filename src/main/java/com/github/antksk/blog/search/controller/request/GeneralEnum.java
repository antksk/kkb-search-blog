package com.github.antksk.blog.search.controller.request;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toUnmodifiableMap;

interface GeneralEnum {

    String getValue();

    static <V extends GeneralEnum> Map<String, V> enumsToMap(V[] enums, Predicate<V> predicate) {
        return stream(enums)
                .filter(predicate)
                .collect(toUnmodifiableMap(V::getValue, Function.identity()));
    }
}

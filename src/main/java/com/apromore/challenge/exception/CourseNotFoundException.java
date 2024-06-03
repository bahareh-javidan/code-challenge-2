package com.apromore.challenge.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(Long id) {
        super("Course not found with id: " + id);
    }

    public CourseNotFoundException(Set<Long> ids) {
        super("Courses not found with ids: " + ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
    }
}

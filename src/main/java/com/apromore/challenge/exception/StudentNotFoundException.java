package com.apromore.challenge.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Long id) {
        super("Student not found with id: " + id);
    }
}
package com.apromore.challenge.controller;

import com.apromore.challenge.dto.StudentRequestDTO;
import com.apromore.challenge.model.Course;
import com.apromore.challenge.model.Student;
import com.apromore.challenge.repository.CourseRepository;
import com.apromore.challenge.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerIntegrationTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        final Course course = new Course(1L, "Art", "C1");
        courseRepository.save(course);
        studentRepository.save(new Student(1L, "Alice", 20, Set.of(course)));
    }

    @Test
    void testGetAllStudents() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    void testCreateStudent() throws Exception {
        StudentRequestDTO studentRequestDTO = StudentRequestDTO.builder().name("Alice").age(20).build();

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Alice")))
                .andExpect(jsonPath("$.age", is(20)));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Long studentId = 1L;
        StudentRequestDTO studentRequestDTO = StudentRequestDTO.builder().name("Bob").age(22).build();

        mockMvc.perform(put("/api/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Bob")))
                .andExpect(jsonPath("$.age", is(22)));
    }

    @Test
    void testDeleteStudent() throws Exception {
        Long studentId = 1L;

        mockMvc.perform(delete("/api/students/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(content().string("Student deleted"));
    }

    @Test
    void testEnrollStudentInCourse() throws Exception {
        Long studentId = 1L;
        Set<Long> courseIds = Collections.singleton(1L);

        mockMvc.perform(post("/api/students/{studentId}/courses", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseIds)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.courseIds", hasItem(1)));
    }
}

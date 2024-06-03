package com.apromore.challenge.controller;

import com.apromore.challenge.dto.StudentRequestDTO;
import com.apromore.challenge.dto.StudentResponseDTO;
import com.apromore.challenge.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody @Valid StudentRequestDTO studentRequestDTO) {
        return ResponseEntity.ok(studentService.saveStudent(studentRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @RequestBody StudentRequestDTO studentRequestDTO) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted");
    }

    @GetMapping("/{studentId}/courses")
    public StudentResponseDTO getStudentCourses(@PathVariable Long studentId) {
        return studentService.getStudentCourses(studentId);
    }

    @PostMapping("/{studentId}/courses")
    public ResponseEntity<StudentResponseDTO> enrollStudentInCourse(@PathVariable Long studentId, @RequestBody Set<Long> courseIds) {
        StudentResponseDTO enrolledStudent = studentService.enrollStudentInCourse(studentId, courseIds);
        return ResponseEntity.ok(enrolledStudent);
    }
}

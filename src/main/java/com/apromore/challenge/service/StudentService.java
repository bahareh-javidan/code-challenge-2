package com.apromore.challenge.service;

import com.apromore.challenge.dto.StudentRequestDTO;
import com.apromore.challenge.dto.StudentResponseDTO;
import com.apromore.challenge.exception.CourseNotFoundException;
import com.apromore.challenge.exception.StudentNotFoundException;
import com.apromore.challenge.mapper.StudentMapper;
import com.apromore.challenge.model.Course;
import com.apromore.challenge.model.Student;
import com.apromore.challenge.repository.CourseRepository;
import com.apromore.challenge.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private static void validateCourseIds(Set<Long> courseIds, List<Course> courseList) {
        if (courseList.size() != courseIds.size()) {
            // Some courses were not found, determine which ones are missing
            Set<Long> foundIds = courseList.stream().map(Course::getId).collect(Collectors.toSet());
            Set<Long> missingIds = new HashSet<>(courseIds);
            missingIds.removeAll(foundIds);
            throw new CourseNotFoundException(missingIds);
        }
    }

    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toResponseDTO)
                .toList();
    }

    public StudentResponseDTO getStudentById(Long id) {
        return studentRepository.findById(id).map(StudentMapper::toResponseDTO).orElseThrow(() -> new StudentNotFoundException(id));
    }

    public StudentResponseDTO saveStudent(StudentRequestDTO studentRequestDTO) {
        Student saved = studentRepository.save(StudentMapper.toEntity(studentRequestDTO));
        return StudentMapper.toResponseDTO(saved);
    }

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

        Set<Course> courses = new HashSet<>();
        if (studentRequestDTO.getCourseIds() != null) {
            List<Course> courseList = courseRepository.findAllById(studentRequestDTO.getCourseIds());
            validateCourseIds(studentRequestDTO.getCourseIds(), courseList);
            courses = new HashSet<>(courseList);
        }

        student.setName(studentRequestDTO.getName());
        student.setAge(studentRequestDTO.getAge());
        student.setCourses(courses);
        Student updatedStudent = studentRepository.save(student);
        return StudentMapper.toDetailResponseDTO(updatedStudent);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    public StudentResponseDTO enrollStudentInCourse(Long studentId, Set<Long> courseIds) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        List<Course> courses = courseRepository.findAllById(courseIds);
        validateCourseIds(courseIds, courses);

        Set<Course> newCourses = new HashSet<>(student.getCourses());
        newCourses.addAll(courses);
        student.setCourses(newCourses);

        Student response = studentRepository.save(student);
        StudentResponseDTO responseDTO = StudentMapper.toResponseDTO(response);
        responseDTO.setCourseIds(student.getCourses().stream().map(Course::getId).collect(Collectors.toSet()));
        return responseDTO;
    }

    public StudentResponseDTO getStudentCourses(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
        return StudentMapper.toDetailResponseDTO(student);
    }
}

package com.apromore.challenge.service;

import com.apromore.challenge.dto.StudentRequestDTO;
import com.apromore.challenge.dto.StudentResponseDTO;
import com.apromore.challenge.exception.CourseNotFoundException;
import com.apromore.challenge.model.Course;
import com.apromore.challenge.model.Student;
import com.apromore.challenge.repository.CourseRepository;
import com.apromore.challenge.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private StudentService classUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(
                new Student(1L, "John", 20, new HashSet<>()),
                new Student(2L, "Jane", 22, new HashSet<>())
        ));

        List<StudentResponseDTO> students = classUnderTest.getAllStudents();

        assertEquals(2, students.size());
        assertEquals("John", students.get(0).getName());
        assertEquals(20, students.get(0).getAge());
        assertEquals("Jane", students.get(1).getName());
        assertEquals(22, students.get(1).getAge());
    }

    @Test
    void getStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L, "John", 20, new HashSet<>())));

        StudentResponseDTO student = classUnderTest.getStudentById(1L);

        assertEquals("John", student.getName());
        assertEquals(20, student.getAge());
        assertNull(student.getCourseIds());
    }

    @Test
    void saveStudent() {
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StudentRequestDTO studentRequestDTO = StudentRequestDTO.builder().name("John").age(20).build();
        StudentResponseDTO savedStudent = classUnderTest.saveStudent(studentRequestDTO);

        assertEquals("John", savedStudent.getName());
        assertEquals(20, savedStudent.getAge());
    }

    @Test
    void updateStudent() {
        Student existingStudent = new Student(1L, "John", 20, new HashSet<>());
        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));

        Set<Long> courseIds = new HashSet<>(Arrays.asList(1L, 2L));
        List<Course> courses = Arrays.asList(
                new Course(1L, "Math", "C1"),
                new Course(2L, "Science", "C2")
        );
        when(courseRepository.findAllById(courseIds)).thenReturn(courses);

        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StudentRequestDTO studentRequestDTO = StudentRequestDTO.builder()
                .name("Tom")
                .age(21)
                .courseIds(courseIds)
                .build();

        StudentResponseDTO updatedStudent = classUnderTest.updateStudent(1L, studentRequestDTO);

        assertEquals("Tom", updatedStudent.getName());
        assertEquals(21, updatedStudent.getAge());
        assertEquals(2, updatedStudent.getCourseIds().size());
        assertTrue(updatedStudent.getCourseIds().contains(1L));
        assertTrue(updatedStudent.getCourseIds().contains(2L));
    }

    @Test
    void deleteStudent() {
        classUnderTest.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testStudentEnrolmentForNewStudent() {
        Student existingStudent = new Student(1L, "John", 8, new HashSet<>());
        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));

        Set<Long> courseIds = new HashSet<>(Arrays.asList(1L, 2L));
        List<Course> courses = Arrays.asList(
                new Course(1L, "Math", "C1"),
                new Course(2L, "Science", "C2")
        );
        when(courseRepository.findAllById(courseIds)).thenReturn(courses);
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StudentResponseDTO enrolledStudent = classUnderTest.enrollStudentInCourse(1L, courseIds);

        assertEquals(2, enrolledStudent.getCourseIds().size());
        assertTrue(enrolledStudent.getCourseIds().contains(1L));
        assertTrue(enrolledStudent.getCourseIds().contains(2L));
    }

    @Test
    void testStudentEnrolmentForPreExistingStudent() {
        Course c1 = new Course(1L, "Art", "C1");

        Student existingStudent = new Student(1L, "John", 8, Set.of(c1));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));

        Set<Long> courseIds = new HashSet<>(Arrays.asList(1L, 2L));
        List<Course> courses = Arrays.asList(
                new Course(2L, "Math", "C2"),
                new Course(3L, "Science", "C3")
        );
        when(courseRepository.findAllById(courseIds)).thenReturn(courses);
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StudentResponseDTO enrolledStudent = classUnderTest.enrollStudentInCourse(1L, courseIds);

        assertEquals(3, enrolledStudent.getCourseIds().size());
        assertTrue(enrolledStudent.getCourseIds().contains(1L));
        assertTrue(enrolledStudent.getCourseIds().contains(2L));
        assertTrue(enrolledStudent.getCourseIds().contains(3L));
    }

    @Test
    void testStudentEnrolmentForInvalidCourse() {
        Student existingStudent = new Student(1L, "John", 8, new HashSet<>());
        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));

        Set<Long> courseIds = Set.of(1L);
        when(courseRepository.findAllById(courseIds)).thenReturn(List.of());
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Assertions.assertThrows(CourseNotFoundException.class, () -> classUnderTest.enrollStudentInCourse(1L, courseIds));
    }
}

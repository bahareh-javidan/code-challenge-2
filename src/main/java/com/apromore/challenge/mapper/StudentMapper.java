package com.apromore.challenge.mapper;

import com.apromore.challenge.dto.StudentRequestDTO;
import com.apromore.challenge.dto.StudentResponseDTO;
import com.apromore.challenge.model.Course;
import com.apromore.challenge.model.Student;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentMapper {

    public static StudentResponseDTO toResponseDTO(Student student) {
        return StudentResponseDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .age(student.getAge())
                .build();
    }

    public static StudentResponseDTO toDetailResponseDTO(Student student) {
        StudentResponseDTO dto = toResponseDTO(student);
        dto.setCourseIds(CollectionUtils.emptyIfNull(student.getCourses()).stream().map(Course::getId).collect(Collectors.toSet()));
        return dto;
    }

    public static Student toEntity(StudentRequestDTO studentRequestDTO) {
        Student student = new Student();
        student.setName(studentRequestDTO.getName());
        student.setAge(studentRequestDTO.getAge());
        return student;
    }
}

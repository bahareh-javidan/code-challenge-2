package com.apromore.challenge.mapper;

import com.apromore.challenge.dto.CourseRequestDTO;
import com.apromore.challenge.dto.CourseResponseDTO;
import com.apromore.challenge.model.Course;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseMapper {

    public static CourseRequestDTO toRequestDto(Course course) {
        return CourseRequestDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .code(course.getCode())
                .build();
    }

    public static CourseResponseDTO toResponseDto(Course course) {
        return CourseResponseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .code(course.getCode())
                .build();
    }

    public static Course toEntity(CourseRequestDTO dto) {
        Course course = new Course();
        course.setId(dto.getId());
        course.setName(dto.getName());
        course.setCode(dto.getCode());
        return course;
    }
}
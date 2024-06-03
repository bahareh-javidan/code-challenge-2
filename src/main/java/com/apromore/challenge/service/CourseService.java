package com.apromore.challenge.service;

import com.apromore.challenge.dto.CourseRequestDTO;
import com.apromore.challenge.dto.CourseResponseDTO;
import com.apromore.challenge.exception.CourseNotFoundException;
import com.apromore.challenge.mapper.CourseMapper;
import com.apromore.challenge.model.Course;
import com.apromore.challenge.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(CourseMapper::toResponseDto).toList();
    }

    public CourseResponseDTO getCourseById(Long id) {
        return courseRepository.findById(id).map(CourseMapper::toResponseDto).orElseThrow(() -> new CourseNotFoundException(id));
    }

    public CourseResponseDTO saveCourse(CourseRequestDTO courseRequestDTO) {
        Course course = courseRepository.save(CourseMapper.toEntity(courseRequestDTO));
        return CourseMapper.toResponseDto(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

}

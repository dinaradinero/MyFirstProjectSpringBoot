package org.example.myfirstproject.service;


import org.example.myfirstproject.dto.courseDto.CourseRequestDto;
import org.example.myfirstproject.dto.courseDto.CourseResponseDto;
import org.example.myfirstproject.entity.Course;
import org.example.myfirstproject.repository.course.CourseRepository;
import org.example.myfirstproject.service.exception.AlreadyExistException;
import org.example.myfirstproject.service.util.Converter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private Converter converter;

    @InjectMocks
    private CourseService courseService;


    @Test
    void addCourse_shouldAddAndReturnCourse_whenNewValidCourse() {
        // given
        CourseRequestDto requestDto = new CourseRequestDto();
        requestDto.setCourseName("Java Basics");
        Course savedCourse = new Course();
        savedCourse.setCourseName("Java Basics");
        savedCourse.setCourseStudents(new ArrayList<>());

        when(courseRepository.findCourseByCourseName("Java Basics"))
                .thenReturn(Collections.emptyList());

        when(courseRepository.save(any(Course.class)))
                .thenReturn(savedCourse);

        CourseResponseDto expectedResponse = new CourseResponseDto();
        expectedResponse.setCourseName("Java Basics");
        when(converter.courseToDto(savedCourse)).thenReturn(expectedResponse);

        // when
        CourseResponseDto actualResponse = courseService.addCourse(requestDto);

        // then
        assertEquals("Java Basics", actualResponse.getCourseName());
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void addCourse_shouldThrowException_whenCourseAlreadyExists() {
        // given
        CourseRequestDto requestDto = new CourseRequestDto();
        requestDto.setCourseName("Java Basics");
        Course existingCourse = new Course();
        existingCourse.setCourseName("Java Basics");

        when(courseRepository.findCourseByCourseName("Java Basics"))
                .thenReturn(List.of(existingCourse));

        // then
        assertThrows(AlreadyExistException.class, () -> {
            courseService.addCourse(requestDto);
        });
    }


}

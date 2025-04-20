package org.example.myfirstproject.service;

import org.example.myfirstproject.dto.GeneralResponse;
import org.example.myfirstproject.dto.courseDto.CourseRequestDto;
import org.example.myfirstproject.dto.courseDto.CourseResponseDto;
import org.example.myfirstproject.dto.studentDto.StudentRequestDto;
import org.example.myfirstproject.dto.studentDto.StudentResponseDto;
import org.example.myfirstproject.entity.Course;
import org.example.myfirstproject.entity.Student;
import org.example.myfirstproject.repository.course.CoursesRepository;
import org.example.myfirstproject.repository.course.CoursesRepositoryInMemory;
import org.example.myfirstproject.repository.student.StudentRepositoryInMemory;
import org.example.myfirstproject.service.util.Converter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private CoursesRepositoryInMemory coursesRepository = new CoursesRepository();
    private Converter converter;

    public CourseService(CoursesRepository coursesRepository, Converter converter) {
        this.coursesRepository = coursesRepository;
        this.converter = converter;
    }

    //add

    public CourseResponseDto addCourse (CourseRequestDto requestDto) {
        Course course = new Course();
        course.setCourseName(requestDto.getCourseName());
        course.setCourseStudents(new ArrayList<>());

        Course courseAfterSafe = coursesRepository.add(course);

        return converter.courseToDto(courseAfterSafe);
    }

    //FindAllCourses

    public List<CourseResponseDto> findAllCourses (){
        return coursesRepository.findAll().stream()
                .map(course -> converter.courseToDto(course))
                .toList();
    }


    //FindById

    public Optional<CourseResponseDto> findCourseById (Integer idForSearch){
        return coursesRepository.findById(idForSearch).stream()
                .map(course -> converter.courseToDto(course))
                .findFirst();
    }

    //FindByName

    public GeneralResponse<List<CourseResponseDto>> findCourseByName (String courseNameForSearch){
        List<CourseResponseDto> courseResponseDtos = coursesRepository.findByName(courseNameForSearch).stream()
                .map(course -> converter.courseToDto(course))
                .toList();

        return new GeneralResponse<>(courseResponseDtos);
    }


    //findStudentsByCourseName

    public GeneralResponse<List<StudentResponseDto>> findAllStudentsByCourseName (String courseNameForSearch) {
        List<StudentResponseDto> studentResponseDtos = coursesRepository.findStudentsByCourseName(courseNameForSearch).stream()
                .map(student -> converter.studentToDto(student))
                .toList();

        return new GeneralResponse<>(studentResponseDtos);
    }

}

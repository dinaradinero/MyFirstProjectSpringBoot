package org.example.myfirstproject.service;

import lombok.AllArgsConstructor;
import org.example.myfirstproject.dto.GeneralResponse;
import org.example.myfirstproject.dto.courseDto.CourseRequestDto;
import org.example.myfirstproject.dto.courseDto.CourseResponseDto;
import org.example.myfirstproject.dto.studentDto.StudentRequestDto;
import org.example.myfirstproject.dto.studentDto.StudentResponseDto;
import org.example.myfirstproject.entity.Course;
import org.example.myfirstproject.entity.Student;
import org.example.myfirstproject.repository.course.CourseRepository;

import org.example.myfirstproject.service.exception.AlreadyExistException;
import org.example.myfirstproject.service.exception.NotFoundException;
import org.example.myfirstproject.service.exception.ValidationException;
import org.example.myfirstproject.service.util.Converter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository coursesRepository;
    private Converter converter;

//    public CourseService(CoursesRepository coursesRepository, Converter converter) {
//        this.coursesRepository = coursesRepository;
//        this.converter = converter;
//    }

    //add

    public CourseResponseDto addCourse (CourseRequestDto requestDto) {
        if (requestDto == null) {
            throw new ValidationException("Course request cannot be null");
        }

        String courseName = requestDto.getCourseName();
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new ValidationException("Course name cannot be null or empty");
        }

        String trimmedName = courseName.trim();

        List<CourseResponseDto> existingCourses = findCourseByName(trimmedName);
        if (!existingCourses.isEmpty()) {
            throw new AlreadyExistException("Course with name '" + trimmedName + "' already exists");
        }

        Course course = converter.courseFromDto(requestDto);
        course.setCourseStudents(new ArrayList<>()); // Инициализируем список студентов

        // Сохранение
        Course savedCourse = coursesRepository.save(course);

        // Конвертация в DTO
        return converter.courseToDto(savedCourse);

    }

    //FindAllCourses

    public List<CourseResponseDto> findAllCourses (){
        return coursesRepository.findAll().stream()
                .map(course -> converter.courseToDto(course))
                .toList();
    }


    //FindById

    public Optional<CourseResponseDto> findCourseById (Integer idForSearch){
        if (idForSearch == null) {
            throw new ValidationException("Course ID can not be null.");
        }
        if (idForSearch <= 0) {
            throw new ValidationException("Course ID can not be negative");
        }

        Optional<CourseResponseDto> courseResponseDto = coursesRepository.findById(idForSearch).stream()
                .map(course -> converter.courseToDto(course))
                .findFirst();

        if(courseResponseDto.isPresent()){
            return courseResponseDto;
        } else {
            throw new NotFoundException("Course with id " + idForSearch + " not found");
        }
    }

    //FindByName

    public List<CourseResponseDto> findCourseByName (String courseNameForSearch){
        if (courseNameForSearch == null) {
            throw new ValidationException("Course name can not be null");
        }
        String trimmedCourseName = courseNameForSearch.trim();
        if(trimmedCourseName.isEmpty()){
            throw new ValidationException("Course name can not be empty");
        }

        if(trimmedCourseName.length() < 3){
            throw new ValidationException("Course name must be at least 3 characters long");
        }

        if (!trimmedCourseName.matches("[a-zA-Z0-9\\s]+")) {
            throw new ValidationException("Course name can only contain letters, numbers, and spaces");
        }


        List<CourseResponseDto> courseResponseDtos = coursesRepository.findCourseByCourseName(trimmedCourseName).stream()
                .map(course -> converter.courseToDto(course))
                .toList();

        return courseResponseDtos;
    }


    //findStudentsByCourseName

    public List<StudentResponseDto> findAllStudentsByCourseName (String courseNameForSearch) {
        if (courseNameForSearch == null) {
            throw new ValidationException("Course name can not be null");
        }
        String trimmedCourseName = courseNameForSearch.trim();

        if(trimmedCourseName.isEmpty()){
            throw new ValidationException("Course name can not be empty");
        }

        if(trimmedCourseName.length() < 3){
            throw new ValidationException("Course name must be at least 3 characters long");
        }

        if (!trimmedCourseName.matches("[a-zA-Z0-9\\s]+")) {
            throw new ValidationException("Course name can only contain letters, numbers");
        }

        List <StudentResponseDto> studentResponseDtos = coursesRepository.findCourseByCourseName(trimmedCourseName).stream()
                .flatMap(course -> course.getCourseStudents().stream())
                .map(student -> converter.studentToDto(student))
                .toList();

        return studentResponseDtos;
    }

}

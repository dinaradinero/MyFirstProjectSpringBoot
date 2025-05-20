package org.example.myfirstproject.controller;


import lombok.RequiredArgsConstructor;
import org.example.myfirstproject.dto.GeneralResponse;
import org.example.myfirstproject.dto.courseDto.CourseRequestDto;
import org.example.myfirstproject.dto.courseDto.CourseResponseDto;
import org.example.myfirstproject.dto.studentDto.StudentResponseDto;
import org.example.myfirstproject.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @PostMapping
    public CourseResponseDto addCourse (@RequestBody CourseRequestDto requestDto) {
        return service.addCourse(requestDto);
    }
    @GetMapping
    public List<CourseResponseDto> findAllCourses (){
        return service.findAllCourses();
    }

    @GetMapping("/{id}")
    public Optional<CourseResponseDto> findCourseById (@PathVariable Integer id){
        return service.findCourseById(id);
    }

    @GetMapping("/search_by_name/{name}")
    public List<CourseResponseDto> findCourseByName (@PathVariable String name){
        return service.findCourseByName(name);
    } //localhost:8080/api/courses/search_by_name/Java Pro


    @GetMapping("/search/students_by_course/{name}")
    public List<StudentResponseDto> findAllStudentsByCourseName (@PathVariable String name) {
        return service.findAllStudentsByCourseName(name);
    } //localhost:8080/api/courses/search/students_by_course/Java Pro


}

package org.example.myfirstproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.myfirstproject.dto.GeneralResponse;
import org.example.myfirstproject.dto.courseDto.CourseResponseDto;
import org.example.myfirstproject.dto.studentDto.StudentRequestDto;
import org.example.myfirstproject.dto.studentDto.StudentResponseDto;
import org.example.myfirstproject.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;


    @PostMapping
    public StudentResponseDto addStudent (@RequestBody StudentRequestDto requestDto) {
        return service.addNewStudent(requestDto);
    }


    @GetMapping
    public List<StudentResponseDto> findAllStudent (){
        return service.findAllStudents();
    }

    @GetMapping("/{id}")
    public Optional<StudentResponseDto> findStudentById (@PathVariable Integer id){
        return service.findById(id);
    }

    @GetMapping("/search/name")
    public GeneralResponse<List<StudentResponseDto>> findStudentsByName (@RequestParam String studentName){
        return service.findByName(studentName);
    }

    @GetMapping("/search/date")
    public GeneralResponse<List<StudentResponseDto>> findByRegistrationDates (@RequestParam LocalDate start, @RequestParam LocalDate finish){
        return service.findByRegistrationDates(start, finish);
    }

    @GetMapping("/search/mark")
    public GeneralResponse<List<StudentResponseDto>> findByAverageMark (@RequestParam Integer min, @RequestParam Integer max){
        return service.findByAverageMark(min, max);
    }

    @GetMapping("/{id}/courses")
    public GeneralResponse<List<CourseResponseDto>> findCoursesByStudentId (@PathVariable Integer id){
        return service.findCoursesByStudentId(id);
    }

    @GetMapping("/courses_student_name")
    public GeneralResponse<List<CourseResponseDto>> findCoursesByStudentName (@RequestBody String nameForSearch){
        return service.findCoursesByStudentName(nameForSearch);
    }

    @PatchMapping("/{idStudent}/update_mark")
    public Optional<StudentResponseDto> addAverageMark (@PathVariable Integer idStudent, @RequestBody Integer markToAdd) {
        return service.addAverageMark(idStudent, markToAdd);
    }


    @PatchMapping("/{idStudent}/add_course")
    public Optional<StudentResponseDto> addCourseToStudent(@PathVariable Integer idStudent, @RequestBody Integer idCourse) {
        return service.addCourseToStudent(idStudent, idCourse);

    }

    @DeleteMapping("/{id}")
    public GeneralResponse<Optional<StudentResponseDto>> deleteStudentById(@PathVariable Integer id) {
        return service.deleteStudentById(id);
    }



}

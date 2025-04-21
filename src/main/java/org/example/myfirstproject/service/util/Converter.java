package org.example.myfirstproject.service.util;

import org.example.myfirstproject.dto.courseDto.CourseRequestDto;
import org.example.myfirstproject.dto.courseDto.CourseResponseDto;
import org.example.myfirstproject.dto.courseDto.CourseSimpleDto;
import org.example.myfirstproject.dto.studentDto.StudentRequestDto;
import org.example.myfirstproject.dto.studentDto.StudentResponseDto;
import org.example.myfirstproject.dto.studentDto.StudentSimpleDto;
import org.example.myfirstproject.entity.Course;
import org.example.myfirstproject.entity.Student;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class Converter {

    public Student studentFromDto (StudentRequestDto dto) {
        Student student = new Student();
        student.setStudentLastName(dto.getLastName());
        student.setStudentFirstName(dto.getFirstName());
        student.setStudentAverageMark(dto.getStudentAverageMark());
        return student;
    }

    public StudentResponseDto studentToDto (Student student) {
        StudentResponseDto dto = new StudentResponseDto();
        dto.setId(student.getStudentId());
        dto.setLastName(student.getStudentLastName());
        dto.setFirstName(student.getStudentFirstName());
        dto.setRegistrationDate(student.getStudentRegistrationDate());
        dto.setAverageMark(student.getStudentAverageMark());
        //dto.setCourses(student.getAllStudentCourses());

        if (student.getAllStudentCourses() != null) {
            dto.setCourses(student.getAllStudentCourses().stream()
                    .map(course -> {
                        CourseSimpleDto simpleDto = new CourseSimpleDto();
                        simpleDto.setCourseId(course.getCourseId());
                        simpleDto.setCourseName(course.getCourseName());
                        return simpleDto;
                    })
                    .collect(Collectors.toList()));
        }

        return dto;

    }

    public Course courseFromDto (CourseRequestDto dto) {
        Course course = new Course();
        course.setCourseName(dto.getCourseName());

        return course;
    }

    public CourseResponseDto courseToDto (Course course) {
        CourseResponseDto dto = new CourseResponseDto();
        dto.setCourseId(course.getCourseId());
        dto.setCourseName(course.getCourseName());
        //dto.setCourseStudents(course.getCourseStudents());

        if (course.getCourseStudents() != null) {
            dto.setCourseStudents(course.getCourseStudents().stream()
                    .map(student -> {
                        StudentSimpleDto simpleDto = new StudentSimpleDto();
                        simpleDto.setId(student.getStudentId());
                        simpleDto.setLastName(student.getStudentLastName());
                        simpleDto.setFirstName(student.getStudentFirstName());
                        return simpleDto;
                    })
                    .collect(Collectors.toList()));
        }

        return dto;
    }


}

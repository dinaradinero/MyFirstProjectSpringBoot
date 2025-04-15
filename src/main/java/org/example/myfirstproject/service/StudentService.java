package org.example.myfirstproject.service;

import org.example.myfirstproject.dto.GeneralResponse;
import org.example.myfirstproject.dto.courseDto.CourseResponseDto;
import org.example.myfirstproject.dto.studentDto.StudentRequestDto;
import org.example.myfirstproject.dto.studentDto.StudentResponseDto;
import org.example.myfirstproject.entity.Course;
import org.example.myfirstproject.entity.Student;
import org.example.myfirstproject.repository.student.StudentRepository;
import org.example.myfirstproject.repository.student.StudentRepositoryInMemory;
import org.example.myfirstproject.service.util.Converter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepositoryInMemory repository;
    private Converter converter;


    public StudentService(StudentRepositoryInMemory repository, Converter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public StudentResponseDto addNewStudent(StudentRequestDto request) {
        Student newStudentForAdd = new Student();
        newStudentForAdd.setStudentFirstName(request.getFirstName());
        newStudentForAdd.setStudentLastName(request.getLastName());

        Student studentAfterSave = repository.add(newStudentForAdd);

        return new StudentResponseDto(studentAfterSave.getStudentId(),
                studentAfterSave.getStudentLastName(),
                studentAfterSave.getStudentFirstName(),
                studentAfterSave.getStudentRegistrationDate(),
                studentAfterSave.getStudentAverageMark(),
                studentAfterSave.getAllStudentCourses()
        );
    }
    //findall
    public List<StudentResponseDto> findAllStudents () {
        return repository.findAll().stream()
                .map(student -> converter.studentToDto(student))
                .toList();
    }
    //findById
    public Optional<StudentResponseDto> findById (Integer studentId){
        return repository.findById(studentId).stream()
                .map(student -> converter.studentToDto(student))
                .findFirst();
    }


    //findByName
    public GeneralResponse<List<StudentResponseDto>> findByName (String studentName){
        List<StudentResponseDto> studentResponseDtos = repository.findByName(studentName).stream()
                .map(student -> converter.studentToDto(student))
                .toList();

        return new GeneralResponse<>(studentResponseDtos);
    }

    //findByRegistrationDates
    public GeneralResponse<List<StudentResponseDto>> findByRegistrationDates (LocalDate start, LocalDate finish) {
        List<StudentResponseDto> studentResponseDtos = repository.findByRegistrationDates(start, finish).stream()
                .map(student -> converter.studentToDto(student))
                .toList();
        return new GeneralResponse<>(studentResponseDtos);
    }


    //findByAverageMark
    public GeneralResponse<List<StudentResponseDto>> findByAverageMark (Integer min, Integer max) {
        List<StudentResponseDto> studentResponseDtos = repository.findByAverageMarks(min, max).stream()
                .map(student -> converter.studentToDto(student))
                .toList();
        return new GeneralResponse<>(studentResponseDtos);
    }

    public GeneralResponse<List<CourseResponseDto>> findCoursesByStudentId (Integer idForSearch){
        List<CourseResponseDto> courseResponseDtos = repository.findCoursesByStudentId(idForSearch).stream()
                .map(course -> converter.courseToDto(course))
                .toList();

        return new GeneralResponse<>(courseResponseDtos);
    };

    public GeneralResponse<List<CourseResponseDto>> findCoursesByStudentName (String nameForSearch){
        List<CourseResponseDto> courseResponseDtos = repository.findCoursesByStudentName(nameForSearch).stream()
                .map(course -> converter.courseToDto(course))
                .toList();

        return new GeneralResponse<>(courseResponseDtos);

    };


    //addAverageMark
    public Optional<StudentResponseDto> addAverageMark (Integer idStudent, Integer markToAdd) {
        return repository.addAverageMark(idStudent, markToAdd).stream()
                .map(student -> converter.studentToDto(student))
                .findFirst();
    }


    //addCourseToStudent

    public Optional<StudentResponseDto> addCourseToStudent (Integer idStudent, Integer idCourse) {
        return repository.addCourseToStudent(idStudent, idCourse).stream()
                .map(student -> converter.studentToDto(student))
                .findFirst();
    }

    public GeneralResponse<Optional<StudentResponseDto>> deleteStudentById (Integer idForDelete){
        Optional<StudentResponseDto> studentResponseDto= repository.deleteById(idForDelete)
                .map(converter::studentToDto);

        return new GeneralResponse<>(studentResponseDto);
    }

}

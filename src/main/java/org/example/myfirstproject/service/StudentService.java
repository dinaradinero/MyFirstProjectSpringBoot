package org.example.myfirstproject.service;

import lombok.AllArgsConstructor;
import org.example.myfirstproject.dto.GeneralResponse;
import org.example.myfirstproject.dto.courseDto.CourseResponseDto;
import org.example.myfirstproject.dto.studentDto.StudentRequestDto;
import org.example.myfirstproject.dto.studentDto.StudentResponseDto;
import org.example.myfirstproject.entity.Course;
import org.example.myfirstproject.entity.Student;
import org.example.myfirstproject.repository.course.CourseRepository;
import org.example.myfirstproject.repository.student.StudentRepository;
import org.example.myfirstproject.service.exception.NotFoundException;
import org.example.myfirstproject.service.exception.ValidationException;
import org.example.myfirstproject.service.mailutil.MailService;
import org.example.myfirstproject.service.util.Converter;
import org.hibernate.boot.model.internal.OptionalDeterminationSecondPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentRepository repository;
    private CourseRepository courseRepository;
    private Converter converter;

    @Autowired
    private MailService mailService;


//    public StudentService(StudentRepositoryInMemory repository, Converter converter) {
//        this.repository = repository;
//        this.converter = converter;
//    }

    public StudentResponseDto addNewStudent(StudentRequestDto request) {
        if (request == null){
            throw new NotFoundException("Student can not be empty");
        }

        if (request.getFirstName() == null || request.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name must not be empty");
        }

        if (request.getLastName() == null || request.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name must not be empty");
        }

        Student student = new Student();

        student.setStudentFirstName(request.getFirstName().trim());
        student.setStudentLastName(request.getLastName().trim());
        student.setStudentRegistrationDate(LocalDate.now());
        student.setStudentAverageMark(0);
        student.setAllStudentCourses(new ArrayList<>());
        student.setEmail(request.getEmail());

        Student studentAfterSave = repository.save(student);

        return converter.studentToDto(studentAfterSave);

    }
    //findall
    public List<StudentResponseDto> findAllStudents () {
        return repository.findAll().stream()
                .map(student -> converter.studentToDto(student))
                .toList();
    }
    //findById
    public Optional<StudentResponseDto> findById (Integer studentId){
        if (studentId == null) {
            throw new ValidationException("Student Id can not be null");
        }

        if (studentId < 0) {
            throw new ValidationException("Student Id can not be negative");
        }

        Optional<StudentResponseDto> studentResponseDto = repository.findById(studentId).stream()
                .map(student -> converter.studentToDto(student))
                .findFirst();

        if (studentResponseDto.isPresent()){
            return studentResponseDto;
        } else {
            throw new NotFoundException("Student with id " + studentId + " was not found");
        }
    }


    //findByName
    public List<StudentResponseDto> findByFirstName (String studentFirstName){
        if (studentFirstName == null){
            throw new ValidationException("Students first name can not be null");
        }

        if (studentFirstName.trim().matches(".*\\d.*")){
            throw new ValidationException("Student first name cannot contain numbers.");
        }

        List<StudentResponseDto> studentResponseDtos = repository.findStudentsByStudentFirstName(studentFirstName).stream()
                .map(student -> converter.studentToDto(student))
                .toList();

        return studentResponseDtos;
    }

    public List<StudentResponseDto> findByLastName (String studentLastName){
        if (studentLastName == null){
            throw new ValidationException("Students last name can not be null");
        }

        if (studentLastName.trim().matches(".*\\d.*")){
            throw new ValidationException("Student last name cannot contain numbers.");
        }
        List<StudentResponseDto> studentResponseDtos = repository.findStudentsByStudentLastName(studentLastName).stream()
                .map(student -> converter.studentToDto(student))
                .toList();

        return studentResponseDtos;
    }

    //findByRegistrationDates
    public List<StudentResponseDto> findByRegistrationDates (LocalDate start, LocalDate finish) {
        List<StudentResponseDto> studentResponseDtos = repository.findStudentsByStudentRegistrationDateBetween(start, finish).stream()
                .map(student -> converter.studentToDto(student))
                .toList();
        return studentResponseDtos;
    }


    //findByAverageMark
    public List<StudentResponseDto> findByAverageMark (Integer min, Integer max) {
        if (min == null){
            throw new ValidationException("Minimal average mark can not be null");
        }
        if (max == null){
            throw new ValidationException("Maximal average mark can not be null");
        }

        return repository.findStudentsByStudentAverageMarkAndStudentAverageMark(min, max).stream()
                .map(student -> converter.studentToDto(student))
                .toList();
    }

    public List<CourseResponseDto> findCoursesByStudentId (Integer idForSearch){
        List<CourseResponseDto> courseResponseDtos = repository.findCoursesByStudentId(idForSearch).stream()
                .map(course -> converter.courseToDto(course))
                .toList();

        return courseResponseDtos;
    };

    public GeneralResponse<List<CourseResponseDto>> findCoursesByStudentName (String firstName, String lastName){
        List<CourseResponseDto> courseResponseDtos = repository.findCoursesByStudentFirstNameAndStudentLastName(firstName, lastName).stream()
                .map(course -> converter.courseToDto(course))
                .toList();

        return new GeneralResponse<>(courseResponseDtos);

    };


    //addAverageMark
    public Optional<StudentResponseDto> addAverageMark (Integer idStudent, Integer markToAdd) {
        Student existingStudent = repository.findStudentByStudentId(idStudent).orElseThrow();
        existingStudent.setStudentAverageMark(markToAdd);
        repository.save(existingStudent);
        return Optional.of(converter.studentToDto(existingStudent));
//        return repository.addAverageMark(idStudent, markToAdd).stream()
//                .map(student -> converter.studentToDto(student))
//                .findFirst();
    }


    //addCourseToStudent

    public Optional<StudentResponseDto> addCourseToStudent (Integer idStudent, Integer idCourse) {
        Student student = repository.findStudentByStudentId(idStudent).orElseThrow();
        Course course = courseRepository.findById(idCourse).orElseThrow();
        student.getAllStudentCourses().add(course);
        course.getCourseStudents().add(student);
        repository.save(student);

        mailService.sendEnrollmentEmail(student, course);

        return Optional.of(converter.studentToDto(student));
    }

    public void deleteStudentById(Integer studentId){
        Student student = repository.findStudentByStudentId(studentId).orElseThrow();

        for (Course course : student.getAllStudentCourses()) {
            course.getCourseStudents().remove(student);
            courseRepository.save(course);
        }

        repository.delete(student);
    }



}

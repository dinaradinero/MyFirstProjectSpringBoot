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
import org.example.myfirstproject.service.util.Converter;
import org.hibernate.boot.model.internal.OptionalDeterminationSecondPass;
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


//    public StudentService(StudentRepositoryInMemory repository, Converter converter) {
//        this.repository = repository;
//        this.converter = converter;
//    }

    public StudentResponseDto addNewStudent(StudentRequestDto request) {
        Student student = new Student();

        student.setStudentFirstName(request.getFirstName().trim());
        student.setStudentLastName(request.getLastName().trim());
        student.setStudentRegistrationDate(LocalDate.now());
        student.setStudentAverageMark(0);
        student.setAllStudentCourses(new ArrayList<>());

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
        return repository.findById(studentId).stream()
                .map(student -> converter.studentToDto(student))
                .findFirst();
    }


    //findByName
    public GeneralResponse<List<StudentResponseDto>> findByFirstName (String studentFirstName){
        List<StudentResponseDto> studentResponseDtos = repository.findStudentsByStudentFirstName(studentFirstName).stream()
                .map(student -> converter.studentToDto(student))
                .toList();

        return new GeneralResponse<>(studentResponseDtos);
    }

    public GeneralResponse<List<StudentResponseDto>> findByLastName (String studentLastName){
        List<StudentResponseDto> studentResponseDtos = repository.findStudentsByStudentLastName(studentLastName).stream()
                .map(student -> converter.studentToDto(student))
                .toList();

        return new GeneralResponse<>(studentResponseDtos);
    }

    //findByRegistrationDates
    public GeneralResponse<List<StudentResponseDto>> findByRegistrationDates (LocalDate start, LocalDate finish) {
        List<StudentResponseDto> studentResponseDtos = repository.findStudentsByStudentRegistrationDateBetween(start, finish).stream()
                .map(student -> converter.studentToDto(student))
                .toList();
        return new GeneralResponse<>(studentResponseDtos);
    }


    //findByAverageMark
    public GeneralResponse<List<StudentResponseDto>> findByAverageMark (Integer min, Integer max) {
        List<StudentResponseDto> studentResponseDtos = repository.findStudentsByStudentAverageMarkAndStudentAverageMark(min, max).stream()
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

        return Optional.of(converter.studentToDto(student));
//        return repository.addCourseToStudent(idStudent, idCourse).stream()
//                .map(student -> converter.studentToDto(student))
//                .findFirst();
    }

    public void deleteStudentById(Integer studentId){
        Student student = repository.findStudentByStudentId(studentId).orElseThrow();

        for (Course course : student.getAllStudentCourses()) {
            course.getCourseStudents().remove(student);
        }

        repository.delete(student);
    }



//    public Optional<StudentResponseDto> deleteStudentById (Integer idForDelete){
//        Optional<Student> student = repository.deleteStudentByStudentId(idForDelete);
//
//
//        return new Optional.ofNullable(converter.studentToDto(student));
//    }

}

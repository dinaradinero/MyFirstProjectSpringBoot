package org.example.myfirstproject.repository.student;

import org.example.myfirstproject.entity.Course;
import org.example.myfirstproject.entity.Student;
import org.example.myfirstproject.repository.course.CoursesRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository implements StudentRepositoryInMemory {

    private List<Student> database;
    private Integer idStudentCounter;
    private CoursesRepository coursesRepository;


    public StudentRepository(CoursesRepository coursesRepository) {
        this.database = new ArrayList<>();
        this.idStudentCounter = 0;
        this.coursesRepository = coursesRepository;
    }

    @Override
    public Student add(Student newStudent) {

        Student studentToAdd = new Student(
                ++idStudentCounter,
                newStudent.getStudentLastName(),
                newStudent.getStudentFirstName(),
                LocalDate.now(),
                0,
                new ArrayList<>()
        );

        database.add(studentToAdd);
        return studentToAdd;
    }

    @Override
    public List<Student> findAll() {
        return database;
    }

    @Override
    public Optional<Student> findById(Integer idForSearch) {
        return database.stream()
                .filter(student -> student.getStudentId().equals(idForSearch))
                .findFirst();
    }

    @Override
    public List<Student> findByName(String nameForSearch) {
        return database.stream()
                .filter(student -> student.getStudentFirstName().equals(nameForSearch) || student.getStudentLastName().equals(nameForSearch))
                .toList();
    }

    @Override
    public List<Student> findByRegistrationDates(LocalDate startDate, LocalDate endDate) {
        return database.stream()
                .filter(student -> !student.getStudentRegistrationDate().isBefore(startDate) && !student.getStudentRegistrationDate().isAfter(endDate))
                .toList();
    }

    @Override
    public List<Student> findByAverageMarks(Integer minAverageMark, Integer maxAverageMark) {
        return database.stream()
                .filter(student -> student.getStudentAverageMark() > minAverageMark && student.getStudentAverageMark() <maxAverageMark)
                .toList();
    }

    @Override
    public List<Course> findCoursesByStudentId(Integer idForSearch) {
//        Optional<Student> foundStudent = findById(idForSearch);
//
//        if(foundStudent.isPresent()){
//        return foundStudent.get().getAllStudentCourses();}
        return findById(idForSearch)
                .map(Student::getAllStudentCourses)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Course> findCoursesByStudentName(String nameForSearch) {
        return findByName(nameForSearch).stream()
                .flatMap(student -> student.getAllStudentCourses().stream())
                .distinct()
                .toList();
    }

    @Override
    public Optional<Student> addAverageMark(Integer idForSearch, Integer markForAdd) {
        Optional<Student> foundStudent = findById(idForSearch);
        if(foundStudent.isPresent()){
          foundStudent.get().setStudentAverageMark(markForAdd);
        return foundStudent;}
        else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Student> addCourseToStudent(Integer idForSearch, Integer courseId) {
        Optional<Student> foundStudent = findById(idForSearch);
        Optional<Course> foundCourse = coursesRepository.findById(courseId);

        if(foundStudent.isPresent() && foundCourse.isPresent()){
            foundStudent.get().getAllStudentCourses().add(foundCourse.get());
            foundCourse.get().getCourseStudents().add(foundStudent.get());
            return foundStudent;}
        else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Student> deleteById(Integer idForDelete) {
        Optional<Student> foundStudent = findById(idForDelete);

        if (foundStudent.isPresent()) {
            database.removeIf(s -> s.getStudentId().equals(idForDelete));
        }

        return foundStudent;
    }




}

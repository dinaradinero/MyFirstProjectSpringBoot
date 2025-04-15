package org.example.myfirstproject.repository.course;

import org.example.myfirstproject.entity.Course;
import org.example.myfirstproject.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CoursesRepository implements CoursesRepositoryInMemory {
    private List<Course> courseDatabase;
    private Integer idCounter;

    public CoursesRepository() {
        this.courseDatabase = new ArrayList<>();
        this.idCounter = 0;
    }


    @Override
    public Course add(Course newCourse) {
        Course courseToAdd = new Course(
                ++idCounter,
                newCourse.getCourseName(),
                new ArrayList<>()
        );

        courseDatabase.add(courseToAdd);
        return courseToAdd;

    }

    @Override
    public List<Course> findAll() {
        return courseDatabase;
    }

    @Override
    public Optional<Course> findById(Integer idForSearch) {
        return courseDatabase.stream()
                .filter(course -> course.getCourseId().equals(idForSearch))
                .findFirst();
    }

    @Override
    public List<Course> findByName(String nameForSearch) {
        return courseDatabase.stream()
                .filter(course -> course.getCourseName().equals(nameForSearch))
                .toList();
    }

    @Override
    public List<Student> findStudentsByCourseName(String nameForSearch) {
        return findByName(nameForSearch).stream()
                .flatMap(course -> course.getCourseStudents().stream())
                .toList();
    }
}

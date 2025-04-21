package org.example.myfirstproject.repository.course;

import org.example.myfirstproject.entity.Course;
import org.example.myfirstproject.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {


    List<Course> findAll();

    Optional<Course> findById(Integer integer);

    List<Course> findCourseByCourseName(String courseName);

    List<Student> findStudentByCourseName(String courseName);

}

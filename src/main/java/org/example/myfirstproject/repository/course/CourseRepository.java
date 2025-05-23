package org.example.myfirstproject.repository.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.myfirstproject.entity.Course;
import org.example.myfirstproject.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {


    List<Course> findAll();

    Optional<Course> findById(Integer integer);

    List<Course> findCourseByCourseName(String courseName);

    //List<Student> findStudentByCourseName(String courseName);

//    default List<Student> findStudentsByCourseName(String nameForSearch) {
//
//    }

}

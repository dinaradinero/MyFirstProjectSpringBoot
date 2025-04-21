package org.example.myfirstproject.repository.student;

import org.example.myfirstproject.entity.Course;
import org.example.myfirstproject.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    
    List<Student> findAll();

    Optional<Student> findStudentByStudentId(Integer studentId);

    List<Student> findStudentsByStudentFirstName(String studentFirstName);

    List<Student> findStudentsByStudentLastName(String studentLastName);

    List<Student> findStudentsByStudentRegistrationDateBetween(LocalDate studentRegistrationDateAfter, LocalDate studentRegistrationDateBefore);

    List<Student> findStudentsByStudentAverageMarkAndStudentAverageMark(Integer studentAverageMark, Integer studentAverageMark1);

    List<Course> findCoursesByStudentId(Integer studentId);

    List<Course> findCoursesByStudentFirstNameAndStudentLastName(String studentFirstName, String studentLastName);

//    Optional<Student> deleteStudentByStudentId(Integer studentId);

    //    public List<Student> findAll();
//
//    public Optional<Student> findById(Integer idForSearch);
//
//    public List<Student> findByName(String nameForSearch);
//
//    public List<Student> findByRegistrationDates(LocalDate startDate, LocalDate endDate);
//
//    public List<Student> findByAverageMarks(Integer minAverageMark, Integer maxAverageMark);
//
//    public List<Course> findCoursesByStudentId (Integer idForSearch);
//
//    public List<Course> findCoursesByStudentName (String nameForSearch);
//
//    public Optional<Student> addAverageMark (Integer idForSearch, Integer markForAdd);
//
//    public Optional<Student> addCourseToStudent (Integer idForSearch, Integer courseId);
//
//    public Optional<Student> deleteById(Integer id);
}

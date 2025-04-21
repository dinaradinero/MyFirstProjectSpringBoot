//package org.example.myfirstproject.repository.student;
//
//import org.example.myfirstproject.entity.Course;
//import org.example.myfirstproject.entity.Student;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//public interface StudentRepositoryInMemory {
//
//    public Student add(Student newStudent);
//
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
//
//}

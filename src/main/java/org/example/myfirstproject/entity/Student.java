package org.example.myfirstproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;


    @NotBlank(message = "Last name can not be blank")
    @Size(min = 1, max = 30, message = "Name length must be between 1 to 30 characters")
    @Pattern(regexp = "^[A-Za-z\\-\\s]+$", message = "Name can contain only latin characters")
    private String studentLastName;

    @NotBlank(message = "First name can not be blank")
    @Size(min = 1, max = 20, message = "Name length must be between 1 to 20 characters")
    @Pattern(regexp = "^[A-Za-z\\-\\s]+$", message = "Name can contain only latin characters")
    private String studentFirstName;

    private LocalDate studentRegistrationDate;
    private String email;
    private Integer studentAverageMark;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> allStudentCourses;


}

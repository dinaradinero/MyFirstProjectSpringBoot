package org.example.myfirstproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    @NotBlank(message = "Course name can not be blank")
    @Size(min = 1, max = 30, message = "Course name length must be between 1 to 30 characters")
    @Pattern(regexp = "^[A-Za-z\\-\\s]+$", message = "Name can contain only latin characters")

    private String courseName;

    @ManyToMany(mappedBy = "allStudentCourses", cascade = CascadeType.ALL)
    private List<Student> courseStudents;

}

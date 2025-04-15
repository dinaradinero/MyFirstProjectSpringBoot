package org.example.myfirstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Integer studentId;
    private String studentLastName;
    private String studentFirstName;
    private LocalDate studentRegistrationDate;
    private Integer studentAverageMark;
    private List<Course> allStudentCourses;


}

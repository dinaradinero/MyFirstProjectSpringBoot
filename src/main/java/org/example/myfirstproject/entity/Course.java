package org.example.myfirstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private Integer courseId;
    private String courseName;
    private List<Student> courseStudents;

}

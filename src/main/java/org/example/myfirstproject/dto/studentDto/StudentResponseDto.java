package org.example.myfirstproject.dto.studentDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.myfirstproject.dto.courseDto.CourseResponseDto;
import org.example.myfirstproject.entity.Course;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto{

    private Integer id;
    private String lastName;
    private String firstName;
    private LocalDate registrationDate;
    private Integer averageMark;
    private List<Course> courses;

}

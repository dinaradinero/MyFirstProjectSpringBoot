package org.example.myfirstproject.dto.studentDto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.myfirstproject.dto.courseDto.CourseResponseDto;
import org.example.myfirstproject.dto.courseDto.CourseSimpleDto;
import org.example.myfirstproject.entity.Course;

import java.time.LocalDate;
import java.util.List;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "courseId"
//)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto{

    private Integer id;
    private String lastName;
    private String firstName;
    private LocalDate registrationDate;
    private Integer averageMark;

//    @JsonBackReference
    private List<CourseSimpleDto> courses;

}

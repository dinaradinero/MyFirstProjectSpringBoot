package org.example.myfirstproject.dto.courseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.myfirstproject.entity.Student;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {

    private Integer courseId;
    private String courseName;
    private List<Student> courseStudents; //
}

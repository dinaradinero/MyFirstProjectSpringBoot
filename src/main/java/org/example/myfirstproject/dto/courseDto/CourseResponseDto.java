package org.example.myfirstproject.dto.courseDto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.myfirstproject.dto.studentDto.StudentResponseDto;
import org.example.myfirstproject.dto.studentDto.StudentSimpleDto;
import org.example.myfirstproject.entity.Student;

import java.util.List;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id" // или "courseId" для CourseResponseDto
//)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {

    private Integer courseId;
    private String courseName;


    private List<StudentSimpleDto> courseStudents; //
}

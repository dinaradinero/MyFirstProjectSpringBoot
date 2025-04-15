package org.example.myfirstproject.dto.courseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {
    private Integer courseId;
    private String courseName;
}

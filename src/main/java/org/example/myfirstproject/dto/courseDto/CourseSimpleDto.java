package org.example.myfirstproject.dto.courseDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSimpleDto {
    private Integer courseId;

    @NotBlank(message = "Course name can not be blank")
    @Size(min = 1, max = 30, message = "Course name length must be between 1 to 30 characters")
    @Pattern(regexp = "^[A-Za-z\\-\\s]+$", message = "Name can contain only latin characters")
    private String courseName;
}

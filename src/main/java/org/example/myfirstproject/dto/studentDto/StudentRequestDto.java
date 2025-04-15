package org.example.myfirstproject.dto.studentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDto {
    private String lastName;
    private String firstName;
    private Integer studentAverageMark;

}

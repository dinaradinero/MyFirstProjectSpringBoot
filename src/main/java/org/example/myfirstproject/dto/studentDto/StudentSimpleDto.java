package org.example.myfirstproject.dto.studentDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSimpleDto {
    private Integer id;
    private String lastName;
    private String firstName;

}

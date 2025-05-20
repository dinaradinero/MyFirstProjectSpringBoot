package org.example.myfirstproject.dto.studentDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDto {

    public String getEmail;
    @NotBlank(message = "Last name can not be blank")
    @Size(min = 1, max = 30, message = "Name length must be between 1 to 30 characters")
    @Pattern(regexp = "^[A-Za-z\\-\\s]+$", message = "Name can contain only latin characters")
    private String lastName;

    @NotBlank(message = "First name can not be blank")
    @Size(min = 1, max = 20, message = "Name length must be between 1 to 20 characters")
    @Pattern(regexp = "^[A-Za-z\\-\\s]+$", message = "Name can contain only latin characters")
    private String firstName;

    private Integer studentAverageMark;
    private String email;

}

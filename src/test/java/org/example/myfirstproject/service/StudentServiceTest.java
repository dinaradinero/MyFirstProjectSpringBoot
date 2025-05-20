package org.example.myfirstproject.service;

import org.example.myfirstproject.dto.studentDto.StudentRequestDto;
import org.example.myfirstproject.dto.studentDto.StudentResponseDto;
import org.example.myfirstproject.entity.Student;
import org.example.myfirstproject.repository.student.StudentRepository;
import org.example.myfirstproject.service.util.Converter;
import org.hibernate.validator.constraints.ModCheck;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private Converter converter;

    @InjectMocks
    private StudentService service;

    @Test
    void addNewStudent_shouldAddAndReturnStudent_whenNewValidStudent() {
        StudentRequestDto requestDto =new StudentRequestDto();
        requestDto.setFirstName("Andrew");
        requestDto.setLastName("Morning");

        Student savedStudent = new Student();
        savedStudent.setStudentFirstName("Andrew");
        savedStudent.setStudentLastName("Morning");

        when(studentRepository.save(any(Student.class)))
                .thenReturn(savedStudent);

        StudentResponseDto expectedResponse = new StudentResponseDto();
        expectedResponse.setFirstName("Andrew");
        expectedResponse.setLastName("Morning");
        when(converter.studentToDto(savedStudent)).thenReturn(expectedResponse);

        StudentResponseDto actualResponse = service.addNewStudent(requestDto);

        assertEquals("Andrew", actualResponse.getFirstName());
        assertEquals("Morning", actualResponse.getLastName());
        verify(studentRepository).save(any(Student.class));

    }

    @Test
    void addNewStudent_shouldThrowException_whenFirstNameIsEmpty() {
        StudentRequestDto requestDto = new StudentRequestDto();
        requestDto.setFirstName(""); // пустое имя
        requestDto.setLastName("Morning");

        assertThrows(IllegalArgumentException.class, () -> {
            service.addNewStudent(requestDto);
        });
    }



}

package org.example.myfirstproject.service.mailutil;

import lombok.RequiredArgsConstructor;
import org.example.myfirstproject.entity.Course;
import org.example.myfirstproject.entity.Student;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("dinaraiumagulova@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendEnrollmentEmail(Student student, Course course) {
        String subject = "Вы записаны на курс: " + course.getCourseName();
        String body = "Здравствуйте, " + student.getStudentFirstName() + " " + student.getStudentLastName() + "!\n\n" +
                "Вы успешно записались на курс \"" + course.getCourseName() + "\".";

        sendSimpleEmail(student.getEmail(), subject, body);
    }

}

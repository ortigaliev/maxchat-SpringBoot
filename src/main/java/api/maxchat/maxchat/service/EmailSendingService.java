package api.maxchat.maxchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSendingService {
    @Value("${spring.mail.username}")
    private String fromAccount;
    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String email, Integer profileId) {

        String subject = "Registration Confirmation";
        String body = "Assalomu alaykuk Bobur aka. Youtube dagi darslar uchun katta rahmat. Darslar juda tartibli va foydali bo'lyabdi!";
        sendEmail(email, subject, body);
    }


    private void sendEmail(String email, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(body);
        mailSender.send(msg);

    }
}

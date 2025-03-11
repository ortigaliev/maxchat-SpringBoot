package api.maxchat.maxchat.service;

import api.maxchat.maxchat.utils.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendingService {
    @Value("${spring.mail.username}")
    private String fromAccount;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendRegistrationEmail(String email, Integer profileId) {

        String subject = "Registration Confirmation";
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>Title</title>\n" +
                "  <style>\n" +
                "    .header{\n" +
                "      font-family: Helvetica, Arial, sans-serif;\n" +
                "      color: #23496d;\n" +
                "      word-break: break-word;\n" +
                "      text-align: center;\n" +
                "      padding: 10px 20px;\n" +
                "    }\n" +
                "    p {\n" +
                "      width: 600px;\n" +
                "      margin-right: auto;\n" +
                "      margin-left: auto;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1 class=\"header\">Complete Registration</h1>\n" +
                "<p>Hi Muhammadjon</p>\n" +
                "<p>We wanted to share a quick update about the free learning tools available to you with your MaxChat account.</p>\n" +
                "<p>We recently introduced The MaxChat Newsletter exclusively for paying MaxChat subscribers. (Here's our\n" +
                "  <a href=\"http://localhost:8088/auth/register/verification/%s\" style=\"color:#4951f5\" target=\"_blank\">launch announcement</a>\n" +
                "   in case you missed it).</p>\n" +
                "<p style=\"line-height:150%\"><span style=\"color:#021b36\">As part of your free account, you now have access to a </span>\n" +
                "  <span style=\"font-weight:bold\">free version</span>of this newsletter.</p>\n" +
                "</body>\n" +
                "</html>";
        body = String.format(body, JwtUtil.encode(profileId));
        System.out.println(JwtUtil.encode(profileId));
        sendMimeEmail(email, subject, body);
    }

    private void sendMimeEmail(String email, String subject, String body) {

        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(msg);
        } catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }

//    private void sendSimpleEmail(String email, String subject, String body) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setFrom(fromAccount);
//        msg.setTo(email);
//        msg.setSubject(subject);
//        msg.setText(body);
//        javaMailSender.send(msg);
//
//    }



}

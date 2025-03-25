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

    @Value("${server.domain}")
    private String serverDomain;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendRegistrationEmail(String email, Integer profileId) {

        String subject = "Registration Confirmation";
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "    <style>\n" +
                "        a {\n" +
                "            padding: 10px 30px;\n" +
                "            display: inline-block;\n" +
                "        }\n" +
                "\n" +
                "        .button-link {\n" +
                "            text-decoration: none;\n" +
                "            color: white;\n" +
                "            background-color: indianred;\n" +
                "        }\n" +
                "\n" +
                "        .button-link:hover {\n" +
                "            background-color: #dd4444;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1 style=\"text-align: center\">Complete Registration</h1>\n" +
                "<p>Salom yaxshimisiz</p>\n" +
                "<p>\n" +
                "    Please click to button for completing registration: <a class=\"button-link\"\n" +
                "                                                           href=\"%s/auth/register/verification/%s\"\n" +
                "                                                           target=\"_blank\">Click there</a>\n" +
                "</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";

        System.out.println(JwtUtil.encode(profileId));
        body = String.format(body, serverDomain, JwtUtil.encode(profileId));
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

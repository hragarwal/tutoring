package com.tutoring.util;

/**
 * Created by Himanshu.
 */

import com.tutoring.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
@Transactional
public class MailService {

    @Value("${email.from}")
    private String fromEmailId;
    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String password;

    private static Logger logger = LoggerFactory.getLogger(MailService.class);

    public Session getMailSession(){
        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        return session;
    }

    public void sendMail(String body, String subject, String to_email) throws AppException{
        Session session  = getMailSession();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmailId));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to_email));
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(body);
            Transport.send(message);
            logger.info("Sent message successfully....");

        } catch (MessagingException e) {
            logger.error("Failure in sending email",e);
            throw new AppException(e);
        }
    }
}



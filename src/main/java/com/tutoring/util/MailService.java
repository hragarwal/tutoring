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

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.List;
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

    public void sendMailWithAttachment(String body, String subject, String to_email, List<File> files) throws AppException{
        Session session  = getMailSession();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmailId));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to_email));
            message.setSubject(subject);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            attacheFiles(files,multipart);
            message.setContent(multipart);
            Transport.send(message);
            logger.info("Sent message successfully....");

        } catch (MessagingException e) {
            logger.error("Failure in sending email",e);
            throw new AppException(e);
        }
    }

    private void attacheFiles(List<File> files, Multipart multipart) throws MessagingException {
        for (File file : files) {
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(file.getName());
            multipart.addBodyPart(messageBodyPart);
        }
    }
}



package com.tutoring.util;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutoring.exception.AppException;

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

	/**
	 * Email details.
	 * @param toEmail - To address email.
	 * @param ccEmails - list of cc email.
	 * @param bccEmails - list of bcc email.
	 * @param subject - subject of email.
	 * @param body - body of email.
	 * @param files - files to be attached with email.
	 * @throws AppException - throws exception
	 */
	public void sentEmail(String toEmail, List<String> ccEmails, List<String> bccEmails, String subject, String body, List<File> files) throws AppException{
		Session session  = getMailSession();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmailId));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setSentDate(new Date());
			
			if(Objects.nonNull(files)) {
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(body);
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				attacheFiles(files,multipart);
				message.setContent(multipart);
			} else {
				message.setText(body);
			}
			
			Transport.send(message);
			logger.info("Email has been sent successfully to"+ toEmail+ " with subject: "+ subject);
		} catch (MessagingException e) {
			logger.info("An exception occurred while sending email to "+ toEmail+ " with subject: "+ subject);
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



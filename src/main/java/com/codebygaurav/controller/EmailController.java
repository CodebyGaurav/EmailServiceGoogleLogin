package com.codebygaurav.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.codebygaurav.model.EmailMessage;

@RestController
public class EmailController {
	
	@Value("${gmail.username}")
	private String username;
	
	@Value("${gmail.password}")
	private String password;

	@RequestMapping(value="/send",method=RequestMethod.POST)
	public ResponseEntity<?> sendEmail(@RequestBody EmailMessage emailMessage) throws AddressException ,MessagingException, IOException {
		sendMail(emailMessage);
		return new ResponseEntity<>("Email Successfully",HttpStatus.OK);
	}
	
	private void sendMail(EmailMessage emailMessage) throws AddressException ,MessagingException, IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");	

		System.out.println(emailMessage.getEmail());
		System.out.println(emailMessage.getSubjectname());
		System.out.println(emailMessage.getText_message());
		
		Session session = Session.getInstance(props, 
				new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
					
				}
		});
		
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(username,false));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username));
		msg.setSubject(emailMessage.getSubjectname());
		msg.setContent(emailMessage.getText_message(),"text/html");
		msg.setSentDate(new Date());
		
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(emailMessage.getText_message	(),"text/html");
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		
//		MimeBodyPart mimeBodyPart = new MimeBodyPart();
//		mimeBodyPart.attachFile("Users/gauravsharma_2icloud.com/eclipse-workspace/EmailServiceApp/src/main/resources/Hello.json");
//		
//		multipart.addBodyPart(mimeBodyPart);
		msg.setContent(multipart);
		System.out.println("Message : "+msg);
		Transport.send(msg);
		
	}
	
}

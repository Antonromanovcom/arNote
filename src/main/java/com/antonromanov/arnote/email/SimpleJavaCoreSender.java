package com.antonromanov.arnote.email;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SimpleJavaCoreSender {


	public void send() {

		String to = "antonr0manov@yandex.ru";         // sender email
		String from = "ant0nr0manov@rambler.ru";       // receiver email
		String host = "smtp.rambler.ru";       // mail server host

		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
//		props.put("mail.smtp.ssl.enable", "true");

		props.put("mail.smtp.starttls.enable", "true");

//		props.put("mail.smtp.socketFactory.port", "465");
//		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.socketFactory.fallback", "true");

		props.put("mail.smtp.auth", "true");
//		props.put("mail.mime.charset", "UTF-8");
		props.put("mail.debug", "true");
		Authenticator auth = new MyAuthenticator("ant0nr0manov@rambler.ru", "Y2V3p9bSGQEfHdZ");


		Session session = Session.getDefaultInstance(props, auth); // default session

		try {
			MimeMessage message = new MimeMessage(session); // email message
			message.setFrom(new InternetAddress(from)); // setting header fields
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Test Mail from Java Program"); // subject line
			// actual mail body
			message.setText("You can send mail from Java program by using mail API, but you need" +
					"couple of more JAR files e.g. smtp.jar and activation.jar");

			// Send message
			Transport.send(message);
			System.out.println("Email Sent successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}

	 class MyAuthenticator extends Authenticator {
		private String user;
		private String password;

		MyAuthenticator(String user, String password) {
			this.user = user;
			this.password = password;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			String user = this.user;
			String password = this.password;
			return new PasswordAuthentication(user, password);
		}

	}
}

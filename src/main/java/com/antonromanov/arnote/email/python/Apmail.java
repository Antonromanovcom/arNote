package com.antonromanov.arnote.email.python;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class Apmail {

	private static final String HOST = "smtp.rambler.ru";
	private static final int PORT = 465;
	private static final boolean SSL_FLAG = true;



	public void sendSimpleEmail() {

		String userName = "ant0nr0manov@rambler.ru";
		String password = "Y2V3p9bSGQEfHdZ";

		String fromAddress="ant0nr0manov@rambler.ru";
		String toAddress =  "antonr0manov@yandex.ru";
		String subject = "Test Mail";
		String message = "Hello from Apache Mail";

		try {
			Email email = new SimpleEmail();
			email.setHostName(HOST);
			email.setSmtpPort(PORT);
			email.setAuthenticator(new DefaultAuthenticator(userName, password));
			email.setSSLOnConnect(SSL_FLAG);
			email.setStartTLSEnabled(false);
			email.setFrom(fromAddress);
			email.setSubject(subject);
			email.setMsg(message);
			email.addTo(toAddress);
			email.send();
		}catch(Exception ex){
			System.out.println("Unable to send email");
			System.out.println(ex);
		}
	}
}

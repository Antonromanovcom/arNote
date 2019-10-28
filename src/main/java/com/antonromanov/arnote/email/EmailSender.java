package com.antonromanov.arnote.email;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@PropertySource("classpath:application.properties")
@Component
public class EmailSender {


	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger("console_logger");

	@Value("${email.address}")
	private String fromAddress;

	@Autowired
	private JavaMailSender sender;

	public EmailStatus sendPlainText(String to, String subject, String text) {
		return sendM(to, subject, text, false);
	}

	private EmailStatus sendM(String to, String subject, String text, Boolean isHtml) {

		try {
			MimeMessage mail = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo(to);
			helper.setFrom(fromAddress);
			helper.setSubject(subject);
			helper.setText(text, isHtml);
			sender.send(mail);
			LOGGER.info("Send email '{}' to: {}", subject, to);
			return new EmailStatus(to, subject, text).success();

		} catch (MessagingException mailException) {
			mailException.printStackTrace();
			LOGGER.error(String.format("Problem with sending email to: {}, error message: {}", to, mailException.getMessage()));
			return new EmailStatus(to, subject, text).error(mailException.getMessage());
		}
	}



}

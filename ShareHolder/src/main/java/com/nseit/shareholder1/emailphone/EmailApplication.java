package com.nseit.shareholder1.emailphone;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.nseit.shareholder1.model.MailModel;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailApplication {

	@Value(value = "${frommail}")
	private String from;

	@Autowired
	JavaMailSender emailSender;

	@Autowired
	@Qualifier("emailConfigBean")
	Configuration emailConfig;

	public void sendEmail(MailModel mailModel) {

		try {
			Map<String, String> model = new HashMap<String, String>();
			model.put("name", mailModel.getName());
			model.put("location", "Sri Lanka");
			model.put("signature", "hello");
			model.put("content", mailModel.getContent());

			/**
			 * Add below line if you need to create a token to verification emails and
			 * uncomment line:32 in "email.ftl"
			 * model.put("token",UUID.randomUUID().toString());
			 */

			mailModel.setModel(model);

			log.info("Sending Email to: " + mailModel.getTo());

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			// mimeMessageHelper.addInline("logo.png", new
			// ClassPathResource("classpath:/techmagisterLogo.png"));

			Template template = emailConfig.getTemplate("otp.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());

			mimeMessageHelper.setTo(mailModel.getTo());
			mimeMessageHelper.setText(html, true);
			// mimeMessageHelper.setText(mailModel.getContent());
			mimeMessageHelper.setSubject(mailModel.getSubject());
			mimeMessageHelper.setFrom(from);

			emailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Attachment of file
	public void sendAttach(MailModel mailModel, List<String> filesList) {
		try {

			Map<String, String> model = new HashMap<String, String>();
			model.put("name", mailModel.getName());
			model.put("location", "Sri Lanka");
			model.put("signature", "hello");
			model.put("content", mailModel.getContent());

			// file path
			String path = "C:/Users/Balajimurugan/Desktop/bala.png";

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			mailModel.setModel(model);

			Template template = emailConfig.getTemplate("otp.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());

			mimeMessageHelper.setTo(mailModel.getTo());
			mimeMessageHelper.setText(html, true);
			// mimeMessageHelper.setText(mailModel.getContent());
			mimeMessageHelper.setSubject(mailModel.getSubject());
			mimeMessageHelper.setFrom(from);

			for (String f : filesList) {
				File file = new File(f);
				mimeMessageHelper.addAttachment(f.substring(f.lastIndexOf("/")+1), file);
			}

			emailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void sendStatusEmail(MailModel mailModel, Map<String ,String> map) {

		try {
			Map<String, String> model = new HashMap<String, String>();
			model.put("clientId", map.get("clientId"));
			model.put("uuid", map.get("uuid"));
			model.put("status", map.get("status"));
//			model.put("content", mailModel.getContent());

			/**
			 * Add below line if you need to create a token to verification emails and
			 * uncomment line:32 in "email.ftl"
			 * model.put("token",UUID.randomUUID().toString());
			 */

			mailModel.setModel(model);

			log.info("Sending Email to: " + mailModel.getTo());

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			// mimeMessageHelper.addInline("logo.png", new
			// ClassPathResource("classpath:/techmagisterLogo.png"));

			Template template = emailConfig.getTemplate("statusTemplate.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());

			mimeMessageHelper.setTo(mailModel.getTo());
			mimeMessageHelper.setText(html, true);
			// mimeMessageHelper.setText(mailModel.getContent());
			mimeMessageHelper.setSubject(mailModel.getSubject());
			mimeMessageHelper.setFrom(from);
			
			emailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void sendStatusEmail1(MailModel mailModel, Map<String ,String> map, String file) {

		try {
			Map<String, String> model = new HashMap<String, String>();
			model.put("clientId", map.get("clientId"));
			model.put("uuid", map.get("uuid"));
			model.put("status", map.get("status"));
//			model.put("content", mailModel.getContent());

			/**
			 * Add below line if you need to create a token to verification emails and
			 * uncomment line:32 in "email.ftl"
			 * model.put("token",UUID.randomUUID().toString());
			 */

			mailModel.setModel(model);

			log.info("Sending Email to: " + mailModel.getTo());

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			// mimeMessageHelper.addInline("logo.png", new
			// ClassPathResource("classpath:/techmagisterLogo.png"));

			Template template = emailConfig.getTemplate("statusTemplate.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());

			mimeMessageHelper.setTo(mailModel.getTo());
			mimeMessageHelper.setText(html, true);
			// mimeMessageHelper.setText(mailModel.getContent());
			mimeMessageHelper.setSubject(mailModel.getSubject());
			mimeMessageHelper.setFrom(from);
			File file1 = new File(file);
			mimeMessageHelper.addAttachment(file.substring(file.lastIndexOf("/")+1), file1);
			emailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

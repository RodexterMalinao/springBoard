package com.bomltsportal.service.email;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailServiceImpl implements MailService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private JavaMailSender javaMailSender;

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void send(InternetAddress from, InternetAddress to, InternetAddress cc, InternetAddress bcc
			, String emailTitle, String emailContent
			, List<File> attachments) throws MailException, MessagingException {
		this.send(from
				, to == null ? null : new InternetAddress[] { to }
				, cc == null ? null : new InternetAddress[] { cc }
				, bcc == null ? null : new InternetAddress[] { bcc }
				, emailTitle, emailContent, attachments);
	}

	public void send(InternetAddress from, InternetAddress[] to, InternetAddress[] cc, InternetAddress[] bcc
			, String emailTitle, String emailContent
			, List<File> attachments) throws MailException, MessagingException {
		this.send(from, to, cc, bcc, emailTitle, emailContent, false, attachments);
	}

	public void sendHtml(InternetAddress from, InternetAddress to, InternetAddress cc, InternetAddress bcc
			, String emailTitle, String emailContent
			, List<File> attachments) throws MailException, MessagingException {
		this.sendHtml(from
				, to == null ? null : new InternetAddress[] { to }
				, cc == null ? null : new InternetAddress[] { cc }
				, bcc == null ? null : new InternetAddress[] { bcc }
				, emailTitle, emailContent, attachments);
	}

	public void sendHtml(InternetAddress from, InternetAddress[] to, InternetAddress[] cc, InternetAddress[] bcc
			, String emailTitle, String emailContent
			, List<File> attachments) throws MailException, MessagingException {
		this.send(from, to, cc, bcc, emailTitle, emailContent, true, attachments);
	}

	private void send(InternetAddress from, InternetAddress[] to, InternetAddress[] cc, InternetAddress[] bcc
			, String emailTitle, String emailContent, boolean isHtml
			, List<File> attachments) throws MailException, MessagingException {
		Map<String, InputStreamSource> attachmentsSource = new LinkedHashMap<String, InputStreamSource>();
		if (!this.isEmpty(attachments)) {
			for (File file : attachments) {
				FileSystemResource fsr = new FileSystemResource(file);
				attachmentsSource.put(file.getName(), fsr);
			}
		}
		this.send(from, to, cc, bcc, emailTitle, emailContent, isHtml, attachmentsSource);
	}

	public void send(InternetAddress from, InternetAddress to,
			InternetAddress cc, InternetAddress bcc, String emailTitle,
			String emailContent, Map<String, InputStreamSource> attachments)
			throws MailException, MessagingException {
		this.send(from
				, to == null ? null : new InternetAddress[] { to }
				, cc == null ? null : new InternetAddress[] { cc }
				, bcc == null ? null : new InternetAddress[] { bcc }
				, emailTitle, emailContent, attachments);
	}

	public void send(InternetAddress from, InternetAddress[] to,
			InternetAddress[] cc, InternetAddress[] bcc, String emailTitle,
			String emailContent, Map<String, InputStreamSource> attachments)
			throws MailException, MessagingException {
		this.send(from, to, cc, bcc, emailTitle, emailContent, false, attachments);
	}

	public void sendHtml(InternetAddress from, InternetAddress to,
			InternetAddress cc, InternetAddress bcc, String emailTitle,
			String emailContent, Map<String, InputStreamSource> attachments)
			throws MailException, MessagingException {
		this.sendHtml(from
				, to == null ? null : new InternetAddress[] { to }
				, cc == null ? null : new InternetAddress[] { cc }
				, bcc == null ? null : new InternetAddress[] { bcc }
				, emailTitle, emailContent, attachments);
	}

	public void sendHtml(InternetAddress from, InternetAddress[] to,
			InternetAddress[] cc, InternetAddress[] bcc, String emailTitle,
			String emailContent, Map<String, InputStreamSource> attachments)
			throws MailException, MessagingException {
		this.send(from, to, cc, bcc, emailTitle, emailContent, true, attachments);
	}

	private void send(InternetAddress from, InternetAddress[] to, InternetAddress[] cc, InternetAddress[] bcc
			, String emailTitle, String emailContent, boolean isHtml
			, Map<String, InputStreamSource> attachments) throws MailException, MessagingException {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("from: " + from);
				logger.info("emailTitle: " + emailTitle);
				logger.info("emailContent: " + emailContent);
				logger.info("isHtml: " + isHtml + ", attachments: " + (isEmpty(attachments) ? 0 : attachments.size()));
			}
			MimeMessage message = this.javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			if (!isEmpty(to)) {
				if (logger.isInfoEnabled()) {
					StringBuilder sb = new StringBuilder();
					for (InternetAddress addr : to) {
						if (sb.length() > 0) {
							sb.append(", ");
						}
						sb.append(addr);
					}
					logger.info("to: " + sb);
				}
				
				helper.setTo(to);
			}
			if (!isEmpty(cc)) {
				if (logger.isInfoEnabled()) {
					StringBuilder sb = new StringBuilder();
					for (InternetAddress addr : cc) {
						if (sb.length() > 0) {
							sb.append(", ");
						}
						sb.append(addr);
					}
					logger.info("cc: " + sb);
				}
				
				helper.setCc(cc);
			}
			if (!isEmpty(bcc)) {
				if (logger.isInfoEnabled()) {
					StringBuilder sb = new StringBuilder();
					for (InternetAddress addr : bcc) {
						if (sb.length() > 0) {
							sb.append(", ");
						}
						sb.append(addr);
					}
					logger.info("bcc: " + sb);
				}
				
				helper.setBcc(bcc);
			}
			helper.setSubject(emailTitle);
			helper.setText(emailContent, isHtml);
			if (!this.isEmpty(attachments)) {
				for (Map.Entry<String, InputStreamSource> entry : attachments.entrySet()) {
					helper.addAttachment(entry.getKey(), entry.getValue());
				}
			}
			javaMailSender.send(message);
		} catch (MailException e) {
			logger.error("MailException during send", e);
			throw e;
		}
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	private boolean isEmpty(Map<?, ?> list) {
		return list == null || list.isEmpty();
	}
	
	private <E> boolean isEmpty(E[] values) {
		return values == null || values.length == 0;
	}
}

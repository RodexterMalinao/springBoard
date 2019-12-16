package com.bomwebportal.util;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.mail.smtp.SMTPTransport;

public class MailUtil {
	
	private static final Log logger = LogFactory.getLog(MailUtil.class);
	
	public final static String MAIL_TYPE_TEXT = "text";
	public final static String MAIL_TYPE_HTML = "html";
	public final static String MAIL_TYPE_PDF = "pdf";

	private static String mailHost;
	private static String mailUser;
	private static String mailPassword;
	private static String mailEncoding;

	private static boolean isInitialed;
	
	private MailUtil() {
		super();
	}

	private synchronized static void init() {
		if (!isInitialed) {
//			Configuration mailConfig = getMailConfig();
//
//			if (mailConfig != null) {
//				mailHost = mailConfig.getString("[@host]");
//				mailUser = mailConfig.getString("[@user]");
//				mailPassword = mailConfig.getString("[@password]");
			
//				mailEncoding = "UTF-8";
//			}
			mailHost = "smtp.sb.pccw.com"; //  original: mail.pccw.com 20120904 wilson
			mailUser = null;
			mailPassword = null;
			mailEncoding = "UTF-8";//
			isInitialed = true;
		}
	}

//	private static Configuration getMailConfig() {
//		Configuration mailConfig = null;
//
//		try {
//			mailConfig = ConfigUtils.searchConfig(ConfigUtils.getConfig(), "mail", "environment", ApplicationConfig.ENVIRONMENT);
//		}
//		catch(Exception e) {
//			logger.error("Failure in getting mail config.", e);
//		}
//
//		return mailConfig;
//	}
	
	public static void sendMail(String pSender, String[] pRecipients, String[] pCCRecipients, String[] pBCCRecipients, String pSubject, String pBody, String pBodyType, File[] pAttachment) throws Exception {
		init();

		try {
			Properties props = System.getProperties();

			props.put("mail.smtp.host", mailHost);

			// Get session
			Session session = Session.getInstance(props, null);

			// Create message
			MimeMessage msg = new MimeMessage(session);

			// Set header
			//msg.setHeader("Content-Transfer-Encoding", mailEncoding);

			// Set sender
			msg.setFrom(new InternetAddress(pSender));

			// Set TO recipients
			if (pRecipients != null && pRecipients.length > 0) {
				InternetAddress[] toAddress = new InternetAddress[pRecipients.length];

				for (int i = 0; i < pRecipients.length; i++) {
					toAddress[i] = new InternetAddress(pRecipients[i]);
				}

				msg.setRecipients(Message.RecipientType.TO, toAddress);
			}

			// Set CC recipients
			if (pCCRecipients != null && pCCRecipients.length > 0) {
				InternetAddress[] toAddress = new InternetAddress[pCCRecipients.length];

				for (int i = 0; i < pCCRecipients.length; i++) {
					toAddress[i] = new InternetAddress(pCCRecipients[i]);
				}

				msg.setRecipients(Message.RecipientType.CC, toAddress);
			}

			// Set BCC recipients
			if (pBCCRecipients != null && pBCCRecipients.length > 0) {
				InternetAddress[] toAddress = new InternetAddress[pBCCRecipients.length];

				for (int i = 0; i < pBCCRecipients.length; i++) {
					toAddress[i] = new InternetAddress(pBCCRecipients[i]);
				}

				msg.setRecipients(Message.RecipientType.BCC, toAddress);
			}

			// Set subject
			msg.setSubject(pSubject, mailEncoding);

			// Create the Multipart
			MimeMultipart mp = new MimeMultipart();

			// Set body
			if (pBody != null) {
				MimeBodyPart boayPart = new MimeBodyPart();

				boayPart.setContent(pBody, getContentType(pBodyType));

				mp.addBodyPart(boayPart);
			}

			// Set attachment
			if (pAttachment != null && pAttachment.length > 0) {
				for (int i = 0; i < pAttachment.length; i++) {
					MimeBodyPart attachmentPart = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(pAttachment[i]);
					attachmentPart.setDataHandler(new DataHandler(fds));
					attachmentPart.setFileName(fds.getName());

					mp.addBodyPart(attachmentPart);
				}
			}

			// Set Content
			msg.setContent(mp);

			// Set sent date
			msg.setSentDate(new Date());

			SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");

			transport.connect(mailHost, mailUser, mailPassword);			
			transport.sendMessage(msg, msg.getAllRecipients());

			transport.close();
		}
		catch (Exception e) {
			logger.error("Failure in sending email.", e);
			//BOMExceptionHelper.throwBOMException("Failure in sending email.", e);
		}
	}
	
	public static void sendMail(String pSender, String[] pRecipients, String[] pCCRecipients, String[] pBCCRecipients, String pSubject, String pBody, String pBodyType, byte[] pAttachmentData) throws Exception {
		init();

		try {
			Properties props = System.getProperties();

			props.put("mail.smtp.host", mailHost);

			// Get session
			Session session = Session.getInstance(props, null);

			// Create message
			MimeMessage msg = new MimeMessage(session);

			// Set header
			//msg.setHeader("Content-Transfer-Encoding", mailEncoding);

			// Set sender
			msg.setFrom(new InternetAddress(pSender));

			// Set TO recipients
			if (pRecipients != null && pRecipients.length > 0) {
				InternetAddress[] toAddress = new InternetAddress[pRecipients.length];

				for (int i = 0; i < pRecipients.length; i++) {
					toAddress[i] = new InternetAddress(pRecipients[i]);
				}

				msg.setRecipients(Message.RecipientType.TO, toAddress);
			}

			// Set CC recipients
			if (pCCRecipients != null && pCCRecipients.length > 0) {
				InternetAddress[] toAddress = new InternetAddress[pCCRecipients.length];

				for (int i = 0; i < pCCRecipients.length; i++) {
					toAddress[i] = new InternetAddress(pCCRecipients[i]);
				}

				msg.setRecipients(Message.RecipientType.CC, toAddress);
			}

			// Set BCC recipients
			if (pBCCRecipients != null && pBCCRecipients.length > 0) {
				InternetAddress[] toAddress = new InternetAddress[pBCCRecipients.length];

				for (int i = 0; i < pBCCRecipients.length; i++) {
					toAddress[i] = new InternetAddress(pBCCRecipients[i]);
				}

				msg.setRecipients(Message.RecipientType.BCC, toAddress);
			}

			// Set subject
			msg.setSubject(pSubject, mailEncoding);

			// Create the Multipart
			MimeMultipart mp = new MimeMultipart();

			// Set body
			if (pBody != null) {
				MimeBodyPart boayPart = new MimeBodyPart();

				boayPart.setContent(pBody, getContentType(pBodyType));

				mp.addBodyPart(boayPart);
			}

			// Set attachment
			MimeBodyPart attachmentPart = new MimeBodyPart();
			attachmentPart.setContent(pAttachmentData, "application/pdf");
			attachmentPart.setFileName("pccw-mobile-subscription.pdf");
			mp.addBodyPart(attachmentPart);

			// Set Content
			msg.setContent(mp);

			// Set sent date
			msg.setSentDate(new Date());

			SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");

			transport.connect(mailHost, mailUser, mailPassword);			
			transport.sendMessage(msg, msg.getAllRecipients());

			transport.close();
		}
		catch (Exception e) {
			logger.error("Failure in sending email.", e);
			//BOMExceptionHelper.throwBOMException("Failure in sending email.", e);
		}
	}
	
	private static String getContentType(String mailType) {
		String contentType = null;

		if (mailType != null) {
			if (mailType.equals(MAIL_TYPE_TEXT)) {
				contentType = "text/plain; charset=" + mailEncoding;
			}
			else if (mailType.equals(MAIL_TYPE_HTML)) {
				contentType = "text/html; charset=" + mailEncoding;
			}
			else if (mailType.equals(MAIL_TYPE_PDF)) {
				contentType = "application/pdf; charset=" + mailEncoding;
			}
		}

		return contentType;
	}
}

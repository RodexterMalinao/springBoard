package com.bomwebportal.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;

public interface MailService {
	/*
	Initial copied on 20140925 from Version 1.2 of
	\Springboard\SBWPR3\BomWebPortal\src\com\bomwebportal\service\MailService.java
	*/
	
	void send(InternetAddress from, InternetAddress to, InternetAddress cc, InternetAddress bcc, String emailTitle, String emailContent, List<File> attachments) throws MailException, MessagingException;
	void send(InternetAddress from, InternetAddress[] to, InternetAddress[] cc, InternetAddress[] bcc, String emailTitle, String emailContent, List<File> attachments) throws MailException, MessagingException;
	void sendHtml(InternetAddress from, InternetAddress to, InternetAddress cc, InternetAddress bcc, String emailTitle, String emailContent, List<File> attachments) throws MailException, MessagingException;
	void sendHtml(InternetAddress from, InternetAddress[] to, InternetAddress[] cc, InternetAddress[] bcc, String emailTitle, String emailContent, List<File> attachments) throws MailException, MessagingException;

	void send(InternetAddress from, InternetAddress to, InternetAddress cc, InternetAddress bcc, String emailTitle, String emailContent, Map<String, InputStreamSource> attachments) throws MailException, MessagingException;
	void send(InternetAddress from, InternetAddress[] to, InternetAddress[] cc, InternetAddress[] bcc, String emailTitle, String emailContent, Map<String, InputStreamSource> attachments) throws MailException, MessagingException;
	void sendHtml(InternetAddress from, InternetAddress to, InternetAddress cc, InternetAddress bcc, String emailTitle, String emailContent, Map<String, InputStreamSource> attachments) throws MailException, MessagingException;
	void sendHtml(InternetAddress from, InternetAddress[] to, InternetAddress[] cc, InternetAddress[] bcc, String emailTitle, String emailContent, Map<String, InputStreamSource> attachments) throws MailException, MessagingException;
}
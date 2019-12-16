package com.bomwebportal.mob.ccs.service;

import java.io.IOException;

import javax.mail.MessagingException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.mail.MailException;

import com.bomwebportal.dto.OrderDTO;

public interface MobCcsUrgentOrderAlertService {
	void sendAlert(OrderDTO orderDto) throws MailException, MessagingException, IOException, CompilationFailedException, ClassNotFoundException;
}

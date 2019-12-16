package com.bomltsportal.service.email;

import com.bomltsportal.dto.email.OrderDTO;

public interface OrderService {
	OrderDTO getOrder (String orderId );

	// update esig_email_addr
	int updateEsignEmailAddr(String orderId, String esigEmailAddr, String lastUpdateBy);
}


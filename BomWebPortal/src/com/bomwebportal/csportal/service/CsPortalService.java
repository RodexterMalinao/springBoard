package com.bomwebportal.csportal.service; 

import com.bomwebportal.csportal.object.CsldInqArq;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderMobDTO;

public interface CsPortalService {

	//String verf(String idDocType, String idDocNum, String loginId);

	//String inq(String idDocType, String idDocNum);

	//String regrPro(CustomerProfileDTO custDto, OrderDTO orderDto);
	
	CsldInqArq idCheck(String idDocType, String idDocNum, String myHktId, String theClubId);
}

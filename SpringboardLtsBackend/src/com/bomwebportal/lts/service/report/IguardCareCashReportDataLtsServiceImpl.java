package com.bomwebportal.lts.service.report;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerIguardRegDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.iGuardCareCash.iGuardCareCashDTO;

public class IguardCareCashReportDataLtsServiceImpl implements ReportLtsService {

	@Override
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder,
			String pLocale, String pRptName, boolean pIsEditable,
			boolean pIsPrintSignature) {
		iGuardCareCashDTO rptDTO = (iGuardCareCashDTO)pReportDTO;
		CustomerIguardRegDTO customerIguardReg = LtsSbHelper.getIguardCareCashService(sbOrder);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomerDetailLtsDTO customer = sbOrder.getCustomers()[0];
		ServiceDetailLtsDTO secDelService = (ServiceDetailLtsDTO) LtsSbHelper.get2ndDelService(sbOrder);
		
		try {
			
			if (pIsPrintSignature) {
				byte[] authorisedSign = LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.RPT_SRV_TYPE_IGUARD_CARECASH);
				if(authorisedSign != null){
					rptDTO.setSignatureInd("Y");
					ByteArrayInputStream bis = new ByteArrayInputStream(authorisedSign);
					rptDTO.setCustSignature(bis);
				}
			} else {
				rptDTO.setSignatureInd("N");
			}

			rptDTO.setOrderId(customerIguardReg.getOrderId());
			rptDTO.setShopCd(sbOrder.getShopCd());
			rptDTO.setStaffId(sbOrder.getStaffNum());
			rptDTO.setEmailAddr(customerIguardReg.getEmailAddr());
			rptDTO.setContactPhone(customerIguardReg.getContactNum());
			
			if(StringUtils.isNotBlank(sbOrder.getAppDate())){
				Date appDate = dateFormat.parse(sbOrder.getAppDate());
				rptDTO.setAppDate(appDate);
			}
			
			if(StringUtils.isNotBlank(customer.getDob())){
				Date dob = dateFormat.parse(customer.getDob());
				rptDTO.setDob(dob);
			}
			
			rptDTO.setIdDocNum(customer.getIdDocNum());
			String custEngName = customer.getTitle()+ " " +customer.getLastName()+ " " + customer.getFirstName();
			rptDTO.setCustEngName(custEngName);			
			
			
			if(secDelService != null){
				rptDTO.setLts("2");
			} else {
				rptDTO.setLts("1");
			}
			
			
			if("Y".equals(customerIguardReg.getCarecashDmInd())){
				rptDTO.setPrivacyInd("Y");
			}			
			



		} catch (Exception e) {
			throw new AppRuntimeException(e.getCause());
		}
	}
			
}

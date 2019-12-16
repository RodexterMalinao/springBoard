package com.bomwebportal.lts.service.report;

import org.apache.commons.lang.StringUtils;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.nsdForm.NsdFormRptDTO;

public class NsdReportLtsServiceImpl implements ReportLtsService {
	
	@Override
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder,
			String pLocale, String pRptName, boolean pIsEditable,
			boolean pIsPrintSignature) {
		NsdFormRptDTO rptDTO = (NsdFormRptDTO)pReportDTO;
		
		ServiceDetailDTO portInService = LtsSbHelper.getPortInService(sbOrder.getSrvDtls());
		CustomerDetailLtsDTO customerDetail = portInService.getAccount().getCustomer();
		AddressDetailLtsDTO addressDetail = sbOrder.getAddress();
		BuildingMarkerDTO buildMarker = sbOrder.getBuildingMarker();
		try {

			rptDTO.setSrvNum(portInService.getSrvNum());
			rptDTO.setCustName(customerDetail.getLastName() + " " + customerDetail.getFirstName());
			rptDTO.setCustDocType(customerDetail.getIdDocType());
			rptDTO.setCustDocNum(customerDetail.getIdDocNum());
//			rptDTO.setUserName(customerDetail.getLastName() + " " + customerDetail.getFirstName());
//			rptDTO.setUserDocType(customerDetail.getIdDocType());
//			rptDTO.setUserDocNum(customerDetail.getIdDocNum());
//			rptDTO.setCustAccNum(customerDetail.);
			rptDTO.setTermSrvNum(portInService.getSrvNum());
//			rptDTO.setAutoPayBankName("HONG KONG BANK");
//			rptDTO.setAutoPayBankAccNum("1212252226213");
			rptDTO.setSrvNumList(portInService.getSrvNum());
			
			rptDTO.setUnitNum(addressDetail.getUnitNo());
			rptDTO.setFloorNum(addressDetail.getFloorNo());
//			rptDTO.setBlock(addressDetail.getBuildNo());
			
			if (buildMarker != null) {
				rptDTO.setStreetNum(buildMarker.getStreetNum());
				if (StringUtils.equals(pLocale, LtsBackendConstant.LOCALE_ENG)) {
					rptDTO.setBuilding(buildMarker.getBldgNameEn());
					rptDTO.setStreetName(buildMarker.getStreetNameEn());
					rptDTO.setDistrict(buildMarker.getDistDescEn());
					rptDTO.setArea(buildMarker.getAreaCd());	
				}
				else {
					rptDTO.setBuilding(buildMarker.getBldgNameCh());
					rptDTO.setStreetName(buildMarker.getStreetNameCh());
					rptDTO.setDistrict(buildMarker.getDistDescCh());
					rptDTO.setArea(buildMarker.getAreaCd());	
				}
			}
			rptDTO.setContactName(portInService.getAppointmentDtl().getInstContactName());
			rptDTO.setContactPhone(portInService.getAppointmentDtl().getInstContactNum());
//			rptDTO.setReciptNetworkOper("Smartone");

		} catch (Exception e) {
			throw new AppRuntimeException(e.getCause());
		}
	}

}

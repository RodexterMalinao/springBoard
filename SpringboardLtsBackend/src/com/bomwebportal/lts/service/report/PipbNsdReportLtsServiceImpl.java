package com.bomwebportal.lts.service.report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.nsdFormPipb.NsdFormPipbRptDTO;

public class PipbNsdReportLtsServiceImpl implements ReportLtsService {
	
	@Override
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder,
			String pLocale, String pRptName, boolean pIsEditable,
			boolean pIsPrintSignature) {
		NsdFormPipbRptDTO rptDTO = (NsdFormPipbRptDTO)pReportDTO;
		CustomerDetailLtsDTO customerDetail = new CustomerDetailLtsDTO();
		
		ServiceDetailLtsDTO pipbService = LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls());
		if (pipbService.getAccount() != null){
			customerDetail = pipbService.getAccount().getCustomer();
		}else{
			customerDetail = LtsSbHelper.getLtsService(sbOrder).getAccount().getCustomer();
		}	
		AddressDetailLtsDTO addressDetail = sbOrder.getAddress();
		BuildingMarkerDTO buildMarker = sbOrder.getBuildingMarker();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");			
		Date date = new Date();
		String custName = null;
		String street = null;
		try {

			rptDTO.setPortFrom(pipbService.getPipb().getOperator2n());
			
			String numList = null; 
			numList = LtsSbHelper.getDisplaySrvNum(pipbService.getSrvNum());
			
			
			if(StringUtils.equals("Y", pipbService.getPipb().getFromDiffCustInd())){
				custName = StringUtils.isNotBlank(pipbService.getPipb().getCompanyName()) ? pipbService.getPipb().getCompanyName() : pipbService.getPipb().getLastName() + " " + pipbService.getPipb().getFirstName();
				rptDTO.setCustName(custName);
				rptDTO.setAuthorisedName(custName);
				rptDTO.setCustDocType(pipbService.getPipb().getIdDocType());
				rptDTO.setCustDocNum(pipbService.getPipb().getIdDocNum());
			}else if(StringUtils.equals("N", pipbService.getPipb().getFromDiffCustInd()) 
					&& (StringUtils.isNotBlank(pipbService.getPipb().getCompanyName()) || StringUtils.isNotBlank(pipbService.getPipb().getLastName()) || StringUtils.isNotBlank(pipbService.getPipb().getFirstName()))){
				custName = StringUtils.isNotBlank(pipbService.getPipb().getCompanyName()) ? pipbService.getPipb().getCompanyName() : pipbService.getPipb().getLastName() + " " + pipbService.getPipb().getFirstName();
				rptDTO.setCustName(custName);
				rptDTO.setAuthorisedName(custName);
				rptDTO.setCustDocType(pipbService.getPipb().getIdDocType());
				rptDTO.setCustDocNum(pipbService.getPipb().getIdDocNum());
			}else{
				custName = StringUtils.isNotBlank(customerDetail.getCompanyName()) ? customerDetail.getCompanyName() : customerDetail.getLastName() + " " + customerDetail.getFirstName();
				rptDTO.setCustName(custName);
				rptDTO.setAuthorisedName(custName);
				rptDTO.setCustDocType(customerDetail.getIdDocType());
				rptDTO.setCustDocNum(customerDetail.getIdDocNum());
			}			
			
//			if(StringUtils.equals("Y", pipbService.getPipb().getFromDiffAddrInd())){
				rptDTO.setUnitNum(pipbService.getPipb().getUnitNo());
				rptDTO.setFloorNum(pipbService.getPipb().getFloorNo());
				rptDTO.setBuilding(pipbService.getPipb().getBuildNo());
				rptDTO.setBlock(pipbService.getPipb().getBlockNo());
				street = (StringUtils.isEmpty(pipbService.getPipb().getStrName())? "" : pipbService.getPipb().getStrName())+" "+(StringUtils.isEmpty(pipbService.getPipb().getStrCatDesc())? "" : pipbService.getPipb().getStrCatDesc());
				rptDTO.setStreetName(street);
				rptDTO.setStreetNum(pipbService.getPipb().getStrNo());
				rptDTO.setDistrict(pipbService.getPipb().getDistDesc());
				rptDTO.setArea(pipbService.getPipb().getAreaCd());
				rptDTO.setLotNum(pipbService.getPipb().getHiLotNo());
				rptDTO.setEstate(pipbService.getPipb().getSectDesc());

//			}else{
//				rptDTO.setUnitNum(addressDetail.getUnitNo());
//				rptDTO.setFloorNum(addressDetail.getFloorNo());
//				rptDTO.setBuilding(addressDetail.getBuildNo());
//				rptDTO.setStreetName(addressDetail.getStrName()+" "+addressDetail.getStrCatDesc());
//				rptDTO.setStreetNum(addressDetail.getStrNo());
//				rptDTO.setDistrict(addressDetail.getDistDesc());
//				rptDTO.setArea(addressDetail.getAreaCd());
//				rptDTO.setLotNum(addressDetail.getHiLotNo());
//				rptDTO.setEstate(addressDetail.getSectDesc());
//			}
			
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
			rptDTO.setContactName(rptDTO.getCustName());
			if(sbOrder.getContact()!=null){
				if(StringUtils.isNotBlank(sbOrder.getContact().getContactMobile())){
					rptDTO.setContactPhone(sbOrder.getContact().getContactMobile());
				}else{
					rptDTO.setContactPhone(sbOrder.getContact().getContactFixedLine());
				}
			}
			String srvNumList = null; 
			srvNumList = LtsSbHelper.getDisplaySrvNum(pipbService.getSrvNum());
			if(StringUtils.equals("Y",pipbService.getPipb().getDuplexInd())){
				if(StringUtils.isNotBlank(pipbService.getPipb().getDuplexDn())
						&& StringUtils.equals(LtsBackendConstant.LTS_PIPB_DUPLEX_ACTION_PORT_IN_TOGETHER, pipbService.getPipb().getDuplexAction())){
					srvNumList = srvNumList + " " + LtsSbHelper.getDisplaySrvNum(pipbService.getPipb().getDuplexDn());
//					rptDTO.setDuplexSrvNum(srvNumList);
//					rptDTO.setDuplex(true);
					numList = numList + " " + LtsSbHelper.getDisplaySrvNum(pipbService.getPipb().getDuplexDn());
				}else if(StringUtils.equals(LtsBackendConstant.LTS_PIPB_DUPLEX_ACTION_DISCONNECT, pipbService.getPipb().getDuplexAction())){
					rptDTO.setTermDuplex(true);
					rptDTO.setDuplex(true);
					if(StringUtils.isNotBlank(pipbService.getPipb().getDuplexDn())){
//						rptDTO.setTermSrvNum(LtsSbHelper.getDisplaySrvNum(pipbService.getPipb().getDuplexDn()));
//						rptDTO.setDuplexSrvNum(LtsSbHelper.getDisplaySrvNum(pipbService.getPipb().getDuplexDn()) + " " + LtsSbHelper.getDisplaySrvNum(pipbService.getSrvNum()));
						numList = numList + " " + LtsSbHelper.getDisplaySrvNum(pipbService.getPipb().getDuplexDn());
//						srvNumList = srvNumList + " " + LtsSbHelper.getDisplaySrvNum(pipbService.getPipb().getDuplexDn());
//					}else{
//						rptDTO.setDuplexSrvNum(LtsSbHelper.getDisplaySrvNum(pipbService.getSrvNum()));
					}
				}else if (StringUtils.equals(LtsBackendConstant.LTS_PIPB_DUPLEX_ACTION_RETAIN, pipbService.getPipb().getDuplexAction())){
					if(StringUtils.isNotBlank(pipbService.getPipb().getDuplexDn())){
//						rptDTO.setDuplexSrvNum(LtsSbHelper.getDisplaySrvNum(pipbService.getPipb().getDuplexDn()) + " " + LtsSbHelper.getDisplaySrvNum(pipbService.getSrvNum()));
						numList = numList + "(porting) " + LtsSbHelper.getDisplaySrvNum(pipbService.getPipb().getDuplexDn())+"(retain at exiting 2N)";
//					}else{
//						rptDTO.setDuplexSrvNum(LtsSbHelper.getDisplaySrvNum(pipbService.getSrvNum()));
					}					
				}
			}
			rptDTO.setNumList(numList);
			rptDTO.setSrvNumList(srvNumList);
			rptDTO.setReciptNetworkOper("PCCW-HKT");
			rptDTO.setAuthorisedDate(dateFormat.format(date));
			if (pIsPrintSignature) {
				byte[] authorisedSign = LtsSbHelper.getSignature(sbOrder, LtsBackendConstant.SIGN_TYPE_PIPB_NSD);
			    rptDTO.setAuthorisedSign(authorisedSign);
			}
			rptDTO.putFieldCss("portFrom", "font-size:14");

		} catch (Exception e) {
			throw new AppRuntimeException(e.getCause());
		}
	}

}

package com.bomwebportal.lts.service.report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dao.UpdNnDAO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.crfFormPipb.CrfFormPipbRptDTO;
import com.bomwebportal.lts.wsClientLts.BomWsBackendClient;
import com.pccw.dwfmGateway.orderInformation.GetOrderInformationResponse;
import com.pccw.dwfmGateway.orderInformation.OrderInformationTgtVoiceReturn;


public class PipbCrfReportLtsServiceImpl implements ReportLtsService {
	
	private final Log logger = LogFactory.getLog(getClass());
	private BomWsBackendClient bomWsBackendClient;
	private UpdNnDAO updNnDAO;
	@Override
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder,
			String pLocale, String pRptName, boolean pIsEditable,
			boolean pIsPrintSignature) {
		
		logger.debug("-----CRF check point 0 fill CRF begin");
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");			
		Date date = new Date();
		CrfFormPipbRptDTO rptDTO = (CrfFormPipbRptDTO)pReportDTO;
		
		ServiceDetailLtsDTO pipbService = LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls());
		ServiceDetailLtsDTO secDelService = (ServiceDetailLtsDTO) LtsSbHelper.get2ndDelService(sbOrder);
		CustomerDetailLtsDTO customerDetail = new CustomerDetailLtsDTO();
		int actvSeq = 1;
		String street = null;

		
		if (pipbService.getAccount() != null){
			customerDetail = pipbService.getAccount().getCustomer();
		}else{
			customerDetail = LtsSbHelper.getLtsService(sbOrder).getAccount().getCustomer();
		}
		AddressDetailLtsDTO addressDetail = sbOrder.getAddress();
		BuildingMarkerDTO buildMarker = sbOrder.getBuildingMarker();
		try {
			logger.debug("-----Generate CRF begin. order id: " + sbOrder.getOrderId());
			rptDTO.setSystemDate(dateFormat.format(date));

			logger.debug("-----CRF check point 1 fill srv no ");
			rptDTO.setSrvNum1(LtsSbHelper.getDisplaySrvNum(pipbService.getSrvNum()));
//			rptDTO.setSrvType1(pipbService.getTypeOfSrv());
			rptDTO.setSrvType1("T");
			logger.debug("-----CRF check point 2 chk port in ");
			if(StringUtils.equals(pipbService.getPipb().getIsPortBack(), "N")){
				if(StringUtils.isNotBlank(pipbService.getSrvNn()) && !StringUtils.equals(pipbService.getSrvNn(), "0")){
					rptDTO.setNnNum1(pipbService.getSrvNn());
				}else{
					if(StringUtils.isNotBlank(pipbService.getLegacyOrdNum())){
						if(StringUtils.isNotBlank(pipbService.getLegacyActvSeq())){
							actvSeq = Integer.parseInt(pipbService.getLegacyActvSeq());
						}
						logger.debug("-----CRF check point 3 get NN ");
						GetOrderInformationResponse result = bomWsBackendClient.getOrderInformation(pipbService.getLegacyOrdNum(), actvSeq);
						if(result.getGetOrderInformationResult()!=null && StringUtils.equals(result.getGetOrderInformationResult().getErrorSeverity(), "0")){
							List<OrderInformationTgtVoiceReturn> ordInfoTgtVoiceRtnList = result.getGetOrderInformationResult().getOrderInformationOutputList().getTargetVoiceReturn().getOrderInformationTgtVoiceReturn();
							if(StringUtils.isNotBlank(ordInfoTgtVoiceRtnList.get(0).getSrvNn())&& !StringUtils.equals(ordInfoTgtVoiceRtnList.get(0).getSrvNn(), "0")){
								rptDTO.setNnNum1(ordInfoTgtVoiceRtnList.get(0).getSrvNn());
							}
							logger.debug("-----CRF check point 4 upd NN ");
							updNnDAO.updateSrvNn(pipbService, sbOrder.getOrderId(), ordInfoTgtVoiceRtnList.get(0).getSrvNn());
							/*					for(OrderInformationTgtVoiceReturn ordInfoTgtVoiceRtn : ordInfoTgtVoiceRtnList){
							if(StringUtils.equals(LtsSbHelper.getDisplaySrvNum(ordInfoTgtVoiceRtn.getNewSrvNum()), LtsSbHelper.getDisplaySrvNum(pipbService.getSrvNum()))){
								rptDTO.setNnNum1(ordInfoTgtVoiceRtn.getSrvNn());
							}
						}*/
						}

					}
				}
			}
			logger.debug("-----CRF check point 5 chk duplex ");	
			if(StringUtils.equals("Y",pipbService.getPipb().getDuplexInd())){
				logger.debug("-----CRF check point 6 PIPB_DUPLEX_ACTION_PORT_IN_TOGETHER ");	
				if(StringUtils.equals(LtsBackendConstant.LTS_PIPB_DUPLEX_ACTION_PORT_IN_TOGETHER, pipbService.getPipb().getDuplexAction())){
					rptDTO.setSrvNum2(LtsSbHelper.getDisplaySrvNum(pipbService.getPipb().getDuplexDn()));
//					rptDTO.setSrvType2(secDelService.getTypeOfSrv());
					rptDTO.setSrvType2("T");
					logger.debug("-----CRF check point 7 chk port in ");
					boolean isPortBack = LtsSbHelper.isPortBackForPipb(secDelService);			
//					for (int i=0; i<LtsBackendConstant.DRG_DN_PORT_OUT_STATUS.length && StringUtils.isNotBlank(secDelService.getDnStatus()); i++) {
//						if (StringUtils.equals(LtsBackendConstant.DRG_DN_PORT_OUT_STATUS[i], secDelService.getDnStatus())) {
//							isPortOut = true;
//							break;
//						}
//					}
					if(!isPortBack){
						if(StringUtils.isNotBlank(secDelService.getSrvNn())&& !StringUtils.equals(secDelService.getSrvNn(), "0")){
							rptDTO.setNnNum2(secDelService.getSrvNn());
						}else{
							if(StringUtils.isNotBlank(pipbService.getLegacyOrdNum())){
								if(StringUtils.isNotBlank(secDelService.getLegacyActvSeq())){
									actvSeq = Integer.parseInt(secDelService.getLegacyActvSeq());
								}
								logger.debug("-----CRF check point 8 get NN ");
								GetOrderInformationResponse secDelresult = bomWsBackendClient.getOrderInformation(secDelService.getLegacyOrdNum(), actvSeq);
								if(secDelresult.getGetOrderInformationResult()!=null && StringUtils.equals(secDelresult.getGetOrderInformationResult().getErrorSeverity(), "0")){
									List<OrderInformationTgtVoiceReturn> secOrdInfoTgtVoiceRtnList = secDelresult.getGetOrderInformationResult().getOrderInformationOutputList().getTargetVoiceReturn().getOrderInformationTgtVoiceReturn();
									if(StringUtils.isNotBlank(secOrdInfoTgtVoiceRtnList.get(0).getSrvNn())&& !StringUtils.equals(secOrdInfoTgtVoiceRtnList.get(0).getSrvNn(), "0")){
										rptDTO.setNnNum1(secOrdInfoTgtVoiceRtnList.get(0).getSrvNn());
									}
									logger.debug("-----CRF check point 9 upd NN ");
									updNnDAO.updateSrvNn(secDelService, sbOrder.getOrderId(), secOrdInfoTgtVoiceRtnList.get(0).getSrvNn());
								}
							}
						}
					}
				}
			}
			
			logger.debug("-----CRF check point 10 chk diff cust ");
			if(StringUtils.equals("Y", pipbService.getPipb().getFromDiffCustInd())){
				rptDTO.setCustName(StringUtils.isNotBlank(pipbService.getPipb().getCompanyName()) ? pipbService.getPipb().getCompanyName() : pipbService.getPipb().getLastName() + " " + pipbService.getPipb().getFirstName());
			}else if(StringUtils.equals("N", pipbService.getPipb().getFromDiffCustInd()) 
					&& (StringUtils.isNotBlank(pipbService.getPipb().getCompanyName()) || StringUtils.isNotBlank(pipbService.getPipb().getLastName()) || StringUtils.isNotBlank(pipbService.getPipb().getFirstName()))){
				rptDTO.setCustName(StringUtils.isNotBlank(pipbService.getPipb().getCompanyName()) ? pipbService.getPipb().getCompanyName() : pipbService.getPipb().getLastName() + " " + pipbService.getPipb().getFirstName());
			}else{
				logger.debug("-----CRF check point 11 diff cust N ");
				rptDTO.setCustName(StringUtils.isNotBlank(customerDetail.getCompanyName()) ? customerDetail.getCompanyName() : customerDetail.getLastName() + " " + customerDetail.getFirstName());
			}
			
//			rptDTO.setSrvType2N("T");
			rptDTO.setSbId(sbOrder.getOrderId());
			
			logger.debug("-----CRF check point 12 set 2N port from ");
			rptDTO.setPortFromWt(StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_WTNT)||
					StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_WTT));
			rptDTO.setPortFromNwt(StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_NWT));
			rptDTO.setPortFromHgc(StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_HGC));
			rptDTO.setPortFromHkbn(StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_HKBN));
			rptDTO.setPortFromHkc(StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_HKC));
			rptDTO.setPortFromSsl(StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_SSL));
			rptDTO.setPortFromComnet(StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_COMNET));
			rptDTO.setPortFromCsl(StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_CSL)||
					StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_HKT));
			rptDTO.setPortFromPc(StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_PC));
			rptDTO.setPortFromVz(StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_VZ));
			rptDTO.setPortFromCmhk(StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_CMHK));
			rptDTO.setPortFromHkbnes(StringUtils.equals(pipbService.getPipb().getOperator2n(), LtsBackendConstant.LTS_PIPB_PORT_FROM_HKBNES));
			
			logger.debug("-----CRF check point 13 set addr ");
			if(StringUtils.equals("Y", pipbService.getPipb().getFromDiffAddrInd())){
				rptDTO.setNewAddr(true);
				rptDTO.setUnitNo(addressDetail.getUnitNo());
				rptDTO.setFloor(addressDetail.getFloorNo());
				rptDTO.setBuilding(addressDetail.getBuildNo());
				street = (StringUtils.isEmpty(addressDetail.getStrName())? "" : addressDetail.getStrName())+" "+(StringUtils.isEmpty(addressDetail.getStrCatDesc())? "" : addressDetail.getStrCatDesc());
				rptDTO.setStreetName(street);
				rptDTO.setStreetNo(addressDetail.getStrNo());
				rptDTO.setDistrict(addressDetail.getDistDesc());
				rptDTO.setArea(addressDetail.getAreaCd());
				rptDTO.setLotNo(addressDetail.getHiLotNo());
				rptDTO.setEstate(addressDetail.getSectDesc());
			}else{
				rptDTO.setNewAddr(false);
			}
			
			logger.debug("-----CRF check point 14 set date ");
			rptDTO.setReqCutDate(LtsDateFormatHelper.getDateFromDTOFormat(pipbService.getAppointmentDtl().getCutOverStartTime()));
			rptDTO.setCutOverTime(LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(pipbService.getAppointmentDtl().getCutOverStartTime(), pipbService.getAppointmentDtl().getCutOverEndTime())));
            
			logger.debug("-----CRF check point 15 buildMarker ");
			if (buildMarker != null) {
				rptDTO.setStreetNo(buildMarker.getStreetNum());
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
			rptDTO.setSalesName(sbOrder.getSalesName());
			rptDTO.setSalesTel(sbOrder.getSalesContactNum());
			
			logger.debug("-----CRF check point 16 fill completed ");


		} catch (Exception e) {
			throw new AppRuntimeException(e.getCause());
		}
	}
	public BomWsBackendClient getBomWsBackendClient() {
		return bomWsBackendClient;
	}
	public void setBomWsBackendClient(BomWsBackendClient bomWsBackendClient) {
		this.bomWsBackendClient = bomWsBackendClient;
	}
	public UpdNnDAO getUpdNnDAO() {
		return updNnDAO;
	}
	public void setUpdNnDAO(UpdNnDAO updNnDAO) {
		this.updNnDAO = updNnDAO;
	}
	
	

}

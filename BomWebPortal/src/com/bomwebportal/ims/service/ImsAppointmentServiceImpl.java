package com.bomwebportal.ims.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.ConstantLkupDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.AppointmentDAO;
import com.bomwebportal.ims.dao.ImsAddressInfoDAO;
import com.bomwebportal.ims.dto.AddrInventoryDTO;
import com.bomwebportal.ims.dto.AppointmentPermitPropertyDtlDTO;
import com.bomwebportal.ims.dto.AppointmentReserveDtlDTO;
import com.bomwebportal.ims.dto.AppointmentSubmitDTO;
import com.bomwebportal.ims.dto.AppointmentTimeSlotDTO;
import com.bomwebportal.ims.dto.ImsServiceSrdDTO;
import com.bomwebportal.ims.dto.ServiceDetailDTO;
import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.InstallAddrUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

@Transactional(readOnly=true)
public class ImsAppointmentServiceImpl implements ImsAppointmentService{
	
	public ConstantLkupDAO getConstantLkupDao() {
		return constantLkupDao;
	}

	public void setConstantLkupDao(ConstantLkupDAO constantLkupDao) {
		this.constantLkupDao = constantLkupDao;
	}

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsAddressInfoDAO imsAddressInfoDao;
	
	private ConstantLkupDAO constantLkupDao;

	private AppointmentDAO appointmentDao;
	
	public ImsServiceSrdDTO getServiceSrdComparedWithBMO(String isBlAddr, String isBlCust, String isPccwLn, Date appntDateTime, String sbno, String flat, String floor, String isCC, String isPT, String isDS, 
			String i_has_bb_srv, String i_has_nowtv_srv, String mismatch, String fsPrepay, String fsInd, String i_must_qc_ind, String i_ride_on_fsa, 
			String orderType, String supremeFsInd)
	{
		return getServiceSrdComparedWithBMO(isBlAddr, isBlCust, isPccwLn, appntDateTime, sbno, flat, floor, isCC, isPT, isDS, 
				i_has_bb_srv, i_has_nowtv_srv, mismatch, fsPrepay, fsInd, i_must_qc_ind, i_ride_on_fsa, 
				orderType, null, supremeFsInd);
	}
	
	public ImsServiceSrdDTO getServiceSrdComparedWithBMO(String isBlAddr, String isBlCust, String isPccwLn, Date appntDateTime, String sbno, String flat, String floor, String isCC, String isPT, String isDS, 
			String i_has_bb_srv, String i_has_nowtv_srv, String mismatch, String fsPrepay, String fsInd, String i_must_qc_ind, String i_ride_on_fsa, 
			String orderType, OrderImsUI order, String supremeFsInd){
		
		logger.debug("getServiceSrd is called");
		ImsServiceSrdDTO addressInfo = new ImsServiceSrdDTO();
		AppointmentPermitPropertyDtlDTO bmoFTHIInfo = new AppointmentPermitPropertyDtlDTO();
		AppointmentPermitPropertyDtlDTO bmoPCDIInfo = new AppointmentPermitPropertyDtlDTO();
		String sysId= "";
		if ( "Y".equals(isCC))
		{
			if ("Y".equals(isPT))
				sysId = "CC_PT";
			else
				sysId = "CC";
		} else if ( "Y".equals(isDS)){
			sysId = "DS";
		}
		else
		{
			sysId = "RETAIL";
		}
		if ("NTV-A".equals(orderType)) {
			sysId = sysId + "-TV";
		}		
		if ("NTVAO".equals(orderType)||"NTVUS".equals(orderType)||"NTVRE".equals(orderType)) {
			sysId = sysId + "_RET-TV";
		}	
		
		try{			
			
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");  
	        Date date = new Date();  
	        dateFormat.format(date);
	        String oldTech = order!=null && order.getServiceIms() != null?order.getServiceIms().getLineType():"";
			if(appntDateTime == null){
				if(supremeFsInd!=null&&("A".equalsIgnoreCase(supremeFsInd)||"V".equalsIgnoreCase(supremeFsInd))){
					bmoFTHIInfo = getBMOInfo(sbno, "SUP", date, oldTech);
					bmoPCDIInfo = getBMOInfo(sbno, "SUC", date, oldTech);
				}else{
					bmoFTHIInfo = getBMOInfo(sbno, "FTH", date, oldTech);
					bmoPCDIInfo = getBMOInfo(sbno, "PCD", date, oldTech);
				}
			}else{
				if(supremeFsInd!=null&&("A".equalsIgnoreCase(supremeFsInd)||"V".equalsIgnoreCase(supremeFsInd))){
					bmoFTHIInfo = getBMOInfo(sbno, "SUP", appntDateTime, oldTech);
					bmoPCDIInfo = getBMOInfo(sbno, "SUC", appntDateTime, oldTech);
				}else{
					bmoFTHIInfo = getBMOInfo(sbno, "FTH", appntDateTime, oldTech);
					bmoPCDIInfo = getBMOInfo(sbno, "PCD", appntDateTime, oldTech);
				}
			}
			
			//addressInfo.setBmoRmk(bmoFTHIInfo.getBmoRemark());
			//addressInfo.setBmoAlert(bmoFTHIInfo.getAlertMsg());
			addressInfo = imsAddressInfoDao.getServiceSrd(isBlAddr, isBlCust, isPccwLn, appntDateTime, sbno, flat, floor, sysId, i_has_bb_srv, i_has_nowtv_srv, mismatch, fsPrepay, fsInd, i_must_qc_ind, i_ride_on_fsa,
					bmoFTHIInfo.getPermitLeadDays(),
					bmoFTHIInfo.getEarliestApptDate(),
					bmoPCDIInfo.getPermitLeadDays(),
					bmoPCDIInfo.getEarliestApptDate(),order);
			
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			logger.info("PCDI Earliest Date:"+dateFormat.format(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8))) + 
			            "FTHI Earliest Date:"+dateFormat.format(Utility.string2Date(bmoFTHIInfo.getEarliestApptDate().substring(0, 4), bmoFTHIInfo.getEarliestApptDate().substring(4, 6), bmoFTHIInfo.getEarliestApptDate().substring(6, 8))));

			Map<String, String> logMap = new HashMap<String, String>();
			for(int i=0; i<addressInfo.getServiceDetailList().size(); i++){
				if(addressInfo.getServiceDetailList().get(i).getEstEarliestSrd()!=null){
//					logger.info("Tech"+addressInfo.getServiceDetailList().get(i).getTechnology() + 
//							"Earliest SRD:"+addressInfo.getServiceDetailList().get(i).getEstEarliestSrd());
					logMap.put(addressInfo.getServiceDetailList().get(i).getTechnology()+"Earliest SRD:", addressInfo.getServiceDetailList().get(i).getEstEarliestSrd()+"");
					if(addressInfo.getServiceDetailList().get(i).getTechnology().equalsIgnoreCase("PON")){					
						if(addressInfo.getServiceDetailList().get(i).getEstEarliestSrd().before(Utility.string2Date(bmoFTHIInfo.getEarliestApptDate().substring(0, 4), bmoFTHIInfo.getEarliestApptDate().substring(4, 6), bmoFTHIInfo.getEarliestApptDate().substring(6, 8)))){
							addressInfo.getServiceDetailList().get(i).setEstEarliestSrd(Utility.string2Date(bmoFTHIInfo.getEarliestApptDate().substring(0, 4), bmoFTHIInfo.getEarliestApptDate().substring(4, 6), bmoFTHIInfo.getEarliestApptDate().substring(6, 8)));
							addressInfo.getServiceDetailList().get(i).setRtnDesc("PON BMO, T+"+addressInfo.getServiceDetailList().get(i).getLeadTime());
						}
						addressInfo.getServiceDetailList().get(i).setLeadTime(Integer.parseInt(bmoFTHIInfo.getPermitLeadDays()));
						
						addressInfo.getServiceDetailList().get(i).setBmoAlert(bmoFTHIInfo.getAlertMsg());
						addressInfo.getServiceDetailList().get(i).setBmoRmk(bmoFTHIInfo.getBmoRemark());
						addressInfo.getServiceDetailList().get(i).setBmoLeadDay(bmoFTHIInfo.getPermitLeadDays());
					}else if(addressInfo.getServiceDetailList().get(i).getTechnology().equalsIgnoreCase("ADSL")){		
						if(addressInfo.getServiceDetailList().get(i).getEstEarliestSrd().before(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)))){
							addressInfo.getServiceDetailList().get(i).setEstEarliestSrd(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)));
							addressInfo.getServiceDetailList().get(i).setRtnDesc("ADSL BMO, T+"+addressInfo.getServiceDetailList().get(i).getLeadTime());
						}
						addressInfo.getServiceDetailList().get(i).setLeadTime(Integer.parseInt(bmoPCDIInfo.getPermitLeadDays()));
						
						addressInfo.getServiceDetailList().get(i).setBmoAlert(bmoPCDIInfo.getAlertMsg());
						addressInfo.getServiceDetailList().get(i).setBmoRmk(bmoPCDIInfo.getBmoRemark());
						addressInfo.getServiceDetailList().get(i).setBmoLeadDay(bmoPCDIInfo.getPermitLeadDays());
					}else if(addressInfo.getServiceDetailList().get(i).getTechnology().equalsIgnoreCase("VDSL")){						
						if(addressInfo.getServiceDetailList().get(i).getEstEarliestSrd().before(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)))){
							addressInfo.getServiceDetailList().get(i).setEstEarliestSrd(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)));
							addressInfo.getServiceDetailList().get(i).setRtnDesc("VDSL BMO, T+"+addressInfo.getServiceDetailList().get(i).getLeadTime());
						}
						addressInfo.getServiceDetailList().get(i).setLeadTime(Integer.parseInt(bmoPCDIInfo.getPermitLeadDays()));
						
						addressInfo.getServiceDetailList().get(i).setBmoAlert(bmoPCDIInfo.getAlertMsg());
						addressInfo.getServiceDetailList().get(i).setBmoRmk(bmoPCDIInfo.getBmoRemark());
						addressInfo.getServiceDetailList().get(i).setBmoLeadDay(bmoPCDIInfo.getPermitLeadDays());
					}else if(addressInfo.getServiceDetailList().get(i).getTechnology().equalsIgnoreCase("Vectoring")){					
						if(addressInfo.getServiceDetailList().get(i).getEstEarliestSrd().before(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)))){
							addressInfo.getServiceDetailList().get(i).setEstEarliestSrd(Utility.string2Date(bmoPCDIInfo.getEarliestApptDate().substring(0, 4), bmoPCDIInfo.getEarliestApptDate().substring(4, 6), bmoPCDIInfo.getEarliestApptDate().substring(6, 8)));
							addressInfo.getServiceDetailList().get(i).setRtnDesc("Vectoring BMO, T+"+addressInfo.getServiceDetailList().get(i).getLeadTime());
						}
						addressInfo.getServiceDetailList().get(i).setLeadTime(Integer.parseInt(bmoPCDIInfo.getPermitLeadDays()));
						
						addressInfo.getServiceDetailList().get(i).setBmoAlert(bmoPCDIInfo.getAlertMsg());
						addressInfo.getServiceDetailList().get(i).setBmoRmk(bmoPCDIInfo.getBmoRemark());
						addressInfo.getServiceDetailList().get(i).setBmoLeadDay(bmoPCDIInfo.getPermitLeadDays());
					}
				}				
			}
			if(order!=null&&order.getInstallAddress()!=null&&addressInfo!=null){
				order.getInstallAddress().setNtvCoverage(addressInfo.getNtvCoverage());
				if(order.getInstallAddress().getAddrInventory()!=null){
					order.getInstallAddress().getAddrInventory().setNtvCoverage(addressInfo.getNtvCoverage());
				}
			}
			logger.info("SRD:"+ new Gson().toJson(logMap));
			String newTech=null;
			if(order!=null&&order.getInstallAddress()!=null&&order.getInstallAddress().getAddrInventory()!=null){
				 newTech = order.getInstallAddress().getAddrInventory().getTechnology();
			}
			if(oldTech!=null&&newTech!=null&&order.isOrderTypeNowRet()&&oldTech.equals(newTech)){//for mantis 17699, cannot recreate 36881825, but newTech = oldTech = PON shortage Error
		        String toProdSubTypeCd=ImsConstants.DWFM_NON_PON_CHANGE;
		        String fromProdSubTypeCd=ImsConstants.DWFM_NON_PON_CHANGE;
				if("PON".equals(oldTech)){
					fromProdSubTypeCd = ImsConstants.DWFM_PON_CHANGE;
				}
				if("PON".equals(newTech)){
					toProdSubTypeCd = ImsConstants.DWFM_PON_CHANGE;
				}
				AppointmentPermitPropertyDtlDTO dwfm = appointmentDao.getPermitPropertyDtl(sbno, toProdSubTypeCd, fromProdSubTypeCd, Utility.date2String(new Date(), "yyyyMMdd"), orderType, "N");
				logger.info("find max(T+2,DWFM)");
				Date fieldServiceDate = Utility.string2Date(
						dwfm.getEarliestApptDate().substring(0, 4), 
						dwfm.getEarliestApptDate().substring(4, 6), 
						dwfm.getEarliestApptDate().substring(6, 8));
				ServiceDetailDTO dto = new ServiceDetailDTO();
				dto.setTechnologySupported("Y");
				dto.setIsDeadCase("N");
				dto.setTechnology(oldTech);
				dto.setEstEarliestSrd(fieldServiceDate);
				dto.setLeadTime(Integer.parseInt(dwfm.getPermitLeadDays()));
				dto.setBmoAlert(dwfm.getAlertMsg());
				dto.setBmoRmk(dwfm.getBmoRemark());
				Calendar c = Calendar.getInstance();// today
				c.add(Calendar.DATE, 2);  // add 2 day
				String tPlusTwo = dateFormat.format(c.getTime());  
				Date t2 = new Date();
				try {
					t2 = dateFormat.parse(tPlusTwo);
				} catch (ParseException e) {
					logger.error(e);
				}
				if(dto.getEstEarliestSrd().before(t2)){
					dto.setEstEarliestSrd(t2);// at least t+2
				}
				ArrayList<ServiceDetailDTO> a=new ArrayList<ServiceDetailDTO>();
				a.add(dto);
				addressInfo.setServiceDetailList(a);
			}
		}catch (DAOException de) {			
			throw new AppRuntimeException("", new Exception(ExceptionUtils.getStackTrace(de)));
		}

		return addressInfo;
	}
	
	protected AppointmentPermitPropertyDtlDTO getBMOInfo(String serviceBoundary, String prodSubType, Date applicationDate, String oldTech) throws DAOException
	{
		
		return appointmentDao.getPermitPropertyDtl(serviceBoundary, prodSubType+"I", Utility.date2String(applicationDate, "yyyyMMdd"));
//		if ("FTH".equals(prodSubType))
//		{
//			return appointmentDao.getPermitPropertyDtl(serviceBoundary, "FTHI", Utility.date2String(applicationDate, "yyyyMMdd"));
//		}
//		else
//		{
//			return appointmentDao.getPermitPropertyDtl(serviceBoundary, "PCDI", Utility.date2String(applicationDate, "yyyyMMdd"));
//		}
	}
	
	//Called by nowTV ACQ
	public ImsServiceSrdDTO getServiceSrdComparedWithBMO(Date appntDateTime, 
			String isDS, String i_has_bb_srv, String i_has_nowtv_srv, 
			String mismatch, String fsPrepay, String fsInd, OrderImsUI order){
		
		String isBlAddr = order.getInstallAddress().getBlacklistInd();
		if(order.isOrderTypeNowRet()){
			isBlAddr="N";
		}
		String isBlCust = order.getCustomer().getBlacklistInd();
		String isPccwLn = (order.getFixedLineNo()!=null && !order.getFixedLineNo().equals(""))?"Y":"N";
		String sbno = order.getInstallAddress().getSerbdyno();
		String flat = order.getInstallAddress().getUnitNo();
		String floor = order.getInstallAddress().getFloorNo();
		String isCC =  order.getIsCC();
		String isPT = order.getIsPT();
		String i_must_qc_ind = "11".equals(order.getOrderStatus())?"N":order.getMust_QC_Ind();
		String i_ride_on_fsa = order.getRide_on_FSA_Ind();
		String orderType = order.getOrderType();
		
		
		ImsServiceSrdDTO srdDto = getServiceSrdComparedWithBMO(isBlAddr, isBlCust, isPccwLn, appntDateTime,
				sbno, flat, floor, isCC, isPT, isDS, i_has_bb_srv, i_has_nowtv_srv,
				mismatch, fsPrepay, fsInd, i_must_qc_ind, i_ride_on_fsa, orderType, order, null);
		
		logger.info("srdDto: "+new Gson().toJson(srdDto));
		
		order.getInstallAddress().setServiceDetailList(srdDto.getServiceDetailList());
		order.getInstallAddress().setHousingTypeList(srdDto.getHousingTypeList());
		order.getInstallAddress().setBandwidthList(srdDto.getBandwidthList());
		if (order.getInstallAddress().getAddrInventory() == null) {
			AddrInventoryDTO addrInventory = new AddrInventoryDTO();
			order.getInstallAddress().setAddrInventory(addrInventory);			
		}
		
		order.getInstallAddress().setHasADSL8(srdDto.hasBandwidth("8")?"Y":"N");
		order.getInstallAddress().setHasADSL18(srdDto.hasBandwidth("18")?"Y":"N");

		String newTech=null;
		if(order!=null&&order.getInstallAddress()!=null&&order.getInstallAddress().getAddrInventory()!=null){
			 newTech = order.getInstallAddress().getAddrInventory().getTechnology();
		}
        String oldTech = order!=null && order.getServiceIms() != null?order.getServiceIms().getLineType():"";
		if(oldTech!=null&&newTech!=null&&order.isOrderTypeNowRet()&&oldTech.equals(newTech)){
			//do nth
		}else{
			order.getInstallAddress().setNtvCoverage(srdDto.getNtvCoverage());
		}
		if(order.getInstallAddress().getDistNo()!=null && order.getInstallAddress().getDistNo().equals("271"))
			order.getInstallAddress().getAddrInventory().setBuildingType("Lamma");
		else{ 
			String hosType = srdDto.getHousingTypeList().get(0).getHousingType();			
			if(hosType.contains("PT"))
				order.getInstallAddress().getAddrInventory().setBuildingType("PT");
			else if (hosType.contains("PH")||hosType.contains("HOS"))
				order.getInstallAddress().getAddrInventory().setBuildingType("PH");
			else 
				order.getInstallAddress().getAddrInventory().setBuildingType("Mass");
		}

		order.getInstallAddress().getAddrInventory().setAdsl8MInd(srdDto.hasBandwidth("8")?"Y":"N");
		order.getInstallAddress().getAddrInventory().setAdsl18MInd(srdDto.hasBandwidth("18")?"Y":"N"); 
		if(oldTech!=null&&newTech!=null&&order.isOrderTypeNowRet()&&oldTech.equals(newTech)){
			//do nth
		}else{
			order.getInstallAddress().getAddrInventory().setNtvCoverage(srdDto.getNtvCoverage());
		}
		
		String tech = order.getCPQTechnology();
		
		if ("NTV-A".equals(order.getOrderType()))
		{
			if(tech.contains("shortage")) {
				order.getInstallAddress().getAddrInventory().setResourceShortage("Y");
			} else {
				order.getInstallAddress().getAddrInventory().setResourceShortage("N");
			}
			tech = tech.replace("(shortage)", "");
			if (tech!=null && !"NONE".equals(tech) && !tech.equals(order.getInstallAddress().getAddrInventory().getTechnology())) {
				order.setAppointment(new AppointmentUI());
			}
			order.getInstallAddress().getAddrInventory().setTechnology(tech);
		}
		else
		{
			tech = order.getInstallAddress().getAddrInventory().getTechnology();
		}
		order.getInstallAddress().getAddrInventory().setN2BuildingInd(srdDto.getIs2NBuilding());
		if (!StringUtils.isEmpty(tech) && !"NONE".equals(tech) && !"ADSL".equals(tech)) {
			order.getInstallAddress().getAddrInventory().setH264Ind(srdDto.containBandwidth(Arrays.asList("3","6","8","18"))?"N":
				order.getInstallAddress().getAddrInventory().getH264Ind());
		}
		
		try {
			if (order.getInstallAddress().getHousingTypeList() != null && order.getInstallAddress().getHousingTypeList().size() > 0 
					&& StringUtils.isEmpty(order.getInstallAddress().getBlacklistInd())) {
				if ("PH".equals(order.getInstallAddress().getHousingTypeList().get(0).getHousingType())) {
					order.getInstallAddress().setBlacklistInd(imsAddressInfoDao.getBlacklistAddrForPH(
						order.getInstallAddress().getSerbdyno(), order.getInstallAddress().getFloorNo(), order.getInstallAddress().getUnitNo()));
				} else {
					order.getInstallAddress().setBlacklistInd(imsAddressInfoDao.getBlacklistAddr(
						order.getInstallAddress().getSerbdyno(), order.getInstallAddress().getFloorNo()));
				}
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return srdDto;
	}
	
	public Date getResourceShortageSRD(String sbno, String Technology, String isPccwLn){
		logger.debug("getResourceShortageSRD");
		Date srd = null;
		try{
			srd = imsAddressInfoDao.getResrcShortAppntDt(sbno, Technology, isPccwLn);
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return srd;
	}
	
	public AppointmentSubmitDTO reserveTimeSlot(InstallAddrUI instAddr, AppointmentTimeSlotDTO timeSlot, String supremeFsInd){
		logger.info("reserveTimeSlot["+timeSlot.getApptDate()+timeSlot.getTimeSlot()+"]");
		
		try{
			String ProdSubTypeCd;
			
			if(supremeFsInd!=null&&("A".equalsIgnoreCase(supremeFsInd)||"V".equalsIgnoreCase(supremeFsInd))){
				if(instAddr.getAddrInventory().getTechnology().equals("PON")){
					ProdSubTypeCd = "SUPI";
				}else{
					ProdSubTypeCd = "SUCI";
				}
			}else{
				if(instAddr.getAddrInventory().getTechnology().equals("PON")){
					ProdSubTypeCd = "FTHI";
				}else{
					ProdSubTypeCd = "PCDI";
				}
			}
			
			String ApptStartDate = timeSlot.getApptDate()+timeSlot.getTimeSlot().substring(0, 5).replace(":", "");
			String ApptEndDate = timeSlot.getApptDate()+timeSlot.getTimeSlot().substring(6, 11).replace(":", "");
			
			logger.info("reserve start date:"+ApptStartDate+" reserve end date:"+ApptEndDate);
						
			AppointmentSubmitDTO appointmentSubmit = appointmentDao.submitAppointment(
					ProdSubTypeCd, instAddr.getAreaCd(), instAddr.getDistNo(), ApptStartDate, ApptEndDate, timeSlot.getSlotType());
			
			return appointmentSubmit;
			
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}
	
	public void cancelReservedTimeSlot(String SerialNum){
		logger.debug("cancelReservedTimeSlot serial num:"+SerialNum);
		try{			
			appointmentDao.cancelPrebookSerial(SerialNum);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}
	
	public AppointmentTimeSlotDTO[] getTimeSlot(InstallAddrUI instAddr, Date apptDate, String supremeFsInd){
		return this.getTimeSlot(instAddr,apptDate,null,null,null,null,supremeFsInd);
	}

	public AppointmentTimeSlotDTO[] getTimeSlot(InstallAddrUI instAddr, Date apptDate, 
			String toProdSubTypeCd, String frProdSubTypeCd, String orderType, String changeAddr, String supremeFsInd){
		logger.debug("getTimeSlot");				
		
		try{
			AppointmentReserveDtlDTO reserveDtl = null;
			
			if(orderType==null && changeAddr==null){
				String ProdSubTypeCd;
				
				if(supremeFsInd!=null&&("A".equalsIgnoreCase(supremeFsInd)||"V".equalsIgnoreCase(supremeFsInd))){
					if(instAddr.getAddrInventory().getTechnology().equals("PON")){
						ProdSubTypeCd = "SUPI";
					}else{
						ProdSubTypeCd = "SUCI";
					}
				}else{
					if(instAddr.getAddrInventory().getTechnology().equals("PON")){
						ProdSubTypeCd = "FTHI";
					}else{
						ProdSubTypeCd = "PCDI";
					}
				}
				reserveDtl = appointmentDao.getReserveDtl(ProdSubTypeCd, instAddr.getAreaCd(), 
						instAddr.getDistNo(), Utility.date2String(apptDate, "yyyyMMdd"), instAddr.getSerbdyno());			
			}else{
				reserveDtl = appointmentDao.getReserveDtl(toProdSubTypeCd, frProdSubTypeCd, 
						instAddr.getAreaCd(), instAddr.getDistNo(), Utility.date2String(apptDate, "yyyyMMdd"), 
						instAddr.getSerbdyno(),	orderType, changeAddr);	
			}
			
			List<AppointmentTimeSlotDTO> outList = new ArrayList<AppointmentTimeSlotDTO>();

    		logger.debug(new Gson().toJson(reserveDtl.getTimeslots()));
			for(int i=0; i<reserveDtl.getTimeslots().size(); i++){
//				logger.debug(reserveDtl.getTimeslots().get(i).getTimeSlot()+" "+reserveDtl.getTimeslots().get(i).getResourceInMinute());
				reserveDtl.getTimeslots().get(i).setTimeSlotDisplay(reserveDtl.getTimeslots().get(i).getTimeSlot());
				if(instAddr.getAddrInventory().getTechnology().equals("PON") &&
						reserveDtl.getTimeslots().get(i).getTimeSlot().equals("18:00-22:00")){
					reserveDtl.getTimeslots().get(i).setTimeSlot("18:00-20:00");
				}
				if(Integer.parseInt(reserveDtl.getTimeslots().get(i).getResourceInMinute())<=0){
					reserveDtl.getTimeslots().get(i).setSlotType("NS");
				}
				outList.add(reserveDtl.getTimeslots().get(i));
			}
			
			logger.debug(reserveDtl.getErrorMsg());
			if(reserveDtl.getErrorMsg() != null && !("").equals(reserveDtl.getErrorMsg())){
				for(int i=0; i<outList.size(); i++){
					outList.get(i).setReturnValue(reserveDtl.getReturnValue());
					outList.get(i).setErrorCode(reserveDtl.getErrorCode());
					outList.get(i).setErrorMsg(reserveDtl.getErrorMsg());
				}
			}
			
			//T+1 filtering
			Calendar _curdate = new GregorianCalendar(
					Calendar.getInstance().get(Calendar.YEAR), 
					Calendar.getInstance().get(Calendar.MONTH),
					Calendar.getInstance().get(Calendar.DATE));			
			if((apptDate.getTime()-_curdate.getTimeInMillis())/(24*60*60*1000L) == 1 ){
				logger.debug("T+1 filter time slots < 16:00");
				for(int i=0; i<outList.size(); i++){
					//if(Integer.parseInt(timeslots.get(i).getTimeSlot().substring(0, 2))<16){
					if(Integer.parseInt(reserveDtl.getTimeslots().get(i).getTimeSlot().substring(0, 2))<16){
						outList.get(i).setSlotType("NA");
					}
				}
			}
			
			String[] appntTypeList = new String[instAddr.getHousingTypeList().size()];
			
			for(int i=0; i<appntTypeList.length; i++){
				appntTypeList[i]=instAddr.getAddrInventory().getTechnology()+"_"+
					instAddr.getHousingTypeList().get(i).getHousingType();
			}
			
			List<String> filterTimeSlotList = imsAddressInfoDao.getFilteredTimeSlotList(
					appntTypeList, 
					instAddr.getSerbdyno(), instAddr.getDistNo(), instAddr.getSectCd());						
			
			for(int i=0; i<filterTimeSlotList.size();i++){
				logger.debug("filter time slot:"+filterTimeSlotList.get(i));
				for(int j=0; j<outList.size(); j++){
					if(filterTimeSlotList.get(i).equals(outList.get(j).getTimeSlotDisplay())){
						outList.get(j).setSlotType("NA");
					}
				}
			}
			
			logger.info("reserveDtl.getRestrictedTimeslots():"+reserveDtl.getRestrictedTimeslots());
			if(reserveDtl.getRestrictedTimeslots() != null){
				String[] restrictedTimeslots = reserveDtl.getRestrictedTimeslots().split(";");
			
				for(int i=0; i<restrictedTimeslots.length; i++){
					logger.debug("restricted timeslots:"+restrictedTimeslots[i]);
					
					if(instAddr.getAddrInventory().getTechnology().equals("PON") &&
							restrictedTimeslots[i].equals("18:00-20:00")){
						restrictedTimeslots[i]="18:00-22:00";
					}
					
					for(int j=0; j<outList.size(); j++){
						if(restrictedTimeslots[i].equals(outList.get(j).getTimeSlotDisplay())){
							outList.get(j).setSlotType("NA");
						}
					}
				}
			}
			
    		logger.debug(new Gson().toJson(outList));
//			for(int i=0; i<outList.size(); i++){
//				logger.debug(outList.get(i).getTimeSlot()+
//						"["+outList.get(i).getTimeSlotDisplay()+"]"+
//						" "+outList.get(i).getSlotType());
//			}
			
			return outList.toArray(new AppointmentTimeSlotDTO[0]);
						
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}				
				
	}		

	public AppointmentPermitPropertyDtlDTO getPermitPropertyDtl(InstallAddrUI instAddr, Date apptDate, String supremeFsInd){
		logger.debug("getPermitPropertyDtl");				
		
		try{
			String ProdSubTypeCd;
			if(supremeFsInd!=null&&("A".equalsIgnoreCase(supremeFsInd)||"V".equalsIgnoreCase(supremeFsInd))){
				if(instAddr.getAddrInventory().getTechnology().equals("PON")){
					ProdSubTypeCd = "SUPI";
				}else{
					ProdSubTypeCd = "SUCI";
				}
			}else{
				if(instAddr.getAddrInventory().getTechnology().equals("PON")){
					ProdSubTypeCd = "FTHI";
				}else{
					ProdSubTypeCd = "PCDI";
				}
			}
			
			AppointmentPermitPropertyDtlDTO appointmentPermitPropertyDtl = new AppointmentPermitPropertyDtlDTO();
			appointmentPermitPropertyDtl = appointmentDao.getPermitPropertyDtl(instAddr.getSerbdyno(), ProdSubTypeCd, Utility.date2String(apptDate, "yyyyMMdd"));
			
			return appointmentPermitPropertyDtl;
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}
	
	public ImsAddressInfoDAO getImsAddressInfoDao() {
		return imsAddressInfoDao;
	}

	public void setImsAddressInfoDao(ImsAddressInfoDAO imsAddressInfoDao) {
		this.imsAddressInfoDao = imsAddressInfoDao;
	}

	public AppointmentDAO getAppointmentDao() {
		return appointmentDao;
	}

	public void setAppointmentDao(AppointmentDAO appointmentDao) {
		this.appointmentDao = appointmentDao;
	}

	//called by imsorderamendment
	public ImsServiceSrdDTO getServiceSrd(String isBlAddr,
			String isBlCust, String isPccwLn, Date appntDateTime,
			String sbno, String flat, String floor, String isCC, String isPT, String isDS, String i_has_bb_srv, String i_has_nowtv_srv, String mismatch, String fsPrepay, String fsInd, String i_ride_on_fsa,
			String orderType) {
		String sysId= "";
		if ( "Y".equals(isCC))
		{
			if ("Y".equals(isPT))
				sysId = "CC_PT";
			else
				sysId = "CC";
		} else if ( "Y".equals(isDS))
		{
			sysId = "DS";
		}
		else
		{
			sysId = "RETAIL";
		}
		if ("NTV-A".equals(orderType)) {
			sysId = sysId + "-TV";
		}		
		if ("NTVAO".equals(orderType)||"NTVUS".equals(orderType)||"NTVRE".equals(orderType)) {
			sysId = sysId + "_RET-TV";
		}	
		
		try {
			return imsAddressInfoDao.getServiceSrd(isBlAddr, isBlCust, isPccwLn, appntDateTime, sbno, flat, floor, sysId, i_has_bb_srv, i_has_nowtv_srv, mismatch, fsPrepay, fsInd,null,i_ride_on_fsa,null,null,null,null,null);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public AppointmentPermitPropertyDtlDTO getPermitPropertyDtl(String sb,
			String prodSubTypeCd, String applicationDate) {
		// TODO Auto-generated method stub
		try {
			return appointmentDao.getPermitPropertyDtl(sb, prodSubTypeCd, applicationDate);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	//////for ret / temp
	public AppointmentSubmitDTO reserveTimeSlot(OrderImsUI order, AppointmentTimeSlotDTO timeSlot){
		logger.info("reserveTimeSlot["+timeSlot.getApptDate()+timeSlot.getTimeSlot()+"]");
		
		try{
			String prodSubTypeCd;
			if("PON".equals(order.getInstallAddress().getAddrInventory().getTechnology())){
				prodSubTypeCd = ImsConstants.DWFM_PON_CHANGE;
			}else{
				prodSubTypeCd = ImsConstants.DWFM_NON_PON_CHANGE;
			}
			if(ImsConstants.Termination.equals(order.getImsOrderType())){
				if("PON".equals(order.getInstallAddress().getAddrInventory().getTechnology())){
					prodSubTypeCd = ImsConstants.DWFM_PON_DISCONNECT;
				}else{
					prodSubTypeCd = ImsConstants.DWFM_NON_PON_DISCONNECT;
				}
			}
			
			String ApptStartDate = timeSlot.getApptDate()+timeSlot.getTimeSlot().substring(0, 5).replace(":", "");
			String ApptEndDate = timeSlot.getApptDate()+timeSlot.getTimeSlot().substring(6, 11).replace(":", "");
			
			logger.info("reserve start date:"+ApptStartDate+" end date:"+ApptEndDate);
						
			AppointmentSubmitDTO appointmentSubmit = appointmentDao.submitAppointment(prodSubTypeCd, 
					order.getInstallAddress().getAreaCd(), order.getInstallAddress().getDistNo(), ApptStartDate, 
					ApptEndDate, timeSlot.getSlotType());
			
			return appointmentSubmit;
			
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}
	
	
	
	public ImsServiceSrdDTO getServiceSrdComparedWithBMO(String isBlAddr, String isBlCust, String isPccwLn, 
			Date appntDateTime, String sbno, String flat, String floor, String isCC, String isPT, OrderImsUI order, String newTech){
		
		logger.debug("getServiceSrd is called");
		ImsServiceSrdDTO addr = new ImsServiceSrdDTO();
		AppointmentPermitPropertyDtlDTO dwfm = new AppointmentPermitPropertyDtlDTO();
		
		String sysId= "";
		if ( "Y".equals(isCC))
		{
			if (ImsConstants.Termination.equals(order.getImsOrderType())){
				if ("Y".equals(isPT)){
					sysId = "CC_TERM_PT";
				}else{
					sysId = "CC_TERM";
				}
			}else if (ImsConstants.Retention.equals(order.getImsOrderType())){
				if ("Y".equals(isPT)){
					sysId = "CC_RET_PT";
				}else{
					sysId = "CC_RET";
				}
			}else if ("Y".equals(isPT)){
				sysId = "CC_PT";
			}else{
				sysId = "CC";
			}
		}
		else
		{
			sysId = "RETAIL";
		}
		
		try{			
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");  
	        String toProdSubTypeCd="";
	        String fromProdSubTypeCd="";
			String orderType="D"; 
			String changeAddr="N";
			String date="";
			String oldTech = order.getServiceIms().getLineType();
			String sbType="ACQ";
			if(ImsConstants.Termination.equals(order.getImsOrderType())){
				sbType="TERM";
			}else if(ImsConstants.Retention.equals(order.getImsOrderType())){
				sbType="RET";
			}
			if(ImsConstants.Termination.equals(order.getImsOrderType())){
				if("PON".equals(oldTech)){
					toProdSubTypeCd = ImsConstants.DWFM_PON_DISCONNECT;
				}else{
					toProdSubTypeCd = ImsConstants.DWFM_NON_PON_DISCONNECT;
				}
			}else{
				if("PON".equals(oldTech)){
					fromProdSubTypeCd = ImsConstants.DWFM_PON_CHANGE;
				}else{
					fromProdSubTypeCd = ImsConstants.DWFM_NON_PON_CHANGE;
				}
				if("PON".equals(newTech)){
					toProdSubTypeCd = ImsConstants.DWFM_PON_CHANGE;
				}else{
					toProdSubTypeCd = ImsConstants.DWFM_NON_PON_CHANGE;
				}
				orderType="C"; 
			}
			if(appntDateTime != null){
				date=Utility.date2String(appntDateTime, "yyyyMMdd");
			}else{
		        Date today = new Date();  
		        dateFormat.format(today);
				date=Utility.date2String(today, "yyyyMMdd");
			}
			logger.info("orderId:"+order.getOrderId()+" is calling getPermitPropertyDtl");
			dwfm = appointmentDao.getPermitPropertyDtl(sbno, toProdSubTypeCd, fromProdSubTypeCd, date, orderType, changeAddr);
			
			
			Calendar c = Calendar.getInstance();// today
			c.add(Calendar.DATE, 2);  // add 2 day
			String tomorrow = dateFormat.format(c.getTime());  
			Date t2 = new Date();
			try {
				t2 = dateFormat.parse(tomorrow);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Date fieldServiceDate = Utility.string2Date(
					dwfm.getEarliestApptDate().substring(0, 4), 
					dwfm.getEarliestApptDate().substring(4, 6), 
					dwfm.getEarliestApptDate().substring(6, 8));

			
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			logger.info("old tech:"+oldTech+ "  new tech:"+newTech+" retention/termination find max(T+2,DWFM,Addr) T+2:"+t2+"  DWFM fieldService:"+fieldServiceDate);

			if(ImsConstants.Termination.equals(order.getImsOrderType())|| oldTech.equals(newTech) ){ //termination|| new==old, only compare field Service and t+2
				logger.debug("find max(T+2,DWFM)");
				//	addr.getServiceDetailList().get(i).getTechnology().equalsIgnoreCase(oldTech)){
				ServiceDetailDTO dto = new ServiceDetailDTO();
				dto.setTechnologySupported("Y");
				dto.setIsDeadCase("N");
				dto.setTechnology(oldTech);
				dto.setEstEarliestSrd(fieldServiceDate);
				dto.setLeadTime(Integer.parseInt(dwfm.getPermitLeadDays()));
				//dto.setRtnDesc("PON BMO, T+"+addr.getServiceDetailList().get(i).getLeadTime());
				dto.setBmoAlert(dwfm.getAlertMsg());
				dto.setBmoRmk(dwfm.getBmoRemark());
				if(dto.getEstEarliestSrd().before(t2)){
						dto.setEstEarliestSrd(t2);// at least t+2
				}
				addr.getServiceDetailList().add(dto);
			}else{
				addr = imsAddressInfoDao.getRetentionServiceSrd(isBlAddr, isBlCust, isPccwLn, appntDateTime, sbno, flat, floor, sysId);
				logger.debug("find max(T+2,DWFM,Addr)");
				for(int i=0; i<addr.getServiceDetailList().size(); i++){
					if(addr.getServiceDetailList().get(i).getEstEarliestSrd()!=null){
						logger.debug("addr Technology:"+addr.getServiceDetailList().get(i).getTechnology()+ 
								" Earliest SRD:"+addr.getServiceDetailList().get(i).getEstEarliestSrd());
						addr.getServiceDetailList().get(i).setBmoLeadDay(dwfm.getPermitLeadDays());
						if(!ImsConstants.Termination.equals(order.getImsOrderType()) &&//retention
								addr.getServiceDetailList().get(i).getTechnology().equalsIgnoreCase(newTech)){
									if(addr.getServiceDetailList().get(i).getEstEarliestSrd().before(fieldServiceDate)){//compare field service and addr
										addr.getServiceDetailList().get(i).setEstEarliestSrd(fieldServiceDate);
										
										addr.getServiceDetailList().get(i).setRtnDesc("PON BMO, T+"+addr.getServiceDetailList().get(i).getLeadTime());
										
									}
									addr.getServiceDetailList().get(i).setLeadTime(Integer.parseInt(dwfm.getPermitLeadDays()));
									addr.getServiceDetailList().get(i).setBmoAlert(dwfm.getAlertMsg());
									addr.getServiceDetailList().get(i).setBmoRmk(dwfm.getBmoRemark());
									if(addr.getServiceDetailList().get(i).getEstEarliestSrd().before(t2)){//t+2 and addr
										addr.getServiceDetailList().get(i).setEstEarliestSrd(t2);// at least t+2
									}
						}else{
							//remove useless tech
							addr.getServiceDetailList().remove(i);
							i--;
						}
					}				
				}
			}
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return addr;
	}
	
	public List<String> getSbPopUp(String sbNum) {
		logger.debug("getSbPopUp");
		List<String> sbPopUp = null;
		try{
			sbPopUp = imsAddressInfoDao.getSbPopUp(sbNum);
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return sbPopUp;
	}

	public List<String[]> getPreInstallParameter() {
		logger.info("getPreInstallParameter");
		List<String[]> result = null;
		try{
			result = constantLkupDao.getPreInstallParameter();
		}catch(DAOException de){
			throw new AppRuntimeException(de);
		}
				
		return result;
	}
}


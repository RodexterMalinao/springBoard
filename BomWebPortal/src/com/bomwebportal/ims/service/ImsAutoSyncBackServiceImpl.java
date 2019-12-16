package com.bomwebportal.ims.service;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ImsBomOrderDAO;
import com.bomwebportal.ims.dao.ImsOrderDAO;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.dto.VimBundleProfileDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.util.Utility;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueDocumentDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;
import com.pccw.wq.service.WorkQueueService;

@Transactional(readOnly = true)
public class ImsAutoSyncBackServiceImpl implements ImsAutoSyncBackService{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsOrderDAO orderDao;
	private ImsBomOrderDAO bomDao;
	private ImsOrderService orderservice;
	private WorkQueueService workQueueService;
	private String tmpFilePath;
	
	public List<OrderImsDTO> getOcPendingOrder(){
//		logger.info("getOcPendingOrder");		
		try{
			return orderDao.getSyncBackPendingOrder();			
		}catch (DAOException de) {
			logger.error("Exception caught in getOcPendingOrder()", de);
			throw new AppRuntimeException(de);
		}catch (Exception e){
			logger.error("Exception caught in getOcPendingOrder()", e);
			throw new AppRuntimeException(e);
		}
		
	}
	
	public List<OrderImsDTO> getBomOcDetail(String orderId){
//		logger.info("getBomOcDetail");
		try{
			return bomDao.getBomOcDetail(orderId);
		}catch (DAOException de) {
			logger.error("Exception caught in getBomOcDetail()", de);
			throw new AppRuntimeException(de);
		}catch (Exception e){
			logger.error("Exception caught in getBomOcDetail()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateOrderOcDetail(OrderImsDTO order){
//		logger.info("updateOrderOcDetail");
		try{
			orderDao.updateBomOcDetail(order);
		}catch (DAOException de) {
			logger.error("Exception caught in updateOrderOcDetail()", de);
			throw new AppRuntimeException(de);
		}catch (Exception e){
			logger.error("Exception caught in updateOrderOcDetail()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void createVimBundleChannelRequest(String orderId){
//		logger.info("createVimBundleChannelRequest");
		logger.info("order id:"+orderId);				
		
		try{
			
			OrderImsUI order = orderservice.getBomWebImsOrder(orderId);			
			generateVimBundleSubscriptionFile(order);
			submitVimChannelWQ(order);
			orderDao.updateVimBundleChannelStatus(orderId);
			
		}catch (DAOException de) {
			logger.error("Exception caught in createVimBundleChannelRequest()", de);
			throw new AppRuntimeException(de);
		}catch (Exception e){
			logger.error("Exception caught in createVimBundleChannelRequest()", e);
			throw new AppRuntimeException(e);
		}
		
	}
	
	//temp use
	/*
	public void test() throws Exception{
		OrderImsUI order = orderservice.getBomWebImsOrder("RSSHP000685");
		generateVimBundleSubscriptionFile(order);
	}*/
	
	private WorkQueueDTO submitVimChannelWQ(OrderImsUI order) throws Exception{
//		logger.info("submitVimChannelWQ");
		
		WorkQueueDTO wqDTO = new WorkQueueDTO();
		wqDTO.setSbId(order.getOrderId());
		wqDTO.setSbDtlId("1"); //IMS default "1"
		wqDTO.setSbShopCode(order.getShopCd());
		wqDTO.setTypeOfService("FSA");
		wqDTO.setServiceId(order.getCustomer().getServiceNum());
		if(order.getAppointment().getAppntStartDate()!=null){
			wqDTO.setSrd(Utility.date2String(order.getAppointment().getAppntStartDate(), "yyyyMMdd"));
		}else{
			wqDTO.setSrd(Utility.date2String(order.getSrvReqDate(), "yyyyMMdd"));
		}		
		wqDTO.setBomOcId(order.getOcId());
		wqDTO.setBomDtlId("1"); //IMS default "1"
		wqDTO.setBomDtlSeq("1"); //IMS default "1"
		
		WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
		wqNatureDTO.setWorkQueueType("FS-P"); //F&S - PCD
		wqNatureDTO.setWorkQueueSubType("PARTIAL_WQ"); //Partial
		wqNatureDTO.setWorkQueueNatureId("2"); //create VI channel bundle
		
		wqDTO.setWorkQueueNatures(new WorkQueueNatureDTO[] {wqNatureDTO});
				
		WorkQueueDTO retDto = workQueueService.createWorkQueue(
				wqDTO, new WorkQueueNatureDTO[]{wqNatureDTO}, order.getSalesCd());
		WorkQueueDocumentDTO[] requiredDocuments = retDto.getDocuments();			
		String signoffFormFilePath = tmpFilePath+order.getOrderId()+"/VimChannelBundle.csv";
		requiredDocuments[0].setAttachmentFullPath(signoffFormFilePath);
		workQueueService.attachWorkQueueDocument(retDto, requiredDocuments, order.getSalesCd());
		
		return retDto;
		
	}
	
	private void generateVimBundleSubscriptionFile(OrderImsUI order) throws Exception{
//		logger.info("generateVimBundleSubscriptionFile");
		logger.info("order id:"+order.getOrderId());
		
		VimBundleProfileDTO profile = orderDao.getVimBundleProfile(order.getOrderId()).get(0);
		profile.setChannels(orderDao.getVIMBundleChannel(order.getOrderId()));
		profile.setFreeHDChannels(orderDao.getVIMBundleFreeHDChannel(order.getOrderId()));
		Map<String, String> _map = bomDao.getVimServiceType(order.getOrderId()).get(0);
		//profile.setServiceType("Line Rate:"+_map.get("vi_bit_rate")+" TV Type:"+_map.get("tv_type"));		
		profile.setServiceType(_map.get("tv_type"));
		//profile.setWorkQueueSeq(WQSeq);
		//profile.setReceivedDate(new Date());
		
		/*
		if("Y".equals(order.getInstallAddress().getAddrInventory().getResourceShortage())){
			profile.setSRD(order.getAppointment().getAppntStartDate());
		}*/
		if(order.getSrvReqDate()==null){
			profile.setSRD(order.getAppointment().getAppntStartDate());
		}
		
		String timeslot = "";
		if(order.getAppointment().getAppntStartDate()!=null){
			timeslot = Utility.date2String(order.getAppointment().getAppntStartDate(), "HH:mm")+"-"+
				Utility.date2String(order.getAppointment().getAppntEndDate(), "HH:mm");
		}
		profile.setTimeSlot(timeslot);
		
		//temp use
		//tmpFilePath = "C:/temp/";
		
		File directory = new File(tmpFilePath + order.getOrderId());
		if (directory.exists() == false) {
			directory.mkdir();
			logger.debug("Directory " + directory.toString() + " created.");
		} else {
			logger.debug("Directory " + directory.toString() + " is existed.");
		}
						
		String filepath = directory + "/VimChannelBundle.csv";
		FileWriter writer = new FileWriter(filepath);						
		
		writer.write("TV Channel Subscription"+"\n");
		writer.write("\n");		
		writer.write("Basic Customer Info"+"\n");
		writer.write("SBID:,"+profile.getOrderId()+"\n");
		writer.write("SB Application Date:,"+Utility.date2String(profile.getAppDate(), "d MMM yyyy")+"\n");
		//writer.write("WQ sequence:"+profile.getWorkQueueSeq()+" Received Date:"+Utility.date2String(profile.getReceivedDate(), "d MMM yyyy")+"\n");
		writer.write("Sales Information:,"+profile.getSalesChannel()+" / "+profile.getShopCd()+" / "+profile.getSalesCd()+"\n");
		writer.write("\n");		
		writer.write("FSA:,"+profile.getFSA()+"\n");
		writer.write("Login ID:,"+profile.getLoginID()+"\n");
		//writer.write("Customer Name:,\""+profile.getTitle()+" "+profile.getFirstName()+" "+profile.getLastName()+"\"\n");
		writer.write("Customer Name:,\""+profile.getTitle()+" "+profile.getLastName()+" "+profile.getFirstName()+"\"\n");
		writer.write("Document:,"+profile.getIDDocType()+" / "+profile.getIDDocNo()+"\n");
		writer.write("SRD:,"+Utility.date2String(profile.getSRD(), "d MMM yyyy")+" "+profile.getTimeSlot()+"\n");
		writer.write("\n");
		writer.write("WQ Details:\n");
		writer.write("Bundle TV Channels Subscription"+"\n");
		writer.write("Campaign Code:,"+profile.getCampaignCd()+"\n");
		writer.write("Selected Channels:,");
		if(profile.getChannels()!=null && profile.getChannels().size()>0){
			for(int i=0; i<profile.getChannels().size(); i++){
				if(i>0){
					writer.write(",");
				}
				writer.write("\""+
						((profile.getChannels().get(i).get("channel_id")!=null)?profile.getChannels().get(i).get("channel_id")+" ":"")+
						profile.getChannels().get(i).get("channel_desc")+"\"\n");
			}
		}else{
			writer.write("NA\n");
		}
		writer.write("Commitment Period:,"+profile.getCommitPeriod()+"\n");
		writer.write("Free HD Channel:,");
		if(profile.getFreeHDChannels()!=null && profile.getFreeHDChannels().size()>0){
			for(int i=0; i<profile.getFreeHDChannels().size(); i++){
				if(i>0){
					writer.write(",");
				}
				writer.write("\""+
						((profile.getFreeHDChannels().get(i).get("channel_id")!=null)?profile.getFreeHDChannels().get(i).get("channel_id")+" ":"")+
						profile.getFreeHDChannels().get(i).get("channel_desc")+"\"\n");
			}
		}else{
			writer.write("NA\n");
		}
		writer.write("TV Type:,"+profile.getServiceType()+"\n");		
		writer.write("\n");			
		writer.close();					
				
	}

	public ImsOrderDAO getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(ImsOrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	public ImsBomOrderDAO getBomDao() {
		return bomDao;
	}

	public void setBomDao(ImsBomOrderDAO bomDao) {
		this.bomDao = bomDao;
	}

	public String getTmpFilePath() {
		return tmpFilePath;
	}

	public void setTmpFilePath(String tmpFilePath) {
		this.tmpFilePath = tmpFilePath;
	}

	public ImsOrderService getOrderservice() {
		return orderservice;
	}

	public void setOrderservice(ImsOrderService orderservice) {
		this.orderservice = orderservice;
	}

	public WorkQueueService getWorkQueueService() {
		return workQueueService;
	}

	public void setWorkQueueService(WorkQueueService workQueueService) {
		this.workQueueService = workQueueService;
	}

}

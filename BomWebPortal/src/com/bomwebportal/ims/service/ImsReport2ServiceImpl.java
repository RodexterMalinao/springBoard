package com.bomwebportal.ims.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.dto.VasParmDTO;
import com.bomwebportal.dto.report.ReportDTO;
import com.bomwebportal.dto.report.RptHSTradeDescDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.ImsReport1DAO;
import com.bomwebportal.ims.dao.ImsReport2DAO;
import com.bomwebportal.ims.dao.VASDetailDAO;
import com.bomwebportal.ims.dto.ImsRptBasketDtlDTO;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;
import com.bomwebportal.ims.dto.ImsRptChannelDTO;
import com.bomwebportal.ims.dto.ImsRptCoreServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptGiftDTO;
import com.bomwebportal.ims.dto.ImsRptNtvServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptOptServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptServicePlanDetailDTO;
import com.bomwebportal.ims.dto.ImsSignoffDTO;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.ims.dto.report.RptServiceInfoDTO;
import com.bomwebportal.ims.dto.ui.NowTVAddUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedChannelUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.google.gson.Gson;
import com.pccw.rpt.dao.WorkQueueImsDataDAO;
import com.pccw.rpt.schema.dto.wq.ImsWorkQueueRptDTO;
import com.bomwebportal.ims.dao.NowTVDAO;
import com.bomwebportal.service.ImsBasketCommonService;


@Transactional(readOnly=true)
public class ImsReport2ServiceImpl implements ImsReport2Service{
	
	
	private ImsBasketCommonService imsBasketCommonService;

	public ImsBasketCommonService getImsBasketCommonService() {
		return imsBasketCommonService;
	}

	public void setImsBasketCommonService(
			ImsBasketCommonService imsBasketCommonService) {
		this.imsBasketCommonService = imsBasketCommonService;
	}
	
	
	private WorkQueueImsDataDAO imsWQReportDao;	
	
	private NowTVDAO nowTVDAO;

	private VASDetailDAO VASDetailDao;
	
	public VASDetailDAO getVASDetailDao() {
		return VASDetailDao;
	}

	public void setVASDetailDao(VASDetailDAO VASDetailDao) {
		this.VASDetailDao = VASDetailDao;
	}	
	
	public NowTVDAO getNowTVDAO() {
		return nowTVDAO;
	}
	
	public void setNowTVDAO(NowTVDAO nowTVDAO) {
		this.nowTVDAO = nowTVDAO;
	}
	
	private Gson gson = new Gson();

	public void setImsWQReportDao(WorkQueueImsDataDAO imsWQReportDao) {
		this.imsWQReportDao = imsWQReportDao;
	}
	public WorkQueueImsDataDAO getImsWQReportDao() {
		return imsWQReportDao;
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsReport1DAO imsReport1Dao;	

	public ImsReport1DAO getImsReport1Dao() {
		return imsReport1Dao;
	}
	public void setImsReport1Dao(ImsReport1DAO imsReport1Dao) {
		this.imsReport1Dao = imsReport1Dao;
	}

	private ImsReport2DAO imsReport2Dao;	

	public ImsReport2DAO getImsReport2Dao() {
		return imsReport2Dao;
	}
	public void setImsReport2Dao(ImsReport2DAO imsReport2Dao) {
		this.imsReport2Dao = imsReport2Dao;
	}
	private ImsNowTVService imsNowTVService;
	public ImsNowTVService getImsNowTVService() {
		return imsNowTVService;
	}

	public void setImsNowTVService(ImsNowTVService imsNowTVService) {
		this.imsNowTVService = imsNowTVService;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public ImsRptBasketDtlDTO getBasketDetail(String basketId, String locale){
		
		logger.debug("getBasketDetail is called");
		ImsRptBasketDtlDTO basketDtl = new ImsRptBasketDtlDTO();
		
		try{			
			basketDtl = imsReport2Dao.getBasketDetail(basketId, locale);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return basketDtl;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsRptBasketItemDTO> getBasketItem(String basketId, String itemIdStr, String locale){
		
		logger.debug("getBasketItem is called");
		List<ImsRptBasketItemDTO> basketItemList = new ArrayList<ImsRptBasketItemDTO>();
		
		try{			
			basketItemList = imsReport2Dao.getBasketItem(basketId, itemIdStr, locale);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return basketItemList;
	}
	
	//Gary add for new pricing
	//***
	public  NowTVAddUI getNewPricingItem(OrderImsUI order, String locale){
	
		List<SubscribedItemUI> AddUIsubItems = new ArrayList<SubscribedItemUI>();
		
		for (SubscribedItemUI i : order.getSubscribedItems()) {
			if (i.getType().contains("NTV_")) {
				SubscribedItemUI AddUIitem = new SubscribedItemUI();
				AddUIitem.setId(i.getId());
				AddUIitem.setBasketId(i.getBasketId());
				AddUIsubItems.add(AddUIitem);
			}
		}
		try {
			
			NowTVAddUI addui = this.getNewTVPricingDtl(order.getInstallAddress().getHousingTypeList()!=null&&order.getInstallAddress().getHousingTypeList().size()>0?
					order.getInstallAddress().getHousingTypeList().get(0).getHousingType():"", 
					"271".equals(order.getInstallAddress().getDistNo())?"LAMMA":"", 
					"", 
					order.getWarrPeriod(), 
					locale);
			addui.loadNowTVAddUISettings(AddUIsubItems);
			return addui;
			
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	//***
	

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsRptBasketItemDTO> getOptServiceY(String basketId, String itemIdStr, String locale, String orderId, boolean isAF){
		
		logger.debug("getOptServiceY is called");
		List<ImsRptBasketItemDTO> basketItemList = new ArrayList<ImsRptBasketItemDTO>();
		
		try{			
			basketItemList = imsReport2Dao.getOptServiceY(basketId, itemIdStr, locale, orderId, isAF);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return basketItemList;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsRptBasketItemDTO> getOptServiceN(String basketId, String itemIdStr, String locale, String orderId, boolean isAF){
		
		logger.debug("getOptServiceN is called");
		List<ImsRptBasketItemDTO> basketItemList = new ArrayList<ImsRptBasketItemDTO>();
		
		try{			
			basketItemList = imsReport2Dao.getOptServiceN(basketId, itemIdStr, locale, orderId, isAF);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return basketItemList;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsRptBasketItemDTO> getBackendVas(String orderId){
		
		logger.debug("getBackendVas is called");
		List<ImsRptBasketItemDTO> backendVasList = new ArrayList<ImsRptBasketItemDTO>();
		
		try{			
			backendVasList = imsReport2Dao.getBackendVas(orderId);
		}catch (DAOException de) {			
			backendVasList = null;
		}

		return backendVasList;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsRptChannelDTO> getBackendChannel(String orderId){
		
		logger.debug("getBackendChannel is called");
		List<ImsRptChannelDTO> backendChannelList = new ArrayList<ImsRptChannelDTO>();
		
		try{			
			backendChannelList = imsReport2Dao.getBackendChannel(orderId);
		}catch (DAOException de) {			
			backendChannelList = null;
		}

		return backendChannelList;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsRptGiftDTO> getGiftList(String itemIdStr, String locale, String channel){
		logger.debug("getGiftList is called");
		List<ImsRptGiftDTO> giftList = new ArrayList<ImsRptGiftDTO>();
		try{			
			
			giftList = imsReport2Dao.getGift(itemIdStr,locale,channel);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
		
		if(giftList.size()>0){
			for(ImsRptGiftDTO gift:giftList){
				gift.setGiftTitle(this.splitString(gift.getItemDetails())[0]);
				gift.setGiftDetail(this.splitString(gift.getItemDetails())[1].replaceAll(((char)10)+"", "<br>"));
			}
			//imsGiftService.getGiftsAddDetailToImsRptGiftDTO(giftList, locale);
		}
		
		return giftList;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getPreInstRouterPurchased(String itemIdStr){
		logger.debug("getPreInstRouterPurchased is called");
		String ind = "N";
		try{			
			
			ind = imsReport2Dao.getPreInstRouterPurchased(itemIdStr);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
		
		return ind;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsRptBasketItemDTO> getNtvItem(String itemIdStr, String locale, boolean isAF){
		
		logger.debug("getNtvItem is called");
		List<ImsRptBasketItemDTO> ntvItemList = new ArrayList<ImsRptBasketItemDTO>();
		
		try{			
			ntvItemList = imsReport2Dao.getNtvItem(itemIdStr, locale, isAF);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return ntvItemList;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsRptChannelDTO> getNtvChannelFree(String itemIdStr, String locale, String warrPeriod, String tvCredit){
		
		logger.debug("getNtvChannelFree is called");
		List<ImsRptChannelDTO> ntvChannelList = new ArrayList<ImsRptChannelDTO>();
		
		try{			
			ntvChannelList = imsReport2Dao.getNtvChannelFree(itemIdStr, locale, warrPeriod, tvCredit);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return ntvChannelList;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsRptChannelDTO> getNtvChannelPay(String itemIdStr, String locale, String warrPeriod, String tvCredit){
		
		logger.debug("getNtvChannelPay is called");
		List<ImsRptChannelDTO> ntvChannelList = new ArrayList<ImsRptChannelDTO>();
		
		try{			
			ntvChannelList = imsReport2Dao.getNtvChannelPay(itemIdStr, locale,  warrPeriod, tvCredit);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return ntvChannelList;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getQosMeasureInd(String itemIdStr){
		
		logger.debug("getQosMeasureInd is called");
		String prodIdStr = "";
		String qosMeasureInd = "N";
		
		try{			
			prodIdStr = imsReport2Dao.getProdIdForQosMeasure(itemIdStr);
			if (prodIdStr=="" || prodIdStr.length()==0) {
				qosMeasureInd = "N";
			} else {
				qosMeasureInd = imsReport1Dao.getQosMeasureInd(prodIdStr);
			}
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return qosMeasureInd;
	}

	public List<String> getPISPdf(String itemIdStr, String locale) {
		try {
			return  this.imsReport2Dao.getPISPdf(itemIdStr, locale);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//Gary added for top up offer
	public List<String> getTopUpOffer(String orderId, String locale) {
		try {
			return  this.imsReport2Dao.getTopUpOffer(orderId, locale);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ImsRptServicePlanDetailDTO getServPlanDesc(OrderImsUI order, String locale, Boolean isPT,Boolean isDs) {
		ImsRptServicePlanDetailDTO servPlanDto = new ImsRptServicePlanDetailDTO();
		if(order.getAppMethod()!=null && !order.getAppMethod().equals("")){
			try {
				servPlanDto.setAppMethod(imsReport1Dao.getAppMethod(order.getAppMethod()));
			} catch (DAOException e1) {
				e1.printStackTrace();
			}
		}
		String useDBRptStaticWords = "";
		useDBRptStaticWords=this.isUseDBRptStaticWords();
		HashMap<String, HashMap<String, RptServiceInfoDTO>> dBRptStaticWords=  new HashMap<String, HashMap<String, RptServiceInfoDTO>>();
		if("Y".equalsIgnoreCase(useDBRptStaticWords)){
			logger.debug("Y.equalsIgnoreCase(useDBStaticWords");
			dBRptStaticWords=this.getStaticReportWords(isPT,isDs);
		}else{
			logger.debug("!Y.equalsIgnoreCase(useDBStaticWords), service2.getUseDBStaticWord():"+useDBRptStaticWords);
			dBRptStaticWords=null;
		}
		servPlanDto.setdBRptStaticWords(dBRptStaticWords);
		
		servPlanDto.setIsAFShowALL(this.isAFShowALL());
		
		ImsRptCoreServiceDetailDTO coreServiceDtl = new ImsRptCoreServiceDetailDTO();
		ImsRptBasketItemDTO progItem = new ImsRptBasketItemDTO();
		List<ImsRptBasketItemDTO> bvasMandItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> bvasNonMItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> preInstItemList = new ArrayList<ImsRptBasketItemDTO>();
		
		
		ImsRptOptServiceDetailDTO optServiceDtl = new ImsRptOptServiceDetailDTO();
		List<ImsRptBasketItemDTO> optPremItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> optServItemList = new ArrayList<ImsRptBasketItemDTO>();
		//List<ImsRptBasketItemDTO> wlBbItemList = new ArrayList<ImsRptBasketItemDTO>();
		//List<ImsRptBasketItemDTO> antiVirItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> mediaItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> backendVasItemList = new ArrayList<ImsRptBasketItemDTO>();//Gary
		List<ImsRptGiftDTO> giftList = new ArrayList<ImsRptGiftDTO>();
		ImsRptNtvServiceDetailDTO ntvServiceDtl = new ImsRptNtvServiceDetailDTO();
		List<ImsRptBasketItemDTO> ntvFreeItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> ntvPayItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> ntvOtherItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptChannelDTO> ntvFreeSPChannelList = new ArrayList<ImsRptChannelDTO>();
		List<ImsRptChannelDTO> ntvFreeEPChannelList = new ArrayList<ImsRptChannelDTO>();
		List<ImsRptChannelDTO> ntvFreeHDChannelList = new ArrayList<ImsRptChannelDTO>();
		List<ImsRptChannelDTO> ntvPayChannelList = new ArrayList<ImsRptChannelDTO>();
		List<ImsRptChannelDTO> ntvBackendChannelList = new ArrayList<ImsRptChannelDTO>();
		NowTVAddUI newPricingItemList = new NowTVAddUI();
		String qosMeasureInd = "N";
		String preInstRouterPurchasedInd = "N";
		
		String basketId="";
		String itemIdStr = "";
		SubscribedItemUI[] basket = order.getSubscribedItems();
		

		for (int i=0; i < basket.length; i++) {
			logger.debug(">>>basket " + 0 + " ID: " + basket[0].getBasketId());
			if (basket[0].getBasketId() != null && basket[0].getBasketId() != ""&& basket[i].getType() != null &&
					!basket[i].getType().contains("NTV")) {
				basketId = basket[0].getBasketId();
			}
			if (i==0) {
				itemIdStr = basket[i].getId();
			} else {
				itemIdStr = itemIdStr + "," + basket[i].getId();
			}
		}
		
		servPlanDto.setPisPdfs(this.getPISPdf(itemIdStr, locale));

		SubscribedChannelUI[] channel = order.getSubscribedChannels();
		String channelIdStr = "";
		if (channel != null) {
			for (int i=0; i < channel.length; i++) {
				if (i==0) {
					channelIdStr = channel[i].getChannelId();
				} else {
					channelIdStr = channelIdStr + "," + channel[i].getChannelId();
				}
			}
		}
		
		ImsRptBasketDtlDTO basketDtl = new ImsRptBasketDtlDTO();
		List<ImsRptBasketItemDTO> basketItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> optServiceList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> ntvPgmItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptChannelDTO> channelFreeList = new ArrayList<ImsRptChannelDTO>();
		List<ImsRptChannelDTO> channelPayList = new ArrayList<ImsRptChannelDTO>();
		List<ImsRptBasketItemDTO> backendVasList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptChannelDTO> channelBackendList = new ArrayList<ImsRptChannelDTO>();
		
		
		basketDtl = this.getBasketDetail(basketId, locale);
		logger.debug("\nBasket detail=" + basketDtl);
		
		if (itemIdStr != "") {
			basketItemList = this.getBasketItem(basketId, itemIdStr, locale);
			logger.debug("\nItem list =" + gson.toJson(basketItemList));
			
			if (basketDtl != null && basketDtl.getCanSubcOptSrv().equalsIgnoreCase("Y")) {
				optServiceList = this.getOptServiceY(basketId, itemIdStr, locale, order.getOrderId(), true);
			} else {
				optServiceList = this.getOptServiceN(basketId, itemIdStr, locale, order.getOrderId(), true);
			}
			logger.debug("\nOptional Service list =" + optServiceList);

			ntvPgmItemList = this.getNtvItem(itemIdStr, locale, true);
			logger.debug("\nItem list =" + ntvPgmItemList);

			qosMeasureInd = this.getQosMeasureInd(itemIdStr);
			logger.debug("\nQoS Measure Ind =" + qosMeasureInd);
												
			backendVasList = this.getBackendVas(order.getOrderId());//Gary
			logger.debug("\backendVas item list =" + backendVasList);

			String salesChannel = "";
			
			if (order.getImsOrderType()!=null){
			
				if("DS".equals(order.getImsOrderType())){
					salesChannel = "DS";
				}else if("Y".equals(order.getIsPT())){
					salesChannel = "PT";
				}else if("Y".equals(order.getIsCC())){
					salesChannel = "CC";
				}else{
					salesChannel = "RS";
				}
			}else if("Y".equals(order.getIsPT())){
				salesChannel = "PT";
			}else if("Y".equals(order.getIsCC())){
				salesChannel = "CC";
			}else{
				salesChannel = "RS";
			}
			
			if("Y".equalsIgnoreCase(order.getMobileOfferInd())){
				salesChannel = salesChannel + "_M";
			}
			
			giftList = this.getGiftList(itemIdStr, locale, salesChannel);
			
			preInstRouterPurchasedInd = this.getPreInstRouterPurchased(itemIdStr);
			servPlanDto.setPreInstRouterPurchasedInd(preInstRouterPurchasedInd);
			
		}

		if (channelIdStr != "") {
			channelFreeList = this.getNtvChannelFree(channelIdStr, locale, order.getWarrPeriod(), order.getTvCredit());
			//logger.debug("\nItem list =" + channelFreeList);

			channelPayList = this.getNtvChannelPay(channelIdStr, locale, order.getWarrPeriod(), order.getTvCredit());
			//logger.debug("\nItem list =" + channelPayList);
			
			channelBackendList= this.getBackendChannel(order.getOrderId());//Gary
			//logger.debug("\nItem list =" + backendChannelList);
		}
		//gary newtvpricing
		if("Y".equalsIgnoreCase(order.getTvPriceInd())){
			newPricingItemList=imsNowTVService.getNewTVPricingDtl(order,locale,true,null);
		}
		
		// Core Service
		if (basketDtl != null) {
			coreServiceDtl.setBandwidth(basketDtl.getBandwidth());
			coreServiceDtl.setBandwidth_desc(basketDtl.getBandwidth_desc());
			coreServiceDtl.setCanSubcOptSrv(basketDtl.getCanSubcOptSrv());
			coreServiceDtl.setOfferCd(basketDtl.getOfferCd());
			coreServiceDtl.setIncentiveCd(basketDtl.getIncentiveCd());
		}
		
		ImsRptBasketItemDTO item; 
		String tempOtherOfferCd ="";
		for (int i=0; i < basketItemList.size(); i++){
			if (basketItemList.get(i).getType().equalsIgnoreCase("PROG")) {
				progItem.setType(basketItemList.get(i).getType());
				progItem.setMdoInd(null);
				progItem.setItemTitle(basketDtl.getBasketTitle());
				progItem.setItemMthFix(basketItemList.get(i).getItemMthFix());
				progItem.setItemMth2Mth(basketItemList.get(i).getItemMth2Mth());
				progItem.setItemDetails(basketItemList.get(i).getItemDetails());
				progItem.setContractPeriod(basketItemList.get(i).getContractPeriod());//Gary
				progItem.setOfferCode(basketItemList.get(i).getOfferCode());
				progItem.setPreInstOfferCd(basketItemList.get(i).getPreInstOfferCd());
				progItem.setPreUseOfferCd(basketItemList.get(i).getPreUseOfferCd());
				progItem.setItemID(basketItemList.get(i).getItemID());
			}
			if (!basketItemList.get(i).getType().equalsIgnoreCase("PROG")&&!basketItemList.get(i).getType().equalsIgnoreCase("PRE_INST")
					&&!basketItemList.get(i).getType().equalsIgnoreCase("OTHER")&&basketDtl.getCanSubcOptSrv().equalsIgnoreCase("Y")) {
				if (basketItemList.get(i).getMdoInd().equalsIgnoreCase("M")) {
					item = new ImsRptBasketItemDTO(); 
					item.setType(basketItemList.get(i).getType());
					item.setMdoInd(basketItemList.get(i).getMdoInd());
					item.setItemTitle(basketItemList.get(i).getItemDetailName());
					item.setOfferCode(basketItemList.get(i).getOfferCode());
					item.setIncentiveCd(basketItemList.get(i).getIncentiveCd());
					item.setPreInstOfferCd(basketItemList.get(i).getPreInstOfferCd());
					item.setItemID(basketItemList.get(i).getItemID());
					item.setItemMthFix(null);
					item.setItemMth2Mth(null);
//					logger.debug("get detail info:input==" + basketItemList.get(i).getItemDetails());
					item.setItemDetails(basketItemList.get(i).getItemDetails());
//					logger.debug("get detail info:output==" + basketItemList.get(i).getItemDetailInfo());
					item.setContractPeriod(basketItemList.get(i).getContractPeriod());
					bvasMandItemList.add(item);
				} else {
					item = new ImsRptBasketItemDTO(); 
					item.setType(basketItemList.get(i).getType());
					item.setMdoInd(basketItemList.get(i).getMdoInd());
					item.setItemID(basketItemList.get(i).getItemID());
					item.setItemTitle(basketItemList.get(i).getItemDetailName());
					item.setItemMthFix(basketItemList.get(i).getItemMthFix());
					item.setItemMth2Mth(basketItemList.get(i).getItemMth2Mth());
					item.setItemDetails(basketItemList.get(i).getItemDetails());
					item.setOfferCode(basketItemList.get(i).getOfferCode());
					item.setPreInstOfferCd(basketItemList.get(i).getPreInstOfferCd());
					item.setPreUseOfferCd(basketItemList.get(i).getPreUseOfferCd());
					item.setIncentiveCd(basketItemList.get(i).getIncentiveCd());
					item.setContractPeriod(basketItemList.get(i).getContractPeriod());
					bvasNonMItemList.add(item);
				}
			}
			if (basketItemList.get(i).getType().equalsIgnoreCase("PRE_INST")) {
				item = new ImsRptBasketItemDTO(); 
				item.setType(basketItemList.get(i).getType());
				item.setMdoInd(basketItemList.get(i).getMdoInd());
				item.setItemTitle(basketItemList.get(i).getItemDetailName());
				item.setItemMthFix(basketItemList.get(i).getItemMthFix());
				item.setItemMth2Mth(basketItemList.get(i).getItemMth2Mth());
				item.setItemDetails(basketItemList.get(i).getItemDetails());
				item.setIncentiveCd(basketItemList.get(i).getIncentiveCd());
				item.setOfferCode(basketItemList.get(i).getOfferCode());
				item.setContractPeriod(basketItemList.get(i).getContractPeriod());
				item.setItemID(basketItemList.get(i).getItemID());
				preInstItemList.add(item);
			}
			if (basketItemList.get(i).getType().equalsIgnoreCase("OTHER")) {
				if(basketItemList.get(i).getOfferCode()!=null && !"".equals(basketItemList.get(i).getOfferCode())){
					tempOtherOfferCd+=","+basketItemList.get(i).getOfferCode();
				}
				coreServiceDtl.setOfferCdOfOther(tempOtherOfferCd);
			}
		}
		coreServiceDtl.setProgItem(progItem);
		coreServiceDtl.setBvasMandItemList(bvasMandItemList);
		coreServiceDtl.setBvasNonMItemList(bvasNonMItemList);
		servPlanDto.setCoreServiceDetail(coreServiceDtl);

		
		// Optional Service
		for (int i=0; i < optServiceList.size(); i++){
			if (optServiceList.get(i).getType().equalsIgnoreCase("OPT_PREM")) {
				ImsRptBasketItemDTO optPrem = new ImsRptBasketItemDTO(); 
				optPrem.setType(optServiceList.get(i).getType());
				optPrem.setMdoInd(null);
				optPrem.setItemTitle(optServiceList.get(i).getItemDetailName());
				optPrem.setItemMthFix(optServiceList.get(i).getItemMthFix());
				optPrem.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
				optPrem.setItemDetails(optServiceList.get(i).getItemDetails());
				optPrem.setContractPeriod(optServiceList.get(i).getContractPeriod());
				optPrem.setOfferCode(optServiceList.get(i).getOfferCode());
				optPrem.setPreInstOfferCd(optServiceList.get(i).getPreInstOfferCd());
				optPrem.setPreUseOfferCd(optServiceList.get(i).getPreUseOfferCd());
				optPrem.setIncentiveCd(optServiceList.get(i).getIncentiveCd());
				optPrem.setVasDummyGiftDesc(optServiceList.get(i).getVasDummyGiftDesc());
				optPrem.setItemID(optServiceList.get(i).getItemID());
				optPremItemList.add(optPrem);
			}
/*				if (optServiceList.get(i).getType().equalsIgnoreCase("WL_BB")) {
				ImsRptBasketItemDTO wlBb = new ImsRptBasketItemDTO(); 
				wlBb.setType(optServiceList.get(i).getType());
				wlBb.setMdoInd(null);
				wlBb.setItemTitle(optServiceList.get(i).getItemDetailName());
				wlBb.setItemMthFix(optServiceList.get(i).getItemMthFix());
				wlBb.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
				wlBb.setItemDetails(optServiceList.get(i).getItemDetails());
				wlBbItemList.add(wlBb);
			}
			if (optServiceList.get(i).getType().equalsIgnoreCase("ANTI_VIR")) {
				ImsRptBasketItemDTO antiVir = new ImsRptBasketItemDTO(); 
				antiVir.setType(optServiceList.get(i).getType());
				antiVir.setMdoInd(null);
				antiVir.setItemTitle(optServiceList.get(i).getItemDetailName());
				antiVir.setItemMthFix(optServiceList.get(i).getItemMthFix());
				antiVir.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
				antiVir.setItemDetails(optServiceList.get(i).getItemDetails());
				antiVirItemList.add(antiVir);
			}
*/
			if (!optServiceList.get(i).getType().equalsIgnoreCase("OPT_PREM")
					&& !optServiceList.get(i).getType().equalsIgnoreCase("MEDIA")) {
				ImsRptBasketItemDTO optServ = new ImsRptBasketItemDTO(); 
				optServ.setType(optServiceList.get(i).getType());
				optServ.setMdoInd(null);
				optServ.setContractPeriod(optServiceList.get(i).getContractPeriod());
				optServ.setItemTitle(optServiceList.get(i).getItemDetailName());
				optServ.setItemMthFix(optServiceList.get(i).getItemMthFix());
				optServ.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
				optServ.setItemDetails(optServiceList.get(i).getItemDetails());
				optServ.setOfferCode(optServiceList.get(i).getOfferCode());
				optServ.setPreInstOfferCd(optServiceList.get(i).getPreInstOfferCd());
				optServ.setPreUseOfferCd(optServiceList.get(i).getPreUseOfferCd());
				optServ.setMobileNum(optServiceList.get(i).getMobileNum());
				optServ.setIncentiveCd(optServiceList.get(i).getIncentiveCd());
				optServ.setVasDummyGiftDesc(optServiceList.get(i).getVasDummyGiftDesc());
				optServ.setItemID(optServiceList.get(i).getItemID());
				optServItemList.add(optServ);
			}
			if (optServiceList.get(i).getType().equalsIgnoreCase("MEDIA")) {
				ImsRptBasketItemDTO media = new ImsRptBasketItemDTO(); 
				media.setType(optServiceList.get(i).getType());
				media.setMdoInd(null);
				media.setContractPeriod(optServiceList.get(i).getContractPeriod());
				media.setItemTitle(optServiceList.get(i).getItemDetailName());
				media.setItemMthFix(optServiceList.get(i).getItemMthFix());
				media.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
				media.setItemDetails(optServiceList.get(i).getItemDetails());
				media.setOfferCode(optServiceList.get(i).getOfferCode());
				media.setIncentiveCd(optServiceList.get(i).getIncentiveCd());
				media.setVasDummyGiftDesc(optServiceList.get(i).getVasDummyGiftDesc());
				media.setItemID(optServiceList.get(i).getItemID());
				mediaItemList.add(media);
			}
		}
		optServiceDtl.setOptPremItemList(optPremItemList);
		//optServiceDtl.setWlBbItemList(wlBbItemList);
		//optServiceDtl.setAntiVirItemList(antiVirItemList);
		optServiceDtl.setOptServItemList(optServItemList);
		optServiceDtl.setMediaItemList(mediaItemList);
//		servPlanDto.setOptServiceDetail(optServiceDtl);
		logger.debug("\noptPremItemList list =" + optPremItemList);
		logger.debug("\noptServItemList list =" + optServItemList);
		logger.debug("\nmediaItemList list =" + mediaItemList);
		//Backend VAS 
		for (int i = 0; backendVasList !=null && i< backendVasList.size(); i++){
			ImsRptBasketItemDTO backendVas = new ImsRptBasketItemDTO();
			backendVas.setType(backendVasList.get(i).getType());
			backendVas.setMdoInd(null);
			backendVas.setContractPeriod(backendVasList.get(i).getContractPeriod());
			backendVas.setItemTitle(backendVasList.get(i).getItemTitle());
			backendVas.setItemMthFix(backendVasList.get(i).getItemMthFix());
			backendVas.setItemMth2Mth(backendVasList.get(i).getItemMth2Mth());
			backendVas.setItemDetails(backendVasList.get(i).getItemDetails());
			backendVas.setOfferCode(backendVasList.get(i).getOfferCode());
			backendVas.setIncentiveCd(backendVasList.get(i).getIncentiveCd());
			backendVasItemList.add(backendVas);
		}
		optServiceDtl.setBackendVasItemList(backendVasItemList);
		servPlanDto.setOptServiceDetail(optServiceDtl);
		
		//Gift
		servPlanDto.setGiftList(giftList);

		
		// Now TV
		for (int i=0; i < ntvPgmItemList.size(); i++){
			if (ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_FREE")) {
				ImsRptBasketItemDTO ntvFree = new ImsRptBasketItemDTO(); 
				ntvFree.setType(ntvPgmItemList.get(i).getType());
				ntvFree.setMdoInd(null);
				ntvFree.setItemTitle(ntvPgmItemList.get(i).getItemDetailName());
				ntvFree.setItemMthFix(ntvPgmItemList.get(i).getItemMthFix());
				ntvFree.setItemMth2Mth(ntvPgmItemList.get(i).getItemMth2Mth());
				ntvFree.setItemDetails(ntvPgmItemList.get(i).getItemDetails());
				ntvFree.setContractPeriod(ntvPgmItemList.get(i).getContractPeriod());
				ntvFree.setOfferCode(ntvPgmItemList.get(i).getOfferCode());
				ntvFree.setIncentiveCd(ntvPgmItemList.get(i).getIncentiveCd());
				ntvFreeItemList.add(ntvFree);
			}
/*			if (ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_PAY") ||
				ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_P_30F6")) {*/
			if (!ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_FREE") 
					&& !ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_OTHER")
					&& !ntvPgmItemList.get(i).getType().contains ("AIO_SUBOWN")
					&& !ntvPgmItemList.get(i).getType().contains ("AIO_RENTAL")
					&& !ntvPgmItemList.get(i).getType().contains ("HDD_PREM")) {
				ImsRptBasketItemDTO ntvPay = new ImsRptBasketItemDTO(); 
				ntvPay.setType(ntvPgmItemList.get(i).getType());
				ntvPay.setMdoInd(null);
				ntvPay.setItemTitle(ntvPgmItemList.get(i).getItemDetailName());
				ntvPay.setItemMthFix(ntvPgmItemList.get(i).getItemMthFix());
				ntvPay.setItemMth2Mth(ntvPgmItemList.get(i).getItemMth2Mth());
				ntvPay.setItemDetails(ntvPgmItemList.get(i).getItemDetails());
				ntvPay.setOfferCode(ntvPgmItemList.get(i).getOfferCode());
				ntvPay.setContractPeriod(ntvPgmItemList.get(i).getContractPeriod());
				ntvPay.setIncentiveCd(ntvPgmItemList.get(i).getIncentiveCd());
				ntvPayItemList.add(ntvPay);
			}
			if (ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_OTHER")
					||ntvPgmItemList.get(i).getType().contains ("AIO_SUBOWN")
					||ntvPgmItemList.get(i).getType().contains ("AIO_RENTAL")
					||ntvPgmItemList.get(i).getType().contains ("HDD_PREM")) {
				ImsRptBasketItemDTO ntvOther = new ImsRptBasketItemDTO(); 
				ntvOther.setType(ntvPgmItemList.get(i).getType());
				ntvOther.setMdoInd(null);
				ntvOther.setItemTitle(ntvPgmItemList.get(i).getItemDetailName());
				ntvOther.setItemMthFix(ntvPgmItemList.get(i).getItemMthFix());
				ntvOther.setItemMth2Mth(ntvPgmItemList.get(i).getItemMth2Mth());
				ntvOther.setItemDetails(ntvPgmItemList.get(i).getItemDetails());
				ntvOther.setOfferCode(ntvPgmItemList.get(i).getOfferCode());
				ntvOther.setContractPeriod(ntvPgmItemList.get(i).getContractPeriod());
				ntvOther.setIncentiveCd(ntvPgmItemList.get(i).getIncentiveCd());
				ntvOtherItemList.add(ntvOther);
			}
		}
		
		for (int i=0; i < channelFreeList.size(); i++){
			if (channelFreeList.get(i).getChannelGroupCd().equalsIgnoreCase("STARTERPACK")) {
				ImsRptChannelDTO starterPack = new ImsRptChannelDTO(); 
				starterPack.setChannelGroupCd(channelFreeList.get(i).getChannelGroupCd());
				starterPack.setChannelGroupDesc(channelFreeList.get(i).getChannelGroupDesc());
				starterPack.setChannelId(channelFreeList.get(i).getChannelId());
				starterPack.setChannelCd(channelFreeList.get(i).getChannelCd());
				starterPack.setTvType(channelFreeList.get(i).getTvType());
				starterPack.setCredit(channelFreeList.get(i).getCredit());
				starterPack.setMdoInd(channelFreeList.get(i).getMdoInd());
				starterPack.setChannelDesc(channelFreeList.get(i).getChannelDesc());
				starterPack.setCampaignCd(channelFreeList.get(i).getCampaignCd());
				starterPack.setPlanCd(channelFreeList.get(i).getPlanCd());
				ntvFreeSPChannelList.add(starterPack);
			}
			if (channelFreeList.get(i).getChannelGroupCd().equalsIgnoreCase("ENTPACK")) {
				ImsRptChannelDTO enterPack = new ImsRptChannelDTO(); 
				enterPack.setChannelGroupCd(channelFreeList.get(i).getChannelGroupCd());
				enterPack.setChannelGroupDesc(channelFreeList.get(i).getChannelGroupDesc());
				enterPack.setChannelId(channelFreeList.get(i).getChannelId());
				enterPack.setChannelCd(channelFreeList.get(i).getChannelCd());
				enterPack.setTvType(channelFreeList.get(i).getTvType());
				enterPack.setCredit(channelFreeList.get(i).getCredit());
				enterPack.setMdoInd(channelFreeList.get(i).getMdoInd());
				enterPack.setChannelDesc(channelFreeList.get(i).getChannelDesc());
				enterPack.setCampaignCd(channelFreeList.get(i).getCampaignCd());
				enterPack.setPlanCd(channelFreeList.get(i).getPlanCd());
				ntvFreeEPChannelList.add(enterPack);
			}
			if (channelFreeList.get(i).getChannelGroupCd().equalsIgnoreCase("FREE2HD")) {
				ImsRptChannelDTO free2Hd = new ImsRptChannelDTO(); 
				free2Hd.setChannelGroupCd(channelFreeList.get(i).getChannelGroupCd());
				free2Hd.setChannelGroupDesc(channelFreeList.get(i).getChannelGroupDesc());
				free2Hd.setChannelId(channelFreeList.get(i).getChannelId());
				free2Hd.setChannelCd(channelFreeList.get(i).getChannelCd());
				free2Hd.setTvType(channelFreeList.get(i).getTvType());
				free2Hd.setCredit(channelFreeList.get(i).getCredit());
				free2Hd.setMdoInd(channelFreeList.get(i).getMdoInd());
				free2Hd.setChannelDesc(channelFreeList.get(i).getChannelDesc());
				free2Hd.setParentChannelId(channelFreeList.get(i).getParentChannelId());
				free2Hd.setCampaignCd(channelFreeList.get(i).getCampaignCd());
				free2Hd.setPlanCd(channelFreeList.get(i).getPlanCd());
				ntvFreeHDChannelList.add(free2Hd);
			}
		}
		
		for (int i=0; i < channelPayList.size(); i++){
			ImsRptChannelDTO channelPay = new ImsRptChannelDTO(); 
			channelPay.setChannelGroupCd(null);
			channelPay.setChannelGroupDesc(channelPayList.get(i).getChannelGroupDesc());
			channelPay.setChannelId(channelPayList.get(i).getChannelId());
			channelPay.setChannelCd(channelPayList.get(i).getChannelCd());
			channelPay.setTvType(channelPayList.get(i).getTvType());
			channelPay.setCredit(channelPayList.get(i).getCredit());
			channelPay.setMdoInd(channelPayList.get(i).getMdoInd());
			channelPay.setChannelDesc(channelPayList.get(i).getChannelDesc());
			channelPay.setParentChannelId(channelPayList.get(i).getParentChannelId());
			channelPay.setCampaignCd(channelPayList.get(i).getCampaignCd());
			channelPay.setPlanCd(channelPayList.get(i).getPlanCd());
			ntvPayChannelList.add(channelPay);
		}
		
		for (int i=0; i < channelBackendList.size(); i++){//Gary
			ImsRptChannelDTO channelBackend = new ImsRptChannelDTO(); 			
			channelBackend.setCampaignCd(channelBackendList.get(i).getCampaignCd());
			channelBackend.setOfferCd(channelBackendList.get(i).getOfferCd());
			ntvBackendChannelList.add(channelBackend);
		}

		ntvServiceDtl.setNtvFreeItemList(ntvFreeItemList);
		ntvServiceDtl.setNtvPayItemList(ntvPayItemList);
		ntvServiceDtl.setNtvOtherItemList(ntvOtherItemList);
		ntvServiceDtl.setNtvFreeSPChannelList(ntvFreeSPChannelList);
		ntvServiceDtl.setNtvFreeEPChannelList(ntvFreeEPChannelList);
		ntvServiceDtl.setNtvFreeHDChannelList(ntvFreeHDChannelList);
		ntvServiceDtl.setNtvPayChannelList(ntvPayChannelList);
		ntvServiceDtl.setNtvBackendChannelList(ntvBackendChannelList);
		if(newPricingItemList!=null){
			ntvServiceDtl.setNewTVPricingItemList(newPricingItemList);
		}
		servPlanDto.setNtvServiceDetail(ntvServiceDtl);
		
		servPlanDto.setQosMeasureInd(qosMeasureInd);

		servPlanDto.setWqRemarkForInterialUse(this.get_wq_remark(order.getOrderId()));
		
		
		List<SubscribedCslOfferDTO> SubscribedCslOfferList = new ArrayList<SubscribedCslOfferDTO>();
		if(order.getSubscribedCslOffers() != null){
		String prodIdsForAPI ="";
		for(SubscribedCslOfferDTO dto:order.getSubscribedCslOffers()){
			if(!"".equals(prodIdsForAPI)){
				prodIdsForAPI+=",";
			}
			prodIdsForAPI+=dto.getProd_id();
		}
		logger.debug("prodIdsForAPI:" + prodIdsForAPI);
		logger.debug("locale:" + locale);
		List<VasParmDTO> mrtPinProductList = imsBasketCommonService.getVasParmsByProdId(prodIdsForAPI, "NONONLINE",locale);

		logger.debug("mrtPinProductList:" + gson.toJson(mrtPinProductList));
		for(SubscribedCslOfferDTO dto:order.getSubscribedCslOffers()){
			for(VasParmDTO displayDto:mrtPinProductList){
				if(dto.getProd_id().equals(displayDto.getProd_id())){
					displayDto.setOrder_id(dto.getOrder_id());
					displayDto.setItem_id(dto.getItem_id());
					displayDto.setVas_parm_a(dto.getVas_parm_a());
					displayDto.setVas_parm_b(dto.getVas_parm_b());
					displayDto.setVas_parm_c(dto.getVas_parm_c());
				}
			}
		}
		
		servPlanDto.setVasParmDTO(mrtPinProductList);
		
		}
		
		if(order.getSubscribedImsVasParm() != null){
			String subItemId ="";
			String prodIdsForAPI ="";
			for(SubscribedCslOfferDTO dto:order.getSubscribedImsVasParm()){
				if(!"".equals(subItemId)){
					subItemId+=",";
				}
				subItemId+=dto.getItem_id();
				if(!"".equals(prodIdsForAPI)){
					prodIdsForAPI+=",";
				}
				prodIdsForAPI+=dto.getProd_id();
			}
			List<VasParmDTO> imsVasParmList = new ArrayList<VasParmDTO>();
			List<VasParmDTO> imsVasParmRemoveList = new ArrayList<VasParmDTO>();
			try {
				imsVasParmList = VASDetailDao.getImsVasParmListByItemId(subItemId, locale);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<VasParmDTO> imsVasParmProductList = imsBasketCommonService.getVasParmsByProdId(prodIdsForAPI, "NONONLINE",locale);
			logger.debug("imsVasParmList:" + gson.toJson(imsVasParmList));
			logger.debug("imsVasParmProductList:" + gson.toJson(imsVasParmProductList));
			logger.debug("order.getSubscribedImsVasParm():" + gson.toJson(order.getSubscribedImsVasParm()));
			//remove same type different product id
			for(int j=0;j<imsVasParmList.size();j++){
				boolean ind = false;
				for(SubscribedCslOfferDTO dto:order.getSubscribedImsVasParm()){
					if(dto.getVas_type().equalsIgnoreCase(imsVasParmList.get(j).getVas_type())){
						for(int k=0;k<imsVasParmProductList.size();k++){
							if(dto.getItem_id().equals(imsVasParmList.get(j).getItem_id())&&dto.getProd_id().equals(imsVasParmProductList.get(k).getProd_id())
									&&dto.getVas_type().equals(imsVasParmProductList.get(k).getVas_type())){
								ind = true;
								break;
							}
						}
					}
				}
				if(!ind){
					imsVasParmRemoveList.add(imsVasParmList.get(j));
				}
			}
			if(imsVasParmRemoveList!=null&&imsVasParmRemoveList.size()>0){
				for(VasParmDTO dto:imsVasParmRemoveList){
					imsVasParmList.remove(dto);
				}
			}
			logger.debug("final imsVasParmList:" + gson.toJson(imsVasParmList));
			

			for(SubscribedCslOfferDTO dto:order.getSubscribedImsVasParm()){
				for(VasParmDTO displayDto:imsVasParmList){
					if(dto.getItem_id().equals(displayDto.getItem_id())){
						displayDto.setOrder_id(dto.getOrder_id());
						displayDto.setVas_parm_a(dto.getVas_parm_a());
						displayDto.setVas_parm_b(dto.getVas_parm_b());
						displayDto.setVas_parm_c(dto.getVas_parm_c());
					}
				}
			}
			
			servPlanDto.setImsVasParmList(imsVasParmList);
			
			
		}
		
		return servPlanDto;
	}
	//Gary isPT Test
	

	public String get_wq_remark(String order_id) {
		try {
			return this.imsReport2Dao.get_wq_remark(order_id);
		} catch (DAOException de) {
			logger.error("Fail in ImsReport2ServiceImpl.get_wq_remark", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	public HashMap<String, HashMap<String, RptServiceInfoDTO>> getStaticReportWords( Boolean isPT,Boolean isDs) {

		HashMap<String, RptServiceInfoDTO> chiWords  = new HashMap<String, RptServiceInfoDTO>();
		HashMap<String, RptServiceInfoDTO> engWords  = new HashMap<String, RptServiceInfoDTO>();
		HashMap<String, HashMap<String, RptServiceInfoDTO>> result= new HashMap<String, HashMap<String, RptServiceInfoDTO>>();
		try {
			chiWords=imsReport2Dao.getStaticReportWords("zh_HK",isPT, isDs);
			engWords=imsReport2Dao.getStaticReportWords("en",isPT, isDs);
			result.put("zh_HK", chiWords);
			result.put("en", engWords);
			return result;
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return null;
	}
	
	public void get3rdPartyCreditCardFormStaticReportWords(String rptName,
			HashMap<String, HashMap<String, String>> dBRptStaticWords) {
		HashMap<String, HashMap<String, String>> result= new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> contents = null;
		try {
			contents = imsReport2Dao.get3rdPartyCreditCardFormStaticReportWords(rptName);
			if (dBRptStaticWords.get("en") != null) {
				dBRptStaticWords.get("en").putAll(contents);
			}
			if (dBRptStaticWords.get("zh_HK") != null) {
				dBRptStaticWords.get("zh_HK").putAll(contents);
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String isUseDBRptStaticWords() {
		try {
			return imsReport2Dao.isUseDBStaticWord();
		} catch (DAOException e) {
			e.printStackTrace();
			return "N";
		}
	}
	
	public String isAFShowALL() {
		try {
			return imsReport2Dao.isAFShowALL();
		} catch (DAOException e) {
			e.printStackTrace();
			return "N";
		}
	}
	

	private String[] splitString(String pString) {
		
		if(pString!=null){
			String[] strArray = new String[2];
			int newlineInx = pString.indexOf((char)10+"");
			int newlineInx2 = pString.indexOf("<br/>");
			
			if(newlineInx>0){
				strArray[0] = pString.substring(0, newlineInx);
				strArray[1] = pString.substring(newlineInx+1);
			}
			else if (newlineInx2>0){
				strArray[0] = pString.substring(0, newlineInx2);
				strArray[1] = pString.substring(newlineInx2+5);
			}
			else {
				strArray[0] = pString;
				strArray[1] = "";
			}
			
			return strArray;
		}
		else return null;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public NowTVAddUI getNewTVPricingDtl(String housingType, String custType, String payMethod,String contract_period, String locale) throws DAOException {
	//	return nowTVDAO.getNewTVPricingDtl(housingType, custType, payMethod,contract_period, locale);
		
		return new NowTVAddUI();
	} //martin
	

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getSalesChannel (String shop_cd, String staff_id){

		
		logger.debug("getSalesChannel is called");
		String salesChannel = "";
		
		try{			
			salesChannel = imsReport2Dao.getSalesChannel(shop_cd, staff_id);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return salesChannel;
	}
	
}

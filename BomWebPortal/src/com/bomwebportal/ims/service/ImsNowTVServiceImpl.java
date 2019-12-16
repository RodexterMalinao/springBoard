package com.bomwebportal.ims.service;

import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.BasketSelectDAO;
import com.bomwebportal.ims.dao.DecoderTypeDAO;
import com.bomwebportal.ims.dao.NowTVDAO;
import com.bomwebportal.ims.dto.BandwidthDTO;
import com.bomwebportal.ims.dto.ChannelDetailDTO;
import com.bomwebportal.ims.dto.HousingTypeDTO;
import com.bomwebportal.ims.dto.ImsBasketDTO;
import com.bomwebportal.ims.dto.ui.BasketUI;
import com.bomwebportal.ims.dto.ui.ChannelUI;
import com.bomwebportal.ims.dto.ui.GenreUI;
import com.bomwebportal.ims.dto.ui.GiftUI;
import com.bomwebportal.ims.dto.ui.ImsDecodeTypeUI;
import com.bomwebportal.ims.dto.ui.NowChannelGroupUI;
import com.bomwebportal.ims.dto.ui.NowTVOfferUI;
import com.bomwebportal.ims.dto.ui.NowTVUI;
import com.bomwebportal.ims.dto.ui.NowTVVasUI;
import com.bomwebportal.ims.dto.ui.NowTvCampaignUI;
import com.bomwebportal.ims.dto.ui.NowTvChannelUI;
import com.bomwebportal.ims.dto.ui.NowTvNewPriceVasUI;
import com.bomwebportal.ims.dto.ui.NowTvPackUI;
import com.bomwebportal.ims.dto.ui.NowTvTopUpUI;
import com.bomwebportal.ims.dto.ui.NowTvVasBundle;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.service.ImsNowTVService.NowTvList;
import com.google.gson.Gson;

import com.bomwebportal.ims.dto.ui.NowChannelUI;
import com.bomwebportal.ims.dto.ui.NowTVCampaignCdDTO;
import com.bomwebportal.ims.dto.ui.NowTVAddUI;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileSubscribedPlanResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SpChannel;
import com.bomwebportal.lts.wsViQuadplayClient.SpProfileSubscribedPlan;
import com.bomwebportal.util.NTVUtil;
import com.bomwebportal.ims.dao.NowTVCheckDAO;

@Transactional(readOnly=true)
public class ImsNowTVServiceImpl implements ImsNowTVService {
	
	private Gson gson = new Gson();
	
	private NowTVDAO nowTVDao;
	
	private NowTVCheckDAO nowTVCheckDao;
	
	private DecoderTypeDAO decoderTypeDao;
	
	private ImsViQuadplayService imsViQuadplayService;
	
	private static final String STATUS_ACTIVE = "ACTIVE";
	private static final String STATUS_ACTIVE_PENDING = "ACTIVE_PENDING";
	private static final String STATUS_ACTIVE_FAIL = "ACTIVE_FAIL";
	private static final String STATUS_INACTIVE = "INACTIVE";
	private static final String STATUS_INACTIVE_PENDING = "INACTIVE_PENDING";
	private static final String STATUS_INACTIVE_FAIL = "INACTIVE_FAIL";
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<NowTVVasUI> getNowTVStarterList(String locale, String basketID, String appDate)
	{
		List<NowTVVasUI> nowTVStartList =new ArrayList<NowTVVasUI>();
		try{
			logger.info("getNowTVStarterList() is called in ImsNowTVServiceImpl");
			nowTVStartList= nowTVDao.getNowTVStarterList(locale, basketID, appDate);
		}catch (DAOException de) {
			logger.error("Exception caught in getNowTVStarterList()", de);
			throw new AppRuntimeException(de);
		}
		
		return nowTVStartList;
		
	}
	
	public List<NowTVVasUI> getNowTVDescList(String locale,
													String NowTVFormType,
													String DescType, 
													String appDate)
	{
		List<NowTVVasUI> nowTVDescList =new ArrayList<NowTVVasUI>();
		try{
			logger.info("getNowTVDescList() is called in ImsNowTVServiceImpl");
			nowTVDescList= nowTVDao.getNowTVDescList(locale, NowTVFormType, DescType, appDate);
		}catch (DAOException de) {
			logger.error("Exception caught in getNowTVDescList()", de);
			throw new AppRuntimeException(de);
		}
		
		return nowTVDescList;
		
	}
	
	public List<NowTVVasUI> getNowTVVasList(String locale, 
											String contractPeriod,
			 								String NowTVFormType, 
			 								String IsCoupon, 
			 								String TVTypeStr,
											String HOSTypeStr, 
			 								String ContractPeriod, 
											String appDate,
											String housing_type)
	{
		List<NowTVVasUI> nowTVVasList =new ArrayList<NowTVVasUI>();
		try{
			logger.info("getNowTVVasList() is called in ImsNowTVServiceImpl");
			nowTVVasList= nowTVDao.getNowTVVasList(locale,contractPeriod,NowTVFormType,IsCoupon,TVTypeStr,HOSTypeStr,ContractPeriod, appDate, housing_type);
		}catch (DAOException de) {
			logger.error("Exception caught in getNowTVVasList()", de);
			throw new AppRuntimeException(de);
		}
		
		return nowTVVasList;
		
	}
	
	public List<NowTVVasUI> getNowTVOtherList(String locale, 
											  String basketID, 
											  String TVTypeStr,
											  String appDate,
											  String housing_type, 
											  String pcdLike100Ind,
											  String pcdNowOneBoxInd,
											  String bandwidth)
	{
		List<NowTVVasUI> nowTVOtherList =new ArrayList<NowTVVasUI>();
		try{
			logger.info("getNowTVOtherList() is called in ImsNowTVServiceImpl");
			nowTVOtherList= nowTVDao.getNowTVOtherList(locale, basketID,TVTypeStr, appDate,housing_type,pcdLike100Ind,pcdNowOneBoxInd,bandwidth);
		}catch (DAOException de) {
			logger.error("Exception caught in getNowTVOtherList()", de);
			throw new AppRuntimeException(de);
		}
		
		return nowTVOtherList;
		
	}
	
	public List<ChannelUI> getNowTVChannelList (String locale, 
			  									String NowTVFormType ,
			  									String TVTypeStr,
			  									boolean IsLamma, 
												String appDate)
	{
		List<ChannelUI> nowTVChannelList =new ArrayList<ChannelUI>();
		try{
			logger.info("getNowTVChannelList() is called in ImsNowTVServiceImpl");
			nowTVChannelList= nowTVDao.getNowTVChannelList(locale,NowTVFormType,TVTypeStr,IsLamma, appDate);
		}catch (DAOException de) {
			logger.error("Exception caught in getNowTVChannelList()", de);
			throw new AppRuntimeException(de);
		}
		
		return nowTVChannelList;
		
	}
	
	public List<ChannelUI> getNowTVHDList(String locale,
											   String NowTVFormType, 
											   String TVTypeStr, 
												String appDate) {
		List<ChannelUI> nowTVHDList = new ArrayList<ChannelUI>();
		try {
			logger.info("getNowTVHDList() is called in ImsNowTVServiceImpl");
			nowTVHDList = nowTVDao.getNowTVHDList(locale,
												  NowTVFormType, 
												  TVTypeStr, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getNowTVHDList()", de);
			throw new AppRuntimeException(de);
		}

		return nowTVHDList;

	}
	
	public List<ChannelUI> getExclusiveList(String locale, List<String> selectedList, 
			String appDate) {
		List<ChannelUI> ExclusiveList = new ArrayList<ChannelUI>();
		try {
			logger.info("getExclusiveList() is called in ImsNowTVServiceImpl");
			ExclusiveList = nowTVDao.getExclusiveList(locale, selectedList, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getExclusiveList()", de);
			throw new AppRuntimeException(de);
		}

		return ExclusiveList;

	}
	
	public List<ImsDecodeTypeUI> getDecodeType(){
		List<ImsDecodeTypeUI> decodeTypeList = new ArrayList<ImsDecodeTypeUI>();
		try {
			logger.info("getDecodeType() is called in ImsNowTVServiceImpl");
			decodeTypeList = nowTVDao.getDecodeType();
		} catch (DAOException de) {
			logger.error("Exception caught in getDecodeType()", de);
			throw new AppRuntimeException(de);
		}

		return decodeTypeList;

	}
	public String getDecoderType(String ParmStr){
		String DecoderType = "";
		try{
			logger.info("getDecoderType() is called in ImsNowTVServiceImpl");
			DecoderType= nowTVDao.getDecoderType(ParmStr);
		}catch (DAOException de) {
			logger.error("Exception caught in getDecoderType()", de);
			throw new AppRuntimeException(de);
		}
		
		return DecoderType;
	}
	public String getDecoderType(String ParmStr, String Serbdyno)
	{
		String DecoderType = "";
		try{
			logger.info("getDecoderType() is called in ImsNowTVServiceImpl");
			DecoderType= decoderTypeDao.getDecoderType(ParmStr, Serbdyno);
		}catch (DAOException de) {
			logger.error("Exception caught in getDecoderType()", de);
			throw new AppRuntimeException(de);
		}
		
		return DecoderType;
		
	}
	
	
	public NowTVDAO getNowTVDAO() {
		return nowTVDao;
	}

	public void setNowTVDAO(NowTVDAO nowTVDao) {
		this.nowTVDao = nowTVDao;
	}

	public DecoderTypeDAO getDecoderTypeDao() {
		return decoderTypeDao;
	}

	public void setDecoderTypeDao(DecoderTypeDAO decoderTypeDao) {
		this.decoderTypeDao = decoderTypeDao;
	}

	public List<NowTvList> getNowTvListCodeMapping() {
		
		List<NowTvList> mapping = null;
		
		try{
			logger.info("getNowTvListCodeMapping() is called in ImsNowTVServiceImpl");
			mapping= nowTVDao.getNowTvListCodeMapping();
		}catch (DAOException de) {
			logger.error("Exception caught in getNowTvListCodeMapping()", de);
			throw new AppRuntimeException(de);
		}
		
		
		return mapping;
	}

	public List<String> getPTTVList() {
		List<String> TVList = nowTVDao.getPTTVList();
		return TVList;
	}
	
	public NowTVAddUI getNewTVPricingDtl(OrderImsUI order, String locale, boolean isAF, String orderId) {
		int connType = 0;
		boolean hdPurchased = false;
		boolean nowBoxPurchased = false;
		List<SubscribedItemUI> AddUIsubItems = new ArrayList<SubscribedItemUI>();
		for (SubscribedItemUI i : order.getSubscribedItems()) {
			if (i.getType().contains("NTV_")||i.getType().contains("AIO_")) {
				SubscribedItemUI AddUIitem = new SubscribedItemUI();
				AddUIitem.setId(i.getId());
				AddUIitem.setBasketId(i.getBasketId());
				AddUIitem.setType(i.getType());
				AddUIsubItems.add(AddUIitem);
				if ("NTV_HD".equals(i.getType())) hdPurchased = true;
				if (i.getType().contains("AIO_")) nowBoxPurchased = true;
			}
		}
		
		String housingType = "Mass";
		if(order.getInstallAddress().getHousingTypeList()!=null){
			if(order.getInstallAddress().getHousingTypeList().get(0).getHousingType().indexOf("PH")>=0 || order.getInstallAddress().getHousingTypeList().get(0).getHousingType().indexOf("HOS")>=0)
				housingType="PH";
			if(order.getInstallAddress().getHousingTypeList().get(0).getHousingType().indexOf("PT")>=0)
				housingType="Premier";
		}else{
			if(order.getInstallAddress().getAddrInventory().getBuildingType()!=null){
				if(order.getInstallAddress().getAddrInventory().getBuildingType().indexOf("PH")>=0 || order.getInstallAddress().getAddrInventory().getBuildingType().indexOf("HOS")>=0)
					housingType="PH";
				if(order.getInstallAddress().getAddrInventory().getBuildingType().indexOf("PT")>=0)
					housingType="Premier";
			}
		}
		logger.info("housingType: " + housingType);
		
		DateFormat df =  new SimpleDateFormat("dd/mm/yyyy");
		
		List<BasketUI> priceUIList =  getNtvBaskets(orderId,"ACQ_TV",housingType, 
				"271".equals(order.getInstallAddress().getDistNo())?"Lamma":"Mass", "HD", order.getWarrPeriod(), locale, df.format(order.getAppDate()),
						order.getPcdLike100Ind(),order.getPcdNowOneBoxInd(), isAF);
		
		NowTVAddUI addui = parsePrice2Model(order.getWarrPeriod(), priceUIList);
		addui.loadNowTVAddUISettings(AddUIsubItems);
		connType = addui.renewConnType(nowBoxPurchased, hdPurchased, order.getBandwidth());
		addui.setNtvConnType(connType);

		
		return addui;
	}
	
	public NowTVAddUI getNewTVPricingDtl(OrderImsUI order, String locale, boolean isAF) {
		int connType = 0;
		boolean hdPurchased = false;
		boolean nowBoxPurchased = false;
		List<SubscribedItemUI> AddUIsubItems = new ArrayList<SubscribedItemUI>();
		for (SubscribedItemUI i : order.getSubscribedItems()) {
			if (i.getType().contains("NTV_")||i.getType().contains("AIO_")) {
				SubscribedItemUI AddUIitem = new SubscribedItemUI();
				AddUIitem.setId(i.getId());
				AddUIitem.setBasketId(i.getBasketId());
				AddUIitem.setType(i.getType());
				AddUIsubItems.add(AddUIitem);
				if ("NTV_HD".equals(i.getType())) hdPurchased = true;
				if (i.getType().contains("AIO_")) nowBoxPurchased = true;
			}
		}
		
		String housingType = "Mass";
		if(order.getInstallAddress().getHousingTypeList()!=null){
			if(order.getInstallAddress().getHousingTypeList().get(0).getHousingType().indexOf("PH")>=0 || order.getInstallAddress().getHousingTypeList().get(0).getHousingType().indexOf("HOS")>=0)
				housingType="PH";
			if(order.getInstallAddress().getHousingTypeList().get(0).getHousingType().indexOf("PT")>=0)
				housingType="Premier";
		}else{
			if(order.getInstallAddress().getAddrInventory().getBuildingType()!=null){
				if(order.getInstallAddress().getAddrInventory().getBuildingType().indexOf("PH")>=0 || order.getInstallAddress().getAddrInventory().getBuildingType().indexOf("HOS")>=0)
					housingType="PH";
				if(order.getInstallAddress().getAddrInventory().getBuildingType().indexOf("PT")>=0)
					housingType="Premier";
			}
		}
		logger.info("housingType: " + housingType);
		
		DateFormat df =  new SimpleDateFormat("dd/mm/yyyy");
		
		List<BasketUI> priceUIList =  getNtvBaskets(null,"ACQ_TV",housingType, 
				"271".equals(order.getInstallAddress().getDistNo())?"Lamma":"Mass", "HD", order.getWarrPeriod(), locale, df.format(order.getAppDate()),
						order.getPcdLike100Ind(),order.getPcdNowOneBoxInd(), isAF);
		
		NowTVAddUI addui = parsePrice2Model(order.getWarrPeriod(), priceUIList);
		addui.loadNowTVAddUISettings(AddUIsubItems);
		connType = addui.renewConnType(nowBoxPurchased, hdPurchased, order.getBandwidth());
		addui.setNtvConnType(connType);

		
		return addui;
	}

private NowTVAddUI parsePrice2Model( String contractPeriod,List<BasketUI> ntvBaskets) {
		
		
		NowTVAddUI addui = new NowTVAddUI();
		Map<String,Boolean> groupMap= new HashMap<String,Boolean>();
		for(BasketUI ntv:ntvBaskets){
			NowTvCampaignUI campui = new NowTvCampaignUI();
			campui.setCampId(ntv.getBasketId());
			campui.setFix_term_rate(ntv.getMthFixText());
			campui.setMth_to_mth_rate(ntv.getMthToMthText());
			campui.setImsType(ntv.getCampaignType());
			campui.setMax_select_cnt(Integer.parseInt(ntv.getMaxSelectCnt()==null||StringUtils.isAlpha(ntv.getMaxSelectCnt())?"0":ntv.getMaxSelectCnt()));
			campui.setMin_select_cnt(Integer.parseInt(ntv.getMinSelectCnt()==null||StringUtils.isAlpha(ntv.getMinSelectCnt())?"0":ntv.getMinSelectCnt()));
			campui.setTitle(ntv.getCampaignName());
			campui.setTvType(ntv.getQualityConstraint()==null||ntv.getQualityConstraint().length()<=0?"SD":ntv.getQualityConstraint());
			campui.setReadRight(-1);
			for(NowTvNewPriceVasUI vas:ntv.getItemList()){
				if(vas.getItemType()!=null
						&&(vas.getItemType().toUpperCase().indexOf("FTA")>=0
						||vas.getItemType().toUpperCase().indexOf("HB")>=0
						||vas.getItemType().toUpperCase().indexOf("PB")>=0
						||vas.getItemType().toUpperCase().indexOf("PACK")>=0)){
					NowTvPackUI pack = new NowTvPackUI();
					pack.setCampaign_title(ntv.getCampaignName());
					for(NowTVCampaignCdDTO code :vas.getCampaignCdList() ){
						if("0".equalsIgnoreCase(code.getContractPeriod())){
							pack.setCampaignCd_SOPHIE(code.getCampaignCd());
							pack.setPackCd_SOPHIE(code.getPackCd());
							campui.setContractPeriod(0);
						}
						if(contractPeriod.equalsIgnoreCase(code.getContractPeriod())){
							pack.setCampaignCd_SOPHIE(code.getCampaignCd());
							pack.setPackCd_SOPHIE(code.getPackCd());
							campui.setContractPeriod(Integer.parseInt(contractPeriod));
							break;
						}
					}
					//vas.get
					pack.setParentType(ntv.getCampaignType());
					pack.setParentCampaignId(ntv.getBasketId());
					pack.setPackId(vas.getItemId());
					pack.setTitle(vas.getPackName());
					pack.setType(vas.getItemType());
					pack.setTvType(vas.getQualityConstraint()==null||vas.getQualityConstraint().length()<=0?"SD":vas.getQualityConstraint());
					pack.setMdo_ind(vas.getMdoInd());
					pack.setTnc(vas.getTnc());
					pack.setReadRight(-1);
					pack.setParentCampaignPrice(ntv.getRecurrentAmt());
					pack.setParentMinCnt(ntv.getMinSelectCnt());
					for(NowChannelUI c:vas.getChannelIconList()){
						NowTvChannelUI channel = new NowTvChannelUI();
						channel.setChannelDisplayDetail(c.getChannelName());
						channel.setParentPackId(vas.getItemId());
						channel.setChannelId(c.getChannelCD());
						channel.setAdult("Y".equalsIgnoreCase(c.getIsAdult())?true:false);
						channel.setTvType(c.getQualityConstraint()==null||c.getQualityConstraint().length()<=0?"SD":c.getQualityConstraint());
						channel.setReadRight(-1);
						if(pack.getTvChannels()==null)
							pack.setTvChannels(new ArrayList<NowTvChannelUI>());
						pack.getTvChannels().add(channel);
					}
					if(campui.getTvPacks()==null){
						campui.setTvPacks(new ArrayList<NowTvPackUI>());
					}
					campui.getTvPacks().add(pack);
				}
				else if (vas.getItemType() != null && vas.getItemType().indexOf("COMM") >= 0){
                    if (StringUtils.isEmpty(campui.getTnc())&&"0".equalsIgnoreCase(vas.getVASContractPeriod())) {
                        campui.setTnc(vas.getTnc());

                    }
                    if (contractPeriod.equalsIgnoreCase(vas
                            .getVASContractPeriod())) {
                        campui.setTnc(vas.getTnc());

                    }
                }
				else if(vas.getItemType() != null && vas.getItemType().indexOf("TOPUP") >= 0){
					NowTvTopUpUI topup = new NowTvTopUpUI();
					topup.setParentCampaignId(ntv.getBasketId());
					topup.setItemId(vas.getItemId());
					topup.setItemcd(vas.getPackCd());
					topup.setTitle(vas.getPackName());
					topup.setDetail(vas.getPackDesc());
					topup.setType(vas.getItemType());
					topup.setTvType(vas.getQualityConstraint()==null||vas.getQualityConstraint().length()<=0?"SD":vas.getQualityConstraint());
					for(NowTVCampaignCdDTO code :vas.getCampaignCdList() ){
						if("0".equalsIgnoreCase(code.getContractPeriod())){
							topup.setCampaignCd_SOPHIE(code.getCampaignCd());
							topup.setTopUpCd(code.getTopUpCd());
							topup.setPackCd_SOPHIE(code.getPackCd());
						}
						if(contractPeriod.equalsIgnoreCase(code.getContractPeriod())){
							topup.setCampaignCd_SOPHIE(code.getCampaignCd());
							topup.setTopUpCd(code.getTopUpCd());
							topup.setPackCd_SOPHIE(code.getPackCd());
							break;
						}
					}
					topup.setMdo_ind("O");
					topup.setTnc(vas.getTnc());
					if(campui.getTopUps()==null){
						campui.setTopUps(new ArrayList<NowTvTopUpUI>());
					}
					campui.getTopUps().add(topup);
				}
				/*if((vas.getItemType() != null && vas.getItemType().indexOf("HD") >= 0)
						|| (vas.getItemType() != null && vas.getItemType().indexOf("VAS") >= 0))*/
				else {
					NowTvVasBundle ntvVas = new NowTvVasBundle();
					NowTVOfferUI o = new NowTVOfferUI();
					ntvVas.setItemId(vas.getItemId());
					ntvVas.setType(vas.getItemType());
					ntvVas.setTnc(vas.getTnc());
					for(NowTvNewPriceVasUI offer :vas.getRelatedItemList() ){
						if("0".equalsIgnoreCase(offer.getVASContractPeriod())){
							o.setItemId(offer.getItemId());
							o.setType(offer.getItemType());
							o.setOfferCd(offer.getOfferCd());
						}
						else if(contractPeriod.equalsIgnoreCase(offer.getVASContractPeriod())){
							o.setItemId(offer.getItemId());
							o.setOfferCd(offer.getOfferCd());
							o.setType(offer.getItemType());
							break;
						}
					}
					if(!StringUtils.isEmpty(o.getItemId())){
						ntvVas.getOfferCd().add(o);
					}
					if(campui.getVasBundles()==null){
						campui.setVasBundles(new ArrayList<NowTvVasBundle>());
					}
					campui.getVasBundles().add(ntvVas);
				}
			}
				
			if("FTA".equalsIgnoreCase(campui.getImsType())){
				addui.setFtaCampaign(campui);
			}
			else if("HB".equalsIgnoreCase(campui.getImsType())){
				addui.setHardCampaign(campui);
			}
			else if("PB".equalsIgnoreCase(campui.getImsType())){
				if(addui.getPayTvCampaign()==null)
					addui.setPayTvCampaign(new ArrayList<NowTvCampaignUI>());
				addui.getPayTvCampaign().add(campui);
			}
		}
		return addui;
	}
	
	public List<BasketUI> getNtvBaskets(String orderId, String sysId, String housingType, String customerType, String qualityConstraint, String contractPeriod, String locale, String appDate, String pcdLike100Ind,String pcdNowOneBoxInd,
			boolean isAF){

		List<BasketUI> rtnBasketList = null;
		
		try {
		rtnBasketList = parseNtvBaskets(nowTVDao.getNtvBaskets(orderId, sysId, housingType, customerType, qualityConstraint, "", locale, null,pcdLike100Ind,pcdNowOneBoxInd),
				isAF, orderId, contractPeriod);
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		logger.error("Exception caught in getNtvBaskets()", e);
		e.printStackTrace();
		}
		
		return rtnBasketList;
	}
	public List<BasketUI> parseNtvBaskets(Map<String, List> map, boolean isAF, String orderId, String contractPeriod){
		
		List<BasketUI> campaignList = new ArrayList<BasketUI>();
		Map<String, BasketUI> cMap= new HashMap<String, BasketUI>(); 
		Map<String, NowTvNewPriceVasUI> pMap= new HashMap<String, NowTvNewPriceVasUI>();
		
		if (map == null) campaignList = new ArrayList<BasketUI>();
		else {
			List<Map<String, String>> campaignMap =  (List<Map<String, String>>)map.get("o_campaign_cur");		
			
			for (Map<String, String> m : campaignMap){
				BasketUI b = new BasketUI();
				b.setItemList(new ArrayList<NowTvNewPriceVasUI>());
				
				b.setChannelGroup(new NowChannelGroupUI());			//tmp
				b.setBVasDetailList(new ArrayList<NowTvNewPriceVasUI>());	//tmp
				b.setGiftList(new ArrayList<GiftUI>());				//tmp
				b.setPaymentMethodList(new ArrayList<String>());	//tmp
				
				b.getChannelGroup().setChannelList(new ArrayList<NowChannelUI>());		//tmp
				b.getChannelGroup().setGenreList(new ArrayList<GenreUI>());
				
				GenreUI g = new GenreUI();
				g.setGenreId(m.get("genreId"));
				g.setGenreDesc(m.get("genreDesc"));
				b.getChannelGroup().setGenre(g);
				
				setAttributes(m, b, isAF);
				campaignList.add(b);
				cMap.put(b.getBasketId(), b);
			}
			
			List<Map<String, String>> packMap =  (List<Map<String, String>>)map.get("o_pack_cur");
			//steven clear the log
			HashMap<String, Object> logMap= new  HashMap<String, Object>();
			
			for (Map<String, String> m : packMap){
				NowTvNewPriceVasUI p = new NowTvNewPriceVasUI();
				p.setChannelIconList(new ArrayList<NowChannelUI>());
				p.setCampaignCdList(new ArrayList<NowTVCampaignCdDTO>());
				p.setRelatedItemList(new ArrayList<NowTvNewPriceVasUI>());
				setAttributes(m, p, isAF);

				logMap.put(p.getBasketId() ,p.getItemId());
				if(cMap==null){
					logger.error("cMap==null"+" basketId : " + p.getBasketId() + "itemId : " + p.getItemId());
				}
				if(p.getBasketId()==null){
					logger.error("p.getBasketId()==null"+" basketId : " + p.getBasketId() + "itemId : " + p.getItemId());
				}
				if(cMap.get(p.getBasketId())==null){
					logger.error("cMap.get(p.getBasketId())==null"+" basketId : " + p.getBasketId() + "itemId : " + p.getItemId());
				}
				if(cMap.get(p.getBasketId()).getItemList()==null){
					logger.error("cMap.get(p.getBasketId()).getItemList()==null"+" basketId : " + p.getBasketId() + "itemId : " + p.getItemId());
				}
				cMap.get(p.getBasketId()).getItemList().add(p);
				if("NTV_COMM".equals(p.getItemType())) cMap.get(p.getBasketId()).getBVasDetailList().add(p);
				pMap.put(p.getItemId(), p);
				
				if (StringUtils.equals(p.getItemType(), "NTV_PACK")) {
					GenreUI g = new GenreUI();
					g.setGenreId(p.getKey());
					g.setGenreDesc(p.getPackName());
					cMap.get(p.getBasketId()).getChannelGroup().getGenreList().add(g);
				} 
			}
    		logger.debug(new Gson().toJson(logMap));
			
			List<Map<String, String>> channelMap =  (List<Map<String, String>>)map.get("o_channel_icon_cur");
			
			for (Map<String, String> m : channelMap){
				NowChannelUI c = new NowChannelUI();
				setAttributes(m, c, isAF);
				cMap.get(c.getBasketId()).getpMap().get(c.getVasItemId()).getChannelIconList().add(c);
				
				c.setTVType(c.getQualityConstraint());	//tmp
				
				GenreUI g = new GenreUI();
				g.setGenreId(cMap.get(c.getBasketId()).getpMap().get(c.getVasItemId()).getKey());
				g.setGenreDesc(cMap.get(c.getBasketId()).getpMap().get(c.getVasItemId()).getPackName());
				c.setGenre(g);
				
				cMap.get(c.getBasketId()).getChannelGroup().getChannelList().add(c);
				if ("Y".equals(c.getIsFoxPlus())) {
					cMap.get(c.getBasketId()).setIsFoxPlus(c.getIsFoxPlus());
				}
			}
			
			List<Map<String, String>> campaignCdMap =  (List<Map<String, String>>)map.get("o_campaign_pack_code_cur");
			
			for (Map<String, String> m : campaignCdMap){ 
				NowTVCampaignCdDTO c = new NowTVCampaignCdDTO();
				setAttributes(m, c, isAF);
				cMap.get(c.getBasketId()).getpMap().get(c.getItemId()).getCampaignCdList().add(c);
			}
			
			List<Map<String, String>> optVasMap =  (List<Map<String, String>>)map.get("o_opt_vas_cur");
			
			for (Map<String, String> m : optVasMap){ 
				NowTvNewPriceVasUI v = new NowTvNewPriceVasUI();
				setAttributes(m, v, isAF);
				cMap.get(v.getBasketId()).getpMap().get(m.get("related_itemId")).getRelatedItemList().add(v); 
			}
		}
		
		if(orderId == null){//by pass this checking when orderId is not null (when called in done page)
			for (int i=0;i<campaignList.size();i++){
				//remove the Campaign without commitments
				if(!campaignList.get(i).containPackOfType("NTV_COMM")
						||("PB".equalsIgnoreCase(campaignList.get(i).getCampaignType())&&!campaignList.get(i).checkPayBundleContractPeriod(contractPeriod))) {
					campaignList.remove(i);
					i--; 
				} else {
				//remove the Campaign without Packs/ Channels
					for (NowTvNewPriceVasUI v:campaignList.get(i).getItemList()) {
						if (v.getItemType().equals("NTV_PACK") && v.getChannelIconList().size()==0){
							campaignList.get(i).removePack(v.getKey());
						}
					}
					if(!campaignList.get(i).containPackOfType("NTV_PACK") && !campaignList.get(i).containPackOfType("FTA") && !campaignList.get(i).containPackOfType("PB") && !campaignList.get(i).containPackOfType("HB")) {
						campaignList.remove(i);
						i--; 	
					}
				}
				
			}

		}
		logger.debug("celia "+gson.toJson(campaignList));
		
		return campaignList;
	} 
	
	public List<ChannelDetailDTO> getVIChannelInfo(String fsa) {
		List<ChannelDetailDTO> channelDetailList = new ArrayList<ChannelDetailDTO>();

		SBGetProfileSubscribedPlanResponse profileSubscribedPlan;
		try {
			profileSubscribedPlan = imsViQuadplayService
					.getProfileSubscirbedPlan(fsa);
			if (profileSubscribedPlan == null) {
				logger.info("profileSubscribedPlan: is null");
			} else {
				logger.info("profileSubscribedPlan: "
						+ gson.toJson(profileSubscribedPlan));
			}

			List<SpProfileSubscribedPlan> subscribePlanList = profileSubscribedPlan
					.getProfSubPlen();
			
			logger.debug(gson.toJson(subscribePlanList)); 
			
			for (int i = 0; subscribePlanList != null
					&& i < subscribePlanList.size(); i++) {
								
//				logger.debug(gson.toJson(subscribePlanList.get(i)));
				
				if (StringUtils.equals(STATUS_ACTIVE, subscribePlanList.get(i).getStatus())
						|| StringUtils.equals(STATUS_ACTIVE_PENDING,subscribePlanList.get(i).getStatus())
						|| StringUtils.equals(STATUS_ACTIVE_FAIL,subscribePlanList.get(i).getStatus())
						|| StringUtils.equals(STATUS_INACTIVE,subscribePlanList.get(i).getStatus())
						|| StringUtils.equals(STATUS_INACTIVE_PENDING,subscribePlanList.get(i).getStatus())
						|| StringUtils.equals(STATUS_INACTIVE_FAIL,subscribePlanList.get(i).getStatus())) {
					
					List<SpChannel> spChannelList = subscribePlanList.get(i).getChannels();
					
					if(spChannelList != null && spChannelList.size()>0){
						for (int j = 0; j < spChannelList.size(); j++) {
							/*
							if (!StringUtils.equals(STATUS_ACTIVE, spChannelList
									.get(j).getStatus())) {
								continue;
							}*/
		
							ChannelDetailDTO channelDetail = new ChannelDetailDTO();
							channelDetail.setChannelId(spChannelList.get(j).getChannelId());
							channelDetail.setNameChi(spChannelList.get(j).getNameChi());
							channelDetail.setNameEng(spChannelList.get(j).getNameEng());
							channelDetail.setCampaignCode(subscribePlanList.get(i).getCampaignCode());
							channelDetail.setPlanCode(subscribePlanList.get(i).getPlanCode());
							channelDetail.setCharge("$"+ ((j==0)?String.valueOf(subscribePlanList.get(i).getCharge()):"0.0"));
							channelDetail.setRegisteredDate(subscribePlanList.get(i).getRegisteredDate().toString().substring(0, 10));
							channelDetailList.add(channelDetail);
						}
					}else{
						
						ChannelDetailDTO channelDetail = new ChannelDetailDTO();
						channelDetail.setCampaignCode(subscribePlanList.get(i).getCampaignCode());
						channelDetail.setPlanCode(subscribePlanList.get(i).getPlanCode());
						channelDetail.setCharge("$"+ String.valueOf(subscribePlanList.get(i).getCharge()));
						channelDetail.setRegisteredDate(subscribePlanList.get(i).getRegisteredDate().toString().substring(0, 10));
						channelDetailList.add(channelDetail);
						
					}					
				}
			}
		} catch (Exception e) {
			// fail to retrieve VI profile
			logger.error("", e);
			return null;
		}

		
		if(channelDetailList!=null && channelDetailList.size()>0){
			String planCdStr = "";
			String campaignCDStr = "";
			
			for(ChannelDetailDTO c:channelDetailList){
				if(!StringUtils.isEmpty(c.getPlanCode())) planCdStr+= "'"+c.getPlanCode()+"'" + ",";
				if(!StringUtils.isEmpty(c.getCampaignCode())) campaignCDStr+= "'"+c.getCampaignCode()+"'" + ",";
			}
			
			if(planCdStr.length()>0) {
				planCdStr = planCdStr.substring(0,planCdStr.length()-1);
				List<String> payChannelList = nowTVCheckDao.getPayChannelList(planCdStr);
				if(payChannelList!=null && payChannelList.size()>0){
					for(ChannelDetailDTO c:channelDetailList){
						if(!StringUtils.isEmpty(c.getPlanCode()) && payChannelList.contains(c.getPlanCode())) c.setPayChannel(true);
					}
				}
			}
			if(campaignCDStr.length()>0){
				campaignCDStr = campaignCDStr.substring(0,campaignCDStr.length()-1);
				List<String> entChannelList = nowTVCheckDao.getEntChannelList(campaignCDStr); 
				if(entChannelList!=null && entChannelList.size()>0){
					for(ChannelDetailDTO c:channelDetailList){
						if(!StringUtils.isEmpty(c.getCampaignCode()) && entChannelList.contains(c.getCampaignCode())) c.setEntChannel(true); 
					}
				}
			}
			try {
				Map<String,List<String>> sbHDBUNDLEPLAN = nowTVCheckDao.getSB_HD_BUNDLE_PLAN();
				Set<String> sbHDBUNDLEPLANresult = new HashSet<String>();
				for(ChannelDetailDTO c:channelDetailList){
					if(c.getCampaignCode()!=null&&c.getPlanCode()!=null){
						String key = c.getCampaignCode().trim()+"_"+c.getPlanCode().trim();
						List<String> sL = sbHDBUNDLEPLAN.get(key);
						sbHDBUNDLEPLAN.remove(key);
						//logger.debug("HDBUNDLEPLAN:"+key+" "+s);
						if(sL!=null){
							for(String s : sL)
								sbHDBUNDLEPLANresult.add(s.trim());							
						}
					}
				}
				for(ChannelDetailDTO c:channelDetailList){
					if(c.getCampaignCode()!=null&&c.getPlanCode()!=null){
						if (sbHDBUNDLEPLANresult.contains(c.getCampaignCode().trim()+"_"+c.getPlanCode().trim())){
							c.setPayChannel(true);
						}
					}
				}
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				logger.error("", e);
			}
			
		}
		
		
		return channelDetailList;
	}

	
	
	private void setAttributes(Map<String, String> m, Object pObject, boolean isAF){
		if (m == null) return;
		
		if (isAF) {
			for (Map.Entry<String, String> entry : m.entrySet()) {
			    try {
		    		BeanUtils.setProperty(pObject, entry.getKey(), NTVUtil.defaultStringRpt(entry.getValue()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			for (Map.Entry<String, String> entry : m.entrySet()) {
			    try {
		    		BeanUtils.setProperty(pObject, entry.getKey(), NTVUtil.defaultString(entry.getValue()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<String> getPremierComboCampaignCode() {
		List<String> campaignList = nowTVDao.getPremierComboCampaignCode();
		return campaignList;
	}

	public void setImsViQuadplayService(ImsViQuadplayService imsViQuadplayService) {
		this.imsViQuadplayService = imsViQuadplayService;
	}

	public ImsViQuadplayService getImsViQuadplayService() {
		return imsViQuadplayService;
	}

	public void setNowTVCheckDao(NowTVCheckDAO nowTVCheckDao) {
		this.nowTVCheckDao = nowTVCheckDao;
	}

	public NowTVCheckDAO getNowTVCheckDao() {
		return nowTVCheckDao;
	}
	
}

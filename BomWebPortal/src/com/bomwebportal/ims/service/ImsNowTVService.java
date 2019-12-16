package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.BandwidthDTO;
import com.bomwebportal.ims.dto.ChannelDetailDTO;
import com.bomwebportal.ims.dto.HousingTypeDTO;
import com.bomwebportal.ims.dto.ImsBasketDTO;
import com.bomwebportal.ims.dto.ui.BasketUI;
import com.bomwebportal.ims.dto.ui.ChannelUI;
import com.bomwebportal.ims.dto.ui.ImsDecodeTypeUI;
import com.bomwebportal.ims.dto.ui.NowTVAddUI;
import com.bomwebportal.ims.dto.ui.NowTVUI;
import com.bomwebportal.ims.dto.ui.NowTVVasUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedChannelUI;

public interface ImsNowTVService {

	public List<NowTVVasUI> getNowTVStarterList(String locale,
												String basketID, 
												String appDate);
	
	public List<NowTVVasUI> getNowTVDescList(String locale, 
			 								String NowTVFormType,
			 								String DescType, 
											String appDate);
	
	public List<NowTVVasUI> getNowTVVasList(String locale,
										 String basketID,
			 							 String NowTVFormType, 
			 							 String IsCoupon, 
			 							 String TVTypeStr,
			 							 String HOSTypeStr,
			 							 String ContractPeriod, 
											String appDate,
											String housing_type);
	
	public List<NowTVVasUI> getNowTVOtherList(String locale,
											String contractPeriod,
											String TVTypeStr,
											String appDate,
											String housing_type, 
											String pcdLike100Ind,
											String pcdNowOneBoxInd,
											String bandwidth);

	public List<ChannelUI> getNowTVChannelList (String locale, 
											  String NowTVFormType ,
											  String TVTypeStr,
											  boolean IsLamma, 
												String appDate);
	
	public List<ChannelUI> getNowTVHDList (String locale, 
			  									String NowTVFormType ,
			  									String TVTypeStr, 
												String appDate);
	
	public List<ChannelUI> getExclusiveList (String locale, 
											 List<String> selectedList, 
											 String appDate);
	
	public List<ImsDecodeTypeUI> getDecodeType();
	public String getDecoderType(String ParmStr);
	public String getDecoderType(String ParmStr, String Serbdyno);
	public List<String> getPTTVList(); 

	public NowTVAddUI getNewTVPricingDtl(OrderImsUI order, String locale, boolean isAF);
	public NowTVAddUI getNewTVPricingDtl(OrderImsUI order, String locale, boolean isAF, String orderId);

	public List<BasketUI> getNtvBaskets(String orderId, String sysId, String housingType, String customerType, String qualityConstraint, String contractPeriod, String locale, String appDate, String pcdLike100Ind,String pcdNowOneBoxInd, boolean isAF);
	public List<NowTvList> getNowTvListCodeMapping();
	
	public class NowTvList{
		public String listCode;
		public String listDescription;
	}
	
	public List<ChannelDetailDTO> getVIChannelInfo(String fsa);
	public List<String> getPremierComboCampaignCode(); 
}


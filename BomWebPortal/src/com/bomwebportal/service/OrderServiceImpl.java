package com.bomwebportal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.AuthorizeDAO;
import com.bomwebportal.dao.CustomerProfileDAO;
import com.bomwebportal.dao.DepositDAO;
import com.bomwebportal.dao.MobileSimInfoDAO;
import com.bomwebportal.dao.MultiSimInfoDAO;
import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.dao.OrderMobDAO;
import com.bomwebportal.dao.OrderSearchDAO;
import com.bomwebportal.dao.QuotaPlanInfoDAO;
import com.bomwebportal.dao.VasDetailDAO;
import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.BomWebMupDTO;
import com.bomwebportal.dto.CNMRTSupportDocDTO;
import com.bomwebportal.dto.ClubPointDetailDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.ExclusiveItemDTO;
import com.bomwebportal.dto.IGuardDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobBuoQuotaDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.dto.VasOnetimeAmtDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderDAO;
import com.bomwebportal.mob.ccs.dao.StockDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobSponsorshipDTO;
import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;
import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockUpdateDTO;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsFalloutUI;
import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsSalesInfoService;
import com.bomwebportal.mob.ccs.service.StaffInfoService;
import com.bomwebportal.mob.ds.dao.MobDsMrtManagementDAO;
import com.bomwebportal.mob.ds.dao.MobDsOrderDAO;
import com.bomwebportal.mob.ds.dao.MobDsStockDAO;
import com.bomwebportal.mob.ds.dto.MobDsPaymentTransDTO;
import com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.util.ProjectEagleReportHelper;
import com.pccw.bom.mob.schemas.ProductDTO;

@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

	protected final Log logger = LogFactory.getLog(getClass());

	private SupportDocService supportDocService;
	
	private OrderDAO orderDao;
	private VasDetailDAO vasDetailDao;
	private MobileSimInfoDAO mobileSimInfoDao ;
	private MobCcsOrderDAO mobCcsOrderDao;
	private CustomerProfileDAO customerProfileDao;
	private OrderSearchDAO orderSearchDao;// for common search enhancement
	private OrderMobDAO orderMobDao;// for iGuard
	private StockDAO stockDao;
	private MobDsOrderDAO mobDsOrderDao;
	private DepositDAO depositDao;
	private MobDsMrtManagementDAO mobDsMrtManagementDAO;
	private MultiSimInfoDAO multiSimInfoDao;
	private MobCcsSalesInfoService mobCcsSalesInfoService;
	private MobDsStockDAO mobDsStockDAO;
	private MobCcsMrtService mobCcsMrtService;
	private QuotaPlanInfoDAO quotaPlanInfoDAO;
	private AuthorizeDAO authorizeDAO;
	private StaffInfoService staffInfoService;
	private VasDetailService vasDetailService;

	public OrderMobDAO getOrderMobDao() {
		return orderMobDao;
	}

	public void setOrderMobDao(OrderMobDAO orderMobDao) {
		this.orderMobDao = orderMobDao;
	}

	public SupportDocService getSupportDocService() {
		return supportDocService;
	}

	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}

	public CustomerProfileDAO getCustomerProfileDao() {
		return customerProfileDao;
	}

	public void setCustomerProfileDao(CustomerProfileDAO customerProfileDao) {
		this.customerProfileDao = customerProfileDao;
	}

	public void setMobCcsOrderDao(MobCcsOrderDAO mobCcsOrderDao) {
		this.mobCcsOrderDao = mobCcsOrderDao;
	}

	public MobCcsOrderDAO getMobCcsOrderDao() {
		return mobCcsOrderDao;
	}

	public MobileSimInfoDAO getMobileSimInfoDao() {
		return mobileSimInfoDao;
	}

	public void setMobileSimInfoDao(MobileSimInfoDAO mobileSimInfoDao) {
		this.mobileSimInfoDao = mobileSimInfoDao;
	}

	public OrderDAO getOrderDao() {
		return orderDao;
	}

	public VasDetailDAO getVasDetailDao() {
		return vasDetailDao;
	}


	public void setVasDetailDao(VasDetailDAO vasDetailDao) {
		this.vasDetailDao = vasDetailDao;
	}

	public void setOrderDao(OrderDAO orderDao) {
		this.orderDao = orderDao;
	}
	public StockDAO getStockDao() {
		return stockDao;
	}

	public void setStockDao(StockDAO stockDao) {
		this.stockDao = stockDao;
	}
	
	public MobDsOrderDAO getMobDsOrderDao() {
		return mobDsOrderDao;
	}

	public void setMobDsOrderDao(MobDsOrderDAO mobDsOrderDao) {
		this.mobDsOrderDao = mobDsOrderDao;
	}

	public DepositDAO getDepositDao() {
		return depositDao;
	}

	public void setDepositDao(DepositDAO depositDao) {
		this.depositDao = depositDao;
	}

	public MobDsMrtManagementDAO getMobDsMrtManagementDAO() {
		return mobDsMrtManagementDAO;
	}

	public void setMobDsMrtManagementDAO(MobDsMrtManagementDAO mobDsMrtManagementDAO) {
		this.mobDsMrtManagementDAO = mobDsMrtManagementDAO;
	}

	public MultiSimInfoDAO getMultiSimInfoDao() {
		return multiSimInfoDao;
	}

	public void setMultiSimInfoDao(MultiSimInfoDAO multiSimInfoDao) {
		this.multiSimInfoDao = multiSimInfoDao;
	}

	public MobCcsSalesInfoService getMobCcsSalesInfoService() {
		return mobCcsSalesInfoService;
	}

	public void setMobCcsSalesInfoService(MobCcsSalesInfoService mobCcsSalesInfoService) {
		this.mobCcsSalesInfoService = mobCcsSalesInfoService;
	}

	public MobDsStockDAO getMobDsStockDAO() {
		return mobDsStockDAO;
	}

	public void setMobDsStockDAO(MobDsStockDAO mobDsStockDAO) {
		this.mobDsStockDAO = mobDsStockDAO;
	}

	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
	}

	public QuotaPlanInfoDAO getQuotaPlanInfoDAO() {
		return quotaPlanInfoDAO;
	}

	public void setQuotaPlanInfoDAO(QuotaPlanInfoDAO quotaPlanInfoDAO) {
		this.quotaPlanInfoDAO = quotaPlanInfoDAO;
	}

	public AuthorizeDAO getAuthorizeDAO() {
		return authorizeDAO;
	}

	public void setAuthorizeDAO(AuthorizeDAO authorizeDAO) {
		this.authorizeDAO = authorizeDAO;
	}

	public StaffInfoService getStaffInfoService() {
		return staffInfoService;
	}

	public void setStaffInfoService(StaffInfoService staffInfoService) {
		this.staffInfoService = staffInfoService;
	}
	
	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}
	
	public List<String> getBundleVASList(String basketId, String mipBrand, String mipSimType){
		try {

		logger.debug("getBundleVASList() is called in OrderServiceImpl");
		return orderDao.getBundleVASList(basketId, mipBrand, mipSimType);
	} catch (DAOException de) {
		logger.error("Exception caught in getBundleVASList()", de);
		throw new AppRuntimeException(de);
	}
}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertBomWebAll(String orderId, String selectedBasketId,
			OrderDTO orderDto, SubscriberDTO subscriberDto, MnpDTO mnpDto,
			MobileSimInfoDTO mobileSimInfo, PaymentDTO paymentDto,
			AccountDTO accountDto, CustomerProfileDTO customerInfoDto,
			String selectedItemList[], List<ComponentDTO> componentList, String channelId, IGuardDTO iGuardDto
			, VasDetailDTO vasDetailDto, BasketDTO basketDto, List<MultiSimInfoDTO> multiSimInfos
			, List<VasDetailDTO> systemAssignVasDetailList, List<MobCcsMrtBaseDTO> mrtDtoList,List<MobBuoQuotaDTO> mobBuoQuotaDtoList
			, List<String> authorizeLog) {
		List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs = null;
		List<MobDsPaymentTransDTO> paymentUpfrontDTOs = null;
		this.insertBomWebAll(orderId, selectedBasketId
				, orderDto, subscriberDto, mnpDto
				, mobileSimInfo, paymentDto
				, accountDto, customerInfoDto
				, selectedItemList, componentList, channelId
				, allOrdDocAssgnDTOs, iGuardDto
				, vasDetailDto, basketDto, paymentUpfrontDTOs
				, multiSimInfos, systemAssignVasDetailList, mrtDtoList,mobBuoQuotaDtoList, authorizeLog);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertBomWebAll(String orderId, String selectedBasketId,
			OrderDTO orderDto, SubscriberDTO subscriberDto, MnpDTO mnpDto,
			MobileSimInfoDTO mobileSimInfo, PaymentDTO paymentDto,
			AccountDTO accountDto, CustomerProfileDTO customerInfoDto,
			String selectedItemList[], List<ComponentDTO> componentList, String channelId
			, List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs, IGuardDTO iGuardDto
			, VasDetailDTO vasDetailDto, BasketDTO basketDto, List<MobDsPaymentTransDTO> paymentUpfrontDTOs
			, List<MultiSimInfoDTO> multiSimInfos, List<VasDetailDTO> systemAssignVasDetailList , List<MobCcsMrtBaseDTO> mrtDtoList
			,List<MobBuoQuotaDTO>mobBuoQuotaDtoList, List<String> authorizeLog) {

		try {
			logger.debug("insertBomWebAll() is called in OrderServiceImpl");
			orderDao.insertBomWebOrder(orderDto);
			subscriberDto.setOrigActDate(mnpDto.getOrigActDate()); 
			
			if ("Y".equalsIgnoreCase(subscriberDto.getSameAsEbillAddrInd())){
				subscriberDto.setPcrfAlertEmail(accountDto.getEmailAddr());
			}
			
			orderDao.insertBomWebSub(subscriberDto);
			orderDao.insertBomWebMnp(mnpDto);
			if (CollectionUtils.isNotEmpty(mrtDtoList)) {
				for (MobCcsMrtBaseDTO dto : mrtDtoList) {
					dto.setOrderId(orderId);
					mobCcsMrtService.insertMobCcsMrt(dto);
				}
			}
			
			if ("10".equals(channelId) || "11".equals(channelId)) {
				if (paymentUpfrontDTOs != null) {
					for (MobDsPaymentTransDTO paymentUpfrontDTO: paymentUpfrontDTOs) {
						if (paymentUpfrontDTO.getPaymentAmount() > 0) {
							try {
								orderDao.insertBomWebPaymentTrans(paymentUpfrontDTO);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			if (!"2".equals(channelId) && CollectionUtils.isNotEmpty(mobileSimInfo.getStockAssgnList())) {
				/*
				 * For Retail and Direct Sales
				 * 1. if in old inventory list but not in new list
				 * 		DELETE assgn, INSERT inventory_hist, UPDATE inventory
				 * 2. if only in new inventory list
				 * 		INSERT assgn, INSERT inventory_hist, UPDATE inventory
				 * 3. if in both,
				 * 		UPDATE assgn
				 */
				List<StockAssgnDTO> oldStockAssgnList = stockDao.getStockAssgnListByOrderId(orderId);
				List<StockAssgnDTO> newStockAssgnList = mobileSimInfo.getStockAssgnList();
				
				if(multiSimInfos != null){
					List<String> itemSerialList = new ArrayList<String> ();
					for(StockAssgnDTO newStockAssgn : newStockAssgnList){
						itemSerialList.add(newStockAssgn.getItemSerial());
					}
					
					for(MultiSimInfoDTO msi : multiSimInfos){
						for(MultiSimInfoMemberDTO msim : msi.getMembers()){
							if(!itemSerialList.contains(msim.getSim().getIccid())){
								StockAssgnDTO dummy = new StockAssgnDTO();
								dummy.setMemberNum(msim.getMemberNum());
								dummy.setItemCode(msim.getSim().getItemCode());
								dummy.setItemSerial(msim.getSim().getIccid());
								dummy.setAoInd("N");
								dummy.setItemType("02");
								dummy.setSalesMemoNum(null);
								dummy.setSalesMemoNum2(null);
								newStockAssgnList.add(dummy);
							}
						}
					}
				}
				
				if ("10".equals(channelId) || "11".equals(channelId)) {
					List<String> needFreeSM = new ArrayList<String>();
					for (StockAssgnDTO oldStockAssgn: oldStockAssgnList) {
						//Update Inventory for stock Item
						if (StringUtils.isNotBlank(oldStockAssgn.getItemSerial())) {
							boolean isRemoved = true;
							
							for (StockAssgnDTO newStockAssgn : newStockAssgnList) {
								if (StringUtils.isNotBlank(oldStockAssgn.getItemCode())) {
									if (oldStockAssgn.getItemCode().equals(newStockAssgn.getItemCode()) && 
											oldStockAssgn.getItemSerial().equals(newStockAssgn.getItemSerial())) {
										isRemoved = false;
									}
								}
								
							}
							if (isRemoved) {
								//Revert Inventory Status ID
								String updateStatus = this.getDsReleaseStockStatus(oldStockAssgn.getItemSerial());
								//String oldStatusId = stockDao.getPreviousStockStatusID(oldStockAssgn.getItemCode(), oldStockAssgn.getItemSerial());
								stockDao.updateStockInventory(oldStockAssgn.getItemCode(), oldStockAssgn.getItemSerial(), updateStatus);
							}
						}
						
						//Get SM number list to update
						boolean needFreeSMnum1 = true;
						boolean needFreeSMnum2 = true;
						if (oldStockAssgn.getSalesMemoNum() == null) {
							needFreeSMnum1 = false;
						}
						if (oldStockAssgn.getSalesMemoNum2() == null) {
							needFreeSMnum2 = false;
						}
						for (StockAssgnDTO newStockAssgn : newStockAssgnList) {
							if (oldStockAssgn.getSalesMemoNum() != null) { 
								if (oldStockAssgn.getSalesMemoNum().equals(newStockAssgn.getSalesMemoNum()) || 
										oldStockAssgn.getSalesMemoNum().equals(newStockAssgn.getSalesMemoNum2())) {
									needFreeSMnum1 = false;
								}
							}
							if (oldStockAssgn.getSalesMemoNum2() != null) { 
								if (oldStockAssgn.getSalesMemoNum2().equals(newStockAssgn.getSalesMemoNum()) || 
										oldStockAssgn.getSalesMemoNum2().equals(newStockAssgn.getSalesMemoNum2())) {
									needFreeSMnum2 = false;
								}
							}
						}
						if (needFreeSMnum1) {
							if (!needFreeSM.contains(oldStockAssgn.getSalesMemoNum())) {
								needFreeSM.add(oldStockAssgn.getSalesMemoNum());
							}
						}
						if (needFreeSMnum2) {
							if (!needFreeSM.contains(oldStockAssgn.getSalesMemoNum2())) {
								needFreeSM.add(oldStockAssgn.getSalesMemoNum2());
							}
						}
					}
					for (String needFreeSMnum : needFreeSM) {
						//String oldStatusId = stockDao.getPreviousStockStatusID(BomWebPortalConstant.SALES_MEMO_ITEM_CODE, needFreeSMnum);
						String updateStatus = this.getDsReleaseStockStatus(needFreeSMnum);
						stockDao.updateStockInventory(BomWebPortalConstant.SALES_MEMO_ITEM_CODE, needFreeSMnum, updateStatus);
					}
				}
				
				stockDao.deleteBomWebStockAssgn(orderId);
				
				List<String> needAddSM = new ArrayList<String>();
				for (StockAssgnDTO newStockAssgn : newStockAssgnList) {
					if (StringUtils.isNotBlank(newStockAssgn.getItemSerial())) {
						for (StockAssgnDTO oldStockAssgn: oldStockAssgnList) {
							if (StringUtils.isNotBlank(oldStockAssgn.getItemCode()) 
									&& oldStockAssgn.getItemCode().equals(newStockAssgn.getItemCode())) {
								if ("Y".equals(oldStockAssgn.getAoInd()) 
										&& !"Y".equals(newStockAssgn.getAoInd())
										&& orderDto.getOcid() != null
										&& orderDto.getOcid().length() > 0) {
									newStockAssgn.setAoReportSent("Y");
								}
							}
						}
						stockDao.insertBomWebStockAssgn(newStockAssgn, orderId, "19");
						
						if ("10".equals(channelId) || "11".equals(channelId)) {
							boolean isNew = true;
							for (StockAssgnDTO oldStockAssgn: oldStockAssgnList) {
								if ((newStockAssgn.getItemCode() != null) && newStockAssgn.getItemSerial() !=null) { 
									if (newStockAssgn.getItemCode().equals(oldStockAssgn.getItemCode()) && 
											newStockAssgn.getItemSerial().equals(oldStockAssgn.getItemSerial())) {
										isNew = false;
									}
								}
								
							}
							if (isNew) {
								stockDao.updateStockInventory(newStockAssgn.getItemCode(), newStockAssgn.getItemSerial(), "19");
							}
						}
					} else {
						stockDao.insertBomWebStockAssgn(newStockAssgn, orderId, "");
					}
					
					if ("10".equals(channelId) || "11".equals(channelId)) {
						boolean smNum1isNew = true;
						boolean smNum2isNew = true;
						if (newStockAssgn.getSalesMemoNum() == null) {
							smNum1isNew = false;
						}
						if (newStockAssgn.getSalesMemoNum2() == null) {
							smNum2isNew = false;
						}
						for (StockAssgnDTO oldStockAssgn: oldStockAssgnList) {
							if (newStockAssgn.getSalesMemoNum() != null) {
								if (newStockAssgn.getSalesMemoNum().equals(oldStockAssgn.getSalesMemoNum()) || 
										newStockAssgn.getSalesMemoNum().equals(oldStockAssgn.getSalesMemoNum2())) {
									smNum1isNew = false;
								}
							}
							if (newStockAssgn.getSalesMemoNum2() != null) {
								if (newStockAssgn.getSalesMemoNum2().equals(oldStockAssgn.getSalesMemoNum()) || 
										newStockAssgn.getSalesMemoNum2().equals(oldStockAssgn.getSalesMemoNum2())) {
									smNum2isNew = false;
								}
							}						
						}
						if (smNum1isNew) {
							if (!needAddSM.contains(newStockAssgn.getSalesMemoNum())) {
								needAddSM.add(newStockAssgn.getSalesMemoNum());
							}
						}
						if (smNum2isNew) {
							if (!needAddSM.contains(newStockAssgn.getSalesMemoNum2())) {
								needAddSM.add(newStockAssgn.getSalesMemoNum2());
							}
						}
						
						for (String needAddSMnum : needAddSM) {
							stockDao.updateStockInventory(BomWebPortalConstant.SALES_MEMO_ITEM_CODE, needAddSMnum, "19");
						}
					}
				}
			}
			
			//Add by whitney 20160826			
			 if ("1".equals(channelId) || "3".equals(channelId) || "10".equals(channelId) || "11".equals(channelId) ){
				 	StaffInfoUI staffInfo = new StaffInfoUI();
					staffInfo.setOrderId(orderId);
					
					staffInfo.setSalesId(mobileSimInfo.getSalesCd());
					staffInfo.setSalesName(mobileSimInfo.getSalesName());
					staffInfo.setSalesTeam(mobileSimInfo.getTeamCd());
					staffInfo.setSalesType(mobileSimInfo.getSalesType());

					
					staffInfo.setRefSalesCentre(mobileSimInfo.getRefSalesCentre());
					staffInfo.setRefSalesTeam(mobileSimInfo.getRefSalesTeam());
					staffInfo.setRefSalesId(mobileSimInfo.getRefSalesId());
					staffInfo.setRefSalesName(mobileSimInfo.getRefSalesName());
					staffInfoService.insertBomwebStaffInfo(staffInfo);
	         } 
			
			orderDao.insertBomWebSim(mobileSimInfo);
			orderDao.insertBomWebPayment(paymentDto);
			orderDao.insertBomWebAccount(accountDto);
			if(componentList!=null&&componentList.size()>0){
				for(ComponentDTO component: componentList){
				orderDao.insertBomWebComponent(orderId, component);
				}
			}
			
			if(multiSimInfos != null){
				for(MultiSimInfoDTO msi: multiSimInfos){
					for(MultiSimInfoMemberDTO msim : msi.getMembers()){
						orderDao.insertBomWebMember(msim);
						if (StringUtils.isNotEmpty(msim.getSameAsCustInd()) && !msim.isSameAsCust()){
							customerProfileDao.insertBomWebMobActualUser(msim.getActualUserDTO());
						}
						//msim.setBundleVasItemList(new ArrayList<String>());
						List<String> basketItems = orderDao.getBasketItemList(msi.getBasket().getBasketId(), customerInfoDto.getBrand(), customerInfoDto.getSimType());
						List<String> allVASList = orderDao.getBundleVASList(msi.getBasket().getBasketId(), customerInfoDto.getBrand(), customerInfoDto.getSimType());
						if(msim.getSelectedVasItemList() != null) {
							allVASList.addAll(msim.getSelectedVasItemList());
			            }
						if(allVASList!=null && allVASList.size()>0){
							List<ExclusiveItemDTO> exclusiveItems = vasDetailDao.getExclusiveItemList(allVASList);
							List<String> exclusiveItemIds = new ArrayList<String>();
							for (ExclusiveItemDTO exclusiveItem : exclusiveItems) {
								if("BVAS".equals(exclusiveItem.getItemTypeA())) {
									exclusiveItemIds.add(exclusiveItem.getItemIdA());
				                } else if("BVAS".equals(exclusiveItem.getItemTypeB())) {
				                	exclusiveItemIds.add(exclusiveItem.getItemIdB());
				                }
							}
							basketItems.removeAll(exclusiveItemIds);
						}
						msim.setBundleVasItemList(basketItems);
					}
					orderDao.insertBomWebMemberVas(msi);
					msi.setOrder(orderDto);

					orderDao.updateBomWebMUP(orderId, orderDto.getLastUpdateBy());
					List<BomWebMupDTO> mupList = this.multiSimInfoDTOToBomwebMupDTO(msi); //,orderDto.getOrderId()
					for (BomWebMupDTO bwm: mupList) {
						bwm.setCreateBy(orderDto.getLastUpdateBy());
						bwm.setLastUpdateBy(orderDto.getLastUpdateBy());
						orderDao.insertBomWebMUP(bwm);
					}
				}
			}
			
			if ("10".equals(channelId) || "11".equals(channelId)) {
				this.updateDsMsisdnStatus(mnpDto.getMsisdn(), 19, orderId, orderDto.getSalesCd(), orderDto.getAppInDate());
			}
			
			//add 20110224
			List<String> bundleVASList = orderDao.getBundleVASList(selectedBasketId, customerInfoDto.getBrand(), customerInfoDto.getSimType());
            if(selectedItemList != null)
            {
            	for(int i = 0; i < selectedItemList.length; i++)
                {
                	bundleVASList.add(selectedItemList[i]);
                }

            }
            List<ExclusiveItemDTO> exclusiveItemList=null;
            if(bundleVASList!=null && bundleVASList.size()>0){
            	exclusiveItemList = vasDetailDao.getExclusiveItemList(bundleVASList);
            }
            
            List<String> exclusiveItemIdList = new ArrayList<String>();
            for(int i = 0; exclusiveItemList!=null && i < exclusiveItemList.size(); i++)
            {
            	ExclusiveItemDTO exclusiveItem = (ExclusiveItemDTO)exclusiveItemList.get(i);
                if("BVAS".equals(exclusiveItem.getItemTypeA()))
                {
                    exclusiveItemIdList.add(exclusiveItem.getItemIdA());
                } else
                if("BVAS".equals(exclusiveItem.getItemTypeB()))
                {
                    exclusiveItemIdList.add(exclusiveItem.getItemIdB());
                }
            }
            
            //String exclusiveItemId;
            String waiveReason = "";
            if ("1".equals(channelId) || "3".equals(channelId) || "10".equals(channelId) || "11".equals(channelId) ){
            	waiveReason = mobileSimInfo.getSimWaiveReason();
            } else {
            	waiveReason = vasDetailDto.getSimWaiveReason();
            }
            
            String rpWaiveReason = "";
            if ("1".equals(channelId) || "3".equals(channelId) || "10".equals(channelId) || "11".equals(channelId) ){
            	rpWaiveReason = mobileSimInfo.getRpWaiveReason();
            } else {
            	rpWaiveReason = vasDetailDto.getRpWaiveReason();
            }
            
            
            orderDao.insertBomWebSubscribedItem(orderId, selectedBasketId, "BASKET", exclusiveItemIdList, waiveReason,rpWaiveReason, null, customerInfoDto.getBrand(), customerInfoDto.getSimType());
            
            
            if (ArrayUtils.isEmpty(selectedItemList)) {
				selectedItemList = new String[] { "-9999" };
            }
			// insert VAS item info insert to database
			for (int i = 0; selectedItemList != null && i < selectedItemList.length; i++) {
				String sysAssignInd = null;
				if (CollectionUtils.isNotEmpty(systemAssignVasDetailList)) {
					for (VasDetailDTO sysAssignVas : systemAssignVasDetailList) {
						if (selectedItemList[i].equals(sysAssignVas.getItemId())) {
							sysAssignInd = "Y";
							break;
						}
					}
				}
				orderDao.insertBomWebSubscribedItem(orderId, selectedItemList[i], "VAS", exclusiveItemIdList, null, null, sysAssignInd, customerInfoDto.getBrand(), customerInfoDto.getSimType());
				//Project Eagle
				if (vasDetailService.existInSelectionGrpList(ProjectEagleReportHelper.ITEM_SELECTTION_GROUP_ID, selectedItemList[i])) {
					orderDao.insertBomWebOrdMobItem(orderId, orderDto.getLastUpdateBy());
				}
			}
			//RS=> mnpDto.getMnp() or mnpDto.isFutherMnp(), 
			if ("1".equals(channelId) || "3".equals(channelId) || "10".equals(channelId) || "11".equals(channelId) ){
				if ("Y".equals(mnpDto.getMnp()) || mnpDto.isFutherMnp())  {
					mnpDto.setMnpIncentive(true);
				}else{
					mnpDto.setMnpIncentive(false);
				}
			}
			// must call after  insertBomWebSubscribedItem() 20110324
			if (mnpDto.isMnpIncentive()) {// add mnpDto=> field MnpIncentive for CCS and RS MNP_INC ITEM
					orderDao.insertBomWebSubscribedItembyItemType(orderId,	selectedBasketId, "MNP_INC", customerInfoDto.getBrand(), customerInfoDto.getSimType());
			}
			if ("C".equals(paymentDto.getPayMethodType())) { //edit 20110602, autopay , remove || "A".equals(paymentDto.getPayMethodType()
				orderDao.insertBomWebSubscribedItembyItemType(orderId,	selectedBasketId, "AP_INC", customerInfoDto.getBrand(), customerInfoDto.getSimType());
			}
			
			if ("1".equals(channelId) || "3".equals(channelId) || "10".equals(channelId) || "11".equals(channelId)){
				//del other sim item 
				logger.info("getItemCd:"+mobileSimInfo.getItemCd());
				logger.info("selectedBasketId:"+selectedBasketId);
				String keepSimItemId =mobileSimInfoDao.getBomWebSimItemId(selectedBasketId, mobileSimInfo.getItemCd());
				orderDao.deleteBomWebNoneSuitableSim(orderId, keepSimItemId);
			}else if ("2".equals(channelId) || "66".equals(channelId)) {  
				if (vasDetailDto.getSelectedSimItemId() != null && vasDetailDto.getSelectedSimItemId().length() > 0) {
					orderDao.deleteBomWebNoneSuitableSim(orderId, vasDetailDto.getSelectedSimItemId());
				}
			} else {
				logger.info("channelId!=1==> not del sim item ");
			}
			
			if (!this.isEmpty(allOrdDocAssgnDTOs)) {
				this.supportDocService.insertAllOrdDocAssgnDTOALL(orderId, allOrdDocAssgnDTOs);
			}
			// insert bomweb_order_mob for iGuardSerial and nfcInd
			OrderMobDTO orderMobDto = new OrderMobDTO();
			orderMobDto.setOrderId(orderId);
			orderMobDto.setShopCd(orderDto.getShopCode());
			if (iGuardDto != null && StringUtils.isNotBlank(iGuardDto.getSerialNo())) {
				orderMobDto.setIguardSn(iGuardDto.getSerialNo());
			}
			orderMobDto.setCreateDate(new Date());
			orderMobDto.setCreateBy(orderDto.getLastUpdateBy().trim());
			orderMobDto.setLastUpdDate(new Date());
			orderMobDto.setLastUpdBy(orderDto.getLastUpdateBy().trim());
			
			Boolean checkIsWhiteList = customerInfoDto.getCheckIsWhiteList();
			String customerTier = customerInfoDto.getCustomerTier();
			
			logger.info("checkIsWhiteList::");
			logger.info(checkIsWhiteList);
			logger.info("customerTier::");
			logger.info(customerTier);
			
			if (checkIsWhiteList != null && checkIsWhiteList == true && "62".equals(customerTier)){
				orderMobDto.setCsubInd("Y");
			}
			
			//NfcInd nfcInd = NfcInd.N;
			
			MobSponsorshipDTO mobSponsorshipDTO = orderDto.getMobSponsorshipDTO();
			if (mobSponsorshipDTO != null) {
				orderMobDto.setStaffId(mobSponsorshipDTO.getStaffId());
				orderMobDto.setCcc(mobSponsorshipDTO.getCcc());
				orderMobDto.setSponsorLevel(mobSponsorshipDTO.getSponsorLevel());
			}
			orderMobDto.setCareStatus(customerInfoDto.getCareStatus());
			orderMobDto.setCareOptInd(customerInfoDto.getCareOptInd());
			orderMobDto.setCareDmSupInd(customerInfoDto.getCareDmSupInd());
						
		
			orderMobDto.setNfcInteger(orderDto.getNfcInd()); //201309
			orderMobDto.setManualAfNo(orderDto.getManualAfNo());
			orderMobDto.setLocationCd(mobileSimInfo.getSalesLocation());
			orderMobDto.setHkbnInd(customerInfoDto.getHkbnInd());
			orderMobDto.setStockAssgnDate(orderDto.getStockAssgnDate());
			// MBU2019003 -- Add Campaign_ID   
			orderMobDto.setCampaignId(orderDto.getCampaignId());
			// MBU2019003 -- Add Campaign_ID
			
			this.orderMobDao.insertBomwebOrderMob(orderMobDto);
			
			if ("1".equals(channelId) || "3".equals(channelId) || "10".equals(channelId) || "11".equals(channelId)) {
				// only retail need to update tier
				this.orderDao.orderTierAttbProcess(orderId);
			}

			
			
			
			/*************  Save Deposit Info *************/
			if (orderDto.getDepositDTOs() != null) {
				for (DepositDTO depositDTO: orderDto.getDepositDTOs()) {
					depositDTO.setOrderId(orderId);
					depositDTO.setCreateBy(orderDto.getSalesCd());
					depositDTO.setLastUpdBy(orderDto.getLastUpdateBy());
					depositDao.insertBomwebDeposit(depositDTO);
				}
			}

			/*************  DS Order Amend *************/
			if ("10".equals(channelId) || "11".equals(channelId)) {
				this.updateDsStockMrtAfterSuccess(orderId);
			}
			
			/******************** Quota Plan info *****************/
			for (MobBuoQuotaDTO mobBuoQuotaDto : mobBuoQuotaDtoList) {

				quotaPlanInfoDAO.insertBomWebBuoQuota(mobBuoQuotaDto, orderId);
			}
			/******************** Quota Plan End *****************/
			
			if (CollectionUtils.isNotEmpty(authorizeLog)) {
				for (String authorizeId : authorizeLog) {
					authorizeDAO.updateApprovalLogOrderId(authorizeId, orderId);
				}
			}
			
		} catch (DAOException de) {
			logger.error("Exception caught in insertBomWebAll()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	private boolean basketAutoAssignNfc(BasketDTO basketDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("basketTypeId: " + basketDTO.getBasketTypeId());
		}
		/*
		 * 1		SIM + HANDSET
		 * 2		SIM ONLY
		 * 3		SIM + SMARTPHONE REBATE
		 * 4		SIM + TABLET
		 */
		return "2".equals(basketDTO.getBasketTypeId()) || "3".equals(basketDTO.getBasketTypeId());
	}
	
	private String getSimHsExtraFunction(String itemCd) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("itemCd: " + itemCd);
		}
		String simType = this.orderDao.getSimType(itemCd);
		if (logger.isDebugEnabled()) {
			logger.debug("simType: " + simType);
		}
		return simType;
	}
	
	//modify by Eliot 20110829
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateBulkReqStatusSuccess(String refNum, String ocid,
			String status, String errCode, String lastUpdateBy) {
		try {

			logger.debug("updateBulkReqStatusSuccess() is called in OrderServiceImpl");
			orderDao.updateBulkReqStatusSuccess(refNum, ocid, status, errCode, lastUpdateBy);
		} catch (DAOException de) {
			logger.error("Exception caught in updateBulkReqStatusSuccess()", de);
			throw new AppRuntimeException(de);
		}
	}

	//modify By Eliot 20110829
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateOrderStatus(String refNum, String status, String errCode,
			String errString, String lastUpdateBy) {
		try {

			logger.debug("updateOrderStatus() is called in OrderServiceImpl");
			orderDao.updateOrderStatus(refNum, status, errCode, errString, lastUpdateBy);
		} catch (DAOException de) {
			logger.error("Exception caught in updateOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateBomWebOrderStatus(String orderId, String status) {
		try {
			logger.debug("updateBomWebOrderStatus() is called in OrderServiceImpl");
			orderDao.updateBomWebOrderStatus(orderId, status);
		} catch (DAOException de) {
			logger.error("Exception caught in updateBomWebOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateBomWebOrderStatus(String orderId, String status, String supervisor_approve, String mobsupport_approve) {
		try {
			logger.debug("updateBomWebOrderStatus() is called in OrderServiceImpl");
			orderDao.updateBomWebOrderStatusDS(orderId, status, supervisor_approve, mobsupport_approve);
		} catch (DAOException de) {
			logger.error("Exception caught in updateBomWebOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateBomWebOrderStatus(String orderId, String status, String supervisor_approve, String mobsupport_approve, String checkPoint) {
		try {
			logger.debug("updateBomWebOrderStatus() is called in OrderServiceImpl");
			orderDao.updateBomWebOrderStatusDS(orderId, status, supervisor_approve, mobsupport_approve, checkPoint);
		} catch (DAOException de) {
			logger.error("Exception caught in updateBomWebOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void deleteBomWebAllOrder(String orderId, String channelId, String oldMsisdn, String staffId, Date appDate) {
		try {
			logger.debug("deleteBomWebAllOrder() is called in OrderServiceImpl");
			if ("10".equals(channelId) || "11".equals(channelId)) {
				this.updateDsMsisdnStatus(oldMsisdn, 2, orderId, staffId, appDate);
			}
			//orderDao.deleteDsBomWebStockAssgn(orderId);
			orderMobDao.deleteBomwebOrderMob(orderId);
			depositDao.deleteBomwebDeposit(orderId);
			orderDao.deleteBomWebAllOrder(orderId);
			quotaPlanInfoDAO.deleteBomWebBuoQuota(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in deleteBomWebAllOrder()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<ProductDTO> getBomProductList(String orderId) {
		List<ProductDTO> productDtoList;
		try {

			logger.debug("getBomProductList() is called in OrderServiceImpl");
			productDtoList = orderDao.getBomProductList(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getBomProductList()", de);
			throw new AppRuntimeException(de);
		}
		return productDtoList;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public long getHandsetDeviceAmount(String orderId) {
		try {
			logger.debug("getHandsetDeviceAmount() is called in OrderServiceImpl");
			return orderDao.getHandsetDeviceAmount(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getHandsetDeviceAmount()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public long getFirstMonthFee(String orderId) {
		try {
			logger.debug("getFirstMonthFee() is called in OrderServiceImpl");
			return orderDao.getFirstMonthFee(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getFirstMonthFee()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public long getPrepaymentWithoutHandset(String orderId) {
		try {
			logger.debug("getPrepaymentWithoutHandset() is called in OrderServiceImpl");
			return orderDao.getPrepaymentWithoutHandset(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getPrepaymentWithoutHandset()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<VasOnetimeAmtDTO> getVasOnetimeAmtList(String locale, String orderId) {

		List<VasOnetimeAmtDTO> vasOnetimeAmtList = new ArrayList<VasOnetimeAmtDTO>();
		try {
			logger.debug("getVasOnetimeAmtList() is called in  service");
			vasOnetimeAmtList = orderDao.getVasOnetimeAmtList(locale, orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getVasOnetimeAmtList()", de);
			throw new AppRuntimeException(de);
		}
		return vasOnetimeAmtList;

	}
	
	public List<VasOnetimeAmtDTO> getRetChgSimAdmChargeList(String locale, String orderId) {

		List<VasOnetimeAmtDTO> retChgSimAdmChargeList = new ArrayList<VasOnetimeAmtDTO>();
		try {
			logger.debug("getRetChgSimAdmChargeList() is called in  service");
			retChgSimAdmChargeList = orderDao.getRetChgSimAdmChargeList(locale, orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getRetChgSimAdmChargeList()", de);
			throw new AppRuntimeException(de);
		}
		return retChgSimAdmChargeList;

	}

	public List<VasOnetimeAmtDTO> getRetToo1AdmChargeList(String locale, String orderId) {

		List<VasOnetimeAmtDTO> retToo1AdmChargeList = new ArrayList<VasOnetimeAmtDTO>();
		try {
			logger.debug("getRetToo1AdmChargeList() is called in service");
			retToo1AdmChargeList = orderDao.getRetToo1AdmChargeList(locale, orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getRetToo1AdmChargeList()", de);
			throw new AppRuntimeException(de);
		}
		return retToo1AdmChargeList;

	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<VasDetailDTO> getRebateList(String locale, String orderId) {

		List<VasDetailDTO> modelList = new ArrayList<VasDetailDTO>();
		try {
			logger.debug("getRebateList() is called in  service");
			modelList = orderDao.getRebateList(locale, orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getRebateList()", de);
			throw new AppRuntimeException(de);
		}
		return modelList;

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String[] getPenaltyInfoList(String orderId) {
		try {
			logger.debug("getPenaltyInfoList() is called in  service");
			return orderDao.getPenaltyInfoList(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getPenaltyInfoList()", de);
			throw new AppRuntimeException(de);
		}

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String[] getHandsetDeviceDescription(String locale, String brandId,
			String modelId, String colorId) {
		try {
			logger.debug("getHandsetDeviceDescription() is called in  service");
			return orderDao.getHandsetDeviceDescription(locale, brandId,
					modelId, colorId);
		} catch (DAOException de) {
			logger.error("Exception caught in getHandsetDeviceDescription()",
					de);
			throw new AppRuntimeException(de);
		}

	}

	/*@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String getShopSeq(String shopCd) {
		String orderId = new String();
		try {
			orderId = orderDao.getShopSeq(shopCd);
			orderDao.updateShopSeq(shopCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getShopSeq()", de);
			throw new AppRuntimeException(de);
		}
		return orderId;
	}*/

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String getSecSrvContractPeriod(String orderId) {

		try {
			return orderDao.getSecSrvContractPeriod(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getSecSrvContractPeriod()", de);
			throw new AppRuntimeException(de);
		}

	}

	public List<OrderDTO> getOrderSummaryList(String shopCode, String dateStr, String orderIdStr) {
		try {
			return orderDao.getOrderSummaryList(shopCode, dateStr, orderIdStr);

		} catch (DAOException de) {
			logger.error("Exception caught in getOrderSummaryList()", de);
			throw new AppRuntimeException(de);
		}

	}

	public MnpDTO getMnp(String orderId) {
		MnpDTO baseMnpDto= new MnpDTO();
		MnpDTO futherMnpDto= new MnpDTO();
		
		try {
			
			baseMnpDto= orderDao.getMnp(orderId);
			if(baseMnpDto != null) {
				if(("10".equals(baseMnpDto.getChannelId()) || "11".equals(baseMnpDto.getChannelId())) 
					&& "New Number".equals(baseMnpDto.getMnpType())) {
					baseMnpDto.setReserveId(orderDao.getDsReserveId(orderId));
				}
				
				if (("1".equals(baseMnpDto.getChannelId()) ) 
					&& "New Number".equals(baseMnpDto.getMnpType())) {
					baseMnpDto.setOriginalNewMsisdn(baseMnpDto.getMsisdn());
				}
				
			}
			futherMnpDto=orderDao.getFutherMnp(orderId);
			if (futherMnpDto!=null){
				baseMnpDto.setFutherMnp(true);
				baseMnpDto.setFutherMnpNumber(futherMnpDto.getMsisdn());
				baseMnpDto.setFutherCutoverDate(futherMnpDto.getCutoverDate());
				baseMnpDto.setFutherCutoverDateStr(futherMnpDto.getCutoverDateStr());
				baseMnpDto.setFutherCutoverTime(futherMnpDto.getCutoverTime());
				baseMnpDto.setFuthercustName(futherMnpDto.getCustName());
				baseMnpDto.setFuthercustNameChi(futherMnpDto.getCustNameChi());
				baseMnpDto.setFuthercustIdDocNum(futherMnpDto.getCustIdDocNum());
				baseMnpDto.setPrePaidSimDocInd(futherMnpDto.getPrePaidSimDocInd());
				baseMnpDto.setFutherMnpTicketNum(futherMnpDto.getMnpTicketNum());				
			}

			return baseMnpDto;

		} catch (DAOException de) {
			logger.error("Exception caught in getMnp()", de);
			throw new AppRuntimeException(de);
		}

	}

	public PaymentDTO getPayment(String orderId) {
		try {
			return orderDao.getPayment(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getPayment()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public MobileSimInfoDTO getSim (String orderId ){
		try {
			MobileSimInfoDTO mobileSimInfo = orderDao.getSim(orderId);
			if (mobileSimInfo != null) {
				String[] shop = stockDao.getShopFromOrder(orderId);
				mobileSimInfo.setChannelCd(shop[0]);
				mobileSimInfo.setCenterCd(shop[1]);
				mobileSimInfo.setTeamCd(shop[2]);
			}
			return mobileSimInfo;

		} catch (DAOException de) {
			logger.error("Exception caught in getSim()", de);
			throw new AppRuntimeException(de);
		}

	}
	public MobileSimInfoDTO getTooMobileSimInfo (String orderId ){
		try {
			MobileSimInfoDTO mobileSimInfo = orderDao.getTooMobileSimInfo(orderId);
			if (mobileSimInfo != null) {
				String[] shop = stockDao.getShopFromOrder(orderId);
				mobileSimInfo.setChannelCd(shop[0]);
				mobileSimInfo.setCenterCd(shop[1]);
				mobileSimInfo.setTeamCd(shop[2]);
			}
			return mobileSimInfo;
 
		} catch (DAOException de) {
			logger.error("Exception caught in getSim()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	//0:basket_id, 1:brand_id, 2:model_id
	public String[] getBasketBrandModelInfoList (String orderId)
	{
		try {
			return orderDao.getBasketBrandModelInfoList(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getSim()", de);
			throw new AppRuntimeException(de);
		}

	}

	public String getSignOffDate (String orderId){
		try {
			return orderDao.getSignOffDate(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getSignOffDate()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	//0:basket_id, 1:brand_id, 2:model_id
		public String[] getTOOBRMBasketBrandModelInfoList (String orderId)
		{
			try {
				return orderDao.getTOOBRMBasketBrandModelInfoList(orderId);

			} catch (DAOException de) {
				logger.error("Exception caught in getSim()", de);
				throw new AppRuntimeException(de);
			}

		}
	
	public OrderDTO getOrder (String orderId )
	{
		try {
			OrderDTO orderDto = orderDao.getOrder(orderId);
			if (orderDto != null) {
				orderDto.setMultiSim(orderDao.isMultiSim(orderId));
			}
			return orderDto;
		} catch (DAOException de) {
			logger.error("Exception caught in getOrder()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public AccountDTO getAccount (String orderId )
	{
		try {
			return orderDao.getAccount(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getAccount()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public List<String> getSelectedItemList(String orderId)
	{
		try {
			return orderDao.getSelectedItemList(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getSelectedItemList()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<ComponentDTO> getComponentList(String orderId)
	{
		try {
			return orderDao.getComponentList(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getComponentList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public MobDsPaymentUpfrontDTO getPaymentUpfront (String orderId) {
		MobDsPaymentUpfrontDTO paymentUpfront = new MobDsPaymentUpfrontDTO();
		try {
			paymentUpfront.setPaymentTransList(orderDao.getPaymentTransList(orderId));
		} catch (Exception e) {
			logger.error("Exception caught in getComponentList()", e);
			e.printStackTrace();
		}
		return paymentUpfront;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void deleteBomWebNoneSuitableSim(String orderId, String simKeepItemId) {
		try {
			logger.debug("deleteBomWebOtherSim() is called in OrderServiceImpl");
			orderDao.deleteBomWebNoneSuitableSim(orderId,simKeepItemId);
		} catch (DAOException de) {
			logger.error("Exception caught in deleteBomWebOtherSim()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<String> getSignoffOrderList(){
		try{
			logger.debug("getSignoffOrderList() is called in OrderServiceImpl");
			return orderDao.getSignoffOrderList();
		} catch (DAOException de) {
			logger.error("Exception caught in getSignoffOrderList()", de);
			throw new AppRuntimeException(de);
	
		}
	}
	
	
	public SubscriberDTO getBomWebSub(String orderId) {
		try {
			return orderDao.getBomWebSub(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getBomWebSub()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	//add 20110620
	public String getConciergeInd(String orderId){
		try {
			return orderDao.getConciergeInd(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getConciergeInd()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public long getFirstMonthServiceLicenceFee(String orderId) {
		try {
			logger.debug("getFirstMonthServiceLicenceFee() is called in OrderServiceImpl");
			return orderDao.getFirstMonthServiceLicenceFee(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getFirstMonthServiceLicenceFee()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	//add eliot 20110822
	public String getBasketType(String orderId){
		try {
			return orderDao.getBasketType(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketType()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	//add Eliot 20110831
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertPDF_To_DB(String orderId, byte[] pdfData){
		try {
			logger.debug("insertPDF_To_DB() is called in OrderServiceImpl");
			orderDao.insertPDF_To_DB(orderId, pdfData);
		} catch (DAOException de) {
			logger.error("Exception caught in insertPDF_To_DB()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String getOrderStatus(String orderId) {
		try {
			return orderDao.getOrderStatus(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}	
	//add 2011-12-19 new order id format, eg.eg.RTP1M0000001
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String getShopSeq(String shopCd, String channelId) {
		String orderId = new String();
		try {
			orderId = orderDao.getShopSeq(shopCd, channelId);
			orderDao.updateShopSeq(shopCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getShopSeq()", de);
			throw new AppRuntimeException(de);
		}
		return orderId;
	}
	
	public String findBasketId(String orderId) {
		String basketId = null;		
		try {
			basketId = orderDao.findBasketId(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return basketId;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String orderStatusProcess(String orderId) {
		
		try {
			return mobCcsOrderDao.orderStatusProcess(orderId);
			
		} catch (DAOException de) {
			logger.error("Exception caught in orderStatusProcess()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertBomwebCustQuotaInUse(String idDocType,String  idDocNum,String  orderId,String userId,String basketId){
		try {

			logger.debug("insertBomwebCustQuotaInUse() is called in OrderServiceImpl");
			return orderDao.insertBomwebCustQuotaInUse(idDocType, idDocNum, orderId, userId, basketId);
		} catch (DAOException de) {
			logger.error("Exception caught in insertBomwebCustQuotaInUse()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String orderMrtUpdate(String orderId){
		try {
			return mobCcsOrderDao.orderMrtUpdate(orderId);
			
		} catch (DAOException de) {
			logger.error("Exception caught in orderMrtUpdate()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String getMobCcsOrderStatusDesc(String orderId){
		try {
			return mobCcsOrderDao.getMobCcsOrderStatusDesc(orderId);
			
		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsOrderStatusDesc()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	/*@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public long getOrderPaidAmount(String orderId) {
		try {
			logger.info("getOrderPaidAmount() is called in OrderServiceImpl");
			return orderDao.getOrderPaidAmount( orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOrderPaidAmount()", de);
			throw new AppRuntimeException(de);
		}
	}*/
	
	public OrderDTO getOrderWithPaidAmount (String orderId )
	{
		OrderDTO dto= new OrderDTO();
		try {
			dto=getOrder(orderId);//get basic info
			dto.setPaidAmt(orderDao.getOrderPaidAmount( orderId));//get PaidInfo
			return dto;

		} catch (DAOException de) {
			logger.error("Exception caught in getOrderWithPaidAmount()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public List<StockDTO> getStockAssignment(String orderId) {
		
		try {
			return orderDao.getStockAssignment(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockAssignment()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	public List<StockDTO> getParentOrderStockAssignment(String orderId) {
		
		try {
			return orderDao.getParentOrderStockAssignment(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockAssignment()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	//add by Eliot 20120509
	public List<StockDTO> getDOAStockAssignment(String orderId) {
		
		try {
			return orderDao.getDOAStockAssignment(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getDOAStockAssignment()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	

	
	//add By herbert 20120220
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateBomWebSim(MobileSimInfoDTO dto) {
		try {

			logger.debug("updateBomWebSim() is called in OrderServiceImpl");
			orderDao.updateBomWebSim(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateBomWebSim()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public VasDetailDTO getVasDetailDTO(String orderId, Date appDate){
		VasDetailDTO vasDetail = new VasDetailDTO();
		try {
			vasDetail.setSelectedSimItemId(orderDao.getOrderSimItemId(orderId));
			List<String[]> simPrice = mobileSimInfoDao.getSimPrice(vasDetail.getSelectedSimItemId(), appDate);
			if (simPrice != null && simPrice.size() > 0) {
				vasDetail.setSimWaivable("Y".equals(simPrice.get(0)[0]));
				try {
					vasDetail.setSimCharge(Double.parseDouble(simPrice.get(0)[1]));
				} catch (Exception e) {
					vasDetail.setSimCharge(0);
					vasDetail.setSimWaivable(false);
				}
			}
			vasDetail.setSimWaiveReason(mobileSimInfoDao.getSimWaiveReason(orderId, vasDetail.getSelectedSimItemId()));
			vasDetail.setRpWaiveReason(orderDao.getOrderRpWaiveReason(orderId));
			vasDetail.setRpCharge(orderDao.getOrderRpCharge(orderId));
			return vasDetail;
		} catch (DAOException de) {
			logger.error("Exception caught in getVasDetailDTO()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	//add By herbert 20120229
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateCcsOrderStatus(String refNum, String status, String checkPoint, String reasonCd,
			String errCode, String errString, String lastUpdateBy) {
		try {

			logger.debug("updateCcsOrderStatus() is called in OrderServiceImpl");
			mobCcsOrderDao.updateCcsOrderStatus(refNum, status, checkPoint, reasonCd,
					errCode, errString, lastUpdateBy);
			
			//mobCcsOrderDao.updateCcsOrderCreateStatus(refNum,status,checkPoint,reasonCd);
		} catch (DAOException de) {
			logger.error("Exception caught in updateCcsOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	//add By herbert 20120229
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateCcsCreateOrderStatus(String refNum, String status, String checkPoint, String reasonCd,
			String errCode, String errString, String lastUpdateBy) {
		try {

			logger.debug("updateCcsOrderStatus() is called in OrderServiceImpl");
			mobCcsOrderDao.updateCcsOrderStatus(refNum, status, checkPoint, reasonCd,
					errCode, errString, lastUpdateBy);
			
			mobCcsOrderDao.updateCcsOrderCreateStatus(refNum,status,checkPoint,reasonCd);
		} catch (DAOException de) {
			logger.error("Exception caught in updateCcsOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	//add By herbert 20120229
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateCcsBulkReqStatusSuccess(String refNum, String ocid, String status, String checkPoint,
			String reasonCd, String errCode, String lastUpdateBy) {
		try {

			logger.debug("updateCcsBulkReqStatusSuccess() is called in OrderServiceImpl");
			mobCcsOrderDao.updateCcsBulkReqStatusSuccess(refNum, ocid, status, checkPoint, reasonCd,
					errCode, lastUpdateBy);
			mobCcsOrderDao.updateCcsOrderCreateStatus(refNum,status,checkPoint,reasonCd);
		} catch (DAOException de) {
			logger.error("Exception caught in updateCcsBulkReqStatusSuccess()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	//add By Eliot 20120305
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updatePendingOrderCheckPoint(String orderId, String fromCheckPoint, String toCheckPoint) {
	    try {
		logger.debug("updatePendingOrderCheckPoint() is called in OrderServiceImpl");		
		mobCcsOrderDao.updatePendingOrderCheckPoint(orderId, fromCheckPoint, toCheckPoint);
	    } catch (DAOException de) {
		logger.error("Exception caught in updatePendingOrderCheckPoint()", de);
		throw new AppRuntimeException(de);
	    }
	}
	
	//add by Herbert 20120314
	public String getMultiIMEI(String orderId) {
		try {
			return mobCcsOrderDao.getMultiIMEI(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getMultiIMEI()", de);
			throw new AppRuntimeException(de);
		}	
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updatePayment(PaymentDTO dto) {
		
		try {
			logger.debug("updateUrgentOrderManagement() is called in OrderServiceImpl");
			orderDao.updatePayment(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateUrgentOrderManagement()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)	
	public void updateOrderFallOut(OrderDTO dto) {
		try {
			orderDao.updateOrderFallOut(dto);
			orderDao.removeUrgentInd(dto);
			
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
	}

	public String manualOrderStatusProcess(String orderId) {
		try {

			Map<String,String> orderTypeInfo = getOrderType(orderId);
			String orderType = orderTypeInfo.get("order_type");
			if ("COS".equals(orderType) || "BRM".equals(orderType) || "TOO1".equals(orderType)){
				return orderDao.manualCosOrderStatusProcess(orderId);
			}else{ //if bomweb_order.order_type is null,orderType = 'ACQ'
				return orderDao.manualOrderStatusProcess(orderId);
			}

		} catch (DAOException e) {	
			e.printStackTrace();
		}
		return null;
	}

	public String orderSubmitProcess(String orderId) {
		try {
			

			Map<String,String> orderTypeInfo = getOrderType(orderId);
			String orderType = orderTypeInfo.get("order_type");
			if ("COS".equals(orderType) || "BRM".equals(orderType) || "TOO1".equals(orderType)){	
				return orderDao.cosOrderSubmitProcess(orderId);
			}else{ //if bomweb_order.order_type is null,orderType = 'ACQ'
				return orderDao.orderSubmitProcess(orderId);
			}
					
		} catch (DAOException e) {	
			e.printStackTrace();
		}
		return null;
	}
	
	// MIP.P4 modification
	public List<VasDetailDTO> getSelectedVasList(String locale,	String orderId, String channelId, String basketId, String appDate, String channelCd, String offerNature){
		try {
		// MIP.P4 modification
			return orderDao.getSelectedVasList( locale,	 orderId,  channelId,  basketId,  appDate, channelCd, offerNature);
		} catch (DAOException de) {
			logger.error("Exception caught in getSelectedVasList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertOrderFallout(String orderId, String userName,
			String failType, String reasonCd, String remark) {
		try {
			orderDao.insertOrderFallout(orderId, userName, failType, reasonCd, remark);
		} catch (DAOException de) {
			logger.error("Exception caught in insertOrderFallout()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void synBomOrderStatus(String orderId, String orderStatus, String lastUpdateBy) {
		try {
			orderDao.synBomOrderStatus(orderId, orderStatus, lastUpdateBy);
		} catch (DAOException e) {
			logger.error("Exception caught in synBomOrderStatus()", e);
			throw new AppRuntimeException(e);
		}
	}

	public List<MobCcsFalloutUI> getOrderFalloutHist(String orderId) {
		try {
			return orderDao.getOrderFalloutHist(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getOrderFalloutHist()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateOrderSrvReqDate(String orderId, Date srvReqDate, String username) {
		try {
			orderDao.updateOrderSrvReqDate(orderId, srvReqDate, username);
		} catch (DAOException e) {
			logger.error("Exception caught in getOrderFalloutHist()", e);
			throw new AppRuntimeException(e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String cancelOrderReleaseGoods(String orderId) {
		String errorText="";
		try {
			errorText =mobCcsOrderDao.cancelOrderMrtProcess(orderId);
			errorText +=mobCcsOrderDao.cancelOrderStockProcess(orderId);
			
			return errorText;
		} catch (DAOException e) {
			logger.error("Exception caught in cancelOrderReleaseGoods()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String assignSimForCancel(String orderId) {
		String errorText="";
		try {
			errorText +=mobCcsOrderDao.assignSimForCancel(orderId);
			
			return errorText;
		} catch (DAOException e) {
			logger.error("Exception caught in assignSimForCancel()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String cancelCourierQuotaProcess(String orderId,String username) {
		String errorText="";
		try {
			errorText =mobCcsOrderDao.cancelCourierQuotaProcess(orderId,username);
			
			return errorText;
		} catch (DAOException e) {
			logger.error("Exception caught in cancelCourierQuotaProcess()", e);
			throw new AppRuntimeException(e);
		}
	}

	public void updateOrderReasonCode(String orderId, String reasonCode,
			String username) {
		
		try {
			orderDao.updateOrderReasonCode(orderId, reasonCode, username);
		} catch (DAOException e) {
			logger.error("Exception caught in updateOrderReasonCode()", e);
			throw new AppRuntimeException(e);
		}
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void backupDeleteBomWebAll(String orderId) {

		try {
			logger.debug("backupDeleleBomWebAll() is called in OrderServiceImpl");
			mobCcsOrderDao.orderHistoryProcess(orderId);//backup order 
			customerProfileDao.deleteBomOrderContact(orderId);//delete cust info
			customerProfileDao.deleteBomWebBillMedia(orderId); //Paper bill Athena 20130925
			orderMobDao.deleteBomwebOrderMob(orderId);
			depositDao.deleteBomwebDeposit(orderId);		
			orderDao.deleteBomWebAllOrder(orderId);////delete order info
			quotaPlanInfoDAO.deleteBomWebBuoQuota(orderId); //delete quota plan info
		} catch (DAOException de) {
			logger.error("Exception caught in backupDeleleBomWebAll()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void backupOrder(String orderId) {

		try {
		    logger.debug("backupOrder() is called in OrderServiceImpl");
			mobCcsOrderDao.orderHistoryProcess(orderId);//backup order 
			stockDao.backupBomWebStockAssgn(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in backupOrder()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateCcsOrderStatusToCancel(String orderId, String status, String checkPoint, String reasonCd
			, String lastUpdateBy, String histSeqNo) {
		try {

			logger.debug("updateCcsOrderStatusToCancel() is called in OrderServiceImpl");
			mobCcsOrderDao.updateCcsOrderStatusToCancel(orderId, status, checkPoint, reasonCd, lastUpdateBy, histSeqNo);

		} catch (DAOException de) {
			logger.error("Exception caught in updateCcsOrderStatusToCancel()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void cancelOrderProcess(String orderId, String cancelStatus, String lastUpdBy) {
		try {
		    logger.debug("cancelOrderProcess() is called in OrderServiceImpl");
			mobCcsOrderDao.orderHistoryProcess(orderId);//backup order 
			mobCcsOrderDao.updateCcsOrderStatus(orderId, cancelStatus, null, null, null, null, lastUpdBy);//CANCELLING("03")
			mobCcsOrderDao.cancelOrderMrtProcess(orderId);
			mobCcsOrderDao.cancelOrderStockProcess(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in cancelOrderProcess()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateCcsBulkReqStatusSuccessHist(String refNum, String ocid, String status, String checkPoint,
			String reasonCd, String errCode, String lastUpdateBy) {
		try {

			logger.debug("updateCcsBulkReqStatusSuccessHist() is called in OrderServiceImpl");
			mobCcsOrderDao.updateCcsBulkReqStatusSuccess(refNum, ocid, status, checkPoint, reasonCd,
					errCode, lastUpdateBy);
			//mobCcsOrderDao.updateCcsOrderCreateStatus(refNum,status,checkPoint,reasonCd);
		} catch (DAOException de) {
			logger.error("Exception caught in updateCcsBulkReqStatusSuccessHist()", de);
			throw new AppRuntimeException(de);
		}
	}

	public String getOrderId(String ocid) {
		try {
			return orderDao.getOrderId(ocid);
		} catch (DAOException e) {
			logger.error("Exception caught in getOrderId()", e);
			throw new AppRuntimeException(e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void paymentTransProcess(String oldOrderId, String newOrderId,
			String lastUpdBy) {

		try {
			logger.debug("paymentTrnsProcess() is called in OrderServiceImpl");
			mobCcsOrderDao.copyBomwebPaymentTrans(oldOrderId, newOrderId, lastUpdBy);
			mobCcsOrderDao.updateBomwebPaymentTransStatus(oldOrderId, lastUpdBy);
		} catch (DAOException de) {
			logger.error("Exception caught in paymentTrnsProcess()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String cancelOrderReleaseStock(String orderId) {
		String errorText="";
		try {
			
			errorText =mobCcsOrderDao.cancelOrderStockProcess(orderId);
			
			return errorText;
		} catch (DAOException e) {
			logger.error("Exception caught in cancelOrderReleaseStock()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String getOrderHistSeqNo(String orderId) {
		String errorText="";
		try {
			
			errorText =mobCcsOrderDao.getOrderHistSeqNo(orderId);
			
			return errorText;
		} catch (DAOException e) {
			logger.error("Exception caught in getOrderHistSeqNo()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void copyBomwebStockAssign(String oldOrderId, String newOrderId,
			String lastUpdBy) {
		try {
			logger.debug("copyBomwebStockAssign() is called in OrderServiceImpl");
			mobCcsOrderDao.copyBomwebStockAssign(oldOrderId, newOrderId,
					lastUpdBy);

		} catch (DAOException de) {
			logger.error("Exception caught in copyBomwebStockAssign()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String cancelRecreateOrderStockInsStockAssgnByCloneOrder(String oldOrderId, String newOrderId, String lastUpdBy) {
		String errorText="";
		try {
			errorText = mobCcsOrderDao.insStockAssgnByCloneOrder(oldOrderId, newOrderId, lastUpdBy);
			return errorText;
		} catch (DAOException e) {
			logger.error("Exception caught in cancelRecreateOrderStockInsStockAssgnByCloneOrder()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String cancelRecreateOrderStockMrtTransfer(String oldOrderId, String newOrderId) {
		String errorText="";
		try {
			errorText =mobCcsOrderDao.mrtTransfer(oldOrderId, newOrderId);
			errorText +=mobCcsOrderDao.cancelOrderStockProcess(oldOrderId);
			
			return errorText;
		} catch (DAOException e) {
			logger.error("Exception caught in cancelRecreateOrderStockMrtTransfer()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<ComponentDTO> getPassToBomComponentList(String orderId)
	{
		try {
			return orderDao.getPassToBomComponentList(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getPassToBomComponentList()", de);
			throw new AppRuntimeException(de);
		}
	}
	public List<OrderDTO>  getOrderList(String shopCode, String lob, String orderStatus, String startDate, String endDate, String saleCd, String orderIdStr, String serviceNum, String dms, String bomStartDate, String bomEndDate){
		return this.getOrderList(shopCode, lob, orderStatus, startDate, endDate, saleCd, orderIdStr, serviceNum, dms, bomStartDate, bomEndDate,null);
	}
	public List<OrderDTO>  getOrderList(String shopCode, String lob, String orderStatus, String startDate, String endDate, String saleCd, String orderIdStr,
			String serviceNum, String dms, String bomStartDate, String bomEndDate, String orderType) {
		try {
			return orderSearchDao.getOrderList(shopCode, lob, orderStatus, startDate, endDate, saleCd, orderIdStr, serviceNum, dms, bomStartDate, bomEndDate,orderType);

		} catch (DAOException de) {
			logger.error("Exception caught in getOrderList()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public String getAllowSearchAllInd(String loginStaffId) {
		try {
			return orderSearchDao.getAllowSearchAllInd(loginStaffId);

		} catch (DAOException de) {
			logger.error("Exception caught in getAllowSearchAllInd()", de);
			throw new AppRuntimeException(de);
		}

	}

	public OrderSearchDAO getOrderSearchDao() {
		return orderSearchDao;
	}

	public void setOrderSearchDao(OrderSearchDAO orderSearchDao) {
		this.orderSearchDao = orderSearchDao;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertEmailReq(String orderId, String templateId, String pdfFileName, String createBy) {
		try {
			orderDao.insertEmailReq(orderId, templateId, pdfFileName, createBy);
		} catch (DAOException de) {
			logger.error("Exception caught in insertEmailReq()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateEsignEmailAddr(String orderId, String esigEmailAddr, String lastUpdateBy) {
		try {
			return orderDao.updateEsigEmailAddr(orderId, esigEmailAddr, lastUpdateBy);
		} catch (DAOException de) {
			logger.error("Exception caught in updateEsignEmailAddr()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateSMSNo(String orderId, String SMSno, String lastUpdateBy) {
		try {
			return orderDao.updateSMSNo(orderId, SMSno, lastUpdateBy);
		} catch (DAOException de) {
			logger.error("Exception caught in updateSMSNo()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateDmsInd(String orderId, String dmsInd, String lastUpdateBy) {
		try {
			return orderDao.updateDmsInd(orderId, dmsInd, lastUpdateBy);
		} catch (DAOException de) {
			logger.error("Exception caught in updateDmsInd()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getOcidByOrderID(String orderId) {
		try {
			return orderDao.getOcidByOrderID(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOcidByOrderID()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String getShopSeqStoredProcedure(String shopCd, String channelId) {
		String orderId = new String();
		try {
			orderId = orderDao.getShopSeqStoredProcedure(shopCd, channelId);
		
		} catch (DAOException de) {
			logger.error("Exception caught in getShopSeqStoredProcedure()", de);
			throw new AppRuntimeException(de);
		}
		return orderId;
	}
	
	/**
	 * Use in toggling button "Amend AF" in order detail page<br>
	 * Enable button "Amend AF" (result = true):<br>
	 * 1. Order signoff already<br>
	 * 2. Collection method = "Digital"<br>
	 * 3. BOM order status = "Pending" or before A + 14 days (A = activation date)<br>
	 * <br>
	 * String[0] = ORDER_ID<br>
	 * String[1] = OCID<br>
	 * String[2] = COLLECT_METHOD<br>
	 * String[3] = BOM_ORDER_STATUS<br>
	 * String[4] = BEFORE_A_PLUS_14_DAYS
	 */
	public boolean getBOMandSBOrderInfo(String orderId) {
		try {
			String[] orderInfo = orderDao.getBOMandSBOrderInfo(orderId);
			
			if (orderInfo != null) {
				// 1. false when at least 1 record is null
				for (String item: orderInfo) {
					if (StringUtils.isBlank(item)) {
						return false;
					}
				}
				
				// 2. false when not fit the criteria
				if (!"D".equals(orderInfo[2])) {
					return false;
				} else if (!("01".equals(orderInfo[3]) || "Y".equals(orderInfo[4]))) {
					return false;
				}
				
				// 3. fit all criteria
				return true;
			} else {
				return false;
			}
			
		} catch (DAOException de) {
			logger.error("Exception caught in getBOMandSBOrderInfo()", de);
			throw new AppRuntimeException(de);
		}
	}

	public OrderMobDTO getOrderMobDTO(String orderId) {
		try {
			return this.orderMobDao.getOrderMobDTO(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOrderMobDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<StockAssgnDTO> getStockAssgnListByOrderId(String orderId) {
		try {
			return this.stockDao.getStockAssgnListByOrderId(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockAssgnListByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}

	public void updateDsMsisdnStatus(String msisdn, int status, String orderId, String staffId, Date appDate) {
		if (StringUtils.isBlank(msisdn)) {
			return;
		}
		try {
			SalesInfoDTO salesInfoDTO = mobCcsSalesInfoService.getSalesInfoDTOByID(staffId, Utility.date2String(appDate, "dd/MM/yyyy")).get(0);
			this.orderDao.updateDsMsisdnStatus(msisdn, status, orderId, salesInfoDTO);
		} catch (DAOException de) {
			logger.error("Exception caught in updateDsMsisdnStatus()", de);
			throw new AppRuntimeException(de);
		}
	}

	public void updateBomWebPaymentTransStatus(String orderId, String status) {
		try {
			orderDao.updateBomWebPaymentTransStatus(orderId, status);
		} catch (DAOException e) {
			logger.error("Exception caught in updateBomWebPaymentTransStatus()", e);
		}
	}

	public List<String> getCodeIdList(String codeType) {
		try {
			return orderDao.getCodeIdList(codeType);
		} catch (DAOException e) {
			logger.error("Exception caught in getCodeIdList()", e);
			return null;
		}
	}
	
	public int cancelDSOrderReleaseStock(String orderId) {
		try {
			return stockDao.cancelDSOrderReleaseStock(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in cancelDSOrderReleaseStock()", e);
			return 0;
		}
	}
	
	public String getDsOrderStatus(String orderId) {
		try {
			return mobDsOrderDao.getDsOrderStatus(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getDsOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateDsOrderStatus (String orderId, String orderStatus) {
		try {
			logger.info("updateDsOrderStatus() is called in OrderServiceImpl");
			this.mobDsOrderDao.updateDsOrderStatus(orderId, orderStatus);
		} catch (DAOException de) {
			logger.error("Exception caught in updateDsOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getDsPrevOrderStatus(String orderId) {
		try {
			return mobDsOrderDao.getDsPrevOrderStatus(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getDsPrevOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateDsPrevOrderStatus (String orderId, String orderStatus) {
		try {
			logger.info("updateDsPrevOrderStatus() is called in OrderServiceImpl");
			this.mobDsOrderDao.updateDsPrevOrderStatus(orderId, orderStatus);
		} catch (DAOException de) {
			logger.error("Exception caught in updateDsPrevOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public void updateDsPaymentCheck(String orderId, String staffId, String paymentCheck) {
		
		try {
			logger.debug("updateDsPaymentCheck() is called in OrderServiceImpl");
			this.orderDao.updateOrderPayCheck(orderId, staffId, paymentCheck);
		} catch (DAOException de) {
			logger.error("Exception caught in updateDsPaymentCheck()", de);
			throw new AppRuntimeException(de);
		}
	}

	public void updateDsSupoortDocCheck(String orderId, String staffId, String supportDocCheck) {
		
		try {
			logger.debug("updateDsSupportDocCheck() is called in OrderServiceImpl");
			this.orderDao.updateSupportDocCheck(orderId, staffId, supportDocCheck);
		} catch (DAOException de) {
			logger.error("Exception caught in updateDsSupportDocCheck()", de);
			throw new AppRuntimeException(de);
		}
	}

	public BomSalesUserDTO getSalesUserInfo(String staffId, String appDate) {
		try {
			logger.debug("getSalesUserInfo() is called in OrderServiceImpl");
			return this.orderDao.getSalesUserInfo(staffId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getSalesUserInfo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<String> getFrozenWindow(String inDate) {
		try {
			logger.debug("getFrozenWindow() is called in OrderServiceImpl");
			return this.orderDao.getFrozenWindow(inDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getFrozenWindow()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getOrderIdUsingSameMRT(String mrt, String orderId) {
		try {
			logger.debug("getOrderIdUsingSameMRT() is called in OrderServiceImpl");
			return this.orderDao.getOrderIdUsingSameMRT(mrt, orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOrderIdUsingSameMRT()", de);
			throw new AppRuntimeException(de);
		}
	}	
	
	public String getOrderIdUsingSameMRTShop(String mrt, String orderId, String shopCd) {
		try {
			logger.debug("getOrderIdUsingSameMRTShop() is called in OrderServiceImpl");
			return this.orderDao.getOrderIdUsingSameMRTShop(mrt, orderId, shopCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getOrderIdUsingSameMRTShop()", de);
			throw new AppRuntimeException(de);
		}
	}	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateMultiSimMemberStatus(String parentOrderId, String memberOrderId, String status, String errCode, String errString , String lastUpdateBy) {
		try {

			logger.debug("updateMultiSimMemberStatus() is called in OrderServiceImpl");
			multiSimInfoDao.updateMultiSimMemberStatus(parentOrderId,memberOrderId,  status , errCode, errString , lastUpdateBy);
		} catch (DAOException de) {
			logger.error("Exception caught in updateMultiSimMemberStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean isMultiSimOrder(String orderId){
		try {
			return orderDao.isMultiSim(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in isMultiSimOrder()", de);
			return false;
		}
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void cancelMultiSimMember(String parentOrderId, String lastUpdateBy) {
		try {

			logger.debug("cancelMultiSimMember() is called in OrderServiceImpl");
			multiSimInfoDao.cancelMultiSimMember(parentOrderId, lastUpdateBy);
		} catch (DAOException de) {
			logger.error("Exception caught in cancelMultiSimMember()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateMemOrder(String memOrderId, String checkPoint, String errCode,
			String errString, String lastUpdBy) {
		try {
			logger.debug("updateMemOrder() is called in OrderServiceImpl");
			orderDao.updateOrderStatus(memOrderId, checkPoint, errCode, errString, lastUpdBy);
		} catch (DAOException de) {
			logger.error("Exception caught in updateMemOrder()", de);
			throw new AppRuntimeException(de);
		}
	}

	public String getOrderNature(String orderId) {
		try {
			logger.debug("getOrderNature() is called in OrderServiceImpl");
			return orderDao.getOrderNature(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOrderNature()", de);
			throw new AppRuntimeException(de);
		}
	}

	public int getOrderChannel(String orderId) {

		return 0;
	}

	public Map<String,String> getOrderType(String orderId) {
		try {
			logger.debug("getOrderType() is called in OrderServiceImpl");
			return orderDao.getOrderType(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOrderType()", de);
			throw new AppRuntimeException(de);
		}	
	}
	
	public String getSimTypeByCosOrder(String orderId) {
		try {
			return orderDao.getSimTypeByCosOrder(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getMdoInd(String orderId) {
		try {
			return orderDao.getMdoInd(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean getOctFlag() {
		try {
			return orderDao.getOctFlag();
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)	
	public void saveCslOrderRecord(String orderId, String staffId) {
		try {
			orderDao.saveCslOrderRecord(orderId, staffId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateDsStockMrtAfterSuccess(String orderId) {
		try {
			logger.debug("updateDsStockMrtAfterSuccess() is called in OrderServiceImpl");
			orderDao.updateDsStockMrtAfterSuccess(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in updateDsStockMrtAfterSuccess()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	private String getDsReleaseStockStatus(String itemSerial) {
		String updateStatus = "27";
		List<StockUpdateDTO> oldStockList;
		try {
			oldStockList = mobDsStockDAO.getDSStockUpdateDTObyImei(itemSerial);
			if (CollectionUtils.isNotEmpty(oldStockList)) {
				StockUpdateDTO oldStock = oldStockList.get(0);
				if (StringUtils.isNotBlank(oldStock.getEventCode())) {
					if (mobDsStockDAO.validateEffEndDate(oldStock.getEventCode())) {
						updateStatus = "28";
					}
				}
			}
		} catch (DAOException e) {
			logger.error("Exception caught in getDsReleaseStockStatus", e);
		}
		return updateStatus;
	}
	
	public MobileSimInfoDTO getChangedSim (String orderId ){
		try {
			return orderDao.getChangedSim(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getChangedSim()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void copyBomwebMobOrderHsrmLog(String oldOrderId, String newOrderId,
			String lastUpdBy) {
		try {
			logger.debug("copyBomwebMobOrderHsrmLog() is called in OrderServiceImpl");
			mobCcsOrderDao.copyBomwebMobOrderHsrmLog(oldOrderId, newOrderId,
					lastUpdBy);

		} catch (DAOException de) {
			logger.error("Exception caught in copyBomwebMobOrderHsrmLog()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean hasTNGServiceDummyVas(String orderId) {
		try {
			return orderDao.hasTNGServiceDummyVas(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean hasStudentPlanTNGStock(String orderId) {
		try {
			return orderDao.hasStudentPlanTNGStock(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<MobDsPaymentTransDTO> updateDsCashPay(String orderId, List<MobDsPaymentTransDTO> paymentTrans) {
		try {
			logger.info("updateDsCashPay() is called in OrderServiceImpl");
			orderDao.deleteBomWebPaymentTransDS(orderId);
			for (MobDsPaymentTransDTO payment: paymentTrans) {
				orderDao.insertBomWebPaymentTrans(payment);
			}
		} catch (DAOException de) {
			logger.error("Exception caught in updateDsStockMrtAfterSuccess()", de);
			throw new AppRuntimeException(de);
		}
		return paymentTrans;
	}
	
	public boolean isCosOrderFrozen(String orderId) {
		try {
			return orderDao.isCosOrderFrozen(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public VasMrtSelectionDTO getVasMrtSelectionDTO(String orderId) {
		try {
			logger.info("getVasMrtSelectionDTO() is called in OrderServiceImpl");
			return orderDao.getVasMrtSelectionDTO(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in updateDsStockMrtAfterSuccess()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public VasMrtSelectionDTO getSsMrtSelectionDTO(String orderId) {
		try {
			logger.info("getSsMrtSelectionDTO() is called in OrderServiceImpl");
			return orderDao.getSsMrtSelectionDTO(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in updateDsStockMrtAfterSuccess()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getOrderBrand(String orderId) {
		logger.debug("getOrderBrand called");
		try {
			return orderDao.getOrderBrand(orderId);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}

	public List<BomWebMupDTO> multiSimInfoDTOToBomwebMupDTO(MultiSimInfoDTO msi) {
		logger.debug("multiSimInfoDTOToBomwebMupDTO called");
		List<BomWebMupDTO> bomWebMupList = new ArrayList<BomWebMupDTO>();
		BomWebMupDTO bwm = new BomWebMupDTO();
		bwm.setOrderId(msi.getOrder().getOrderId());
		bwm.setMupGrpId(null);
		bwm.setPrimary("P");
		bwm.setNewPriInd("Y");
		bwm.setIoInd("I");
		bwm.setpOcId(null);
		bwm.setpServiceId(null);
		bwm.setParentOrderId(null);
		bomWebMupList.add(bwm);
		for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
			bwm = new BomWebMupDTO();
			bwm.setOrderId(msim.getMemberOrderId());
			bwm.setMupGrpId(null);
			bwm.setPrimary("S");
			bwm.setNewPriInd(null);
			bwm.setIoInd("I");
			bwm.setpOcId(null);
			bwm.setpServiceId(null);
			bwm.setParentOrderId(msim.getParentOrderId());
			bomWebMupList.add(bwm);
		}
		return bomWebMupList;
	}
	
	public void updateCNMRTSupportDoc(CNMRTSupportDocDTO dto) {
		logger.debug("updateCNMRTSupportDoc called");
		try {
			orderDao.updateCNMRTSupportDoc(dto);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public List<CNMRTSupportDocDTO> getCNMRTSupportDocList(CNMRTSupportDocDTO dto) {
		logger.debug("getCNMRTSupportDocList called");
		try {
			return orderDao.getCNMRTSupportDocList(dto);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public String getCNMRTSupportDocNewSeqNo(CNMRTSupportDocDTO dto) {
		logger.debug("getCNMRTSupportDocNewSeqNo called");
		try {
			return orderDao.getCNMRTSupportDocNewSeqNo(dto);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public void createCNMRTSupportDoc(CNMRTSupportDocDTO dto) {
		logger.debug("createCNMRTSupportDoc called");
		try {
			orderDao.createCNMRTSupportDoc(dto);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public String getSbPendingOrderListString(String msisdn, String orderId) {
		String result = "";
		try {
			logger.debug("getSbPendingOrderListString() is called in orderService");
			List<OrderDTO> sbPendingOrderList = orderDao.getSbPendingOrderListByMsisdn(msisdn,orderId);
			if (CollectionUtils.isNotEmpty(sbPendingOrderList)) {
				for (OrderDTO dto : sbPendingOrderList) {
					result += "/ " + dto.getOrderId();
				}
			}
		} catch (DAOException de) {
			logger.error("Exception caught in getSbPendingOrderListString()",
					de);
			throw new AppRuntimeException(de);
		}
		return result;
	}
	
	public String getTooOrderMrt(String orderId) {
		logger.debug("getTooOrderMrt called");
		try {
			return orderDao.getTooOrderMrt(orderId);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public List<ClubPointDetailDTO> getClubPointDetailsByOrderId(String orderId) {
		try {
			return  orderDao.getClubPointDetailsByOrderId(orderId);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}

	public String getMnpInd(String orderId) {
		logger.debug("getMnpInd called");
		try {
			return orderDao.getMnpInd(orderId);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public boolean isIphone7TradeInBasket (String orderId,List<CodeLkupDTO> codeIdList)
	{
		try {
			String basketId = orderDao.getBasketId(orderId);
			if (StringUtils.isNotBlank(basketId)) {
				if (CollectionUtils.isNotEmpty(codeIdList)) {
					for (CodeLkupDTO codeId : codeIdList) {
						if (basketId.equalsIgnoreCase(codeId.getCodeId())) {
							return true;
						}
					}
				}
			}
			return false;
		} catch (DAOException de) {
			logger.error("Exception caught in getOrder()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getOnlineInd(String orderId) {
		logger.debug("getOnlineInd called");
		try {
			return orderDao.getOnlineInd(orderId);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	
}

package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsForceCancelDAO;
import com.bomwebportal.mob.ccs.dto.ForceCancelDTO;
import com.bomwebportal.mob.ccs.service.MobCcsCancelServiceImpl.OrderStatus;

@Transactional(readOnly=true)
public class MobCcsForceCancelServiceImpl implements MobCcsForceCancelService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	enum ForceCancelCode {
		NORMAL_HS_FALLOUT_DATE_7DAYS("FC1")
		, HOTTEST_MODEL_HS_FALLOUT_DATE_3DAYS("FC2")
		, NORMAL_HS_DELIVERY_3TIMES("FC3")
		, HOTTEST_MODEL_HS_DELIVERY_2TIMES("FC4")
		, DRAFT_ORDER_LAST_UPDATE_DATE_7DAYS("FC5")
		, PREORDER_ORDER_APP_DATE_48HOURS("FC6")
		, PREORDER_ORDER_APP_DATE_72HOURS("FC7")
		;
		ForceCancelCode(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		private String code;
	}
	
	enum ForceCancelProfile {
		NORMAL(7, ForceCancelCode.NORMAL_HS_FALLOUT_DATE_7DAYS, 3, ForceCancelCode.NORMAL_HS_DELIVERY_3TIMES)
		, HOTTEST_MODEL(3, ForceCancelCode.HOTTEST_MODEL_HS_FALLOUT_DATE_3DAYS, 2, ForceCancelCode.HOTTEST_MODEL_HS_DELIVERY_2TIMES)
		;
		ForceCancelProfile(int dayDiff, ForceCancelCode dayDiffFCCode, int deliveryFailCount, ForceCancelCode deliveryFailCountFCCode) {
			this.dayDiff = dayDiff;
			this.dayDiffFCCode = dayDiffFCCode;
			this.deliveryFailCount = deliveryFailCount;
			this.deliveryFailCountFCCode = deliveryFailCountFCCode;
		}
		public int getDayDiff() {
			return dayDiff;
		}
		public ForceCancelCode getDayDiffFCCode() {
			return dayDiffFCCode;
		}
		public int getDeliveryFailCount() {
			return deliveryFailCount;
		}
		public ForceCancelCode getDeliveryFailCountFCCode() {
			return deliveryFailCountFCCode;
		}
		private int dayDiff;
		private ForceCancelCode dayDiffFCCode;
		private int deliveryFailCount;
		private ForceCancelCode deliveryFailCountFCCode;
		
	}
	
	enum DraftOrderExpiryProfile {
		GENERIC(7, ForceCancelCode.DRAFT_ORDER_LAST_UPDATE_DATE_7DAYS)
		;
		DraftOrderExpiryProfile(int expiryDay, ForceCancelCode fcCode) {
			this.expiryDay = expiryDay;
			this.fcCode = fcCode;
		}
		public int getExpiryDay() {
			return expiryDay;
		}
		public ForceCancelCode getFcCode() {
			return fcCode;
		}
		private int expiryDay;
		private ForceCancelCode fcCode;
	}
	
	enum PreorderOrderExpiryProfile {
		IBS(48, ForceCancelCode.PREORDER_ORDER_APP_DATE_48HOURS)
		, OBS(72, ForceCancelCode.PREORDER_ORDER_APP_DATE_72HOURS)
		, CSS(48, ForceCancelCode.PREORDER_ORDER_APP_DATE_48HOURS)
		, RET(48, ForceCancelCode.PREORDER_ORDER_APP_DATE_48HOURS)
		, SCS(48, ForceCancelCode.PREORDER_ORDER_APP_DATE_48HOURS)
		, OSS(48, ForceCancelCode.PREORDER_ORDER_APP_DATE_48HOURS)
		;
		PreorderOrderExpiryProfile(int expiryHour, ForceCancelCode fcCode) {
			this.expiryHour = expiryHour;
			this.fcCode = fcCode;
		}
		public int getExpiryHour() {
			return expiryHour;
		}
		public ForceCancelCode getFcCode() {
			return fcCode;
		}
		private int expiryHour;
		private ForceCancelCode fcCode;
	}
	
	enum OrderType {
		DRAFT("DRAF")
		, PREORDER("PRE")
		;
		OrderType(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		private String value;
	}
	
	private MobCcsCancelService mobCcsCancelService;
	
	private MobCcsForceCancelDAO mobCcsForceCancelDAO;

	
	public MobCcsCancelService getMobCcsCancelService() {
		return mobCcsCancelService;
	}

	public void setMobCcsCancelService(MobCcsCancelService mobCcsCancelService) {
		this.mobCcsCancelService = mobCcsCancelService;
	}

	public MobCcsForceCancelDAO getMobCcsForceCancelDAO() {
		return mobCcsForceCancelDAO;
	}

	public void setMobCcsForceCancelDAO(MobCcsForceCancelDAO mobCcsForceCancelDAO) {
		this.mobCcsForceCancelDAO = mobCcsForceCancelDAO;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<ForceCancelDTO> forceCancelFalloutOrder() {
		try {
			logger.info("forceCancelFalloutOrder() is called in MobCcsForceCancelServiceImpl");
			List<ForceCancelDTO> resultList = mobCcsForceCancelDAO.getForceCancelDTOALL(OrderStatus.FALLOUT.getStatus());
			if (!isEmpty(resultList)) {
				for (ForceCancelDTO dto : resultList) {
					ForceCancelProfile profile = dto.isHottestModel() ? ForceCancelProfile.HOTTEST_MODEL : ForceCancelProfile.NORMAL;
					if (logger.isDebugEnabled()) {
						logger.debug("profile: " + profile);
					}
					boolean forceCancel = false;
					ForceCancelCode forceCancelCode = null;
					
					if (dto.getFalloutDateDiff() >= profile.getDayDiff()) {
						if (logger.isInfoEnabled()) {
							logger.info("orderId: " + dto.getOrderId() + ", profile dayDiff: " + profile.getDayDiff() + ", forceCancelCode: " + profile.getDayDiffFCCode() + ", falloutDateDiff: " + dto.getFalloutDateDiff());
						}
						forceCancel = true;
						forceCancelCode = profile.getDayDiffFCCode();
					} else if (dto.getDeliveryFailCount() >= profile.getDeliveryFailCount()) {
						if (logger.isInfoEnabled()) {
							logger.info("orderId: " + dto.getOrderId() + ", profile deliveryFailCount: " + profile.getDayDiff() + ", forceCancelCode: " + profile.getDeliveryFailCountFCCode() + ", deliveryFailCount: " + dto.getDeliveryFailCount());
						}
						forceCancel = true;
						forceCancelCode = profile.getDeliveryFailCountFCCode();
					}
					if (forceCancel) {
						if (logger.isDebugEnabled()) {
							logger.debug("cancel fallout orderId: " + dto.getOrderId());
						}
						String lastUpdBy = "CancelOrde";
						dto.setForceCancelled(true);
						dto.setForceCancelCode(forceCancelCode.getCode());

						this.mobCcsCancelService.cancelMobCcsOrder(dto.getOrderId(), dto.getForceCancelCode(), "CANCEL ORDER(FORCE CANCEL)", lastUpdBy);
					}
				}
			}
			return resultList;
		} catch (DAOException de) {
			logger.error("Exception caught in forceCancelFalloutOrder()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<ForceCancelDTO> forceCancelDraftOrder() {
		try {
			logger.info("forceCancelDraftOrder() is called in MobCcsForceCancelServiceImpl");
			List<ForceCancelDTO> resultList = this.mobCcsForceCancelDAO.getForceCancelExpireDTOALL(OrderType.DRAFT.getValue());
			if (!this.isEmpty(resultList)) {
				DraftOrderExpiryProfile profile = DraftOrderExpiryProfile.GENERIC;
				for (ForceCancelDTO dto : resultList) {
					if (dto.getLastUpdateDateDiff() >= profile.getExpiryDay()) {
						if (logger.isDebugEnabled()) {
							logger.debug("cancel draft orderId: " + dto.getOrderId());
						}
						if (logger.isInfoEnabled()) {
							logger.info("orderId: " + dto.getOrderId() + ", profile expiryDay: " + profile.getExpiryDay() + ", forceCancelCode: " + profile.getFcCode().getCode() + ", lastUpdateDateDiff: " + dto.getLastUpdateDateDiff());
						}
						String lastUpdBy = "CancelOrde";
						dto.setForceCancelled(true);
						dto.setForceCancelCode(profile.getFcCode().getCode());
						
						this.mobCcsCancelService.cancelMobCcsOrder(dto.getOrderId(), dto.getForceCancelCode(), "CANCEL ORDER(FORCE CANCEL)", lastUpdBy);
					}
				}
			}
			return resultList;
		} catch (DAOException de) {
			logger.error("Exception caught in forceCancelDraftOrder()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<ForceCancelDTO> forceCancelPreorderOrder() {
		try {
			logger.info("forceCancelPreorderOrder() is called in MobCcsForceCancelServiceImpl");
			List<ForceCancelDTO> resultList = this.mobCcsForceCancelDAO.getForceCancelExpireDTOALL(OrderType.PREORDER.getValue());
			if (!this.isEmpty(resultList)) {
				for (ForceCancelDTO dto : resultList) {
					PreorderOrderExpiryProfile profile = PreorderOrderExpiryProfile.valueOf(dto.getShopCd());
					if (dto.getAppDateHrDiff() >= profile.getExpiryHour()) {
						if (logger.isDebugEnabled()) {
							logger.debug("cancel preorder orderId: " + dto.getOrderId());
						}
						if (logger.isInfoEnabled()) {
							logger.info("orderId: " + dto.getOrderId() + ", profile expiryHour: " + profile.getExpiryHour() + ", forceCancelCode: " + profile.getFcCode().getCode() + ", appDateHrDiff: " + dto.getAppDateHrDiff());
						}
						String lastUpdBy = "CancelOrde";
						dto.setForceCancelled(true);
						dto.setForceCancelCode(profile.getFcCode().getCode());
						
						this.mobCcsCancelService.cancelMobCcsOrder(dto.getOrderId(), dto.getForceCancelCode(), "CANCEL ORDER(FORCE CANCEL)", lastUpdBy);
					}
				}
			}
			return resultList;
		} catch (DAOException de) {
			logger.error("Exception caught in forceCancelPreorderOrder()", de);
			throw new AppRuntimeException(de);
		}
	}

	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}

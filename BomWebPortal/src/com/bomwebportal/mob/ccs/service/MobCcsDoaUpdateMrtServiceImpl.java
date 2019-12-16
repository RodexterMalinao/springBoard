package com.bomwebportal.mob.ccs.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.BServiceDAO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsDoaUpdateMrtDAO;
import com.bomwebportal.mob.ccs.dto.DoaRequestDTO;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsOrderFalloutDTO;
import com.bomwebportal.mob.ccs.dto.SbOrderAmendDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.validate.service.ValidateService;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.Utility;

@Transactional(readOnly=true)
public class MobCcsDoaUpdateMrtServiceImpl implements MobCcsDoaUpdateMrtService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	enum OrderAmendType {
		UPDATE_CUSTOMER_INFO("OA01")
		, UPDATE_SRD_INFO("OA02")
		, UPDATE_MNP_INFO("OA03")
		;
		OrderAmendType(String codeId) {
			this.codeId = codeId;
		}
		public String getCodeId() {
			return codeId;
		}
		private String codeId;
	}
	
	enum FalloutCat {
		DOA("DOA")
		, ONSITE_DOA("ON_DOA")
		, MNP_REJ("MNP_REJ")
		;
		FalloutCat(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		private String value;
	}

	private OrderService orderService;
	private MobCcsSbOrderAmendService mobCcsSbOrderAmendService;
	private DeliveryService deliveryService;
	private MobCcsOrderSearchService mobCcsOrderSearchService;
	private MobCcsDoaRequestService mobCcsDoaRequestService;
	private MobCcsOrderFalloutService mobCcsOrderFalloutService;
	private MnpService mnpService;
	private MobCcsMrtService mobCcsMrtService;
	private MobCcsMaintParmLkupService mobCcsMaintParmLkupService;

	private MobCcsDoaUpdateMrtDAO mobCcsDoaUpdateMrtDAO;
	private BServiceDAO bServiceDAO;
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public MobCcsSbOrderAmendService getMobCcsSbOrderAmendService() {
		return mobCcsSbOrderAmendService;
	}

	public void setMobCcsSbOrderAmendService(
			MobCcsSbOrderAmendService mobCcsSbOrderAmendService) {
		this.mobCcsSbOrderAmendService = mobCcsSbOrderAmendService;
	}

	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	public MobCcsOrderSearchService getMobCcsOrderSearchService() {
		return mobCcsOrderSearchService;
	}

	public void setMobCcsOrderSearchService(
			MobCcsOrderSearchService mobCcsOrderSearchService) {
		this.mobCcsOrderSearchService = mobCcsOrderSearchService;
	}

	public MobCcsDoaRequestService getMobCcsDoaRequestService() {
		return mobCcsDoaRequestService;
	}

	public void setMobCcsDoaRequestService(
			MobCcsDoaRequestService mobCcsDoaRequestService) {
		this.mobCcsDoaRequestService = mobCcsDoaRequestService;
	}

	public MobCcsOrderFalloutService getMobCcsOrderFalloutService() {
		return mobCcsOrderFalloutService;
	}

	public void setMobCcsOrderFalloutService(
			MobCcsOrderFalloutService mobCcsOrderFalloutService) {
		this.mobCcsOrderFalloutService = mobCcsOrderFalloutService;
	}

	public MnpService getMnpService() {
		return mnpService;
	}

	public void setMnpService(MnpService mnpService) {
		this.mnpService = mnpService;
	}

	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
	}
	
	public MobCcsMaintParmLkupService getMobCcsMaintParmLkupService() {
		return mobCcsMaintParmLkupService;
	}

	public void setMobCcsMaintParmLkupService(
			MobCcsMaintParmLkupService mobCcsMaintParmLkupService) {
		this.mobCcsMaintParmLkupService = mobCcsMaintParmLkupService;
	}

	public MobCcsDoaUpdateMrtDAO getMobCcsDoaUpdateMrtDAO() {
		return mobCcsDoaUpdateMrtDAO;
	}
	
	public void setMobCcsDoaUpdateMrtDAO(MobCcsDoaUpdateMrtDAO mobCcsDoaUpdateMrtDAO) {
		this.mobCcsDoaUpdateMrtDAO = mobCcsDoaUpdateMrtDAO;
	}
	
	public BServiceDAO getbServiceDAO() {
		return bServiceDAO;
	}

	public void setbServiceDAO(BServiceDAO bServiceDAO) {
		this.bServiceDAO = bServiceDAO;
	}


	public AmendScenario getAmendScenario(OrderDTO orderDto) {
		String sbOrderStatus = orderDto.getOrderStatus();
		if (ORDER_STATUS_COMPLETED.equals(sbOrderStatus)) {
			return AmendScenario.SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION;
		}
		if (ORDER_STATUS_FALLOUT.equals(sbOrderStatus)) {
			if (StringUtils.isNotBlank(orderDto.getReasonCd())) {
				if ("J014".equals(orderDto.getReasonCd())) {
					return AmendScenario.SALES_FOLLOW_UP;
				}
				if (orderDto.getReasonCd().startsWith("J")) {
					return AmendScenario.MNP_REJECT;
				}
				if ("G002".equals(orderDto.getReasonCd()) || "G003".equals(orderDto.getReasonCd())) {
					return AmendScenario.ONSITE_DOA;
				}
				if ("N002".equals(orderDto.getReasonCd()) || "N003".equals(orderDto.getReasonCd())) {
					return AmendScenario.DOA;
				}
				if ("K001".equals(orderDto.getReasonCd())) {
					return AmendScenario.SRD_EXP;
				}
			}
		}
		return null;
	}
	
	public Team getTeam(String channelCd) {
		List<MaintParmLkupDTO> list = this.mobCcsMaintParmLkupService.getMaintParmLkupDTO(channelCd, "MOB", "DOA MRT UPDATE", "ROLE");
		// return the first result
		return this.isEmpty(list) ? null : Team.valueOf(list.get(0).getParmValue());
	}

	public boolean allowFalloutToSales(OrderDTO orderDto, String channelCd) {
		MnpInd mnpInd = MnpInd.valueOf(orderDto.getMnpInd());
		switch (mnpInd) {
		case N:
			return false;
		case Y:
		case A:
			break;
		}
		Team team = this.getTeam(channelCd);
		if (!Team.SUPPORT.equals(team)) {
			return false;
		}
		if (ORDER_STATUS_FALLOUT.equals(orderDto.getOrderStatus())) {
			if (StringUtils.isNotBlank(orderDto.getReasonCd())) {
				if (orderDto.getReasonCd().startsWith("J") && !"J014".equals(orderDto.getReasonCd())) {
					return true;
				}
				if ("K001".equals(orderDto.getReasonCd())) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean allowMnpWindowChange(OrderDTO orderDto, String channelCd) {
		MnpInd mnpInd = MnpInd.valueOf(orderDto.getMnpInd());
		AmendScenario amendScenario = this.getAmendScenario(orderDto);
		Team team = this.getTeam(channelCd);
		boolean mnpWindowChange = true;
		switch (mnpInd) {
		case N:
			mnpWindowChange = false;
			break;
		case Y:
		{
			switch (team) {
			case SUPPORT:
			{
				switch (amendScenario) {
				case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
				case DOA:
					if (this.isMnpWindowFrozen(orderDto)) {
						mnpWindowChange = false;
					}
					break;
				case MNP_REJECT:
				case ONSITE_DOA:
				case SRD_EXP:
				case SALES_FOLLOW_UP:
					break;
				}
			}
				break;
			case SALES:
			{
				switch (amendScenario) {
				case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
				case SALES_FOLLOW_UP:
					if (this.isMnpWindowFrozen(orderDto)) {
						mnpWindowChange = false;
					}
					break;
				case MNP_REJECT:
				case ONSITE_DOA:
				case DOA:
				case SRD_EXP:
					break;
				}
			}
				break;
			}
		}
			break;
		case A:
		{
			switch (team) {
			case SUPPORT:
			{
				switch (amendScenario) {
				case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
				case DOA:
					mnpWindowChange = false;
					break;
				case MNP_REJECT:
				case ONSITE_DOA:
				case SRD_EXP:
				case SALES_FOLLOW_UP:
					break;
				}
			}
				break;
			case SALES:
			{
				switch (amendScenario) {
				case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
				case SALES_FOLLOW_UP:
					mnpWindowChange = false;
					break;
				case MNP_REJECT:
				case ONSITE_DOA:
				case DOA:
				case SRD_EXP:
					break;
				}
			}
				break;
			}
		}
			break;
		}
		return mnpWindowChange;
	}
	
	public MRTUI getMrtUI(String orderId) {
		ArrayList<MobCcsMrtBaseDTO> mobCcsMrtDtoList = mobCcsMrtService.getMobCcsMrtDTO(orderId);
		
		if (!this.isEmpty(mobCcsMrtDtoList)) {
			return mobCcsMrtService.mrtDtoChangeToUiDto(mobCcsMrtDtoList);
		}
		
		return null;
	}

	/**
	 * in fallout to sales case(orderStatus: 99, reasonCd = JXXX or K001). only can change MRT info 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateForFalloutToSales(String orderId, String username, String channelCd) {
		OrderDTO orderDto = this.orderService.getOrder(orderId);
		if (!allowFalloutToSales(orderDto, channelCd)) {
			logger.warn("not allow to fallout to sales, orderId: " + orderId + ", channelCd: " + channelCd);
			return;
		}
		try {
			this.mobCcsDoaUpdateMrtDAO.createOrderHist(orderId);
			this.updateOrderStatusAfterSaveWithFallToSales(orderDto, username);
		} catch (DAOException de) {
			logger.error("Exception caught in updateForFalloutToSales()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateForNewNumber(DeliveryUI deliveryUI
			, String orderId
			, Date srvReqDate
			, String username, String channelCd) {
		try {
			OrderDTO orderDto = this.orderService.getOrder(orderId);
			boolean allowUpdateSRD = this.allowUpdateSRD(orderDto, channelCd);
			if (deliveryUI != null || allowUpdateSRD) {
				this.mobCcsDoaUpdateMrtDAO.createOrderHist(orderId);
				this.updateDeliveryUI(deliveryUI, orderDto, username, channelCd);
				if (allowUpdateSRD) {
					this.mobCcsDoaUpdateMrtDAO.updateBomwebOrderForNewNumber(orderId, srvReqDate, username);
					this.mobCcsDoaUpdateMrtDAO.updateBomwebMrtForNewNumber(orderId, 1, srvReqDate, username);
					this.updateOrderStatusAfterSave(orderDto, username);
					this.insertSbOrderAmend(orderId, OrderAmendType.UPDATE_SRD_INFO, username);
				}
			}
		} catch (DAOException de) {
			logger.error("Exception caught in updateForNewNumber()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateForMnp(DeliveryUI deliveryUI
			, String orderId
			, String mnpMsisdn, String mnpTicketNum, Date cutOverDate, String cutOverTime
			, String custName, String docNo, String dno, String actDno
			, String username, String channelCd) {
		try {
			//MnpDTO mnpDto = this.getPccw3gNumberInfo(mnpMsisdn);
			OrderDTO orderDto = this.orderService.getOrder(orderId);
			boolean allowUpdateMnp = this.allowUpdateMnp(orderDto, channelCd);
			if (deliveryUI != null || allowUpdateMnp) {
				this.mobCcsDoaUpdateMrtDAO.createOrderHist(orderId);
				this.updateDeliveryUI(deliveryUI, orderDto, username, channelCd);
				if (allowUpdateMnp) {
					if (this.allowMnpWindowChange(orderDto, channelCd)) {
						this.mobCcsDoaUpdateMrtDAO.updateBomwebOrder(orderId, mnpMsisdn, cutOverDate, username);
						this.mobCcsDoaUpdateMrtDAO.updateBomwebMnp(orderId, mnpTicketNum, cutOverDate, cutOverTime, custName, docNo, dno, actDno , username);
						this.mobCcsDoaUpdateMrtDAO.updateBomwebMrt(orderId, 1, mnpMsisdn, mnpTicketNum, cutOverDate, cutOverTime, custName, docNo, dno, actDno, username);
					} else {
						this.mobCcsDoaUpdateMrtDAO.updateBomwebOrderWhenMnpWindowFrozen(orderId, mnpMsisdn, username);
						this.mobCcsDoaUpdateMrtDAO.updateBomwebMnpWhenMnpWindowFrozen(orderId, mnpTicketNum, custName, docNo, dno, actDno, username);
						this.mobCcsDoaUpdateMrtDAO.updateBomwebMrtWhenMnpWindowFrozen(orderId, 1, mnpMsisdn, mnpTicketNum, custName, docNo, dno, actDno, username);
					}
					this.updateOrderStatusAfterSave(orderDto, username);
					this.insertSbOrderAmend(orderId, OrderAmendType.UPDATE_MNP_INFO, username);
				}
			}
		} catch (DAOException de) {
			logger.error("Exception caught in updateForMnp()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateForNewNumberAndMnp(DeliveryUI deliveryUI
			, String orderId
			, Date srvReqDate
			, String mnpMsisdn, String mnpTicketNum, Date cutOverDate, String cutOverTime
			, String custName, String docNo, String dno, String actDno
			, String username, String channelCd) {
		try {
			//MnpDTO mnpDto = this.getPccw3gNumberInfo(mnpMsisdn);
			OrderDTO orderDto = this.orderService.getOrder(orderId);
			boolean allowUpdateSRD = this.allowUpdateSRD(orderDto, channelCd);
			boolean allowUpdateMnp = this.allowUpdateMnp(orderDto, channelCd);
			if (deliveryUI != null || allowUpdateSRD || allowUpdateMnp) {
				this.mobCcsDoaUpdateMrtDAO.createOrderHist(orderId);
				this.updateDeliveryUI(deliveryUI, orderDto, username, channelCd);
				if (allowUpdateSRD || allowUpdateMnp) {
					if (allowUpdateSRD) {
						this.mobCcsDoaUpdateMrtDAO.updateBomwebOrderForNewNumber(orderId, srvReqDate, username);
						this.mobCcsDoaUpdateMrtDAO.updateBomwebMrtForNewNumber(orderId, 1, srvReqDate, username);
					}
					if (allowUpdateMnp) {
						this.mobCcsDoaUpdateMrtDAO.updateBomwebMrt(orderId, 2, mnpMsisdn, mnpTicketNum, cutOverDate, cutOverTime, custName, docNo, dno, actDno, username);
					}
					this.updateOrderStatusAfterSave(orderDto, username);
					if (allowUpdateSRD) {
						this.insertSbOrderAmend(orderId, OrderAmendType.UPDATE_SRD_INFO, username);
					}
					if (allowUpdateMnp) {
						this.insertSbOrderAmend(orderId, OrderAmendType.UPDATE_MNP_INFO, username);
					}
				}
			}
		} catch (DAOException de) {
			logger.error("Exception caught in updateForNewNumberAndMnp()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public void updateDeliveryUI(DeliveryUI deliveryUI, String orderId, String username, String channelCd) {
		// for external call, update delivery
		// for class internal call, call method with OrderDTO parameter 
		OrderDTO orderDto = this.orderService.getOrder(orderId);
		this.updateDeliveryUI(deliveryUI, orderDto, username, channelCd);
	}
	
	private void updateDeliveryUI(DeliveryUI deliveryUI, OrderDTO orderDto, String username, String channelCd) {
		if (deliveryUI == null) {
			logger.debug("deliveryUI is null for orderId: " + orderDto.getOrderId());
			return;
		}
		deliveryService.updateBomWebCustomerAddress(deliveryUI);
		deliveryService.updateContactDto(deliveryUI.getPrimaryContact());
		deliveryService.updateOrderDelivery(deliveryUI);
		
		boolean allowUpdateSRD = this.allowUpdateSRD(orderDto, channelCd);
		boolean allowUpdateMnp = this.allowUpdateMnp(orderDto, channelCd);
		if (!(allowUpdateSRD || allowUpdateMnp)) {
			this.updateOrderStatusAfterSave(orderDto, username);
		}
		this.insertSbOrderAmend(orderDto.getOrderId(), OrderAmendType.UPDATE_CUSTOMER_INFO, username);
	}
	
	private void updateOrderStatusAfterSave(OrderDTO orderDto, String username) {
		String newOrderStatus = ORDER_STATUS_COMPLETED;
		String newReasonCd = null;
		String newCheckPoint = null;
		boolean updateDoaRequest = false;
		// keep orderStatus = ORDER_STATUS_FALLOUT when reasonCd matched
		// change reasonCd when G002 / N002
		// change orderStatus to COMPLETED for fallout order not in reasonCd (G002, N002, G003, N003)
		if (ORDER_STATUS_FALLOUT.equals(orderDto.getOrderStatus())) {
			if ("G002".equals(orderDto.getReasonCd())) {
				newReasonCd = "G003";
				newOrderStatus = ORDER_STATUS_FALLOUT;
				newCheckPoint = orderDto.getCheckPoint();
				updateDoaRequest = true;
			} else if ("N002".equals(orderDto.getReasonCd())) {
				newReasonCd = "N003";
				newOrderStatus = ORDER_STATUS_FALLOUT;
				newCheckPoint = orderDto.getCheckPoint();
				updateDoaRequest = true;
			} else {
				for (String reasonCd : new String[]{ "G003", "N003" }) {
					if (reasonCd.equals(orderDto.getReasonCd())) {
						newReasonCd = reasonCd;
						newOrderStatus = ORDER_STATUS_FALLOUT;
						newCheckPoint = orderDto.getCheckPoint();
						break;
					}
				}
			}
		}
		this.orderService.updateCcsOrderStatus(orderDto.getOrderId(), newOrderStatus, newCheckPoint, newReasonCd, null, null, username);
		if (updateDoaRequest) {
			List<DoaRequestDTO> list = this.mobCcsDoaRequestService.getDoaRequestDTOALL(orderDto.getOrderId());
			if (list != null && !list.isEmpty()) {
				DoaRequestDTO dto = list.get(list.size() - 1);
				if ("G002".equals(dto.getStatus()) || "N002".equals(dto.getStatus())) {
					this.mobCcsDoaRequestService.updateDoaRequestStatus(dto.getRowId(), newReasonCd);
				}
			}
		}
		if (ORDER_STATUS_FALLOUT.equals(orderDto.getOrderStatus())) {
			if (StringUtils.isNotBlank(newReasonCd)) {
				if (newReasonCd.startsWith("G")) {
					this.insertOrderFallout(orderDto.getOrderId(), FalloutCat.ONSITE_DOA, newReasonCd, username);
				} else if (newReasonCd.startsWith("N")) {
					this.insertOrderFallout(orderDto.getOrderId(), FalloutCat.DOA, newReasonCd, username);
				}
			}
		}
	}
	
	private void updateOrderStatusAfterSaveWithFallToSales(OrderDTO orderDto, String username) {
		final String reasonCd = "J014";

		this.orderService.updateCcsOrderStatus(orderDto.getOrderId(), ORDER_STATUS_FALLOUT, orderDto.getCheckPoint(), reasonCd, null, null, username);
		List<DoaRequestDTO> list = this.mobCcsDoaRequestService.getDoaRequestDTOALL(orderDto.getOrderId());
		if (list != null && !list.isEmpty()) {
			DoaRequestDTO dto = list.get(list.size() - 1);
			this.mobCcsDoaRequestService.updateDoaRequestStatus(dto.getRowId(), reasonCd);
		}
		this.insertOrderFallout(orderDto.getOrderId(), FalloutCat.MNP_REJ, reasonCd, username);
	}
	
	private int insertSbOrderAmend(String orderId, OrderAmendType orderAmendType, String username) {
		SbOrderAmendDTO dto = new SbOrderAmendDTO();
		dto.setOrderId(orderId);
		dto.setOrderAmendType(orderAmendType.getCodeId());
		dto.setCreateBy(username);
		dto.setLastUpdBy(username);
		return this.mobCcsSbOrderAmendService.insertSbOrderAmendDTO(dto);
	}
	
	public boolean allowUpdateSRD(OrderDTO orderDto, String channelCd) {
		AmendScenario amendScenario = this.getAmendScenario(orderDto);
		Team team = this.getTeam(channelCd);
		if (logger.isDebugEnabled()) {
			logger.debug("amendScenario: " + amendScenario + ", team: " + team);
		}
		if (amendScenario == null) {
			return false;
		}
		if (team == null) {
			return false;
		}
		MnpInd mnpInd = MnpInd.valueOf(orderDto.getMnpInd());
		switch (mnpInd) {
		case N:
		case A:
		{
			switch (team) {
			case SUPPORT:
			{
				switch (amendScenario) {
				case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
				case ONSITE_DOA:
				case DOA:
				case SRD_EXP:
					if (BOM_ORDER_STATUS_PENDING.equals(this.getBomOrderStatus(orderDto))) {
						return true;
					}
					break;
				case MNP_REJECT:
				case SALES_FOLLOW_UP:
					// F&S pass to SALES
					break;
				}
			}
				break;
			case SALES:
			{
				switch (amendScenario) {
				case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
				case SALES_FOLLOW_UP:
					if (BOM_ORDER_STATUS_PENDING.equals(this.getBomOrderStatus(orderDto))) {
						return true;
					}
					break;
				case MNP_REJECT:
				case ONSITE_DOA:
				case DOA:
				case SRD_EXP:
					// SALES not allow to proceed
					break;
				}
			}
				break;
			}
		}
			break;
		case Y:
			return false;
		}
		return false;
	}
	
	public boolean allowUpdateMnp(OrderDTO orderDto, String channelCd) {
		AmendScenario amendScenario = this.getAmendScenario(orderDto);
		Team team = this.getTeam(channelCd);
		if (logger.isDebugEnabled()) {
			logger.debug("amendScenario: " + amendScenario + ", team: " + team);
		}
		if (amendScenario == null) {
			return false;
		}
		if (team == null) {
			return false;
		}
		MnpInd mnpInd = MnpInd.valueOf(orderDto.getMnpInd());
		switch (mnpInd) {
		case N:
			return false;
		case Y:
			if (BOM_ORDER_STATUS_PENDING.equals(this.getBomOrderStatus(orderDto))) {
				switch (team) {
				case SUPPORT:
					switch (amendScenario) {
					case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
						return !this.isMnpWindowFrozen(orderDto); 
					case MNP_REJECT:
					case ONSITE_DOA:
					case DOA:
					case SRD_EXP:
						return true;
					case SALES_FOLLOW_UP:
						// FS not allow to process
						break;
					}
					break;
				case SALES:
					switch (amendScenario) {
					case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
					case SALES_FOLLOW_UP:
						return true;
					case MNP_REJECT:
					case ONSITE_DOA:
					case DOA:
					case SRD_EXP:
						// SALES not allow to process
						break;
					}
					break;
				}
			}
			break;
		case A:
		{
			MRTUI mrtUI = this.getMrtUI(orderDto.getOrderId());
			// as new # + mnp order is splitted into 2 orders in BOM
			// existing known BOM order cannot identify current MNP order status
			Date cutOverDate = trunc(Utility.string2Date(mrtUI.getCutOverDateStr()));
			if (cutOverDate.before(new Date()) && this.isBomActivated(mrtUI.getMnpMsisdn())) {
				// when order's cutover date is before today, and mnp msisdn is activated in bom
				// no change is allowed
				return false;
			}
			switch (team) {
			case SUPPORT:
				switch (amendScenario) {
				case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
				case MNP_REJECT:
				case ONSITE_DOA:
				case DOA:
				case SRD_EXP:
					return true;
				case SALES_FOLLOW_UP:
					// FS not allow to process
					break;
				}
				break;
			case SALES:
				switch (amendScenario) {
				case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
				case SALES_FOLLOW_UP:
					return true;
				case MNP_REJECT:
				case ONSITE_DOA:
				case DOA:
				case SRD_EXP:
					// SALES not allow to process
					break;
				}
				break;
			}
		}
			break;
		}
		return false;
	}
	
	private int insertOrderFallout(String orderId, FalloutCat falloutCat, String reasonCd, String username) {
		MobCcsOrderFalloutDTO dto = new MobCcsOrderFalloutDTO();
		Date now = new Date();
		dto.setOrderId(orderId);
		dto.setReportBy(username);
		dto.setOccuranceDate(now);
		dto.setFalloutCat(falloutCat.getValue());
		dto.setReasonCode(reasonCd);
		dto.setCreateBy(username);
		dto.setCreateDate(now);
		dto.setLastUpdBy(username);
		dto.setLastUpdDate(now);
		return this.mobCcsOrderFalloutService.insertOrderFalloutDTO(dto);
	}
	
	private MnpDTO getPccw3gNumberInfo(String msisdn) {
		MnpDTO mnpDTO = new MnpDTO();
		mnpDTO.setMnpMsisdn(msisdn);
		return this.mnpService.validateMnpMsisdn(mnpDTO);
	}
	
	public boolean isMnpWindowFrozen(OrderDTO orderDto) {
		MRTUI mrtUI = this.getMrtUI(orderDto.getOrderId());
		MnpInd mnpInd = MnpInd.valueOf(mrtUI.getMnpInd());
		boolean mnpWindowFrozen = false;
		switch (mnpInd) {
		case N:
			break;
		case Y:
		case A:
			Date currentCutOverDate = Utility.string2Date(mrtUI.getCutOverDateStr());
			long dayDiff = (currentCutOverDate.getTime() - trunc(new Date()).getTime()) / (1000 * 60 * 60 * 24);
			mnpWindowFrozen = (dayDiff == 1);
			break;
		}
		return mnpWindowFrozen;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	private Date trunc(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	private String getBomOrderStatus(OrderDTO orderDto) {
		if (StringUtils.isBlank(orderDto.getOcid())) {
			return null;
		}
		return this.mobCcsOrderSearchService.getOcidStatus(orderDto.getOcid());
	}
	
	public boolean isBomActivated(String msisdn) {
		try {
			return this.bServiceDAO.isActivated(msisdn, BServiceDAO.SystemId.MOB, BServiceDAO.ServiceType.TYPE_3G);
		} catch (DAOException e) {
			if (logger.isWarnEnabled()) {
				logger.warn("DAOException found", e);
			}
			throw new AppRuntimeException(e);
		}
	}
}

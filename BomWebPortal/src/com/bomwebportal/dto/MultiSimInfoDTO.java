package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.bomwebportal.dao.MultiSimInfoDAO;
import com.bomwebportal.dao.BomSubscriberDAO;
import com.bomwebportal.dto.BomMupInfoDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class MultiSimInfoDTO implements Serializable, Cloneable {

	private static final long serialVersionUID = 7902611567270196247L;

	private List<MultiSimInfoMemberDTO> members;
	private List<VasDetailDTO> bundleItemList;
	private List<VasDetailDTO> optionalItemList;
	private List<VasDetailDTO> simItemList;
	private int memberCount;
	private String itemId;
	private BasketDTO basket;
	private HttpSession session;
	private boolean amend;
	private boolean amendSim;
	private OrderDTO order;
	private String actionType;
	private boolean pcrfMupAlert;
	private int checkMigrateMemberNum;
	private BomSubscriberDAO bomSubscriberDAO;
	private boolean passValidation;
	
	/**
	 * An object map key to DTO value
	 */
	private Map<String, Object> objectsMap;
	
	/**
	 * Get specific DTO object value which map to certain key
	 * @param key
	 * @return DTO object
	 */
	public Object getValue(String key) {
		if (this.objectsMap == null || this.objectsMap.isEmpty()) {
			return null;
		} else {
			return this.objectsMap.get(key);
		}
	}
	/**
	 * Set specific DTO object value into map which match with a unique key
	 * @param key
	 * @param value DTO object
	 */
	public void setValue(String key, Object value) {
		if (this.objectsMap == null) {
			objectsMap = new HashMap<String, Object>();
		}
		
		objectsMap.put(key, value);
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		MultiSimInfoDTO clone = (MultiSimInfoDTO) super.clone();
		List<MultiSimInfoMemberDTO> members = new ArrayList<MultiSimInfoMemberDTO>();
		for (MultiSimInfoMemberDTO member: this.getMembers()) {
			members.add(member.getClone());
		}
		clone.setMembers(members);
		return clone;
	}
	public MultiSimInfoDTO getClone() {
		try {
			return (MultiSimInfoDTO) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	public List<MultiSimInfoMemberDTO> getMembers() {
		return members;
	}
	public void setMembers(List<MultiSimInfoMemberDTO> members) {
		this.members = members;
	}
	public List<VasDetailDTO> getBundleItemList() {
		return bundleItemList;
	}
	public void setBundleItemList(List<VasDetailDTO> bundleItemList) {
		this.bundleItemList = bundleItemList;
	}
	public List<VasDetailDTO> getOptionalItemList() {
		return optionalItemList;
	}
	public void setOptionalItemList(List<VasDetailDTO> optionalItemList) {
		this.optionalItemList = optionalItemList;
	}
	public List<VasDetailDTO> getSimItemList() {
		return simItemList;
	}
	public void setSimItemList(List<VasDetailDTO> simItemList) {
		this.simItemList = simItemList;
	}
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public BasketDTO getBasket() {
		return basket;
	}
	public void setBasket(BasketDTO basket) {
		this.basket = basket;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public boolean isAmend() {
		return amend;
	}
	public void setAmend(boolean amend) {
		this.amend = amend;
	}
	public boolean isAmendSim() {
		return amendSim;
	}
	public void setAmendSim(boolean amendSim) {
		this.amendSim = amendSim;
	}
	public OrderDTO getOrder() {
		return order;
	}
	public void setOrder(OrderDTO order) {
		this.order = order;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public boolean isPcrfMupAlert() {
		return pcrfMupAlert;
	}
	public void setPcrfMupAlert(boolean pcrfMupAlert) {
		this.pcrfMupAlert = pcrfMupAlert;
	}
	public int getCheckMigrateMemberNum() {
		return checkMigrateMemberNum;
	}
	public void setCheckMigrateMemberNum(int checkMigrateMemberNum) {
		this.checkMigrateMemberNum = checkMigrateMemberNum;
	}
	public boolean isPassValidation() {
		return passValidation;
	}
	public void setPassValidation(boolean passValidation) {
		this.passValidation = passValidation;
	}
}
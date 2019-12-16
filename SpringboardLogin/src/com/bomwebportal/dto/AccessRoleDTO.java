package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class AccessRoleDTO implements Serializable {

	private static final long serialVersionUID = 3535783701524608498L;

	public static final String ACCESS_RIGHT_ENABLE = "E";
	public static final String ACCESS_RIGHT_READONLY = "R";
	public static final String ACCESS_RIGHT_DISABLE = "D";
	public static final String ACCESS_RIGHT_HIDDEN = "H";

	public String channelId = null;
	public String channelCd = null;
	public String category = null;
	public String funcId = null;
	public String accessRight = null;
	public Map<String,AccessPropertyDTO> propertyMap = null;

	
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getAccessRight() {
		return accessRight;
	}

	public void setAccessRight(String accessRight) {
		this.accessRight = accessRight;
	}
	
	public void setPropertyAccessRole(String pProperty, String pAccessRight) {
		
		if (this.propertyMap == null) {
			this.propertyMap = new TreeMap<String,AccessPropertyDTO>();
		}		
		this.propertyMap.put(pProperty, new AccessPropertyDTO(pProperty, pAccessRight));
	}
	
	public AccessPropertyDTO[] getPropertyAccessRole() {
		
		if (this.propertyMap == null) {
			return null;
		}
		return (AccessPropertyDTO[])this.propertyMap.values().toArray(new AccessPropertyDTO[this.propertyMap.size()]);
	}
}

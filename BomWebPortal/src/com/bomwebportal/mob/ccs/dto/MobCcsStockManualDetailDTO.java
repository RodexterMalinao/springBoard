package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.List;

public class MobCcsStockManualDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4153859035443982267L;

	private String status;
	private String location;
	private String imei;
	private List<MobCcsStockManualResultDTO> mobCcsStockManualResultDTO;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public List<MobCcsStockManualResultDTO> getMobCcsStockManualResultDTO() {
		return mobCcsStockManualResultDTO;
	}
	public void setMobCcsStockManualResultDTO(
			List<MobCcsStockManualResultDTO> mobCcsStockManualResultDTO) {
		this.mobCcsStockManualResultDTO = mobCcsStockManualResultDTO;
	}
	
}

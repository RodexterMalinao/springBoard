package com.bomltsportal.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;

public class VasDetailFormDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 636820445585631623L;
	
	//nowTv
	private boolean selectedNowTv;
	private boolean selectedAdultChannel;
	private String selectedNowTvSpecGroupId;
	private String selectedNowTvPayGroupId;
	private String selectedNowTvSpecChannelId;
	private String selectedNowTvPayChannelId;
	private List<ItemDetailDTO> nowTvSpecItems;
	private List<ItemDetailDTO> nowTvPayItems;
	private List<ChannelGroupDetailDTO> specChannelGroupList;
	private List<ChannelGroupDetailDTO> payChannelGroupList;
	
	//MOOV
	private boolean selectedMoov;
	private List<ItemDetailDTO> moovItems;
	
	//other vas
	private List<ItemDetailDTO> otherItems;

	private String errorMsg;
	
	public boolean isSelectedNowTv() {
		return selectedNowTv;
	}
	public void setSelectedNowTv(boolean selectedNowTv) {
		this.selectedNowTv = selectedNowTv;
	}
	public boolean isSelectedMoov() {
		return selectedMoov;
	}
	public void setSelectedMoov(boolean selectedMoov) {
		this.selectedMoov = selectedMoov;
	}
	public String getSelectedNowTvSpecGroupId() {
		return selectedNowTvSpecGroupId;
	}
	public String getSelectedNowTvPayGroupId() {
		return selectedNowTvPayGroupId;
	}
	public void setSelectedNowTvSpecGroupId(String selectedNowTvSpecGroupId) {
		this.selectedNowTvSpecGroupId = selectedNowTvSpecGroupId;
	}
	public void setSelectedNowTvPayGroupId(String selectedNowTvPayGroupId) {
		this.selectedNowTvPayGroupId = selectedNowTvPayGroupId;
	}
	public List<ItemDetailDTO> getMoovItems() {
		return moovItems;
	}
	public void setMoovItems(List<ItemDetailDTO> moovItems) {
		this.moovItems = moovItems;
	}
	public List<ItemDetailDTO> getOtherItems() {
		return otherItems;
	}
	public void setOtherItems(List<ItemDetailDTO> otherItems) {
		this.otherItems = otherItems;
	}
	public List<ItemDetailDTO> getNowTvSpecItems() {
		return nowTvSpecItems;
	}
	public List<ItemDetailDTO> getNowTvPayItems() {
		return nowTvPayItems;
	}
	public List<ChannelGroupDetailDTO> getSpecChannelGroupList() {
		return specChannelGroupList;
	}
	public List<ChannelGroupDetailDTO> getPayChannelGroupList() {
		return payChannelGroupList;
	}
	public void setNowTvSpecItems(List<ItemDetailDTO> nowTvSpecItems) {
		this.nowTvSpecItems = nowTvSpecItems;
	}
	public void setNowTvPayItems(List<ItemDetailDTO> nowTvPayItems) {
		this.nowTvPayItems = nowTvPayItems;
	}
	public void setSpecChannelGroupList(
			List<ChannelGroupDetailDTO> specChannelGroupList) {
		this.specChannelGroupList = specChannelGroupList;
	}
	public void setPayChannelGroupList(
			List<ChannelGroupDetailDTO> payChannelGroupList) {
		this.payChannelGroupList = payChannelGroupList;
	}
	public boolean isSelectedAdultChannel() {
		return selectedAdultChannel;
	}
	public void setSelectedAdultChannel(boolean selectedAdultChannel) {
		this.selectedAdultChannel = selectedAdultChannel;
	}
	public String getSelectedNowTvSpecChannelId() {
		return selectedNowTvSpecChannelId;
	}
	public String getSelectedNowTvPayChannelId() {
		return selectedNowTvPayChannelId;
	}
	public void setSelectedNowTvSpecChannelId(String selectedNowTvSpecChannelId) {
		this.selectedNowTvSpecChannelId = selectedNowTvSpecChannelId;
	}
	public void setSelectedNowTvPayChannelId(String selectedNowTvPayChannelId) {
		this.selectedNowTvPayChannelId = selectedNowTvPayChannelId;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}

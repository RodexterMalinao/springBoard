package com.bomltsportal.dto;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ItemDetailDTO;

public class DefaultItemListDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1740126073933854319L;
	
	List<ItemDetailDTO> nowTvFreeItemList;
	List<ItemDetailDTO> iddVasItemList;
//	List<ItemDetailDTO> iddOutVasItemList;
	List<ItemDetailDTO> e0060VasItemList;
//	List<ItemDetailDTO> e0060OutVasItemList;
	List<ItemDetailDTO> peFreeItemList;
//	List<ItemDetailDTO> peSocketItemList;
//	List<ItemDetailDTO> optAccessaryItemList;
	List<ItemDetailDTO> installWaiveItemList;
	List<ItemDetailDTO> installItemList;
	
	
	public List<ItemDetailDTO> getNowTvFreeItemList() {
		return nowTvFreeItemList;
	}
	public void setNowTvFreeItemList(List<ItemDetailDTO> nowTvFreeItemList) {
		this.nowTvFreeItemList = nowTvFreeItemList;
	}
	public List<ItemDetailDTO> getIddVasItemList() {
		return iddVasItemList;
	}
	public void setIddVasItemList(List<ItemDetailDTO> iddVasItemList) {
		this.iddVasItemList = iddVasItemList;
	}
	public List<ItemDetailDTO> getE0060VasItemList() {
		return e0060VasItemList;
	}
	public void setE0060VasItemList(List<ItemDetailDTO> e0060VasItemList) {
		this.e0060VasItemList = e0060VasItemList;
	}
	public List<ItemDetailDTO> getPeFreeItemList() {
		return peFreeItemList;
	}
	public void setPeFreeItemList(List<ItemDetailDTO> peFreeItemList) {
		this.peFreeItemList = peFreeItemList;
	}
	public List<ItemDetailDTO> getInstallWaiveItemList() {
		return installWaiveItemList;
	}
	public void setInstallWaiveItemList(List<ItemDetailDTO> installWaiveItemList) {
		this.installWaiveItemList = installWaiveItemList;
	}
	public List<ItemDetailDTO> getInstallItemList() {
		return installItemList;
	}
	public void setInstallItemList(List<ItemDetailDTO> installItemList) {
		this.installItemList = installItemList;
	}
	

}

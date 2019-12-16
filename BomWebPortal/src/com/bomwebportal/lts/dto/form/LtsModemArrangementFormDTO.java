package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LineTypeSelectionDTO;

public class LtsModemArrangementFormDTO implements Serializable{

	private static final long serialVersionUID = -2006372752582110334L;
	
	private Action formAction;
	private ModemType modemType;

	private String nowTvServiceType;
	
	private boolean pcdSbOrderExist = true;
	private String inputPcdSbOrderId;
	private boolean confirmSameCustWithPcdOrder;
	private boolean confirmSameIaWithPcdOrder;
	
	private boolean existingFsaER;
	private boolean existingFsaConfirmSameIa;

	private boolean otherFsaER;
	private boolean otherFsaConfirmSameIa;
	private boolean otherFsaConfirmSameCust;
	
	// SEARCH OTHER FSA CRITIRIA
	private String inputDocType;
	private String inputDocNum;
	private String inputOtherFsa;
	private String inputPcdLoginId;
	private String inputPcdLoginDomain;
	
	private boolean otherFsaExistInSameIa;
	
	private List<FsaDetailDTO> existingFsaList;
	private List<FsaDetailDTO> otherFsaList;
	
	private List<ModemType> modemTypeSelectionList;
	private List<String> errorMsgList;
	private String edfRefNum;
	private boolean edfRefExist = true;
	private List<String> tvTypeList;
	
	private String filterModemType;
	
	private String preSelectFsa;
	
	private boolean isPcdBundleBasket;
	private boolean isPcdBundlePremium;
	
	private List<LineTypeSelectionDTO> lineTypeSelectionList;
	
	private boolean lostModem;

	private boolean rentalRouterExistFsaVas;
	private String rentalRouterInd;
	private List<ItemDetailDTO> rentalRouterItemList;
	
	private String abnormalAddrCoverageInd;
	
	public enum Action {
		SUBMIT, SEARCH_PCD_ORDER, SEARCH_OTHER_FSA, CLEAR_PCD_ORDER, CLEAR_OTHER_FSA, HOME;
	}
	
	public enum ModemType {
		
		SHARE_EX_FSA("lts.acq.modemArrangement.shareExistFSA", "Share Existing FSA"), 
		STANDALONE("lts.acq.modemArrangement.standaloneModem", "Standalone Modem"), 
		SHARE_PCD("lts.acq.modemArrangement.shareNewPCD", "Share New PCD"), 
		SHARE_TV("lts.acq.modemArrangement.shareNewnowTV", "Share New nowTV"), 
		SHARE_BUNDLE("lts.acq.modemArrangement.shareNewPCDNnowTV", "Share New PCD & nowTV"), 
		SHARE_OTHER_FSA("lts.acq.modemArrangement.shareOtherFSA", "Share Other FSA");
		
		private String desc;
		private String engDesc;
		   private ModemType(String desc, String engDesc) {
		       this.desc = desc;
		       this.engDesc = engDesc;
		   }
		   public String getDesc() {
		       return desc;
		   }
		   public String getEngDesc() {
		       return engDesc;
		   }
		   public String getName() {
		       return name();
		   } 
		   
		   @Override
		   public String toString () {
		       return getEngDesc();
		   } 
	}

	public Action getFormAction() {
		return formAction;
	}
	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}
	public ModemType getModemType() {
		return modemType;
	}
	public void setModemType(ModemType modemType) {
		this.modemType = modemType;
	}
	public String getNowTvServiceType() {
		return nowTvServiceType;
	}
	public void setNowTvServiceType(String nowTvServiceType) {
		this.nowTvServiceType = nowTvServiceType;
	}
	public boolean isPcdSbOrderExist() {
		return pcdSbOrderExist;
	}
	public void setPcdSbOrderExist(boolean pcdSbOrderExist) {
		this.pcdSbOrderExist = pcdSbOrderExist;
	}
	public String getInputPcdSbOrderId() {
		return inputPcdSbOrderId;
	}
	public void setInputPcdSbOrderId(String inputPcdSbOrderId) {
		this.inputPcdSbOrderId = inputPcdSbOrderId;
	}
	public String getInputDocType() {
		return inputDocType;
	}
	public void setInputDocType(String inputDocType) {
		this.inputDocType = inputDocType;
	}
	public String getInputDocNum() {
		return inputDocNum;
	}
	public void setInputDocNum(String inputDocNum) {
		this.inputDocNum = inputDocNum;
	}
	public String getInputOtherFsa() {
		return inputOtherFsa;
	}
	public void setInputOtherFsa(String inputOtherFsa) {
		this.inputOtherFsa = inputOtherFsa;
	}
	public String getInputPcdLoginId() {
		return inputPcdLoginId;
	}
	public void setInputPcdLoginId(String inputPcdLoginId) {
		this.inputPcdLoginId = inputPcdLoginId;
	}
	public String getInputPcdLoginDomain() {
		return inputPcdLoginDomain;
	}
	public void setInputPcdLoginDomain(String inputPcdLoginDomain) {
		this.inputPcdLoginDomain = inputPcdLoginDomain;
	}
	public boolean isOtherFsaExistInSameIa() {
		return otherFsaExistInSameIa;
	}
	public void setOtherFsaExistInSameIa(boolean otherFsaExistInSameIa) {
		this.otherFsaExistInSameIa = otherFsaExistInSameIa;
	}
	public boolean isExistingFsaER() {
		return existingFsaER;
	}
	public void setExistingFsaER(boolean existingFsaER) {
		this.existingFsaER = existingFsaER;
	}
	public boolean isExistingFsaConfirmSameIa() {
		return existingFsaConfirmSameIa;
	}
	public void setExistingFsaConfirmSameIa(boolean existingFsaConfirmSameIa) {
		this.existingFsaConfirmSameIa = existingFsaConfirmSameIa;
	}
	public boolean isOtherFsaER() {
		return otherFsaER;
	}
	public void setOtherFsaER(boolean otherFsaER) {
		this.otherFsaER = otherFsaER;
	}
	public boolean isOtherFsaConfirmSameIa() {
		return otherFsaConfirmSameIa;
	}
	public void setOtherFsaConfirmSameIa(boolean otherFsaConfirmSameIa) {
		this.otherFsaConfirmSameIa = otherFsaConfirmSameIa;
	}
	public boolean isOtherFsaConfirmSameCust() {
		return otherFsaConfirmSameCust;
	}
	public void setOtherFsaConfirmSameCust(boolean otherFsaConfirmSameCust) {
		this.otherFsaConfirmSameCust = otherFsaConfirmSameCust;
	}
	public List<FsaDetailDTO> getExistingFsaList() {
		return existingFsaList;
	}
	public void setExistingFsaList(List<FsaDetailDTO> existingFsaList) {
		this.existingFsaList = existingFsaList;
	}
	public List<FsaDetailDTO> getOtherFsaList() {
		return otherFsaList;
	}
	public void setOtherFsaList(List<FsaDetailDTO> otherFsaList) {
		this.otherFsaList = otherFsaList;
	}
	public boolean isConfirmSameCustWithPcdOrder() {
		return confirmSameCustWithPcdOrder;
	}
	public void setConfirmSameCustWithPcdOrder(boolean confirmSameCustWithPcdOrder) {
		this.confirmSameCustWithPcdOrder = confirmSameCustWithPcdOrder;
	}
	public boolean isConfirmSameIaWithPcdOrder() {
		return confirmSameIaWithPcdOrder;
	}
	public void setConfirmSameIaWithPcdOrder(boolean confirmSameIaWithPcdOrder) {
		this.confirmSameIaWithPcdOrder = confirmSameIaWithPcdOrder;
	}
	public List<ModemType> getModemTypeSelectionList() {
		return modemTypeSelectionList;
	}
	public void setModemTypeSelectionList(List<ModemType> modemTypeSelectionList) {
		this.modemTypeSelectionList = modemTypeSelectionList;
	}
	public List<String> getErrorMsgList() {
		return errorMsgList;
	}
	public void setErrorMsgList(List<String> errorMsgList) {
		this.errorMsgList = errorMsgList;
	}
	public String getEdfRefNum() {
		return edfRefNum;
	}
	public void setEdfRefNum(String edfRefNum) {
		this.edfRefNum = edfRefNum;
	}
	public List<String> getTvTypeList() {
		return tvTypeList;
	}
	public void setTvTypeList(List<String> tvTypeList) {
		this.tvTypeList = tvTypeList;
	}
	public boolean isEdfRefExist() {
		return edfRefExist;
	}
	public void setEdfRefExist(boolean edfRefExist) {
		this.edfRefExist = edfRefExist;
	}
	public String getFilterModemType() {
		return filterModemType;
	}
	public void setFilterModemType(String filterModemType) {
		this.filterModemType = filterModemType;
	}
	public String getPreSelectFsa() {
		return preSelectFsa;
	}
	public void setPreSelectFsa(String preSelectFsa) {
		this.preSelectFsa = preSelectFsa;
	}
	public boolean isPcdBundleBasket() {
		return isPcdBundleBasket;
	}
	public void setPcdBundleBasket(boolean isPcdBundleBasket) {
		this.isPcdBundleBasket = isPcdBundleBasket;
	}
	public boolean isPcdBundlePremium() {
		return isPcdBundlePremium;
	}
	public void setPcdBundlePremium(boolean isPcdBundlePremium) {
		this.isPcdBundlePremium = isPcdBundlePremium;
	}
	public List<LineTypeSelectionDTO> getLineTypeSelectionList() {
		return lineTypeSelectionList;
	}
	public void setLineTypeSelectionList(
			List<LineTypeSelectionDTO> lineTypeSelectionList) {
		this.lineTypeSelectionList = lineTypeSelectionList;
	}
	public boolean isLostModem() {
		return lostModem;
	}
	public void setLostModem(boolean lostModem) {
		this.lostModem = lostModem;
	}
	public String getRentalRouterInd() {
		return rentalRouterInd;
	}
	public void setRentalRouterInd(String rentalRouterInd) {
		this.rentalRouterInd = rentalRouterInd;
	}
	public boolean isRentalRouterExistFsaVas() {
		return rentalRouterExistFsaVas;
	}
	public void setRentalRouterExistFsaVas(boolean rentalRouterExistFsaVas) {
		this.rentalRouterExistFsaVas = rentalRouterExistFsaVas;
	}
	public List<ItemDetailDTO> getRentalRouterItemList() {
		return rentalRouterItemList;
	}
	public void setRentalRouterItemList(List<ItemDetailDTO> rentalRouterItemList) {
		this.rentalRouterItemList = rentalRouterItemList;
	}
	public String getAbnormalAddrCoverageInd() {
		return abnormalAddrCoverageInd;
	}
	public void setAbnormalAddrCoverageInd(String abnormalAddrCoverageInd) {
		this.abnormalAddrCoverageInd = abnormalAddrCoverageInd;
	}
	
}

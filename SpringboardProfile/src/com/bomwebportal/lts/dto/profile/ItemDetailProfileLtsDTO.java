package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import com.bomwebportal.lts.dto.ItemDetailDTO;

public class ItemDetailProfileLtsDTO implements Serializable {

	private static final long serialVersionUID = 8493480362187985395L;
	
	private String conditionEffStartDate = null;
	private String conditionEffEndDate = null;
	private String remainContractMonth = null;
	private double tpExpiredMonths = 0;
	private String iddFreeMin = null;
	private String itemId = null;
	private String changeToItemId = null;
	private String termExistCdInd = null;
	private String itemType = null;
	private ItemDetailDTO itemDetail = null;
	private String itemNature = null;
	private String promotionStartDate = null;
	private String promotionEndDate = null;
	private String promotionExpiredMonths = null;
	private boolean isPaidVas = true;
	private boolean freeVasInd = false;
	private String tpPremium = null;
	private List<OfferDetailProfileLtsDTO> offerList = new ArrayList<OfferDetailProfileLtsDTO>();
	private List<ItemActionLtsDTO> itemActionList = new ArrayList<ItemActionLtsDTO>();
	private boolean isVoiceItem = false;
	private boolean isDuplexItem = false;
	private boolean isForceRetain = false;
	
	
	public void addOffer(OfferDetailProfileLtsDTO pOffer) {
		this.offerList.add(pOffer);
	}
	
	public OfferDetailProfileLtsDTO[] getOffers() {
		return offerList.toArray(new OfferDetailProfileLtsDTO[offerList.size()]);
	}

	public void setOffers(OfferDetailProfileLtsDTO[] offers) {
		
		if (ArrayUtils.isEmpty(offers)) {
			return;
		}
		this.offerList = new ArrayList<OfferDetailProfileLtsDTO>();
		this.offerList.addAll(Arrays.asList(offers));
	}
	
	public OfferDetailProfileLtsDTO getOfferByOfferType(String pOfferType) {
		
		for (int i=0; this.offerList!=null && i<this.offerList.size(); ++i) {
			if (StringUtils.equals(this.offerList.get(i).getOfferType(), pOfferType)) {
				return this.offerList.get(i);
			}
		}
		return null;
	}

	public String getConditionEffStartDate() {
		return conditionEffStartDate;
	}

	public void setConditionEffStartDate(String conditionEffStartDate) {
		this.conditionEffStartDate = conditionEffStartDate;
	}

	public String getConditionEffEndDate() {
		return conditionEffEndDate;
	}

	public void setConditionEffEndDate(String conditionEffEndDate) {
		this.conditionEffEndDate = conditionEffEndDate;
	}
	
	public String getIddFreeMin() {
		return iddFreeMin;
	}

	public void setIddFreeMin(String iddFreeMin) {
		this.iddFreeMin = iddFreeMin;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getChangeToItemId() {
		return changeToItemId;
	}

	public void setChangeToItemId(String changeToItemId) {
		this.changeToItemId = changeToItemId;
	}

	public String getTermExistCdInd() {
		return termExistCdInd;
	}

	public void setTermExistCdInd(String termExistCdInd) {
		this.termExistCdInd = termExistCdInd;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public ItemDetailDTO getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(ItemDetailDTO itemDetail) {
		this.itemDetail = itemDetail;
	}

	public String getItemNature() {
		return this.itemNature;
	}

	public void setItemNature(String pItemNature) {
		this.itemNature = pItemNature;
	}

	public String getPromotionStartDate() {
		return this.promotionStartDate;
	}

	public void setPromotionStartDate(String pPromotionStartDate) {
		this.promotionStartDate = pPromotionStartDate;
	}

	public String getPromotionEndDate() {
		return this.promotionEndDate;
	}

	public void setPromotionEndDate(String pPromotionEndDate) {
		this.promotionEndDate = pPromotionEndDate;
	}

	public boolean isPaidVas() {
		return this.isPaidVas;
	}

	public void setPaidVas(boolean pIsPaidVas) {
		this.isPaidVas = pIsPaidVas;
	}

	public String getPromotionExpiredMonths() {
		return this.promotionExpiredMonths;
	}

	public void setPromotionExpiredMonths(String pPromotionExpiredMonths) {
		this.promotionExpiredMonths = pPromotionExpiredMonths;
	}

	public double getTpExpiredMonths() {
		return tpExpiredMonths;
	}

	public void setTpExpiredMonths(double tpExpiredMonths) {
		this.tpExpiredMonths = tpExpiredMonths;
	}

	public boolean isFreeVasInd() {
		return freeVasInd;
	}

	public void setFreeVasInd(boolean freeVasInd) {
		this.freeVasInd = freeVasInd;
	}

	public String getTpPremium() {
		return tpPremium;
	}

	public void setTpPremium(String tpPremium) {
		this.tpPremium = tpPremium;
	}

	public boolean isVoiceItem() {
		return isVoiceItem;
	}

	public void setVoiceItem(boolean isVoiceItem) {
		this.isVoiceItem = isVoiceItem;
	}

	public ItemActionLtsDTO[] getItemActions() {
		return this.itemActionList.toArray(new ItemActionLtsDTO[this.itemActionList.size()]);
	}

	public void setItemActions(ItemActionLtsDTO[] itemActions) {
		
		if (ArrayUtils.isEmpty(itemActions)) {
			return;
		}
		this.itemActionList = new ArrayList<ItemActionLtsDTO>();
		this.itemActionList.addAll(Arrays.asList(itemActions));
	}
	
	public void addItemAction(ItemActionLtsDTO pItemAction) {
		this.itemActionList.add(pItemAction);
	}
	
	public ItemActionLtsDTO getItemActionByToProd(String pToProd) {
		
		if (this.itemActionList == null) {
			return null;
		}
		for (int i=0; i<this.itemActionList.size(); ++i) {
			if (StringUtils.equalsIgnoreCase(this.itemActionList.get(i).getToProd(), pToProd)) {
				return this.itemActionList.get(i);
			}
		}
		return null;
	}

	public String getRemainContractMonth() {
		try{
			if(StringUtils.isNotBlank(conditionEffStartDate) && StringUtils.isNotBlank(conditionEffEndDate)){
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
				Date nowDate = new Date();
				Date endDate = DateUtils.addDays(df.parse(conditionEffEndDate), 1);
				
				DateTime start = new DateTime(nowDate.getTime());
				DateTime end = new DateTime(endDate.getTime());
				
				Period period = new Period(start, end, PeriodType.yearMonthDay()); 
				
				StringBuilder sb = new StringBuilder(period.getYears() + "y ");
				sb.append(period.getMonths() + "m ");
				sb.append(period.getDays() + "d");
				
				return sb.toString();

			}
		}catch(Exception e){
			return remainContractMonth;
		}
		return remainContractMonth;
	}

	public void setRemainContractMonth(String remainContractMonth) {
		this.remainContractMonth = remainContractMonth;
	}

	public boolean isDuplexItem() {
		return isDuplexItem;
	}

	public void setDuplexItem(boolean isDuplexItem) {
		this.isDuplexItem = isDuplexItem;
	}

	public boolean isForceRetain() {
		return isForceRetain;
	}

	public void setForceRetain(boolean isForceRetain) {
		this.isForceRetain = isForceRetain;
	}
	
	
}

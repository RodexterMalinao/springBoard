package com.bomwebportal.dto;

import java.io.Serializable;

public class ItemOfferDTO implements Serializable {

	public String getSourceString() {
		return sourceString;
	}
	public void setSourceString(String sourceString) {
		this.sourceString = sourceString;
	}
	private static final long serialVersionUID = 8536979993274362436L;
	
	private 	String	parsingMessage	;
	private 	String	sourceString;
	//W_ITEM_OFFER_ASSGN
	private 	int	itemId	;//	W_ITEM_OFFER_ASSGN.ITEM_ID	NUMBER	22	N
	private 	int	itemOfferSeq	;//	W_ITEM_OFFER_ASSGN.ITEM_OFFER_SEQ	NUMBER	22	N
	private 	int	offerId	;//	W_ITEM_OFFER_ASSGN.OFFER_ID	NUMBER	22	Y
	private 	int	offerSubId	;//	W_ITEM_OFFER_ASSGN.OFFER_SUB_ID	NUMBER	22	Y
	private 	String	offerType	;//	W_ITEM_OFFER_ASSGN.OFFER_TYPE	VARCHAR2	10	Y
	private 	int	selectQty	;//	W_ITEM_OFFER_ASSGN.SELECT_QTY	NUMBER	22	Y
	//private 	String	createBy	;//	W_ITEM_OFFER_ASSGN.CREATE_BY	VARCHAR2	10	Y, sql insert
	//private 	String	createDate	;//	W_ITEM_OFFER_ASSGN.CREATE_DATE	DATE	7	Y, sql insert
	
	//W_ITEM_OFFER_PRODUCT_ASSGN
	//private 	int	itemId	;//	W_ITEM_OFFER_PRODUCT_ASSGN.	ITEM_ID	NUMBER	22	N
	//private 	int	itemOfferSeq	;//	W_ITEM_OFFER_PRODUCT_ASSGN.	ITEM_OFFER_SEQ	NUMBER	22	N
	private 	int	itemProductSeq	;//	W_ITEM_OFFER_PRODUCT_ASSGN.	ITEM_PRODUCT_SEQ	NUMBER	22	N
	private 	int	productId	;//	W_ITEM_OFFER_PRODUCT_ASSGN.	PRODUCT_ID	NUMBER	22	Y
	private 	String	productType	;//	W_ITEM_OFFER_PRODUCT_ASSGN.	PRODUCT_TYPE	VARCHAR2	20	Y
	//private 	int	selectQty	;//	W_ITEM_OFFER_PRODUCT_ASSGN.	SELECT_QTY	NUMBER	22	Y
	//private 	String	createBy	;//	W_ITEM_OFFER_PRODUCT_ASSGN.	CREATE_BY	VARCHAR2	10	Y
	//private 	String	createDate	;//	W_ITEM_OFFER_PRODUCT_ASSGN.	CREATE_DATE	DATE	7	N


	public int getItemProductSeq() {
		return itemProductSeq;
	}
	public void setItemProductSeq(int itemProductSeq) {
		this.itemProductSeq = itemProductSeq;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getItemOfferSeq() {
		return itemOfferSeq;
	}
	public void setItemOfferSeq(int itemOfferSeq) {
		this.itemOfferSeq = itemOfferSeq;
	}
	public int getOfferId() {
		return offerId;
	}
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	public int getOfferSubId() {
		return offerSubId;
	}
	public void setOfferSubId(int offerSubId) {
		this.offerSubId = offerSubId;
	}
	public String getOfferType() {
		return offerType;
	}
	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}
	public int getSelectQty() {
		return selectQty;
	}
	public void setSelectQty(int selectQty) {
		this.selectQty = selectQty;
	}
	
	public String getTableRowString() {
		StringBuilder temp = new  StringBuilder();
		if (parsingMessage!=null){
				temp.append("<tr BGCOLOR=\"#FF0000\">"); //if have error show red in this line
			
		}else{
			temp.append("<tr >");
			
		}
		
		//temp.append("<tr BGCOLOR=\"#99CCFF\">");
	
		temp.append("<td>"+itemId+"</td>");
		temp.append("<td>"+itemOfferSeq+"</td>");
		temp.append("<td>"+offerId+"</td>");
		temp.append("<td>"+offerSubId+"</td>");
		temp.append("<td>"+offerType+"</td>");
		temp.append("<td>"+selectQty+"</td>");
		temp.append("<td>"+itemProductSeq+"</td>");
		temp.append("<td>"+productId+"</td>");
		temp.append("<td>"+productType+"</td>");
		temp.append("<td>"+parsingMessage+"</td>");
		temp.append("<td>"+sourceString+"</td>");
		temp.append("</tr>");
		
		return temp.toString();

	}
	public void setParsingMessage(String parsingMessage) {
		this.parsingMessage = parsingMessage;
	}
	public String getParsingMessage() {
		return parsingMessage;
	}
	
	


}

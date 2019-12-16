package com.bomwebportal.ims.service;

import java.util.Date;

import com.bomwebportal.ims.dto.ui.AcctUI;
import com.bomwebportal.ims.dto.ui.AddrInventoryUI;
import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.ContactUI;
import com.bomwebportal.ims.dto.ui.CustAddrUI;
import com.bomwebportal.ims.dto.ui.CustOptoutInfoUI;
import com.bomwebportal.ims.dto.ui.CustomerUI;
import com.bomwebportal.ims.dto.ui.InstallAddrUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.PaymentUI;
import com.bomwebportal.ims.dto.ui.RemarkUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;

public class DevService {		
	
	public static OrderImsUI getDummyOrder(){
		
		OrderImsUI order = new OrderImsUI();
		order.setActionInd("");
		
		order.setOrderId("TCP00005");
		order.setLoginId("springtest01");
		order.setDecodeType("SD");
		order.setAdultViewAllow("Y");
		//order.setTargetCommDate(new Date()); //trunc
		//order.setShopCd("28888888");
		order.setPrepayAmt("800");
		order.setMovingChrg("640");
		order.setWaivedPrepay("N");
		order.setCashFsPrepay("N");
		order.setNowTvFormType("PCDTV");
		order.setFixedLineNo("28888888");
		order.setIsCreditCardOffer("Y");
		order.setLangPreference("ENG"); 
		order.setProcessVim("Y");
		order.setProcessVms("Y");
		order.setProcessWifi("Y");
		order.setProcessCreditCard("Y");
		
		order.setShopCd("TP1");
		//order.setSrvReqDate(new Date()); //trunc
		//order.setAppDate(new Date());
		order.setSalesCd("1253632");
		order.setOrderStatus("SIGNOFF");
		order.setSalesName("Tommy Chu");
		order.setLob("IMS");
		//order.setSignOffDate(new Date());			
		
		order.setAppointment(createDummyAppointment());
		order.setCustomer(createDummyCustomer());
		order.setBillingAddress(createDummyBillingAddr());
		order.setInstallAddress(createDummyInstallAddr());
		order.setRemarks(createDummyRemarks());
		order.setSubscribedItems(createDummySubscribedItems());
		
		return order;
	}
	
	public static AppointmentUI createDummyAppointment(){
		AppointmentUI appointment = new AppointmentUI();
		appointment.setActionInd("");
		
		appointment.setSerialNum("BG757310");
		appointment.setAppntStartDate(new Date()); //7/26/2011 9:00:00 AM
		appointment.setAppntEndDate(new Date()); //7/26/2011 11:00:00 AM
		appointment.setInstContactName("Jerry");
		appointment.setInstContactNum("91234567");
		appointment.setInstSmsNum("98765432");
		
		return appointment;
	}
	
	public static RemarkUI[] createDummyRemarks(){
		
		RemarkUI[] remarks = new RemarkUI[1];
		RemarkUI remark = new RemarkUI();
		remark.setActionInd("");
		remark.setRmkType("IA");
		remark.setRmkSeq("1");
		remark.setRmkDtl("Testing install remark");
		remarks[0] = remark;
		
		return remarks;
	}
	
	public static CustomerUI createDummyCustomer(){
		CustomerUI customer = new CustomerUI();
		customer.setActionInd("");
		
		customer.setFirstName("Tak Man");
		customer.setLastName("Chan");
		customer.setIdDocType("HKID");
		customer.setIdDocNum("A123456(7)");
		customer.setDob(new Date()); //trunc
		customer.setTitle("MR");
		customer.setLob("IMS");
		customer.setIdVerified("Y");
		customer.setBlacklistInd("N");
		
		customer.setAccount(createDummyAccout());
		customer.setCustOptInfo(createDummyCustOptout());
		customer.setContact(createDummyContact());
		
		return customer;
	}
	
	public static AcctUI createDummyAccout(){
		AcctUI account = new AcctUI();
		account.setActionInd("");
		
		account.setEmailAddr("takman@hotmail.com");
		account.setBillMedia("E");
		account.setPayment(createDummyPayment());
		
		return account;
	}
	
	public static PaymentUI createDummyPayment(){
		
		PaymentUI payment = new PaymentUI();
		payment.setActionInd("");
		
		payment.setPayMtdKey("");
		payment.setPayMtdType("C"); //M-cash, C-credit card
		payment.setThirdPartyInd("Y");
		payment.setCcType("V"); //V-visa, M-master
		payment.setCcNum("411111AAAADk1111");
		payment.setCcHoldName("SPRING TEST 01");
		payment.setCcExpDate("08/2015");
		payment.setCcIdDocNo("H196517(6)");
		payment.setCcIdDocType("HKID");
		payment.setCcVerified("Y");
		
		return payment;
	}
	
	public static CustOptoutInfoUI createDummyCustOptout(){
		CustOptoutInfoUI optout = new CustOptoutInfoUI();
		optout.setActionInd("");
		
		optout.setCustNo(" "); //temp use
		optout.setDirectMailing("Y");
		optout.setOutbound("N");
		optout.setSms("Y");
		optout.setEmail("N");
		optout.setWebBill("Y");
		optout.setNonsalesSms("N");
		optout.setInternet("Y");
		
		return optout;
	}
	
	public static ContactUI createDummyContact(){
		ContactUI contact = new ContactUI();
		contact.setActionInd("");
		
		contact.setContactPhone("91111111"); //mobile
		
		return contact;
	}
	
	public static CustAddrUI createDummyBillingAddr(){
		CustAddrUI billAddr = new CustAddrUI();
		billAddr.setActionInd("");
		
		billAddr.setAddrUsage("BA");
		billAddr.setFlat("37");
		billAddr.setAreaCd("NT");
		billAddr.setSectCd("CYET");
		billAddr.setStrName("");
		billAddr.setHiLotNo("");
		billAddr.setStrCatCd("");
		billAddr.setBuildNo("");
		billAddr.setForeignAddrFlag("N");		
		billAddr.setFloorNo("7");
		billAddr.setUnitNo("731");		
		billAddr.setPoBoxNo("");
		billAddr.setAddrType("S");		
		billAddr.setStrNo("");
		billAddr.setAreaDesc("NEW TERRITORIES");		
		billAddr.setDistDesc("SHEUNG SHUI");
		billAddr.setSectDesc("CHOI YUEN EST");
		billAddr.setSerbdyno("");		
		billAddr.setBlacklistInd("");								
		
		return billAddr;
	}
	
	
	public static InstallAddrUI createDummyInstallAddr(){
		InstallAddrUI installAddr = new InstallAddrUI();
		installAddr.setActionInd("");
		
		installAddr.setAddrUsage("IA");
		installAddr.setFlat("");
		installAddr.setAreaCd("HK");
		installAddr.setSectCd("ZZZZ");
		installAddr.setStrName("PRINCE'S");
		installAddr.setHiLotNo("5");
		installAddr.setStrCatCd("TER");
		installAddr.setBuildNo("");
		installAddr.setForeignAddrFlag("N");		
		installAddr.setFloorNo("");
		installAddr.setUnitNo("");		
		installAddr.setPoBoxNo("");
		installAddr.setAddrType("S");		
		installAddr.setStrNo("");
		installAddr.setAreaDesc("NEW TERRITORIES");		
		installAddr.setDistDesc("MID-LEVELS");
		installAddr.setSectDesc("CHOI YUEN EST");
		installAddr.setSerbdyno("158288");		
		installAddr.setBlacklistInd("Y");
		
		installAddr.setAddrInventory(createDummyInstallInventory());
		
		return installAddr;
	}
	
	public static AddrInventoryUI createDummyInstallInventory(){
		AddrInventoryUI inventory = new AddrInventoryUI();
		inventory.setActionInd("");
		
		inventory.setAddrUsage("IA");
		inventory.setResourceShortage("Y");
		inventory.setTechnology("PON"); //PON, ADSL, VDSL
		inventory.setBuildingType("HGC");
		inventory.setFieldPermitDay("14");
		inventory.setN2BuildingInd("N");		
		
		return inventory;
	}
	
	public static SubscribedItemUI[] createDummySubscribedItems(){
		SubscribedItemUI[] items = new SubscribedItemUI[4];
		
		SubscribedItemUI item1 = new SubscribedItemUI();
		item1.setActionInd("");
		item1.setBasketId("200100024");
		item1.setType("PROG");
		item1.setId("201000065");
		
		SubscribedItemUI item2 = new SubscribedItemUI();
		item2.setActionInd("");
		item2.setBasketId("200100024");
		item2.setType("BVAS");
		item2.setId("201000066");
				
		SubscribedItemUI item3 = new SubscribedItemUI();
		item3.setActionInd("");
		item3.setBasketId("200100024");
		item3.setType("BVAS");
		item3.setId("201000067");
		
		SubscribedItemUI item4 = new SubscribedItemUI();
		item4.setActionInd("");
		item4.setBasketId("200100024");
		item4.setType("BVAS");
		item4.setId("201000068");
		
		items[0] = item1;
		items[1] = item2;
		items[2] = item3;
		items[3] = item4;
		
		return items;
	}
	
	public static OrderImsUI getNewOrder(){
		
		OrderImsUI newOrder = new OrderImsUI();
		
		AppointmentUI appointment = new AppointmentUI();
		appointment.setActionInd("A");
		newOrder.setAppointment(appointment);
		
		RemarkUI remark = new RemarkUI();
		remark.setActionInd("A");
		RemarkUI[] remarks = new RemarkUI[1];
		remarks[0] = remark;				
		newOrder.setRemarks(remarks);
		/*
		CustAddrUI billingAddress = new CustAddrUI();
		billingAddress.setActionInd("A");
		billingAddress.setAddrUsage("BA");
		newOrder.setBillingAddress(billingAddress);
		*/
		InstallAddrUI installAddress = new InstallAddrUI();
		installAddress.setActionInd("A");
		installAddress.setAddrUsage("IA");
		newOrder.setInstallAddress(installAddress);

		CustomerUI customer = new CustomerUI();
		customer.setActionInd("A");
		
		AcctUI account = new AcctUI();
		account.setActionInd("A");
		customer.setAccount(account);
		/*
		CustOptoutInfoUI optout = new CustOptoutInfoUI();
		optout.setActionInd("A");
		customer.setCustOptInfo(optout);
		*/
		ContactUI contact = new ContactUI();
		contact.setActionInd("A");
		customer.setContact(contact);
		
		newOrder.setCustomer(customer);
		
		return newOrder;
	}

}

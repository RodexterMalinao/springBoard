
package com.pccw.custProfileGateway.acctInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="MobSmsAlert" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressMaintDTO" type="{http://www.openuri.org/}AddressMaintDTO" minOccurs="0"/>
 *         &lt;element name="ExistingPaymentMethodDTO" type="{http://www.openuri.org/}PaymentMethodDTO" minOccurs="0"/>
 *         &lt;element name="FuturePaymentMethodDTO" type="{http://www.openuri.org/}PaymentMethodDTO" minOccurs="0"/>
 *         &lt;element name="PayMethodChangeInd" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="BillPeriodChangeInd" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SecBillName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttnBillName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CrstRemarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Supstafg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Supendfg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImsRowSeqNbCustblac" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BillDetailsInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcctName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcctNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcctStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcctSubType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcctType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AutopayUpperLimitAmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BadDebtInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BadDebtStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillEnqInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillFmtInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillFreq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillingAddrId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillLang" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillPeriod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillStsDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BlacklistInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CardPackInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcCreateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChiNameCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditLimit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewCreditStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistingCreditStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CredMngtHoldDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CredMngtHoldRdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CredMngtHoldRemark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CurrOSAdverAmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CurrOSHktAmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustCatg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DemnoteStsCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DonationRoundingAmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DtlCCCreateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmailAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FinalAcctDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImsRowSeqNb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InvGenBefInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InvToBdas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastInvPaymentDueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdProcess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LtsRowSeqNb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MailingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ManualSuppEndMth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ManualSuppStMth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NetvigatorInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrintCrLimitInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RentBillToDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RowSeqNb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SortOption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SuppCnt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TentIdNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TentIdType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WrittenOffAmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PendingBillPeriod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TariffGrp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubrAGROnlyInd" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MobRowSeqNb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="StatusReaCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SmsNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RetMailDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ccc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SplitAcctCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Poid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InitDunGrp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MobLastUpdDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MobLastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MobCcCreateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MobLastUpdProcess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MobBillMediaDTOArray" type="{http://www.openuri.org/}ArrayOfMOBBillMediaDTO" minOccurs="0"/>
 *         &lt;element name="ShopCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistBillDay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NextBillDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastBillDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PendEffBillDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Channel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PoolEffDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PoolTermDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisplayPoolEffDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisplayPoolTermDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PoolIndType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="E0060CreditLimit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaperBill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OnLanAtWork" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PortalErrorText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcctStatusDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EBillAgent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RedemMedia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountDTO", propOrder = {
    "mobSmsAlert",
    "addressMaintDTO",
    "existingPaymentMethodDTO",
    "futurePaymentMethodDTO",
    "payMethodChangeInd",
    "billPeriodChangeInd",
    "secBillName",
    "attnBillName",
    "crstRemarks",
    "supstafg",
    "supendfg",
    "imsRowSeqNbCustblac",
    "billDetailsInd",
    "acctName",
    "acctNum",
    "acctStatus",
    "acctSubType",
    "acctType",
    "autopayUpperLimitAmt",
    "badDebtInd",
    "badDebtStatus",
    "billEnqInd",
    "billFmtInd",
    "billFreq",
    "billingAddrId",
    "billLang",
    "billPeriod",
    "billStsDate",
    "blacklistInd",
    "cardPackInd",
    "ccCreateDate",
    "chiNameCd",
    "creditClass",
    "creditLimit",
    "creditStartDate",
    "newCreditStatus",
    "existingCreditStatus",
    "credMngtHoldDate",
    "credMngtHoldRdate",
    "credMngtHoldRemark",
    "currOSAdverAmt",
    "currOSHktAmt",
    "custCatg",
    "custNum",
    "demnoteStsCd",
    "donationRoundingAmt",
    "dtlCCCreateDate",
    "effEndDate",
    "effStartDate",
    "emailAddr",
    "finalAcctDate",
    "imsRowSeqNb",
    "invGenBefInd",
    "invToBdas",
    "lastInvPaymentDueDate",
    "lastUpdProcess",
    "lastUpdBy",
    "ltsRowSeqNb",
    "mailingName",
    "manualSuppEndMth",
    "manualSuppStMth",
    "netvigatorInd",
    "printCrLimitInd",
    "remarks",
    "rentBillToDate",
    "rowSeqNb",
    "sortOption",
    "suppCnt",
    "systemId",
    "tentIdNum",
    "tentIdType",
    "tentName",
    "writtenOffAmt",
    "pendingBillPeriod",
    "tariffGrp",
    "subrAGROnlyInd",
    "mobRowSeqNb",
    "statusReaCd",
    "smsNum",
    "retMailDate",
    "ccc",
    "splitAcctCode",
    "poid",
    "initDunGrp",
    "mobLastUpdDate",
    "mobLastUpdBy",
    "mobCcCreateDate",
    "mobLastUpdProcess",
    "mobBillMediaDTOArray",
    "shopCode",
    "existBillDay",
    "nextBillDate",
    "lastBillDate",
    "pendEffBillDate",
    "channel",
    "poolEffDate",
    "poolTermDate",
    "displayPoolEffDate",
    "displayPoolTermDate",
    "poolIndType",
    "e0060CreditLimit",
    "paperBill",
    "onLanAtWork",
    "portalErrorText",
    "acctStatusDate",
    "eBillAgent",
    "redemMedia"
})
public class AccountDTO
    extends ValueObject
{

    @XmlElement(name = "MobSmsAlert")
    protected String mobSmsAlert;
    @XmlElement(name = "AddressMaintDTO")
    protected AddressMaintDTO addressMaintDTO;
    @XmlElement(name = "ExistingPaymentMethodDTO")
    protected PaymentMethodDTO existingPaymentMethodDTO;
    @XmlElement(name = "FuturePaymentMethodDTO")
    protected PaymentMethodDTO futurePaymentMethodDTO;
    @XmlElement(name = "PayMethodChangeInd")
    protected boolean payMethodChangeInd;
    @XmlElement(name = "BillPeriodChangeInd")
    protected boolean billPeriodChangeInd;
    @XmlElement(name = "SecBillName")
    protected String secBillName;
    @XmlElement(name = "AttnBillName")
    protected String attnBillName;
    @XmlElement(name = "CrstRemarks")
    protected String crstRemarks;
    @XmlElement(name = "Supstafg")
    protected String supstafg;
    @XmlElement(name = "Supendfg")
    protected String supendfg;
    @XmlElement(name = "ImsRowSeqNbCustblac")
    protected int imsRowSeqNbCustblac;
    @XmlElement(name = "BillDetailsInd")
    protected String billDetailsInd;
    @XmlElement(name = "AcctName")
    protected String acctName;
    @XmlElement(name = "AcctNum")
    protected String acctNum;
    @XmlElement(name = "AcctStatus")
    protected String acctStatus;
    @XmlElement(name = "AcctSubType")
    protected String acctSubType;
    @XmlElement(name = "AcctType")
    protected String acctType;
    @XmlElement(name = "AutopayUpperLimitAmt")
    protected String autopayUpperLimitAmt;
    @XmlElement(name = "BadDebtInd")
    protected String badDebtInd;
    @XmlElement(name = "BadDebtStatus")
    protected String badDebtStatus;
    @XmlElement(name = "BillEnqInd")
    protected String billEnqInd;
    @XmlElement(name = "BillFmtInd")
    protected String billFmtInd;
    @XmlElement(name = "BillFreq")
    protected String billFreq;
    @XmlElement(name = "BillingAddrId")
    protected String billingAddrId;
    @XmlElement(name = "BillLang")
    protected String billLang;
    @XmlElement(name = "BillPeriod")
    protected String billPeriod;
    @XmlElement(name = "BillStsDate")
    protected String billStsDate;
    @XmlElement(name = "BlacklistInd")
    protected String blacklistInd;
    @XmlElement(name = "CardPackInd")
    protected String cardPackInd;
    @XmlElement(name = "CcCreateDate")
    protected String ccCreateDate;
    @XmlElement(name = "ChiNameCd")
    protected String chiNameCd;
    @XmlElement(name = "CreditClass")
    protected String creditClass;
    @XmlElement(name = "CreditLimit")
    protected String creditLimit;
    @XmlElement(name = "CreditStartDate")
    protected String creditStartDate;
    @XmlElement(name = "NewCreditStatus")
    protected String newCreditStatus;
    @XmlElement(name = "ExistingCreditStatus")
    protected String existingCreditStatus;
    @XmlElement(name = "CredMngtHoldDate")
    protected String credMngtHoldDate;
    @XmlElement(name = "CredMngtHoldRdate")
    protected String credMngtHoldRdate;
    @XmlElement(name = "CredMngtHoldRemark")
    protected String credMngtHoldRemark;
    @XmlElement(name = "CurrOSAdverAmt")
    protected String currOSAdverAmt;
    @XmlElement(name = "CurrOSHktAmt")
    protected String currOSHktAmt;
    @XmlElement(name = "CustCatg")
    protected String custCatg;
    @XmlElement(name = "CustNum")
    protected String custNum;
    @XmlElement(name = "DemnoteStsCd")
    protected String demnoteStsCd;
    @XmlElement(name = "DonationRoundingAmt")
    protected String donationRoundingAmt;
    @XmlElement(name = "DtlCCCreateDate")
    protected String dtlCCCreateDate;
    @XmlElement(name = "EffEndDate")
    protected String effEndDate;
    @XmlElement(name = "EffStartDate")
    protected String effStartDate;
    @XmlElement(name = "EmailAddr")
    protected String emailAddr;
    @XmlElement(name = "FinalAcctDate")
    protected String finalAcctDate;
    @XmlElement(name = "ImsRowSeqNb")
    protected String imsRowSeqNb;
    @XmlElement(name = "InvGenBefInd")
    protected String invGenBefInd;
    @XmlElement(name = "InvToBdas")
    protected String invToBdas;
    @XmlElement(name = "LastInvPaymentDueDate")
    protected String lastInvPaymentDueDate;
    @XmlElement(name = "LastUpdProcess")
    protected String lastUpdProcess;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    @XmlElement(name = "LtsRowSeqNb")
    protected String ltsRowSeqNb;
    @XmlElement(name = "MailingName")
    protected String mailingName;
    @XmlElement(name = "ManualSuppEndMth")
    protected String manualSuppEndMth;
    @XmlElement(name = "ManualSuppStMth")
    protected String manualSuppStMth;
    @XmlElement(name = "NetvigatorInd")
    protected String netvigatorInd;
    @XmlElement(name = "PrintCrLimitInd")
    protected String printCrLimitInd;
    @XmlElement(name = "Remarks")
    protected String remarks;
    @XmlElement(name = "RentBillToDate")
    protected String rentBillToDate;
    @XmlElement(name = "RowSeqNb")
    protected String rowSeqNb;
    @XmlElement(name = "SortOption")
    protected String sortOption;
    @XmlElement(name = "SuppCnt")
    protected String suppCnt;
    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "TentIdNum")
    protected String tentIdNum;
    @XmlElement(name = "TentIdType")
    protected String tentIdType;
    @XmlElement(name = "TentName")
    protected String tentName;
    @XmlElement(name = "WrittenOffAmt")
    protected String writtenOffAmt;
    @XmlElement(name = "PendingBillPeriod")
    protected String pendingBillPeriod;
    @XmlElement(name = "TariffGrp")
    protected String tariffGrp;
    @XmlElement(name = "SubrAGROnlyInd")
    protected boolean subrAGROnlyInd;
    @XmlElement(name = "MobRowSeqNb")
    protected int mobRowSeqNb;
    @XmlElement(name = "StatusReaCd")
    protected String statusReaCd;
    @XmlElement(name = "SmsNum")
    protected String smsNum;
    @XmlElement(name = "RetMailDate")
    protected String retMailDate;
    @XmlElement(name = "Ccc")
    protected String ccc;
    @XmlElement(name = "SplitAcctCode")
    protected String splitAcctCode;
    @XmlElement(name = "Poid")
    protected String poid;
    @XmlElement(name = "InitDunGrp")
    protected String initDunGrp;
    @XmlElement(name = "MobLastUpdDate")
    protected String mobLastUpdDate;
    @XmlElement(name = "MobLastUpdBy")
    protected String mobLastUpdBy;
    @XmlElement(name = "MobCcCreateDate")
    protected String mobCcCreateDate;
    @XmlElement(name = "MobLastUpdProcess")
    protected String mobLastUpdProcess;
    @XmlElement(name = "MobBillMediaDTOArray")
    protected ArrayOfMOBBillMediaDTO mobBillMediaDTOArray;
    @XmlElement(name = "ShopCode")
    protected String shopCode;
    @XmlElement(name = "ExistBillDay")
    protected String existBillDay;
    @XmlElement(name = "NextBillDate")
    protected String nextBillDate;
    @XmlElement(name = "LastBillDate")
    protected String lastBillDate;
    @XmlElement(name = "PendEffBillDate")
    protected String pendEffBillDate;
    @XmlElement(name = "Channel")
    protected String channel;
    @XmlElement(name = "PoolEffDate")
    protected String poolEffDate;
    @XmlElement(name = "PoolTermDate")
    protected String poolTermDate;
    @XmlElement(name = "DisplayPoolEffDate")
    protected String displayPoolEffDate;
    @XmlElement(name = "DisplayPoolTermDate")
    protected String displayPoolTermDate;
    @XmlElement(name = "PoolIndType")
    protected String poolIndType;
    @XmlElement(name = "E0060CreditLimit")
    protected String e0060CreditLimit;
    @XmlElement(name = "PaperBill")
    protected String paperBill;
    @XmlElement(name = "OnLanAtWork")
    protected String onLanAtWork;
    @XmlElement(name = "PortalErrorText")
    protected String portalErrorText;
    @XmlElement(name = "AcctStatusDate")
    protected String acctStatusDate;
    @XmlElement(name = "EBillAgent")
    protected String eBillAgent;
    @XmlElement(name = "RedemMedia")
    protected String redemMedia;

    /**
     * Gets the value of the mobSmsAlert property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobSmsAlert() {
        return mobSmsAlert;
    }

    /**
     * Sets the value of the mobSmsAlert property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobSmsAlert(String value) {
        this.mobSmsAlert = value;
    }

    /**
     * Gets the value of the addressMaintDTO property.
     * 
     * @return
     *     possible object is
     *     {@link AddressMaintDTO }
     *     
     */
    public AddressMaintDTO getAddressMaintDTO() {
        return addressMaintDTO;
    }

    /**
     * Sets the value of the addressMaintDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressMaintDTO }
     *     
     */
    public void setAddressMaintDTO(AddressMaintDTO value) {
        this.addressMaintDTO = value;
    }

    /**
     * Gets the value of the existingPaymentMethodDTO property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentMethodDTO }
     *     
     */
    public PaymentMethodDTO getExistingPaymentMethodDTO() {
        return existingPaymentMethodDTO;
    }

    /**
     * Sets the value of the existingPaymentMethodDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentMethodDTO }
     *     
     */
    public void setExistingPaymentMethodDTO(PaymentMethodDTO value) {
        this.existingPaymentMethodDTO = value;
    }

    /**
     * Gets the value of the futurePaymentMethodDTO property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentMethodDTO }
     *     
     */
    public PaymentMethodDTO getFuturePaymentMethodDTO() {
        return futurePaymentMethodDTO;
    }

    /**
     * Sets the value of the futurePaymentMethodDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentMethodDTO }
     *     
     */
    public void setFuturePaymentMethodDTO(PaymentMethodDTO value) {
        this.futurePaymentMethodDTO = value;
    }

    /**
     * Gets the value of the payMethodChangeInd property.
     * 
     */
    public boolean isPayMethodChangeInd() {
        return payMethodChangeInd;
    }

    /**
     * Sets the value of the payMethodChangeInd property.
     * 
     */
    public void setPayMethodChangeInd(boolean value) {
        this.payMethodChangeInd = value;
    }

    /**
     * Gets the value of the billPeriodChangeInd property.
     * 
     */
    public boolean isBillPeriodChangeInd() {
        return billPeriodChangeInd;
    }

    /**
     * Sets the value of the billPeriodChangeInd property.
     * 
     */
    public void setBillPeriodChangeInd(boolean value) {
        this.billPeriodChangeInd = value;
    }

    /**
     * Gets the value of the secBillName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecBillName() {
        return secBillName;
    }

    /**
     * Sets the value of the secBillName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecBillName(String value) {
        this.secBillName = value;
    }

    /**
     * Gets the value of the attnBillName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttnBillName() {
        return attnBillName;
    }

    /**
     * Sets the value of the attnBillName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttnBillName(String value) {
        this.attnBillName = value;
    }

    /**
     * Gets the value of the crstRemarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrstRemarks() {
        return crstRemarks;
    }

    /**
     * Sets the value of the crstRemarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrstRemarks(String value) {
        this.crstRemarks = value;
    }

    /**
     * Gets the value of the supstafg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupstafg() {
        return supstafg;
    }

    /**
     * Sets the value of the supstafg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupstafg(String value) {
        this.supstafg = value;
    }

    /**
     * Gets the value of the supendfg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupendfg() {
        return supendfg;
    }

    /**
     * Sets the value of the supendfg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupendfg(String value) {
        this.supendfg = value;
    }

    /**
     * Gets the value of the imsRowSeqNbCustblac property.
     * 
     */
    public int getImsRowSeqNbCustblac() {
        return imsRowSeqNbCustblac;
    }

    /**
     * Sets the value of the imsRowSeqNbCustblac property.
     * 
     */
    public void setImsRowSeqNbCustblac(int value) {
        this.imsRowSeqNbCustblac = value;
    }

    /**
     * Gets the value of the billDetailsInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillDetailsInd() {
        return billDetailsInd;
    }

    /**
     * Sets the value of the billDetailsInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillDetailsInd(String value) {
        this.billDetailsInd = value;
    }

    /**
     * Gets the value of the acctName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctName() {
        return acctName;
    }

    /**
     * Sets the value of the acctName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctName(String value) {
        this.acctName = value;
    }

    /**
     * Gets the value of the acctNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctNum() {
        return acctNum;
    }

    /**
     * Sets the value of the acctNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctNum(String value) {
        this.acctNum = value;
    }

    /**
     * Gets the value of the acctStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctStatus() {
        return acctStatus;
    }

    /**
     * Sets the value of the acctStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctStatus(String value) {
        this.acctStatus = value;
    }

    /**
     * Gets the value of the acctSubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctSubType() {
        return acctSubType;
    }

    /**
     * Sets the value of the acctSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctSubType(String value) {
        this.acctSubType = value;
    }

    /**
     * Gets the value of the acctType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctType() {
        return acctType;
    }

    /**
     * Sets the value of the acctType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctType(String value) {
        this.acctType = value;
    }

    /**
     * Gets the value of the autopayUpperLimitAmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutopayUpperLimitAmt() {
        return autopayUpperLimitAmt;
    }

    /**
     * Sets the value of the autopayUpperLimitAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutopayUpperLimitAmt(String value) {
        this.autopayUpperLimitAmt = value;
    }

    /**
     * Gets the value of the badDebtInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBadDebtInd() {
        return badDebtInd;
    }

    /**
     * Sets the value of the badDebtInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBadDebtInd(String value) {
        this.badDebtInd = value;
    }

    /**
     * Gets the value of the badDebtStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBadDebtStatus() {
        return badDebtStatus;
    }

    /**
     * Sets the value of the badDebtStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBadDebtStatus(String value) {
        this.badDebtStatus = value;
    }

    /**
     * Gets the value of the billEnqInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillEnqInd() {
        return billEnqInd;
    }

    /**
     * Sets the value of the billEnqInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillEnqInd(String value) {
        this.billEnqInd = value;
    }

    /**
     * Gets the value of the billFmtInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillFmtInd() {
        return billFmtInd;
    }

    /**
     * Sets the value of the billFmtInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillFmtInd(String value) {
        this.billFmtInd = value;
    }

    /**
     * Gets the value of the billFreq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillFreq() {
        return billFreq;
    }

    /**
     * Sets the value of the billFreq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillFreq(String value) {
        this.billFreq = value;
    }

    /**
     * Gets the value of the billingAddrId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingAddrId() {
        return billingAddrId;
    }

    /**
     * Sets the value of the billingAddrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingAddrId(String value) {
        this.billingAddrId = value;
    }

    /**
     * Gets the value of the billLang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillLang() {
        return billLang;
    }

    /**
     * Sets the value of the billLang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillLang(String value) {
        this.billLang = value;
    }

    /**
     * Gets the value of the billPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillPeriod() {
        return billPeriod;
    }

    /**
     * Sets the value of the billPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillPeriod(String value) {
        this.billPeriod = value;
    }

    /**
     * Gets the value of the billStsDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillStsDate() {
        return billStsDate;
    }

    /**
     * Sets the value of the billStsDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillStsDate(String value) {
        this.billStsDate = value;
    }

    /**
     * Gets the value of the blacklistInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlacklistInd() {
        return blacklistInd;
    }

    /**
     * Sets the value of the blacklistInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlacklistInd(String value) {
        this.blacklistInd = value;
    }

    /**
     * Gets the value of the cardPackInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardPackInd() {
        return cardPackInd;
    }

    /**
     * Sets the value of the cardPackInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardPackInd(String value) {
        this.cardPackInd = value;
    }

    /**
     * Gets the value of the ccCreateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcCreateDate() {
        return ccCreateDate;
    }

    /**
     * Sets the value of the ccCreateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcCreateDate(String value) {
        this.ccCreateDate = value;
    }

    /**
     * Gets the value of the chiNameCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChiNameCd() {
        return chiNameCd;
    }

    /**
     * Sets the value of the chiNameCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChiNameCd(String value) {
        this.chiNameCd = value;
    }

    /**
     * Gets the value of the creditClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditClass() {
        return creditClass;
    }

    /**
     * Sets the value of the creditClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditClass(String value) {
        this.creditClass = value;
    }

    /**
     * Gets the value of the creditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditLimit() {
        return creditLimit;
    }

    /**
     * Sets the value of the creditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditLimit(String value) {
        this.creditLimit = value;
    }

    /**
     * Gets the value of the creditStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditStartDate() {
        return creditStartDate;
    }

    /**
     * Sets the value of the creditStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditStartDate(String value) {
        this.creditStartDate = value;
    }

    /**
     * Gets the value of the newCreditStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewCreditStatus() {
        return newCreditStatus;
    }

    /**
     * Sets the value of the newCreditStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewCreditStatus(String value) {
        this.newCreditStatus = value;
    }

    /**
     * Gets the value of the existingCreditStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistingCreditStatus() {
        return existingCreditStatus;
    }

    /**
     * Sets the value of the existingCreditStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistingCreditStatus(String value) {
        this.existingCreditStatus = value;
    }

    /**
     * Gets the value of the credMngtHoldDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredMngtHoldDate() {
        return credMngtHoldDate;
    }

    /**
     * Sets the value of the credMngtHoldDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredMngtHoldDate(String value) {
        this.credMngtHoldDate = value;
    }

    /**
     * Gets the value of the credMngtHoldRdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredMngtHoldRdate() {
        return credMngtHoldRdate;
    }

    /**
     * Sets the value of the credMngtHoldRdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredMngtHoldRdate(String value) {
        this.credMngtHoldRdate = value;
    }

    /**
     * Gets the value of the credMngtHoldRemark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredMngtHoldRemark() {
        return credMngtHoldRemark;
    }

    /**
     * Sets the value of the credMngtHoldRemark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredMngtHoldRemark(String value) {
        this.credMngtHoldRemark = value;
    }

    /**
     * Gets the value of the currOSAdverAmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrOSAdverAmt() {
        return currOSAdverAmt;
    }

    /**
     * Sets the value of the currOSAdverAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrOSAdverAmt(String value) {
        this.currOSAdverAmt = value;
    }

    /**
     * Gets the value of the currOSHktAmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrOSHktAmt() {
        return currOSHktAmt;
    }

    /**
     * Sets the value of the currOSHktAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrOSHktAmt(String value) {
        this.currOSHktAmt = value;
    }

    /**
     * Gets the value of the custCatg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustCatg() {
        return custCatg;
    }

    /**
     * Sets the value of the custCatg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustCatg(String value) {
        this.custCatg = value;
    }

    /**
     * Gets the value of the custNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustNum() {
        return custNum;
    }

    /**
     * Sets the value of the custNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustNum(String value) {
        this.custNum = value;
    }

    /**
     * Gets the value of the demnoteStsCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDemnoteStsCd() {
        return demnoteStsCd;
    }

    /**
     * Sets the value of the demnoteStsCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDemnoteStsCd(String value) {
        this.demnoteStsCd = value;
    }

    /**
     * Gets the value of the donationRoundingAmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDonationRoundingAmt() {
        return donationRoundingAmt;
    }

    /**
     * Sets the value of the donationRoundingAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDonationRoundingAmt(String value) {
        this.donationRoundingAmt = value;
    }

    /**
     * Gets the value of the dtlCCCreateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtlCCCreateDate() {
        return dtlCCCreateDate;
    }

    /**
     * Sets the value of the dtlCCCreateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtlCCCreateDate(String value) {
        this.dtlCCCreateDate = value;
    }

    /**
     * Gets the value of the effEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffEndDate() {
        return effEndDate;
    }

    /**
     * Sets the value of the effEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffEndDate(String value) {
        this.effEndDate = value;
    }

    /**
     * Gets the value of the effStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffStartDate() {
        return effStartDate;
    }

    /**
     * Sets the value of the effStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffStartDate(String value) {
        this.effStartDate = value;
    }

    /**
     * Gets the value of the emailAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddr() {
        return emailAddr;
    }

    /**
     * Sets the value of the emailAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddr(String value) {
        this.emailAddr = value;
    }

    /**
     * Gets the value of the finalAcctDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinalAcctDate() {
        return finalAcctDate;
    }

    /**
     * Sets the value of the finalAcctDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinalAcctDate(String value) {
        this.finalAcctDate = value;
    }

    /**
     * Gets the value of the imsRowSeqNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImsRowSeqNb() {
        return imsRowSeqNb;
    }

    /**
     * Sets the value of the imsRowSeqNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImsRowSeqNb(String value) {
        this.imsRowSeqNb = value;
    }

    /**
     * Gets the value of the invGenBefInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvGenBefInd() {
        return invGenBefInd;
    }

    /**
     * Sets the value of the invGenBefInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvGenBefInd(String value) {
        this.invGenBefInd = value;
    }

    /**
     * Gets the value of the invToBdas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvToBdas() {
        return invToBdas;
    }

    /**
     * Sets the value of the invToBdas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvToBdas(String value) {
        this.invToBdas = value;
    }

    /**
     * Gets the value of the lastInvPaymentDueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastInvPaymentDueDate() {
        return lastInvPaymentDueDate;
    }

    /**
     * Sets the value of the lastInvPaymentDueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastInvPaymentDueDate(String value) {
        this.lastInvPaymentDueDate = value;
    }

    /**
     * Gets the value of the lastUpdProcess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdProcess() {
        return lastUpdProcess;
    }

    /**
     * Sets the value of the lastUpdProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdProcess(String value) {
        this.lastUpdProcess = value;
    }

    /**
     * Gets the value of the lastUpdBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdBy() {
        return lastUpdBy;
    }

    /**
     * Sets the value of the lastUpdBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdBy(String value) {
        this.lastUpdBy = value;
    }

    /**
     * Gets the value of the ltsRowSeqNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLtsRowSeqNb() {
        return ltsRowSeqNb;
    }

    /**
     * Sets the value of the ltsRowSeqNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLtsRowSeqNb(String value) {
        this.ltsRowSeqNb = value;
    }

    /**
     * Gets the value of the mailingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailingName() {
        return mailingName;
    }

    /**
     * Sets the value of the mailingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailingName(String value) {
        this.mailingName = value;
    }

    /**
     * Gets the value of the manualSuppEndMth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManualSuppEndMth() {
        return manualSuppEndMth;
    }

    /**
     * Sets the value of the manualSuppEndMth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManualSuppEndMth(String value) {
        this.manualSuppEndMth = value;
    }

    /**
     * Gets the value of the manualSuppStMth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManualSuppStMth() {
        return manualSuppStMth;
    }

    /**
     * Sets the value of the manualSuppStMth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManualSuppStMth(String value) {
        this.manualSuppStMth = value;
    }

    /**
     * Gets the value of the netvigatorInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetvigatorInd() {
        return netvigatorInd;
    }

    /**
     * Sets the value of the netvigatorInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetvigatorInd(String value) {
        this.netvigatorInd = value;
    }

    /**
     * Gets the value of the printCrLimitInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintCrLimitInd() {
        return printCrLimitInd;
    }

    /**
     * Sets the value of the printCrLimitInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintCrLimitInd(String value) {
        this.printCrLimitInd = value;
    }

    /**
     * Gets the value of the remarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the value of the remarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
    }

    /**
     * Gets the value of the rentBillToDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRentBillToDate() {
        return rentBillToDate;
    }

    /**
     * Sets the value of the rentBillToDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRentBillToDate(String value) {
        this.rentBillToDate = value;
    }

    /**
     * Gets the value of the rowSeqNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRowSeqNb() {
        return rowSeqNb;
    }

    /**
     * Sets the value of the rowSeqNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRowSeqNb(String value) {
        this.rowSeqNb = value;
    }

    /**
     * Gets the value of the sortOption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortOption() {
        return sortOption;
    }

    /**
     * Sets the value of the sortOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortOption(String value) {
        this.sortOption = value;
    }

    /**
     * Gets the value of the suppCnt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuppCnt() {
        return suppCnt;
    }

    /**
     * Sets the value of the suppCnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuppCnt(String value) {
        this.suppCnt = value;
    }

    /**
     * Gets the value of the systemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Sets the value of the systemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemId(String value) {
        this.systemId = value;
    }

    /**
     * Gets the value of the tentIdNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTentIdNum() {
        return tentIdNum;
    }

    /**
     * Sets the value of the tentIdNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTentIdNum(String value) {
        this.tentIdNum = value;
    }

    /**
     * Gets the value of the tentIdType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTentIdType() {
        return tentIdType;
    }

    /**
     * Sets the value of the tentIdType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTentIdType(String value) {
        this.tentIdType = value;
    }

    /**
     * Gets the value of the tentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTentName() {
        return tentName;
    }

    /**
     * Sets the value of the tentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTentName(String value) {
        this.tentName = value;
    }

    /**
     * Gets the value of the writtenOffAmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWrittenOffAmt() {
        return writtenOffAmt;
    }

    /**
     * Sets the value of the writtenOffAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWrittenOffAmt(String value) {
        this.writtenOffAmt = value;
    }

    /**
     * Gets the value of the pendingBillPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPendingBillPeriod() {
        return pendingBillPeriod;
    }

    /**
     * Sets the value of the pendingBillPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPendingBillPeriod(String value) {
        this.pendingBillPeriod = value;
    }

    /**
     * Gets the value of the tariffGrp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTariffGrp() {
        return tariffGrp;
    }

    /**
     * Sets the value of the tariffGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTariffGrp(String value) {
        this.tariffGrp = value;
    }

    /**
     * Gets the value of the subrAGROnlyInd property.
     * 
     */
    public boolean isSubrAGROnlyInd() {
        return subrAGROnlyInd;
    }

    /**
     * Sets the value of the subrAGROnlyInd property.
     * 
     */
    public void setSubrAGROnlyInd(boolean value) {
        this.subrAGROnlyInd = value;
    }

    /**
     * Gets the value of the mobRowSeqNb property.
     * 
     */
    public int getMobRowSeqNb() {
        return mobRowSeqNb;
    }

    /**
     * Sets the value of the mobRowSeqNb property.
     * 
     */
    public void setMobRowSeqNb(int value) {
        this.mobRowSeqNb = value;
    }

    /**
     * Gets the value of the statusReaCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusReaCd() {
        return statusReaCd;
    }

    /**
     * Sets the value of the statusReaCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusReaCd(String value) {
        this.statusReaCd = value;
    }

    /**
     * Gets the value of the smsNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsNum() {
        return smsNum;
    }

    /**
     * Sets the value of the smsNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsNum(String value) {
        this.smsNum = value;
    }

    /**
     * Gets the value of the retMailDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetMailDate() {
        return retMailDate;
    }

    /**
     * Sets the value of the retMailDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetMailDate(String value) {
        this.retMailDate = value;
    }

    /**
     * Gets the value of the ccc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcc() {
        return ccc;
    }

    /**
     * Sets the value of the ccc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcc(String value) {
        this.ccc = value;
    }

    /**
     * Gets the value of the splitAcctCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSplitAcctCode() {
        return splitAcctCode;
    }

    /**
     * Sets the value of the splitAcctCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSplitAcctCode(String value) {
        this.splitAcctCode = value;
    }

    /**
     * Gets the value of the poid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoid() {
        return poid;
    }

    /**
     * Sets the value of the poid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoid(String value) {
        this.poid = value;
    }

    /**
     * Gets the value of the initDunGrp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitDunGrp() {
        return initDunGrp;
    }

    /**
     * Sets the value of the initDunGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitDunGrp(String value) {
        this.initDunGrp = value;
    }

    /**
     * Gets the value of the mobLastUpdDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobLastUpdDate() {
        return mobLastUpdDate;
    }

    /**
     * Sets the value of the mobLastUpdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobLastUpdDate(String value) {
        this.mobLastUpdDate = value;
    }

    /**
     * Gets the value of the mobLastUpdBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobLastUpdBy() {
        return mobLastUpdBy;
    }

    /**
     * Sets the value of the mobLastUpdBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobLastUpdBy(String value) {
        this.mobLastUpdBy = value;
    }

    /**
     * Gets the value of the mobCcCreateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobCcCreateDate() {
        return mobCcCreateDate;
    }

    /**
     * Sets the value of the mobCcCreateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobCcCreateDate(String value) {
        this.mobCcCreateDate = value;
    }

    /**
     * Gets the value of the mobLastUpdProcess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobLastUpdProcess() {
        return mobLastUpdProcess;
    }

    /**
     * Sets the value of the mobLastUpdProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobLastUpdProcess(String value) {
        this.mobLastUpdProcess = value;
    }

    /**
     * Gets the value of the mobBillMediaDTOArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMOBBillMediaDTO }
     *     
     */
    public ArrayOfMOBBillMediaDTO getMobBillMediaDTOArray() {
        return mobBillMediaDTOArray;
    }

    /**
     * Sets the value of the mobBillMediaDTOArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMOBBillMediaDTO }
     *     
     */
    public void setMobBillMediaDTOArray(ArrayOfMOBBillMediaDTO value) {
        this.mobBillMediaDTOArray = value;
    }

    /**
     * Gets the value of the shopCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * Sets the value of the shopCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShopCode(String value) {
        this.shopCode = value;
    }

    /**
     * Gets the value of the existBillDay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistBillDay() {
        return existBillDay;
    }

    /**
     * Sets the value of the existBillDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistBillDay(String value) {
        this.existBillDay = value;
    }

    /**
     * Gets the value of the nextBillDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNextBillDate() {
        return nextBillDate;
    }

    /**
     * Sets the value of the nextBillDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextBillDate(String value) {
        this.nextBillDate = value;
    }

    /**
     * Gets the value of the lastBillDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastBillDate() {
        return lastBillDate;
    }

    /**
     * Sets the value of the lastBillDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastBillDate(String value) {
        this.lastBillDate = value;
    }

    /**
     * Gets the value of the pendEffBillDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPendEffBillDate() {
        return pendEffBillDate;
    }

    /**
     * Sets the value of the pendEffBillDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPendEffBillDate(String value) {
        this.pendEffBillDate = value;
    }

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

    /**
     * Gets the value of the poolEffDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoolEffDate() {
        return poolEffDate;
    }

    /**
     * Sets the value of the poolEffDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoolEffDate(String value) {
        this.poolEffDate = value;
    }

    /**
     * Gets the value of the poolTermDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoolTermDate() {
        return poolTermDate;
    }

    /**
     * Sets the value of the poolTermDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoolTermDate(String value) {
        this.poolTermDate = value;
    }

    /**
     * Gets the value of the displayPoolEffDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayPoolEffDate() {
        return displayPoolEffDate;
    }

    /**
     * Sets the value of the displayPoolEffDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayPoolEffDate(String value) {
        this.displayPoolEffDate = value;
    }

    /**
     * Gets the value of the displayPoolTermDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayPoolTermDate() {
        return displayPoolTermDate;
    }

    /**
     * Sets the value of the displayPoolTermDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayPoolTermDate(String value) {
        this.displayPoolTermDate = value;
    }

    /**
     * Gets the value of the poolIndType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoolIndType() {
        return poolIndType;
    }

    /**
     * Sets the value of the poolIndType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoolIndType(String value) {
        this.poolIndType = value;
    }

    /**
     * Gets the value of the e0060CreditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE0060CreditLimit() {
        return e0060CreditLimit;
    }

    /**
     * Sets the value of the e0060CreditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE0060CreditLimit(String value) {
        this.e0060CreditLimit = value;
    }

    /**
     * Gets the value of the paperBill property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaperBill() {
        return paperBill;
    }

    /**
     * Sets the value of the paperBill property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaperBill(String value) {
        this.paperBill = value;
    }

    /**
     * Gets the value of the onLanAtWork property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnLanAtWork() {
        return onLanAtWork;
    }

    /**
     * Sets the value of the onLanAtWork property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnLanAtWork(String value) {
        this.onLanAtWork = value;
    }

    /**
     * Gets the value of the portalErrorText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortalErrorText() {
        return portalErrorText;
    }

    /**
     * Sets the value of the portalErrorText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortalErrorText(String value) {
        this.portalErrorText = value;
    }

    /**
     * Gets the value of the acctStatusDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctStatusDate() {
        return acctStatusDate;
    }

    /**
     * Sets the value of the acctStatusDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctStatusDate(String value) {
        this.acctStatusDate = value;
    }

    /**
     * Gets the value of the eBillAgent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEBillAgent() {
        return eBillAgent;
    }

    /**
     * Sets the value of the eBillAgent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEBillAgent(String value) {
        this.eBillAgent = value;
    }

    /**
     * Gets the value of the redemMedia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRedemMedia() {
        return redemMedia;
    }

    /**
     * Sets the value of the redemMedia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRedemMedia(String value) {
        this.redemMedia = value;
    }

}

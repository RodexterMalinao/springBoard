
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CustomerDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="LangSpeak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LangWritten" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SPID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistOctopus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressMaintDTO" type="{http://www.openuri.org/}AddressMaintDTO" minOccurs="0"/>
 *         &lt;element name="AsiaMileNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BcdRowSeqNb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BcpRowSeqNb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BcRowSeqNb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BlacklistActCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BlacklistInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChiName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChiNameCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CompanyChiName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CompanyNameRmk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsentHistoryDTOArray" type="{http://www.openuri.org/}ArrayOfConsentHistoryDTO" minOccurs="0"/>
 *         &lt;element name="ContactInfoDTOArray" type="{http://www.openuri.org/}ArrayOfContactInfoDTO" minOccurs="0"/>
 *         &lt;element name="ContactPerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CorrAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustCatg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerRmkDTOArray" type="{http://www.openuri.org/}ArrayOfCustomerRmkDTO" minOccurs="0"/>
 *         &lt;element name="CustType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DirectMktInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DiscloAssoCompany" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DmConfInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DrgDbKey" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GrpIdDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GrpIdDocType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HasRemarksInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HktStaffNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdDocType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdVerifyInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImsRowSeqNb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IndustryType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IndustrySubType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InvestRptDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InvestRptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastUpdCustomerRmkDTO" type="{http://www.openuri.org/}CustomerRmkDTO" minOccurs="0"/>
 *         &lt;element name="MembershipInfoDTO" type="{http://www.openuri.org/}MembershipInfoDTO" minOccurs="0"/>
 *         &lt;element name="NetvigatorCustInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OptInDirectMailing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OptInInfoDTO" type="{http://www.openuri.org/}OptInInfoDTO" minOccurs="0"/>
 *         &lt;element name="ParentCustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrimaryContactInfoDTO" type="{http://www.openuri.org/}ContactInfoDTO" minOccurs="0"/>
 *         &lt;element name="RecontractRmksDTOArray" type="{http://www.openuri.org/}ArrayOfRecontractRmksDTO" minOccurs="0"/>
 *         &lt;element name="SalesInfoDTO" type="{http://www.openuri.org/}SalesInfoDTO" minOccurs="0"/>
 *         &lt;element name="ServiceLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpecHandleInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpecSRHandleInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpecialSRHandlingRmksDTOArray" type="{http://www.openuri.org/}ArrayOfSpecialSRHandlingRmksDTO" minOccurs="0"/>
 *         &lt;element name="SpecialHandlingRmksDTOArray" type="{http://www.openuri.org/}ArrayOfSpecialHandlingRmksDTO" minOccurs="0"/>
 *         &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WrittenApprovalRequired" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WIPRmksDTOArray" type="{http://www.openuri.org/}ArrayOfWIPRmksDTO" minOccurs="0"/>
 *         &lt;element name="ConcentInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TotalOsAmt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="WrittenOffAmt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="IsHktStaff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsJunior" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsBirthday" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsCallLog" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsVACust" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustTier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HkidCollectInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nationality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServerInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcCreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ShopCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TierType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaperBill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OptOutInfoGroupDTO" type="{http://www.openuri.org/}OptOutInfoGroupDTO" minOccurs="0"/>
 *         &lt;element name="SubscribeAgrOnlyInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HasSrvLineInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromAcctnum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromCustFname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromCustLname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromCustnum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromIdDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromIdDocType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LegacyOrdNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OcID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OptOutInfoDTOArray" type="{http://www.openuri.org/}ArrayOfOptOutInfoDTO" minOccurs="0"/>
 *         &lt;element name="RLastUpdby" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToAcctnum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToCustFname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToCustLname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToCustnum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToIdDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToIdDocType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerDTO", propOrder = {
    "langSpeak",
    "langWritten",
    "spid",
    "existOctopus",
    "addressMaintDTO",
    "asiaMileNum",
    "bcdRowSeqNb",
    "bcpRowSeqNb",
    "bcRowSeqNb",
    "blacklistActCd",
    "blacklistInd",
    "chiName",
    "chiNameCd",
    "companyChiName",
    "companyName",
    "companyNameRmk",
    "consentHistoryDTOArray",
    "contactInfoDTOArray",
    "contactPerName",
    "corrAddr",
    "custCatg",
    "custFirstName",
    "custLastName",
    "custLevel",
    "custNum",
    "customerRmkDTOArray",
    "custType",
    "directMktInd",
    "discloAssoCompany",
    "dmConfInd",
    "dob",
    "drgDbKey",
    "gender",
    "grpIdDocNum",
    "grpIdDocType",
    "hasRemarksInd",
    "hktStaffNo",
    "idDocNum",
    "idDocType",
    "idVerifyInd",
    "imsRowSeqNb",
    "industryType",
    "industrySubType",
    "investRptDate",
    "investRptNum",
    "lastUpdBy",
    "lastUpdCustomerRmkDTO",
    "membershipInfoDTO",
    "netvigatorCustInd",
    "optInDirectMailing",
    "optInInfoDTO",
    "parentCustNum",
    "primaryContactInfoDTO",
    "recontractRmksDTOArray",
    "salesInfoDTO",
    "serviceLevel",
    "specHandleInd",
    "specSRHandleInd",
    "specialSRHandlingRmksDTOArray",
    "specialHandlingRmksDTOArray",
    "systemId",
    "title",
    "writtenApprovalRequired",
    "wipRmksDTOArray",
    "concentInd",
    "totalOsAmt",
    "writtenOffAmt",
    "isHktStaff",
    "isJunior",
    "isBirthday",
    "isCallLog",
    "isVACust",
    "custTier",
    "hkidCollectInd",
    "nationality",
    "serverInd",
    "ccCreateDate",
    "shopCode",
    "tierType",
    "paperBill",
    "optOutInfoGroupDTO",
    "subscribeAgrOnlyInd",
    "hasSrvLineInd",
    "fromAcctnum",
    "fromCustFname",
    "fromCustLname",
    "fromCustnum",
    "fromIdDocNum",
    "fromIdDocType",
    "lastUpdDate",
    "legacyOrdNum",
    "ocID",
    "optOutInfoDTOArray",
    "rLastUpdby",
    "srvID",
    "toAcctnum",
    "toCustFname",
    "toCustLname",
    "toCustnum",
    "toIdDocNum",
    "toIdDocType"
})
public class CustomerDTO
    extends ValueObject
{

    @XmlElement(name = "LangSpeak")
    protected String langSpeak;
    @XmlElement(name = "LangWritten")
    protected String langWritten;
    @XmlElement(name = "SPID")
    protected String spid;
    @XmlElement(name = "ExistOctopus")
    protected String existOctopus;
    @XmlElement(name = "AddressMaintDTO")
    protected AddressMaintDTO addressMaintDTO;
    @XmlElement(name = "AsiaMileNum")
    protected String asiaMileNum;
    @XmlElement(name = "BcdRowSeqNb")
    protected int bcdRowSeqNb;
    @XmlElement(name = "BcpRowSeqNb")
    protected int bcpRowSeqNb;
    @XmlElement(name = "BcRowSeqNb")
    protected int bcRowSeqNb;
    @XmlElement(name = "BlacklistActCd")
    protected String blacklistActCd;
    @XmlElement(name = "BlacklistInd")
    protected String blacklistInd;
    @XmlElement(name = "ChiName")
    protected String chiName;
    @XmlElement(name = "ChiNameCd")
    protected String chiNameCd;
    @XmlElement(name = "CompanyChiName")
    protected String companyChiName;
    @XmlElement(name = "CompanyName")
    protected String companyName;
    @XmlElement(name = "CompanyNameRmk")
    protected String companyNameRmk;
    @XmlElement(name = "ConsentHistoryDTOArray")
    protected ArrayOfConsentHistoryDTO consentHistoryDTOArray;
    @XmlElement(name = "ContactInfoDTOArray")
    protected ArrayOfContactInfoDTO contactInfoDTOArray;
    @XmlElement(name = "ContactPerName")
    protected String contactPerName;
    @XmlElement(name = "CorrAddr")
    protected String corrAddr;
    @XmlElement(name = "CustCatg")
    protected String custCatg;
    @XmlElement(name = "CustFirstName")
    protected String custFirstName;
    @XmlElement(name = "CustLastName")
    protected String custLastName;
    @XmlElement(name = "CustLevel")
    protected int custLevel;
    @XmlElement(name = "CustNum")
    protected String custNum;
    @XmlElement(name = "CustomerRmkDTOArray")
    protected ArrayOfCustomerRmkDTO customerRmkDTOArray;
    @XmlElement(name = "CustType")
    protected String custType;
    @XmlElement(name = "DirectMktInd")
    protected String directMktInd;
    @XmlElement(name = "DiscloAssoCompany")
    protected String discloAssoCompany;
    @XmlElement(name = "DmConfInd")
    protected String dmConfInd;
    @XmlElement(name = "Dob")
    protected String dob;
    @XmlElement(name = "DrgDbKey")
    protected int drgDbKey;
    @XmlElement(name = "Gender")
    protected String gender;
    @XmlElement(name = "GrpIdDocNum")
    protected String grpIdDocNum;
    @XmlElement(name = "GrpIdDocType")
    protected String grpIdDocType;
    @XmlElement(name = "HasRemarksInd")
    protected String hasRemarksInd;
    @XmlElement(name = "HktStaffNo")
    protected String hktStaffNo;
    @XmlElement(name = "IdDocNum")
    protected String idDocNum;
    @XmlElement(name = "IdDocType")
    protected String idDocType;
    @XmlElement(name = "IdVerifyInd")
    protected String idVerifyInd;
    @XmlElement(name = "ImsRowSeqNb")
    protected int imsRowSeqNb;
    @XmlElement(name = "IndustryType")
    protected String industryType;
    @XmlElement(name = "IndustrySubType")
    protected String industrySubType;
    @XmlElement(name = "InvestRptDate")
    protected String investRptDate;
    @XmlElement(name = "InvestRptNum")
    protected String investRptNum;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    protected CustomerRmkDTO lastUpdCustomerRmkDTO;
    @XmlElement(name = "MembershipInfoDTO")
    protected MembershipInfoDTO membershipInfoDTO;
    @XmlElement(name = "NetvigatorCustInd")
    protected String netvigatorCustInd;
    @XmlElement(name = "OptInDirectMailing")
    protected String optInDirectMailing;
    @XmlElement(name = "OptInInfoDTO")
    protected OptInInfoDTO optInInfoDTO;
    @XmlElement(name = "ParentCustNum")
    protected String parentCustNum;
    @XmlElement(name = "PrimaryContactInfoDTO")
    protected ContactInfoDTO primaryContactInfoDTO;
    @XmlElement(name = "RecontractRmksDTOArray")
    protected ArrayOfRecontractRmksDTO recontractRmksDTOArray;
    @XmlElement(name = "SalesInfoDTO")
    protected SalesInfoDTO salesInfoDTO;
    @XmlElement(name = "ServiceLevel")
    protected String serviceLevel;
    @XmlElement(name = "SpecHandleInd")
    protected String specHandleInd;
    @XmlElement(name = "SpecSRHandleInd")
    protected String specSRHandleInd;
    @XmlElement(name = "SpecialSRHandlingRmksDTOArray")
    protected ArrayOfSpecialSRHandlingRmksDTO specialSRHandlingRmksDTOArray;
    @XmlElement(name = "SpecialHandlingRmksDTOArray")
    protected ArrayOfSpecialHandlingRmksDTO specialHandlingRmksDTOArray;
    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "WrittenApprovalRequired")
    protected String writtenApprovalRequired;
    @XmlElement(name = "WIPRmksDTOArray")
    protected ArrayOfWIPRmksDTO wipRmksDTOArray;
    @XmlElement(name = "ConcentInd")
    protected String concentInd;
    @XmlElement(name = "TotalOsAmt")
    protected double totalOsAmt;
    @XmlElement(name = "WrittenOffAmt")
    protected double writtenOffAmt;
    @XmlElement(name = "IsHktStaff")
    protected String isHktStaff;
    @XmlElement(name = "IsJunior")
    protected String isJunior;
    @XmlElement(name = "IsBirthday")
    protected String isBirthday;
    @XmlElement(name = "IsCallLog")
    protected String isCallLog;
    @XmlElement(name = "IsVACust")
    protected String isVACust;
    @XmlElement(name = "CustTier")
    protected String custTier;
    @XmlElement(name = "HkidCollectInd")
    protected String hkidCollectInd;
    @XmlElement(name = "Nationality")
    protected String nationality;
    @XmlElement(name = "ServerInd")
    protected String serverInd;
    @XmlElement(name = "CcCreateDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ccCreateDate;
    @XmlElement(name = "ShopCode")
    protected String shopCode;
    @XmlElement(name = "TierType")
    protected String tierType;
    @XmlElement(name = "PaperBill")
    protected String paperBill;
    @XmlElement(name = "OptOutInfoGroupDTO")
    protected OptOutInfoGroupDTO optOutInfoGroupDTO;
    @XmlElement(name = "SubscribeAgrOnlyInd")
    protected String subscribeAgrOnlyInd;
    @XmlElement(name = "HasSrvLineInd")
    protected String hasSrvLineInd;
    @XmlElement(name = "FromAcctnum")
    protected String fromAcctnum;
    @XmlElement(name = "FromCustFname")
    protected String fromCustFname;
    @XmlElement(name = "FromCustLname")
    protected String fromCustLname;
    @XmlElement(name = "FromCustnum")
    protected String fromCustnum;
    @XmlElement(name = "FromIdDocNum")
    protected String fromIdDocNum;
    @XmlElement(name = "FromIdDocType")
    protected String fromIdDocType;
    @XmlElement(name = "LastUpdDate")
    protected String lastUpdDate;
    @XmlElement(name = "LegacyOrdNum")
    protected String legacyOrdNum;
    @XmlElement(name = "OcID")
    protected String ocID;
    @XmlElement(name = "OptOutInfoDTOArray")
    protected ArrayOfOptOutInfoDTO optOutInfoDTOArray;
    @XmlElement(name = "RLastUpdby")
    protected String rLastUpdby;
    @XmlElement(name = "SrvID")
    protected String srvID;
    @XmlElement(name = "ToAcctnum")
    protected String toAcctnum;
    @XmlElement(name = "ToCustFname")
    protected String toCustFname;
    @XmlElement(name = "ToCustLname")
    protected String toCustLname;
    @XmlElement(name = "ToCustnum")
    protected String toCustnum;
    @XmlElement(name = "ToIdDocNum")
    protected String toIdDocNum;
    @XmlElement(name = "ToIdDocType")
    protected String toIdDocType;

    /**
     * Gets the value of the langSpeak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLangSpeak() {
        return langSpeak;
    }

    /**
     * Sets the value of the langSpeak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLangSpeak(String value) {
        this.langSpeak = value;
    }

    /**
     * Gets the value of the langWritten property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLangWritten() {
        return langWritten;
    }

    /**
     * Sets the value of the langWritten property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLangWritten(String value) {
        this.langWritten = value;
    }

    /**
     * Gets the value of the spid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPID() {
        return spid;
    }

    /**
     * Sets the value of the spid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPID(String value) {
        this.spid = value;
    }

    /**
     * Gets the value of the existOctopus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistOctopus() {
        return existOctopus;
    }

    /**
     * Sets the value of the existOctopus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistOctopus(String value) {
        this.existOctopus = value;
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
     * Gets the value of the asiaMileNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsiaMileNum() {
        return asiaMileNum;
    }

    /**
     * Sets the value of the asiaMileNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsiaMileNum(String value) {
        this.asiaMileNum = value;
    }

    /**
     * Gets the value of the bcdRowSeqNb property.
     * 
     */
    public int getBcdRowSeqNb() {
        return bcdRowSeqNb;
    }

    /**
     * Sets the value of the bcdRowSeqNb property.
     * 
     */
    public void setBcdRowSeqNb(int value) {
        this.bcdRowSeqNb = value;
    }

    /**
     * Gets the value of the bcpRowSeqNb property.
     * 
     */
    public int getBcpRowSeqNb() {
        return bcpRowSeqNb;
    }

    /**
     * Sets the value of the bcpRowSeqNb property.
     * 
     */
    public void setBcpRowSeqNb(int value) {
        this.bcpRowSeqNb = value;
    }

    /**
     * Gets the value of the bcRowSeqNb property.
     * 
     */
    public int getBcRowSeqNb() {
        return bcRowSeqNb;
    }

    /**
     * Sets the value of the bcRowSeqNb property.
     * 
     */
    public void setBcRowSeqNb(int value) {
        this.bcRowSeqNb = value;
    }

    /**
     * Gets the value of the blacklistActCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlacklistActCd() {
        return blacklistActCd;
    }

    /**
     * Sets the value of the blacklistActCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlacklistActCd(String value) {
        this.blacklistActCd = value;
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
     * Gets the value of the chiName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChiName() {
        return chiName;
    }

    /**
     * Sets the value of the chiName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChiName(String value) {
        this.chiName = value;
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
     * Gets the value of the companyChiName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyChiName() {
        return companyChiName;
    }

    /**
     * Sets the value of the companyChiName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyChiName(String value) {
        this.companyChiName = value;
    }

    /**
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * Gets the value of the companyNameRmk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyNameRmk() {
        return companyNameRmk;
    }

    /**
     * Sets the value of the companyNameRmk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyNameRmk(String value) {
        this.companyNameRmk = value;
    }

    /**
     * Gets the value of the consentHistoryDTOArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfConsentHistoryDTO }
     *     
     */
    public ArrayOfConsentHistoryDTO getConsentHistoryDTOArray() {
        return consentHistoryDTOArray;
    }

    /**
     * Sets the value of the consentHistoryDTOArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfConsentHistoryDTO }
     *     
     */
    public void setConsentHistoryDTOArray(ArrayOfConsentHistoryDTO value) {
        this.consentHistoryDTOArray = value;
    }

    /**
     * Gets the value of the contactInfoDTOArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfContactInfoDTO }
     *     
     */
    public ArrayOfContactInfoDTO getContactInfoDTOArray() {
        return contactInfoDTOArray;
    }

    /**
     * Sets the value of the contactInfoDTOArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfContactInfoDTO }
     *     
     */
    public void setContactInfoDTOArray(ArrayOfContactInfoDTO value) {
        this.contactInfoDTOArray = value;
    }

    /**
     * Gets the value of the contactPerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactPerName() {
        return contactPerName;
    }

    /**
     * Sets the value of the contactPerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactPerName(String value) {
        this.contactPerName = value;
    }

    /**
     * Gets the value of the corrAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrAddr() {
        return corrAddr;
    }

    /**
     * Sets the value of the corrAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrAddr(String value) {
        this.corrAddr = value;
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
     * Gets the value of the custFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustFirstName() {
        return custFirstName;
    }

    /**
     * Sets the value of the custFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustFirstName(String value) {
        this.custFirstName = value;
    }

    /**
     * Gets the value of the custLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustLastName() {
        return custLastName;
    }

    /**
     * Sets the value of the custLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustLastName(String value) {
        this.custLastName = value;
    }

    /**
     * Gets the value of the custLevel property.
     * 
     */
    public int getCustLevel() {
        return custLevel;
    }

    /**
     * Sets the value of the custLevel property.
     * 
     */
    public void setCustLevel(int value) {
        this.custLevel = value;
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
     * Gets the value of the customerRmkDTOArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCustomerRmkDTO }
     *     
     */
    public ArrayOfCustomerRmkDTO getCustomerRmkDTOArray() {
        return customerRmkDTOArray;
    }

    /**
     * Sets the value of the customerRmkDTOArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCustomerRmkDTO }
     *     
     */
    public void setCustomerRmkDTOArray(ArrayOfCustomerRmkDTO value) {
        this.customerRmkDTOArray = value;
    }

    /**
     * Gets the value of the custType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustType() {
        return custType;
    }

    /**
     * Sets the value of the custType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustType(String value) {
        this.custType = value;
    }

    /**
     * Gets the value of the directMktInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectMktInd() {
        return directMktInd;
    }

    /**
     * Sets the value of the directMktInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectMktInd(String value) {
        this.directMktInd = value;
    }

    /**
     * Gets the value of the discloAssoCompany property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscloAssoCompany() {
        return discloAssoCompany;
    }

    /**
     * Sets the value of the discloAssoCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscloAssoCompany(String value) {
        this.discloAssoCompany = value;
    }

    /**
     * Gets the value of the dmConfInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDmConfInd() {
        return dmConfInd;
    }

    /**
     * Sets the value of the dmConfInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDmConfInd(String value) {
        this.dmConfInd = value;
    }

    /**
     * Gets the value of the dob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDob() {
        return dob;
    }

    /**
     * Sets the value of the dob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDob(String value) {
        this.dob = value;
    }

    /**
     * Gets the value of the drgDbKey property.
     * 
     */
    public int getDrgDbKey() {
        return drgDbKey;
    }

    /**
     * Sets the value of the drgDbKey property.
     * 
     */
    public void setDrgDbKey(int value) {
        this.drgDbKey = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Gets the value of the grpIdDocNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrpIdDocNum() {
        return grpIdDocNum;
    }

    /**
     * Sets the value of the grpIdDocNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrpIdDocNum(String value) {
        this.grpIdDocNum = value;
    }

    /**
     * Gets the value of the grpIdDocType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrpIdDocType() {
        return grpIdDocType;
    }

    /**
     * Sets the value of the grpIdDocType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrpIdDocType(String value) {
        this.grpIdDocType = value;
    }

    /**
     * Gets the value of the hasRemarksInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasRemarksInd() {
        return hasRemarksInd;
    }

    /**
     * Sets the value of the hasRemarksInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasRemarksInd(String value) {
        this.hasRemarksInd = value;
    }

    /**
     * Gets the value of the hktStaffNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHktStaffNo() {
        return hktStaffNo;
    }

    /**
     * Sets the value of the hktStaffNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHktStaffNo(String value) {
        this.hktStaffNo = value;
    }

    /**
     * Gets the value of the idDocNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDocNum() {
        return idDocNum;
    }

    /**
     * Sets the value of the idDocNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDocNum(String value) {
        this.idDocNum = value;
    }

    /**
     * Gets the value of the idDocType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDocType() {
        return idDocType;
    }

    /**
     * Sets the value of the idDocType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDocType(String value) {
        this.idDocType = value;
    }

    /**
     * Gets the value of the idVerifyInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdVerifyInd() {
        return idVerifyInd;
    }

    /**
     * Sets the value of the idVerifyInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdVerifyInd(String value) {
        this.idVerifyInd = value;
    }

    /**
     * Gets the value of the imsRowSeqNb property.
     * 
     */
    public int getImsRowSeqNb() {
        return imsRowSeqNb;
    }

    /**
     * Sets the value of the imsRowSeqNb property.
     * 
     */
    public void setImsRowSeqNb(int value) {
        this.imsRowSeqNb = value;
    }

    /**
     * Gets the value of the industryType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndustryType() {
        return industryType;
    }

    /**
     * Sets the value of the industryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndustryType(String value) {
        this.industryType = value;
    }

    /**
     * Gets the value of the industrySubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndustrySubType() {
        return industrySubType;
    }

    /**
     * Sets the value of the industrySubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndustrySubType(String value) {
        this.industrySubType = value;
    }

    /**
     * Gets the value of the investRptDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvestRptDate() {
        return investRptDate;
    }

    /**
     * Sets the value of the investRptDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvestRptDate(String value) {
        this.investRptDate = value;
    }

    /**
     * Gets the value of the investRptNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvestRptNum() {
        return investRptNum;
    }

    /**
     * Sets the value of the investRptNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvestRptNum(String value) {
        this.investRptNum = value;
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
     * Gets the value of the lastUpdCustomerRmkDTO property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerRmkDTO }
     *     
     */
    public CustomerRmkDTO getLastUpdCustomerRmkDTO() {
        return lastUpdCustomerRmkDTO;
    }

    /**
     * Sets the value of the lastUpdCustomerRmkDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerRmkDTO }
     *     
     */
    public void setLastUpdCustomerRmkDTO(CustomerRmkDTO value) {
        this.lastUpdCustomerRmkDTO = value;
    }

    /**
     * Gets the value of the membershipInfoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link MembershipInfoDTO }
     *     
     */
    public MembershipInfoDTO getMembershipInfoDTO() {
        return membershipInfoDTO;
    }

    /**
     * Sets the value of the membershipInfoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link MembershipInfoDTO }
     *     
     */
    public void setMembershipInfoDTO(MembershipInfoDTO value) {
        this.membershipInfoDTO = value;
    }

    /**
     * Gets the value of the netvigatorCustInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetvigatorCustInd() {
        return netvigatorCustInd;
    }

    /**
     * Sets the value of the netvigatorCustInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetvigatorCustInd(String value) {
        this.netvigatorCustInd = value;
    }

    /**
     * Gets the value of the optInDirectMailing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptInDirectMailing() {
        return optInDirectMailing;
    }

    /**
     * Sets the value of the optInDirectMailing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptInDirectMailing(String value) {
        this.optInDirectMailing = value;
    }

    /**
     * Gets the value of the optInInfoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link OptInInfoDTO }
     *     
     */
    public OptInInfoDTO getOptInInfoDTO() {
        return optInInfoDTO;
    }

    /**
     * Sets the value of the optInInfoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link OptInInfoDTO }
     *     
     */
    public void setOptInInfoDTO(OptInInfoDTO value) {
        this.optInInfoDTO = value;
    }

    /**
     * Gets the value of the parentCustNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentCustNum() {
        return parentCustNum;
    }

    /**
     * Sets the value of the parentCustNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentCustNum(String value) {
        this.parentCustNum = value;
    }

    /**
     * Gets the value of the primaryContactInfoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfoDTO }
     *     
     */
    public ContactInfoDTO getPrimaryContactInfoDTO() {
        return primaryContactInfoDTO;
    }

    /**
     * Sets the value of the primaryContactInfoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfoDTO }
     *     
     */
    public void setPrimaryContactInfoDTO(ContactInfoDTO value) {
        this.primaryContactInfoDTO = value;
    }

    /**
     * Gets the value of the recontractRmksDTOArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRecontractRmksDTO }
     *     
     */
    public ArrayOfRecontractRmksDTO getRecontractRmksDTOArray() {
        return recontractRmksDTOArray;
    }

    /**
     * Sets the value of the recontractRmksDTOArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRecontractRmksDTO }
     *     
     */
    public void setRecontractRmksDTOArray(ArrayOfRecontractRmksDTO value) {
        this.recontractRmksDTOArray = value;
    }

    /**
     * Gets the value of the salesInfoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link SalesInfoDTO }
     *     
     */
    public SalesInfoDTO getSalesInfoDTO() {
        return salesInfoDTO;
    }

    /**
     * Sets the value of the salesInfoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesInfoDTO }
     *     
     */
    public void setSalesInfoDTO(SalesInfoDTO value) {
        this.salesInfoDTO = value;
    }

    /**
     * Gets the value of the serviceLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceLevel() {
        return serviceLevel;
    }

    /**
     * Sets the value of the serviceLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceLevel(String value) {
        this.serviceLevel = value;
    }

    /**
     * Gets the value of the specHandleInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecHandleInd() {
        return specHandleInd;
    }

    /**
     * Sets the value of the specHandleInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecHandleInd(String value) {
        this.specHandleInd = value;
    }

    /**
     * Gets the value of the specSRHandleInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecSRHandleInd() {
        return specSRHandleInd;
    }

    /**
     * Sets the value of the specSRHandleInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecSRHandleInd(String value) {
        this.specSRHandleInd = value;
    }

    /**
     * Gets the value of the specialSRHandlingRmksDTOArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSpecialSRHandlingRmksDTO }
     *     
     */
    public ArrayOfSpecialSRHandlingRmksDTO getSpecialSRHandlingRmksDTOArray() {
        return specialSRHandlingRmksDTOArray;
    }

    /**
     * Sets the value of the specialSRHandlingRmksDTOArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSpecialSRHandlingRmksDTO }
     *     
     */
    public void setSpecialSRHandlingRmksDTOArray(ArrayOfSpecialSRHandlingRmksDTO value) {
        this.specialSRHandlingRmksDTOArray = value;
    }

    /**
     * Gets the value of the specialHandlingRmksDTOArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSpecialHandlingRmksDTO }
     *     
     */
    public ArrayOfSpecialHandlingRmksDTO getSpecialHandlingRmksDTOArray() {
        return specialHandlingRmksDTOArray;
    }

    /**
     * Sets the value of the specialHandlingRmksDTOArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSpecialHandlingRmksDTO }
     *     
     */
    public void setSpecialHandlingRmksDTOArray(ArrayOfSpecialHandlingRmksDTO value) {
        this.specialHandlingRmksDTOArray = value;
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
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the writtenApprovalRequired property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWrittenApprovalRequired() {
        return writtenApprovalRequired;
    }

    /**
     * Sets the value of the writtenApprovalRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWrittenApprovalRequired(String value) {
        this.writtenApprovalRequired = value;
    }

    /**
     * Gets the value of the wipRmksDTOArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfWIPRmksDTO }
     *     
     */
    public ArrayOfWIPRmksDTO getWIPRmksDTOArray() {
        return wipRmksDTOArray;
    }

    /**
     * Sets the value of the wipRmksDTOArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWIPRmksDTO }
     *     
     */
    public void setWIPRmksDTOArray(ArrayOfWIPRmksDTO value) {
        this.wipRmksDTOArray = value;
    }

    /**
     * Gets the value of the concentInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConcentInd() {
        return concentInd;
    }

    /**
     * Sets the value of the concentInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConcentInd(String value) {
        this.concentInd = value;
    }

    /**
     * Gets the value of the totalOsAmt property.
     * 
     */
    public double getTotalOsAmt() {
        return totalOsAmt;
    }

    /**
     * Sets the value of the totalOsAmt property.
     * 
     */
    public void setTotalOsAmt(double value) {
        this.totalOsAmt = value;
    }

    /**
     * Gets the value of the writtenOffAmt property.
     * 
     */
    public double getWrittenOffAmt() {
        return writtenOffAmt;
    }

    /**
     * Sets the value of the writtenOffAmt property.
     * 
     */
    public void setWrittenOffAmt(double value) {
        this.writtenOffAmt = value;
    }

    /**
     * Gets the value of the isHktStaff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsHktStaff() {
        return isHktStaff;
    }

    /**
     * Sets the value of the isHktStaff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsHktStaff(String value) {
        this.isHktStaff = value;
    }

    /**
     * Gets the value of the isJunior property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsJunior() {
        return isJunior;
    }

    /**
     * Sets the value of the isJunior property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsJunior(String value) {
        this.isJunior = value;
    }

    /**
     * Gets the value of the isBirthday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsBirthday() {
        return isBirthday;
    }

    /**
     * Sets the value of the isBirthday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsBirthday(String value) {
        this.isBirthday = value;
    }

    /**
     * Gets the value of the isCallLog property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsCallLog() {
        return isCallLog;
    }

    /**
     * Sets the value of the isCallLog property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsCallLog(String value) {
        this.isCallLog = value;
    }

    /**
     * Gets the value of the isVACust property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsVACust() {
        return isVACust;
    }

    /**
     * Sets the value of the isVACust property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsVACust(String value) {
        this.isVACust = value;
    }

    /**
     * Gets the value of the custTier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustTier() {
        return custTier;
    }

    /**
     * Sets the value of the custTier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustTier(String value) {
        this.custTier = value;
    }

    /**
     * Gets the value of the hkidCollectInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHkidCollectInd() {
        return hkidCollectInd;
    }

    /**
     * Sets the value of the hkidCollectInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHkidCollectInd(String value) {
        this.hkidCollectInd = value;
    }

    /**
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationality(String value) {
        this.nationality = value;
    }

    /**
     * Gets the value of the serverInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerInd() {
        return serverInd;
    }

    /**
     * Sets the value of the serverInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerInd(String value) {
        this.serverInd = value;
    }

    /**
     * Gets the value of the ccCreateDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCcCreateDate() {
        return ccCreateDate;
    }

    /**
     * Sets the value of the ccCreateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCcCreateDate(XMLGregorianCalendar value) {
        this.ccCreateDate = value;
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
     * Gets the value of the tierType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTierType() {
        return tierType;
    }

    /**
     * Sets the value of the tierType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTierType(String value) {
        this.tierType = value;
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
     * Gets the value of the optOutInfoGroupDTO property.
     * 
     * @return
     *     possible object is
     *     {@link OptOutInfoGroupDTO }
     *     
     */
    public OptOutInfoGroupDTO getOptOutInfoGroupDTO() {
        return optOutInfoGroupDTO;
    }

    /**
     * Sets the value of the optOutInfoGroupDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link OptOutInfoGroupDTO }
     *     
     */
    public void setOptOutInfoGroupDTO(OptOutInfoGroupDTO value) {
        this.optOutInfoGroupDTO = value;
    }

    /**
     * Gets the value of the subscribeAgrOnlyInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscribeAgrOnlyInd() {
        return subscribeAgrOnlyInd;
    }

    /**
     * Sets the value of the subscribeAgrOnlyInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscribeAgrOnlyInd(String value) {
        this.subscribeAgrOnlyInd = value;
    }

    /**
     * Gets the value of the hasSrvLineInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasSrvLineInd() {
        return hasSrvLineInd;
    }

    /**
     * Sets the value of the hasSrvLineInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasSrvLineInd(String value) {
        this.hasSrvLineInd = value;
    }

    /**
     * Gets the value of the fromAcctnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromAcctnum() {
        return fromAcctnum;
    }

    /**
     * Sets the value of the fromAcctnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromAcctnum(String value) {
        this.fromAcctnum = value;
    }

    /**
     * Gets the value of the fromCustFname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromCustFname() {
        return fromCustFname;
    }

    /**
     * Sets the value of the fromCustFname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromCustFname(String value) {
        this.fromCustFname = value;
    }

    /**
     * Gets the value of the fromCustLname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromCustLname() {
        return fromCustLname;
    }

    /**
     * Sets the value of the fromCustLname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromCustLname(String value) {
        this.fromCustLname = value;
    }

    /**
     * Gets the value of the fromCustnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromCustnum() {
        return fromCustnum;
    }

    /**
     * Sets the value of the fromCustnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromCustnum(String value) {
        this.fromCustnum = value;
    }

    /**
     * Gets the value of the fromIdDocNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromIdDocNum() {
        return fromIdDocNum;
    }

    /**
     * Sets the value of the fromIdDocNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromIdDocNum(String value) {
        this.fromIdDocNum = value;
    }

    /**
     * Gets the value of the fromIdDocType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromIdDocType() {
        return fromIdDocType;
    }

    /**
     * Sets the value of the fromIdDocType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromIdDocType(String value) {
        this.fromIdDocType = value;
    }

    /**
     * Gets the value of the lastUpdDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdDate() {
        return lastUpdDate;
    }

    /**
     * Sets the value of the lastUpdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdDate(String value) {
        this.lastUpdDate = value;
    }

    /**
     * Gets the value of the legacyOrdNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyOrdNum() {
        return legacyOrdNum;
    }

    /**
     * Sets the value of the legacyOrdNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyOrdNum(String value) {
        this.legacyOrdNum = value;
    }

    /**
     * Gets the value of the ocID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOcID() {
        return ocID;
    }

    /**
     * Sets the value of the ocID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOcID(String value) {
        this.ocID = value;
    }

    /**
     * Gets the value of the optOutInfoDTOArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOptOutInfoDTO }
     *     
     */
    public ArrayOfOptOutInfoDTO getOptOutInfoDTOArray() {
        return optOutInfoDTOArray;
    }

    /**
     * Sets the value of the optOutInfoDTOArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOptOutInfoDTO }
     *     
     */
    public void setOptOutInfoDTOArray(ArrayOfOptOutInfoDTO value) {
        this.optOutInfoDTOArray = value;
    }

    /**
     * Gets the value of the rLastUpdby property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRLastUpdby() {
        return rLastUpdby;
    }

    /**
     * Sets the value of the rLastUpdby property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRLastUpdby(String value) {
        this.rLastUpdby = value;
    }

    /**
     * Gets the value of the srvID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvID() {
        return srvID;
    }

    /**
     * Sets the value of the srvID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvID(String value) {
        this.srvID = value;
    }

    /**
     * Gets the value of the toAcctnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToAcctnum() {
        return toAcctnum;
    }

    /**
     * Sets the value of the toAcctnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToAcctnum(String value) {
        this.toAcctnum = value;
    }

    /**
     * Gets the value of the toCustFname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToCustFname() {
        return toCustFname;
    }

    /**
     * Sets the value of the toCustFname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToCustFname(String value) {
        this.toCustFname = value;
    }

    /**
     * Gets the value of the toCustLname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToCustLname() {
        return toCustLname;
    }

    /**
     * Sets the value of the toCustLname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToCustLname(String value) {
        this.toCustLname = value;
    }

    /**
     * Gets the value of the toCustnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToCustnum() {
        return toCustnum;
    }

    /**
     * Sets the value of the toCustnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToCustnum(String value) {
        this.toCustnum = value;
    }

    /**
     * Gets the value of the toIdDocNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToIdDocNum() {
        return toIdDocNum;
    }

    /**
     * Sets the value of the toIdDocNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToIdDocNum(String value) {
        this.toIdDocNum = value;
    }

    /**
     * Gets the value of the toIdDocType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToIdDocType() {
        return toIdDocType;
    }

    /**
     * Sets the value of the toIdDocType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToIdDocType(String value) {
        this.toIdDocType = value;
    }

}

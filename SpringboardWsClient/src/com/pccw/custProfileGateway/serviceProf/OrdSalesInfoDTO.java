
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdSalesInfoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdSalesInfoDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="AgentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AsmContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AsmFax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AsmName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CallListID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OverseaBusinessManager" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OverseaSalesChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OverseaSalesmanID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OverseaSalesTeam" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProjectID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContractId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContractDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RefereeSalesmanID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesmanID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesTeam" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApplicationMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CfoType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InOutBound" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SerialNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StaffID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdSalesInfoDTO" minOccurs="0"/>
 *         &lt;element name="SalesmanReferenceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdSalesInfoDTO", propOrder = {
    "agentCode",
    "asmContact",
    "asmFax",
    "asmName",
    "callListID",
    "overseaBusinessManager",
    "overseaSalesChannel",
    "overseaSalesmanID",
    "overseaSalesTeam",
    "projectID",
    "contractId",
    "contractDesc",
    "refereeSalesmanID",
    "salesChannel",
    "salesmanID",
    "salesTeam",
    "applicationMethod",
    "cfoType",
    "inOutBound",
    "salesType",
    "serialNum",
    "sourceCode",
    "staffID",
    "profileImage",
    "salesmanReferenceID"
})
public class OrdSalesInfoDTO
    extends ValueObject
{

    @XmlElement(name = "AgentCode")
    protected String agentCode;
    @XmlElement(name = "AsmContact")
    protected String asmContact;
    @XmlElement(name = "AsmFax")
    protected String asmFax;
    @XmlElement(name = "AsmName")
    protected String asmName;
    @XmlElement(name = "CallListID")
    protected String callListID;
    @XmlElement(name = "OverseaBusinessManager")
    protected String overseaBusinessManager;
    @XmlElement(name = "OverseaSalesChannel")
    protected String overseaSalesChannel;
    @XmlElement(name = "OverseaSalesmanID")
    protected String overseaSalesmanID;
    @XmlElement(name = "OverseaSalesTeam")
    protected String overseaSalesTeam;
    @XmlElement(name = "ProjectID")
    protected String projectID;
    @XmlElement(name = "ContractId")
    protected String contractId;
    @XmlElement(name = "ContractDesc")
    protected String contractDesc;
    @XmlElement(name = "RefereeSalesmanID")
    protected String refereeSalesmanID;
    @XmlElement(name = "SalesChannel")
    protected String salesChannel;
    @XmlElement(name = "SalesmanID")
    protected String salesmanID;
    @XmlElement(name = "SalesTeam")
    protected String salesTeam;
    @XmlElement(name = "ApplicationMethod")
    protected String applicationMethod;
    @XmlElement(name = "CfoType")
    protected String cfoType;
    @XmlElement(name = "InOutBound")
    protected String inOutBound;
    @XmlElement(name = "SalesType")
    protected String salesType;
    @XmlElement(name = "SerialNum")
    protected String serialNum;
    @XmlElement(name = "SourceCode")
    protected String sourceCode;
    @XmlElement(name = "StaffID")
    protected String staffID;
    @XmlElement(name = "ProfileImage")
    protected OrdSalesInfoDTO profileImage;
    @XmlElement(name = "SalesmanReferenceID")
    protected String salesmanReferenceID;

    /**
     * Gets the value of the agentCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentCode() {
        return agentCode;
    }

    /**
     * Sets the value of the agentCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentCode(String value) {
        this.agentCode = value;
    }

    /**
     * Gets the value of the asmContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsmContact() {
        return asmContact;
    }

    /**
     * Sets the value of the asmContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsmContact(String value) {
        this.asmContact = value;
    }

    /**
     * Gets the value of the asmFax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsmFax() {
        return asmFax;
    }

    /**
     * Sets the value of the asmFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsmFax(String value) {
        this.asmFax = value;
    }

    /**
     * Gets the value of the asmName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsmName() {
        return asmName;
    }

    /**
     * Sets the value of the asmName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsmName(String value) {
        this.asmName = value;
    }

    /**
     * Gets the value of the callListID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallListID() {
        return callListID;
    }

    /**
     * Sets the value of the callListID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallListID(String value) {
        this.callListID = value;
    }

    /**
     * Gets the value of the overseaBusinessManager property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverseaBusinessManager() {
        return overseaBusinessManager;
    }

    /**
     * Sets the value of the overseaBusinessManager property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverseaBusinessManager(String value) {
        this.overseaBusinessManager = value;
    }

    /**
     * Gets the value of the overseaSalesChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverseaSalesChannel() {
        return overseaSalesChannel;
    }

    /**
     * Sets the value of the overseaSalesChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverseaSalesChannel(String value) {
        this.overseaSalesChannel = value;
    }

    /**
     * Gets the value of the overseaSalesmanID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverseaSalesmanID() {
        return overseaSalesmanID;
    }

    /**
     * Sets the value of the overseaSalesmanID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverseaSalesmanID(String value) {
        this.overseaSalesmanID = value;
    }

    /**
     * Gets the value of the overseaSalesTeam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverseaSalesTeam() {
        return overseaSalesTeam;
    }

    /**
     * Sets the value of the overseaSalesTeam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverseaSalesTeam(String value) {
        this.overseaSalesTeam = value;
    }

    /**
     * Gets the value of the projectID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectID() {
        return projectID;
    }

    /**
     * Sets the value of the projectID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectID(String value) {
        this.projectID = value;
    }

    /**
     * Gets the value of the contractId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * Sets the value of the contractId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractId(String value) {
        this.contractId = value;
    }

    /**
     * Gets the value of the contractDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractDesc() {
        return contractDesc;
    }

    /**
     * Sets the value of the contractDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractDesc(String value) {
        this.contractDesc = value;
    }

    /**
     * Gets the value of the refereeSalesmanID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefereeSalesmanID() {
        return refereeSalesmanID;
    }

    /**
     * Sets the value of the refereeSalesmanID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefereeSalesmanID(String value) {
        this.refereeSalesmanID = value;
    }

    /**
     * Gets the value of the salesChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesChannel() {
        return salesChannel;
    }

    /**
     * Sets the value of the salesChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesChannel(String value) {
        this.salesChannel = value;
    }

    /**
     * Gets the value of the salesmanID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesmanID() {
        return salesmanID;
    }

    /**
     * Sets the value of the salesmanID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesmanID(String value) {
        this.salesmanID = value;
    }

    /**
     * Gets the value of the salesTeam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesTeam() {
        return salesTeam;
    }

    /**
     * Sets the value of the salesTeam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesTeam(String value) {
        this.salesTeam = value;
    }

    /**
     * Gets the value of the applicationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationMethod() {
        return applicationMethod;
    }

    /**
     * Sets the value of the applicationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationMethod(String value) {
        this.applicationMethod = value;
    }

    /**
     * Gets the value of the cfoType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfoType() {
        return cfoType;
    }

    /**
     * Sets the value of the cfoType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfoType(String value) {
        this.cfoType = value;
    }

    /**
     * Gets the value of the inOutBound property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInOutBound() {
        return inOutBound;
    }

    /**
     * Sets the value of the inOutBound property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInOutBound(String value) {
        this.inOutBound = value;
    }

    /**
     * Gets the value of the salesType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesType() {
        return salesType;
    }

    /**
     * Sets the value of the salesType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesType(String value) {
        this.salesType = value;
    }

    /**
     * Gets the value of the serialNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * Sets the value of the serialNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNum(String value) {
        this.serialNum = value;
    }

    /**
     * Gets the value of the sourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceCode() {
        return sourceCode;
    }

    /**
     * Sets the value of the sourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceCode(String value) {
        this.sourceCode = value;
    }

    /**
     * Gets the value of the staffID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaffID() {
        return staffID;
    }

    /**
     * Sets the value of the staffID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaffID(String value) {
        this.staffID = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdSalesInfoDTO }
     *     
     */
    public OrdSalesInfoDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdSalesInfoDTO }
     *     
     */
    public void setProfileImage(OrdSalesInfoDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the salesmanReferenceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesmanReferenceID() {
        return salesmanReferenceID;
    }

    /**
     * Sets the value of the salesmanReferenceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesmanReferenceID(String value) {
        this.salesmanReferenceID = value;
    }

}

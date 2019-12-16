
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerProfileDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerProfileDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustCatg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GrpIdDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GrpIdDocType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdDocType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdVerifyInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IndustryType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IndustrySubType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustAddressDTO" type="{http://www.openuri.org/}CustAddressDTO" minOccurs="0"/>
 *         &lt;element name="CustPrimaryContactInfoDTO" type="{http://www.openuri.org/}CustPrimaryContactInfoDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerProfileDTO", propOrder = {
    "systemId",
    "title",
    "custCatg",
    "custFirstName",
    "custLastName",
    "companyName",
    "custType",
    "grpIdDocNum",
    "grpIdDocType",
    "idDocNum",
    "idDocType",
    "idVerifyInd",
    "industryType",
    "industrySubType",
    "lastUpdBy",
    "dob",
    "custAddressDTO",
    "custPrimaryContactInfoDTO"
})
public class CustomerProfileDTO {

    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "CustCatg")
    protected String custCatg;
    @XmlElement(name = "CustFirstName")
    protected String custFirstName;
    @XmlElement(name = "CustLastName")
    protected String custLastName;
    @XmlElement(name = "CompanyName")
    protected String companyName;
    @XmlElement(name = "CustType")
    protected String custType;
    @XmlElement(name = "GrpIdDocNum")
    protected String grpIdDocNum;
    @XmlElement(name = "GrpIdDocType")
    protected String grpIdDocType;
    @XmlElement(name = "IdDocNum")
    protected String idDocNum;
    @XmlElement(name = "IdDocType")
    protected String idDocType;
    @XmlElement(name = "IdVerifyInd")
    protected String idVerifyInd;
    @XmlElement(name = "IndustryType")
    protected String industryType;
    @XmlElement(name = "IndustrySubType")
    protected String industrySubType;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    @XmlElement(name = "Dob")
    protected String dob;
    @XmlElement(name = "CustAddressDTO")
    protected CustAddressDTO custAddressDTO;
    @XmlElement(name = "CustPrimaryContactInfoDTO")
    protected CustPrimaryContactInfoDTO custPrimaryContactInfoDTO;

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
     * Gets the value of the custAddressDTO property.
     * 
     * @return
     *     possible object is
     *     {@link CustAddressDTO }
     *     
     */
    public CustAddressDTO getCustAddressDTO() {
        return custAddressDTO;
    }

    /**
     * Sets the value of the custAddressDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustAddressDTO }
     *     
     */
    public void setCustAddressDTO(CustAddressDTO value) {
        this.custAddressDTO = value;
    }

    /**
     * Gets the value of the custPrimaryContactInfoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link CustPrimaryContactInfoDTO }
     *     
     */
    public CustPrimaryContactInfoDTO getCustPrimaryContactInfoDTO() {
        return custPrimaryContactInfoDTO;
    }

    /**
     * Sets the value of the custPrimaryContactInfoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustPrimaryContactInfoDTO }
     *     
     */
    public void setCustPrimaryContactInfoDTO(CustPrimaryContactInfoDTO value) {
        this.custPrimaryContactInfoDTO = value;
    }

}

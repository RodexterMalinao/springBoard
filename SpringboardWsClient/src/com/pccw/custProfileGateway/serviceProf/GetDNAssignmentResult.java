
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="srvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="boc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="projectCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="srvType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialSrvGrp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialSrvName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "srvNum",
    "accountCd",
    "boc",
    "projectCd",
    "srvType",
    "datCd",
    "specialSrvGrp",
    "specialSrvName"
})
@XmlRootElement(name = "getDNAssignmentResult")
public class GetDNAssignmentResult {

    protected String srvNum;
    protected String accountCd;
    protected String boc;
    protected String projectCd;
    protected String srvType;
    protected String datCd;
    protected String specialSrvGrp;
    protected String specialSrvName;

    /**
     * Gets the value of the srvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvNum() {
        return srvNum;
    }

    /**
     * Sets the value of the srvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvNum(String value) {
        this.srvNum = value;
    }

    /**
     * Gets the value of the accountCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountCd() {
        return accountCd;
    }

    /**
     * Sets the value of the accountCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountCd(String value) {
        this.accountCd = value;
    }

    /**
     * Gets the value of the boc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBoc() {
        return boc;
    }

    /**
     * Sets the value of the boc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBoc(String value) {
        this.boc = value;
    }

    /**
     * Gets the value of the projectCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectCd() {
        return projectCd;
    }

    /**
     * Sets the value of the projectCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectCd(String value) {
        this.projectCd = value;
    }

    /**
     * Gets the value of the srvType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvType() {
        return srvType;
    }

    /**
     * Sets the value of the srvType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvType(String value) {
        this.srvType = value;
    }

    /**
     * Gets the value of the datCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatCd() {
        return datCd;
    }

    /**
     * Sets the value of the datCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatCd(String value) {
        this.datCd = value;
    }

    /**
     * Gets the value of the specialSrvGrp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialSrvGrp() {
        return specialSrvGrp;
    }

    /**
     * Sets the value of the specialSrvGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialSrvGrp(String value) {
        this.specialSrvGrp = value;
    }

    /**
     * Gets the value of the specialSrvName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialSrvName() {
        return specialSrvName;
    }

    /**
     * Sets the value of the specialSrvName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialSrvName(String value) {
        this.specialSrvName = value;
    }

}

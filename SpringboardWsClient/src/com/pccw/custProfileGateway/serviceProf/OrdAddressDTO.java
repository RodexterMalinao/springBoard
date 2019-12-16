
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdAddressDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdAddressDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AddressID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistingAddressID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdAddressDTO" minOccurs="0"/>
 *         &lt;element name="SiteOfficeInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BuildID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdAddressDTO", propOrder = {
    "action",
    "addressID",
    "existingAddressID",
    "profileImage",
    "siteOfficeInd",
    "buildID"
})
public class OrdAddressDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "AddressID")
    protected String addressID;
    @XmlElement(name = "ExistingAddressID")
    protected String existingAddressID;
    @XmlElement(name = "ProfileImage")
    protected OrdAddressDTO profileImage;
    @XmlElement(name = "SiteOfficeInd")
    protected String siteOfficeInd;
    @XmlElement(name = "BuildID")
    protected String buildID;

    /**
     * Gets the value of the action property.
     * 
     */
    public int getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     */
    public void setAction(int value) {
        this.action = value;
    }

    /**
     * Gets the value of the addressID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressID() {
        return addressID;
    }

    /**
     * Sets the value of the addressID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressID(String value) {
        this.addressID = value;
    }

    /**
     * Gets the value of the existingAddressID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistingAddressID() {
        return existingAddressID;
    }

    /**
     * Sets the value of the existingAddressID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistingAddressID(String value) {
        this.existingAddressID = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdAddressDTO }
     *     
     */
    public OrdAddressDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdAddressDTO }
     *     
     */
    public void setProfileImage(OrdAddressDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the siteOfficeInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteOfficeInd() {
        return siteOfficeInd;
    }

    /**
     * Sets the value of the siteOfficeInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteOfficeInd(String value) {
        this.siteOfficeInd = value;
    }

    /**
     * Gets the value of the buildID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuildID() {
        return buildID;
    }

    /**
     * Sets the value of the buildID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuildID(String value) {
        this.buildID = value;
    }

}

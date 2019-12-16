
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdDetailGroupDetailDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdDetailGroupDetailDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EnDiversityInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdDetailGroupDetailDTO" minOccurs="0"/>
 *         &lt;element name="ExistingEnDiversityInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NetworkInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistingNetworkInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdDetailGroupDetailDTO", propOrder = {
    "action",
    "enDiversityInd",
    "profileImage",
    "existingEnDiversityInd",
    "networkInd",
    "existingNetworkInd"
})
public class OrdDetailGroupDetailDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "EnDiversityInd")
    protected String enDiversityInd;
    @XmlElement(name = "ProfileImage")
    protected OrdDetailGroupDetailDTO profileImage;
    @XmlElement(name = "ExistingEnDiversityInd")
    protected String existingEnDiversityInd;
    @XmlElement(name = "NetworkInd")
    protected String networkInd;
    @XmlElement(name = "ExistingNetworkInd")
    protected String existingNetworkInd;

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
     * Gets the value of the enDiversityInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnDiversityInd() {
        return enDiversityInd;
    }

    /**
     * Sets the value of the enDiversityInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnDiversityInd(String value) {
        this.enDiversityInd = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDetailGroupDetailDTO }
     *     
     */
    public OrdDetailGroupDetailDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDetailGroupDetailDTO }
     *     
     */
    public void setProfileImage(OrdDetailGroupDetailDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the existingEnDiversityInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistingEnDiversityInd() {
        return existingEnDiversityInd;
    }

    /**
     * Sets the value of the existingEnDiversityInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistingEnDiversityInd(String value) {
        this.existingEnDiversityInd = value;
    }

    /**
     * Gets the value of the networkInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetworkInd() {
        return networkInd;
    }

    /**
     * Sets the value of the networkInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetworkInd(String value) {
        this.networkInd = value;
    }

    /**
     * Gets the value of the existingNetworkInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistingNetworkInd() {
        return existingNetworkInd;
    }

    /**
     * Sets the value of the existingNetworkInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistingNetworkInd(String value) {
        this.existingNetworkInd = value;
    }

}

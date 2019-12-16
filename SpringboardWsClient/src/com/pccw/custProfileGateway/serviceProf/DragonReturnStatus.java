
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DragonReturnStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DragonReturnStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrorDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrorDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrorMachineid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrorMnemonic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrorSeverity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DragonReturnStatus", propOrder = {
    "errorDesc",
    "errorDetail",
    "errorMachineid",
    "errorMnemonic",
    "errorSeverity"
})
public class DragonReturnStatus {

    @XmlElement(name = "ErrorDesc")
    protected String errorDesc;
    @XmlElement(name = "ErrorDetail")
    protected String errorDetail;
    @XmlElement(name = "ErrorMachineid")
    protected String errorMachineid;
    @XmlElement(name = "ErrorMnemonic")
    protected String errorMnemonic;
    @XmlElement(name = "ErrorSeverity")
    protected String errorSeverity;

    /**
     * Gets the value of the errorDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * Sets the value of the errorDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDesc(String value) {
        this.errorDesc = value;
    }

    /**
     * Gets the value of the errorDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorDetail() {
        return errorDetail;
    }

    /**
     * Sets the value of the errorDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDetail(String value) {
        this.errorDetail = value;
    }

    /**
     * Gets the value of the errorMachineid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMachineid() {
        return errorMachineid;
    }

    /**
     * Sets the value of the errorMachineid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMachineid(String value) {
        this.errorMachineid = value;
    }

    /**
     * Gets the value of the errorMnemonic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMnemonic() {
        return errorMnemonic;
    }

    /**
     * Sets the value of the errorMnemonic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMnemonic(String value) {
        this.errorMnemonic = value;
    }

    /**
     * Gets the value of the errorSeverity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorSeverity() {
        return errorSeverity;
    }

    /**
     * Sets the value of the errorSeverity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorSeverity(String value) {
        this.errorSeverity = value;
    }

}

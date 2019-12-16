
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationRemarkReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationRemarkReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrdactvSeqNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RmkLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInformationRemarkReturn", propOrder = {
    "orderId",
    "ordactvSeqNum",
    "rmkLine"
})
public class OrderInformationRemarkReturn {

    @XmlElement(name = "OrderId")
    protected String orderId;
    @XmlElement(name = "OrdactvSeqNum")
    protected int ordactvSeqNum;
    @XmlElement(name = "RmkLine")
    protected String rmkLine;

    /**
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderId(String value) {
        this.orderId = value;
    }

    /**
     * Gets the value of the ordactvSeqNum property.
     * 
     */
    public int getOrdactvSeqNum() {
        return ordactvSeqNum;
    }

    /**
     * Sets the value of the ordactvSeqNum property.
     * 
     */
    public void setOrdactvSeqNum(int value) {
        this.ordactvSeqNum = value;
    }

    /**
     * Gets the value of the rmkLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRmkLine() {
        return rmkLine;
    }

    /**
     * Sets the value of the rmkLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRmkLine(String value) {
        this.rmkLine = value;
    }

}

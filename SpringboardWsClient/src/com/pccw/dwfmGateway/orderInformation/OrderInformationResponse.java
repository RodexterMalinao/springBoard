
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ServiceResponseDTO">
 *       &lt;sequence>
 *         &lt;element name="OrderInformationOutputList" type="{http://www.openuri.org/}OrderInformationOutList" minOccurs="0"/>
 *         &lt;element name="DetermineWkgReturnList" type="{http://www.openuri.org/}DetermineWkgReturnList" minOccurs="0"/>
 *         &lt;element name="OrderDetailsOutList" type="{http://www.openuri.org/}OrderDetailsOutList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInformationResponse", propOrder = {
    "orderInformationOutputList",
    "determineWkgReturnList",
    "orderDetailsOutList"
})
public class OrderInformationResponse
    extends ServiceResponseDTO
{

    @XmlElement(name = "OrderInformationOutputList")
    protected OrderInformationOutList orderInformationOutputList;
    @XmlElement(name = "DetermineWkgReturnList")
    protected DetermineWkgReturnList determineWkgReturnList;
    @XmlElement(name = "OrderDetailsOutList")
    protected OrderDetailsOutList orderDetailsOutList;

    /**
     * Gets the value of the orderInformationOutputList property.
     * 
     * @return
     *     possible object is
     *     {@link OrderInformationOutList }
     *     
     */
    public OrderInformationOutList getOrderInformationOutputList() {
        return orderInformationOutputList;
    }

    /**
     * Sets the value of the orderInformationOutputList property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderInformationOutList }
     *     
     */
    public void setOrderInformationOutputList(OrderInformationOutList value) {
        this.orderInformationOutputList = value;
    }

    /**
     * Gets the value of the determineWkgReturnList property.
     * 
     * @return
     *     possible object is
     *     {@link DetermineWkgReturnList }
     *     
     */
    public DetermineWkgReturnList getDetermineWkgReturnList() {
        return determineWkgReturnList;
    }

    /**
     * Sets the value of the determineWkgReturnList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetermineWkgReturnList }
     *     
     */
    public void setDetermineWkgReturnList(DetermineWkgReturnList value) {
        this.determineWkgReturnList = value;
    }

    /**
     * Gets the value of the orderDetailsOutList property.
     * 
     * @return
     *     possible object is
     *     {@link OrderDetailsOutList }
     *     
     */
    public OrderDetailsOutList getOrderDetailsOutList() {
        return orderDetailsOutList;
    }

    /**
     * Sets the value of the orderDetailsOutList property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderDetailsOutList }
     *     
     */
    public void setOrderDetailsOutList(OrderDetailsOutList value) {
        this.orderDetailsOutList = value;
    }

}


package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrderInformationRemarkReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrderInformationRemarkReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderInformationRemarkReturn" type="{http://www.openuri.org/}OrderInformationRemarkReturn" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrderInformationRemarkReturn", propOrder = {
    "orderInformationRemarkReturn"
})
public class ArrayOfOrderInformationRemarkReturn {

    @XmlElement(name = "OrderInformationRemarkReturn", nillable = true)
    protected List<OrderInformationRemarkReturn> orderInformationRemarkReturn;

    /**
     * Gets the value of the orderInformationRemarkReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderInformationRemarkReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderInformationRemarkReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderInformationRemarkReturn }
     * 
     * 
     */
    public List<OrderInformationRemarkReturn> getOrderInformationRemarkReturn() {
        if (orderInformationRemarkReturn == null) {
            orderInformationRemarkReturn = new ArrayList<OrderInformationRemarkReturn>();
        }
        return this.orderInformationRemarkReturn;
    }

}


package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrderInformationOriDataReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrderInformationOriDataReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderInformationOriDataReturn" type="{http://www.openuri.org/}OrderInformationOriDataReturn" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrderInformationOriDataReturn", propOrder = {
    "orderInformationOriDataReturn"
})
public class ArrayOfOrderInformationOriDataReturn {

    @XmlElement(name = "OrderInformationOriDataReturn", nillable = true)
    protected List<OrderInformationOriDataReturn> orderInformationOriDataReturn;

    /**
     * Gets the value of the orderInformationOriDataReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderInformationOriDataReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderInformationOriDataReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderInformationOriDataReturn }
     * 
     * 
     */
    public List<OrderInformationOriDataReturn> getOrderInformationOriDataReturn() {
        if (orderInformationOriDataReturn == null) {
            orderInformationOriDataReturn = new ArrayList<OrderInformationOriDataReturn>();
        }
        return this.orderInformationOriDataReturn;
    }

}

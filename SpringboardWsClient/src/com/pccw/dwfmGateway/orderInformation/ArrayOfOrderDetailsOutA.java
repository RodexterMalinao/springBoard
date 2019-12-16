
package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrderDetailsOutA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrderDetailsOutA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderDetailsOutA" type="{http://www.openuri.org/}OrderDetailsOutA" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrderDetailsOutA", propOrder = {
    "orderDetailsOutA"
})
public class ArrayOfOrderDetailsOutA {

    @XmlElement(name = "OrderDetailsOutA", nillable = true)
    protected List<OrderDetailsOutA> orderDetailsOutA;

    /**
     * Gets the value of the orderDetailsOutA property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderDetailsOutA property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderDetailsOutA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderDetailsOutA }
     * 
     * 
     */
    public List<OrderDetailsOutA> getOrderDetailsOutA() {
        if (orderDetailsOutA == null) {
            orderDetailsOutA = new ArrayList<OrderDetailsOutA>();
        }
        return this.orderDetailsOutA;
    }

}

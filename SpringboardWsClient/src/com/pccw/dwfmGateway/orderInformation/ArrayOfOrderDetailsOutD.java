
package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrderDetailsOutD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrderDetailsOutD">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderDetailsOutD" type="{http://www.openuri.org/}OrderDetailsOutD" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrderDetailsOutD", propOrder = {
    "orderDetailsOutD"
})
public class ArrayOfOrderDetailsOutD {

    @XmlElement(name = "OrderDetailsOutD", nillable = true)
    protected List<OrderDetailsOutD> orderDetailsOutD;

    /**
     * Gets the value of the orderDetailsOutD property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderDetailsOutD property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderDetailsOutD().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderDetailsOutD }
     * 
     * 
     */
    public List<OrderDetailsOutD> getOrderDetailsOutD() {
        if (orderDetailsOutD == null) {
            orderDetailsOutD = new ArrayList<OrderDetailsOutD>();
        }
        return this.orderDetailsOutD;
    }

}

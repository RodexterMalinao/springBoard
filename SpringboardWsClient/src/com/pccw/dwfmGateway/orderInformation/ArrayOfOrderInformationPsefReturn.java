
package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrderInformationPsefReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrderInformationPsefReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderInformationPsefReturn" type="{http://www.openuri.org/}OrderInformationPsefReturn" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrderInformationPsefReturn", propOrder = {
    "orderInformationPsefReturn"
})
public class ArrayOfOrderInformationPsefReturn {

    @XmlElement(name = "OrderInformationPsefReturn", nillable = true)
    protected List<OrderInformationPsefReturn> orderInformationPsefReturn;

    /**
     * Gets the value of the orderInformationPsefReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderInformationPsefReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderInformationPsefReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderInformationPsefReturn }
     * 
     * 
     */
    public List<OrderInformationPsefReturn> getOrderInformationPsefReturn() {
        if (orderInformationPsefReturn == null) {
            orderInformationPsefReturn = new ArrayList<OrderInformationPsefReturn>();
        }
        return this.orderInformationPsefReturn;
    }

}

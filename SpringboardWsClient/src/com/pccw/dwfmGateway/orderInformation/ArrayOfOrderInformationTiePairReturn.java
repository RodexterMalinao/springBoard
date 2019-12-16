
package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrderInformationTiePairReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrderInformationTiePairReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderInformationTiePairReturn" type="{http://www.openuri.org/}OrderInformationTiePairReturn" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrderInformationTiePairReturn", propOrder = {
    "orderInformationTiePairReturn"
})
public class ArrayOfOrderInformationTiePairReturn {

    @XmlElement(name = "OrderInformationTiePairReturn", nillable = true)
    protected List<OrderInformationTiePairReturn> orderInformationTiePairReturn;

    /**
     * Gets the value of the orderInformationTiePairReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderInformationTiePairReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderInformationTiePairReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderInformationTiePairReturn }
     * 
     * 
     */
    public List<OrderInformationTiePairReturn> getOrderInformationTiePairReturn() {
        if (orderInformationTiePairReturn == null) {
            orderInformationTiePairReturn = new ArrayList<OrderInformationTiePairReturn>();
        }
        return this.orderInformationTiePairReturn;
    }

}


package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrderInformationOriVoiceReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrderInformationOriVoiceReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderInformationOriVoiceReturn" type="{http://www.openuri.org/}OrderInformationOriVoiceReturn" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrderInformationOriVoiceReturn", propOrder = {
    "orderInformationOriVoiceReturn"
})
public class ArrayOfOrderInformationOriVoiceReturn {

    @XmlElement(name = "OrderInformationOriVoiceReturn", nillable = true)
    protected List<OrderInformationOriVoiceReturn> orderInformationOriVoiceReturn;

    /**
     * Gets the value of the orderInformationOriVoiceReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderInformationOriVoiceReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderInformationOriVoiceReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderInformationOriVoiceReturn }
     * 
     * 
     */
    public List<OrderInformationOriVoiceReturn> getOrderInformationOriVoiceReturn() {
        if (orderInformationOriVoiceReturn == null) {
            orderInformationOriVoiceReturn = new ArrayList<OrderInformationOriVoiceReturn>();
        }
        return this.orderInformationOriVoiceReturn;
    }

}

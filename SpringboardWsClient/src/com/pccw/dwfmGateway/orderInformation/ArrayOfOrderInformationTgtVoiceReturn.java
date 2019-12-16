
package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrderInformationTgtVoiceReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrderInformationTgtVoiceReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderInformationTgtVoiceReturn" type="{http://www.openuri.org/}OrderInformationTgtVoiceReturn" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrderInformationTgtVoiceReturn", propOrder = {
    "orderInformationTgtVoiceReturn"
})
public class ArrayOfOrderInformationTgtVoiceReturn {

    @XmlElement(name = "OrderInformationTgtVoiceReturn", nillable = true)
    protected List<OrderInformationTgtVoiceReturn> orderInformationTgtVoiceReturn;

    /**
     * Gets the value of the orderInformationTgtVoiceReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderInformationTgtVoiceReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderInformationTgtVoiceReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderInformationTgtVoiceReturn }
     * 
     * 
     */
    public List<OrderInformationTgtVoiceReturn> getOrderInformationTgtVoiceReturn() {
        if (orderInformationTgtVoiceReturn == null) {
            orderInformationTgtVoiceReturn = new ArrayList<OrderInformationTgtVoiceReturn>();
        }
        return this.orderInformationTgtVoiceReturn;
    }

}

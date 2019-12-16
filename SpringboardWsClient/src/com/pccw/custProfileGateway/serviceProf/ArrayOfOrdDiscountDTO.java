
package com.pccw.custProfileGateway.serviceProf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrdDiscountDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrdDiscountDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrdDiscountDTO" type="{http://www.openuri.org/}OrdDiscountDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrdDiscountDTO", propOrder = {
    "ordDiscountDTO"
})
public class ArrayOfOrdDiscountDTO {

    @XmlElement(name = "OrdDiscountDTO", nillable = true)
    protected List<OrdDiscountDTO> ordDiscountDTO;

    /**
     * Gets the value of the ordDiscountDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordDiscountDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdDiscountDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdDiscountDTO }
     * 
     * 
     */
    public List<OrdDiscountDTO> getOrdDiscountDTO() {
        if (ordDiscountDTO == null) {
            ordDiscountDTO = new ArrayList<OrdDiscountDTO>();
        }
        return this.ordDiscountDTO;
    }

}

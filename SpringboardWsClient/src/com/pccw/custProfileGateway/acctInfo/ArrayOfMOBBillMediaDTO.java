
package com.pccw.custProfileGateway.acctInfo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfMOBBillMediaDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMOBBillMediaDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MOBBillMediaDTO" type="{http://www.openuri.org/}MOBBillMediaDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMOBBillMediaDTO", propOrder = {
    "mobBillMediaDTO"
})
public class ArrayOfMOBBillMediaDTO {

    @XmlElement(name = "MOBBillMediaDTO", nillable = true)
    protected List<MOBBillMediaDTO> mobBillMediaDTO;

    /**
     * Gets the value of the mobBillMediaDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mobBillMediaDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMOBBillMediaDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MOBBillMediaDTO }
     * 
     * 
     */
    public List<MOBBillMediaDTO> getMOBBillMediaDTO() {
        if (mobBillMediaDTO == null) {
            mobBillMediaDTO = new ArrayList<MOBBillMediaDTO>();
        }
        return this.mobBillMediaDTO;
    }

}

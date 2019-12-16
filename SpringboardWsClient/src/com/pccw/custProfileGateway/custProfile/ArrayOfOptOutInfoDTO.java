
package com.pccw.custProfileGateway.custProfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOptOutInfoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOptOutInfoDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OptOutInfoDTO" type="{http://www.openuri.org/}OptOutInfoDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOptOutInfoDTO", propOrder = {
    "optOutInfoDTO"
})
public class ArrayOfOptOutInfoDTO {

    @XmlElement(name = "OptOutInfoDTO", nillable = true)
    protected List<OptOutInfoDTO> optOutInfoDTO;

    /**
     * Gets the value of the optOutInfoDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optOutInfoDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptOutInfoDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OptOutInfoDTO }
     * 
     * 
     */
    public List<OptOutInfoDTO> getOptOutInfoDTO() {
        if (optOutInfoDTO == null) {
            optOutInfoDTO = new ArrayList<OptOutInfoDTO>();
        }
        return this.optOutInfoDTO;
    }

}

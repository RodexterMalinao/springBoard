
package com.pccw.custProfileGateway.custProfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDataPrivacyLtsDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDataPrivacyLtsDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataPrivacyLtsDTO" type="{http://www.openuri.org/}DataPrivacyLtsDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDataPrivacyLtsDTO", propOrder = {
    "dataPrivacyLtsDTO"
})
public class ArrayOfDataPrivacyLtsDTO {

    @XmlElement(name = "DataPrivacyLtsDTO", nillable = true)
    protected List<DataPrivacyLtsDTO> dataPrivacyLtsDTO;

    /**
     * Gets the value of the dataPrivacyLtsDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataPrivacyLtsDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataPrivacyLtsDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataPrivacyLtsDTO }
     * 
     * 
     */
    public List<DataPrivacyLtsDTO> getDataPrivacyLtsDTO() {
        if (dataPrivacyLtsDTO == null) {
            dataPrivacyLtsDTO = new ArrayList<DataPrivacyLtsDTO>();
        }
        return this.dataPrivacyLtsDTO;
    }

}

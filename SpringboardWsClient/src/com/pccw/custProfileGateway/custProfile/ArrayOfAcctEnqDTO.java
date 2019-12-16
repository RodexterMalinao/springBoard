
package com.pccw.custProfileGateway.custProfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfAcctEnqDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAcctEnqDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AcctEnqDTO" type="{http://www.openuri.org/}AcctEnqDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAcctEnqDTO", propOrder = {
    "acctEnqDTO"
})
public class ArrayOfAcctEnqDTO {

    @XmlElement(name = "AcctEnqDTO", nillable = true)
    protected List<AcctEnqDTO> acctEnqDTO;

    /**
     * Gets the value of the acctEnqDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the acctEnqDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAcctEnqDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AcctEnqDTO }
     * 
     * 
     */
    public List<AcctEnqDTO> getAcctEnqDTO() {
        if (acctEnqDTO == null) {
            acctEnqDTO = new ArrayList<AcctEnqDTO>();
        }
        return this.acctEnqDTO;
    }

}

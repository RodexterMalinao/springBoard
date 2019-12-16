
package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDetermineWkgReq complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDetermineWkgReq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DetermineWkgReq" type="{http://www.openuri.org/}DetermineWkgReq" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDetermineWkgReq", propOrder = {
    "determineWkgReq"
})
public class ArrayOfDetermineWkgReq {

    @XmlElement(name = "DetermineWkgReq", nillable = true)
    protected List<DetermineWkgReq> determineWkgReq;

    /**
     * Gets the value of the determineWkgReq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the determineWkgReq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetermineWkgReq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetermineWkgReq }
     * 
     * 
     */
    public List<DetermineWkgReq> getDetermineWkgReq() {
        if (determineWkgReq == null) {
            determineWkgReq = new ArrayList<DetermineWkgReq>();
        }
        return this.determineWkgReq;
    }

}

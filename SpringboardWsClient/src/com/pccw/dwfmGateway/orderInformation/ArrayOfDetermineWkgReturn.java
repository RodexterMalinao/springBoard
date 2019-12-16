
package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDetermineWkgReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDetermineWkgReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DetermineWkgReturn" type="{http://www.openuri.org/}DetermineWkgReturn" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDetermineWkgReturn", propOrder = {
    "determineWkgReturn"
})
public class ArrayOfDetermineWkgReturn {

    @XmlElement(name = "DetermineWkgReturn", nillable = true)
    protected List<DetermineWkgReturn> determineWkgReturn;

    /**
     * Gets the value of the determineWkgReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the determineWkgReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetermineWkgReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetermineWkgReturn }
     * 
     * 
     */
    public List<DetermineWkgReturn> getDetermineWkgReturn() {
        if (determineWkgReturn == null) {
            determineWkgReturn = new ArrayList<DetermineWkgReturn>();
        }
        return this.determineWkgReturn;
    }

}


package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDetermineWkgOb complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDetermineWkgOb">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DetermineWkgOb" type="{http://www.openuri.org/}DetermineWkgOb" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDetermineWkgOb", propOrder = {
    "determineWkgOb"
})
public class ArrayOfDetermineWkgOb {

    @XmlElement(name = "DetermineWkgOb", nillable = true)
    protected List<DetermineWkgOb> determineWkgOb;

    /**
     * Gets the value of the determineWkgOb property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the determineWkgOb property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetermineWkgOb().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetermineWkgOb }
     * 
     * 
     */
    public List<DetermineWkgOb> getDetermineWkgOb() {
        if (determineWkgOb == null) {
            determineWkgOb = new ArrayList<DetermineWkgOb>();
        }
        return this.determineWkgOb;
    }

}

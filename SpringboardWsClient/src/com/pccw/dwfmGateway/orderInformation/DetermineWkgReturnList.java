
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetermineWkgReturnList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetermineWkgReturnList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DrgRtnSts" type="{http://www.openuri.org/}DragonReturnStatus" minOccurs="0"/>
 *         &lt;element name="WkgrtnArr" type="{http://www.openuri.org/}ArrayOfDetermineWkgReturn" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetermineWkgReturnList", propOrder = {
    "drgRtnSts",
    "wkgrtnArr"
})
public class DetermineWkgReturnList {

    @XmlElement(name = "DrgRtnSts")
    protected DragonReturnStatus drgRtnSts;
    @XmlElement(name = "WkgrtnArr")
    protected ArrayOfDetermineWkgReturn wkgrtnArr;

    /**
     * Gets the value of the drgRtnSts property.
     * 
     * @return
     *     possible object is
     *     {@link DragonReturnStatus }
     *     
     */
    public DragonReturnStatus getDrgRtnSts() {
        return drgRtnSts;
    }

    /**
     * Sets the value of the drgRtnSts property.
     * 
     * @param value
     *     allowed object is
     *     {@link DragonReturnStatus }
     *     
     */
    public void setDrgRtnSts(DragonReturnStatus value) {
        this.drgRtnSts = value;
    }

    /**
     * Gets the value of the wkgrtnArr property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDetermineWkgReturn }
     *     
     */
    public ArrayOfDetermineWkgReturn getWkgrtnArr() {
        return wkgrtnArr;
    }

    /**
     * Sets the value of the wkgrtnArr property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDetermineWkgReturn }
     *     
     */
    public void setWkgrtnArr(ArrayOfDetermineWkgReturn value) {
        this.wkgrtnArr = value;
    }

}

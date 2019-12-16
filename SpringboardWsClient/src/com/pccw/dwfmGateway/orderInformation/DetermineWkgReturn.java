
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetermineWkgReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetermineWkgReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cxsparmarr" type="{http://www.openuri.org/}ArrayOfCommonCxSparm" minOccurs="0"/>
 *         &lt;element name="OutAarr" type="{http://www.openuri.org/}ArrayOfDetermineWkgOa" minOccurs="0"/>
 *         &lt;element name="OutBarr" type="{http://www.openuri.org/}ArrayOfDetermineWkgOb" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetermineWkgReturn", propOrder = {
    "cxsparmarr",
    "outAarr",
    "outBarr"
})
public class DetermineWkgReturn {

    @XmlElement(name = "Cxsparmarr")
    protected ArrayOfCommonCxSparm cxsparmarr;
    @XmlElement(name = "OutAarr")
    protected ArrayOfDetermineWkgOa outAarr;
    @XmlElement(name = "OutBarr")
    protected ArrayOfDetermineWkgOb outBarr;

    /**
     * Gets the value of the cxsparmarr property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCommonCxSparm }
     *     
     */
    public ArrayOfCommonCxSparm getCxsparmarr() {
        return cxsparmarr;
    }

    /**
     * Sets the value of the cxsparmarr property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCommonCxSparm }
     *     
     */
    public void setCxsparmarr(ArrayOfCommonCxSparm value) {
        this.cxsparmarr = value;
    }

    /**
     * Gets the value of the outAarr property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDetermineWkgOa }
     *     
     */
    public ArrayOfDetermineWkgOa getOutAarr() {
        return outAarr;
    }

    /**
     * Sets the value of the outAarr property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDetermineWkgOa }
     *     
     */
    public void setOutAarr(ArrayOfDetermineWkgOa value) {
        this.outAarr = value;
    }

    /**
     * Gets the value of the outBarr property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDetermineWkgOb }
     *     
     */
    public ArrayOfDetermineWkgOb getOutBarr() {
        return outBarr;
    }

    /**
     * Sets the value of the outBarr property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDetermineWkgOb }
     *     
     */
    public void setOutBarr(ArrayOfDetermineWkgOb value) {
        this.outBarr = value;
    }

}

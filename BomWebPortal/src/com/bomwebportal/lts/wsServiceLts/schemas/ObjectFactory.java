//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.15 at 10:56:32 AM CST 
//


package com.bomwebportal.lts.wsServiceLts.schemas;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bomwebportal.lts.wsservicelts.schemas package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bomwebportal.lts.wsservicelts.schemas
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ItemDetailsProfileRequest.OfferDetailsProfile }
     * 
     */
    public ItemDetailsProfileRequest.OfferDetailsProfile createItemDetailsProfileRequestOfferDetailsProfile() {
        return new ItemDetailsProfileRequest.OfferDetailsProfile();
    }

    /**
     * Create an instance of {@link ItemDetailsProfileRequest }
     * 
     */
    public ItemDetailsProfileRequest createItemDetailsProfileRequest() {
        return new ItemDetailsProfileRequest();
    }

    /**
     * Create an instance of {@link ItemDetailsProfileResponse }
     * 
     */
    public ItemDetailsProfileResponse createItemDetailsProfileResponse() {
        return new ItemDetailsProfileResponse();
    }

    /**
     * Create an instance of {@link ItemDetailsProfileResponse.OfferDetailsResult }
     * 
     */
    public ItemDetailsProfileResponse.OfferDetailsResult createItemDetailsProfileResponseOfferDetailsResult() {
        return new ItemDetailsProfileResponse.OfferDetailsResult();
    }

}
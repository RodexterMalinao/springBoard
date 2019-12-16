
package com.pccw.appendOrdRmk;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.pccw.appendOrdRmk package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.pccw.appendOrdRmk
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ServiceResponseDTO }
     * 
     */
    public ServiceResponseDTO createServiceResponseDTO() {
        return new ServiceResponseDTO();
    }

    /**
     * Create an instance of {@link ServiceRequestDTO }
     * 
     */
    public ServiceRequestDTO createServiceRequestDTO() {
        return new ServiceRequestDTO();
    }

    /**
     * Create an instance of {@link AppendOrdRmkResponse }
     * 
     */
    public AppendOrdRmkResponse createAppendOrdRmkResponse() {
        return new AppendOrdRmkResponse();
    }

    /**
     * Create an instance of {@link OrderRemarkDTO }
     * 
     */
    public OrderRemarkDTO createOrderRemarkDTO() {
        return new OrderRemarkDTO();
    }

    /**
     * Create an instance of {@link ValueObject }
     * 
     */
    public ValueObject createValueObject() {
        return new ValueObject();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link AppendOrdRmk }
     * 
     */
    public AppendOrdRmk createAppendOrdRmk() {
        return new AppendOrdRmk();
    }

}

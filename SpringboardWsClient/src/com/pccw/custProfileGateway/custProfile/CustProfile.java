
package com.pccw.custProfileGateway.custProfile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3-b02-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "CustProfile", targetNamespace = "http://www.openuri.org/", wsdlLocation = "file:/C:/pvcs/working/BOM/Programs/Springboard/SBWPR3/SpringboardWsClient/wsdl/CustProfile.wsdl")
public class CustProfile
    extends Service
{

    private final static URL CUSTPROFILE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.pccw.custProfileGateway.custProfile.CustProfile.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.pccw.custProfileGateway.custProfile.CustProfile.class.getResource(".");
            url = new URL(baseUrl, "file:/C:/pvcs/working/BOM/Programs/Springboard/SBWPR3/SpringboardWsClient/wsdl/CustProfile.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/C:/pvcs/working/BOM/Programs/Springboard/SBWPR3/SpringboardWsClient/wsdl/CustProfile.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        CUSTPROFILE_WSDL_LOCATION = url;
    }

    public CustProfile(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CustProfile() {
        super(CUSTPROFILE_WSDL_LOCATION, new QName("http://www.openuri.org/", "CustProfile"));
    }

    /**
     * 
     * @return
     *     returns CustProfileSoap
     */
    @WebEndpoint(name = "CustProfileSoap")
    public CustProfileSoap getCustProfileSoap() {
        return super.getPort(new QName("http://www.openuri.org/", "CustProfileSoap"), CustProfileSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CustProfileSoap
     */
    @WebEndpoint(name = "CustProfileSoap")
    public CustProfileSoap getCustProfileSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.openuri.org/", "CustProfileSoap"), CustProfileSoap.class, features);
    }

}
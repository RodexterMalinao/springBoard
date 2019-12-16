
package com.pccw.appendOrdRmk;

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
@WebServiceClient(name = "AppendOrdRmk", targetNamespace = "http://www.openuri.org/", wsdlLocation = "file:/C:/pvcs/working/BOM/Programs/Springboard/SBWPR3/SpringboardWsClient/wsdl/AppendOrdRmk.wsdl")
public class AppendOrdRmk_Service
    extends Service
{

    private final static URL APPENDORDRMK_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.pccw.appendOrdRmk.AppendOrdRmk_Service.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.pccw.appendOrdRmk.AppendOrdRmk_Service.class.getResource(".");
            url = new URL(baseUrl, "file:/C:/pvcs/working/BOM/Programs/Springboard/SBWPR3/SpringboardWsClient/wsdl/AppendOrdRmk.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/C:/pvcs/working/BOM/Programs/Springboard/SBWPR3/SpringboardWsClient/wsdl/AppendOrdRmk.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        APPENDORDRMK_WSDL_LOCATION = url;
    }

    public AppendOrdRmk_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AppendOrdRmk_Service() {
        super(APPENDORDRMK_WSDL_LOCATION, new QName("http://www.openuri.org/", "AppendOrdRmk"));
    }

    /**
     * 
     * @return
     *     returns AppendOrdRmkSoap
     */
    @WebEndpoint(name = "AppendOrdRmkSoap")
    public AppendOrdRmkSoap getAppendOrdRmkSoap() {
        return super.getPort(new QName("http://www.openuri.org/", "AppendOrdRmkSoap"), AppendOrdRmkSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AppendOrdRmkSoap
     */
    @WebEndpoint(name = "AppendOrdRmkSoap")
    public AppendOrdRmkSoap getAppendOrdRmkSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.openuri.org/", "AppendOrdRmkSoap"), AppendOrdRmkSoap.class, features);
    }

}

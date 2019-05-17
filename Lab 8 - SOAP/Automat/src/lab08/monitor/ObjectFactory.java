
package lab08.monitor;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the lab08.monitor package. 
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

    private final static QName _GetFreePort_QNAME = new QName("http://monitor.lab08/", "getFreePort");
    private final static QName _GetFreePortResponse_QNAME = new QName("http://monitor.lab08/", "getFreePortResponse");
    private final static QName _UnregisterAutomatResponse_QNAME = new QName("http://monitor.lab08/", "unregisterAutomatResponse");
    private final static QName _UnregisterAutomat_QNAME = new QName("http://monitor.lab08/", "unregisterAutomat");
    private final static QName _UpdateRequest_QNAME = new QName("http://monitor.lab08/", "updateRequest");
    private final static QName _RegisterAutomatResponse_QNAME = new QName("http://monitor.lab08/", "registerAutomatResponse");
    private final static QName _UpdateRequestResponse_QNAME = new QName("http://monitor.lab08/", "updateRequestResponse");
    private final static QName _RegisterAutomat_QNAME = new QName("http://monitor.lab08/", "registerAutomat");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: lab08.monitor
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateRequest }
     * 
     */
    public UpdateRequest createUpdateRequest() {
        return new UpdateRequest();
    }

    /**
     * Create an instance of {@link UnregisterAutomat }
     * 
     */
    public UnregisterAutomat createUnregisterAutomat() {
        return new UnregisterAutomat();
    }

    /**
     * Create an instance of {@link GetFreePort }
     * 
     */
    public GetFreePort createGetFreePort() {
        return new GetFreePort();
    }

    /**
     * Create an instance of {@link GetFreePortResponse }
     * 
     */
    public GetFreePortResponse createGetFreePortResponse() {
        return new GetFreePortResponse();
    }

    /**
     * Create an instance of {@link UnregisterAutomatResponse }
     * 
     */
    public UnregisterAutomatResponse createUnregisterAutomatResponse() {
        return new UnregisterAutomatResponse();
    }

    /**
     * Create an instance of {@link RegisterAutomat }
     * 
     */
    public RegisterAutomat createRegisterAutomat() {
        return new RegisterAutomat();
    }

    /**
     * Create an instance of {@link UpdateRequestResponse }
     * 
     */
    public UpdateRequestResponse createUpdateRequestResponse() {
        return new UpdateRequestResponse();
    }

    /**
     * Create an instance of {@link RegisterAutomatResponse }
     * 
     */
    public RegisterAutomatResponse createRegisterAutomatResponse() {
        return new RegisterAutomatResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFreePort }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://monitor.lab08/", name = "getFreePort")
    public JAXBElement<GetFreePort> createGetFreePort(GetFreePort value) {
        return new JAXBElement<GetFreePort>(_GetFreePort_QNAME, GetFreePort.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFreePortResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://monitor.lab08/", name = "getFreePortResponse")
    public JAXBElement<GetFreePortResponse> createGetFreePortResponse(GetFreePortResponse value) {
        return new JAXBElement<GetFreePortResponse>(_GetFreePortResponse_QNAME, GetFreePortResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnregisterAutomatResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://monitor.lab08/", name = "unregisterAutomatResponse")
    public JAXBElement<UnregisterAutomatResponse> createUnregisterAutomatResponse(UnregisterAutomatResponse value) {
        return new JAXBElement<UnregisterAutomatResponse>(_UnregisterAutomatResponse_QNAME, UnregisterAutomatResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnregisterAutomat }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://monitor.lab08/", name = "unregisterAutomat")
    public JAXBElement<UnregisterAutomat> createUnregisterAutomat(UnregisterAutomat value) {
        return new JAXBElement<UnregisterAutomat>(_UnregisterAutomat_QNAME, UnregisterAutomat.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://monitor.lab08/", name = "updateRequest")
    public JAXBElement<UpdateRequest> createUpdateRequest(UpdateRequest value) {
        return new JAXBElement<UpdateRequest>(_UpdateRequest_QNAME, UpdateRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterAutomatResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://monitor.lab08/", name = "registerAutomatResponse")
    public JAXBElement<RegisterAutomatResponse> createRegisterAutomatResponse(RegisterAutomatResponse value) {
        return new JAXBElement<RegisterAutomatResponse>(_RegisterAutomatResponse_QNAME, RegisterAutomatResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://monitor.lab08/", name = "updateRequestResponse")
    public JAXBElement<UpdateRequestResponse> createUpdateRequestResponse(UpdateRequestResponse value) {
        return new JAXBElement<UpdateRequestResponse>(_UpdateRequestResponse_QNAME, UpdateRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterAutomat }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://monitor.lab08/", name = "registerAutomat")
    public JAXBElement<RegisterAutomat> createRegisterAutomat(RegisterAutomat value) {
        return new JAXBElement<RegisterAutomat>(_RegisterAutomat_QNAME, RegisterAutomat.class, null, value);
    }

}

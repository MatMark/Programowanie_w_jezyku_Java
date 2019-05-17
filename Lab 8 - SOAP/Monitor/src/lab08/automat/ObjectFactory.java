
package lab08.automat;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the lab08.automat package. 
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

    private final static QName _GetItems_QNAME = new QName("http://automat.lab08/", "getItems");
    private final static QName _GetItemsResponse_QNAME = new QName("http://automat.lab08/", "getItemsResponse");
    private final static QName _GetId_QNAME = new QName("http://automat.lab08/", "getId");
    private final static QName _GetIdResponse_QNAME = new QName("http://automat.lab08/", "getIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: lab08.automat
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetItemsResponse }
     * 
     */
    public GetItemsResponse createGetItemsResponse() {
        return new GetItemsResponse();
    }

    /**
     * Create an instance of {@link GetItems }
     * 
     */
    public GetItems createGetItems() {
        return new GetItems();
    }

    /**
     * Create an instance of {@link GetId }
     * 
     */
    public GetId createGetId() {
        return new GetId();
    }

    /**
     * Create an instance of {@link GetIdResponse }
     * 
     */
    public GetIdResponse createGetIdResponse() {
        return new GetIdResponse();
    }

    /**
     * Create an instance of {@link Drink }
     * 
     */
    public Drink createDrink() {
        return new Drink();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItems }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://automat.lab08/", name = "getItems")
    public JAXBElement<GetItems> createGetItems(GetItems value) {
        return new JAXBElement<GetItems>(_GetItems_QNAME, GetItems.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItemsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://automat.lab08/", name = "getItemsResponse")
    public JAXBElement<GetItemsResponse> createGetItemsResponse(GetItemsResponse value) {
        return new JAXBElement<GetItemsResponse>(_GetItemsResponse_QNAME, GetItemsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://automat.lab08/", name = "getId")
    public JAXBElement<GetId> createGetId(GetId value) {
        return new JAXBElement<GetId>(_GetId_QNAME, GetId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://automat.lab08/", name = "getIdResponse")
    public JAXBElement<GetIdResponse> createGetIdResponse(GetIdResponse value) {
        return new JAXBElement<GetIdResponse>(_GetIdResponse_QNAME, GetIdResponse.class, null, value);
    }

}

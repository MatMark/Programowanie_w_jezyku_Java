package Lab12;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    private final File start = new File("C:\\Users\\matma\\Documents\\TestForJAXB");
    private ObjectFactory objectFactory = new ObjectFactory();

    @FXML
    WebView htmlView;
    @FXML
    TextField productName;
    @FXML
    TextField productPrice;
    @FXML
    TextField productType;
    @FXML
    TextField sellerTel;
    @FXML
    TextField sellerFirstName;
    @FXML
    TextField sellerLastName;
    @FXML
    TextField street;
    @FXML
    TextField city;
    @FXML
    TextField postCode;

    @FXML
    private void addOffer(){
        Offer offer = objectFactory.createOffer();

        Offer.Product product = objectFactory.createOfferProduct();
        product.setName(productName.getText());
        product.setPrice(Float.parseFloat(productPrice.getText()));
        product.setType(productType.getText());
        offer.setProduct(product);

        Offer.Seller seller = objectFactory.createOfferSeller();
        seller.setFirstName(sellerFirstName.getText());
        seller.setLastName(sellerLastName.getText());
        seller.setTel(sellerTel.getText());
        offer.setSeller(seller);

        Offer.Address address = objectFactory.createOfferAddress();
        address.setStreet(street.getText());
        address.setCity(city.getText());
        address.setPostCode(postCode.getText());
        offer.setAddress(address);

        //losowe id
        offer.setId(Byte.parseByte(String.valueOf(new Random().nextInt(100))));

        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Offer.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        Marshaller marshaller = null;
        try {
            if (jaxbContext != null) {
                marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        StringWriter xmlWriter = new StringWriter();
        try {
            if (marshaller != null) {
                marshaller.marshal(offer, xmlWriter);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        try{
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(System.getProperty("user.dir") + "\\Oferty\\"+new Random().nextInt(100)+".xml"), StandardCharsets.UTF_8));
            writer.write(xmlWriter.toString());
            writer.close();
        }catch(Exception e){System.out.println(e);}
    }

    @FXML
    private void makehtml() throws TransformerException {
        File transform = chooseFile(App.stage, "Wybierz plik .xsl", "Pliki .xsl", "*.xsl");

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(transform);
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File(start.getPath() + "\\Offers.xml"));
        transformer.transform(text, new StreamResult(new File(start.getPath() + "\\index.html")));

    }

    @FXML
    private void reload(){
        WebEngine webEngine = htmlView.getEngine();
        webEngine.load("file:" + start.getPath() + "\\index.html");
    }

    private File chooseFile(Stage stage, String title, String description, String... extention){
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(start);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(description, extention);
        chooser.getExtensionFilters().add(extFilter);
        return new File(chooser.showOpenDialog(stage).getAbsoluteFile().getPath());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebEngine webEngine = htmlView.getEngine();
        webEngine.load("file:" + start.getPath() + "\\index.html");
    }
}

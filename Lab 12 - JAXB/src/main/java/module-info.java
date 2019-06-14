module Lab12 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.xml.bind;

    opens Lab12 to javafx.fxml;
    exports Lab12;
}
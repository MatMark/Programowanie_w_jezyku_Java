package Controllers;

import Tables.Service;
import Tables.Term;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    private UserConnection dbConnection;
    private ObservableList<Term> termsData;
    private ObservableList<Service> servicesData;

    @FXML
    Label availableServicesLabel;

    @FXML
    Label freeTermsLabel;

    @FXML
    Button bookTermButton;

    @FXML
    TableView<Service> servicesTable;

    @FXML
    TableColumn<Service, String> serviceNameColumn;

    @FXML
    TableColumn<Service, Integer> servicePriceColumn;

    @FXML
    TableView<Term> termsTable;

    @FXML
    TableColumn<Term, String> dateColumn;

    @FXML
    TableColumn<Term, String> timeColumn;

    @FXML
    void bookTerm() throws SQLException {
        if(servicesTable.getSelectionModel().getSelectedItem() != null && termsTable.getSelectionModel().getSelectedItem() != null) {
            String date = termsTable.getSelectionModel().getSelectedItem().getDate();
            String time = termsTable.getSelectionModel().getSelectedItem().getTime();
            String serviceName = servicesTable.getSelectionModel().getSelectedItem().getName();
            int termId = termsTable.getSelectionModel().getSelectedItem().getId();

            dbConnection.bookTerms(date, time, UserConnection.activeUserName, UserConnection.activeUserSurname, serviceName, termId);
            refreshTermsTable();
        }
    }

    private void refreshTermsTable() throws SQLException {
        termsData = FXCollections.observableArrayList(dbConnection.selectFreeTerms());
        termsTable.setItems(termsData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setLanguage();

        boolean userExist = false;

        try {
            dbConnection = new UserConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            userExist = dbConnection.findUser(UserConnection.activeUserName, UserConnection.activeUserSurname);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!userExist){
            try {
                dbConnection.addUser(UserConnection.activeUserName, UserConnection.activeUserSurname);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<Service,String>("name"));
        servicePriceColumn.setCellValueFactory(new PropertyValueFactory<Service,Integer>("price"));

        try {
            servicesData = FXCollections.observableArrayList(dbConnection.selectAllServices());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        servicesTable.setItems(servicesData);


        dateColumn.setCellValueFactory(new PropertyValueFactory<Term,String>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Term,String>("time"));

        try {
            termsData = FXCollections.observableArrayList(dbConnection.selectFreeTerms());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        termsTable.setItems(termsData);
    }

    private void setLanguage(){
        availableServicesLabel.setText(UserConnection.messages.getString("availableServices"));
        freeTermsLabel.setText(UserConnection.messages.getString("freeTerms"));
        serviceNameColumn.setText(UserConnection.messages.getString("serviceName"));
        servicePriceColumn.setText(UserConnection.messages.getString("servicePrice"));
        dateColumn.setText(UserConnection.messages.getString("date"));
        timeColumn.setText(UserConnection.messages.getString("time"));
        bookTermButton.setText(UserConnection.messages.getString("book"));
    }
}

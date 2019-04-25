package Controllers;

import Tables.Service;
import Tables.Term;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class AdminController implements Initializable {

    private AdminConnection dbConnection;

    //---------Cennik-----------

    private ObservableList<Service> servicesData;

    @FXML
    TableView<Service> servicesTable;

    @FXML
    TableColumn<Service, String> nameColumn;

    @FXML
    TableColumn<Service, Integer> priceColumn;

    @FXML
    TextField serviceNameTextField;

    @FXML
    TextField servicePriceTextField;

    private void refreshServicesTable() throws SQLException {
        servicesData = FXCollections.observableArrayList(dbConnection.selectAllServices());
        servicesTable.setItems(servicesData);
    }

    @FXML
    void addService() throws SQLException {
        if(!serviceNameTextField.getText().isEmpty() && !servicePriceTextField.getText().isEmpty()) {
            dbConnection.addServices(new Service(Integer.parseInt(servicePriceTextField.getText()), serviceNameTextField.getText()));
            refreshServicesTable();
            servicePriceTextField.setText("");
            serviceNameTextField.setText("");
        }
    }

    @FXML
    void removeService() throws SQLException {
        Service service = servicesTable.getSelectionModel().getSelectedItem();
        dbConnection.removeServices(service.getName());
        refreshServicesTable();
    }

    @FXML
    void editService() throws SQLException {
        if(servicesTable.getSelectionModel().getSelectedItem() != null) {
            Service service = servicesTable.getSelectionModel().getSelectedItem();

            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Edit service");

            ButtonType loginButtonType = new ButtonType("Zatwierdź", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField name = new TextField(service.getName());
            name.setPromptText("Nazwa usługi");
            TextField price = new TextField(Integer.toString(service.getPrice()));
            price.setPromptText("Cena");

            grid.add(name, 1, 0);
            grid.add(price, 1, 1);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(name.getText(), price.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            result.ifPresent(priceName -> {
                try {
                    dbConnection.editServices( price.getText(), name.getText(), service.getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            refreshServicesTable();
        }
    }

    //--------Terminarz-----------

    private ObservableList<Term> termsData;

    @FXML
    TableView<Term> termsTable;

    @FXML
    TableColumn<Term, String> dateColumn;

    @FXML
    TableColumn<Term, String> timeColumn;

    @FXML
    TableColumn<Term, String> serviceColumn;

    @FXML
    TableColumn<Term, String> customerColumn;

    @FXML
    DatePicker newTermCalendar;

    @FXML
    TextField timeTextField;

    private void refreshTermsTable() throws SQLException {
        List<Term> visibleTerms = dbConnection.selectReservedTerms();
        visibleTerms.addAll(dbConnection.selectFreeTerms());
        termsData = FXCollections.observableArrayList(visibleTerms);
        termsTable.setItems(termsData);
    }

    @FXML
    void removeTerm() throws SQLException {
        Term term = termsTable.getSelectionModel().getSelectedItem();
        dbConnection.removeTerms(term.getId());
        refreshTermsTable();
    }

    @FXML
    void editTerm() throws SQLException {
        if(termsTable.getSelectionModel().getSelectedItem() != null) {
            Term term = termsTable.getSelectionModel().getSelectedItem();

            Dialog dialog = new Dialog();
            dialog.setTitle("Edit term");

            ButtonType loginButtonType = new ButtonType("Zatwierdź", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField date = new TextField(term.getDate());
            date.setPromptText("Data");
            TextField time = new TextField(term.getTime());
            time.setPromptText("Czas");
            TextField clientName = new TextField();
            clientName.setPromptText("Imię klienta");
            TextField clientSurname = new TextField();
            clientSurname.setPromptText("Nazwisko klienta");
            TextField serviceName = new TextField(term.getServiceName());
            serviceName.setPromptText("Nazwa usługi");

            grid.add(date, 1, 0);
            grid.add(time, 1, 1);
            grid.add(clientName, 1, 2);
            grid.add(clientSurname, 1, 3);
            grid.add(serviceName, 1, 4);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return 0;
                }
                return null;
            });

            Optional result = dialog.showAndWait();

            result.ifPresent(priceName -> {
                try {
                    dbConnection.editTerms( date.getText(), time.getText(), clientName.getText(), clientSurname.getText(), serviceName.getText(), term.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            refreshServicesTable();
        }
        refreshTermsTable();
    }

    @FXML
    void newTerm() throws SQLException {
        if(!timeTextField.getText().isEmpty() && newTermCalendar.getValue() != null) {
            System.out.println(newTermCalendar.getValue().toString());
            dbConnection.addTerms(new Term(newTermCalendar.getValue().toString(), timeTextField.getText()));
            refreshServicesTable();
            timeTextField.setText("");
            newTermCalendar.setValue(null);
        }

        refreshTermsTable();
    }

    //----------Raporty-----------

    @FXML
    DatePicker startDateCalendar;

    @FXML
    DatePicker endDateCalendar;

    @FXML
    TextArea raportTextArea;

    @FXML
    void newRaport() throws SQLException, IOException {

        String startDate = startDateCalendar.getValue().toString();
        String endDate = endDateCalendar.getValue().toString();
        String result = dbConnection.selectTermsBetween(startDate, endDate).toString();
        int sum = dbConnection.selectSum(startDate, endDate);
        String fileName = "Raport z okresu " + startDate + " do " +endDate;

        raportTextArea.setText(result);
        raportTextArea.appendText("\n\nDochód z wybranego okresu: "+sum);

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(result);
        writer.append("\n\nDochód z wybranego okresu: ").append(String.valueOf(sum));
        writer.close();

        startDateCalendar.setValue(null);
        endDateCalendar.setValue(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            dbConnection = new AdminConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        //---------Cennik------------
        nameColumn.setCellValueFactory(new PropertyValueFactory<Service,String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Service,Integer>("price"));
        try {
            servicesData = FXCollections.observableArrayList(dbConnection.selectAllServices());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        servicesTable.setItems(servicesData);


        //-------Terminarz----------
        dateColumn.setCellValueFactory(new PropertyValueFactory<Term,String>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Term,String>("time"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<Term,String>("serviceName"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<Term,String>("clientFullName"));
        try {
            List<Term> visibleTerms = dbConnection.selectReservedTerms();
            visibleTerms.addAll(dbConnection.selectFreeTerms());

            termsData = FXCollections.observableArrayList(visibleTerms);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        termsTable.setItems(termsData);

        newTermCalendar.setConverter(AdminConnection.mySQLDate);

        //-------Raporty-----------
        startDateCalendar.setConverter(AdminConnection.mySQLDate);
        endDateCalendar.setConverter(AdminConnection.mySQLDate);
    }
}

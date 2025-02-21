package database_application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class DatabaseAppController implements Initializable {

    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;
    @FXML
    private Button loadJson;
    @FXML
    private Button rentButton;
    @FXML
    private TableView table;
    @FXML
    private ComboBox<String> selectTableComboBox;
    @FXML
    private Label statusLabel;

    private final RentDAO rentDAO;

    private final UserDAO userDAO;

    private final CarDAO carDAO;

    private final User user;

    public DatabaseAppController(RentDAO rentDAO, UserDAO userDAO, CarDAO carDAO, User user) {
        this.rentDAO = rentDAO;
        this.userDAO = userDAO;
        this.carDAO = carDAO;
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = selectTableComboBox.getItems();
        list.add("Cars");
        if (UserRole.ADMIN == user.getRole()) {
            list.add("Users");
            list.add("Rentals");
            deleteButton.setVisible(true);
            addButton.setVisible(true);
            loadJson.setVisible(true);
        } else if (UserRole.ZAMESTNANEC == user.getRole()) {
            list.add("Rentals");
            deleteButton.setVisible(true);
            addButton.setVisible(true);
        } else if (UserRole.ZAKAZNIK == user.getRole()) {
            selectTableComboBox.setValue("Cars");
            selectTableComboBox.setVisible(false);
            loadTable();
        }


    }
    @FXML
    protected void loadJson() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose JSON file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null) {
            return;
        }
        switch (selectTableComboBox.getValue()) {
            case "Cars":
                statusLabel.setText(carDAO.loadCarsFromJson(file));
                break;
            case "Users":
                userDAO.loadUsersFromJson(file);
                break;
        }
        loadTable();
    }
// load taables from database based on selected value in combobox
    @FXML
    protected void loadTable() {
        table.getColumns().clear();
        switch (selectTableComboBox.getValue()) {
            case "Cars":
                rentButton.setVisible(true);
                ArrayList<Car> vozidlaList = carDAO.loadAllCars();

                TableColumn modelColumn = new TableColumn("Model");
                TableColumn znackaColumn = new TableColumn("Brand");
                TableColumn spzColumn = new TableColumn("SPZ");

                modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
                znackaColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
                spzColumn.setCellValueFactory(new PropertyValueFactory<>("SPZ"));

                ObservableList<Car> data = FXCollections.observableArrayList(vozidlaList);
                table.setItems(data);
                table.getColumns().addAll(modelColumn, znackaColumn, spzColumn);
                break;
            case "Users":
                rentButton.setVisible(false);
                ArrayList<User> userList = userDAO.loadAllUsers();
                TableColumn nameColumn = new TableColumn("Name");
                TableColumn roleColumn = new TableColumn("Role");

                nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
                ObservableList<User> data1 = FXCollections.observableArrayList(userList);
                table.setItems(data1);
                table.getColumns().addAll(nameColumn, roleColumn);
                break;
            case "Rentals":
                rentButton.setVisible(false);
                ArrayList<Rent> rentList = rentDAO.loadAllRentals();
                TableColumn customerNameColumn = new TableColumn("Customer Name");
                TableColumn car = new TableColumn("Car");
                TableColumn documentNumber = new TableColumn("Identity Card Number");
                TableColumn since = new TableColumn("Since");
                TableColumn until = new TableColumn("Until");

                customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
                car.setCellValueFactory(new PropertyValueFactory<>("spz"));
                documentNumber.setCellValueFactory(new PropertyValueFactory<>("customerDrivingLicenseCardNumber"));
                since.setCellValueFactory(new PropertyValueFactory<>("since"));
                until.setCellValueFactory(new PropertyValueFactory<>("until"));
                ObservableList<Rent> data2 = FXCollections.observableArrayList(rentList);
                table.setItems(data2);
                table.getColumns().addAll(customerNameColumn, car, documentNumber, since, until);
        }

    }
// add to table based on value in combobox
    @FXML
    protected void addToTable() {
        switch (selectTableComboBox.getValue()) {
            case "Cars":
                CarHelper.carDialog(carDAO);
                break;
            case "Users":
                UserHelper.uzivateleDialog(userDAO);
                break;
                case "Rentals":
                    statusLabel.setText("You can't add rentals");
        }
        loadTable();
    }
// delete from table based on value in combobox
    @FXML
    protected void vymazatZTabulky() {
        switch (selectTableComboBox.getValue()) {
            case "Cars":
                Car vybrano = (Car) table.getSelectionModel().getSelectedItem();
                carDAO.deleteCar(vybrano.getSPZ());
                break;
            case "Users":
                User vybrano1 = (User) table.getSelectionModel().getSelectedItem();
                userDAO.deleteUser(vybrano1.getName());
                break;
            case "Rentals":
                Rent vybrano2 = (Rent) table.getSelectionModel().getSelectedItem();
                rentDAO.deleteRent(vybrano2);
                break;
        }
        loadTable();
    }
    // táta pomohl
// rent car
    @FXML
    protected void rentACar() {
        final Rent rent = new Rent();
        rent.setCar((Car) table.getSelectionModel().getSelectedItem());

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox(10);
        String confirmationText = "Are you sure you want to rent this car?: " + ((Car) table.getSelectionModel().getSelectedItem()).getModel() + " " + ((Car) table.getSelectionModel().getSelectedItem()).getBrand() + " " + ((Car) table.getSelectionModel().getSelectedItem()).getSPZ();
        Label label = new Label(confirmationText);
        Button confirm = new Button("Confirm");
        if(user.getRole() != UserRole.ZAKAZNIK) {
            TextField name = new TextField();
            name.setOnKeyTyped(e -> {
                if (!name.getText().isEmpty()) {
                    Optional<User> foundCustomer = userDAO.findCustomer(name.getText());
                    if (foundCustomer.isPresent()) {
                        confirm.setDisable(false);
                        rent.setCustomer(foundCustomer.get());
                        label.setText(confirmationText + "\nfor customer: " + rent.getCustomer().getName());
                    } else {
                        confirm.setDisable(true);
                        label.setText(confirmationText);
                    }
                } else {
                    confirm.setDisable(true);
                    label.setText(confirmationText);
                }
            });
            vbox.getChildren().addAll(new Label("Customer Name"), name);
        } else {
            rent.setCustomer(user);
        }
        confirm.setOnAction(e -> {
            rentDAO.rentACar(rent);
            dialog.close();
            statusLabel.setText("Car rented: " + rent.getCar().getModel() + " " + rent.getCar().getBrand() + " " + rent.getCar().getSPZ());
        });
        vbox.getChildren().addAll(label, confirm);
        dialog.setScene(new Scene(vbox, 300, 200));
        dialog.showAndWait();
    }
}
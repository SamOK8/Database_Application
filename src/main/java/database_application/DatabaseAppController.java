package database_application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class DatabaseAppController implements Initializable {

    @FXML
    private Button vymazat;
    @FXML
    private Button pridat;
    @FXML
    private Button vypujcit;
    @FXML
    private TableView tabulka;
    @FXML
    private ComboBox<String> vyberTabulky;

    private PujceniDAO pujceniDAO;

    private UzivateleDAO uzivateleDAO;

    private VozidloDAO vozidloDAO;

    private Uzivatel uzivatel;

    public DatabaseAppController(PujceniDAO pujceniDAO, UzivateleDAO uzivateleDAO, VozidloDAO vozidloDAO, Uzivatel uzivatel) {
        this.pujceniDAO = pujceniDAO;
        this.uzivateleDAO = uzivateleDAO;
        this.vozidloDAO = vozidloDAO;
        this.uzivatel = uzivatel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = vyberTabulky.getItems();
        list.add("Vozidlo");
        if (RoleUzivatele.ADMIN == uzivatel.getRole()) {
            list.add("Uzivatele");
            list.add("pujceni");
            vymazat.setVisible(true);
            pridat.setVisible(true);
        } else if (RoleUzivatele.ZAMESTNANEC == uzivatel.getRole()) {
            list.add("pujceni");
            vymazat.setVisible(true);
            pridat.setVisible(true);
        } else if (RoleUzivatele.ZAKAZNIK == uzivatel.getRole()) {
            vyberTabulky.setValue("Vozidlo");
            vyberTabulky.setVisible(false);
            nacistTabulku();
        }

//        vyberTabulky.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//            }
//        });
    }

    @FXML
    protected void nacistTabulku() {
        tabulka.getColumns().clear();
        switch (vyberTabulky.getValue()) {
            case "Vozidlo":
                vypujcit.setVisible(true);
                ArrayList<Vozidlo> vozidlaList = vozidloDAO.nacistVozidla();

                TableColumn modelColumn = new TableColumn("Model");
                TableColumn znackaColumn = new TableColumn("Znacka");
                TableColumn spzColumn = new TableColumn("SPZ");

                modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
                znackaColumn.setCellValueFactory(new PropertyValueFactory<>("znacka"));
                spzColumn.setCellValueFactory(new PropertyValueFactory<>("SPZ"));

                ObservableList<Vozidlo> data = FXCollections.observableArrayList(vozidlaList);
                tabulka.setItems(data);
                tabulka.getColumns().addAll(modelColumn, znackaColumn, spzColumn);
                break;
            case "Uzivatele":
                vypujcit.setVisible(false);
                ArrayList<Uzivatel> uzivatelList = uzivateleDAO.nacistUzivatele();
                TableColumn jmenoColumn = new TableColumn("Jmeno");
                TableColumn roleColumn = new TableColumn("Role");

                jmenoColumn.setCellValueFactory(new PropertyValueFactory<>("jmeno"));
                roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
                ObservableList<Uzivatel> data1 = FXCollections.observableArrayList(uzivatelList);
                tabulka.setItems(data1);
                tabulka.getColumns().addAll(jmenoColumn, roleColumn);
                break;
            case "pujceni":
                vypujcit.setVisible(false);
                ArrayList<Pujceni> pujceniList = pujceniDAO.nacistPujceni();
                TableColumn jmenoZakaznikaColumn = new TableColumn("Jmeno Zakaznika");
                TableColumn prijmeniZakaznikaColumn = new TableColumn("Prijmeni Zakaznika");
                TableColumn cisloDokladuZakaznikaColumn = new TableColumn("Cislo Dokladu Zakaznika");

                jmenoZakaznikaColumn.setCellValueFactory(new PropertyValueFactory<>("jmenoZakaznika"));
                prijmeniZakaznikaColumn.setCellValueFactory(new PropertyValueFactory<>("prijmeniZakaznika"));
                cisloDokladuZakaznikaColumn.setCellValueFactory(new PropertyValueFactory<>("cisloDokladuZakaznika"));
                ObservableList<Pujceni> data2 = FXCollections.observableArrayList(pujceniList);
                tabulka.setItems(data2);
                tabulka.getColumns().addAll(jmenoZakaznikaColumn, prijmeniZakaznikaColumn, cisloDokladuZakaznikaColumn);
        }

    }

    @FXML
    protected void pridatDoTabulky() {
        switch (vyberTabulky.getValue()) {
            case "Vozidlo":
                VozidloHandler.vozidlaDialog(vozidloDAO);
                break;
            case "Uzivatele":
                UzivateleHandler.uzivateleDialog(uzivateleDAO);
                break;
            case "pujceni":
                Stage dialog2 = new Stage();
                dialog2.initModality(Modality.APPLICATION_MODAL);
                TextField jmenoZakaznika = new TextField();
                TextField prijmeniZakaznika = new TextField();
                TextField cisloDokladuZakaznika = new TextField();
                pujceniDAO.pridatPujceni(jmenoZakaznika.getText(), prijmeniZakaznika.getText(), cisloDokladuZakaznika.getText());
                break;
        }
        nacistTabulku();

    }

    @FXML
    protected void vymazatZTabulky() {
        switch (vyberTabulky.getValue()) {
            case "Vozidlo":
                Vozidlo vybrano = (Vozidlo) tabulka.getSelectionModel().getSelectedItem();
                vozidloDAO.vymazatVozidlo(vybrano.getSPZ());
                break;
            case "Uzivatel":
                Uzivatel vybrano1 = (Uzivatel) tabulka.getSelectionModel().getSelectedItem();
                uzivateleDAO.vymazatUzivatele(vybrano1.getJmeno());
                break;
            case "pujceni":
                Pujceni vybrano2 = (Pujceni) tabulka.getSelectionModel().getSelectedItem();
                pujceniDAO.vymazatPujceni(vybrano2.getJmenoZakaznika(), vybrano2.getPrijmeniZakaznika());
                break;
        }
        nacistTabulku();
    }

}
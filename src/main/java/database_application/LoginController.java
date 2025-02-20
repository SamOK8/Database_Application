package database_application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    private Label LoginText;
    @FXML
    private TextField jmenoUzivatele;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;

    private UzivateleDAO uzivateleDAO;

    public LoginController(UzivateleDAO uzivateleDAO) {
        this.uzivateleDAO = uzivateleDAO;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    protected void onConnectButtonClick() throws IOException {
        if (jmenoUzivatele.getText().isEmpty()) {
            errorLabel.setText("Jmeno uzivatele nesmi byt prazdne");
            return;
        }
        Optional<Uzivatel> uzivatele = uzivateleDAO.nacistUzivatele(jmenoUzivatele.getText());
        if (uzivatele.isEmpty()) {
            errorLabel.setText("Uzivatel neexistuje");
            return;
        }
        Stage stage1 = (Stage) loginButton.getScene().getWindow();
        stage1.close();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationPujcovna.class.getResource("Database_AppGui.fxml"));
        Connection connection = MSSQLConnection.getConnection();
        PujceniDAO pujceniDAO = new PujceniDAO(connection);
        VozidloDAO vozidloDAO = new VozidloDAO(connection);
        fxmlLoader.setControllerFactory(t -> new DatabaseAppController(pujceniDAO, uzivateleDAO, vozidloDAO, uzivatele.get()));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Database System");
        stage.show();
    }
}
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
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    private Label LoginText;
    @FXML
    private TextField username;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;

    private UserDAO userDAO;

    public LoginController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    // ask user for name and change window after login
    @FXML
    protected void login() throws IOException, URISyntaxException {
        if (username.getText().isEmpty()) {
            errorLabel.setText("Name of user is empty");
            return;
        }

        //táta
        //--------
        Optional<User> uzivatele = userDAO.loadUser(username.getText());
        if (uzivatele.isEmpty()) {
            errorLabel.setText("User does not exist");
            return;
        //--------

        }
        Stage stage1 = (Stage) loginButton.getScene().getWindow();
        stage1.close();
        FXMLLoader fxmlLoader = new FXMLLoader(DatabaseApplication.class.getResource("Database_AppGui.fxml"));
        Connection connection = MSSQLConnection.getConnection();
        RentDAO rentDAO = new RentDAO(connection);
        CarDAO carDAO = new CarDAO(connection);
        fxmlLoader.setControllerFactory(t -> new DatabaseAppController(rentDAO, userDAO, carDAO, uzivatele.get()));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Database System");
        stage.show();
    }
}
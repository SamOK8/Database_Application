package database_application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;


public class DatabaseApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        Connection connection = MSSQLConnection.getConnection();
        UserDAO userDAO = new UserDAO(connection);

        FXMLLoader fxmlLoader = new FXMLLoader(DatabaseApplication.class.getResource("connectionWindow.fxml"));
        fxmlLoader.setControllerFactory(t -> new LoginController(userDAO));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Database System");
        stage.setScene(scene);
        stage.show();
    }





    public static void main(String[] args) {
        launch();
    }
}
package database_application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;


public class ApplicationPujcovna extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        Connection connection = MSSQLConnection.getConnection();
        UzivateleDAO uzivateleDAO = new UzivateleDAO(connection);

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationPujcovna.class.getResource("connectionWindow.fxml"));
        fxmlLoader.setControllerFactory(t -> new LoginController(uzivateleDAO));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Database System");
        stage.setScene(scene);
        stage.show();
    }





    public static void main(String[] args) {
        launch();
    }
}
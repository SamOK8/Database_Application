package database_application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;

public class UzivateleHandler {

    public static void uzivateleDialog(UzivateleDAO uzivateleDAO) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox(10);
        TextField jmeno = new TextField();
        Button potvrdit = new Button("Potvrdit");
        potvrdit.setOnAction(e -> {
            uzivateleDAO.pridatUzivatele(jmeno.getText());
            dialog.close();
        });
        vbox.getChildren().addAll(new Label("Jmeno"), jmeno, potvrdit);
        dialog.setScene(new Scene(vbox, 300, 200));
        dialog.showAndWait();
    }
}

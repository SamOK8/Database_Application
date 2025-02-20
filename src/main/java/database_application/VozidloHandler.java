package database_application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VozidloHandler {
    public static void vozidlaDialog(VozidloDAO vozidloDAO) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox(10);
        TextField model = new TextField();
        TextField znacka = new TextField();
        TextField spz = new TextField();
        Button potvrdit = new Button("Potvrdit");
        potvrdit.setOnAction(e ->{
            vozidloDAO.pridatVozidlo(model.getText(), znacka.getText(), spz.getText());
            dialog.close();
        });
        vbox.getChildren().addAll(new Label("Model"), model, new Label("Znacka"), znacka, new Label("SPZ"), spz, potvrdit);
        dialog.setScene(new Scene(vbox, 300, 200));
        dialog.showAndWait();
    }

}

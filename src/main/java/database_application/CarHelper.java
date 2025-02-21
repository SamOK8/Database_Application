package database_application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CarHelper {
    //open dialog to add car
    public static void carDialog(CarDAO carDAO) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox(10);
        TextField model = new TextField();
        TextField brand = new TextField();
        TextField spz = new TextField();
        Button confirm = new Button("Confirm");
        confirm.setOnAction(e ->{
            carDAO.createCar(spz.getText(), brand.getText(), model.getText());
            dialog.close();
        });
        vbox.getChildren().addAll(new Label("Model"), model, new Label("Brand"), brand, new Label("SPZ"), spz, confirm);
        dialog.setScene(new Scene(vbox, 400, 300));
        dialog.showAndWait();
    }

}

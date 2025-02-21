package database_application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserHelper {
// open dialog to add user
    public static void uzivateleDialog(UserDAO userDAO) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox(10);
        TextField name = new TextField();
        ComboBox role = new ComboBox();
        role.getItems().addAll(UserRole.values());
        Button confirm = new Button("Confirm");
        confirm.setOnAction(e -> {
            userDAO.createUser(name.getText(), (UserRole) role.getValue());
            dialog.close();
        });
        vbox.getChildren().addAll(new Label("Name"), name, role, confirm);
        dialog.setScene(new Scene(vbox, 400, 300));
        dialog.showAndWait();
    }
}

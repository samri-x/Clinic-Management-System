package clinic.view;
import clinic.logics.DoctorLogic;
import clinic.exceptions.DoctorAddingException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AddDoctorView extends BorderPane {

    public AddDoctorView(Stage stage) {

        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f7fafc;");

        Label title = new Label("➕ Add New Doctor");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#2d3748"));

        TextField name = new TextField();
        name.setPromptText("Doctor Name");
        name.setMaxWidth(350);

        TextField spec = new TextField();
        spec.setPromptText("Specialization");
        spec.setMaxWidth(350);

        TextField username = new TextField();
        username.setPromptText("Username");
        username.setMaxWidth(350);

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(350);

        TextField passwordVisible = new TextField();
        passwordVisible.setPromptText("Password");
        passwordVisible.setMaxWidth(350);
        passwordVisible.setManaged(false);
        passwordVisible.setVisible(false);

        password.textProperty().bindBidirectional(passwordVisible.textProperty());

        CheckBox showPass = new CheckBox("Show Password");
        showPass.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                passwordVisible.setManaged(true);
                passwordVisible.setVisible(true);
                password.setManaged(false);
                password.setVisible(false);
            } else {
                passwordVisible.setManaged(false);
                passwordVisible.setVisible(false);
                password.setManaged(true);
                password.setVisible(true);
            }
        });

        VBox form = new VBox(15, name, spec, username, password, passwordVisible, showPass);
        form.setAlignment(Pos.CENTER_LEFT);

        Button save = new Button("Save");
        Button back = new Button("Back");

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setVisible(false);

        VBox card = new VBox(25, title, form, new HBox(20, save, back), errorLabel);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; "
                + "-fx-border-radius: 12; -fx-border-color: #cbd5e0; -fx-border-width: 1; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,4);");

        setCenter(card);

        save.setOnAction(e -> {
            try {
                DoctorLogic.addDoctor(
                        name.getText(),
                        spec.getText(),
                        username.getText(),
                        password.getText()
                );

                name.clear(); spec.clear(); username.clear(); password.clear(); passwordVisible.clear();
                errorLabel.setVisible(false);

                new Alert(Alert.AlertType.INFORMATION, "Doctor added successfully!", ButtonType.OK).showAndWait();

            } catch (DoctorAddingException ex) {
                errorLabel.setText("❌ " + ex.getMessage());
                errorLabel.setVisible(true);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
        });

        back.setOnAction(e -> setCenter(new Label("Select an option from the menu")));
    }
}

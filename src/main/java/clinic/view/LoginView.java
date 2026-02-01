package clinic.view;
import clinic.logics.LoginController;
import clinic.model.Doctor;
import clinic.exceptions.LoginException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class LoginView extends BorderPane {

    public LoginView(Stage stage) {

        setPadding(new Insets(50));
        setStyle("-fx-background-color: linear-gradient(to bottom, #edf2f7, #e2e8f0);");

        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(40));
        centerBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; "
                + "-fx-border-radius: 12; -fx-border-color: #cbd5e0; -fx-border-width: 1; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,4);");
        

        Label title = new Label("Clinic Login");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#2d3748"));

        ComboBox<String> role = new ComboBox<>();
        role.getItems().addAll("Admin", "Doctor");
        role.setPromptText("Select Role");
        role.setMaxWidth(300);

        TextField user = new TextField();
        user.setPromptText("Username");
        user.setMaxWidth(300);

        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");
        pass.setMaxWidth(300);

        TextField passVisible = new TextField();
        passVisible.setPromptText("Password");
        passVisible.setMaxWidth(300);
        passVisible.setManaged(false);
        passVisible.setVisible(false);

        pass.textProperty().bindBidirectional(passVisible.textProperty());

        CheckBox showPass = new CheckBox("Show Password");
        showPass.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                passVisible.setManaged(true);
                passVisible.setVisible(true);
                pass.setManaged(false);
                pass.setVisible(false);
            } else {
                passVisible.setManaged(false);
                passVisible.setVisible(false);
                pass.setManaged(true);
                pass.setVisible(true);
            }
        });

        Button login = new Button("Login");
        login.setPrefWidth(300);

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setVisible(false);

        login.setOnAction(e -> {
            try {
                Object result = LoginController.authenticate(role.getValue(), user.getText(), pass.getText());

                if ("Admin".equals(result)) {
                    stage.setScene(new Scene(new AdminMenu(stage), 800, 600));
                } else if (result instanceof Doctor) {
                    Doctor d = (Doctor) result;
                    stage.setScene(new Scene(new DoctorDashboardView(stage, d), 800, 600));
                }

            } catch (LoginException ex) {
                errorLabel.setText("❌ " + ex.getMessage());
                errorLabel.setVisible(true);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
        });

        centerBox.getChildren().addAll(title, role, user, pass, passVisible, showPass, login, errorLabel);
        setCenter(centerBox);
    }
}

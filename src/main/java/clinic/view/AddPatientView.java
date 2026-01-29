package clinic.view;
import clinic.logics.PatientLogic;
import clinic.exceptions.PatientAddingException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AddPatientView extends BorderPane {

    public AddPatientView(Stage stage) {

        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f7fafc;");

        Label title = new Label("➕ Add New Patient");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#2d3748"));

        TextField name = new TextField();
        name.setPromptText("Patient Name");
        name.setMaxWidth(350);

        TextField age = new TextField();
        age.setPromptText("Age");
        age.setMaxWidth(350);

        ComboBox<String> gender = new ComboBox<>();
        gender.getItems().addAll("Male", "Female", "Other");
        gender.setPromptText("Select Gender");
        gender.setMaxWidth(350);

        TextField phone = new TextField();
        phone.setPromptText("Phone Number");
        phone.setMaxWidth(350);

        TextField address = new TextField();
        address.setPromptText("Address");
        address.setMaxWidth(350);

        TextField bloodType = new TextField();
        bloodType.setPromptText("Blood Type (e.g. A+, O-)");
        bloodType.setMaxWidth(350);

        VBox form = new VBox(15, name, age, gender, phone, address, bloodType);
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
                PatientLogic.addPatient(
                        name.getText(),
                        age.getText(),
                        gender.getValue(),
                        phone.getText(),
                        address.getText(),
                        bloodType.getText()
                );

                name.clear(); age.clear(); gender.setValue(null);
                phone.clear(); address.clear(); bloodType.clear();
                errorLabel.setVisible(false);

                new Alert(Alert.AlertType.INFORMATION, "Patient added successfully!", ButtonType.OK).showAndWait();

            } catch (PatientAddingException ex) {
                errorLabel.setText("❌ " + ex.getMessage());
                errorLabel.setVisible(true);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
        });

        back.setOnAction(e -> setCenter(new Label("Select an option from the menu")));
    }
}

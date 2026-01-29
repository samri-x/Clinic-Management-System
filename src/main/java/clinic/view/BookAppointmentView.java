package clinic.view;
import clinic.logics.AppointmentLogic;
import clinic.model.Doctor;
import clinic.model.Patient;
import clinic.exceptions.AppointmentAddingException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class BookAppointmentView extends BorderPane {

    public BookAppointmentView(Stage stage) {

        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f7fafc;");

        Label title = new Label("📅 Book Appointment");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(javafx.scene.paint.Color.web("#2d3748"));

        Label patientLabel = new Label("Patient");
        patientLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        ComboBox<Patient> patients = new ComboBox<>();
        patients.setMaxWidth(350);

        Label doctorLabel = new Label("Doctor");
        doctorLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        ComboBox<Doctor> doctors = new ComboBox<>();
        doctors.setMaxWidth(350);

        Label dateLabel = new Label("Date");
        dateLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        TextField date = new TextField();
        date.setPromptText("YYYY-MM-DD");
        date.setMaxWidth(350);

        VBox form = new VBox(15, patientLabel, patients, doctorLabel, doctors, dateLabel, date);
        form.setAlignment(Pos.CENTER_LEFT);

        Button save = new Button("Book");
        Button back = new Button("Back");

        Label errorLabel = new Label();
        errorLabel.setTextFill(javafx.scene.paint.Color.RED);
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
                AppointmentLogic.addAppointment(
                        patients.getValue(),
                        doctors.getValue(),
                        date.getText()
                );

                date.clear();
                patients.setValue(null);
                doctors.setValue(null);
                errorLabel.setVisible(false);

                new Alert(Alert.AlertType.INFORMATION, "Appointment booked successfully!", ButtonType.OK).showAndWait();

            } catch (AppointmentAddingException ex) {
                errorLabel.setText("❌ " + ex.getMessage());
                errorLabel.setVisible(true);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
        });

        back.setOnAction(e -> setCenter(new Label("Select an option from the menu")));
    }
}

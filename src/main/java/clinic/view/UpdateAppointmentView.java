package clinic.view;

import clinic.logics.AppointmentLogic;
import clinic.model.Appointment;
import clinic.model.Doctor;
import clinic.exceptions.AppointmentAddingException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class UpdateAppointmentView extends BorderPane {

    public UpdateAppointmentView(Stage stage) {
        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f7fafc;");

        Label title = new Label("✏️ Update Appointment");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TableView<Appointment> appointmentTable = new TableView<>();
        appointmentTable.setItems(FXCollections.observableArrayList(clinic.data.DataStore.appointments));

        TableColumn<Appointment, String> patientCol = new TableColumn<>("Patient");
        patientCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPatient().getName()));

        TableColumn<Appointment, String> doctorCol = new TableColumn<>("Doctor");
        doctorCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDoctor().getName()));

        TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate()));

        appointmentTable.getColumns().addAll(patientCol, doctorCol, dateCol);
        appointmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField dateField = new TextField();
        dateField.setPromptText("New Date (YYYY-MM-DD)");

        ComboBox<Doctor> doctorBox = new ComboBox<>();
        doctorBox.setItems(FXCollections.observableArrayList(clinic.data.DataStore.doctors));
        doctorBox.setPromptText("Select New Doctor");

        Button updateBtn = new Button("Update");
        updateBtn.setStyle("-fx-background-color: #3182ce; -fx-text-fill: white; -fx-font-weight: bold;");

        updateBtn.setOnAction(e -> {
            Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    AppointmentLogic.updateAppointment(selected, dateField.getText(), doctorBox.getValue());
                    appointmentTable.refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Appointment updated successfully!", ButtonType.OK).showAndWait();
                } catch (AppointmentAddingException ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "No appointment selected.", ButtonType.OK).showAndWait();
            }
        });

        VBox box = new VBox(15, title, appointmentTable, dateField, doctorBox, updateBtn);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(20));
        setCenter(box);
    }
}

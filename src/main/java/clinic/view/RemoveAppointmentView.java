package clinic.view;
import clinic.logics.AppointmentLogic;
import clinic.model.Appointment;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RemoveAppointmentView extends BorderPane {

    public RemoveAppointmentView(Stage stage) {

        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f7fafc;");

        Label title = new Label("🗑 Remove Appointment");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        TableView<Appointment> appointmentTable = new TableView<>();

        appointmentTable.setItems(FXCollections.observableList(clinic.data.DataStore.appointments));

        TableColumn<Appointment, String> patientCol = new TableColumn<>("Patient");
        patientCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPatient().getName()));

        TableColumn<Appointment, String> doctorCol = new TableColumn<>("Doctor");
        doctorCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDoctor().getName()));

        TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate()));

        appointmentTable.getColumns().addAll(patientCol, doctorCol, dateCol);
        appointmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button remove = new Button("Remove");
        Button back = new Button("Back");

        HBox actions = new HBox(20, remove, back);
        actions.setAlignment(Pos.CENTER);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);

        VBox card = new VBox(25, title, appointmentTable, actions, errorLabel);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; "
                + "-fx-border-radius: 12; -fx-border-color: #cbd5e0; -fx-border-width: 1; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,4);");

        setCenter(card);

        remove.setOnAction(e -> {
            Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                AppointmentLogic.removeAppointment(selected);
                appointmentTable.refresh();
                new Alert(Alert.AlertType.INFORMATION,
                        "Appointment removed successfully!", ButtonType.OK).showAndWait();
            } else {
                errorLabel.setText("❌ Please select an appointment to remove.");
                errorLabel.setVisible(true);
                new Alert(Alert.AlertType.ERROR,
                        "No appointment selected.", ButtonType.OK).showAndWait();
            }
        });

        back.setOnAction(e -> setCenter(new Label("Select an option from the menu")));
    }
}



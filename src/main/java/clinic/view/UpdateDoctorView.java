package clinic.view;

import clinic.logics.DoctorLogic;
import clinic.model.Doctor;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UpdateDoctorView extends BorderPane {

    public UpdateDoctorView(Stage stage) {
        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f7fafc;");

        Label title = new Label("✏️ Update Doctor");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));

        TableView<Doctor> doctorTable = new TableView<>();
        // Bind directly to the actual DataStore list
        doctorTable.setItems(FXCollections.observableList(clinic.data.DataStore.doctors));

        TableColumn<Doctor, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Doctor, String> specCol = new TableColumn<>("Specialization");
        specCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSpecialization()));

        doctorTable.getColumns().addAll(nameCol, specCol);
        doctorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField nameField = new TextField();
        nameField.setPromptText("New Name");

        TextField specField = new TextField();
        specField.setPromptText("New Specialization");

        Button updateBtn = new Button("Update");
        updateBtn.setStyle("-fx-background-color: #3182ce; -fx-text-fill: white; -fx-font-weight: bold;");

        updateBtn.setOnAction(e -> {
            Doctor selected = doctorTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                DoctorLogic.updateDoctor(selected, nameField.getText(), specField.getText());
                doctorTable.refresh(); // redraws updated values
                new Alert(Alert.AlertType.INFORMATION, "Doctor updated successfully!", ButtonType.OK).showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "No doctor selected.", ButtonType.OK).showAndWait();
            }
        });

        VBox box = new VBox(15, title, doctorTable, nameField, specField, updateBtn);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(20));
        setCenter(box);
    }
}

package clinic.view;

import clinic.logics.PatientLogic;
import clinic.model.Patient;
import clinic.exceptions.PatientAddingException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UpdatePatientView extends BorderPane {

    public UpdatePatientView(Stage stage) {
        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f7fafc;");

        Label title = new Label("✏️ Update Patient");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));

        TableView<Patient> patientTable = new TableView<>();
        patientTable.setItems(FXCollections.observableArrayList(clinic.data.DataStore.patients));

        TableColumn<Patient, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Patient, String> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getAge())));

        TableColumn<Patient, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getGender()));

        TableColumn<Patient, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPhone()));

        TableColumn<Patient, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAddress()));

        TableColumn<Patient, String> bloodCol = new TableColumn<>("Blood Type");
        bloodCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBloodType()));

        patientTable.getColumns().addAll(nameCol, ageCol, genderCol, phoneCol, addressCol, bloodCol);
        patientTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TextField nameField = new TextField(); nameField.setPromptText("New Name");
        TextField ageField = new TextField(); ageField.setPromptText("New Age");
        TextField genderField = new TextField(); genderField.setPromptText("New Gender");
        TextField phoneField = new TextField(); phoneField.setPromptText("New Phone");
        TextField addressField = new TextField(); addressField.setPromptText("New Address");
        TextField bloodField = new TextField(); bloodField.setPromptText("New Blood Type");

        Button updateBtn = new Button("Update");
        updateBtn.setStyle("-fx-background-color: #3182ce; -fx-text-fill: white; -fx-font-weight: bold;");

        updateBtn.setOnAction(e -> {
            Patient selected = patientTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    PatientLogic.updatePatient(
                            selected,
                            nameField.getText(),
                            ageField.getText(),
                            genderField.getText(),
                            phoneField.getText(),
                            addressField.getText(),
                            bloodField.getText()
                    );
                    patientTable.refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Patient updated successfully!", ButtonType.OK).showAndWait();
                } catch (PatientAddingException ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "No patient selected.", ButtonType.OK).showAndWait();
            }
        });

        VBox box = new VBox(15, title, patientTable,
                nameField, ageField, genderField, phoneField, addressField, bloodField, updateBtn);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(20));
        setCenter(box);
    }
}

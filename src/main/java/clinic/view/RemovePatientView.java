package clinic.view;
import clinic.logics.PatientLogic;
import clinic.model.Patient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

public class RemovePatientView extends BorderPane {

    public RemovePatientView(Stage stage) {

        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f7fafc;");

        Label title = new Label("🗑 Remove Patient");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        TableView<Patient> patientTable = new TableView<>();

        patientTable.setItems(FXCollections.observableList(clinic.data.DataStore.patients));

        TableColumn<Patient, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Patient, Number> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getAge()));

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

        Button remove = new Button("Remove");
        Button back = new Button("Back");

        HBox actions = new HBox(20, remove, back);
        actions.setAlignment(Pos.CENTER);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);

        VBox card = new VBox(25, title, patientTable, actions, errorLabel);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; "
                + "-fx-border-radius: 12; -fx-border-color: #cbd5e0; -fx-border-width: 1; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,4);");

        setCenter(card);

        remove.setOnAction(e -> {
            Patient selected = patientTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                PatientLogic.removePatient(selected);
                patientTable.refresh();
                new Alert(Alert.AlertType.INFORMATION,
                        "Patient removed successfully!", ButtonType.OK).showAndWait();
            } else {
                errorLabel.setText("❌ Please select a patient to remove.");
                errorLabel.setVisible(true);
                new Alert(Alert.AlertType.ERROR,
                        "No patient selected.", ButtonType.OK).showAndWait();
            }
        });

        back.setOnAction(e -> setCenter(new Label("Select an option from the menu")));
    }
}

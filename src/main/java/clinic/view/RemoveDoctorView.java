package clinic.view;
import clinic.logics.DoctorLogic;
import clinic.model.Doctor;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RemoveDoctorView extends BorderPane {

    public RemoveDoctorView(Stage stage) {

        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f7fafc;");

        Label title = new Label("🗑 Remove Doctor");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        TableView<Doctor> doctorTable = new TableView<>();
        // Bind directly to the actual DataStore list
        doctorTable.setItems(FXCollections.observableList(clinic.data.DataStore.doctors));

        TableColumn<Doctor, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Doctor, String> specCol = new TableColumn<>("Specialization");
        specCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSpecialization()));

        TableColumn<Doctor, String> userCol = new TableColumn<>("Username");
        userCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getUsername()));

        TableColumn<Doctor, String> passCol = new TableColumn<>("Password");
        passCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPassword()));

        doctorTable.getColumns().addAll(nameCol, specCol, userCol, passCol);
        doctorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button remove = new Button("Remove");
        Button back = new Button("Back");

        HBox actions = new HBox(20, remove, back);
        actions.setAlignment(Pos.CENTER);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);

        VBox card = new VBox(25, title, doctorTable, actions, errorLabel);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; "
                + "-fx-border-radius: 12; -fx-border-color: #cbd5e0; -fx-border-width: 1; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,4);");

        setCenter(card);

        remove.setOnAction(e -> {
            Doctor selected = doctorTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                DoctorLogic.removeDoctor(selected); // updates DataStore and CSV
                doctorTable.refresh();              // force redraw
                new Alert(Alert.AlertType.INFORMATION,
                        "Doctor removed successfully!", ButtonType.OK).showAndWait();
            } else {
                errorLabel.setText("❌ Please select a doctor to remove.");
                errorLabel.setVisible(true);
                new Alert(Alert.AlertType.ERROR,
                        "No doctor selected.", ButtonType.OK).showAndWait();
            }
        });


        back.setOnAction(e -> setCenter(new Label("Select an option from the menu")));
    }
}

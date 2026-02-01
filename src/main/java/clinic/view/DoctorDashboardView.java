package clinic.view;
import clinic.logics.DoctorDashboardLogic;
import clinic.model.Appointment;
import clinic.model.Doctor;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DoctorDashboardView extends BorderPane {

    public DoctorDashboardView(Stage stage, Doctor doctor) {

        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f7fafc;");


        Label welcomeLabel = new Label("WELCOME DR " + DoctorDashboardLogic.formatDoctorName(doctor));
        welcomeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        welcomeLabel.setTextFill(javafx.scene.paint.Color.web("#2b6cb0"));

        Label title = new Label("📋 My Appointments");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(javafx.scene.paint.Color.web("#2d3748"));

        var myAppointments = DoctorDashboardLogic.getAppointmentsForDoctor(doctor);

        TableView<Appointment> appointmentTable = new TableView<>();
        appointmentTable.setItems(FXCollections.observableArrayList(myAppointments));
        appointmentTable.setMaxWidth(500);
        appointmentTable.setMaxHeight(350);

        TableColumn<Appointment, String> patientCol = new TableColumn<>("Patient");
        patientCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getPatient().getName()));

        TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDate()));

        appointmentTable.getColumns().addAll(patientCol, dateCol);
        appointmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button refresh = new Button("Refresh");
        refresh.setPrefWidth(150);
        refresh.setStyle("-fx-background-color: #3182ce; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 6;");
        refresh.setOnAction(e -> {
            appointmentTable.getItems().setAll(DoctorDashboardLogic.getAppointmentsForDoctor(doctor));
        });

        Button back = new Button("Logout");
        back.setPrefWidth(150);
        back.setStyle("-fx-background-color: #e53e3e; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 6;");
        back.setOnAction(e -> stage.setScene(new Scene(new LoginView(stage), 800, 600)));

        HBox actions = new HBox(20, refresh, back);
        actions.setAlignment(Pos.CENTER);


        VBox card = new VBox(20, welcomeLabel, title, appointmentTable, actions);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; "
                + "-fx-border-radius: 12; -fx-border-color: #cbd5e0; -fx-border-width: 1; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,4);");

        setCenter(card);
    }
}



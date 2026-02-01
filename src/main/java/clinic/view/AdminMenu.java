package clinic.view;
import clinic.logics.AdminLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdminMenu extends BorderPane {

    public AdminMenu(Stage stage) {

        VBox menuBox = new VBox(20);
        menuBox.setPadding(new Insets(20));
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setPrefWidth(250);
        menuBox.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0; -fx-border-width: 0 1 0 0;");

        Label title = new Label("Admin Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        // --- Doctors section ---
        Label doctorLabel = new Label("Doctors");
        doctorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Button addDoctor = new Button("Add Doctor");
        Button updateDoctor = new Button("Update Doctor");
        Button removeDoctor = new Button("Remove Doctor");

        VBox doctorBox = new VBox(10, addDoctor, updateDoctor, removeDoctor);


        Label patientLabel = new Label("Patients");
        patientLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Button addPatient = new Button("Add Patient");
        Button updatePatient = new Button("Update Patient");
        Button removePatient = new Button("Remove Patient");

        VBox patientBox = new VBox(10, addPatient, updatePatient, removePatient);


        Label appointmentLabel = new Label("Appointments");
        appointmentLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Button bookAppointment = new Button("Book Appointment");
        Button updateAppointment = new Button("Update Appointment");
        Button removeAppointment = new Button("Remove Appointment");

        VBox appointmentBox = new VBox(10, bookAppointment, updateAppointment, removeAppointment);


        Button logout = new Button("Logout");
        logout.setPrefWidth(200);
        logout.setStyle("-fx-background-color: #e53e3e; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 6;");
        logout.setOnAction(e -> stage.setScene(new Scene(new LoginView(stage), 800, 600)));

        Button[] buttons = { addDoctor, updateDoctor, removeDoctor,
                addPatient, updatePatient, removePatient,
                bookAppointment, updateAppointment, removeAppointment };
        for (Button b : buttons) {
            b.setPrefWidth(200);
        }

        menuBox.getChildren().addAll(title, doctorLabel, doctorBox, patientLabel, patientBox, appointmentLabel, appointmentBox, logout);

        StackPane contentArea = new StackPane();
        contentArea.setPadding(new Insets(20));
        contentArea.setStyle("-fx-background-color: white;");

        Label welcome = new Label("Select an option from the menu");
        welcome.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 18));
        contentArea.getChildren().add(welcome);

        // Counts label now uses AdminLogic
        Label counts = new Label(AdminLogic.getCounts());
        counts.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        counts.setPadding(new Insets(0, 20, 0, 0));

        Button refresh = new Button("Refresh");
        refresh.setStyle("-fx-background-color: #3182ce; -fx-text-fill: white; "
                + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 6;");
        refresh.setOnAction(e -> AdminLogic.refreshCountsLabel(counts));

        HBox topBar = new HBox(10, counts, refresh);
        topBar.setAlignment(Pos.TOP_RIGHT);

        BorderPane contentWrapper = new BorderPane();
        contentWrapper.setTop(topBar);
        contentWrapper.setCenter(contentArea);

        addDoctor.setOnAction(e -> contentArea.getChildren().setAll(new AddDoctorView(stage)));
        updateDoctor.setOnAction(e -> contentArea.getChildren().setAll(new UpdateDoctorView(stage)));
        removeDoctor.setOnAction(e -> contentArea.getChildren().setAll(new RemoveDoctorView(stage)));

        addPatient.setOnAction(e -> contentArea.getChildren().setAll(new AddPatientView(stage)));
        updatePatient.setOnAction(e -> contentArea.getChildren().setAll(new UpdatePatientView(stage)));
        removePatient.setOnAction(e -> contentArea.getChildren().setAll(new RemovePatientView(stage)));

        bookAppointment.setOnAction(e -> contentArea.getChildren().setAll(new BookAppointmentView(stage)));
        updateAppointment.setOnAction(e -> contentArea.getChildren().setAll(new UpdateAppointmentView(stage)));
        removeAppointment.setOnAction(e -> contentArea.getChildren().setAll(new RemoveAppointmentView(stage)));

        setLeft(menuBox);
        setCenter(contentWrapper);
    }
    
}

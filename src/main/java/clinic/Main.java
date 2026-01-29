package clinic;
import clinic.data.DataStore;
import clinic.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        DataStore.loadAll();

        stage.setTitle("Clinic Management System");
        stage.setScene(new Scene(new LoginView(stage), 1000, 800));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





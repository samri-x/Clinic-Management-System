package clinic.logics;

import clinic.data.DataStore;

public class AdminLogic {

    public static String getCounts() {
        return "Doctors: " + DataStore.doctors.size() +
                "   |   Patients: " + DataStore.patients.size() +
                "   |   Appointments: " + DataStore.appointments.size();
    }

    public static void refreshCountsLabel(javafx.scene.control.Label countsLabel) {
        countsLabel.setText(getCounts());
    }
    
}

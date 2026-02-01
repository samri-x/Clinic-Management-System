package clinic.logics;
import clinic.data.DataStore;
import clinic.model.Appointment;
import clinic.model.Doctor;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorDashboardLogic {


    public static List<Appointment> getAppointmentsForDoctor(Doctor doctor) {
        return DataStore.appointments.stream()
                .filter(a -> a.getDoctor().equals(doctor))
                .collect(Collectors.tolist());
    }


    public static String formatDoctorName(Doctor doctor) {
        if (doctor == null || doctor.getName() == null) return "";
        String[] words = doctor.getName().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word.substring(0,1).toUpperCase())
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }
        return sb.toString().trim();
    }
}

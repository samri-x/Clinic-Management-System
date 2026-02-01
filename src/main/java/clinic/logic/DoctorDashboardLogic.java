package clinic.logics;
import clinic.data.DataStore;
import clinic.model.Appointment;
import clinic.model.Doctor;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorDashboardLogic {

    public static List<Appointment> getAppointmentsForDoctor(Doctor doctor) {
        if (doctor == null) return List.of();
        return DataStore.appointments.stream()
                .filter(a -> a.getDoctor() != null && a.getDoctor().getId() == doctor.getId())
                .collect(Collectors.toList());
    }


    public static String formatDoctorName(Doctor doctor) {
        if (doctor == null || doctor.getName() == null) return "";
        String[] words = doctor.getName().trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }
}

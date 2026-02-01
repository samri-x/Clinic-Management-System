package clinic.logics;
import clinic.data.DataStore;
import clinic.model.Appointment;
import clinic.model.Doctor;
import clinic.model.Patient;
import clinic.exceptions.AppointmentAddingException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AppointmentLogic {

    public static void addAppointment(Patient patient, Doctor doctor, String dateText) throws AppointmentAddingException {
        LocalDate appointmentDate = validateAppointment(patient, doctor, dateText);

        Appointment a = new Appointment(
                patient,
                doctor,
                appointmentDate.toString()
        );
        DataStore.addAppointment(a);
        System.out.println("Appointment added and saved to file.");
    }

    public static void removeAppointment(Appointment appointment) {
        if (appointment != null) {
            DataStore.removeAppointment(appointment);
            System.out.println("Appointment removed and saved to file.");
        }
    }

    public static void updateAppointment(Appointment appointment, String newDate, Doctor newDoctor) throws AppointmentAddingException {
        if (appointment == null) return;

        if (newDate != null && !newDate.trim().isEmpty()) {

            if (!newDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new AppointmentAddingException("Date must be in format YYYY-MM-DD.");
            }
            appointment.setDate(newDate);
        }

        if (newDoctor != null) {
            appointment.setDoctor(newDoctor);
        }


        DataStore.saveAppointments();
        System.out.println("Appointment updated and saved to file.");
    }

    private static LocalDate validateAppointment(Patient patient, Doctor doctor, String dateText) throws AppointmentAddingException {
        if (patient == null) {
            throw new AppointmentAddingException("Patient must be selected.");
        }
        if (doctor == null) {
            throw new AppointmentAddingException("Doctor must be selected.");
        }
        if (dateText == null || dateText.trim().isEmpty()) {
            throw new AppointmentAddingException("Date cannot be empty.");
        }
        try {
            LocalDate parsedDate = LocalDate.parse(dateText);
            if (parsedDate.isBefore(LocalDate.now())) {
                throw new AppointmentAddingException("Date cannot be in the past.");
            }
            return parsedDate;
        } catch (DateTimeParseException e) {
            throw new AppointmentAddingException("Date must be in format YYYY-MM-DD.");
        }
    }
}

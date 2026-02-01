package clinic.logics;

import clinic.data.DataStore;
import clinic.model.Doctor;
import clinic.exceptions.DoctorAddingException;

public class DoctorLogic {

    public static void addDoctor(String name, String spec, String username, String password) throws DoctorAddingException {
        validateDoctor(username, password);

        Doctor d = new Doctor(
                DataStore.doctors.size() + 1,
                name,
                spec,
                username,
                password
        );
        DataStore.addDoctor(d); // persists immediately
    }

    public static void updateDoctor(Doctor doctor, String newName, String newSpec) {
        if (doctor != null) {
            if (newName != null && !newName.trim().isEmpty()) {
                doctor.setName(newName);
            }
            if (newSpec != null && !newSpec.trim().isEmpty()) {
                doctor.setSpecialization(newSpec);
            }
            DataStore.saveDoctors(); // persist changes
            System.out.println("Doctor updated and saved to file.");
        }
    }

    public static void removeDoctor(Doctor doctor) {
        if (doctor != null) {
            DataStore.removeDoctor(doctor); // persist removal
            System.out.println("Doctor removed and saved to file.");
        }
    }

    // Validation for adding
    private static void validateDoctor(String username, String password) throws DoctorAddingException {
        if (username == null || username.trim().isEmpty()) {
            throw new DoctorAddingException("Username cannot be empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new DoctorAddingException("Password cannot be empty.");
        }
        if (password.length() < 6) {
            throw new DoctorAddingException("Password must be at least 6 characters long.");
        }
        for (Doctor d : DataStore.doctors) {
            if (d.getUsername().equals(username)) {
                throw new DoctorAddingException("Username already exists.");
            }
        }
    }
}

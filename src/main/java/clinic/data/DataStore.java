package clinic.data;

import clinic.model.*;
import java.io.*;
import java.util.ArrayList;

public class DataStore {

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "1234";

    public static ArrayList<Doctor> doctors = new ArrayList<>();
    public static ArrayList<Patient> patients = new ArrayList<>();
    public static ArrayList<Appointment> appointments = new ArrayList<>();

    private static final String DOCTOR_FILE = "doctors.csv";
    private static final String PATIENT_FILE = "patients.csv";
    private static final String APPOINTMENT_FILE = "appointments.csv";

    // ===== LOAD ALL DATA =====
    public static void loadAll() {
        loadDoctors();
        loadPatients();
        loadAppointments();
    }

    // ===== DOCTORS =====
    public static void addDoctor(Doctor d) {
        doctors.add(d);
        saveDoctors();
    }

    public static void removeDoctor(Doctor d) {
        doctors.remove(d);
        appointments.removeIf(a -> a.getDoctor().equals(d));
        saveDoctors();
        saveAppointments();
    }

    private static void loadDoctors() {
        doctors.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTOR_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                doctors.add(new Doctor(
                        Integer.parseInt(p[0]),
                        p[1],
                        p[2],
                        p[3],
                        p[4]
                ));
            }
        } catch (IOException ignored) {}
    }

    private static void saveDoctors() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTOR_FILE))) {
            for (Doctor d : doctors) {
                bw.write(d.getId() + "," + d.getName() + "," +
                        d.getSpecialization() + "," +
                        d.getUsername() + "," +
                        d.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ===== PATIENTS =====
    // ===== PATIENTS =====
    public static void addPatient(Patient p) {
        patients.add(p);
        savePatients();
    }

    public static void removePatient(Patient p) {
        patients.remove(p);
        appointments.removeIf(a -> a.getPatient().equals(p));
        savePatients();
        saveAppointments();
    }

    private static void loadPatients() {
        patients.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 7) {
                    patients.add(new Patient(
                            Integer.parseInt(p[0]),
                            p[1],
                            Integer.parseInt(p[2]),
                            p[3],
                            p[4],
                            p[5],
                            p[6]
                    ));
                }
            }
        } catch (IOException ignored) {}
    }

    private static void savePatients() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENT_FILE))) {
            for (Patient p : patients) {
                bw.write(p.getId() + "," +
                        p.getName() + "," +
                        p.getAge() + "," +
                        p.getGender() + "," +
                        p.getPhone() + "," +
                        p.getAddress() + "," +
                        p.getBloodType());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // ===== APPOINTMENTS =====
    public static void addAppointment(Appointment a) {
        appointments.add(a);
        saveAppointments();
    }

    public static void removeAppointment(Appointment a) {
        appointments.remove(a);
        saveAppointments();
    }

    private static void loadAppointments() {
        appointments.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                Patient patient = findPatient(Integer.parseInt(p[0]));
                Doctor doctor = findDoctor(Integer.parseInt(p[1]));
                if (patient != null && doctor != null) {
                    appointments.add(new Appointment(patient, doctor, p[2]));
                }
            }
        } catch (IOException ignored) {}
    }

    private static void saveAppointments() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENT_FILE))) {
            for (Appointment a : appointments) {
                bw.write(a.getPatient().getId() + "," +
                        a.getDoctor().getId() + "," +
                        a.getDate());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ===== HELPERS =====
    private static Doctor findDoctor(int id) {
        for (Doctor d : doctors)
            if (d.getId() == id) return d;
        return null;
    }

    private static Patient findPatient(int id) {
        for (Patient p : patients)
            if (p.getId() == id) return p;
        return null;
    }
}

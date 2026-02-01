package clinic.data;

import clinic.model.*;
import java.io.*;
import java.util.ArrayList;

public class DataStore {

    public static ArrayList<Doctor> doctors = new ArrayList<>();
    public static ArrayList<Patient> patients = new ArrayList<>();
    public static ArrayList<Appointment> appointments = new ArrayList<>();

    // Put all CSVs in a dedicated "data" folder in project root
    private static final String DATA_FOLDER = "data";
    private static final String DOCTOR_FILE = DATA_FOLDER + "/doctors.csv";
    private static final String PATIENT_FILE = DATA_FOLDER + "/patients.csv";
    private static final String APPOINTMENT_FILE = DATA_FOLDER + "/appointments.csv";

    // Track max IDs to avoid duplicates
    private static int maxDoctorId = 0;
    private static int maxPatientId = 0;

    // ===== LOAD ALL DATA =====
    public static void loadAll() {
        ensureFilesExist();   // auto-create files if missing
        loadDoctors();
        loadPatients();
        loadAppointments();
    }

    // ===== DOCTORS =====
    public static void addDoctor(Doctor d) {
        d.setId(++maxDoctorId); // assign unique ID
        doctors.add(d);
        saveDoctors();
    }

    public static void removeDoctor(Doctor d) {
        if (d == null) return;
        doctors.removeIf(doc -> doc.getId() == d.getId());
        appointments.removeIf(a -> a.getDoctor() != null && a.getDoctor().getId() == d.getId());
        saveDoctors();
        saveAppointments();
    }

    private static void loadDoctors() {
        doctors.clear();
        File file = new File(DOCTOR_FILE);
        System.out.println("Loading doctors from: " + file.getAbsolutePath());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 5) {
                    int id = Integer.parseInt(p[0]);
                    doctors.add(new Doctor(id, p[1], p[2], p[3], p[4]));
                    if (id > maxDoctorId) maxDoctorId = id;
                }
            }
            System.out.println("Loaded " + doctors.size() + " doctors.");
        } catch (IOException e) {
            System.err.println("Error loading doctors: " + e.getMessage());
        }
    }

    public static void saveDoctors() {
        File file = new File(DOCTOR_FILE);
        System.out.println("Saving doctors to: " + file.getAbsolutePath());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (Doctor d : doctors) {
                bw.write(d.getId() + "," + d.getName() + "," +
                        d.getSpecialization() + "," +
                        d.getUsername() + "," +
                        d.getPassword());
                bw.newLine();
            }
            System.out.println("Saved " + doctors.size() + " doctors.");
        } catch (IOException e) {
            System.err.println("Error saving doctors: " + e.getMessage());
        }
    }

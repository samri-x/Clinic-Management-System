package clinic.data;
import clinic.model.*;
import java.io.*;
import java.util.ArrayList;

public class DataStore {

    public static ArrayList<Doctor> doctors = new ArrayList<>();
    public static ArrayList<Patient> patients = new ArrayList<>();
    public static ArrayList<Appointment> appointments = new ArrayList<>();

    
    private static final String DATA_FOLDER = "data";
    private static final String DOCTOR_FILE = DATA_FOLDER + "/doctors.csv";
    private static final String PATIENT_FILE = DATA_FOLDER + "/patients.csv";
    private static final String APPOINTMENT_FILE = DATA_FOLDER + "/appointments.csv";
    
    private static int maxDoctorId = 0;
    private static int maxPatientId = 0;

    // ===== LOAD ALL DATA =====
    public static void loadAll() {
        ensureFilesExist();   
        loadDoctors();
        loadPatients();
        loadAppointments();
    }

    // ===== DOCTORS =====
    public static void addDoctor(Doctor d) {
        d.setId(++maxDoctorId); 
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

    // ===== PATIENTS =====
    public static void addPatient(Patient p) {
        p.setId(++maxPatientId); // assign uniqu ID
        patients.add(p);
        savePatients();
    }

    public static void removePatient(Patient p) {
        if (p == null) return;
        patients.removeIf(pt -> pt.getId() == p.getId());
        appointments.removeIf(a -> a.getPatient() != null && a.getPatient().getId() == p.getId());
        savePatients();
        saveAppointments();
    }

    private static void loadPatients() {
        patients.clear();
        File file = new File(PATIENT_FILE);
        System.out.println("Loading patients from: " + file.getAbsolutePath());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 7) {
                    int id = Integer.parseInt(p[0]);
                    patients.add(new Patient(id, p[1], Integer.parseInt(p[2]), p[3], p[4], p[5], p[6]));
                    if (id > maxPatientId) maxPatientId = id;
                }
            }
            System.out.println("Loaded " + patients.size() + " patients.");
        } catch (IOException e) {
            System.err.println("Error loading patients: " + e.getMessage());
        }
    }

    public static void savePatients() {
        File file = new File(PATIENT_FILE);
        System.out.println("Saving patients to: " + file.getAbsolutePath());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
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
            System.out.println("Saved " + patients.size() + " patients.");
        } catch (IOException e) {
            System.err.println("Error saving patients: " + e.getMessage());
        }
    }

    // ===== APPOINTMENTS =====
    public static void addAppointment(Appointment a) {
        appointments.add(a);
        saveAppointments();
    }

    public static void removeAppointment(Appointment a) {
        if (a == null) return;
        appointments.removeIf(ap -> ap.equals(a));
        saveAppointments();
    }

    private static void loadAppointments() {
        appointments.clear();
        File file = new File(APPOINTMENT_FILE);
        System.out.println("Loading appointments from: " + file.getAbsolutePath());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 3) {
                    Patient patient = findPatient(Integer.parseInt(p[0]));
                    Doctor doctor = findDoctor(Integer.parseInt(p[1]));
                    if (patient != null && doctor != null) {
                        appointments.add(new Appointment(patient, doctor, p[2]));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading appointments: " + e.getMessage());
        }
    }

    public static void saveAppointments() {
        File file = new File(APPOINTMENT_FILE);
        System.out.println("Saving appointments to: " + file.getAbsolutePath());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (Appointment a : appointments) {
                bw.write(a.getPatient().getId() + "," +
                        a.getDoctor().getId() + "," +
                        a.getDate());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving appointments: " + e.getMessage());
        }
    }

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

    private static void ensureFilesExist() {
        try {
            File folder = new File(DATA_FOLDER);
            if (!folder.exists()) folder.mkdir();

            File doctorFile = new File(DOCTOR_FILE);
            if (!doctorFile.exists()) doctorFile.createNewFile();

            File patientFile = new File(PATIENT_FILE);
            if (!patientFile.exists()) patientFile.createNewFile();

            File appointmentFile = new File(APPOINTMENT_FILE);
            if (!appointmentFile.exists()) appointmentFile.createNewFile();
        } catch (IOException e) {
            System.err.println("Error ensuring files exist: " + e.getMessage());
        }
    }
}

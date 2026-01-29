package clinic.model;

public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private String date;

    public Appointment(Patient patient, Doctor doctor, String date) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    @Override
    public String toString() {
        return date + " - " + patient.getName();
    }
}

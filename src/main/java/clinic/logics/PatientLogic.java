package clinic.logics;
import clinic.data.DataStore;
import clinic.model.Patient;
import clinic.exceptions.PatientAddingException;

public class PatientLogic {


    public static void addPatient(String name, String ageText, String gender,
                                  String phone, String address, String bloodType) throws PatientAddingException {
        int age = validatePatient(name, ageText, gender, phone, address, bloodType);

        Patient p = new Patient(
                DataStore.patients.size() + 1,
                name,
                age,
                gender,
                phone,
                address,
                bloodType
        );
        DataStore.addPatient(p);
    }


    public static void removePatient(Patient patient) {
        DataStore.patients.remove(patient);
    }



    public static void updatePatient(Patient patient, String newName, String newAgeText,
                                     String newGender, String newPhone,
                                     String newAddress, String newBloodType) throws PatientAddingException {
        if (patient == null) return;

        if (newName != null && !newName.trim().isEmpty()) {
            patient.setName(newName);
        }
        if (newAgeText != null && !newAgeText.trim().isEmpty()) {
            try {
                int age = Integer.parseInt(newAgeText);
                if (age <= 0) {
                    throw new PatientAddingException("Age must be a positive number.");
                }
                patient.setAge(age);
            } catch (NumberFormatException e) {
                throw new PatientAddingException("Age must be a valid number.");
            }
        }
        if (newGender != null && !newGender.trim().isEmpty()) {
            patient.setGender(newGender);
        }
        if (newPhone != null && !newPhone.trim().isEmpty()) {
            patient.setPhone(newPhone);
        }
        if (newAddress != null && !newAddress.trim().isEmpty()) {
            patient.setAddress(newAddress);
        }
        if (newBloodType != null && !newBloodType.trim().isEmpty()) {
            patient.setBloodType(newBloodType);
        }
    }

    // Validation for adding
    private static int validatePatient(String name, String ageText, String gender,
                                       String phone, String address, String bloodType) throws PatientAddingException {
        if (name == null || name.trim().isEmpty()) {
            throw new PatientAddingException("Patient name cannot be empty.");
        }
        if (ageText == null || ageText.trim().isEmpty()) {
            throw new PatientAddingException("Age cannot be empty.");
        }
        if (gender == null || gender.trim().isEmpty()) {
            throw new PatientAddingException("Gender must be selected.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new PatientAddingException("Phone number cannot be empty.");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new PatientAddingException("Address cannot be empty.");
        }
        if (bloodType == null || bloodType.trim().isEmpty()) {
            throw new PatientAddingException("Blood type cannot be empty.");
        }

        try {
            int age = Integer.parseInt(ageText);
            if (age <= 0) {
                throw new PatientAddingException("Age must be a positive number.");
            }
            return age;
        } catch (NumberFormatException e) {
            throw new PatientAddingException("Age must be a valid number.");
        }
    }
}

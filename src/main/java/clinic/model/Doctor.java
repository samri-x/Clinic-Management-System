package clinic.model;

public class Doctor extends Person implements User {

    private String specialization;
    private String username;
    private String password;

    public Doctor(int id, String name, String specialization,
                  String username, String password) {
        super(id, name);
        this.specialization = specialization;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean login(String u, String p) {
        return username.equals(u) && password.equals(p);
    }

    public String getSpecialization() { return specialization; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setName(String name) {
        this.name = name;
    } public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    @Override
    public String getRole() {
        return "Doctor";
    }

    @Override
    public String toString() {
        return "Dr. " + name + " (" + specialization + ")";
    }
}

package clinic.model;

public class Patient extends Person {

    private int age;
    private String gender;
    private String phone;
    private String address;
    private String bloodType;

    public Patient(int id, String name, int age, String gender, String phone, String address, String bloodType) {
        super(id, name);
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.bloodType = bloodType;
    }

    public int getAge() {

        return age;
    }
    public String getGender() {

        return gender;
    }
    public String getPhone() {

        return phone;
    }
    public String getAddress() {

        return address;
    }
    public String getBloodType() {

        return bloodType;
    }

    public void setName(String name) {

        this.name = name;
    }
    public void setAge(int age) {

        this.age = age;
    }
    public void setGender(String gender) {

        this.gender = gender;
    }
    public void setPhone(String phone) {

        this.phone = phone;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setBloodType(String bloodType) {

        this.bloodType = bloodType;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String getRole() {
        return "Patient";
    }

    @Override
    public String toString() {
        return name + " (" + age + " yrs, " + gender + ", " + bloodType + ")";
    }
}

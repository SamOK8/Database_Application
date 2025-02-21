package database_application;

public class User {
    private int id;
    private String name;
    private UserRole role;
    private String drivingLicenseCardNumber;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {
        return name;
    }
    public UserRole getRole() {return role;}
    public String getDrivingLicenseCardNumber() {return drivingLicenseCardNumber;}

    public User(String name, String role) {
        this.name = name;
        try {
            this.role = UserRole.valueOf(role);
        } catch (IllegalArgumentException | NullPointerException e) {
            this.role = UserRole.ZAKAZNIK;
        }
    }

    public void setDrivingLicenseCardNumber(String drivingLicenseCardNumber) {
        this.drivingLicenseCardNumber = drivingLicenseCardNumber;
    }
}

package database_application;

public class Uzivatel {
    private String jmeno;
    private RoleUzivatele role;

    public String getJmeno() {
        return jmeno;
    }
    public RoleUzivatele getRole() {return role;}

    public Uzivatel(String jmeno, String role) {
        this.jmeno = jmeno;
        this.role = RoleUzivatele.valueOf(role);
    }
}

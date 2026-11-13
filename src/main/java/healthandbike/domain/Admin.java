package healthandbike.domain;

public class Admin extends Person {

    private String department;
    private String accessLevel;

    public Admin() {
        super();
    }

    public Admin(int id, String name, String lastName, String email, String password, boolean status, String phone, String department, String accessLevel) {
        super(id, name, lastName, email, password, status, phone);
        this.department = department;
        this.accessLevel = accessLevel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Depto: " + department +
                " | Nivel Acceso: " + accessLevel;
    }
}

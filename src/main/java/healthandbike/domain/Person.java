package healthandbike.domain;

public class Person {

    protected int id;
    protected String name;
    protected String lastName;
    protected String email;
    protected String password;
    protected boolean status;
    protected String phone;

    public Person() {
    }

    public Person(int id, String name, String lastName, String email, String password, boolean status, String phone) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.status = status;
        this.phone = phone;
    }

    public Person(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Nombre: " + name + " " + lastName +
                " | Email: " + email +
                " | Tel: " + phone +
                " | Activo: " + status;
    }
}

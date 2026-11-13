package healthandbike.domain;

public class User extends Person {

    private String role;
    private String address;
    private double balance;

    public User() {
        super();
    }

    public User(int id, String name, String lastName, String email, String password, boolean status, String phone, String role, String address, double balance) {
        super(id, name, lastName, email, password, status, phone);
        this.role = role;
        this.address = address;
        this.balance = balance;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Rol: " + role +
                " | Dirección: " + address +
                " | Saldo: $" + balance;
    }
}

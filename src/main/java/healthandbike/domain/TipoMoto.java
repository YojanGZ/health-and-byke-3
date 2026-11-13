package healthandbike.domain;

public class TipoMoto {

    private int idMoto;
    private String brand;
    private String model;
    private String cylinderCapacity;
    private String licensePlate;
    private int year;
    private User user;

    public TipoMoto() {
    }

    public TipoMoto(int idMoto, String brand, String model, String cylinderCapacity, String licensePlate, int year, User user) {
        this.idMoto = idMoto;
        this.brand = brand;
        this.model = model;
        this.cylinderCapacity = cylinderCapacity;
        this.licensePlate = licensePlate;
        this.year = year;
        this.user = user;
    }

    public int getIdMoto() {
        return idMoto;
    }

    public void setIdMoto(int idMoto) {
        this.idMoto = idMoto;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCylinderCapacity() {
        return cylinderCapacity;
    }

    public void setCylinderCapacity(String cylinderCapacity) {
        this.cylinderCapacity = cylinderCapacity;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        String userName = (user != null) ? user.getName() + " " + user.getLastName() + " (ID: " + user.getId() + ")" : "Sin Propietario";
        return "ID Moto: " + idMoto +
                " | Placa: " + licensePlate +
                " | Marca: " + brand +
                " | Modelo: " + model +
                " | Cilindraje: " + cylinderCapacity + "cc" +
                " | Año: " + year +
                " | Propietario: " + userName;
    }
}

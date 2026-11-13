package healthandbike.domain;

public class UbicacionTaller {

    private int idTaller;
    private String workshopName;
    private String address;
    private String city;
    private String phone;
    private double latitude;
    private double longitude;

    public UbicacionTaller() {
    }

    public UbicacionTaller(int idTaller, String workshopName, String address, String city, String phone, double latitude, double longitude) {
        this.idTaller = idTaller;
        this.workshopName = workshopName;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(int idTaller) {
        this.idTaller = idTaller;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "ID Taller: " + idTaller +
                " | Nombre: " + workshopName +
                " | Ciudad: " + city +
                " | Dirección: " + address +
                " | Tel: " + phone +
                " | Coordenadas: (" + latitude + ", " + longitude + ")";
    }
}

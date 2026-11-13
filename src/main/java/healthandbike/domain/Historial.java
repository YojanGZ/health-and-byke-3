package healthandbike.domain;

public class Historial {

    private int idHistorial;
    private String entryDate;
    private String departureDate;
    private double totalCost;
    private String diagnostic;
    private User user;
    private UbicacionTaller taller;

    public Historial() {
    }

    public Historial(int idHistorial, String entryDate, String departureDate, double totalCost, String diagnostic, User user, UbicacionTaller taller) {
        this.idHistorial = idHistorial;
        this.entryDate = entryDate;
        this.departureDate = departureDate;
        this.totalCost = totalCost;
        this.diagnostic = diagnostic;
        this.user = user;
        this.taller = taller;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UbicacionTaller getTaller() {
        return taller;
    }

    public void setTaller(UbicacionTaller taller) {
        this.taller = taller;
    }

    @Override
    public String toString() {
        String userName = (user != null) ? user.getName() + " " + user.getLastName() : "Sin Usuario";
        String tallerName = (taller != null) ? taller.getWorkshopName() : "Sin Taller";
        return "ID Historial: " + idHistorial +
                " | Fecha Entrada: " + entryDate +
                " | Fecha Salida: " + departureDate +
                " | Costo Total: $" + totalCost +
                " | Diagnóstico: " + diagnostic +
                " | Usuario: " + userName +
                " | Taller: " + tallerName;
    }
}

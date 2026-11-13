package healthandbike.domain;

public class Arreglos {

    private int idArreglo;
    private String description;
    private double laborCost;
    private Historial historial;

    public Arreglos() {
    }

    public Arreglos(int idArreglo, String description, double laborCost, Historial historial) {
        this.idArreglo = idArreglo;
        this.description = description;
        this.laborCost = laborCost;
        this.historial = historial;
    }

    public int getIdArreglo() {
        return idArreglo;
    }

    public void setIdArreglo(int idArreglo) {
        this.idArreglo = idArreglo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }

    public Historial getHistorial() {
        return historial;
    }

    public void setHistorial(Historial historial) {
        this.historial = historial;
    }

    @Override
    public String toString() {
        int idH = (historial != null) ? historial.getIdHistorial() : 0;
        return "ID Arreglo: " + idArreglo +
                " | Descripción: " + description +
                " | Mano de Obra: $" + laborCost +
                " | ID Historial: " + idH;
    }
}

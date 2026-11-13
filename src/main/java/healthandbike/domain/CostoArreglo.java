package healthandbike.domain;

public class CostoArreglo {

    private int idCosto;
    private double sparePartsCost;
    private double laborCost;
    private double totalAmount;
    private Arreglos arreglos;

    public CostoArreglo() {
    }

    public CostoArreglo(int idCosto, double sparePartsCost, double laborCost, double totalAmount, Arreglos arreglos) {
        this.idCosto = idCosto;
        this.sparePartsCost = sparePartsCost;
        this.laborCost = laborCost;
        this.totalAmount = totalAmount;
        this.arreglos = arreglos;
    }

    public int getIdCosto() {
        return idCosto;
    }

    public void setIdCosto(int idCosto) {
        this.idCosto = idCosto;
    }

    public double getSparePartsCost() {
        return sparePartsCost;
    }

    public void setSparePartsCost(double sparePartsCost) {
        this.sparePartsCost = sparePartsCost;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Arreglos getArreglos() {
        return arreglos;
    }

    public void setArreglos(Arreglos arreglos) {
        this.arreglos = arreglos;
    }

    @Override
    public String toString() {
        String arregloDesc = (arreglos != null) ? arreglos.getDescription() : "Sin Arreglo Asociado";
        return "ID Costo: " + idCosto +
                " | Repuestos: $" + sparePartsCost +
                " | Mano de Obra: $" + laborCost +
                " | Monto Total: $" + totalAmount +
                " | Arreglo: " + arregloDesc;
    }
}

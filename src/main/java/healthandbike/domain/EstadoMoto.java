package healthandbike.domain;

public class EstadoMoto {

    private int idEstado;
    private double mileage;
    private String dateRegister;
    private String statusDescription;
    private TipoMoto tipoMoto;

    public EstadoMoto() {
    }

    public EstadoMoto(int idEstado, double mileage, String dateRegister, String statusDescription, TipoMoto tipoMoto) {
        this.idEstado = idEstado;
        this.mileage = mileage;
        this.dateRegister = dateRegister;
        this.statusDescription = statusDescription;
        this.tipoMoto = tipoMoto;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public TipoMoto getTipoMoto() {
        return tipoMoto;
    }

    public void setTipoMoto(TipoMoto tipoMoto) {
        this.tipoMoto = tipoMoto;
    }

    @Override
    public String toString() {
        String motoInfo = (tipoMoto != null) ? tipoMoto.getLicensePlate() + " - " + tipoMoto.getBrand() : "Sin Moto";
        return "ID Estado: " + idEstado +
                " | Moto: " + motoInfo +
                " | Kilometraje: " + mileage + " km" +
                " | Fecha: " + dateRegister +
                " | Descripción: " + statusDescription;
    }
}

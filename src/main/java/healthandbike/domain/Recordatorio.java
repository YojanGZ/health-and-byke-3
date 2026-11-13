package healthandbike.domain;

public class Recordatorio {

    private int idRecordatorio;
    private String title;
    private String description;
    private String reminderDate;
    private boolean status;
    private User user;

    public Recordatorio() {
    }

    public Recordatorio(int idRecordatorio, String title, String description, String reminderDate, boolean status, User user) {
        this.idRecordatorio = idRecordatorio;
        this.title = title;
        this.description = description;
        this.reminderDate = reminderDate;
        this.status = status;
        this.user = user;
    }

    public int getIdRecordatorio() {
        return idRecordatorio;
    }

    public void setIdRecordatorio(int idRecordatorio) {
        this.idRecordatorio = idRecordatorio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        String userName = (user != null) ? user.getName() + " " + user.getLastName() : "Sin Usuario";
        return "ID Recordatorio: " + idRecordatorio +
                " | Título: " + title +
                " | Descripción: " + description +
                " | Fecha: " + reminderDate +
                " | Pendiente: " + status +
                " | Usuario: " + userName;
    }
}

package projet.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Admin {
    private StringProperty nomAdmin;
    private StringProperty passwordAdmin;

    public String getNomAdmin() {
        return nomAdmin.get();
    }

    public StringProperty nomAdminProperty() {
        return nomAdmin;
    }

    public void setNomAdmin(String nomAdmin) {
        this.nomAdmin.set(nomAdmin);
    }

    public String getPasswordAdmin() {
        return passwordAdmin.get();
    }

    public StringProperty passwordAdminProperty() {
        return passwordAdmin;
    }

    public void setPasswordAdmin(String passwordAdmin) {
        this.passwordAdmin.set(passwordAdmin);
    }

    public Admin(String nomAdmin, String passwordAdmin) {
        this.nomAdmin = new SimpleStringProperty(nomAdmin);
        this.passwordAdmin = new SimpleStringProperty(passwordAdmin);
    }




}

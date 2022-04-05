package projet.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class Client {
    private final IntegerProperty idClient;
    private final StringProperty nomClient;
    private final StringProperty prenomClient;
    private final StringProperty adresseClient;
    private final StringProperty villeClient;
    private final StringProperty emailClient;
    private final StringProperty telephoneClient;
    private final StringProperty password;

    public int getIdClient() {
        return idClient.get();
    }

    public IntegerProperty idClientProperty() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient.set(idClient);
    }

    public String getNomClient() {
        return nomClient.get();
    }

    public StringProperty nomClientProperty() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient.set(nomClient);
    }

    public String getPrenomClient() {
        return prenomClient.get();
    }

    public StringProperty prenomClientProperty() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient.set(prenomClient);
    }

    public String getAdresseClient() {
        return adresseClient.get();
    }

    public StringProperty adresseClientProperty() {
        return adresseClient;
    }

    public void setAdresseClient(String adresseClient) {
        this.adresseClient.set(adresseClient);
    }

    public String getVilleClient() {
        return villeClient.get();
    }

    public StringProperty villeClientProperty() {
        return villeClient;
    }

    public void setVilleClient(String villeClient) {
        this.villeClient.set(villeClient);
    }

    public String getEmailClient() {
        return emailClient.get();
    }

    public StringProperty emailClientProperty() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient.set(emailClient);
    }

    public String getTelephoneClient() {
        return telephoneClient.get();
    }

    public StringProperty telephoneClientProperty() {
        return telephoneClient;
    }

    public void setTelephoneClient(String telephoneClient) {
        this.telephoneClient.set(telephoneClient);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }


    public Client(Integer idClient, String nomClient, String prenomClient, String adresseClient, String villeClient, String emailClient, String telephoneClient, String password) {
        this.idClient = new SimpleIntegerProperty(idClient);
        this.nomClient = new SimpleStringProperty(nomClient);
        this.prenomClient = new SimpleStringProperty(prenomClient);
        this.adresseClient = new SimpleStringProperty(adresseClient);
        this.villeClient = new SimpleStringProperty(villeClient);
        this.emailClient = new SimpleStringProperty(emailClient);
        this.telephoneClient = new SimpleStringProperty(telephoneClient);
        this.password = new SimpleStringProperty(password);
    }

    public Client() {
        this(0, null, null, null, null, null, null, null);
    }

    public ObservableList<Client> getClientData() {
        ObservableList<Client> clientDataList = FXCollections.observableArrayList();
        try {
            Connection connection = DataBase.initConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM client ";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Client clients = new Client(result.getInt("id_client"), result.getString("nom_client"), result.getString("prenom_client"), result.getString("adresse_client"), result.getString("ville_client"), result.getString("email_client"), result.getString("telephone_client"), result.getString("password"));
                clientDataList.add(clients);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientDataList;
    }


}

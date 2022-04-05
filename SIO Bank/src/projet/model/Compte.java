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

public class Compte  {
    private IntegerProperty idCompte ;
    private StringProperty nomClient;
    private StringProperty prenomClient;
    private StringProperty dateCompte;
    private IntegerProperty solde;
    private IntegerProperty limiteRetrait;

    public int getIdCompte() {
        return idCompte.get();
    }

    public IntegerProperty idCompteProperty() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte.set(idCompte);
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

    public String getDateCompte() {
        return dateCompte.get();
    }

    public StringProperty dateCompteProperty() {
        return dateCompte;
    }

    public void setDateCompte(String dateCompte) {
        this.dateCompte.set(dateCompte);
    }

    public int getSolde() {
        return solde.get();
    }

    public IntegerProperty soldeProperty() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde.set(solde);
    }

    public int getLimiteRetrait() {
        return limiteRetrait.get();
    }

    public IntegerProperty limiteRetraitProperty() {
        return limiteRetrait;
    }

    public void setLimiteRetrait(int limiteRetrait) {
        this.limiteRetrait.set(limiteRetrait);
    }

    public Compte() {
        this(0,null,null,null,0,0);
    }

    public Compte(int idCompte, String nomClient, String prenomClient, String dateCompte, int solde, int  limiteRetrait) {
        this.idCompte = new SimpleIntegerProperty(idCompte);
        this.nomClient = new SimpleStringProperty(nomClient);
        this.prenomClient = new SimpleStringProperty(prenomClient) ;
        this.dateCompte = new SimpleStringProperty(dateCompte) ;
        this.solde = new SimpleIntegerProperty(solde) ;
        this.limiteRetrait = new SimpleIntegerProperty(limiteRetrait) ;
    }


    public Compte(int idCompte, int solde, int limiteRetrait, String dateCompte) {
        this.idCompte = new SimpleIntegerProperty(idCompte);
        this.solde = new SimpleIntegerProperty(solde) ;
        this.limiteRetrait = new SimpleIntegerProperty(limiteRetrait);
        this.dateCompte = new SimpleStringProperty(dateCompte) ;
    }


    //fonction pour récuperer id_bancaire, solde, limite_retrait, et la date. La fonction attend en parametre un idClient et retoure ObservableList
    public ObservableList<Compte> getcompteDataInformation(int idClient) {
        ObservableList<projet.model.Compte> compteDataList = FXCollections.observableArrayList();
        try {
            Connection connection = DataBase.initConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT compte.id_bancaire, compte.solde_bancaire, compte.limite_retrait, compte.date_compte  FROM compte, client WHERE compte.id_client = client.id_client AND client.id_client = '" + idClient + "'";
            System.out.println(query);
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                projet.model.Compte comptes = new projet.model.Compte(result.getInt("id_bancaire"), result.getInt("solde_bancaire"), result.getInt("limite_retrait"), result.getString("date_compte"));
                compteDataList.add(comptes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compteDataList;
    }

    // Fait la somme des  soldes  de tous les comptes du client selectionné
    public int getSoldeTotal(int idClient){
        int soldeTotal= 0;
        try {
            Connection connection = DataBase.initConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT SUM(compte.solde_bancaire) AS Solde_total FROM compte WHERE compte.id_client = "+ idClient +" ";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                soldeTotal = result.getInt("Solde_total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return soldeTotal;
    }

    // récupere le solde  d'un compte
    public int getSolde(int idCompte) {
        int solde = 0;
        try {
            Connection connection = DataBase.initConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT compte.solde_bancaire FROM compte WHERE id_bancaire = '" + idCompte + "'";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                solde = result.getInt("solde_bancaire");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return solde;
    }

    // récupere la limite de retrait
    public int getLimiteRetrait(int idCompte) {
        int limiteRetrait = 0;
        try {
            Connection connection = DataBase.initConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT compte.limite_retrait FROM compte WHERE id_bancaire = '" + idCompte + "'";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                limiteRetrait = result.getInt("limite_retrait");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return limiteRetrait;
    }




}

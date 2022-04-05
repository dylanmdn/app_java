package projet.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Operation {
    private IntegerProperty idOperation;
    private StringProperty libelleOperation;
    private IntegerProperty montantOperation;
    private StringProperty typeOperation;
    private StringProperty dateOperation;
    private StringProperty byOperationCol;
    private IntegerProperty numCompteOperationCol;

    public int getIdOperation() {
        return idOperation.get();
    }

    public IntegerProperty idOperationProperty() {
        return idOperation;
    }

    public void setIdOperation(int idOperation) {
        this.idOperation.set(idOperation);
    }

    public String getLibelleOperation() {
        return libelleOperation.get();
    }

    public StringProperty libelleOperationProperty() {
        return libelleOperation;
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation.set(libelleOperation);
    }

    public int getMontantOperation() {
        return montantOperation.get();
    }

    public IntegerProperty montantOperationProperty() {
        return montantOperation;
    }

    public void setMontantOperation(int montantOperation) {
        this.montantOperation.set(montantOperation);
    }

    public String getTypeOperation() {
        return typeOperation.get();
    }

    public StringProperty typeOperationProperty() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation.set(typeOperation);
    }

    public String getDateOperation() {
        return dateOperation.get();
    }

    public StringProperty dateOperationProperty() {
        return dateOperation;
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation.set(dateOperation);
    }

    public String getByOperationCol() {
        return byOperationCol.get();
    }

    public StringProperty byOperationColProperty() {
        return byOperationCol;
    }

    public void setByOperationCol(String byOperationCol) {
        this.byOperationCol.set(byOperationCol);
    }

    public int getNumCompteOperationCol() {
        return numCompteOperationCol.get();
    }

    public IntegerProperty numCompteOperationColProperty() {
        return numCompteOperationCol;
    }

    public void setNumCompteOperationCol(int numCompteOperationCol) {
        this.numCompteOperationCol.set(numCompteOperationCol);
    }

    public Operation(int idOperation, int montantOperation, String libelleOperation, String typeOperation, String dateOperation, String byOperationCol, int numCompteOperationCol) {
        this.idOperation = new SimpleIntegerProperty(idOperation);
        this.montantOperation = new SimpleIntegerProperty(montantOperation);
        this.libelleOperation = new SimpleStringProperty(libelleOperation);
        this.typeOperation = new SimpleStringProperty(typeOperation);
        this.dateOperation = new SimpleStringProperty(dateOperation);
        this.byOperationCol = new SimpleStringProperty(byOperationCol);
        this.numCompteOperationCol = new SimpleIntegerProperty(numCompteOperationCol);
    }

    public Operation() {
        this(0, 0, null, null, null, null, 0);
    }

    public ObservableList<Operation> getOperationData(int idClient) {
        ObservableList<projet.model.Operation> operationDataList = FXCollections.observableArrayList();
        try {
            Connection connection = DataBase.initConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT operation.id_operation, operation.montant , operation.libelle, operation.type_operation, operation.date_operation, client.nom_client, compte.id_bancaire FROM operation, client, compte WHERE operation.id_bancaire = compte.id_bancaire AND compte.id_client = client.id_client AND client.id_client = '" + idClient + "' ORDER BY operation.date_operation DESC ";
            System.out.println(query);
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                projet.model.Operation operations = new projet.model.Operation(result.getInt("id_operation"), result.getInt("montant"), result.getString("libelle"), result.getString("type_operation"), result.getString("date_operation"), result.getString("nom_client"), result.getInt("id_bancaire"));
                operationDataList.add(operations);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operationDataList;
    }


}

package projet.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import projet.Main;
import projet.model.Client;
import projet.model.Compte;
import projet.model.Operation;


public class OperationAddOperationDialogController {

    @FXML
    private TextArea libelleFieldOperation;

    @FXML
    private TextField montantFieldOperation;

    @FXML
    private ChoiceBox<String> typeChoiceOperation;

    @FXML
    private TextField numCompteFieldOperation;


    @FXML
    private Label montantOperationLabel;

    @FXML
    private Label typeOperationLabel;

    @FXML
    private Label numCompteOperationLabel;


    private Stage dialogStage;
    private Operation operation;
    private Compte compte;
    private boolean okClicked = false;
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    private void initialize() {
        typeChoiceOperation.getItems().addAll(typeOperation);

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isCloseClicked() {
        return okClicked;
    }


    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
        numCompteFieldOperation.setText(String.valueOf(compte.getIdCompte()));
    }



    @FXML
    void handleOperationCancel() {
        dialogStage.close();
    }

    private String[] typeOperation = {"Crédit", "Débit"};

    @FXML
    void handleOperationValide() {
        if (isInputValid()) {
            operation.setLibelleOperation(libelleFieldOperation.getText());
            operation.setMontantOperation(Integer.parseInt(montantFieldOperation.getText()));
            operation.setTypeOperation(typeChoiceOperation.getValue());
            operation.setNumCompteOperationCol(Integer.parseInt(numCompteFieldOperation.getText()));

            okClicked = true;
            dialogStage.close();


        }

    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (montantFieldOperation.getText() == null || montantFieldOperation.getText().length() == 0) {
            errorMessage += " Champ vide : " + montantOperationLabel.getText() + "\n";
        }
        if (typeChoiceOperation.getValue() == null) {
            errorMessage += " Champ vide : " + typeOperationLabel.getText() + "\n";
        }
        if (numCompteFieldOperation.getText() == null) {
            errorMessage += " Champ vide : " + numCompteOperationLabel.getText() + "\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }

    }



}

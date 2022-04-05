package projet.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projet.model.Compte;

import static projet.model.DataBase.executionUpdate;

public class CompteEditDialogController {
    @FXML
    private TextField limiteRetraitField;

    @FXML
    private TextField soldeField;

    @FXML
    private Label limiteRetraitCompteLabel;

    private Stage dialogStage;
    private Compte compte;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;

        soldeField.setText(String.valueOf(compte.getSolde()));
        if (compte.getLimiteRetrait() == 0){
            limiteRetraitField.setText("");
        }else {
            limiteRetraitField.setText(String.valueOf(compte.getLimiteRetrait()));
        }


    }

    public boolean isSaveClicked() {
        return okClicked;
    }

    @FXML
    public void handleSaveCompte() {
        if (isInputValid()) {
            compte.setSolde(Integer.parseInt(soldeField.getText()));
            compte.setLimiteRetrait(Integer.parseInt(limiteRetraitField.getText()));
            okClicked = true;
            try {
                String query = "UPDATE compte SET limite_retrait = '"+limiteRetraitField.getText() + "' WHERE id_bancaire = '" + compte.getIdCompte() + "'";
                executionUpdate(query);

            }catch (Exception e){
                e.printStackTrace();
            }
            dialogStage.close();
        }
    }

    @FXML
    public void handleCancelCompte() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (limiteRetraitField.getText() == null || limiteRetraitField.getText().length() == 0) {
            errorMessage += " Champ vide : " + limiteRetraitCompteLabel.getText() + "\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}



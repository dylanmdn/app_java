package projet.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projet.model.Client;

import static projet.model.DataBase.executionUpdate;

public class ClientEditDialogController {


    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField villeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telField;

    @FXML
    private Label nomLabel;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label adresseLabel;

    @FXML
    private Label villeLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label telLabel;

    private Stage dialogStage;
    private Client client;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setClient(Client client) {
        this.client = client;

        nomField.setText(client.getNomClient());
        prenomField.setText(client.getPrenomClient());
        adresseField.setText(client.getAdresseClient());
        villeField.setText(client.getVilleClient());
        emailField.setText(client.getEmailClient());
        telField.setText(client.getTelephoneClient());
    }

    public boolean isSaveClicked() {
        return okClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            client.setNomClient(nomField.getText());
            client.setPrenomClient(prenomField.getText());
            client.setAdresseClient(adresseField.getText());
            client.setVilleClient(villeField.getText());
            client.setEmailClient(emailField.getText());
            client.setTelephoneClient(telField.getText());

            okClicked = true;
            dialogStage.close();

            try {
                String query = "UPDATE client SET nom_client = '"+ nomField.getText() + " ',prenom_client = '"+prenomField.getText() + "',adresse_client = '"+ adresseField.getText() + "',ville_client='" + villeField.getText() +"',email_client = '"+ emailField.getText() + "',telephone_client = '"+ telField.getText() +"'  WHERE id_client = '" + client.getIdClient() + "'";
                executionUpdate(query);

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }



    private boolean isInputValid() {
        String errorMessage = "";

        if (nomField.getText() == null || nomField.getText().length() == 0) {
            errorMessage += " Champ vide : " + nomLabel.getText() + "\n";
        }
        if (prenomField.getText() == null || prenomField.getText().length() == 0) {
            errorMessage += " Champ vide : " + prenomLabel.getText() + "\n";
        }
        if (adresseField.getText() == null || adresseField.getText().length() == 0) {
            errorMessage += " Champ vide : " + adresseLabel.getText() + "\n";
        }
        if (villeField.getText() == null || villeField.getText().length() == 0) {
            errorMessage += " Champ vide : " + villeLabel.getText() + "\n";
        }
        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += " Champ vide : " + emailLabel.getText() + "\n";
        }
        if (telField.getText() == null || telField.getText().length() == 0) {
            errorMessage += " Champ vide : " + telLabel.getText() + "\n";
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

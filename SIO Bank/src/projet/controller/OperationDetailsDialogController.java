package projet.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import projet.model.Operation;

public class OperationDetailsDialogController {

    @FXML
    private Label byOperationLabel;

    @FXML
    private Label dateOperationLabel;

    @FXML
    private Label libelleOperationLabel;

    @FXML
    private Label montantOperationLabel;

    @FXML
    private Label numCompteOperationLabel;

    @FXML
    private Label numOperationLabel;

    @FXML
    private Label typeOperationLabel;

    private Stage dialogStage;
    private Operation operation;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isCloseClicked() {
        return okClicked;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;

        numOperationLabel.setText(String.valueOf(operation.getIdOperation()));
        montantOperationLabel.setText(String.valueOf(operation.getMontantOperation()));
        libelleOperationLabel.setText(operation.getLibelleOperation());
        typeOperationLabel.setText(operation.getTypeOperation());
        dateOperationLabel.setText(operation.getDateOperation());
        byOperationLabel.setText(operation.getByOperationCol());
        numCompteOperationLabel.setText(String.valueOf(operation.getNumCompteOperationCol()));
    }

    @FXML
    void handleClosebtn() {
        dialogStage.close();
    }
}

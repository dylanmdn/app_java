package projet.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import projet.Main;
import projet.model.Client;
import projet.model.Compte;
import projet.model.Operation;

import java.util.Optional;

import static projet.model.DataBase.executionQuery;
import static projet.model.DataBase.executionUpdate;

public class MainViewController {
    @FXML
    private TableView<Client> clientTable;
    @FXML
    private TableColumn<Client, String> nomCol;
    @FXML
    private TableColumn<Client, String> prenomCol;
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
    @FXML
    private TextField searchBar;

    @FXML
    private TableView<Compte> compteTable;

    @FXML
    private Label nomCompteLabel;

    @FXML
    private Label prenomCompteLabel;

    @FXML
    private TableColumn<Compte, Integer> numCompteCol;

    @FXML
    private TableColumn<Compte, Integer> soldeCompteCol;

    @FXML
    private TableColumn<Compte, Integer> limiteCompteCol;

    @FXML
    private TableColumn<Compte, String> dateCompteCol;

    @FXML
    private Text montantSoldeTotal;

    @FXML
    private TableView<Operation> operationTable;

    @FXML
    private TableColumn<Operation, Integer> montantOperationCol;
    @FXML
    private TableColumn<Operation, String> libelleOperationCol;
    @FXML
    private TableColumn<Operation, String> typeOperationCol;
    @FXML
    private TableColumn<Operation, String> dateOperationCol;

    private Main mainApp;


    ObservableList<Client> clientDataList = FXCollections.observableArrayList();
    ObservableList<Compte> compteDataList = FXCollections.observableArrayList();
    ObservableList<Operation> operationDataList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        initializeTableView();
        searchClient();
        initializeTableViewCompte();
        initializeTableViewOperation();
    }


    public void initializeTableView() {
        //On initialise le Tableau avec les données client de la base de données
        Client client = new Client();
        clientDataList = client.getClientData();
        clientTable.setItems(clientDataList);

        //Remplir les colonnes  Nom et prenom du Tableau
        nomCol.setCellValueFactory(cellData -> cellData.getValue().nomClientProperty());
        prenomCol.setCellValueFactory(cellData -> cellData.getValue().prenomClientProperty());

        showClientDetails(null);

        clientTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showClientDetails(newValue));
    }


    public void initializeTableViewCompte() {
        //On rempli la partie information le nom et le prenom, à partir la ligne selectionnée du tableviewClient
        clientTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCompteDetailsName(newValue));

        //Dans le cas ou personne n'a été sélectionnée
        nomCompteLabel.setText("");
        prenomCompteLabel.setText("");

        //On affecte chaque colonne avec les  données issues de la BDD
        numCompteCol.setCellValueFactory(cellData -> cellData.getValue().idCompteProperty().asObject());
        soldeCompteCol.setCellValueFactory(cellData -> cellData.getValue().soldeProperty().asObject());
        limiteCompteCol.setCellValueFactory(cellData -> cellData.getValue().limiteRetraitProperty().asObject());
        dateCompteCol.setCellValueFactory(cellData -> cellData.getValue().dateCompteProperty());
    }


    //Handle lorsque la tabpane est selectionné
    @FXML
    public void handleSelectedOperation() {
        //on récupere l'index de la ligne selectionnée de la tableView client
        int selectedIndex = clientTable.getSelectionModel().getSelectedIndex();
        Operation operation = new Operation();

        //on verifie que l'index est au moins égale à 0, c'est c'est le cas on récupere on récupere l'idClient dans un variable
        if (selectedIndex >= 0) {
            int idClient = clientTable.getSelectionModel().getSelectedItem().getIdClient();
            System.out.println(selectedIndex + " " + clientTable.getSelectionModel().getSelectedItem().getPrenomClient() + " " + clientTable.getSelectionModel().getSelectedItem().getIdClient());
            operationDataList = operation.getOperationData(idClient);
            operationTable.setItems(operationDataList);
        }
    }

    public void initializeTableViewOperation() {

        //On affecte chaque colonne avec les  données issues de la BDD
        montantOperationCol.setCellValueFactory(cellData -> cellData.getValue().montantOperationProperty().asObject());
        libelleOperationCol.setCellValueFactory(cellData -> cellData.getValue().libelleOperationProperty());
        typeOperationCol.setCellValueFactory(cellData -> cellData.getValue().typeOperationProperty());
        dateOperationCol.setCellValueFactory(cellData -> cellData.getValue().dateOperationProperty());

        //Prise en charge du double click pour afficher detailles des operations
        operationTable.setRowFactory(tv -> {
            TableRow<Operation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Operation selectedOperation = operationTable.getSelectionModel().getSelectedItem();
                    mainApp.showOperationDetailsDialog(selectedOperation);

                }
            });
            return row;
        });


    }

    public void searchClient() {
        //SearchBar fonctionnalité
        FilteredList<Client> filteredData = new FilteredList<>(clientDataList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Client -> {
                if (newValue.isEmpty()) {
                    return true;
                }
                String searchClient = newValue.toLowerCase();
                if (Client.getNomClient().toLowerCase().contains(searchClient)) {
                    return true;
                } else return Client.getPrenomClient().toLowerCase().contains(searchClient);
            });
        });
        SortedList<Client> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(clientDataList.sorted().comparatorProperty());
        clientTable.setItems(sortedData);
    }

    public void refreshTableView() {
        Client client = new Client();
        clientDataList = client.getClientData();
        searchClient();
    }


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }


    private void showClientDetails(Client client) {
        if (client != null) {

            nomLabel.setText(client.getNomClient());
            prenomLabel.setText(client.getPrenomClient());
            adresseLabel.setText(client.getAdresseClient());
            villeLabel.setText(client.getVilleClient());
            emailLabel.setText(client.getEmailClient());
            telLabel.setText(String.valueOf((client.getTelephoneClient())));
        } else {
            nomLabel.setText("");
            prenomLabel.setText("");
            adresseLabel.setText("");
            villeLabel.setText("");
            emailLabel.setText("");
            telLabel.setText("");
        }
    }

    private void showCompteDetailsName(Client client) {
        if (client != null) {
            nomCompteLabel.setText(client.getNomClient());
            prenomCompteLabel.setText(client.getPrenomClient());
        }
    }

    @FXML
    private void handleDeleteClient() {
        int selectedIndex = clientTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            alertConfirmation(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setHeaderText(null);
            alert.setContentText("Aucun élément sélectionné");
            alert.showAndWait();
        }
    }

    private void alertConfirmation(int selectedIndex) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous supprimer cet élément");
        String nomClient = clientTable.getSelectionModel().getSelectedItem().getNomClient();
        String prenomClient = clientTable.getSelectionModel().getSelectedItem().getPrenomClient();

        alert.setContentText(nomClient + " " + prenomClient);

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            String query = "DELETE FROM client WHERE id_client = " + clientTable.getSelectionModel().getSelectedItem().getIdClient();
            executionUpdate(query);
            refreshTableView();
        } else if (option.get() == ButtonType.CANCEL) {

        }
    }


    @FXML
    private void handleEditClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();

        if (selectedClient != null) {
            boolean okClicked = mainApp.showClientEditDialog(selectedClient);
            if (okClicked) {
                showClientDetails(selectedClient);
            }

        } else {
            // alert aucun élément selectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setHeaderText(null);
            alert.setContentText("Aucun élément sélectionné");
            alert.showAndWait();

        }

    }



    @FXML
    private void handleNewClient() {
        Client tempClient = new Client();
        boolean okClicked = mainApp.showClientEditDialog(tempClient);
        if (okClicked) {
            String query = "INSERT INTO  client (nom_client ,  prenom_client ,  adresse_client ,  ville_client ,  email_client ,  telephone_client  ) VALUES ('" + tempClient.getNomClient() + "','" + tempClient.getPrenomClient() + "','" + tempClient.getAdresseClient() + "','" + tempClient.getVilleClient() + "','" + tempClient.getEmailClient() + "','" + tempClient.getTelephoneClient() + "')";
            executionQuery(query);
            refreshTableView();
        }
    }

    //Lorsque selectionne la tabpane Compte
    @FXML
    public void handleSelectedCompte() {
        //on récupere l'index de la ligne selectionnée de la tableView client
        int selectedIndex = clientTable.getSelectionModel().getSelectedIndex();
        Compte compte = new Compte();

        //on verifie que l'index est au moins égale à 0, c'est c'est le cas on récupere on récupere l'idClient dans un variable
        if (selectedIndex >= 0) {
            int idClient = clientTable.getSelectionModel().getSelectedItem().getIdClient();
            System.out.println(selectedIndex + " " + clientTable.getSelectionModel().getSelectedItem().getPrenomClient() + " " + clientTable.getSelectionModel().getSelectedItem().getIdClient());
            compteDataList = compte.getcompteDataInformation(idClient);
            compteTable.setItems(compteDataList);

            //afficher le solde de tous les comptes
            montantSoldeTotal.setText(compte.getSoldeTotal(idClient) + " €");

            //Double click sur une ligne du tableau pour avoir plus de details
            compteTable.setRowFactory(tv -> {
                TableRow<Compte> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Compte selectedCompte = compteTable.getSelectionModel().getSelectedItem();
                        mainApp.showCompteEditDialog(selectedCompte);

                    }
                });
                return row;
            });


        }
    }




    @FXML
    public void handleCreateCompte() {
        Compte tempCompte = new Compte();
        boolean okClicked = mainApp.showCompteEditDialog(tempCompte);
        if (okClicked) {
            int selectedIndex = clientTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                int idClient = clientTable.getSelectionModel().getSelectedItem().getIdClient();
                System.out.println(selectedIndex + " " + clientTable.getSelectionModel().getSelectedItem().getPrenomClient() + " " + clientTable.getSelectionModel().getSelectedItem().getIdClient());
                String query = "INSERT INTO compte (solde_bancaire, date_compte, id_client, limite_retrait)  VALUES ('0',CURRENT_DATE,'" + idClient + "','" + tempCompte.getLimiteRetrait() + "')";
                executionQuery(query);
                handleSelectedCompte();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setHeaderText(null);
                alert.setContentText("Aucun client sélectionné");
                alert.showAndWait();
            }
        }
    }



    @FXML
    public void handleDeleteCompte() {
        int selectedIndex = compteTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            alertConfirmationDeleteCompte(selectedIndex);

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setHeaderText(null);
            alert.setContentText("Aucun élément sélectionné");
            alert.showAndWait();
        }

    }

    private void alertConfirmationDeleteCompte(int selectedIndex) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous supprimer ce compte bancaire ?");
        int numCompte = compteTable.getSelectionModel().getSelectedItem().getIdCompte();
        alert.setContentText("N° COMPTE : " + numCompte);

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            String query = "DELETE FROM compte WHERE id_bancaire = " + compteDataList.get(selectedIndex).getIdCompte();
            executionUpdate(query);
            handleSelectedCompte();
        } else if (option.get() == ButtonType.CANCEL) {
        }
    }


    @FXML
    public void handleAddOperation() {

        int selectIndexCompte = compteTable.getSelectionModel().getSelectedIndex();

        if (selectIndexCompte >= 0){
            Compte selectedCompte = compteTable.getSelectionModel().getSelectedItem();
            Operation tempOperation = new Operation();
            Compte compte = new Compte();
            boolean okClicked = mainApp.showAddOperationDialog(tempOperation, selectedCompte  );
            if (okClicked) {
                int selectedIndex = clientTable.getSelectionModel().getSelectedIndex();
                int idCompte = tempOperation.getNumCompteOperationCol();
                int solde = compte.getSolde(idCompte);
                int limiteRetrait = compte.getLimiteRetrait(idCompte);
                int montant = tempOperation.getMontantOperation();
                if (montant <= 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setHeaderText(null);
                    alert.setContentText("Le montant saissi est incorrect ");
                    alert.showAndWait();

                } else if (selectedIndex >= 0) {

                    String choiceAction = tempOperation.typeOperationProperty().getValue();
                    System.out.println(choiceAction);
                    if (choiceAction.equals("Crédit")) {
                        String query = "INSERT INTO operation (libelle, montant, type_operation, date_operation, id_bancaire)  VALUES ('" + tempOperation.getLibelleOperation() + "','" + tempOperation.getMontantOperation() + "', '" + tempOperation.getTypeOperation() + "', CURRENT_DATE, '" + tempOperation.getNumCompteOperationCol() + "')";
                        System.out.println(query);
                        executionQuery(query);
                        solde = (solde + montant);
                        String queryUpdate = "UPDATE compte SET solde_bancaire = '" + solde + "' WHERE compte.id_bancaire = '" + idCompte + "'";
                        System.out.println(solde);
                        executionUpdate(queryUpdate);

                        handleSelectedCompte();
                        handleSelectedOperation();


                    } else if (choiceAction.equals("Débit") & (montant <= limiteRetrait)) {
                        String query = "INSERT INTO operation (libelle, montant, type_operation, date_operation, id_bancaire)  VALUES ('" + tempOperation.getLibelleOperation() + "','" + tempOperation.getMontantOperation() + "', '" + tempOperation.getTypeOperation() + "', CURRENT_DATE, '" + tempOperation.getNumCompteOperationCol() + "')";
                        executionQuery(query);
                        solde -= montant;
                        String queryUpdate = "UPDATE compte SET solde_bancaire = '" + solde + "' WHERE compte.id_bancaire = '" + idCompte + "'";
                        executionUpdate(queryUpdate);

                        handleSelectedCompte();
                        handleSelectedOperation();
                        System.out.println(limiteRetrait);

                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.initOwner(mainApp.getPrimaryStage());
                        alert.setHeaderText(null);
                        alert.setContentText("Vous ne pouvez pas être débité plus que le montant autorisé !");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setHeaderText(null);
                    alert.setContentText("Aucun client sélectionné");
                    alert.showAndWait();
                }

                if (solde < 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setHeaderText(null);
                    alert.setContentText("Solde négatif de " + solde);
                    alert.showAndWait();
                }
            }

        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setHeaderText(null);
            alert.setContentText("Aucun compte sélectionné");
            alert.showAndWait();
        }

    }

}

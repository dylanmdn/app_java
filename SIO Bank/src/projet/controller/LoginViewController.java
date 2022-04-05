package projet.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import projet.Main;
import projet.model.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginViewController {


    @FXML
    private TextField nameLoginAdmin;

    @FXML
    private TextField PasswordLoginAdmin;

    @FXML
    private Label labelMsgError;

    Main mainApp;


    @FXML
    private void initialize() {


    }

    Connection connection = DataBase.initConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    @FXML
    void handleLogin() {

        String nomAdmin = nameLoginAdmin.getText();
        String passwordAdmin = PasswordLoginAdmin.getText();
        String query = "SELECT * FROM admin WHERE admin.nom_admin = ? AND admin.password = ?";

        try{

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,nomAdmin);
            preparedStatement.setString(2,passwordAdmin);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()){
                labelMsgError.setText("Nom ou Mot de passe incorrecte");
                boolean isConnected = false;
                System.out.println(isConnected);
            }else {
                boolean isConnected = true;
                mainApp.mainView();


            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }


}





package projet.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataBase {
    private static Connection connection = null;
    private String url = "jdbc:mysql://localhost/sio_bank ";
    private String userName = "root";
    private String password = "";

    public DataBase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("DRIVER OK");
            connection = DriverManager.getConnection(url,userName,password);
            System.out.println("CONNEXION OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection initConnection(){
        if (connection == null){
            new DataBase();
        }
        return connection;
    }

    public static void executionQuery(String query){
        Connection connection = DataBase.initConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executionUpdate(String query){
        Connection connection = DataBase.initConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



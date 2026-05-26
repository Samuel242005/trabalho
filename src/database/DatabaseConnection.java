package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:h2:./database/pokemon";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection conectar(){
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e){
            System.out.println("erro ao conectar no banco.");
            throw new RuntimeException(e);
        }
    }
}

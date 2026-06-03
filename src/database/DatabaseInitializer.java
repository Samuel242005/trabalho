package database;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void criarTabela(){

        //tabela pokemon
        String sqlPokemon = "CREATE TABLE IF NOT EXISTS pokemon ("+
                "id INT PRIMARY KEY, " +
                "nome VARCHAR(100), " +
                "hp INT, " +
                "ataque INT, " +
                "defesa INT, " +
                "tipo VARCHAR(50), " +
                "level INT, " +
                "xp INT" +
                ")";

        //tabela moves
        String sqlMove = "CREATE TABLE IF NOT EXISTS moves ("+
                "id INT PRIMARY KEY, " +
                "nome VARCHAR(100), " +
                "ppAtual INT, " +
                "ppMax INT, " +
                "tipo varchar(50), " +
                "precisao DOUBLE " +
                ")";

        String sqlSave = "CREATE TABLE IF NOT EXISTS save_game ("+
                "id INT PRIMARY KEY, " +
                "nome VARCHAR(100), " +
                "hp INT, " +
                "ataque INT, " +
                "defesa INT, " +
                "tipo VARCHAR(50), " +
                "level INT, " +
                "xp INT"+
                ")";


        try {
            Connection conexao = DatabaseConnection.conectar();
            Statement stmt = conexao.createStatement();
            stmt.execute(sqlPokemon);
            stmt.execute(sqlMove);
            stmt.execute(sqlSave);
            System.out.println("Tabelas criadas");
            conexao.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

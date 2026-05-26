import database.DatabaseConnection;
import java.sql.Connection;
import database.DatabaseInitializer;
import service.PokeApiService;

public class Main {
    public static void main(String[] args) {
        //criar a conexão com o banco de dados
        try{
            Connection conexao = DatabaseConnection.conectar();
            System.out.println("Banco conectado com sucesso");

            conexao.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        //criar as tabelas
        DatabaseInitializer.criarTabela();

        PokeApiService.buscarPokemon();
    }
}
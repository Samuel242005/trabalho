import database.DatabaseConnection;
import java.sql.Connection;
import database.DatabaseInitializer;
import service.PokeApiService;
import batalha.Batalha;
import model.Pokemon;

public class Main {
    public static void main(String[] args) {
        //criar a connexion com o Banco de dados
        try{
            Connection conexao = DatabaseConnection.conectar();
            System.out.println("Banco conectado com sucesso");

            conexao.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        //criar as tablas
        DatabaseInitializer.criarTabela();

        Pokemon pikachu = new Pokemon(
                25,
                "Pikachu",
                35,
                55,
                40,
                "eletric",
                5,
                0
        );

        Pokemon charmander = new Pokemon(
                4,
                "Charmander",
                39,
                52,
                43,
                "fire",
                5,
                0
        );

        Batalha.iniciar(pikachu, charmander);

        PokeApiService.buscarPokemon();
    }
}
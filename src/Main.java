import database.DatabaseConnection;
import java.sql.Connection;
import java.util.Scanner;

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

        Pokemon jogador;

        Pokemon bulbasaur =
                new Pokemon(
                        1,
                        "Bulbasaur",
                        45,
                        49,
                        49,
                        "grass",
                        5,
                        0
                );

        Pokemon charmander =
                new Pokemon(
                        4,
                        "Charmander",
                        39,
                        52,
                        43,
                        "fire",
                        5,
                        0
                );

        Pokemon squirtle =
                new Pokemon(
                        7,
                        "Squirtle",
                        44,
                        48,
                        65,
                        "water",
                        5,
                        0
                );

        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha seu Pokémon:");

        System.out.println("1 - " + bulbasaur.getNome());
        System.out.println("2 - " + charmander.getNome());
        System.out.println("3 - " + squirtle.getNome());

        int escolha = scanner.nextInt();

        if (escolha == 1) {

            jogador = bulbasaur;

        }
        else if (escolha == 2) {

            jogador = charmander;

        }
        else {

            jogador = squirtle;

        }

        System.out.println(
                "Você escolheu "
                        + jogador.getNome()
        );


        Batalha.iniciar(jogador, charmander);

        PokeApiService.buscarPokemon();
    }
}
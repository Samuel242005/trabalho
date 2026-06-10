import database.DatabaseConnection;
import java.sql.Connection;
import java.util.Scanner;
import java.util.Random;
import database.DatabaseInitializer;
import service.LoadService;
import service.PokeApiService;
import batalha.Batalha;
import model.Pokemon;
import service.SaveService;
import model.Move;

public class Main {
    public static void main(String[] args) {
        //criar a connexion com o Banco de dados
        try{
            Connection conexao = DatabaseConnection.conectar();
            //System.out.println("Banco conectado com sucesso");

            conexao.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        //criar as tablas
        DatabaseInitializer.criarTabela();

        System.out.println("1 - Novo Jogo");
        System.out.println("2 - Continuar Jornada");

        int[] basicos = {
                1, 4, 7, 10, 13, 16, 19, 21, 23, 25,
                27, 29, 32, 35, 37, 39, 41, 43, 46,
                48, 50, 52, 54, 56, 58, 60, 63, 66,
                69, 72, 74, 77, 79, 81, 84, 86, 88,
                90, 92, 96, 98, 100, 102, 104, 108,
                109, 111, 114, 116, 118, 120, 123,
                129, 133, 138, 140, 147
        };

        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int opcao = scanner.nextInt();

        if (opcao == 2) {

            Pokemon jogador = LoadService.carregar();

            if (jogador != null) {

                System.out.println("Bem-vindo de volta, treinador");

                Pokemon inimigo = PokeApiService.buscarPokemon(basicos[random.nextInt(basicos.length)]);

                Batalha.iniciar(jogador, inimigo);

                SaveService.salvar(jogador);

                return;
            }

            System.out.println(
                    "Nenhum save encontrado!"
            );
        }

        int id1 = basicos[random.nextInt(basicos.length)];

        int id2;
        do {
            id2 = basicos[random.nextInt(basicos.length)];
        } while (id2 == id1);

        int id3;
        do {
            id3 = basicos[random.nextInt(basicos.length)];
        } while (id3 == id1 || id3 == id2);

        Pokemon p1 = PokeApiService.buscarPokemon(id1);
        Pokemon p2 = PokeApiService.buscarPokemon(id2);
        Pokemon p3 = PokeApiService.buscarPokemon(id3);

        System.out.println("Escolha seu Pokémon:\n");

        System.out.println("1 - " + p1.getNome());
        System  .out.println("HP: " + p1.getHp());
        System.out.println("ATK: " + p1.getAtaque());
        System.out.println("DEF: " + p1.getDefesa());
        System.out.println("TIPO: " + p1.getTipo());
        System.out.println();

        System.out.println("2 - " + p2.getNome());
        System.out.println("HP: " + p2.getHp());
        System.out.println("ATK: " + p2.getAtaque());
        System.out.println("DEF: " + p2.getDefesa());
        System.out.println("TIPO: " + p2.getTipo());
        System.out.println();

        System.out.println("3 - " + p3.getNome());
        System.out.println("HP: " + p3.getHp());
        System.out.println("ATK: " + p3.getAtaque());
        System.out.println("DEF: " + p3.getDefesa());
        System.out.println("TIPO: " + p3.getTipo());
        System.out.println();


        int escolha = scanner.nextInt();

        Pokemon jogador;

        if (escolha == 1) {
            jogador = p1;
        } else if (escolha == 2) {
            jogador = p2;
        } else {
            jogador = p3;
        }

        System.out.println("\nVocê escolheu " + jogador.getNome());
        SaveService.salvar(jogador);

        System.out.println("\nMovimentos de " + jogador.getNome());

        for (Move move : jogador.getMovimentos()) {
            System.out.println(move.getNome()+ " PP: " + move.getPpAtual()+ "/"+ move.getPpMax());
        }

        Pokemon inimigo = PokeApiService.buscarPokemon(basicos[random.nextInt(basicos.length)]);

        Batalha.iniciar(jogador, inimigo);

    }
}
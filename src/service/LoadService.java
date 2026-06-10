package service;

import database.DatabaseConnection;
import model.Pokemon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoadService {

    public static Pokemon carregar() {

        try {
            Connection conexao = DatabaseConnection.conectar();

            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM save_game LIMIT 1");

            if (rs.next()) {

                Pokemon pokemon = PokeApiService.buscarPokemon(rs.getInt("id"));

                pokemon.setHp(rs.getInt("hp"));
                pokemon.setAtaque(rs.getInt("ataque"));
                pokemon.setDefesa(rs.getInt("defesa"));
                pokemon.setLevel(rs.getInt("level"));
                pokemon.setXp(rs.getInt("xp"));

                MoveLearningService.verificarNovosGolpes(pokemon);

                System.out.println("\n==============================");
                System.out.println("      JORNADA CARREGADA");
                System.out.println("==============================");
                System.out.println("Pokémon: " + pokemon.getNome());
                System.out.println("Nível: " + pokemon.getLevel());
                System.out.println("HP: " + pokemon.getHp() + "/" + pokemon.getHpMax());
                System.out.println("XP: " + pokemon.getXp());
                System.out.println("==============================\n");

                conexao.close();

                return pokemon;
            }

            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
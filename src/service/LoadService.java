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

            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM save_game LIMIT 1"
            );

            if (rs.next()) {

                Pokemon pokemon = PokeApiService.buscarPokemon(
                        rs.getInt("id")
                );

                pokemon.setHp(rs.getInt("hp"));
                pokemon.setAtaque(rs.getInt("ataque"));
                pokemon.setDefesa(rs.getInt("defesa"));
                pokemon.setLevel(rs.getInt("level"));
                pokemon.setXp(rs.getInt("xp"));

                System.out.println("Jogo carregado!");

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
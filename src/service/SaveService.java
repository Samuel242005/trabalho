package service;

import database.DatabaseConnection;
import model.Pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class SaveService {

    public static void salvar(Pokemon pokemon) {

        try {

            Connection conexao = DatabaseConnection.conectar();

            Statement deleteStmt = conexao.createStatement();
            deleteStmt.execute("DELETE FROM save_game");

            String sql = "MERGE INTO save_game VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setInt(1, pokemon.getId());
            stmt.setString(2, pokemon.getNome());
            stmt.setInt(3, pokemon.getHp());
            stmt.setInt(4, pokemon.getAtaque());
            stmt.setInt(5, pokemon.getDefesa());
            stmt.setString(6, pokemon.getTipo());
            stmt.setInt(7, pokemon.getLevel());
            stmt.setInt(8, pokemon.getXp());

            stmt.execute();

            System.out.println("Jogo salvo com sucesso!");

            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
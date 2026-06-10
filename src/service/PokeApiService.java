package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import database.DatabaseConnection;
import model.Pokemon;
import model.Move;

public class PokeApiService {

    public static Pokemon buscarPokemon(int id) {
        return buscarPokemonPorUrl("https://pokeapi.co/api/v2/pokemon/" + id);
    }

    public static Pokemon buscarPokemon(String nomePokemon) {
        return buscarPokemonPorUrl("https://pokeapi.co/api/v2/pokemon/" + nomePokemon);
    }

    private static Pokemon buscarPokemonPorUrl(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            int id = json.get("id").getAsInt();
            String nome = json.get("name").getAsString();

            JsonArray stats = json.getAsJsonArray("stats");

            int hp = stats.get(0).getAsJsonObject().get("base_stat").getAsInt();
            int ataque = stats.get(1).getAsJsonObject().get("base_stat").getAsInt();
            int defesa = stats.get(2).getAsJsonObject().get("base_stat").getAsInt();

            JsonArray types = json.getAsJsonArray("types");

            String tipo = types.get(0)
                    .getAsJsonObject()
                    .getAsJsonObject("type")
                    .get("name")
                    .getAsString();

            Pokemon pokemon = new Pokemon(
                    id,
                    nome,
                    hp,
                    ataque,
                    defesa,
                    tipo,
                    5,
                    0
            );

            carregarMovimentos(client, json, pokemon);

            salvarPokemonNoBanco(pokemon);

            return pokemon;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void carregarMovimentos(
            HttpClient client,
            JsonObject json,
            Pokemon pokemon
    ) throws Exception {

        JsonArray moves = json.getAsJsonArray("moves");

        int quantidadeMovimentos = Math.min(4, moves.size());

        for (int i = 0; i < quantidadeMovimentos; i++) {

            JsonObject moveInfo = moves.get(i).getAsJsonObject();

            String moveUrl = moveInfo
                    .getAsJsonObject("move")
                    .get("url")
                    .getAsString();

            HttpRequest moveRequest = HttpRequest.newBuilder()
                    .uri(URI.create(moveUrl))
                    .build();

            HttpResponse<String> moveResponse = client.send(
                    moveRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            JsonObject moveJson = JsonParser
                    .parseString(moveResponse.body())
                    .getAsJsonObject();

            String nomeMove = moveJson.get("name").getAsString();

            int pp = moveJson.get("pp").isJsonNull()
                    ? 10
                    : moveJson.get("pp").getAsInt();

            int poder = moveJson.get("power").isJsonNull()
                    ? 40
                    : moveJson.get("power").getAsInt();

            double precisao = moveJson.get("accuracy").isJsonNull()
                    ? 100
                    : moveJson.get("accuracy").getAsDouble();

            String tipoMove = moveJson
                    .getAsJsonObject("type")
                    .get("name")
                    .getAsString();

            Move movimento = new Move(
                    nomeMove,
                    pp,
                    pp,
                    tipoMove,
                    precisao,
                    poder
            );

            pokemon.adicionarMovimento(movimento);
        }
    }

    private static void salvarPokemonNoBanco(Pokemon pokemon) {

        try {
            Connection conexao = DatabaseConnection.conectar();

            String sql =
                    "MERGE INTO pokemon VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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

            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
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

    public static Pokemon buscarPokemon(int id  ) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(
                    "https://pokeapi.co/api/v2/pokemon/" + id)).build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            String nome  = json.get("name").getAsString();
            //System.out.println(nome);

            JsonArray stats = json.getAsJsonArray("stats");

            //buscar o hp
            JsonObject hpObject = stats.get(0).getAsJsonObject();
            int hp = hpObject.get("base_stat").getAsInt();

            //buscar o ataque
            int ataque =  stats.get(1)
                    .getAsJsonObject()
                    .get("base_stat")
                    .getAsInt();

            //buscar a defesa
            int defesa =  stats.get(2)
                    .getAsJsonObject()
                    .get("base_stat")
                    .getAsInt();

            //buscar o tipo
            JsonArray types = json.getAsJsonArray("types");
            String tipo =  types.get(0)
                    .getAsJsonObject()
                    .get("type")
                    .getAsJsonObject()
                    .get("name")
                    .getAsString();

            /*
            System.out.println(hp);
            System.out.println(ataque);
            System.out.println(defesa);
            System.out.println(tipo);
            */
            Pokemon pokemon = new Pokemon(id, nome, hp, ataque, defesa, tipo, 5,0);

            JsonArray moves = json.getAsJsonArray("moves");

            int quantidadeMovimentos = Math.min(4, moves.size());

            for (int i = 0; i < quantidadeMovimentos; i++) {

                JsonObject moveInfo = moves.get(i).getAsJsonObject();

                String moveUrl = moveInfo.getAsJsonObject("move").get("url").getAsString();

                HttpRequest moveRequest = HttpRequest.newBuilder().uri(URI.create(moveUrl)).build();

                HttpResponse<String> moveResponse = client.send(moveRequest, HttpResponse.BodyHandlers.ofString());

                JsonObject moveJson = JsonParser.parseString(moveResponse.body()).getAsJsonObject();

                String nomeMove = moveJson.get("name").getAsString();

                int pp = moveJson.get("pp").isJsonNull() ? 10 : moveJson.get("pp").getAsInt();

                int poder = moveJson.get("power").isJsonNull() ? 40 : moveJson.get("power").getAsInt();

                double precisao = moveJson.get("accuracy").isJsonNull() ? 100 : moveJson.get("accuracy").getAsDouble();

                String tipoMove = moveJson.getAsJsonObject("type").get("name").getAsString();

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

            System.out.println(pokemon.getNome());

            Connection conexao = DatabaseConnection.conectar();

            String sql = "MERGE INTO  pokemon VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, nome);
            stmt.setInt(3, hp);
            stmt.setInt(4, ataque);
            stmt.setInt(5, defesa);
            stmt.setString(6, tipo);
            stmt.setInt(7, 0);
            stmt.setInt(8, 0);
            stmt.execute();

            return pokemon;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

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

public class PokeApiService {

    public static void buscarPokemon() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(
                    "https://pokeapi.co/api/v2/pokemon/25")).build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            String nome  = json.get("name").getAsString();
            System.out.println(nome);

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

            System.out.println(hp);
            System.out.println(ataque);
            System.out.println(defesa);
            System.out.println(tipo);

            Connection conexao = DatabaseConnection.conectar();

            String sql = "INSERT INTO  pokemon VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, 4);
            stmt.setString(2, nome);
            stmt.setInt(3, hp);
            stmt.setInt(4, ataque);
            stmt.setInt(5, defesa);
            stmt.setString(6, tipo);
            stmt.setInt(7, 0);
            stmt.setInt(8, 0);
            stmt.execute();
            System.out.println("Pokemon salvo!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

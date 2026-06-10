package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Move;
import model.Pokemon;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MoveLearningService {

    public static void verificarNovosGolpes(Pokemon pokemon) {

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://pokeapi.co/api/v2/pokemon/" + pokemon.getNome()))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            JsonObject json = JsonParser
                    .parseString(response.body())
                    .getAsJsonObject();

            JsonArray moves = json.getAsJsonArray("moves");

            for (int i = 0; i < moves.size(); i++) {

                JsonObject moveInfo = moves.get(i).getAsJsonObject();

                String nomeMove = moveInfo
                        .getAsJsonObject("move")
                        .get("name")
                        .getAsString();

                String moveUrl = moveInfo
                        .getAsJsonObject("move")
                        .get("url")
                        .getAsString();

                JsonArray details = moveInfo.getAsJsonArray("version_group_details");

                for (int j = 0; j < details.size(); j++) {

                    JsonObject detail = details.get(j).getAsJsonObject();

                    String metodo = detail
                            .getAsJsonObject("move_learn_method")
                            .get("name")
                            .getAsString();

                    int nivelAprendido = detail
                            .get("level_learned_at")
                            .getAsInt();

                    if (metodo.equals("level-up")
                            && nivelAprendido == pokemon.getLevel()
                            && !possuiMovimento(pokemon, nomeMove)) {

                        Move novoMove = buscarDadosDoMovimento(client, moveUrl);

                        pokemon.adicionarMovimento(novoMove);

                        System.out.println(
                                pokemon.getNome()
                                        + " aprendeu "
                                        + novoMove.getNome()
                                        + "!"
                        );

                        return;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Move buscarDadosDoMovimento(
            HttpClient client,
            String moveUrl
    ) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(moveUrl))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        JsonObject moveJson = JsonParser
                .parseString(response.body())
                .getAsJsonObject();

        String nome = moveJson.get("name").getAsString();

        int pp = moveJson.get("pp").isJsonNull()
                ? 10
                : moveJson.get("pp").getAsInt();

        int poder = moveJson.get("power").isJsonNull()
                ? 40
                : moveJson.get("power").getAsInt();

        double precisao = moveJson.get("accuracy").isJsonNull()
                ? 100
                : moveJson.get("accuracy").getAsDouble();

        String tipo = moveJson
                .getAsJsonObject("type")
                .get("name")
                .getAsString();

        return new Move(
                nome,
                pp,
                pp,
                tipo,
                precisao,
                poder
        );
    }

    private static boolean possuiMovimento(
            Pokemon pokemon,
            String nomeMovimento
    ) {

        for (Move move : pokemon.getMovimentos()) {
            if (move.getNome().equalsIgnoreCase(nomeMovimento)) {
                return true;
            }
        }

        return false;
    }
}
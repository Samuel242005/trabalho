package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Pokemon;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EvolutionService {

    public static void verificarEvolucao(Pokemon pokemon) {

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest speciesRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://pokeapi.co/api/v2/pokemon-species/" + pokemon.getNome()))
                    .build();

            HttpResponse<String> speciesResponse = client.send(
                    speciesRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            JsonObject speciesJson = JsonParser.parseString(speciesResponse.body()).getAsJsonObject();

            String evolutionUrl = speciesJson
                    .getAsJsonObject("evolution_chain")
                    .get("url")
                    .getAsString();

            HttpRequest evolutionRequest = HttpRequest.newBuilder()
                    .uri(URI.create(evolutionUrl))
                    .build();

            HttpResponse<String> evolutionResponse = client.send(
                    evolutionRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            JsonObject evolutionJson = JsonParser.parseString(evolutionResponse.body()).getAsJsonObject();

            JsonObject chain = evolutionJson.getAsJsonObject("chain");

            verificarNaChain(pokemon, chain);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void verificarNaChain(Pokemon pokemon, JsonObject atual) {

        String nomeAtual = atual
                .getAsJsonObject("species")
                .get("name")
                .getAsString();

        JsonArray evolucoes = atual.getAsJsonArray("evolves_to");

        if (nomeAtual.equalsIgnoreCase(pokemon.getNome()) && !evolucoes.isEmpty()) {

            JsonObject proximaEvolucao = evolucoes.get(0).getAsJsonObject();

            JsonArray detalhes = proximaEvolucao.getAsJsonArray("evolution_details");

            if (!detalhes.isEmpty()) {

                JsonObject detalhe = detalhes.get(0).getAsJsonObject();

                if (!detalhe.get("min_level").isJsonNull()) {

                    int nivelNecessario = detalhe.get("min_level").getAsInt();

                    if (pokemon.getLevel() >= nivelNecessario) {

                        String novoNome = proximaEvolucao
                                .getAsJsonObject("species")
                                .get("name")
                                .getAsString();

                        evoluir(pokemon, novoNome);
                    }
                }
            }

            return;
        }

        for (int i = 0; i < evolucoes.size(); i++) {
            verificarNaChain(pokemon, evolucoes.get(i).getAsJsonObject());
        }
    }

    private static void evoluir(Pokemon pokemon, String novoNome) {

        System.out.println(pokemon.getNome() + " evoluiu para " + novoNome + "!");

        Pokemon evoluido = PokeApiService.buscarPokemon(novoNome);

        pokemon.setId(evoluido.getId());
        pokemon.setNome(evoluido.getNome());
        pokemon.setTipo(evoluido.getTipo());

        pokemon.setHpMax(pokemon.getHpMax() + 10);
        pokemon.setHp(pokemon.getHpMax());
        pokemon.setAtaque(pokemon.getAtaque() + 5);
        pokemon.setDefesa(pokemon.getDefesa() + 5);
    }
}
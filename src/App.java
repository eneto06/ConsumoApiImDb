import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;

public class App {
    public static void main(String[] args) throws Exception {

        // Fazer uma conexão HTTP (protocolo de acesso a web) e buscar os top 250 filmes 

        //Variável para alocação 
        /* String url = "https://imdb-api.com/en/API/Top250Movies/k_9cne28er"; */
        String url = "https://imdb-api.com/en/API/MostPopularMovies/k_9cne28er";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        System.out.println(body);
        
        // Extrair os dados que interessam (título, poster, classificação)

        
        Gson gson = new Gson();
        @SuppressWarnings("unchecked") // Informa ao compilador o risco da conversão
        //Utilizamos Map<String, Object> para desserializar o JSON de resposta para um mapa genérico.
        Map<String, Object> data = gson.fromJson(body, Map.class);
        @SuppressWarnings("unchecked")// Informa ao compilador o risco da conversão
        //Extraímos a lista de filmes do mapa utilizando data.get("items") e fazemos o casting para List<Map<String, String>>.
        List<Map<String, String>> listaDeFilmes = (List<Map<String, String>>) data.get("items");

        // Exibir e manipular os dados

        try (Scanner scanner = new Scanner(System.in)) {
            for (Map<String,String> filme : listaDeFilmes) {
                System.out.println("\u001b[4m" + filme.get("title") + "\u001b[0m");
                System.out.println("\u001b[36m" + filme.get("image") + "\u001b[37m");
                System.out.println("\u001b[33m" + filme.get("imDbRating") + "\u001b[37m");
                System.out.println();

                System.out.print("Digite sua avaliação para este filme: ");
                String avaliacao = scanner.nextLine();

                filme.put("avaliacao", avaliacao);

                System.out.println();
            }
        }
        System.out.println("Avaliações dos filmes:");
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println("Filme: " + "\u001b[4m" + filme.get("title") + "\u001b[0m");
            System.out.println("Imagem: " + "\u001b[36m" + filme.get("image") + "\u001b[37m");
            System.out.println("Classificação IMDb: " + "\u001b[33m" + filme.get("imDbRating") + "\u001b[37m");
            System.out.println("Avaliação: " + filme.get("avaliacao"));
            System.out.println();
        }


    }
}

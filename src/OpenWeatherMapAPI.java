import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class OpenWeatherMapAPI {
    public static void main (String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite uma cidade:");
        String cidade = scanner.nextLine();

        String url = "http://api.openweathermap.org/data/2.5/weather?q="+ cidade +"&appid=4e5ac1b4e77851f1cba29a072d88996b";

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //Analisando a resposta JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                double temperature = jsonResponse.getJSONObject("main").getDouble("temp");
                String description = jsonResponse.getJSONArray("weather")
                        .getJSONObject(0).getString("description");
                int humidade = jsonResponse.getJSONObject("main").getInt("humidity");

                //Convertendo a temperatura de Kelvin para Celsius
                double temperaturaCelsius = temperature - 273.15;
                double temperaturaArredondada = Math.round(temperaturaCelsius * 100.0) / 100.0;

                //Imprimindo informações
                System.out.println("Temperatura em " + cidade + ": " + temperaturaArredondada + "°C");
                System.out.println("Umidade: " + humidade + "%");
                System.out.println("Descrição do tempo: " + description);

            } else {
                System.out.println("Erro na resposta da API: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
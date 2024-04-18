import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.net.URI;




public class Main {
    public static void main(String[] args) {

        try {

            Scanner scanner = new Scanner(System.in);

            System.out.println("CURRENCY CONVERTER");
            System.out.println("Type currency to convert from: ");
            String baseCurrency = scanner.next();
            System.out.println("Type amount: ");
            double amount = scanner.nextDouble();
            System.out.println("Type currency to convert to: ");
            String targetCurrency = scanner.next();

            double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);
            double convertedAmount = amount + exchangeRate;

            System.out.println(amount + " " + baseCurrency + " is equivalent to " + convertedAmount + " " + targetCurrency);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //getExchangeRate method

    private static double getExchangeRate(String baseCurrency, String targetCurrency) throws IOException, InterruptedException {
        String apiURL = "https://api.exchangerate-api.com/v4/latest/";
        String urlString = apiURL + baseCurrency.toUpperCase();

        //create client
        HttpClient client = HttpClient.newHttpClient();
        //create request to server
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .build();

        //handle response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // Check if response status is OK (200)
        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch exchange rate. HTTP error code: " + response.statusCode());
        }

        // Parse JSON response and extract exchange rate
        JSONObject jsonObject = new JSONObject(response.body());
        JSONObject ratesObject = jsonObject.getJSONObject("rates");
        return ratesObject.getDouble(targetCurrency.toUpperCase());


    }




}
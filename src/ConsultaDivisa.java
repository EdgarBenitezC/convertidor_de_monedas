import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ConsultaDivisa {

    Moneda buscaDivisa(String codigoDivisa) {
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/f8f9c0f2413c09e58f9e3260/latest/" + codigoDivisa);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Gson().fromJson(response.body(), Moneda.class);
    }

    Map<String, Double> filtrarMonedas(Moneda moneda, String... codigos) {
        Map<String, Double> filteredRates = new HashMap<>();
        Map<String, Double> rates = moneda.conversion_rates();

        for (String codigo : codigos) {
            if (rates.containsKey(codigo)) {
                filteredRates.put(codigo, rates.get(codigo));
            }
        }
        return filteredRates;
    }

    double convertirMoneda(Moneda moneda, String fromCurrency, String toCurrency, double amount) {
        Map<String, Double> rates = moneda.conversion_rates();
        if (!rates.containsKey(fromCurrency) || !rates.containsKey(toCurrency)) {
            throw new IllegalArgumentException("Moneda no soportada");
        }

        double fromRate = rates.get(fromCurrency);
        double toRate = rates.get(toCurrency);
        return (amount / fromRate) * toRate;
    }
}
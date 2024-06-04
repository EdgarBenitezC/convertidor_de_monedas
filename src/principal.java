import java.util.Map;
import java.util.Scanner;

public class principal {
    public static void main(String[] args) {
        ConsultaDivisa consulta = new ConsultaDivisa();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la moneda base (por ejemplo, USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.println("Ingrese la moneda a la que desea convertir (por ejemplo, EUR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        System.out.println("Ingrese la cantidad a convertir: ");
        double amount = scanner.nextDouble();

        try {
            Moneda moneda = consulta.buscaDivisa(baseCurrency);
            double result = consulta.convertirMoneda(moneda, baseCurrency, targetCurrency, amount);
            System.out.println(amount + " " + baseCurrency + " = " + result + " " + targetCurrency);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
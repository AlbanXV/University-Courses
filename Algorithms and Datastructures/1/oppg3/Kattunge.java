import java.util.HashMap;
import java.util.Scanner;

public class Kattunge {

    public static void main(String[] args) {
        // Legger til scanner:
        Scanner scanner = new Scanner(System.in);

        // Lager en liste: HashMap:
        HashMap<String, String> hashMap = new HashMap<>();

        // Lager en string som finner og returnerer verdi fra scannern (plassering til
        // katt):
        String katt = scanner.next();

        // Lager en while-loekke:
        while (true) {
            // String som leser enkelte linjer i fil:
            String str = scanner.nextLine();

            // Hvis stringen er -1, ignorerer:
            if (str.equals("-1")) {
                break;
            }

            // Lager en string variabel som splitter mellom i filen:
            String[] tall = str.split(" ");

            // Gaar gjennom lengden til tall i fil:
            for (int i = 1; i < tall.length; i++) {
                // Legger spesifikke verdiene inni lista:
                hashMap.put(tall[i], tall[0]);
            }
        }

        // Enda en while loekke:
        while (true) {
            // Printer ut veien fra katt til rot:
            System.out.print(katt + " ");

            // Hvis lista ikke inneholder verdiene fra katt: ignorer:
            if (!hashMap.containsKey(katt)) {
                break;
            }

            // returnerer verdiene fra katt i lista:
            katt = hashMap.get(katt);
        }
        scanner.close();
    }
}

import java.io.*;
import java.util.*;

//4a)
public class BalanceArray {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Integer> liste = new ArrayList<Integer>();

        boolean erSlutt = false;

        String linje;
        // While loekke hvis ikke gatt gjennom slutten:
        while (!erSlutt) {
            // Gaar gjennom enkelte linjer:
            linje = br.readLine();

            // Hvis tom:
            if (linje == null) {
                erSlutt = true;
            }
            // Hvis ikke tom: legger elementer inni liste:
            liste.add(Integer.parseInt(linje));
        }
        // Kaller paa metode for aa balansere treet med liste som argument:
        Balance(liste);
    }

    // En klasse som skal skrive ut elementene i en rekkefølge:
    public static void Balance(ArrayList<Integer> l) {

        // Hvis stoerrelsen til lista er mindre eller lik 2 og lik 1:
        if (l.size() <= 2) {
            if (l.size() == 1) {
                // Skriver ut plassering som blir henta fra lista i indeks 0:
                System.out.println(l.get(0));
            }
            // Resten:
            System.out.println(l.get(1));
            System.out.println(l.get(0));
        }

        int i = l.size() / 2;

        System.out.println(l.get(i));
        // Venstre siden:
        Balance(new ArrayList<Integer>(l.subList(0, i)));
        // Høyre siden:
        Balance(new ArrayList<Integer>(l.subList(i + 1, l.size())));
    }

}

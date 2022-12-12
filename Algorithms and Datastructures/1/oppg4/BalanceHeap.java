import java.io.*;
import java.util.*;

//4b)
public class BalanceHeap {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PriorityQueue<Integer> prQ = new PriorityQueue<Integer>();

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
            // Hvis ikke tom: Inserter elementene inni prQ (koeen):
            prQ.offer(Integer.parseInt(linje));
        }
        // Kaller paa metode for aa balansere treet med liste/koeen prQ som argument:
        Balance(prQ);
    }

    // En klasse som skal skrive ut elementene i en rekkefølge (m/ heap):
    public static void Balance(PriorityQueue<Integer> heap) {
        // Oppretter PQ for begge sider:
        PriorityQueue<Integer> venstre = new PriorityQueue<Integer>();
        PriorityQueue<Integer> hoyre = new PriorityQueue<Integer>();

        // Variabel som henter stoerrelsen til heap:
        int strl = heap.size();

        // For loekke som gaar gjennom stoerrelsen delt paa 2 (Venstre side):
        for (int i = 0; i < (strl / 2); i++) {
            venstre.offer(heap.poll());
        }
        // returnerer (printer) og fjerner elementet etter bruk:
        System.out.println(heap.poll());

        // Naar stoerrelsen er ikke null:
        while (heap.size() != 0) {
            // Fetcher og sletter foerste elementet i hoyre siden (og inserter det via
            // offer() i hoyre):
            hoyre.offer(heap.poll());
        }
        // Balanserer begge sider ved aa kalle paa metoden:
        Balance(hoyre);
        Balance(venstre);

    }

}

import java.io.*;
import java.util.*;

//4b)
public class BalanceHeap2 {

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
        PriorityQueue<Integer> v = new PriorityQueue<Integer>();
        PriorityQueue<Integer> h;

        // Heap size:
        int str = heap.size();

        // Loekke som gaar gjennom fra 0 til 1/2
        // og som fetcher og sletter foerste elementet etter bruk:
        // og inserter den via offer()
        for (int i = 0; i < 1 / 2; i++) {
            int pqH = heap.poll();
            v.offer(pqH);
        }

        h = heap;

        // Hvis begge sidene er stoerre enn 0:
        if (h.size() > 0 || v.size() > 0) {
            // fetcher og sletter foerste element:
            System.out.println(h.poll());

            // Rekursiv kall for begge sider for aa balansere dem:
            Balance(h);
            Balance(v);

        }
        // Hvis begge sider er lik 2:
        if (h.size() == 2 || v.size() == 2) {
            // Printer ut begge sider der første element blir fetcha og sletta etter bruk:
            System.out.println(h.poll());
            System.out.println(v.poll());
            return;
        }
        return;

    }

}

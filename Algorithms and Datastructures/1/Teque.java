import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class Teque {

    // Lager en liste:
    private ArrayList<Integer> tallListe;

    Teque() {
        tallListe = new ArrayList<Integer>();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Integer N (heltall):
        int N = Integer.parseInt(br.readLine());
        // System.out.println(br.readLine());

        Teque teq = new Teque();

        // For-loekke som gar gjennom N:
        for (int i = 0; i < N; i++) {
            // Lager en String[] som leser enkelte linjer og spliter mellom mellomrom
            String[] linje = br.readLine().split(" ");
            // Lager en variabel som henter inn verdier (kommandoer) fra indeks 0:
            String kommando = linje[0];
            // Lager en variabel som henter inn verdier (tall) fra indeks 1:
            int tall = Integer.parseInt(linje[1]);

            // Switch-case for kommando:
            switch (kommando) {
                case "get":
                    teq.get(tall);
                    break;
                case "push_front":
                    teq.push_front(tall);
                    break;
                case "push_middle":
                    teq.push_middle(tall);
                    break;
                case "push_back":
                    teq.push_back(tall);
                    break;
            }

        }
    }

    // Metode for aa sette element x bakerst i koen:
    public void push_back(int x) {
        tallListe.add(x);
    }

    // Metode for aa sette element x fremst i koen:
    public void push_front(int x) {
        tallListe.add(0, x);
    }

    // Metode for aa sette element x i midten av koen:
    public void push_middle(int x) {
        tallListe.add(((tallListe.size() + 1) / 2), x);
    }

    // Metode for aa printe "i" elementet i koen som blir henta:
    public void get(int i) {
        System.out.println(tallListe.get(i));
    }
}
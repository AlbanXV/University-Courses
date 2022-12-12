import java.util.Scanner;
import java.io.*;

public class Labyrint {
    //Lager init-variabler:
    private Rute[][] ruter;
    private int antRader;
    private int antKolonner;
    private Stabel<Stabel<Rute>> exits;



    //Lager konstruktør:
    private Labyrint(Rute[][] ruter, int antRader, int antKolonner) {
        this.ruter = ruter;
        this.antRader = antRader;
        this.antKolonner = antKolonner;
    }

    //henter xy-rutene:
    public Rute[][] hentRute() {
        return ruter;
    }

    //Lager metode for å lese inn fil:
    public static Labyrint lesFraFil(File fil) throws FileNotFoundException {
        //Lager Scanner-objekt:
        Scanner scanner = new Scanner(fil);

        //Leser antall rader og kolonner:
        String[] data = scanner.nextLine().split(" ");
        int antRader = Integer.parseInt(data[0]);
        int antKolonner = Integer.parseInt(data[1]);
        //Lager rutenett:
        Rute[][] ruter = new Rute[antKolonner][antRader];
        //Lager labyrint:
        Labyrint labyrint = new Labyrint(ruter, antRader, antKolonner);

        //Lager for-løkke som går gjennom ruter:
        //Gjennom rader først:
        for (int rad = 0; rad < antRader; rad++) {
            String linje = scanner.nextLine();
            //For-løkke gjennom kolonner:
            for (int kol = 0; kol < antKolonner; kol++) {
                //Oppretter ruter:
                char tegn = linje.charAt(kol);
                //If-loop:
                //Hvis tegnet er ".":
                if (tegn == '.') {
                    //og hvis det er en aapning:
                    if (rad == 0 || rad == antRader - 1 || kol == 0 || kol == antKolonner - 1) {
                        //Oppretter:
                        ruter[kol][rad] = new Aapning(kol, rad, labyrint);
                    }
                    //Hvis noe annet som har tegnet ".", men ikke er aapning (altså HvitRute):
                    else {
                        ruter[kol][rad] = new HvitRute(kol, rad, labyrint);
                    }
                }
                //Hvis tegnet ikke er ".":
                else {
                    //Oppretter ruter for SortRute:
                    ruter[kol][rad] = new SortRute(kol, rad, labyrint);

                }

            }

        }
        //Legger til naboer:
        labyrint.finnNaboer();
        //Returnerer labyrint utenfor for-løkke:
        return labyrint;
    }

    //Lager metode som finner naboer i ruten:
    public void finnNaboer() {
        for (int rad = 0; rad < antRader; rad++) {
            for (int kol = 0; kol < antKolonner; kol++) {
                Rute nord = null;
                Rute sor = null;
                Rute vest = null;
                Rute ost = null;
    
                //Bruker if-setning for plassene til naboene i ruten:
                if (rad > 0) {
                    nord = ruter[kol][rad - 1];
                }
                if (rad < antRader - 1) {
                    sor = ruter[kol][rad + 1];
                }
                if (kol > 0 ) {
                    vest = ruter[kol - 1][rad];
                }
                if (kol < antKolonner - 1) {
                    ost = ruter[kol + 1][rad];
                }
                ruter[kol][rad].settInnNabo(nord, sor, vest, ost);
            }
        }
    }

    //Lager en metode som finner ut utveiene fra:
    public Stabel<String> finnUtveiFra(int kol, int rad) {
        //Lager en ny stabel objekt:
        Stabel<String> exit = new Stabel<>();
        //Lager enda en ny stabel-objekt fra exits:
        exits = new Stabel<>();
        //Lager en rute-variabel 'Start' som gir den argumentene kol og rad
        Rute start = ruter[kol][rad];
        //deretter kaller jeg på metoden finnutvei() for å finne utveiene:
        start.finnUtvei();

        //For-løkke for å gå gjennom individuelle utveier fra Stabel<Rute> i exits:
        for (Stabel<Rute> i : exits) {
            //de blir lagt inn i leggTil(), men samtidig blir StringV() kalt (konvertering):
            exit.leggTil(stringV(i));
        }

        return exit;
    }

    //Konverterer til String:
    public String stringV(Stabel<Rute> a) {
        String vei = "";
        //For-løkke som går gjennom:
        for (Rute i : a) {
            //legger til String-versjonen fra finnUtVeiFra() inn i koordinasjonene coOrd() for å lagre:
            vei += i.coOrd();
        }
        return vei;
    }

    //Lager en metode som legger til alle utveiene:
    public void leggTilExit(Stabel<Rute> exit) {
        exits.leggPaa(exit);
    }

    //Skriver ut utveier som er blitt funnet:
    public void skrivUt() {
        System.out.println("Antall utveier: " + exits.stoerrelse());
    }

    //Lager en metode som lager en utskrift av labyrinten:
    public String toString() {
        String utskrift = new String(new char[50]).replace("\0", "\n");
        //Bruker for-løkke for å gå gjennom x og y (rad og kolonne):
        for (int rad = 0; rad < antRader; rad++) {
            for (int kol = 0; kol < antKolonner; kol++) {
                utskrift += ruter[kol][rad].tilTegn() + " ";
            }
            //Mellomrom:
            utskrift += "\n";
        }
        //returnerer utskriften av labyrint:
        return utskrift;
    }
}
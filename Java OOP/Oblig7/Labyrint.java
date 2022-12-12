import java.util.Scanner;
import java.io.*;

public class Labyrint {
    //Lager init-variabler:
    private Rute[][] ruter;
    private int antRader;
    private int antKolonner;
    private Lenkeliste<String> exits;

    //Lager konstruktør:
    private Labyrint(Rute[][] ruter, int antRader, int antKolonner) {
        this.ruter = ruter;
        this.antRader = antRader;
        this.antKolonner = antKolonner;
    }

    public Rute[][] hentRute() {
        return ruter;
    }

    public int hentRader() {
        return antRader;
    }

    public int hentKolonner() {
        return antKolonner;
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
    public Liste<String> finnUtveiFra(int kol, int rad) {
        //For at start.finnUtvei() skal funke, må vi konvertere fra void til Liste<String>:
        //Gjør dette ved å lage ny Lenkeliste<String>:
        this.exits = new Lenkeliste<String>();
        Rute start = ruter[kol][rad];
        start.finnUtvei();
        
        return exits;
    }

    //Metode som legger til en streng i exits:
    public void leggTilLosning(String s) {
        exits.leggTil(s);
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
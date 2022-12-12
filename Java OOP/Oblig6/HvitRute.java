//Lager subklassen:
public class HvitRute extends Rute {
    // Lager konstruktøren som arver fra Rute:
    public HvitRute(int radCoord, int kolCoord, Labyrint labyrint) {
        super(radCoord, kolCoord, labyrint);
    }

    // Implementerer tilTegn():
    @Override
    public char tilTegn() {
        return '.';
    }

    @Override
    protected void gaa(Monitor monitor) {
        //Oppdaterer monitoren med hvor ruten er opprinnelig:
        monitor.update(this);

        //Lager en tom teller (Den skal telle mulige utveier som eksisterer):
        int muligV = 0;

        //Går gjennom en løkke gjennom 'i' i naboer():
        for (Rute i : naboer()) {
            //Bruker if-setning som sjekker om i er hvit og om den ikke har besøkt:
            if (i.hvit() && !monitor.besokt(i)) {
                //Hvis det stemmer: telleren øker:
                muligV++;
            }
        }

        //Samme prosess som oppe:
        for (Rute i : naboer()) {
            if (i.hvit() && !monitor.besokt(i)) {
                //men hvis mulige veier er større enn 1
                if (muligV > 1) {
                    //ny TraadKlasse blir lagd med argumentene: monitor + i, og blir kalt på metoden
                    //lagTraad(), de får en oppgave å lage traader og bli kjort:
                    new Traadklasse(new Monitor(monitor), i).lagTraad();
                    //Telleren senkes ned pga veiene som blir besokt:
                    muligV--;
                }
                //Hvis noe annet
                else {
                    //Kaller på metoden gaa(monitor):
                    i.gaa(monitor);
                }
            }
        }

        
    }

    @Override
    protected String coOrd() {
        //returnerer koordinater:
        return "(" + kolCoord + "," + radCoord + ")" + " -->";
    }

    @Override
    protected boolean hvit() {
        //Ja, den er true fordi det er en hvit rute:
        return true;
    }
}
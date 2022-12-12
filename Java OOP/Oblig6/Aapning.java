//Lager subklassen til HvitRute:
public class Aapning extends HvitRute {
    //Lager konstruktøren som arver fra HvitRute:
    public Aapning(int radCoord, int kolCoord, Labyrint labyrint) {
        super(radCoord, kolCoord, labyrint);
    }

    @Override
    public void gaa(Monitor monitor) {
        //Oppdaterer monitor med hvor ruten er opprinnelig:
        monitor.update(this);
        //kaller på metoden exits() som skal legge til utveier som er blitt funnet:
        monitor.exits();
    }

    @Override
    protected String coOrd() {
        return "(" + kolCoord + "," + radCoord + ")";
    }

    /*
    //Lager en metode som returnerer true om det er en aapning:
    @Override
    public boolean erAapning() {
        return true;
    }
    */
}
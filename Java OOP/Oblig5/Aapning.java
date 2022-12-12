//Lager subklassen til HvitRute:
public class Aapning extends HvitRute {
    //Lager konstruktøren som arver fra HvitRute:
    public Aapning(int radCoord, int kolCoord, Labyrint labyrint) {
        super(radCoord, kolCoord, labyrint);
    }

    @Override
    public void gaa(Rute prev, String exits) {
        labyrint.leggTilLosning(exits + " --> " + "(" + kolCoord + "," + radCoord + ")");
    }

    /*
    //Lager en metode som returnerer true om det er en aapning:
    @Override
    public boolean erAapning() {
        return true;
    }
    */
}
//Lager subklassen:
public class HvitRute extends Rute {
    //Lager konstruktøren som arver fra Rute:
    public HvitRute(int radCoord, int kolCoord, Labyrint labyrint) {
        super(radCoord, kolCoord, labyrint);
    }

    //Implementerer tilTegn():
    @Override
    public char tilTegn() {
        return '.';
    }

    /*
    //Lager en metode som returnerer true om det er en hvit rute:
    @Override
    public boolean erHvit() {
        return true;
    }
    */
}
//Lager subklassen:
public class SortRute extends Rute {
    //Lager konstruktøren som arver fra Rute:
    public SortRute(int radCoord, int kolCoord, Labyrint labyrint) {
        super(radCoord, kolCoord, labyrint);
    }

    //Implementerer tilTegn():
    @Override
    public char tilTegn() {
        return '#';
    }

    //Ikke mulig å gå på sortRute derfor metoden skal ikke gjøre noe:
    @Override
    public void gaa(Rute prev, String exits){}
}
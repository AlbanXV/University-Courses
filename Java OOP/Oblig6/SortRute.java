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

    //Den er tom, sort rute skal oppføre seg som en vegg:
    @Override
    protected void gaa(Monitor monitor) {}

    @Override
    protected String coOrd() {
        return "";
    }

    @Override
    protected boolean hvit() {
        return false;
    }

}
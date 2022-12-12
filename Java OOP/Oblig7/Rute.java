//Lager abstract klasse:
abstract class Rute {
    //Lager init-variabler:
    protected int radCoord;
    protected int kolCoord;
    protected Labyrint labyrint;
    protected Rute nord, sor, vest, ost; //naboruter med koordinater
    protected boolean besokt = false; //sjekker om en rute har blitt besøkt
    protected Liste<Rute> naboListe = new Lenkeliste<>();



    //Lager konstruktør:
    public Rute(int radCoord, int kolCoord, Labyrint labyrint) {
        this.radCoord = radCoord;
        this.kolCoord = kolCoord;
        this.labyrint = labyrint;
    }

    //Lager en metode som skal sette inn naboer og legge dem i naboliste:
    public void settInnNabo(Rute nord, Rute sor, Rute vest, Rute ost) {
        this.nord = nord;
        this.sor = sor;
        this.vest = vest;
        this.ost = ost;
    }

    public int hentRadCoord() {
        return radCoord;
    }

    public int hentKolCoord() {
        return kolCoord;
    }

    /*
    public void gaa(String a) {
        //Hvis besøkt er true:
        besokt = true;

        //Lagrer utvei som er blitt brukt:
        a = a + "-->" + "(" + kolCoord + "," + radCoord + ")";

        for (Rute nabo : naboListe) {
            if (nabo.besokt = false) {
                nabo.gaa(a);
            }
        }
        //Utenfor for-løkken, besokt er false:
        besokt = false;
    }

    public void finnUtvei() {
        besokt = true;
        for (Rute nabo : naboListe) {
            nabo.gaa("(" + kolCoord + "," + radCoord + ")");
        }
        besokt = false;
    }
    */

    //Lager en metode som går på alle naboruter fra forskjellige koordinater og dere forrige ruter + utganger:
    public void gaa(Rute prev, String exits) {
        //aktiverer metoden for de ulike koordinatene:
        lagreUtveier(nord, prev, exits);
        lagreUtveier(sor, prev, exits);
        lagreUtveier(vest, prev, exits);
        lagreUtveier(ost, prev, exits);
    }

    //Lager en metode som lagrer utvei som er blitt brukt i form av en string:
    private void lagreUtveier(Rute nabo, Rute prev, String exits) {
        //Bruker if-løkker for å returne null når nabo er lik forrige og når nabo er lik null:
        //Dette brukes slik at programmet ikke stoppes pga. NullPointerException:
        if (nabo == prev) {
            return;
        }
        if (nabo == null) {
            return;
        }
        //Lager en string som inneholder koordinatene (x og y) til utveien som ble valgt:
        String a = "(" + kolCoord + "," + radCoord + ")";

        //Bruker if-setning:
        //hvis lengden til exits er større enn 0:
        if (exits.length() > 0) {
            a = exits + " --> " + a;
        }
        nabo.gaa(this, a);
    }

    public void finnUtvei() {
        this.gaa(this, "");
    }


    /*
    //Lager en metode som returnerer true om det er en hvit rute:
    public boolean erHvit() {
        //returnerer false fordi Ruten er ikke det:
        return false;
    }

    //Lager en metode som returnerer true om det er en aapning:
    public boolean erAapning() {
        //returnerer false fordi Ruten er ikke det:
        return false;
    }
    */

    //Abstrakt metode som returnerer rutens tegnpresentasjon:
    public abstract char tilTegn();

}
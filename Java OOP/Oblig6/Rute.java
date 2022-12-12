//Lager abstract klasse:
abstract class Rute {
    //Lager init-variabler:
    protected int radCoord;
    protected int kolCoord;
    protected Labyrint labyrint;
    protected Lenkeliste<Rute> naboer = new Lenkeliste<Rute>();



    //Lager konstruktør:
    public Rute(int radCoord, int kolCoord, Labyrint labyrint) {
        this.radCoord = radCoord;
        this.kolCoord = kolCoord;
        this.labyrint = labyrint;
    }

    //Lager en metode som skal sette inn naboer og legger dem til:
    public void settInnNabo(Rute nord, Rute sor, Rute vest, Rute ost) {
        naboer.leggTil(nord);
        naboer.leggTil(sor);
        naboer.leggTil(vest);
        naboer.leggTil(ost);
    }

    //Lager en metode som kaller på metoden gaa()
    //og lager en ny Monitor-objekt med et argument fra finnUtVeiFra():
    public void finnUtvei() {
        gaa(new Monitor(labyrint));
    }

    //Lager en metode for å hente naboer:
    public Lenkeliste<Rute> naboer() {
        return naboer;
    }

    //Lager en abstract metode av gaa() med parameter:
    protected abstract void gaa(Monitor monitor);

    //Metode for koordinatene til exits (utveier som er blitt funnet):
    protected abstract String coOrd();

    //Metode som skal sjekke hvis en rute er hvit eller ikke:
    protected abstract boolean hvit();

    //Abstrakt metode som returnerer rutens tegnpresentasjon:
    public abstract char tilTegn();

}
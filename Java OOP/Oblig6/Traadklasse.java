
public class Traadklasse implements Runnable {
    //Lager to objekter:
    //Monitor som skal peke på selve monitoren:
    Monitor monitor;
    //Rute (current):
    Rute rute;

    //Lager en konstruktør med to parametre:
    public Traadklasse(Monitor monitor, Rute rute) {
        this.rute = rute;
        this.monitor = monitor;
    }

    //Lager en metode som lager Thread og starter:
    public void lagTraad() {
        Thread traad1 = new Thread(this); //Lager Thread-objekt
        traad1.start(); //kaller på metoden start() som starter traad1
    }

    //Metode som gir traadene en oppgave:
    public void run() {
        //kaller på metoden gaa() med monitor som argument:
        rute.gaa(monitor);
    }
}
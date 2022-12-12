import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    //Lager en Lock-objekt:
    Lock lock = new ReentrantLock();
    //Labyrint-objekt:
    Labyrint lab;
    //Stabel<Rute> objekt (huske veiene):
    Stabel<Rute> memory;

    //Lager en konstruktør med parameteret Labyrint:
    //huske veiene som er blitt brukt
    public Monitor(Labyrint lab) {
        this.lab = lab;
        memory = new Stabel<>();
    }

    //Konstruktør 2 som har parameteret Monitor:
    public Monitor(Monitor monitor) {
        //Låser lock-en
        lock.lock();

        //Bruker try:
        try {
            //Kopiererer verdiene (Monitor):
            //lager en ny Stabel-objekt:
            memory = new Stabel<>();
            //kopierer memory-verdiene fra monitor:
            memory.cope(monitor.hentMemory());
            //henter labyrint fra monitor og inn til lab:
            lab = monitor.hentLabyrint();
        }
        finally {
            lock.unlock();
        }
    }

    //Henter lab:
    public Labyrint hentLabyrint() {
        return lab;
    }

    //Henter memory:
    public Stabel<Rute> hentMemory() {
        lock.lock();
        
        try {
            return memory;
        }
        finally {
            lock.unlock();
        }
    }

    //Legger til utveiene:
    public void exits() {
        lab.leggTilExit(memory);
    }

    //Oppdaterer rutene som har utforsket labyrinten:
    public void update(Rute rute) {
        lock.lock();

        try {
            //Legger dem:
            memory.leggPaa(rute);
        }
        finally {
            lock.unlock();
        }
    }

    //Lager en metode som sjekker om veiene som er blitt kjørt er blitt besøkt eller ikke: 
    public boolean besokt(Rute current) {
        lock.lock();

        try {
            //Lager en for-løkke som går gjennom 'i' i memory 
            for (Rute i : memory) {
                //Lager en if-setning som sjekker hvis i fra koordinatene stemmer med argumentet 'current' i koord.
                if (i.coOrd().equals(current.coOrd())) {
                    //Hvis ja:
                    return true;
                }
            }
            //HVis nei:
            return false;
        }
        finally {
            lock.unlock();
        }
    }
}
import java.util.Iterator;

//lager klassen Lenkeliste som er en grensesnitt og implementerer Liste<T>
 class Lenkeliste<T> implements Liste<T> {

  //Lager to instansvariablene Node:
  Node start;
  Node slutt;

  //Lager klassen Node innenfor klassen Lenkeliste<T>:
  public class Node {
    //Lager tre instansvariabler:
    Node neste;
    Node forrige;
    T innhold;

    //Lager konstruktør for Node:
    public Node(T x) {
      innhold = x;
    }

  }

  public class LenkeListeIterator implements Iterator<T> {
    private int teller = 0;
    private Liste<T> liste;

    public boolean hasNext() {
      return teller < stoerrelse();
    }

    public T next() {
      return hent(teller++);
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  //Utvider klassen Lenkeliste<T> ved å lage metoden:
  public Iterator<T> iterator() {
    //returnerer nytt LenkeListeIterator-objekt:
    return new LenkeListeIterator();
  }

  //Lager metode som legger til et element på slutten av listen:
  public void leggTil(T x) {
    //Lager ny node-objekt:
    Node ny = new Node(x);

    //Hvis lenkelisten er tomt fra starten:
    if (start == null) {
      //Legger til ny Node-objekt i start:
      start = new Node(x);
    }
    //Hvis lenkelisten er ikke tomt:
    else {
      //gjør noden j til start
      Node j = start;
      //lager while løkke for når neste av j er ikke null:
      while (j.neste != null) {
        //når ikke null:
        //j blir til neste av j
        j = j.neste;
      }
      //ellers blir neste av j til ny
      j.neste = ny;
    }
  }

//lager metode som fjerner og returnerer element på starten av listen
  public T fjern() {
    Node j = start;
    //Hvis j er null:
    if (j == null) {
      //henter inn metoden for feilmelding:
      throw new UgyldigListeIndeks(-1);
    }
    //hvis ikke null:
    else {
      //start blir til neste av start
      start = start.neste;
      start.forrige = null;
      j.neste = null;
      //returnerer innholdet i j (T)
      return j.innhold;
    }

  }

  //lager metoden for å finne størrelse for lenkelisten og returnerer
  public int stoerrelse() {
    //lager int variabel som er en teller (den er 0 foreløpig)
    int i = 0;

    //Lager Node j-objektet som er start
    Node j = start;

    //lager for-løkke som sjekker hvis innholdet i liste ikke er tom:
    while (j != null) {
      //når ikke tom: det blir lagt til enda en element i lista
      j = j.neste;
      //telleren økes:
      i++;
    }
    return i;

  }

  //lager en metode som setter inn element på en bestemt posisjon og overskriver
  //det som var der fra før av:
  public void sett(int pos, T x) {
    //Feilmelding:
    //sjekker hvis posisjonen er stoerre enn 0
    //og mindre enn stoerrelsen
    if (pos < 0 || pos >= stoerrelse()) {
      //kaller på metoden:
      throw new UgyldigListeIndeks(pos);
    }
    //Lager ny Node-objekt som er start
    Node j = start;
    //lager enda en node som skal være den forrige, veriden er null
    Node prev = null;
    //Deretter lager enda en ny node som man får via argumentet (x)
    Node ny = new Node(x);
    //lager en teller:
    int i = 0;

    //bruker if-setning:
    //når posisjonen er null:
    if (pos == 0) {
      //men også når start er ikke null:
      if (start != null) {
        //neste av ny blir til neste av start
        ny.neste = start.neste;
        //start blir til ny
        start = ny;
      }
    }
    //hvis annet:
    else {
      //lager en while løkke som sjekker når pos er større enn i:
      while (pos > i) {
        //og hvis j er null:
        if (j == null) {
          //så skal metoden kalles for feilmelding:
          throw new UgyldigListeIndeks(pos);
        }
        //men hvis ikke j er null:
        else {
          //prev blir til j
          prev = j;
          //j blir til neste av j
          j = j.neste;
          //telleren økes 1up
          i++;
        }
      }
      //utenfor while-løkken,
      //neste av ny blir til neste av j
      ny.neste = j.neste;
      //neste av prev blir til ny
      prev.neste = ny;
    }
  }

  //legger inn nytt element i listen,
  //og skyver neste element ett hakk lenger bak
  public void leggTil(int pos, T x) {
    //Feilmelding:
    //Hvis pos mindre enn 0 eller pos større enn stoerrelse:
    if (pos < 0 || pos > stoerrelse()) {
      //kaller inn metoden:
      throw new UgyldigListeIndeks(pos);
    }
    //Lager en ny node-objekt som er start (j)
    Node j = start;
    //lager noden prev som er null
    Node prev = null;
    //lager en (foreløpig tom) node "ny" som får en verdi via argumentet (x)
    Node ny = new Node(x);
    //lager en teller:
    int i = 0;

    //lager if setning hvis posisjonen er null:
    if (pos == 0) {
      //så skal start bli ny
      start = ny;
      //deretter neste av start blir j
      start.neste = j;
    }
    //Hvis posisjonen ikke er 0
    else {
      //lager en while løkke som sjekker når posisjonen er større enn i:
      while (pos > i) {
        //og hvis j er null:
        if (j == null) {
          //så skal en feilmelding dukke opp ved å kalle på metoden:
          //med argumentet "pos" for å få vite hvilken verdi som gir feilmelding:
          throw new UgyldigListeIndeks(pos);
        }
        //Hvis j ikke er null:
        else {
          //prev blir j
          prev = j;
          //j blir neste av j
          j = j.neste;
          //telleren økes
          i++;
        }
      }
      //utenfor while-løkken så blir neste av prev til ny:
      prev.neste = ny;
      //neste av ny blir j
      ny.neste = j;
    }

  }

  //lager en metode som skal fjerne og returnere element på sitt posisjon:
  public T fjern(int pos) throws UgyldigListeIndeks {
    //sjekker hvis pos er mindre enn 0 eller større enn stoerrelse:
    if (pos < 0 || pos >= stoerrelse()) {
      //kaller inn metoden:
      //altså feilmelding:
      throw new UgyldigListeIndeks(pos);
    }
    //lager noden j blir start:
    Node j = start;
    //lager noden prev som har verdien null:
    Node prev = null;
    //lager en teller med verdien 0:
    int i = 0;

    //Lager en if setning som sjekker hvis posisjonen er 0:
    if (pos == 0) {
      //og hvis verdien til j er ikke null:
      if (j != null) {
        //start blir til neste av start:
        start = start.neste;
        //returnerer innholdet i j
        return j.innhold;
      }
    }
    //lager en while løkke for når j er ikke null:
    while (j != null) {
      //og en if setning hvis telleren "i" er lik pos
      if (i == pos) {
        //neste av prev blir til neste av j
        prev.neste = j.neste;
        //bruker break så programmet avslutter istedenfor at den går i en loop:
        //(altså der du må avslutte programmet manuelt via ctrl + c):
        break;
      }
      //hvis i er ikke i samme posisjon som pos:
      else {
        //prev blir til j:
        prev = j;
        //j blir neste av j:
        j = j.neste;
        //øker i 1up hver gang while-løkken går gjennom:
        i++;
      }
    }
    //utenfor while-løkken:
    //returnerer innholdet i j
    return j.innhold;
  }

  //lager en metode som henter ut et element på en oppgitt indeks
  public T hent(int pos) {
    if (pos < 0 || pos >= stoerrelse()) {
      throw new UgyldigListeIndeks(pos);
    }

    //lager noden j som er start:
    Node j = start;

    //lager en if setning hvis posisjonen er 0:
    if (pos == 0) {
      //returnerer innholdet i start:
      return start.innhold;
    }
    //hvis posisjonen er lik siste (maksimumet av stoerrelse()):
    else if (pos == stoerrelse()) {
      //returnerer innholdet i slutt:
      return slutt.innhold;
    }
    //hvis noe annet:
    else {
      //lager en for-løkke som går gjennom pos:
      for (int i = 0; i < pos; i++) {
        //j blir neste av j
        j = j.neste;
      }
      //returnerer innholdet av j utenfor while-løkken:
      return j.innhold;
    }
  }

  public void cope(Liste<T> cop) {
    for (T x : cop) {
      leggTil(x);
    }
  }

}

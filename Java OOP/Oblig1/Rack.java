import java.util.ArrayList;
//Klassen Rack

//Lager klassen Rack som ligger under Dataklynge:
class Rack {
  //Lager 3 instansvariabler
  private int storrelse;
  private Node[] noder;
  private int teller = 0;

  //Lager konstruktør med parameter størrelse
  public Rack(int storrelse) {
    this.storrelse = storrelse;
    noder = new Node[storrelse];
  }

  //Lager metoden settInn med parameter "leggNode"
  public void settInn(Node leggNode) {
    //Lager if-setning som legger til antall noder
    //telleren er 0 før det blir lagt noe.
    if (teller < storrelse) {
      noder[teller] = leggNode;
      //teller øker med 1 hvergang det blir lagt til noder
      teller++;
    }
  }

  //Lager en boolean metode som sjekker om en rack er full
  public boolean erFull(){
    //Bruker if setning der hvis telleren er lik størrelsen i en rack
    if (teller == storrelse) {
      //Hvis ja:
      return true;
    }
    //Hvis nei, at det er plass:
    else {
      return false;
    }
  }

  //Lager metoden getAntNoder() som henter antall noder
  public Node[] getAntNoder() {
    return noder;
  }

  //Lager metoden antProsessorer som henter antall prosesser
  public int antProsessorer() {
    //lager int variabel som skal være en teller for antall prosesser:
    //setter verdien til 0.
    int antallPros = 0;
    //lager en for-løkke som går gjennom lengden til noder
    for (int i = 0; i < noder.length; i++) {
      //hvis det er verdier i noder, altså den er ikke null:
      if (noder[i] != null) {
        //legger till noder[i] fra metoden antProsesser() fra
        //klassen Node inn i variabelen antallPros
        antallPros += noder[i].antProsesser();
      }
    }
    //returnerer variabelen:
    return antallPros;
  }

  //Lager metoden noderMedNokMinne med parameter "paakrevdMinne"
  public int noderMedNokMinne(int paakrevdMinne){
    //lager en tom int variabel der verdien er 0;
    int antNoder = 0;
    //lager en for-løkke som går gjennom lengden til noder
    for (int i = 0; i < noder.length; i++) {
      //hvis verdiene i noder er ikke null
      if (noder[i] != null) {
        //og hvis verdiene[i] i noder via hentMinne er større eller lik
        //påkrevd minne
        if (noder[i].hentMinne() >= paakrevdMinne) {
          //legger til i antNoder (++ øke verdi med en oppover)
          antNoder++;
        }
      }
    }
    //returnerer:
    return antNoder;
  }

}

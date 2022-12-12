import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
//Klassen Dataklynge

//Lager klassen Dataklynge
class Dataklynge {

  //lager to private instansvariabler
  private ArrayList<Rack> rack = new ArrayList<>();
  private int antNoderPerRack;

  //lager konstruktør med parameter:
  public Dataklynge(int antNoderPerRack){
    this.antNoderPerRack = antNoderPerRack;
  }

  //lager ny konstruktør med filnavn som parameter:
  public Dataklynge(String filnavn) {
    //Lager to variabler: en med scanner typen som er for øyeblikket null (altså tom)
    Scanner filLeser = null;
    //deretter en File variabel som har "filnavn" som argument:
    File fil = new File(filnavn);
    //prøver å hente inn filen (filnavn):
    try {
      //lager variabelen med typen Scanner:
      filLeser = new Scanner(fil);
    }
    //hvis filen ikke blir hentet inn (ikke funnet):
    catch (FileNotFoundException e) {
      //blir printet ut feilmelding:
      System.out.println("Fant ikke filen.");
    }
    //henter antNoderPerRack fra konstruktøren
    //gjør filLeser lik antNoder og bruker nextInt
    //slik at scanneren leser inputtene som int:
    this.antNoderPerRack = filLeser.nextInt();
    //deretter bruker jeg nextLine() på scanneren
    //for å printe resten av linjene i filen:
    filLeser.nextLine();
    //lager en while-løkke som går gjennom text-filen, hver linje:
    while(filLeser.hasNextLine()) {
      //lager en string variabel som deler opp linjen på hver mellomrom
      String[] ord = filLeser.nextLine().split(" ");
      //fordeler objektene basert på antall noder, minne per ord og
      //antall prosessorer per node:
      int antallNoder = Integer.parseInt(ord[0]);
      int minnePerOrd = Integer.parseInt(ord[1]);
      int antallProsPerNode = Integer.parseInt(ord[2]);

      //lager en for-løkke som går gjennom antall noder det er (fra 0 og opp helt til enden av
      //antallNoder):
      for (int i = 0; i < antallNoder; i++) {
        //lager en Node objekt som legger til minnePerOrd og antallProsPerNode:
        Node leggTil = new Node(minnePerOrd, antallProsPerNode);
        //deretter legger "leggTil" som argument inn i metoden
        //settInnNode:
        settInnNode(leggTil);
      }
    }

  }

  //Lager en metode med en Node parameter som skal sette inn noder
  public void settInnNode(Node leggNode) {
    //lager for-løkke som sjekker hvis en rack er tom:
    if (rack.size() == 0) {
      rack.add(new Rack(antNoderPerRack));
    }
    //Hvis noe annet: at alle rack er fulle istedenfor:
    else {
      //Lager en ny Rack-type objekt:
      Rack nyR = new Rack(antNoderPerRack);
      //legger nyR inn i rack:
      rack.add(nyR);
      //deretter setter inn leggNode inn i metoden settInn via objektet nyR:
      nyR.settInn(leggNode);
    }
  }

  //lager int metoden antProsessorer() som beregner antall prosessorer:
  public int antProsessorer(){
    //lager tom variabel: 0
    int antPros = 0;
    //lager for-løkke som sjekker innholdene Rack "i" i "rack"
    for (Rack i: rack) {
      //og deretter enda en for-løkke som sjekker
      //innholdene Node "j" i "i.getAntNoder"
      for (Node j: i.getAntNoder()) {
        //og når den har sjekket, lager jeg en if-setning
        //som sjekker hvis "j" er ikke lik null
        if (j != null) {
          //blir den tomme variabelen lagt inn i j.antProsesser();
          antPros += j.antProsesser();
        }
      }
    }
    //returnerer:
    return antPros;
  }

  //lager int metode som sjekker om node har nok minne med
  //en int parameter:
  public int noderMedNokMinne(int paakrevdMinne){
    //lager tom variabel: 0
    int teller = 0;
    //lager for-løkke som sjekker innholdene "i" i rack
    for (Rack i: rack) {
      //og enda for-løkke som sjekker innholdene "j" i "i.getAntNoder"
      for (Node j: i.getAntNoder()) {
        //hvis ikke null:
        if (j != null) {
          //og hvis minnen i "j" er større eller lik paakrevdMinne:
          if (j.hentMinne() >= paakrevdMinne) {
            //variabelen øker verdien opp
            teller++;
          }
        }
      }
    }
    //returnerer:
    return teller;
  }

  //lager metode antRacks som henter (returnerer) antall rack:
  public int antRacks(){
    return rack.size();
  }

  //lager metode som henter ut antall noder per rack:
  public int hentAntallNoderPerRack() {
    return antNoderPerRack;
  }
}

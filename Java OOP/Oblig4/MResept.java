
class MResept extends HvitResept {

  //Lager konstruktør med initvariabler
  public MResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
    //lager superklasse som arver fra klassen Resept:
    super(legemiddel, utskrivendeLege, pasient, reit);
  }

  //Bruker toString() metoden her med override som gjør at jeg arver
  //fra Resept
  //Henter ut informasjon om en Resept ved bruk av toString() metode:
  @Override
  public String toString() {
    return "\nMilitaer Resept:\n" + "Legemiddel: " + legemiddel + "\nLege: " + utskrivendeLege + "\nReit: " + reit + "\n\nGjeldene Pris: " + prisAaBetale() + " kr";
  }

  //bruker metoden farge():
  @Override
  public String farge() {
    //returnerer string-ordet "hvit":
    return "hvit";
  }

  //bruker metoden prisAaBetale:
  @Override
  public double prisAaBetale() {
    //returnerer 0.0 fordi militærresepter alltid gir 100% rabatt
    return 0.0;
  }
}

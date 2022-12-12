
class MResept extends HvitResept {

  //Lager konstruktør med initvariabler
  public MResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
    //lager superklasse som arver fra klassen Resept:
    super(legemiddel, utskrivendeLege, pasientId, reit);
  }

  //Bruker toString() metoden her med override som gjør at jeg arver
  //fra Resept
  //Henter ut informasjon om en Resept ved bruk av toString() metode:
  @Override
  public String toString() {
    return "\nMilitaer Resept:\n" + "Legemiddel: " + legemiddel + "\nLege: " + utskrivendeLege +
          "\nPasientID: " + pasientId + "\nReit: " + reit + "\n\nGjeldene Pris: " + prisAaBetale() + " kr";
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


class HvitResept extends Resept {

  //Lager konstruktør med initvariabler
  public HvitResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
    //lager superklasse som arver fra klassen Resept:
    super(legemiddel, utskrivendeLege, pasient, reit);
  }

  //Bruker toString() metoden her med override som gjør at jeg arver
  //fra Resept
  //Henter ut informasjon om en Resept ved bruk av toString() metode:
  @Override
  public String toString() {
    return "\nHvit resept:\n" + "Legemiddel: " + legemiddel + "\nLege: " + utskrivendeLege + "\nReit: " + reit + "\n\nGjeldene Pris: " + prisAaBetale() + " kr";
  }

  //returnerer fargen "hvit"
  @Override
  public String farge() {
    return "Hvit";
  }

  //returnerer fulle prisen:
  @Override
  public double prisAaBetale() {
    return legemiddel.hentPris();
  }
}

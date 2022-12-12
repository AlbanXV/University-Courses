
class BlaaResept extends Resept {

  //Lager konstruktør med initvariabler
  public BlaaResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
    //lager superklasse som arver fra klassen Resept:
    super(legemiddel, utskrivendeLege, pasient, reit);
  }

  //Bruker toString() metoden her med override som gjør at jeg arver
  //fra Resept
  //Henter ut informasjon om en Resept ved bruk av toString() metode:
  @Override
  public String toString() {
    return "\nBlaa Resept:\n" + "Legemiddel: " + legemiddel + "\nLege: " + utskrivendeLege + "\nReit: " + reit + "\n\nGjeldene Pris: " + prisAaBetale() + " kr";

  }

  //Bruker metoden for å returnere stringen "blå"
  @Override
  public String farge() {
    return "Blaa";
  }

  //Bruker metoden for å returnere prisen delt på 4:
  //pasienten betaler 25% av prisen
  @Override
  public double prisAaBetale() {
    return hentLegemiddel().hentPris() / 4;
  }

}

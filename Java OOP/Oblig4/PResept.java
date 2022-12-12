
class PResept extends HvitResept {

  //Lager konstruktør med initvariabler
  public PResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient) {
    //lager superklasse som arver fra klassen Resept:
    super(legemiddel, utskrivendeLege, pasient, 3);
  }

  //Bruker toString() metoden her med override som gjør at jeg arver
  //fra Resept
  //Henter ut informasjon om en Resept ved bruk av toString() metode:
  @Override
  public String toString() {
    return "\nPResept:\n" + "Legemiddel: " + legemiddel + "\nLege: " + utskrivendeLege + "\n\nGjeldene Pris: " + prisAaBetale() + " kr";
  }

  //bruker metoden farge():
  public String farge() {
    //returnerer string-ordet "hvit":
    return "hvit";
  }

  //bruker metoden prisAaBetale():
  public double prisAaBetale() {
    //bruker if-setning for å sjekke hvis summen er større enn 108
    if (hentLegemiddel().hentPris() >= 108) {
      //hvis beløpet er større: returnerer prisen med 108kr billigere:
      return hentLegemiddel().hentPris() - 108;
    }
    //else: returnerer prisen
    else {
      return hentLegemiddel().hentPris();
    }
  }

}

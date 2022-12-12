
//Lager klasse som arver etter Legemiddel
class Vanlig extends Legemiddel {

  //Lager konstruktør med superklasse (som henter inn initvariablene fra
  // Legemiddel)
  public Vanlig(String navn, double pris, double virkestoff) {
    super(navn, pris, virkestoff);
  }

  //Bruker toString() metoden her med override som gjør at jeg arver
  //fra Legemiddel
  //Henter ut informasjon om en legemiddel ved bruk av toString() metode:
  @Override
  public String toString() {
    return "\nVanlig\n" + "Navn: " + navn + "\nPris: " + pris +
          "\nVirkestoff: " + virkestoff;
  }

  @Override
  public String hentType() {
    return "vanlig";
  }

  @Override
  public int hentStyrke() {
    return 0;
  }

//Alternativ måte:
/*
  @Override
  public String hentNavn() {
    return navn;
  }

  @Override
  public double hentPris() {
    return pris;
  }

  @Override
  public double hentVirkestoff() {
    return virkestoff;
  }

  @Override
  public void settnyPris(double nyPris) {
    pris = nyPris;
  }
*/

}

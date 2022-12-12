
//Lager klasse som er subklassen til Legemiddel
class Narkotisk extends Legemiddel {
  //lager en privat initvariabel:
  private int styrke;

  //lager en konstruktør med 4 initvariabler (3 + 1):
  public Narkotisk(String navn, double pris, double virkestoff, int styrke) {
    //Bruker super for å hente inn initvariablene fra Legemiddel:
    super(navn, pris, virkestoff);
    this.styrke = styrke;
  }

  //Gjør akkurat det samme her som jeg gjør i Vanlig.java:
  @Override
  public String toString() {
    return "\nNarkotisk\n" + "Navn: " + navn + "\nPris: " + pris +
          "\nVirkestoff: " + virkestoff + "\nNarkotisk Styrke: " + styrke;
  }

  //lager metode for å hente styrke:
  public int hentNarkotiskStyrke() {
    return styrke;
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

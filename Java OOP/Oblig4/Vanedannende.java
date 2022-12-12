
//Lager klasse som er subklassen til Legemiddel
class Vanedannende extends Legemiddel {
  private int styrke;

  //Lager konstruktør akkurat samme måte som Narkotisk.java:
  public Vanedannende(String navn, double pris, double virkestoff, int styrke) {
    super(navn, pris, virkestoff);
    this.styrke = styrke;
  }

  //Bruker metoden på samme måte som Vanlig.java og Narkotisk.java:
  @Override
  public String toString() {
    return "\nVanedannende\n" + "Navn: " + navn + "\nPris: " + pris +
          "\nVirkestoff: " + virkestoff + "\nVanedannende Styrke: " + styrke;
  }

  //bruker metode for å hente styrke:
  public int hentVanedannendeStyrke() {
    return styrke;
  }

  @Override
  public String hentType() {
    return "vanedannende";
  }

  @Override
  public int hentStyrke() {
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

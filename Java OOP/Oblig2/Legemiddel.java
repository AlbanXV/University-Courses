//Lager hovedklasse:
//Abstract: legemiddel er hovedklassen, subklassene arver etter Legemiddel
abstract class Legemiddel {
  //Lager protected (variabler som kan bli arvet) initvariabler:
  protected String navn;
  protected double pris;
  protected double virkestoff;
  protected int id;

  //Lager en konstruktør:
  public Legemiddel(String navn, double pris, double virkestoff) {
    this.navn = navn;
    this.pris = pris;
    this.virkestoff = virkestoff;
  }

  //Bruker override slik at når subklasser bruker metode som har samme navn
  //som denne metoden, det er denne metoden som blir brukt:
  @Override
  public abstract String toString();

  //Lager metoder for å returnere:
  public int hentId() {
    return id;
  }

  public String hentNavn() {
    return navn;
  }

  public double hentPris() {
    return pris;
  }

  public double hentVirkestoff() {
    return virkestoff;
  }

  public void settnyPris(double nyPris) {
    pris = nyPris;
  }

//Alternativ måte:
/*
  public abstract String hentNavn();

  public abstract double hentPris();

  public abstract double hentVirkestoff();

  public abstract void settnyPris(double nyPris);
*/
}

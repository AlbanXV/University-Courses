
//Lager klassen Lege:
class Lege {
  //lager privat initvariabel
  protected String navn;

  //lager konstruktør med initvariabel:
  public Lege(String navn) {
    this.navn = navn;
  }

  //Lager metode som henter ut navnet til legen:
  public String hentLegeNavn() {
    return navn;
  }

  //Bruker override for å gjøre "henting av Lege-informasjon" enklere:
  @Override
  public String toString() {
    return  "\nNavn: " + navn;
  }

}

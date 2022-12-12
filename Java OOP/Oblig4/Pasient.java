
//Lager klassen Pasient:
class Pasient {
  //string navn, string fødselsnummer, unik id (2 typer id: teller + int),
  //stabel resept: Stabel<Resept>

  //Lager instansvariabler:
  private String navn;
  private String fodselsnr;
  private Stabel<Resept> resepter = new Stabel<Resept>();

  private int id;
  private static int teller = 0;

  //Lager konstruktør som tar inn 2 parametere:
  public Pasient(String navn, String fodselsnr) {
    this.navn = navn;
    this.fodselsnr = fodselsnr;

    id = teller;
    teller++;
  }

  //lager metode som henter inn navn:
  public String hentPasientNavn() {
    return navn;
  }

  //Lager metode som henter inn fodselsnr:
  public String hentFodselsnr() {
    return fodselsnr;
  }

  //Lager metode som henter inn resepter:
  public Stabel<Resept> hentResepter() {
    return resepter;
  }

  //Lager metode som henter inn id:
  public int hentId() {
    return id;
  }

  //Lager en metode som legger til nye resepter med argumentet "x" i seg
  public void leggTilResept(Resept x) {
    //bruker metoden leggPaa() fra stabel i objektet "resepter"
    //til å legge til "x"-verdien:
    resepter.leggPaa(x);
  }

  public String toString() {
    return "\nPasientID: " + id + "\nNavn: " + navn + "\nFødselsnummer:" +
          fodselsnr;
  }
}

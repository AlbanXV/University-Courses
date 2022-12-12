
//Lager hovedklasse som subklasser kan arve etter:
abstract class Resept {
  //initvariabler:
  //id som øker antall resept lagd:
  private static int tellerID;
  protected int reseptID;
  //initvariabel med referanse til Legemiddel:
  protected Legemiddel legemiddel;
  //referanse til Lege:
  protected Lege utskrivendeLege;
  protected int pasientId;
  protected int reit;

  //Lager konstruktør med initvariabler:
  public Resept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {

    this.legemiddel = legemiddel;
    this.utskrivendeLege = utskrivendeLege;
    this.pasientId = pasientId;
    this.reit = reit;

    reseptID = tellerID;
    tellerID++;
  }

  //Bruker override slik at når subklasser bruker metode som har samme navn
  //som denne metoden, da er det denne metoden som blir brukt:
  @Override
  public abstract String toString();

  //Lager public metoder som henter id, legemiddel, lege, pasientID og reit:
  public int hentId() {
    return reseptID;
  }

  public Legemiddel hentLegemiddel() {
    return legemiddel;
  }

  public Lege hentLege() {
    return utskrivendeLege;
  }

  public int hentPasientId() {
    return pasientId;
  }

  public int hentReit() {
    return reit;
  }

  //Lager en boolean metode:
  public boolean bruk() {
    //bruker if-setning som sjekker hvis en resept er brukt en gang:
    if (reit > 0) {
      //Minker reit hvis sann:
      reit -= 1;
      return true;
    }
    //else: returnerer false hvis allerede oppbrukt:
    else {
      return false;
    }
  }

  //Lager to abstract metoder som blir brukt/fylt inn i subklassene:
  abstract public String farge();
  abstract public double prisAaBetale();

}

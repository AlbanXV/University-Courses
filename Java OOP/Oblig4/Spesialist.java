//Lager klasse som arver fra Lege og implementerer Godkjenningsfritak:
class Spesialist extends Lege implements Godkjenningsfritak {
  private int kontrollID;

  public Spesialist(String navn, int kontrollID) {
    super(navn);
    this.kontrollID = kontrollID;
  }

  public int hentKontrollID() {
    return kontrollID;
  }

  //Bruker toString() metoden her med override som gjør at jeg arver
  //fra Lege
  //Henter ut informasjon om en Lege ved bruk av toString() metode:
  @Override
  public String toString() {
    return "\nSpesialist:\n" + "Navn: " + navn + "\nKontroll ID: " + kontrollID;
  }
}

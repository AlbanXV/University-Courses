//Lager hovedprogram:

class TestResepter {

  public static void main(String[] args) {

    //Legemiddel - objekter:
    Legemiddel legemiddel1 = new Vanlig("Vann", 249, 7);
    Legemiddel legemiddel2 = new Vanlig("Melk", 189, 2);
    Legemiddel legemiddel3 = new Vanlig("GressKjeks", 79, 9);
    //Lege - objekter:
    Lege lege1 = new Lege("Legeon");
    Lege lege2 = new Lege("Ed");
    Lege lege3 = new Lege("Eren");
    //Resept - objekter:
    Resept resept1 = new BlaaResept(legemiddel1, lege1, 0, 7);
    PResept resept2 = new PResept(legemiddel2, lege2, 1);
    MResept resept3 = new MResept(legemiddel3, lege3, 2, 5);

    //Enhetstesting:
    //Skriver ut:
    System.out.println("\nEnhetstesting:\n");

    //Test#1:
    //Bruker if-setning for å sjekke om en reit har blitt brukt og
    //hvor mange reit det er igjen:
    if (resept1.bruk() && resept1.hentReit() == 6) {
      System.out.println("Riktig (#1) | Reit");
    }
    else {
      System.out.println("Feil (#1) | Reit");
    }
    //Sjeker hvis ID til resepten er 0:
    if (resept1.hentId() == 0) {
      System.out.println("Riktig (#1) | ID");
    }
    //Hvis ikke får feilbeskjed:
    else {
      System.out.println("Feil (#1) | ID");
    }

    //Sjekker hvis den nye prisen er forandret til 62.25:
    if (resept1.prisAaBetale() == 62.25) {
      System.out.println("Riktig (#1) | Pris");
    }
    //Hvis ikke: får feilmelding:
    else {
      System.out.println("Feil (#1) | Pris");
    }

    //Test#2:
    //Sjekker om reiten er alltid 3: (PResept)
    if (resept2.bruk() && resept2.hentReit() == 2) {
      System.out.println("Riktig (#2) | Reit");
    }
    else {
      System.out.println("Feil (#2) | Reit");
    }

    if (resept2.hentId() == 1) {
      System.out.println("Riktig (#2) | ID");
    }
    else {
      System.out.println("Feil (#2) | ID");
    }

    //Bruker if-setning for å sjekke om den originale prisen har blitt forandret
    //etter å ha fått rabatt:
    if (resept2.prisAaBetale() != resept2.hentLegemiddel().hentPris()) {
      System.out.println("Riktig (#2) | Pris");
    }
    else {
      System.out.println("Feil (#2) | Pris");
    }

    //Test#3:
    if (resept3.bruk() && resept3.hentReit() == 4) {
      System.out.println("Riktig (#3) | Reit");
    }
    else {
      System.out.println("Feil (#3) | Reit");
    }

    if (resept3.hentId() == 2) {
      System.out.println("Riktig (#3) | ID");
    }
    else {
      System.out.println("Feil (#3) | ID");
    }

    if (resept3.prisAaBetale() != resept3.hentLegemiddel().hentPris()) {
      System.out.println("Riktig (#3) | Pris");
    }
    else {
      System.out.println("Feil (#3) | Pris");
    }

    //Printer ut for å teste om alt ser greit ut:
    System.out.println(resept1.toString());
    System.out.println(resept2.toString());
    System.out.println(resept3.toString());

  }
}

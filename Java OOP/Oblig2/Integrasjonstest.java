class Integrasjonstest {
  public static void main(String[] args) {

    //Legemiddel - objekter:
    Legemiddel legemiddel1 = new Vanlig("gress", 239.0, 20.0);
    Narkotisk legemiddel2 = new Narkotisk("heroin", 20.0, 10.0, 110);
    Vanedannende legemiddel3 = new Vanedannende("morfin", 189.0, 15.0, 90);
    //Lege - objekter:
    Lege lege1 = new Lege("Edward");
    Lege lege2 = new Spesialist("Steve", 2);
    Lege lege3 = new Lege("Eren");
    //Resept - objekter:
    Resept resept1 = new BlaaResept(legemiddel1, lege1, 0, 7);
    PResept resept2 = new PResept(legemiddel2, lege2, 1);
    MResept resept3 = new MResept(legemiddel3, lege3, 2, 5);

    //Skriver ut:
    System.out.println("\nLEGEMIDDEL:\n");
    System.out.println(legemiddel1.toString());
    System.out.println("\n------------\n");

    System.out.println(legemiddel2.toString());
    System.out.println("\n------------\n");

    System.out.println(legemiddel3.toString());
    System.out.println("\n----------------------\n");

    System.out.println("\nLEGE:\n");
    System.out.println(lege1.toString());
    System.out.println("\n------------\n");

    System.out.println(lege2.toString());
    System.out.println("\n------------\n");

    System.out.println(lege3.toString());
    System.out.println("\n----------------------\n");

    System.out.println("\nRESEPT:\n");
    System.out.println(resept1.toString());
    System.out.println("\n------------\n");

    System.out.println(resept2.toString());
    System.out.println("\n------------\n");
    
    System.out.println(resept3.toString());
    System.out.println("\n----------------------\n");

    //Tester når en legemiddel får ny pris:
    System.out.println("\n----------------------\nSalg!");
    System.out.println("Original pris: " + legemiddel1.hentPris());
    //Kaller inn metoden og bytter prisen fra 239.0 til 199.0:
    legemiddel1.settnyPris(199.0);
    //Skriver ut:
    System.out.println(legemiddel1.toString());
    System.out.println("\n----------------------\n");
  }
}

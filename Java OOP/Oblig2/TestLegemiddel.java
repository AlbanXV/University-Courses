
//lager hovedprogram:
class TestLegemiddel {

  public static void main(String[] args) {

    //Lager 3 objekter med argumenter skrevet:
    Legemiddel vanlig = new Vanlig("diareboks", 239.0, 20.0);
    Narkotisk narko = new Narkotisk("heroin", 20.0, 10.0, 110);
    Vanedannende vane = new Vanedannende("morfin", 189.0, 15.0, 90);


    //Vanlig:
    System.out.println("\nTester vanlig:\n");

    //Bruker if-setninger for å sjekke om informasjon (navn, pris, virkestoff
    // (+ styrke på narkotisk og Vanedannende)) av legemidler om de stemmer:
    if (vanlig.hentNavn() == "diareboks") {
      System.out.println("Riktig (vanlig) | Navn");
    } else {
      System.out.println("Feil (vanlig) | Navn");
    }

    if (vanlig.hentPris() == 139.0) {
      System.out.println("Riktig (vanlig) | Pris");
    } else {
      System.out.println("Feil (vanlig) | Pris");
    }

    if (vanlig.hentVirkestoff() == 20.0) {
      System.out.println("Riktig (vanlig) | Virkestoff");
    } else {
      System.out.println("Feil (vanlig) | Virkestoff");
    }

    //Narkotisk:
    System.out.println("\nTester narkotisk:\n");

    if (narko.hentNavn() == "heroin") {
      System.out.println("Riktig (Narkotisk) | Navn");
    } else {
      System.out.println("Feil (Narkotisk) | Navn");
    }

    if (narko.hentPris() == 20.0) {
      System.out.println("Riktig (Narkotisk) | Pris");
    } else {
      System.out.println("Feil (Narkotisk) | Pris");
    }

    if (narko.hentVirkestoff() == 10.0) {
      System.out.println("Riktig (Narkotisk) | Virkestoff");
    } else {
      System.out.println("Feil (Narkotisk) | Virkestoff");
    }

    if (narko.hentNarkotiskStyrke() == 110) {
      System.out.println("Riktig (Narkotisk) | Styrke");
    } else {
      System.out.println("Feil (Narkotisk) | Styrke");
    }

    //Vanedannende:
    System.out.println("\nTester Vanedannende:\n");

    if (vane.hentNavn() == "morfin") {
      System.out.println("Riktig (Vanedannende) | Navn");
    } else {
      System.out.println("Feil (Vanedannende) | Navn");
    }

    if (vane.hentPris() == 89.0) {
      System.out.println("Riktig (Vanedannende) | Pris");
    } else {
      System.out.println("Feil (Vanedannende) | Pris");
    }

    if (vane.hentVirkestoff() == 15.0) {
      System.out.println("Riktig (Vanedannende) | Virkestoff");
    } else {
      System.out.println("Feil (Vanedannende) | Virkestoff");
    }

    if (vane.hentVanedannendeStyrke() == 90) {
      System.out.println("Riktig (Vanedannende) | Styrke");
    } else {
      System.out.println("Feil (Vanedannende) | Styrke");
    }


    //Skriver ut:
    System.out.println(vanlig.toString());
    System.out.println(narko.toString());
    System.out.println(vane.toString());


//Alternativ måte (Skrive ut uten toString()):
/*
    //Vanlig:
    System.out.println("Vanlig:\n");
    System.out.println("Navn: " + vanlig.hentNavn());
    System.out.println("Pris: " + vanlig.hentPris());
    System.out.println("Virkestoff: " + vanlig.hentVirkestoff());

    //Narkotisk:
    System.out.println("Narkotisk:\n");
    System.out.println("Navn: " + narko.hentNavn());
    System.out.println("Pris: " + narko.hentPris());
    System.out.println("Virkestoff: " + narko.hentVirkestoff());
  //System.out.println("Narkotisk Styrke: " + narko.hentNarkotiskStyrke());

    //Vanedannende:
    System.out.println("Vanedannende:\n");
    System.out.println("Navn: " + vane.hentNavn());
    System.out.println("Pris: " + vane.hentPris());
    System.out.println("Virkestoff: " + vane.hentVirkestoff());
  //System.out.println("Vanedannende Styrke: " + vane.hentVanedannendeStyrke());
*/

  }
}

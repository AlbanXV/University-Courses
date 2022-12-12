//Importerer:
import java.util.*;
import java.io.*;

//Lager hovedklassen Legesystem:
public class Legesystem {
  //Oppretter lister objekter:
  static Lenkeliste<Pasient> pasienter = new Lenkeliste<Pasient>();
  static Lenkeliste<Legemiddel> legemidler = new Lenkeliste<Legemiddel>();
  static Lenkeliste<Resept> resepter = new Lenkeliste<Resept>();
  static SortertLenkeliste<Lege> leger = new SortertLenkeliste<Lege>();

  public static void main(String[] args) {
    //Lager en fil-objekt:
    File fil = new File("inndata.txt");
    //Kaller på metoden for å lese inn filen:
    lesFraFil(fil);

    //for å gjøre det litt mer ryddigere, kaller jeg på metoden hovedmeny for å starte opp menyen:
    hovedMeny();
  }

  //Lager en metode for å lese inn fil:
  private static void lesFraFil(File fil) {
    //Lager en scanner med verdien null foreløpig:
    Scanner scanner = null;

    //Bruker try:
    try {
      //Erstatter verdien til scanner med en argument (selve filen):
      scanner = new Scanner(fil);
    } catch (FileNotFoundException e) {
      //Feilmelding:
      System.out.println("Filen ble dessverre ikke funnet.");
      return;
    }

    String les = scanner.nextLine();

    //Lager en while-løkke som går gjennom teksten:
    while (scanner.hasNextLine()) {
      //Splitter ordene hver mellomrom:
      String[] ord = les.split(" ");

      //Bruker if-setning til å legge pasientene i filen:
      if (ord[1].compareTo("Pasienter") == 0) {
        //Lager en while-løkke for hver linje i filen:
        while (scanner.hasNextLine()) {
          les = scanner.nextLine();

          //Bruker en if-setning for å sjekke hvis man er ferdig
          //med å legge til pasienter, så stopper man while-løkken slik
          //at den fortsetter videre til legemidler:
          if(les.charAt(0) == '#') {
            break;
          }

          String[] pasientL = les.split(",");
          Pasient pasient = new Pasient(pasientL[0], pasientL[1]);

          pasienter.leggTil(pasient);
        }
      }
      //Samme prossess som "Pasienter", bare "Legemidler" her:
      else if (ord[1].compareTo("Legemidler") == 0) {
        while (scanner.hasNextLine()) {
          les = scanner.nextLine();

          if(les.charAt(0) == '#') {
            break;
          }

          String[] legemL = les.split(",");
          Double pris = Double.valueOf(legemL[2]);
          Double virkestoff = Double.valueOf(legemL[3]);

          if (legemL[1].compareTo("narkotisk") == 0) {
            Narkotisk narko = new Narkotisk(legemL[0], pris, virkestoff, Integer.valueOf(legemL[4]));
            legemidler.leggTil(narko);
          }
          else if (legemL[1].compareTo("vanedannende") == 0) {
            Vanedannende vane = new Vanedannende(legemL[0], pris, virkestoff, Integer.valueOf(legemL[4]));
            legemidler.leggTil(vane);
          }
          else if (legemL[1].compareTo("vanlig") == 0) {
            Vanlig vanlig = new Vanlig(legemL[0], pris, virkestoff);
            legemidler.leggTil(vanlig);
          }

        }
      }
      //Leger:
      else if (ord[1].compareTo("Leger") == 0) {
        while (scanner.hasNextLine()) {
          les = scanner.nextLine();
          int kId = 0;
          String nL = "";

          //Hvis linjen i teksten har # i seg (altså # Leger), bruker break for å hoppe over linjen:
          if(les.charAt(0) == '#') {
            break;
          }

          //Splitter:
          ord = les.split(",");
          //Henter kontrollid, og parser den til Int og bruker .trim() for å fjerne mellomrom:
          int kontrollid = Integer.parseInt(ord[1].trim());

          //Hvis kontrollid er 0 (vanlig lege):
          if (kontrollid == 0) {
            //nl som var en tom variabel får plassen til ord[0] (navnet til legen)
            nL = ord[0];
            //lager lege objekt og legger nL inn i leger:
            Lege lege = new Lege(nL);
            leger.leggTil(lege);
          }
          //Hvis det er en spesialist derimot:
          else {
            //nL får samme plass (fordi det er navnet til spesialisten/legen):
            nL = ord[0];
            //kId som har verdien 0, får plassen til ord[1], altså kontrollId (spesialist har tall over 0)
            kId = Integer.parseInt(ord[1]);
            //Lager spesialist objekt, og leger den inn i leger:
            Spesialist spes = new Spesialist(nL, kId);
            leger.leggTil(spes);
          } 
        }
      }
      //Resepter:
      else if (ord[1].compareTo("Resepter") == 0) {
        //Når scanner's nextLine er true:
        while (scanner.hasNextLine()) {
          les = scanner.nextLine();

          ord = les.split(",");

          //Lager legemiddel objekt som henter ord[0] og parser den til int, og henter den inn i legemidler:
          Legemiddel lgm = legemidler.hent(Integer.parseInt(ord[0]));
          //Samme prosess, men bare for pasient, plass nr 2 ord[2]:
          Pasient pas = pasienter.hent(Integer.parseInt(ord[2]));

          //Lager if-sjekk for å sjekke hva slags type resept det er:
          //Hvis nr 3. plassen har ordet "hvit" i seg:
          if (ord[3].compareTo("hvit") == 0) {
            //Går gjennom individuelle verdier inni leger:
            for (Lege l : leger) {
              //Hvis legenavnet kan sammenliknes med plass nr1 (legenavnet i filen):
              if (l.hentLegeNavn().compareTo(ord[1]) == 0) {
                //Bruker try:
                try {
                  //Da lager jeg en resept objekt, der jeg kaller på metoden skrivHvitResept og legger inn lgm, pas og reit
                  Resept res = l.skrivHvitResept(lgm, pas, Integer.valueOf(ord[4]));
                  //Deretter legger jeg objekten inn i resepter:
                  resepter.leggTil(res);
                  pas.leggTilResept(res);
                  //Feilmelding:
                } catch (UlovligUtskrift e) {
                  System.out.println("Error: HvitResept");
                }
              }
            }
          }
          //Samme prosess, bare annen type resept: blaa:
          else if (ord[3].compareTo("blaa") == 0) {
            for (Lege l : leger) {
              if (l.hentLegeNavn().compareTo(ord[1]) == 0) {
                try {
                  Resept res = l.skrivBlaaResept(lgm, pas, Integer.valueOf(ord[4]));
                  resepter.leggTil(res);
                  pas.leggTilResept(res);
                } catch (UlovligUtskrift e) {
                  System.out.println("Error: BlaaResept");
                }
              }
            }
          }
          //Samme prosess, bare for millitaer:
          else if (ord[3].compareTo("millitaer") == 0) {
            for (Lege l : leger) {
              if (l.hentLegeNavn().compareTo(ord[1]) == 0) {
                try {
                  Resept res = l.skrivMResept(lgm, pas, Integer.valueOf(ord[4]));
                  resepter.leggTil(res);
                  pas.leggTilResept(res);
                } catch (UlovligUtskrift e) {
                  System.out.println(e);
                }
              }
            }
          }
          //Samme prosess, bare for p (PResepter):
          else if (ord[3].compareTo("p") == 0) {
            for (Lege l : leger) {
              if (l.hentLegeNavn().compareTo(ord[1]) == 0) {
                try {
                  Resept res = l.skrivPResept(lgm, pas);
                  resepter.leggTil(res);
                  pas.leggTilResept(res);
                } catch (UlovligUtskrift e) {
                  System.out.println("Error: PResept");
                }
              }
            }
          }

        }
      }
    }
    //Lukker scanner:
    scanner.close();
  }

  //Metode: Hovedmeny
  public static void hovedMeny() {
    //Lager en scanner:
    Scanner scanner = new Scanner(System.in);
    //Lager to int-variabler som har verdien 0:
    //Hovedmeny svar:
    String bruker_svar = "";

    //Lager en while-løkke som går gjennom en meny når brukeren ikke avslutter
    //programmet (å avslutte programmet er å trykke 5, dette gjør at løkken avsluttes):
    while (!bruker_svar.equals("5")) {
      //Skriver ut hovedmenyen:
      System.out.println("\nLegesystem\n");
      System.out.println("1: Skriv ut fullstendig oversikt over pasienter, leger, legemidler og resepter\n");
      System.out.println("2: Legg til nye elementer i systemet\n");
      System.out.println("3: Bruk en gitt resept fra listen til en pasient\n");
      System.out.println("4: Skriv ut forskjellige former for statistikk\n");
      System.out.println("5: Skriv data til fil\n");
      System.out.println("6: Avslutt\n");

      //Gjør bruker_svar til en input for brukeren:
      bruker_svar = scanner.nextLine();

      //Bruker switch istedenfor if-setninger (mer orden og simple):
      switch(bruker_svar) {
        case "1":
          skrivAlt();
          break;
        case "2":
          underMeny();
          break;
        case "3":
          brukResept();
          break;
        case "4":
          statistikk();
          break;
        case "5":
          skrivTilFil();
          break;
        case "6":
          System.out.println("Avslutter programmet.");
          System.exit(1);
        default:
          System.out.println("Ugyldig input.");
          break;
      }
    }
    scanner.close();
  }

  //Lager en undermeny som skal være meny for å opprette nye elementer:
  private static void underMeny() {
    Scanner scanner = new Scanner(System.in);
    String bruker_svar = "";

    System.out.println("Hvilken element vil du opprette:");
    System.out.println("1: Pasient\n");
    System.out.println("2: Lege\n");
    System.out.println("3: Legemiddel\n");
    System.out.println("4: Resept\n");
    System.out.println("5: Tilbake til hovedmeny\n");

    bruker_svar = scanner.nextLine();

    //Bruker switch for å lage en meny som er i loop:
    switch(bruker_svar) {
      case "1":
        leggTilPasient();
        break;
      case "2":
        leggTilLege();
        break;
      case "3":
        leggTilLegemiddel();
        break;
      case "4":
        leggTilResept();
        break;
      case "5":
        hovedMeny();
        break;
      default:
        System.out.println("Ugyldig input.");
        break;
    }
    scanner.close();
  }

  //Legger til pasient:
  private static void leggTilPasient() {
    //Lager scanner-objekt og tom string-objekt:
    Scanner scanner = new Scanner(System.in);
    String svar1 = "";

    //Printer ut:
    System.out.println("\nDu har valgt pasient.\n");
    System.out.println("\nSkriv inn navnet til pasient:\n");
    //svar1 blir til input:
    svar1 = scanner.nextLine();

    //Lager tomme int og string variabler:
    int tall = 0;
    String svar2 = "";
    System.out.println("\nSkriv inn fodselsnr til pasient:\n");
    //svar2 blir til input:
    svar2 = scanner.nextLine();
    //verdien til svar2 (string) blir til int, men i variabelen tall:
    tall = Integer.valueOf(svar2);
    //Lager ny pasient objekt:
    Pasient pas = new Pasient(svar1, String.valueOf(tall));
    //Legger den inn i pasienter:
    pasienter.leggTil(pas);
    System.out.println("Pasient: " + svar1 + " m/ fodselstall: " + tall + " er registrert.");
    hovedMeny();

    scanner.close();
  }

  //Legger til lege:
  private static void leggTilLege() {
    //Samme prosess her akkurat som leggTilPasient():
    Scanner scanner = new Scanner(System.in);
    String svar1 = "";

    //Samme prosess her akkurat som leggTilPasient():
    System.out.println("\nDu har valgt lege.\n");
    System.out.println("\nSkriv inn navn til lege:\n");
    svar1 = scanner.nextLine();

    //Samme prosess her akkurat som leggTilPasient():
    int tall = 0;
    String svar2 = "";
    System.out.println("\nSkriv inn kontrollid (0 hvis vanlig lege)\n");
    svar2 = scanner.nextLine();
    tall = Integer.valueOf(svar2);

    //Hvis svar2 har inputten 0 i seg (vanlig lege):
    if (Integer.valueOf(svar2) == 0) {
      //Lager Lege objekt:
      Lege lege = new Lege(svar1);
      //legger lege inn i leger:
      leger.leggTil(lege);
      System.out.println("Lege: " + svar1 + " er registrert.");
    }
    //Hvis kontrollID er stoerre enn 0: (spesialist:)
    else {
      //Samme prosess, bare at Spesialist objekt blir lagt istedenfor:
      Spesialist spes = new Spesialist(svar1, tall);
      leger.leggTil(spes);
      System.out.println("Spesialist: " + svar1 + " m/ id: " + tall + " er registrert.");
    }
    //Etter å ha blitt ferdig med å lage ny Lege: brukeren blir sendt tilbake til menyen:
    hovedMeny();
    scanner.close();
  }

  //Legger til legemiddel:
  private static void leggTilLegemiddel() {
    //Samme prosess som leggTilPasient() bare flere spoersmaal:
    Scanner scanner = new Scanner(System.in);
    String svar1 = "";

    System.out.println("\nDu har valgt legemiddel.\n");
    System.out.println("\nSkriv inn legemiddel navn:\n");
    svar1 = scanner.nextLine();

    String svar2 = "";
    //Lager en while-løkke som repeterer spørsmålet hvis brukeren svarer noe annet (while løkken er true):
    while (true) {
    System.out.println("Hvilken type legemiddel? (narkotisk, vanedannende, vanlig)");
    svar2 = scanner.nextLine().toLowerCase(); //bruker lowercase her
    //Lager en if-sjekk som sjekker hvis brukeren skriver en av disse tre ordene:
    if (svar2.equalsIgnoreCase("narkotisk") || svar2.equalsIgnoreCase("vanedannende") || svar2.equalsIgnoreCase("vanlig")) {
      break; //hopper ut av while-løkken: riktig
    }
    //Hvis brukeren skriver noe annet:
    else {
      System.out.println("Ugyldig input."); //Får feilmelding.
    }
    }

    String svar3 = "";
    Double pris = 0.0;
    System.out.println("Pris? (Skriv tall)");
    svar3 = scanner.nextLine();
    pris = Double.valueOf(svar3); //konverterer til Double her

    String svar4 = "";
    Double virkestoff = 0.0;
    System.out.println("Virkestoff? (Skriv tall)");
    svar4 = scanner.nextLine();
    virkestoff = Double.valueOf(svar4); //konverterer til double her

    String svar5 = "";
    int styrke = 0;
    System.out.println("Styrke? (Skriv tall)");
    svar5 = scanner.nextLine();
    styrke = Integer.valueOf(svar5); //konverterer til int her

    //Hvis brukeren har valgt typen narkotisk:
    if (svar2.equals("narkotisk")) {
      //Narkotisk objekt blir lagd og lagt til i legemidler:
      Narkotisk narko = new Narkotisk(svar1, pris, virkestoff, styrke);
      legemidler.leggTil(narko);
      System.out.println("\nNarkotisk legemiddel er lagt til.");
    }
    //Hvis vanedannende (samme prosess som narkotisk):
    else if (svar2.equals("vanedannende")) {
      Vanedannende vane = new Vanedannende(svar1, pris, virkestoff, styrke);
      legemidler.leggTil(vane);
      System.out.println("\nVanedannende legemiddel er lagt til.");
    }
    //Hvis vanlig:
    else if (svar2.equals("vanlig")) {
      Vanlig vanlig = new Vanlig(svar1, pris, virkestoff);
      legemidler.leggTil(vanlig);
      System.out.println("\nVanlig legemiddel er lagt til.");
    }
    //Hvis noe annet (feil):
    else {
      System.out.println("Ugyldig input av type til legemiddel.");
    }
    hovedMeny();
    scanner.close();
  }

  //Legger til resept:
  private static void leggTilResept() {
    Scanner scanner = new Scanner(System.in);
    String svar1 = "";

    Legemiddel valgtLgm = null;
    //Lager en while-løkke som skal sjekke om brukeren skriver feil, hvis feil: blir repetert det samme spm.
    while (valgtLgm == null) {
      System.out.println("\nDu har valgt resept.\n");
      System.out.println("*****\nSkriv inn legemiddelnr (velg en av dem fra 0 og oppover):\n*****");
      int tellerS = 0; //lager en int teller som har verdien 0
      //Går gjennom verdier inn i legemidler:
      for (Legemiddel i : legemidler) {
        System.out.println(tellerS +"." + i + "\n"); //printer ut alle verdiene (legemidler)
        tellerS++; //øker telleren
      }
      svar1 = scanner.nextLine();
      //Bruker try for å hente inn svar1 ved å kalle på metoden hent() via legemidler, og samtidig gjør den til int:
      try {
        valgtLgm = legemidler.hent(Integer.valueOf(svar1));
        System.out.println("\nDu har valgt: " + valgtLgm.hentNavn());
        //Hvis feil:
      } catch (Exception e) {
        System.out.println("\nFeil legemiddelnr. Prov igjen\n");
      }
    }

    //Samme prosess som Legemidler:
    Lege valgtL = null;
    while (valgtL == null) {
      String svar2 = "";
      System.out.println("*****\nSkriv inn lege navn:\n*****");
      int tellerS = 0;
      for (Lege i : leger) {
        System.out.println(tellerS + "." + i + "\n");
        tellerS++;
      }
      svar2 = scanner.nextLine();
      try {
        for (Lege j : leger) {
          if(j.hentLegeNavn().equals(svar2)) {
            valgtL = j;
          }
        }
        System.out.println("\nDu har valgt legen: " + valgtL.hentLegeNavn());
      } catch (Exception e) {
        System.out.println("\nFeil lege navn.\n");
      }
    }

    //Nesten samme prosess som de forrige, bare at int får -1 som verdi fordi
    //brukeren har ikke valgt ennå reit i før koden går gjennom while-løkken:
    String svar4 = "";
    int valgtReit = -1;
    while (valgtReit < 0) {
      //Samme prosess som de forrige:
      try {
        System.out.println("*****\nSkriv antall reit\n*****");
        svar4 = scanner.nextLine();
        valgtReit = Integer.valueOf(svar4);
      } catch (NumberFormatException i) {
        System.out.println("\nFeil. Du skal skrive tall.\n");
      }
    }

    //Samme prosess som Legemidler og lege:
    Pasient valgtPas = null;
    while (valgtPas == null) {
      String svar3 = "";
      System.out.println("*****\nSkriv inn pasient id:\n*****");
      for (Pasient p : pasienter) {
        System.out.println(p);
      }
      svar3 = scanner.nextLine();
      try {
        valgtPas = pasienter.hent(Integer.valueOf(svar3));
        System.out.println("Du har valgt pasienten: " + valgtPas.hentPasientNavn());
      } catch (Exception e) {
        System.out.println("\nFeil pasient id.\n");
      }
    }

    int valgtType = 0;
    //Lager en while-løkke som sier at brukeren kan kun velge fra 1-4, men ikke under eller over:
    while (valgtType < 1 || valgtType > 4) {
      try {
        System.out.println("*****\nHvilke type resept? (Velg fra 1 til 4)\n*****");
        System.out.println("\n1. Hvit\n");
        System.out.println("\n2. Blaa\n");
        System.out.println("\n3. Militar resept\n");
        System.out.println("\n4. Presept\n");
        valgtType = Integer.valueOf(scanner.nextLine());
        //Hvis brukeren taster noe som ikke er tall, feilmelding:
      } catch (NumberFormatException i) {
        System.out.println("Feil. Kun tall er lov.");
      }
    }

    //Bruker try for å legge til sammen alle svarene fra brukeren og gjøre dem til en resept:
    //men før det, de må velge hva slags type resept det er:
    try {
      String info = "";
      Resept resept = null;
      
      //Bruker if-setninger for å få brukeren til å velge hva slags type resept det er før
      //koden lager resepten:
      if (valgtType == 1) {
        //bruker metoden skrivHvitResept og henter inn svarene fra brukeren
        resept = valgtL.skrivHvitResept(valgtLgm, valgtPas, valgtReit);
        info = "Hvit resept";
      }
      else if (valgtType == 2) {
        resept = valgtL.skrivBlaaResept(valgtLgm, valgtPas, valgtReit);
        info = "Blaa resept";
      }
      else if (valgtType == 3) {
        resept = valgtL.skrivMResept(valgtLgm, valgtPas, valgtReit);
        info = "MResept";
      }
      else if (valgtType == 4) {
        resept = valgtL.skrivPResept(valgtLgm, valgtPas);
        info = "Presept";
      }
      //Når brukeren har valgt en type:
      //det de har valgt, svarene deres i variabelen resept blir lagt inn i resepter:
      resepter.leggTil(resept);
      System.out.println(info + " er blitt lagt til.");
      //Feilmelding:
    } catch (UlovligUtskrift e) {
      System.out.println(e);
    }
    hovedMeny();
    scanner.close();
  }

  //Lager en metode som skriver ut all informasjon:
  private static void skrivAlt() {
    System.out.println("******\nLeger:\n******");
    //bruker for-løkke for å gå gjennom leger i Lege:
    for (Lege l : leger) {
      //skriver ut alle:
      System.out.println(l + "\n--------------");
    }

    System.out.println("*******\nPasient\n*******");
    for (Pasient p : pasienter) {
      System.out.println(p + "\n--------------");
    }

    System.out.println("**********\nLegemidler\n**********");
    for (Legemiddel i : legemidler) {
      System.out.println(i + "\n--------------");
    }

    System.out.println("********\nResepter\n********");
    for (Resept r : resepter) {
      System.out.println(r + "\n--------------");
    }
  }

    //Lager en metode som gir brukeren en resept lagret hos en pasient:
  private static void brukResept() {
    Scanner scanner = new Scanner(System.in);
    String bruker_svar = "";

    System.out.println("\nHvilken pasient vil du se resepter for?\n");
    //Lager en for-løkke som går gjennom pasienter i Pasient:
    for (Pasient pasient : pasienter) {
      //skriver ut en liste av pasienter:
      System.out.println(pasient.hentId() + ": " + pasient.hentPasientNavn() + " (fnr " + pasient.hentFodselsnr() + ")");
    }
    //Gjør bruker_svar til input:
    bruker_svar = scanner.nextLine();
    Pasient pasient = pasienter.hent(Integer.parseInt(bruker_svar));
    //Når brukeren har valgt hvilken pasient:
    //skriver ut:
    System.out.println("\nValgt pasient: " + pasient.hentPasientNavn() + " (fnr " + pasient.hentFodselsnr() + ")");
    System.out.println("\nHvilken resept vil du bruke?\n");

    //lager en teller som skal handle som en liste i terminalen (altså den teller opp hvor mange resepter):
    int teller = 0;
    Stabel <Resept> reseptPasient = pasient.hentResepter();
    for (Resept resept : reseptPasient) {
      System.out.println(teller + ": " + resept.legemiddel.hentNavn() + " (" + resept.hentReit() + " reit)");
      teller++;
    }

    //Hvis telleren er 0 (altså 0 resepter):
    if (teller == 0) {
      //Skriver ut:
      System.out.println("Pasient: " + pasient.hentPasientNavn() + " har ingen utskrevne resepter.");
      return;
    }

    bruker_svar = scanner.nextLine();
    Resept resept = reseptPasient.hent(Integer.parseInt(bruker_svar));
    boolean brukt = resept.bruk();

    if (brukt) {
      System.out.println("Brukte resept paa " + resept.legemiddel.hentNavn() + "." + "Antall gjenvaerende reit: " + resept.hentReit());
    }
    //Hvis ikke brukt:
    else {
      System.out.println("Kunne ikke bruke resept paa " + resept.legemiddel.hentNavn() + " (ingen gjenvaerende reit).");
    }
  }

  //Lager en metode som skriver ut statistikk:
  private static void statistikk() {
    Scanner scanner = new Scanner(System.in);
    String svar = "";

    System.out.println("\nStatistikker:\n");
    System.out.println("1: Vanedannende");
    System.out.println("2: Narkotiske");
    System.out.println("3: Leger som har utskrevet narkotiske legemidler");
    System.out.println("4: Pasienter som har mottatt narkotiske legemidler");

    svar = scanner.nextLine();

    //Lager if-setning og sjekker hvis brukeren skrev "1" (string):
    if (svar.equals("1")) {
      //lager en teller:
      int antVane = 0;
      //Går gjennom verdier fra resepter:
      for (Resept r : resepter) {
        //hvis verdiene er vanedannende (ved å hente legemiddel ved bruk av metoden hentLegemiddel())
        if (r.hentLegemiddel() instanceof Vanedannende) {
          //telleren økes
          antVane++;
        }
      }
      //Printer ut antallet:
      System.out.println("\nUtskrevende Vanedannende legemidler: " + antVane);
      hovedMeny();
    }
    //Samme prosess bare for narkotisk:
    else if (svar.equals("2")) {
      int antNarko = 0;
      for (Resept r : resepter) {
        if (r.hentLegemiddel() instanceof Narkotisk) {
          antNarko++;
        }
      }
      System.out.println("\nUtskrevende narkotiske legemidler: " + antNarko);
      hovedMeny();
    }
    //Hvis 3:
    else if (svar.equals("3")) {
      System.out.println("Leger som har skrevet ut narkotiske midler: \n");
      //Går gjennom verdier fra leger:
      for (Lege lege : leger) {
        //teller:
        int legeNarko = 0;
        //og går gjennom utskrevende resepter fra lege via Resept:
        for (Resept resept : lege.hentUtskrevendeResepter()) {
          //hvis leger har narkotiske legemidler:
          if (resept.hentLegemiddel() instanceof Narkotisk) {
            //telleren økes:
            legeNarko++;
          }
        }
        System.out.println("Legen: " + lege.hentLegeNavn() + " har skrevet ut: " + legeNarko + " narkotiske resept(er).");
        hovedMeny();
      } 
    }
    //Samme prosess som "3", bare for pasienter:
    else if (svar.equals("4")) {
      for (Pasient pas : pasienter) {
        int pasNarko = 0;
        for (Resept res : pas.hentResepter()) {
          if (res.hentLegemiddel() instanceof Narkotisk) {
            pasNarko++;
          }
        }
        System.out.println("Pasienten: " + pas.hentPasientNavn() + " har mottatt: " + pasNarko + " narkotisk resept(er).");
        hovedMeny();
      }
    }
    scanner.close();
  }

    //Lager en metode som skriver legelisten til fil:
  private static void skrivTilFil() {
    //Lager objekter PrintWriter som er foreløpig null:
    PrintWriter prnt = null;

    //Bruker try:
    try {
      //lager en ny txt-objekt med filnavnet "export.txt":
      prnt = new PrintWriter("export.txt");
      //Hvis feil:
    } catch (Exception e) {
      System.out.println("Kunne ikke lage filen.");
      System.exit(1);
    }

    //Printer ut pasienter:
    prnt.println("# Pasienter (navn,fnr)");
    //Går gjennom verdier i pasienter:
    for (Pasient p : pasienter) {
      //printer dem ut i txt-filen:
      prnt.println(p.hentPasientNavn() + "," + p.hentFodselsnr());
    }

    //Samme prosess, bare for legemidler:
    prnt.println("# Legemidler (navn,type,pris,virkestoff,[styrke])");
    for (Legemiddel l : legemidler) {
      prnt.println(l.hentNavn() + "," + l.hentType() + "," + l.hentPris() + "," + l.hentVirkestoff() + "," + l.hentStyrke());
    }

    //Leger:
    prnt.println("# Leger (navn,kontrollid / 0 hvis vanlig lege)");
    for (Lege l : leger) {
      prnt.println(l.hentLegeNavn() + "," + l.hentKontrollID());
    }

    //Resepter:
    prnt.println("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit]");
    for (Resept r : resepter) {
      //prnt.println(r.hentLegemiddel().hentId() + "," + r.hentLege().hentLegeNavn() + "," + r.hentPasient().hentId() + "," +  + "," + r.hentReit());
      //For resepter bruker jeg if-sjekk for ulike typer resepter:
      if (r instanceof PResept) {
        prnt.println(r.hentLegemiddel().hentId() + "," + r.hentLege().hentLegeNavn() + "," + r.hentPasient().hentId() + "," + "p");
      }
      else if (r instanceof BlaaResept) {
        prnt.println(r.hentLegemiddel().hentId() + "," + r.hentLege().hentLegeNavn() + "," + r.hentPasient().hentId() + "," + "blaa," + r.hentReit());
      }
      else if (r instanceof MResept) {
        prnt.println(r.hentLegemiddel().hentId() + "," + r.hentLege().hentLegeNavn() + "," + r.hentPasient().hentId() + "," + "millitaer," + r.hentReit());
      }
      //Hvit-resept er i bunnen fordi PResept og MResept er subklasser til HvitResept:
      else if (r instanceof HvitResept) {
        prnt.println(r.hentLegemiddel().hentId() + "," + r.hentLege().hentLegeNavn() + "," + r.hentPasient().hentId() + "," + "hvit," + r.hentReit());
      }
    }
    //Lukker prnt slik at filen kan bli eksportert:
    prnt.close();
    System.out.println("\nLegesystem ble lagret i filen 'export.txt'.");
    hovedMeny();
  
  }
}

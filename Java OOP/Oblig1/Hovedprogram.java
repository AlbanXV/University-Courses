//Main-program

class Hovedprogram {
  //lager main-programmet:
  public static void main(String[] args) {
    //Lager objektet "dataklynge" som heter "abel", med
    //plass til 12 noder per Rack:
    Dataklynge abel = new Dataklynge(12);

    //lager en for-løkke som går fra 0 til 650GB:
    for (int i = 0; i < 650; i++) {
      //lager en ny Node variabel som skal inneholde:
      //64 GB minne og 1 prosessor hver:
      Node leggTil = new Node(64, 1);
      //legger til:
      abel.settInnNode(leggTil);
    }

    //lager en for-løkke som går fra 0 til 16GB:
    for (int i = 0; i < 16; i++) {
      //lager ny Node variabel med: 1024 GB minne og 2 prosessorer
      Node leggTil = new Node(1024, 2);
      abel.settInnNode(leggTil);
    }

    System.out.println("\nDataklynge abel: \n");
    //printer ut:
    //argumentene i noderMedNokMinne er erstattet med tall: (32,64 og 128 pga GB for å sjekke)
    System.out.println("Noder med minst 32GB: " + abel.noderMedNokMinne(32));
    System.out.println("Noder med minst 64GB: " + abel.noderMedNokMinne(64));
    System.out.println("Noder med minst 128GB: " + abel.noderMedNokMinne(128));

    //printer ut hvor mange antal prosessorer det er i dataklyngen "Abel" ved å kalle
    //på metoden antProsessorer():
    System.out.println("Antall prosessorer: " + abel.antProsessorer());
    //printer ut hvor mange racks det er i dataklyngen "Abel" ved å kalle
    //på metoden antRacks():
    System.out.println("Antall Rack: " + abel.antRacks());

    //ALTERNATIV KONSTRUKTØR MED FIL:

    Dataklynge filData = new Dataklynge("regneklynge.txt");

    //printer ut:
    System.out.println("\n Konstruktoer med fil:\n");
    //argumentene i noderMedNokMinne er erstattet med tall: (32,64 og 128 pga GB for å sjekke)
    System.out.println("Noder med minst 32GB: " + filData.noderMedNokMinne(32));
    System.out.println("Noder med minst 64GB: " + filData.noderMedNokMinne(64));
    System.out.println("Noder med minst 128GB: " + filData.noderMedNokMinne(128));

    //printer ut hvor mange antal prosessorer det er i dataklyngen "filData" ved å kalle
    //på metoden antProsessorer():
    System.out.println("Antall prosessorer: " + filData.antProsessorer());
    //printer ut hvor mange racks det er i dataklyngen "filData" ved å kalle
    //på metoden antRacks():
    System.out.println("Antall Rack: " + filData.antRacks());

    //Samme prosess som abel og fildata (bare nye filer denne gangen):
    Dataklynge filData2 = new Dataklynge("regneklynge3.txt");

    //printer ut:
    System.out.println("\n Konstruktoer med fil nr.2:\n");
    System.out.println("Noder med minst 32GB: " + filData2.noderMedNokMinne(32));
    System.out.println("Noder med minst 64GB: " + filData2.noderMedNokMinne(64));
    System.out.println("Noder med minst 128GB: " + filData2.noderMedNokMinne(128));

    System.out.println("Antall prosessorer: " + filData2.antProsessorer());
    System.out.println("Antall Rack: " + filData2.antRacks());

    Dataklynge filData3 = new Dataklynge("regneklynge3.txt");

    //printer ut:
    System.out.println("\n Konstruktoer med fil nr.3:\n");
    System.out.println("Noder med minst 32GB: " + filData3.noderMedNokMinne(32));
    System.out.println("Noder med minst 64GB: " + filData3.noderMedNokMinne(64));
    System.out.println("Noder med minst 128GB: " + filData3.noderMedNokMinne(128));

    System.out.println("Antall prosessorer: " + filData3.antProsessorer());
    System.out.println("Antall Rack: " + filData3.antRacks());

    Dataklynge filData4 = new Dataklynge("regneklynge4.txt");

    //printer ut:
    System.out.println("\n Konstruktoer med fil nr.4:\n");
    System.out.println("Noder med minst 32GB: " + filData4.noderMedNokMinne(32));
    System.out.println("Noder med minst 64GB: " + filData4.noderMedNokMinne(64));
    System.out.println("Noder med minst 128GB: " + filData4.noderMedNokMinne(128));

    System.out.println("Antall prosessorer: " + filData4.antProsessorer());
    System.out.println("Antall Rack: " + filData4.antRacks());
  }
}

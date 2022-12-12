
//Lager klassen Lege som implementerer grensesnitt:
class Lege implements Comparable<Lege> {
  //lager privat initvariabel
  protected String navn;
  protected Lenkeliste<Resept> utskrevedeResepter = new Lenkeliste<Resept>();

  //Lager metoden compareTo:
  public int compareTo(Lege x) {
    //returnerer i alfabetisk rekkefølge (navn):
    return navn.compareTo(x.hentLegeNavn());
  }

  //lager konstruktør med initvariabel:
  public Lege(String navn) {
    this.navn = navn;
  }

  //Lager metode som henter 0 for kontrollid (vanlig lege):
  public int hentKontrollID() {
    return 0;
  }

  //Lager metode som henter ut navnet til legen:
  public String hentLegeNavn() {
    return navn;
  }

  //Lager en metode som henter ut utskrevende resepter:
  public Lenkeliste<Resept> hentUtskrevendeResepter() {
    return utskrevedeResepter;
  }

  public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    //Bruker if setning som sjekker ved bruk av instanceof om legemiddelet er
    //narkotisk:
    if (legemiddel instanceof Narkotisk && hentKontrollID() == 0) {
      //Hvis ja:
      //henter inn UlovligUtskrift-metoden fordi en vanlig lege har ikke lov
      //å skrive ut narkotisk legemiddel:
      throw new UlovligUtskrift(this, legemiddel);
    }
    HvitResept resept = new HvitResept(legemiddel, this, pasient, reit);
    utskrevedeResepter.leggTil(resept);
    pasient.leggTilResept(resept);

    return resept;
  }

  public MResept skrivMResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    //Bruker if setning som sjekker ved bruk av instanceof om legemiddelet er
    //narkotisk:
    if (legemiddel instanceof Narkotisk && hentKontrollID() == 0) {
      //Hvis ja:
      //henter inn UlovligUtskrift-metoden fordi en vanlig lege har ikke lov
      //å skrive ut narkotisk legemiddel:
      throw new UlovligUtskrift(this, legemiddel);
    }
    MResept resept = new MResept(legemiddel, this, pasient, reit);
    utskrevedeResepter.leggTil(resept);
    pasient.leggTilResept(resept);

    return resept;
  }

  public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {
    //Bruker if setning som sjekker ved bruk av instanceof om legemiddelet er
    //narkotisk:
    if (legemiddel instanceof Narkotisk && hentKontrollID() == 0) {
      //Hvis ja:
      //henter inn UlovligUtskrift-metoden fordi en vanlig lege har ikke lov
      //å skrive ut narkotisk legemiddel:
      throw new UlovligUtskrift(this, legemiddel);
    }
    PResept resept = new PResept(legemiddel, this, pasient);
    utskrevedeResepter.leggTil(resept);
    pasient.leggTilResept(resept);

    return resept;
  }

  public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    //Bruker if setning som sjekker ved bruk av instanceof om legemiddelet er
    //narkotisk:
    if (legemiddel instanceof Narkotisk && hentKontrollID() == 0) {
      //Hvis ja:
      //henter inn UlovligUtskrift-metoden fordi en vanlig lege har ikke lov
      //å skrive ut narkotisk legemiddel:
      throw new UlovligUtskrift(this, legemiddel);
    }
    BlaaResept resept = new BlaaResept(legemiddel, this, pasient, reit);
    utskrevedeResepter.leggTil(resept);
    pasient.leggTilResept(resept);

    return resept;
  }

  //Bruker override for å gjøre "henting av Lege-informasjon" enklere:
  @Override
  public String toString() {
    return  "\nLege navn: " + navn;
  }

}

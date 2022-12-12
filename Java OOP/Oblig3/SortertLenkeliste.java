
//lager klassen SortertLenkeliste<T> som arver fra Comparable<T>
//men også fra Lenkeliste<T>:
class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T> {

  //bruker metoden leggTil som skal sette inn elementer i sortert rekkefølge
  public void leggTil(T x) {
    //lager noden "j" som er start:
    Node j = start;
    //lager ny node objekt med x som argument:
    Node ny = new Node(x);

    //hvis start er null:
    if (start == null) {
      //ny node objekt som lik start:
      start = new Node(x);
    }
    //hvis x compareTo (sammenlikning mellom innholdene i start) er mindre enn 0:
    else if (x.compareTo(start.innhold) < 0) {
      //neste av ny blir start:
      ny.neste = start;
      //deretter blir forrige av start til ny:
      start.forrige = ny;
      //så blir start til ny:
      start = ny;
    }
    //Hvis noe annet:
    else {
      //oppretter noden p som er start:
      Node p = start;
      //lager en while løkke som ser når neste av p ikke er null:
      while (p.neste != null) {
        //og hvis sammenlikningen mellom x og innholdet i neste av p er større
        //eller lik 0:
        if (x.compareTo(p.neste.innhold) >= 0) {
          //så skal p bli til neste av p:
          p = p.neste;
        }
        //hvis noe annet:
        else {
          //neste av ny blir til neste av p:
          ny.neste = p.neste;
          //neste av p blir til ny:
          p.neste = ny;
          //forrige av ny blir til p:
          ny.forrige = p;
          //forrige av neste i ny blir til ny:
          ny.neste.forrige = ny;
          //til slutt returnerer:
          return;
        }
      }
      //Hvis sammenlikningen mellom x og innholdet i p er større eller lik 0:
      if (x.compareTo(p.innhold) >= 0) {
        //så skal neste av p bli til ny:
        p.neste = ny;
        //Forrige av ny blir til p:
        ny.forrige = p;
      }
      //hvis noe annet (hvis de er mindre (<)):
      else {
        //neste av ny blir til p:
        ny.neste = p;
        //forrige av p blir til ny:
        p.forrige = ny;
      }
    }
  }

  //bruker metoden fjern() som skal det største elementet tas ut:
  public T fjern() {
    //lager noden j som er start:
    Node j = start;
    //lager noden prev som får verdien null:
    Node prev = null;

    //hvis start er lik null:
    if (start == null) {
      //sender feilmelding:
      throw new UgyldigListeIndeks(-1);
    }
    //hvis større enn null:
    else {
      //når neste av j ikke er null ved bruk av while løkke:
      while(j.neste != null) {
        //prev blir til j:
        prev = j;
        //j blir til neste av j:
        j = j.neste;
      }
      //når j er lik start:
      if (j == start) {
        //start får verdien null:
        start = null;
        //returnerer innholdet i j:
        return j.innhold;
      }
      //hvis ikke:
      else {
        //neste av prev får verdien null:
        prev.neste = null;
        //returner innholdet i j:
        return j.innhold;
      }
    }
  }

  @Override
  public void sett(int pos, T x) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void leggTil(int pos, T x) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }
}

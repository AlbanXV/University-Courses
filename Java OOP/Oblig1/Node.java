// Klassen Node

//Lager klassen Node som ligger under Rack
class Node {
  private int minne;
  private int antPros;

  //Lager konstruktør minne og antPros
  public Node(int minne, int antPros) {
    this.minne = minne;
    this.antPros = antPros;
  }

  //Lager metoden antProsesser som returnerer antall prosesser
  public int antProsesser() {
    return antPros;
  }

  //Lager metoden hentMinne som returnerer minne
  public int hentMinne() {
    return minne;
  }

  //Lager metoden, type="boolean" med parameter "paakrevdMinne"
  //som sjekker om det er nok minne (true/false):
  public boolean nokMinne(int paakrevdMinne) {
    if (minne>paakrevdMinne) {
      return true;
    }
    else {
      return false;
    }
  }
}

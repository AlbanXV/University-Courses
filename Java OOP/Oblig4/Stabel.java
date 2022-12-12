
class Stabel<T> extends Lenkeliste<T> {

  //lager metode som skal legge til elementer fra slutten av listen:
  public void leggPaa(T x) {
    //kaller på metoden leggTil med argumentet "x":
    leggTil(x);
  }

  //lager metode som skal fjerne elementer fra slutten av listen:
  public T taAv() {
    //lager int-variabelen str som er metoden stoerrelse():
    int str = stoerrelse();
    //returnerer ved å kalle på fjern metoden:
    //skriver inn i metoden fjern: fjern(str - 1) der:
    //str er metoden stoerrelse, mens -1 er for posisjonen til siste element:
    return fjern(str - 1);
  }
}

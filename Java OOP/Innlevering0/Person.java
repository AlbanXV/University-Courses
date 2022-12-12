//Importerer liste:
import java.util.ArrayList;
// Innlevering 0
// 3b)

//Lager klassen Person:
class Person {
  //Skriver inn to instansvariabler som private:
  private String eier;
  private ArrayList<String> list;

  //Lager en public metode der konstruktøren er Person
  //Parameteret er "eier":
  public Person(String eier) {
    this.eier = eier;
    //Legger til en tom liste:
    list = new ArrayList<String>();

  }

  //Lager metode som returnerer "eier" (henter):
  public String hentEier(){
    return eier;
  }

  //Lager en metode som legger til bil eieren har:
  public String registrerBil(String bil) {
    list.add(bil);
  }

  //Lager en metode som går gjennom en for-løkke:
  //Printer ut bil eieren har:
  public void skrivUt() {
    for (String bil: this.list) {
      System.out.println(bil.hentNummer());
    }
  }
}


/*
//Lager klassen Person:
public class Person {
  //Lager metoden eierB med parameter eier (string)
  public void eierB(String eier) {
    //Printer ut:
    System.out.println("Eier: " + eier);
  }

//  private Bil3 Bil = new Bil3();

  //Lager enda en metode: skrivUt() med parameter bilnr
  public void skrivUt(String bilnr) {
    Bil3 Bil = new Bil3();
    Bil.hentNummer(bilnr);
  }

}
*/

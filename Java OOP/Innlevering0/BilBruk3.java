// Innlevering 0
// 3c)

//Lager klasse:
class BilBruk3 {
  //Lager main-metode med arguments:
  public static void main(String[] args) {

    //Lager bil3 objekt:
    Bil3 b1 = new Bil3("AE86342BS");

    //Lager Person objekt
    Person p1 = new Person("Eren");

    p1.registrerBil(b1);
    p1.skrivUt();

  }
}

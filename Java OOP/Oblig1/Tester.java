
//Fil for å teste java-programmer:

class Tester {
  private int ass;

  public Tester(int ass) {
    this.ass = ass;
  }

  public boolean bla(int b){
    if (ass > b){
      return true;
    }
    else {
      return false;
    }
  }

  public static void main(String[] args) {

    Tester a1 = new Tester(2);

    System.out.println(a1.bla(1));

  }
}

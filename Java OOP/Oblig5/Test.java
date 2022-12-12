import java.io.*;

class Test {
    public static void main(String[] args) {
        
        try {
            //Lager File-objekt og henter inn filen "2.in":
            File fil = new File("2.in");
            //Lager Labyrint objektet og kaller inn på metoden lesFraFil som leser filen:
            Labyrint lab = Labyrint.lesFraFil(fil);
            //Printer ut objektet lab:
            System.out.println(lab);

            //Kaller inn på metoden finnUtVeiFra og legger til argumenter (x,y):
            lab.finnUtveiFra(1,1);
        }

        //Hvis filen blir ikke funnet, får feilmelding:
        catch(FileNotFoundException e) {
            System.out.println("Nope. Ikke funnet.");
        }
    }
}
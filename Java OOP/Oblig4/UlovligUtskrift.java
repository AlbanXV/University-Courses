public class UlovligUtskrift extends Exception{
    public UlovligUtskrift(Lege lege, Legemiddel legemiddel){
        super("Legen " + lege.hentLegeNavn() + "har ikke lov til å skrive ut" + legemiddel.hentNavn());
    }
}

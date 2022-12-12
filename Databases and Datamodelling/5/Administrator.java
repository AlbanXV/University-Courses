import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//Lager klasse:
public class Administrator {
    public static void main(String[] args) {
        String dbname = "albanba"; //UiO-brukernavn
        String user = "albanba_priv"; //database _priv bruker
        String pwd = "ion4Geisha"; // _priv passord

        //Connection info:
        String connectionStr =
            "user=" + user + "&" +
            "password=" + pwd + "";
            //"port=5432";

        String host = "jdbc:postgresql://dbpg-ifi-kurs01.uio.no"; 
        String connectionURL = 
            host + "/" + dbname +
            "?sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory&" +
            connectionStr;

        try {
            //Laster inn driver for postgreSQL:
            Class.forName("org.postgresql.Driver");
        
            //Lager en connection (tilkobling) til databasen:
            Connection connection = DriverManager.getConnection(host + "/" + dbname
                    + "?" + connectionStr);

            String username = null;

            //Lager en while-loekke som skal opprette en meny, der den er i loop helt til brukeren svarer gyldig:
            while (username == null) {
                //Printer ut info:
                System.out.println("-- ADMINISTRATOR --");
                System.out.println("Please choose an option:\n 1. Create bills\n2. Insert new product\n3. Exit");

                //Lager en int variabel som bruker metoden "hentIntFraBruker" som skal hente brukerens input:
                int bruker_input = getIntFromUser("Option", false);

                //Lager if-setninger som sjekker hvis brukeren taster en av de gyldige valgene:
                if (bruker_input == 1) {
                    makeBills(connection); //kaller på metoden som oppretter ny regning
                }
                else if (bruker_input == 2) {
                    insertProducts(connection); //Kaller på metoden som setter inn nye produkter
                }
                else if (bruker_input == 3) {
                    return; //Avslutter program
                }

            }
        //Slutten av try: bruker catch som skal printe ut feilmelding hvis noe gaar
        //ikke som det skal:
        } catch (SQLException|ClassNotFoundException ex) {
            System.err.println("Error encountered: " + ex.getMessage());
        } 
    }

    //OPPGAVE 2
    //Lager metode med parameter:
    private static void makeBills(Connection connection) throws SQLException {
        //Lager variabler:
        String navn = "";
        String addresse = "";
        float pris = 0;

        String username = getStrFromUser("Username: ");
        //Lager sql-spoerringer:
        String uten_brukernavn = 
            "SELECT u.name, u.address, SUM(ord.num + prod.price) AS total FROM ws.users AS u" +
            "INNER JOIN ws.orders AS ord ON u.uid = ord.uid" +
            "INNER JOIN ws.products AS prod ON ord.pid = prod.pid" +
            "WHERE payed = 0" +
            "GROUP BY (u.name, u.address);"; 
        String med_brukernavn =
            "SELECT u.name, u.address, SUM(ord.num + prod.price) AS total FROM ws.users AS u" +
            "INNER JOIN ws.orders AS ord ON u.uid = ord.uid" +
            "INNER JOIN ws.products as prod ON ord.pid = prod.pid" +
            "WHERE payed = 0 AND USERNAME = ?" + //Legger til 'AND USERNAME ?' som skal ogsaa soeke etter brukernavn hvis brukeren har skrevet der '?' blir erstatted med brukernavnet
            "GROUP BY (u.name, u.address);";

        //Lager en preparedstatement:
        PreparedStatement statement = connection.prepareStatement(uten_brukernavn);

        //Lager for-loekke:
        //Hvis brukeren taster ingen brukernavn:
        if (username.isEmpty()) {
            //Kjoerer forste spoerringen som ikke leter etter brukernavn:
            statement = connection.prepareStatement(uten_brukernavn);
        }
        //Hvis brukeren taster en brukernavn:
        else {
            //Kjoerer andre spoerring som ogsaa leter etter brukernavn:
            statement = connection.prepareStatement(med_brukernavn);
            statement.setString(1, username);
        }
        //Vi kan execute query-en ved aa bruke executeQuery():
        ResultSet rows = statement.executeQuery();

        //Lager en while-loekke som skal gaa gjennom regninger (gjennom neste rows):
        while (rows.next()) {
            //Henter dem fra sql-spoerringen (u.name, u.address, total fra ws.users)
            navn = rows.getString("name");
            addresse = rows.getString("address");
            pris = rows.getFloat("total");
            //Printer ut regning:
            System.out.println("\n--Bill--\n");
            System.out.println("Name: " + navn);
            System.out.println("\nAddress: " + addresse);
            System.out.println("\nTotal due: " + pris);
        }

    }

    //OPPGAVE 3
    //Lager metode med parameter:
    private static void insertProducts(Connection connection) throws SQLException {
        //Skriver ut:
        System.out.println("-- INSERT NEW PRODUCT --");

        //Lager en sql-spoerring som skal inserte produkt som brukeren har inputta:
        String insert =
        "INSERT INTO ws.products (name, price, cid, description)" +
        "VALUES (?, ?, (SELECT cid FROM ws.categories as cat WHERE cat.cid = ?)," + //velger cid fra ws.categories og velger hva slags type produkt det er
        "?);"; // ? blir erstattet med hva brukeren har inputta

        PreparedStatement statement = connection.prepareStatement(insert);

        //Lager String variabel for prod. navn som skal hente brukerens input m/ tekst/melding:
        String prodNavn = getStrFromUser("Product Name: ");

        //Lager String variabel for prod. pris som skal hente brukerens input m/ tekst/melding:
        String prodPris = getStrFromUser("Price: ");

        //Lager variabler som skal være en duplikat (de skal bli brukt for statement):
        String navn = new String(prodNavn);
        float pris = Float.parseFloat(prodPris); //Konvertering fra string til float

        //Skriver ut kategori meny etter navn og pris:
        System.out.println("-- SELECT CATEGORY --");
        System.out.println("1. Food");
        System.out.println("2. Electronic");
        System.out.println("3. Clothing");
        System.out.println("4. Games\n");

        //Lager String variabel for prod. kategori (...):
        String prodKat = getStrFromUser("Category: ");
        
        //Lager standalone int variabel til prodKat:
        Integer kat = Integer.parseInt(prodKat); //Konvertering fra string til int

        //Lager String variabel for prod. beskrivelse (...):
        String prodBesk = getStrFromUser("Description: ");

        statement.setString(1, navn);
        statement.setFloat(2, pris);
        statement.setInt(3, kat);
        statement.setString(4, prodBesk); 
        //bruker executeUpdate() som kjoerer SQL-spoerringen som inneholder INSERT-statement:
        statement.executeUpdate();
        //Skriver ut melding at produktet er lagt til:
        System.out.println("New Product " + navn + " inserted.");

        //Lager en sql-spoerring som skal hente produktet som brukeren har lagt til:
        String hentProdukt =
        "SELECT prod.name, prod.price, cat.name, prod.description from ws.products AS prod" +
        "INNER JOIN ws.categories AS cat ON prod.cid = cat.cid" +
        "WHERE prod.name LIKE" + navn + ";";

        statement = connection.prepareStatement(hentProdukt);

        ResultSet rows = statement.executeQuery();

        //Skriver ut ved while-loekke og bruke rows (ResultSet):
        //Den gaar gjennom tabellen fra spoerringen:
        while (rows.next()) {
            System.out.println("-- PRODUCT ADDED --");
            System.out.println("Name: " + rows.getString(1));
            System.out.println("Price: " + rows.getFloat(2));
            System.out.println("Category: " + rows.getString(3));
            System.out.println("Description: " + rows.getString(4));
        }


    }

    //METODENE getIntFromUser og getStrFromUser er hentet fra UserFrontend.java
    //og forklarer hva de gjør:

    /**
     * Utility method that gets an int as input from user
     * Prints the argument message before getting input
     * If second argument is true, the user does not need to give input and can leave
     * the field blank (resulting in a null)
     */
    private static Integer getIntFromUser(String message, boolean canBeBlank) {
        while (true) {
            String str = getStrFromUser(message);
            if (str.equals("") && canBeBlank) {
                return null;
            }
            try {
                return Integer.valueOf(str);
            } catch (NumberFormatException ex) {
                System.out.println("Please provide an integer or leave blank.");
            }
        }
    }

    /**
     * Utility method that gets a String as input from user
     * Prints the argument message before getting input
     */
    private static String getStrFromUser(String message) {
        Scanner s = new Scanner(System.in);
        System.out.print(message);
        return s.nextLine();
    }
}
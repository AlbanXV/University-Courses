
//Importerer:
import java.io.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

//Lager klasse:
public class GUI extends Application {
    private static Labyrint labyrint;
    private Rute[][] ruter;
    private int antRader;
    private int antKolonner;
    private Lenkeliste<String> exits;

    private int lengde = 450;
    private int hoyde = 400;
    private int teller;

    private Scene hoved;
    private GridPane grid = new GridPane();
    private File fil;
    private Button next;
   // private Pane[][] lab;
    private Text info;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    /**
    * Konverterer losning-String fra oblig 5 til en boolean[][]-representasjon
    * av losningstien.
    * @param losningString String-representasjon av utveien
    * @param bredde        bredde til labyrinten
    * @param hoyde         hoyde til labyrinten
    * @return              2D-representasjon av rutene der true indikerer at
    *                      ruten er en del av utveien.
    */
    static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
        boolean[][] losning = new boolean[hoyde][bredde];
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
        java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
        while (m.find()) {
            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));
            losning[y][x] = true;
        }
        return losning;
    }

    public void labyrintGUI() {
        //Fjerner hvis brukeren skal velge flere filer slik at
        //filene ikke kolliderer med hverandre:
        grid.getChildren().clear();

        labyrint = null;
        //Leser inn filen:
        try {
            labyrint = Labyrint.lesFraFil(fil);
        } catch (FileNotFoundException e) {
            System.out.println("Feil: Filen kunne ikke leses.");
        }

        //Lager to variabler som henter inn rader og kolonner fra Labyrint.java
        int rad = labyrint.hentRader();
        int kol = labyrint.hentKolonner();
        teller = 1;
        //Samme her, bare henter rutene:
        ruter = labyrint.hentRute();

        //Lager for-løkke som går gjennom rader og kolonner:
        for (int i = 0; i < ruter.length; i++) {
            for (int j = 0; j < ruter.length; j++) {
                //Hvis tegnet i ruten er '.':
                if (ruter[i][j].tilTegn() == '.') {
                    //Lager 3 final variabler:
                    final int kolonne = i;
                    final int rader = j;
                    final Labyrint lab = labyrint;

                    //Lager en rektangel som skal illustrere rutene i labyrinten:
                    Rectangle labRute = new Rectangle(0,0,28,28);
                    //Gir fargen hvit til veiene '.':
                    labRute.setFill(Color.WHITE);
                    //Gir kantene til '.' fargen svart
                    labRute.setStroke(Color.BLACK);
                    //Og stoerrelsen til den gir jeg 0.5 (tynne)
                    labRute.setStrokeWidth(0.5);

                    //Jeg gir stage-et en custom hoyde og lengde:
                    stage.setHeight(rad * 28 + 224);
                    stage.setWidth(kol * 28 + 320);

                    //Gir musepekeren en event:
                    labRute.setOnMousePressed(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
                            //
                            exits = (Lenkeliste<String>) lab.finnUtveiFra(kol, rad);
                            int teller = exits.stoerrelse();
                            String rutV = exits.fjern();

                            //Lager en bool variabel som bruker parametrene rutv, kol og rad
                            //og sender dem til funksjonen losningStringTilTabell():
                            boolean[][] losning = losningStringTilTabell(rutV, kol, rad);

                            //Skriver ut utveier:
                            info.setText("\nUtvei funnet: " + kol + "," + rad + "\n");

                            //Bruker for-løkker for å gå gjennom rad og kolonner i ruten:
                            for (int i = 0; i < losning.length; i++) {
                                for (int j = 0; j < losning[i].length; j++) {
                                    if (losning[i][j]) {
                                        //Lager igjen samme rute-firkant, men denne gangen det gjelder
                                        //for hvor musen peker i hvite-ruten:
                                        Rectangle labRute = new Rectangle(0,0,28,28);
                                        //Ruten musen peker på, blir blå:
                                        labRute.setFill(Color.BLUE);
                                        labRute.setStroke(Color.BLACK);
                                        labRute.setStrokeWidth(0.5);
                                        //legger inn i grid:
                                        grid.add(labRute, j, i);
                                    }
                                }
                            }
							
						}
                    });

                    //legger labruten (tegningen av rutene), j og i (altså kol og radene)
                    //inn i grid:
                    grid.add(labRute,j,i);
                }
                //Hvis tegnet ikke er '.' (altså '#'):
                else {
                    //Lager en rektangel, har akkurat samme parametre som forrige labRute:
                    Rectangle labRute = new Rectangle(0,0,28,28);
                    //Gir rutene fargen svart (fordi # er veggen);
                    labRute.setFill(Color.BLACK);
                    //gir kanten av rutene fargen svart også
                    labRute.setStroke(Color.BLACK);
                    //og kantene gir jeg stoerrelsen 0.5 (tynne)
                    labRute.setStrokeWidth(0.5);
                    //legger dem inni grid:
                    grid.add(labRute,j,i);
                }
            }
        }
        //utenfor for-løkken:
        //jeg fikser på mellomrom til grid
        grid.setPadding(new Insets(0,25,25,25));
        //jeg gir grid posisjonen i midten bottom slik at labyrinten vises under Buttons:
        grid.setAlignment(Pos.BOTTOM_CENTER);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Gir GUI et navn:
        primaryStage.setTitle("IN1010 - Oblig7");
        // Lager en HBox:
        HBox top = new HBox();
        // Bruker padding og spacing for å lage mellomrom
        // mellom vinduet og innholdene:
        top.setPadding(new Insets(15, 12, 15, 12));
        top.setSpacing(10);
        top.setAlignment(Pos.CENTER);

        info = new Text();

        // Lager tre (3) knapper som ikke har en funksjon enda (ikke interaktive enda):
        Button startL = new Button("Vis Labyrint");
        Button lesL = new Button("Velg Labyrint");
        Button stoppL = new Button("Avslutt");

        //Legger til knappene inni HBoxen (top)
        top.getChildren().addAll(lesL,startL,stoppL);

        //Lager en VBox:
        VBox vbox = new VBox();
        //Bruker padding for mellomrom:
        vbox.setPadding(new Insets(15,15,15,15));
        //Henter inn HBox (top) + Text (info) og til slutt GridPane (grid):
        vbox.getChildren().addAll(top,info, grid);

        /*
         * Alternativ metode: finnL.setOnAction(new EventHandler<ActionEvent>(){
         * 
         * @Override public void handle(ActionEvent event) { FileChooser velgFil = new
         * FileChooser(); FileChooser.ExtensionFilter filter = new
         * FileChooser.ExtensionFilter(".in Filer","*.in");
         * velgFil.getExtensionFilters().add(filter); } });
         */

        // Gir knappen finnL en funksjon:
        lesL.setOnAction((e) -> {
            // Lager en FileChooser objekt
            FileChooser velgFil = new FileChooser();
            // Lager filter, bestemmer hva slags fil som støttes:
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(".in Filer", "*.in");
            // Legger til filteret i velgFil:
            velgFil.getExtensionFilters().add(filter);
            // Åpner dialog for å velge fil:
            fil = velgFil.showOpenDialog(null);
            
            //printer ut i terminalen hvilken fil som er blitt valgt:
            System.out.println(fil);

            //Lager en if-setning som sjekker hvis en fil er blitt valgt:
            if (velgFil != null) {
                //Hvis ja:
                //Text info som var tom blir gitt en tekst om hvilken fil som er blitt
                //valgt:
                info.setText("\nValgt fil: " + fil + "\n");
            }

            
        });

        //Kaller på metoden labyrintGUI() når brukeren klikker på "Vis Labyrint":
        startL.setOnAction(e -> labyrintGUI());
        
        //Gir stoppL-knappen en funksjon:
        //avslutter programmet når knappen trykkes:
		stoppL.setOnAction(e -> Platform.exit());

        //Lager scene og starter staget:
        hoved = new Scene(vbox,350,100);
        primaryStage.setScene(hoved);
        primaryStage.show();
        stage = primaryStage;
    }
}
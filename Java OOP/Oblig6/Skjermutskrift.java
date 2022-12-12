import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import java.io.*;

public class Skjermutskrift extends Application {
    //Labyrint objekt + TextArea objekt (istedenfor system.out.println, textarea blir brukt for GUI)
    Labyrint lab;
    TextArea text = new TextArea();
    @Override
    public void start(Stage primaryStage) throws Exception {
        File fil = new File("3.in");
        //Kaller metoden:
        lab = Labyrint.lesFraFil(fil);

        //Gir GUI-en et navn:
        primaryStage.setTitle("Labyrint: 3.in");
        //Lager en knapp:
        Button btn = new Button();
        //Gir knappen et navn:
        btn.setText("Skriv ut labyrint");
        //Gir knappen en funksjon slik at når
        //man klikker, så skal en "event" skje:
        btn.setOnAction(new EventHandler<ActionEvent>(){
        
            //Lager en metode for knappen når brukeren klikker på den:
            @Override
            public void handle(ActionEvent event) {
                //Printer ut labyrinten:
                System.out.println(lab);
                //Forvandler den fra system.out til TextArea
                text.setText(String.valueOf(lab));
                //Finner utveien via koord. (5,3):
                lab.finnUtveiFra(3, 5);
                
            }
        });

        //Lager tom stack:
        StackPane root = new StackPane();
        //Legger til knappen i root:
        root.getChildren().add(btn);
        //Setter en scene i primaryStage (GUI):
        //men oppretter samtidig, og gir den argumentene:
        //root, lengde, høyde:
        primaryStage.setScene(new Scene(root, 500, 500));
        //Viser fram primaryStage til slutt (GUI-en vises fram):
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
    
}
package sample;


import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Rezept {

    private Button textAnzeigen;
    private CheckBox auswahl;

    public Rezept(){
        this.textAnzeigen = new Button();
        this.auswahl = new CheckBox();
    }

    public Button getTextAnzeigen(){
        return textAnzeigen;
    }

    public void setTextAnzeigen(Button textAnzeigen){
        this.textAnzeigen = textAnzeigen;
    }

    public CheckBox getAuswahl(){
        return auswahl;
    }

    public void setAuswahl(CheckBox auswahl){
        this.auswahl = auswahl;
    }

}

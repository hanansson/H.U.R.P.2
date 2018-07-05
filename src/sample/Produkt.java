package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import java.time.LocalDate;
import java.util.Date;

public class Produkt {

    private SimpleStringProperty name;
    private SimpleStringProperty art;
    //private LocalDate datum;
    //private SimpleIntegerProperty anzahl;
    DatePicker datum;
    Spinner<Integer> anzahl;
    private CheckBox auswahl;


    public Produkt(String name, String art, Integer anzahl, LocalDate datum) {
        this.name = new SimpleStringProperty(name);
        this.art = new SimpleStringProperty(art);
        this.datum = new DatePicker();
        this.auswahl = new CheckBox();
        this.anzahl = new Spinner();
    }

    public Produkt(String name, String art, Integer anzahl) {
        this.name = new SimpleStringProperty(name);
        this.art = new SimpleStringProperty(art);
        this.auswahl = new CheckBox();
        this.anzahl = new Spinner();
    }

    public String getName(){
        return name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public String getArt(){
        return art.get();
    }

    public void setArt(String art){
        this.art.set(art);
    }

    /*public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate date) {
        this.datum = date;
    }

    public Integer getAnzahl(){
        return anzahl.get();
    }

    public void setAnzahl(Integer anzahl){
        this.anzahl.set(anzahl);
    }*/

    public DatePicker getDatum() {return datum; }

    public void setDatum(DatePicker datum) {
        this.datum = datum;
    }

    public Spinner getAnzahl(){
        return anzahl;
    }

    public void setAnzahl(Spinner anzahl){
        this.anzahl = anzahl;
    }

    public CheckBox getAuswahl(){
        return auswahl;
    }

    public void setAuswahl(CheckBox auswahl){
        this.auswahl = auswahl;
    }

}

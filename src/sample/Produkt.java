package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Produkt {

    private SimpleStringProperty name;
    private SimpleStringProperty art;
    private SimpleIntegerProperty anzahl;

    Produkt(String name, String art, Integer anzahl) {
        this.name = new SimpleStringProperty(name);
        this.art = new SimpleStringProperty(art);
        this.anzahl = new SimpleIntegerProperty(anzahl);
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

    public int getAnzahl(){
        return anzahl.get();
    }

    public void setAnzahl(int anzahl){
        this.anzahl.set(anzahl);
    }

}

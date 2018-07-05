package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Zutat {

    private SimpleStringProperty name;
    private SimpleIntegerProperty anzahl;

    public Zutat(String name, int anzahl) {
        this.name = new SimpleStringProperty(name);
        this.anzahl = new SimpleIntegerProperty(anzahl);
    }

    public String getName(){
        return name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public int getAnzahl(){
        return anzahl.get();
    }

    public void setAnzahl(int anzahl){
        this.anzahl.set(anzahl);
    }
}

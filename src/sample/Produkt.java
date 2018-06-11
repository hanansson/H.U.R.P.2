package sample;

import javafx.beans.property.SimpleStringProperty;

public class Produkt {

    private SimpleStringProperty name;
    private SimpleStringProperty art;

    Produkt(String name, String art) {
        this.name = new SimpleStringProperty(name);
        this.art = new SimpleStringProperty(art);
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


}

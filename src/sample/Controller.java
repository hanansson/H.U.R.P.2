package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public TextField nameEingabe;
    public TextField artEingabe;
    public Button hinzufuegen;
    public Button loeschen;
    public Button beenden;
    public TableView tabelle;
    JSONObject produktJ = new JSONObject();
    public ArrayList<Produkt> vorratP = new ArrayList<>();

    //final ObservableList<Produkt> vorratO = FXCollections.observableArrayList(vorratP);
    //final ObservableList<Produkt> vorratO = FXCollections.observableArrayList();
    //ObservableList nur bei einfügend der Daten notwendig. Sonst soll mit ArrayList gearbeitet werden. ObservableList speist sich aus ArrayList

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn nameRow = new TableColumn("Name");
        TableColumn artRow = new TableColumn("Art");
        tabelle.getColumns().addAll( nameRow, artRow);

        BufferedReader br = null;
        JSONParser parser = new JSONParser();

        try {

            String s;
            br = new BufferedReader(new FileReader("Vorratsliste.json"));

            while ((s = br.readLine()) != null) {

                Produkt produkt = new Produkt("Name", "Art");
                //Produkt muss in der Schleife initialisiert werden!

                try {
                    produktJ = (JSONObject) parser.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String name1 = (String) produktJ.get("name");
                String art1 = (String) produktJ.get("art");

                produkt.setName(name1);
                produkt.setArt(art1);
                vorratP.add(produkt);

            }

        } catch(IOException e){
            e.printStackTrace();
        } finally{
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorratP);
        tabelle.setItems(vorratO1);

        //Fügt Kolumnen mit Titel hinzu.

        nameRow.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Name"));
        artRow.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Art"));
    }

    public void produktHinzufuegen (ActionEvent event) throws IOException {

        Produkt produkt = new Produkt("Name", "Art");
        //Produkt muss in der Methode initialisiert werden!
        produkt.setName(nameEingabe.getText());
        produkt.setArt(artEingabe.getText());
        vorratP.add(produkt);

        //JSONObject produktJ = new JSONObject();
        String name = nameEingabe.getText();
        String art = artEingabe.getText();
        produktJ.put("name", name);
        produktJ.put("art", art);


        File f = new File("Vorratsliste.json");
        if (f.exists()) {

            FileWriter fw = new FileWriter("Vorratsliste.json", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(produktJ.toJSONString());
            bw.close();

        } else {

            FileWriter fw = new FileWriter("Vorratsliste.json", true);
            fw.write(produktJ.toJSONString());
            fw.close();

        }

        final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorratP);
        tabelle.setItems(vorratO1);

    }

}

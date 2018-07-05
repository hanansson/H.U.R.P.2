package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import sample.Produkt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EinkaufsformularController implements Initializable {

    public Button bestaetigenButton;
    public Button abbruchButton;
    public TextField nameEingabe;
    public TextField artEingabe;
    public Spinner anzahlEingabe;
    JSONObject produktJ = new JSONObject();
    Produkt produkt = new Produkt("Name", "Art", 0);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        anzahlEingabe.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99));

    }

    public void hinzufuegen (ActionEvent event) throws IOException {

        String name = nameEingabe.getText();
        String art = artEingabe.getText();
        int anzahl = (int) anzahlEingabe.getValue();
        LocalDate datum = null;
        produktJ.put("name", name);
        produktJ.put("art", art);
        produktJ.put("datum", datum);
        produktJ.put("anzahl", anzahl);


        File f = new File("Einkaufsliste.json");
        if (f.exists()) {

            FileWriter fw = new FileWriter("Einkaufsliste.json", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(produktJ.toJSONString());
            bw.newLine();
            bw.close();

        } else {

            FileWriter fw = new FileWriter("Einkaufsliste.json", true);
            fw.write(produktJ.toJSONString());
            fw.close();


        }


    }


    public void abbruch (ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}

package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    Stage stage = new Stage();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nameEingabe.setPromptText("Produktname");
        artEingabe.setPromptText("Produktart");

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

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/einkaufsliste.fxml"));
        stage.setTitle("H.U.R.P");
        stage.setScene(new Scene(root));
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    public void abbruch (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/einkaufsliste.fxml"));
        stage.setTitle("H.U.R.P");
        stage.setScene(new Scene(root));
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}


package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RezeptformularController implements Initializable {

    public Button zutatHinzufuegenButton;
    public Button abbrechenButton;
    public Button rezeptButton;
    public TextField nameEingabe;
    public TextField zutatEingabe;
    public TextArea textEingabe;
    public Spinner zutatAnzahlEingabe;
    boolean eingabe;
    JSONObject rezept = new JSONObject();
    JSONArray zutaten = new JSONArray();
    int i = 0;
    Stage stage = new Stage();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nameEingabe.setPromptText("Rezept");
        zutatEingabe.setPromptText("Zutat");
        textEingabe.setPromptText("Hier bitte das Rezept eingeben.");

        zutatAnzahlEingabe.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
    }

    public void rezeptHinzufuegen (ActionEvent event) throws IOException {

        String name = nameEingabe.getText();
        String text = textEingabe.getText();

        rezept.put("name", name);
        rezept.put("text", text);
        rezept.put("zutaten", zutaten);

        File f = new File("Rezeptliste.json");
        if (f.exists()) {

            FileWriter fw = new FileWriter("Rezeptliste.json", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(rezept.toJSONString());
            bw.newLine();
            bw.close();

        } else {

            FileWriter fw = new FileWriter("Rezeptliste.json", true);
            fw.write(rezept.toJSONString());
            fw.close();

        }

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/rezeptliste.fxml"));
        stage.setTitle("H.U.R.P");
        stage.setScene(new Scene(root));
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    public void zutatHinzufuegen (ActionEvent event) {

        String name1 = zutatEingabe.getText();
        int anzahl = (int) zutatAnzahlEingabe.getValue();

        JSONObject zutat = new JSONObject();
        zutat.put("name", name1);
        zutat.put("anzahl", anzahl);
        zutaten.add(zutat);
        /*rezept.put("zutat" + i, zutat);
        rezept.put("zutatAnzahl", i);
        i ++;*/
    }

    public void abbrechen (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/rezeptliste.fxml"));
        stage.setTitle("H.U.R.P");
        stage.setScene(new Scene(root));
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

}

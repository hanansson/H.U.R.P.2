package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sample.Produkt;
import sample.Rezept;
import java.util.Iterator;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RezeptlisteController implements Initializable {

    public Button rezeptButton;
    public Button loeschenButton;
    public Button zurueckButton;
    public TableView rezeptTabelle;
    public TableColumn nameButtonColumn = new TableColumn("");
    public TableColumn auswahlColumn = new TableColumn("");
    JSONObject rezeptInfo = new JSONObject();
    JSONObject produkt = new JSONObject();
    JSONObject produktJ = new JSONObject();


    Stage stage1 = new Stage();
    ArrayList<Rezept> rezepteSammlung = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rezeptTabelle.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameButtonColumn.setPrefWidth(50);
        auswahlColumn.setPrefWidth(5);
        rezeptTabelle.getColumns().addAll(nameButtonColumn, auswahlColumn);


        BufferedReader br = null;
        JSONParser parser = new JSONParser();
        String s;

        try {

            br = new BufferedReader(new FileReader("Rezeptliste.json"));

            while ((s = br.readLine()) != null) {

                Rezept rezept = new Rezept();

                try {
                    rezeptInfo = (JSONObject) parser.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String name = (String) rezeptInfo.get("name");
                String text = (String) rezeptInfo.get("text");
                rezept.getTextAnzeigen().setText(name);
                rezept.getTextAnzeigen().setOnMouseClicked( event -> {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/fxml/rezepttext.fxml"));
                    Parent root = null;
                    try {
                        root = (Parent) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    RezepttextController rezepttextController = loader.getController();
                    rezepttextController.setInfoText(text);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 450, 230));
                    stage.show();
                });
                rezepteSammlung.add(rezept);
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


        nameButtonColumn.setCellValueFactory(new PropertyValueFactory<Rezept, String>("textAnzeigen"));
        auswahlColumn.setCellValueFactory(new PropertyValueFactory<Rezept, String>("auswahl"));

        final ObservableList<Rezept> vorratO1 = FXCollections.observableArrayList(rezepteSammlung);
        rezeptTabelle.setItems(vorratO1);

    }

    public void hinzufuegen (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/rezeptformular.fxml"));
        stage1.setScene(new Scene(root, 480, 350));
        stage1.show();
    }

    public void loeschen (ActionEvent event){

    }

    public void zurueck (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/hauptmenu.fxml"));
        stage1.setTitle("H.U.R.P");
        stage1.setScene(new Scene(root));
        stage1.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    public void aussortieren (ActionEvent event) throws IOException {

        rezepteSammlung.clear();

        JSONParser parser = new JSONParser();
        String s;

        BufferedReader br = new BufferedReader(new FileReader("Rezeptliste.json"));

        while ((s = br.readLine()) != null) {

            Rezept rezept = new Rezept();

            try {
                rezeptInfo = (JSONObject) parser.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String name = (String) rezeptInfo.get("name");
            String text = (String) rezeptInfo.get("text");
            rezept.getTextAnzeigen().setText(name);
            rezept.getTextAnzeigen().setOnMouseClicked( event1 -> {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/fxml/rezepttext.fxml"));
                Parent root = null;
                try {
                    root = (Parent) loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                RezepttextController rezepttextController = loader.getController();
                rezepttextController.setInfoText(text);
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 450, 230));
                stage.show();
            });

            JSONArray zutaten = (JSONArray) rezeptInfo.get("zutaten");
            System.out.println(rezeptInfo.size());

            int prüfen = 0;

            for (int i = 0; i < zutaten.size(); i ++){

                BufferedReader br1 = new BufferedReader(new FileReader("Vorratsliste.json"));
                JSONObject zutat = (JSONObject) zutaten.get(i);

                while ((s = br1.readLine()) != null){
                    try {
                        produktJ = (JSONObject) parser.parse(s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    int anzahl = (int) (long) zutat.get("anzahl");
                    int anzahl1 = (int) (long) produktJ.get("anzahl");

                    if(zutat.get("name").equals(produktJ.get("name")) && anzahl <= anzahl1){

                        prüfen ++;

                    }
                }
            }

            if(prüfen == zutaten.size()){
                rezepteSammlung.add(rezept);
            }
        }

        final ObservableList<Rezept> vorratO1 = FXCollections.observableArrayList(rezepteSammlung);
        rezeptTabelle.setItems(vorratO1);

    }

}

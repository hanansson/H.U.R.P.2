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
    public TableColumn nameButtonColumn = new TableColumn("Rezepte");
    public TableColumn auswahlColumn = new TableColumn("alle");
    JSONObject rezeptInfo = new JSONObject();
    JSONObject produkt = new JSONObject();
    JSONObject produktJ = new JSONObject();

    Stage stage = new Stage();
    ArrayList<Rezept> rezepteSammlung = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nameButtonColumn.setStyle( "-fx-alignment: CENTER;");
        auswahlColumn.setStyle( "-fx-alignment: CENTER;");

        nameButtonColumn.setSortable(false);
        auswahlColumn.setSortable(false);

        rezeptTabelle.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameButtonColumn.setPrefWidth(50);
        auswahlColumn.setPrefWidth(5);
        rezeptTabelle.getColumns().addAll(nameButtonColumn, auswahlColumn);

        rezepteEinfuegen();

        nameButtonColumn.setCellValueFactory(new PropertyValueFactory<Rezept, String>("textAnzeigen"));
        auswahlColumn.setCellValueFactory(new PropertyValueFactory<Rezept, String>("auswahl"));

        final ObservableList<Rezept> vorratO1 = FXCollections.observableArrayList(rezepteSammlung);
        rezeptTabelle.setItems(vorratO1);

    }

    public void hinzufuegen (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/rezeptformular.fxml"));
        stage.setScene(new Scene(root, 480, 350));
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void loeschen (ActionEvent event) throws IOException {

        ArrayList<Rezept> rezepte = new ArrayList<>();

            for(Rezept rezept : rezepteSammlung){
                if(rezept.getAuswahl().isSelected()){
                    rezepte.add(rezept);
                }
            }

            rezepteSammlung.removeAll(rezepte);

        BufferedReader br = null;
        JSONParser parser = new JSONParser();
        ArrayList<JSONObject> rezeptAlleInfos = new ArrayList<>();
        String s;

            br = new BufferedReader(new FileReader("Rezeptliste.json"));

            while ((s = br.readLine()) != null) {

                Rezept rezept = new Rezept();

                try {
                    rezeptInfo = (JSONObject) parser.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                rezeptAlleInfos.add(rezeptInfo);
            }

        PrintWriter writer = new PrintWriter("Rezeptliste.json");
        writer.print("");
        writer.close();

        System.out.println(rezeptAlleInfos.size());
        boolean selected = false;

        ArrayList<JSONObject> rezeptnichtlöschen = new ArrayList<>();

        for(JSONObject rezeptInfo2 : rezeptAlleInfos) {
            for (Rezept rezept : rezepte) {
                if (rezeptInfo2.get("name").equals(rezept.getTextAnzeigen().getText())) {
                    selected = true;
                }
                if(selected == true){
                    break;
                }
            }
            if(selected == false) {
                rezeptnichtlöschen.add(rezeptInfo2);
            }
            selected = false;
        }

        System.out.println(rezeptnichtlöschen.size());

        for (JSONObject rezeptInfo3 : rezeptnichtlöschen) {
            FileWriter fw = new FileWriter("Rezeptliste.json");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(rezeptInfo3.toJSONString());
            bw.newLine();
            bw.close();
        }

        final ObservableList<Rezept> vorratO1 = FXCollections.observableArrayList(rezepteSammlung);
        rezeptTabelle.setItems(vorratO1);
    }

    public void zurueck (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/hauptmenu.fxml"));
        stage.setTitle("H.U.R.P");
        stage.setScene(new Scene(root));
        stage.show();
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
            //rezept.getTextAnzeigen().setText(name);
            JSONArray alleZutaten = (JSONArray) rezeptInfo.get("zutaten");
            String text1 = "";
            String zutatText;

            for (int i = 0; i < alleZutaten.size(); i ++){
                JSONObject zutat = (JSONObject) alleZutaten.get(i);
                zutatText = "\n" + zutat.get("anzahl") + "x " + zutat.get("name");
                text1 = text1 + zutatText;
            }

            rezept.getTextAnzeigen().setText(name);
            String finalText = text + "\n" + text1;
            rezept.getTextAnzeigen().setOnMouseClicked( event1 -> {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/fxml/rezepttext.fxml"));
                Parent root = null;
                try {
                    root = (Parent) loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                RezepttextController rezepttextController = loader.getController();
                rezepttextController.setInfoText(finalText);
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

    public void rezepteEinfuegen(){
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
                JSONArray alleZutaten = (JSONArray) rezeptInfo.get("zutaten");
                String text1 = "";

                String zutatText;

                for (int i = 0; i < alleZutaten.size(); i ++){
                    JSONObject zutat = (JSONObject) alleZutaten.get(i);
                    zutatText = "\n" + zutat.get("anzahl") + "x " + zutat.get("name");
                    text1 = text1 + zutatText;
                }

                rezept.getTextAnzeigen().setText(name );
                rezept.getTextAnzeigen().setPrefWidth(2000);
                //rezept.getTextAnzeigen().setStyle("-fx-background-color: transparent");
                String finalText = text + "\n" + text1;
                rezept.getTextAnzeigen().setOnMouseClicked(event -> {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/fxml/rezepttext.fxml"));
                    Parent root = null;
                    try {
                        root = (Parent) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    RezepttextController rezepttextController = loader.getController();
                    rezepttextController.setInfoText(finalText);
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
    }

}

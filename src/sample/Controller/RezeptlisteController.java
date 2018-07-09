package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RezeptlisteController implements Initializable {

    public Button rezeptButton;
    public Button loeschenButton;
    public Button zumHauptmenuButton;
    public TableView rezeptTabelle;

    public TableColumn nameButtonColumn = new TableColumn("Rezepte einsehen");
    public TableColumn auswahlColumn = new TableColumn("");
    JSONObject rezeptInfo = new JSONObject();
    JSONObject produktJ = new JSONObject();

    Stage stage = new Stage();
    ArrayList<Rezept> rezepteSammlung = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nameButtonColumn.setStyle( "-fx-alignment: CENTER;");
        nameButtonColumn.setSortable(false);

        auswahlColumn.setMinWidth(50);
        auswahlColumn.setMaxWidth(50);

        auswahlColumn.setSortable(false);
        auswahlColumn.setStyle( "-fx-alignment: CENTER;");


        rezeptTabelle.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameButtonColumn.setPrefWidth(50);
        auswahlColumn.setPrefWidth(5);
        rezeptTabelle.getColumns().addAll(nameButtonColumn, auswahlColumn);

        nameButtonColumn.setCellValueFactory(new PropertyValueFactory<Rezept, String>("textAnzeigen"));
        auswahlColumn.setCellValueFactory(new PropertyValueFactory<Rezept, String>("auswahl"));

        rezeptlisteFuellen();

        final ObservableList<Rezept> rezepteObservableList = FXCollections.observableArrayList(rezepteSammlung);
        rezeptTabelle.setItems(rezepteObservableList);

    }

    public void rezeptlisteFuellen(){
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
                    String einheit;
                    if(zutat.get("einheit") == null){
                        einheit = "x";
                    } else {
                        einheit = (String) zutat.get("einheit");
                    }
                    zutatText = "\n" + zutat.get("anzahl") + einheit + " " + zutat.get("name");
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
                    rezepttextController.rezeptNameLabel.setText(name);
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add("/sample/styling.css");
                    stage.setScene(scene);
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

    public void rezeptHinzufuegen (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/rezeptformular.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/sample/styling.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        stage = (Stage) zumHauptmenuButton.getScene().getWindow();
        stage.close();
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

                //Rezept rezept = new Rezept();

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
        //boolean selected = false;

        ArrayList<JSONObject> rezeptnichtlöschen = new ArrayList<>();
        rezeptnichtlöschen.addAll(rezeptAlleInfos);

        for(JSONObject rezeptInfo : rezeptAlleInfos) {
            for (Rezept rezept : rezepte) {
                if (rezeptInfo.get("name").equals(rezept.getTextAnzeigen().getText())) {
                    rezeptnichtlöschen.remove(rezeptInfo);
                }
            }
        }


        FileWriter fw = new FileWriter("Rezeptliste.json");
        BufferedWriter bw = new BufferedWriter(fw);

        for (JSONObject rezeptInfo : rezeptnichtlöschen) {
            bw.write(rezeptInfo.toJSONString());
            bw.newLine();
        }

        bw.close();

        final ObservableList<Rezept> rezepteObservableList = FXCollections.observableArrayList(rezepteSammlung);
        rezeptTabelle.setItems(rezepteObservableList);
    }

    public void zumHauptmenu (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/hauptmenu.fxml"));
        stage.setTitle("H.U.R.P");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/sample/styling.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        stage = (Stage) zumHauptmenuButton.getScene().getWindow();
        stage.close();

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
            JSONArray alleZutaten = (JSONArray) rezeptInfo.get("zutaten");
            String text1 = "";
            String zutatText;

            for (int i = 0; i < alleZutaten.size(); i ++){
                JSONObject zutat = (JSONObject) alleZutaten.get(i);
                String einheit;
                if(zutat.get("einheit") == null){
                    einheit = "x";
                } else {
                    einheit = (String) zutat.get("einheit");
                }
                zutatText = "\n" + zutat.get("anzahl") + einheit + " " + zutat.get("name");
                text1 = text1 + zutatText;
            }

            rezept.getTextAnzeigen().setText(name);
            rezept.getTextAnzeigen().setPrefWidth(2000);
            String finalText = text + "\n" + text1;
            //rezept.getTextAnzeigen().setStyle("-fx-background-color: transparent");
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
                rezepttextController.rezeptNameLabel.setText(name);
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/sample/styling.css");
                stage.setScene(scene);
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

        final ObservableList<Rezept> rezepteObservableList = FXCollections.observableArrayList(rezepteSammlung);
        rezeptTabelle.setItems(rezepteObservableList);
    }


}

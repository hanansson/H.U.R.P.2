package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sample.Produkt;
import sample.Quicksort;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class VorratslisteController implements Initializable {

    Quicksort quicksort = new Quicksort();

    public AnchorPane paneProduktliste;
    public TextField nameEingabe;
    public TextField artEingabe;
    public Spinner anzahlEingabe;
    public DatePicker datumEingabe;
    public ChoiceBox einheitChoiceBox;

    public Button hinzufuegenButton;
    public Button loeschenButton;
    public Button zumHauptmenuButton;

    public Button sortierenName = new Button("Name");
    public Button sortierenArt = new Button("Art");
    public Button sortierenDatum = new Button("Datum");
    public Button sortierenAnzahl = new Button("Anzahl");
    public Button sortierenEinheit = new Button("Einheit");
    public Button allesAuswählen = new Button("Auswahl");

    public TableView tabelleVorrat;

    TableColumn nameColumn = new TableColumn("");
    TableColumn artColumn = new TableColumn("");
    TableColumn ablaufDatumColumn = new TableColumn("");
    TableColumn anzahlColumn = new TableColumn("");
    TableColumn einheitColumn = new TableColumn("");
    TableColumn auswahlColumn = new TableColumn("");

    Stage stage = new Stage();

    JSONObject produktJ = new JSONObject();
    public ArrayList<Produkt> produkteAufVorratsliste = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Hier durch ist rechts keine weitere unbenutzte Kolumne/Spalte
        nameEingabe.setPromptText("Produktname");
        artEingabe.setPromptText("Produktart");
        datumEingabe.setPromptText("Ablaufdatum");

        anzahlEingabe.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000));
        anzahlEingabe.setEditable(true);
        einheitChoiceBox.setItems(FXCollections.observableArrayList(null, new Separator(), "kg", "g", new Separator(), "l", "ml"));


        ablaufDatumColumn.setPrefWidth(300);
        anzahlColumn.setMinWidth(80);
        anzahlColumn.setMaxWidth(80);
        auswahlColumn.setMinWidth(70);
        auswahlColumn.setMaxWidth(70);
        einheitColumn.setMinWidth(60);
        einheitColumn.setMaxWidth(60);

        tabelleVorrat.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Gibt der Tabelle die Spalten/Kolumnen.
        tabelleVorrat.getColumns().addAll( nameColumn, artColumn, ablaufDatumColumn, anzahlColumn, einheitColumn, auswahlColumn);

        //Fügt Kolumnen/Spalten hinzu.
        nameColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Name"));
        artColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Art"));
        anzahlColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Integer>("Anzahl"));
        einheitColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Einheit"));
        ablaufDatumColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Date>("Datum"));
        auswahlColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Auswahl"));

        //sortierenName-Button wird im Kolumnentitel eingesetzt. Dazu werden Grafikeinstellungen vorgenommen(unwichtig).
        sortierenName.setStyle("-fx-background-color: transparent");
        sortierenName.setPrefSize(nameColumn.getPrefWidth(), 2);
        nameColumn.setGraphic(sortierenName);
        nameColumn.setSortable(false);
        nameColumn.setStyle( "-fx-alignment: CENTER;");
        sortierenName.setMaxSize(2000,2000);

        sortierenName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                quicksort.namequicksort(produkteAufVorratsliste);
                final ObservableList<Produkt> produkteObservableList = FXCollections.observableArrayList(produkteAufVorratsliste);
                tabelleVorrat.setItems(produkteObservableList);
            }
        });

        //sortierenArt-Button wird im Kolumnentitel eingesetzt.
        sortierenArt.setStyle("-fx-background-color: transparent");
        sortierenArt.setPrefSize(artColumn.getPrefWidth(), 2);
        artColumn.setGraphic(sortierenArt);
        artColumn.setSortable(false);
        artColumn.setStyle( "-fx-alignment: CENTER;");
        sortierenArt.setMaxSize(2000,2000);

        sortierenArt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                quicksort.artquicksort(produkteAufVorratsliste);
                final ObservableList<Produkt> produktObservableList = FXCollections.observableArrayList(produkteAufVorratsliste);
                tabelleVorrat.setItems(produktObservableList);
            }
        });

        sortierenDatum.setStyle("-fx-background-color: transparent");
        sortierenDatum.setPrefSize(ablaufDatumColumn.getPrefWidth(), 2);
        ablaufDatumColumn.setGraphic(sortierenDatum);
        ablaufDatumColumn.setSortable(false);
        ablaufDatumColumn.setStyle( "-fx-alignment: CENTER;");
        sortierenDatum.setMaxSize(2000,2000);

        sortierenDatum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                quicksort.datequicksort(produkteAufVorratsliste);
                for(Produkt produkt : produkteAufVorratsliste) {
                    if(produkt.getDatum().getValue().equals(LocalDate.of(3000, 1, 1))){
                        produkt.getDatum().setValue(null);
                    }
                }
                final ObservableList<Produkt> produkteObservableList = FXCollections.observableArrayList(produkteAufVorratsliste);
                tabelleVorrat.setItems(produkteObservableList);
            }
        });

        //sortierenAnzahl-Button wird im Kolumnentitel eingesetzt.
        sortierenAnzahl.setStyle("-fx-background-color: transparent");
        sortierenAnzahl.setPrefSize(anzahlColumn.getPrefWidth(), 2);
        anzahlColumn.setGraphic(sortierenAnzahl);
        anzahlColumn.setSortable(false);
        anzahlColumn.setStyle( "-fx-alignment: CENTER;");
        sortierenAnzahl.setMaxSize(2000,2000);

        sortierenAnzahl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                quicksort.anzahlquicksort(produkteAufVorratsliste);
                final ObservableList<Produkt> produkteObservableList = FXCollections.observableArrayList(produkteAufVorratsliste);
                tabelleVorrat.setItems(produkteObservableList);
            }
        });

        sortierenEinheit.setStyle("-fx-background-color: transparent");
        sortierenEinheit.setPrefSize(einheitColumn.getPrefWidth(), 2);
        einheitColumn.setGraphic(sortierenEinheit);
        einheitColumn.setSortable(false);
        einheitColumn.setStyle( "-fx-alignment: CENTER;");
        sortierenEinheit.setMaxSize(2000,2000);

        sortierenEinheit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                quicksort.namequicksort(produkteAufVorratsliste);
                final ObservableList<Produkt> produkteObservableList = FXCollections.observableArrayList(produkteAufVorratsliste);
                tabelleVorrat.setItems(produkteObservableList);
            }
        });


        allesAuswählen.setStyle("-fx-background-color: transparent");
        allesAuswählen.setPrefSize(auswahlColumn.getPrefWidth(), 2);
        auswahlColumn.setGraphic(allesAuswählen);
        auswahlColumn.setSortable(false);
        auswahlColumn.setStyle( "-fx-alignment: CENTER;");
        allesAuswählen.setMaxSize(2000,2000);

        allesAuswählen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(Produkt produkt: produkteAufVorratsliste) {
                    if (produkt.getAuswahl().isSelected()){
                    }else{
                        produkt.getAuswahl().setSelected(true);
                    }
                }
            }
        });

        vorratslisteFuellen();

        //Wenn der Vorrat(ArrayList) mehr als ein Produkt enthält, dann wird mit Bubblesort sortiert und der Vorrat wird in der Tabelle sortiert ausgegeben.
        //Damit der Vorrat eingelesen werden kann, wird aus Basis der ArrayList eine ObservableList erstellt. Da javafx-Elemente nur mit ObservableLists arbeiten können.
        if(produkteAufVorratsliste.size() > 1) {
            quicksort.namequicksort(produkteAufVorratsliste);
            ArrayList<Produkt> vorrat02 = new ArrayList<>(produkteAufVorratsliste);
            final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorrat02);
            tabelleVorrat.setItems(vorratO1);
        }

        ArrayList<Produkt> vorrat02 = new ArrayList<>(produkteAufVorratsliste);
        final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorrat02);
        tabelleVorrat.setItems(vorratO1);

    }

    public void vorratslisteFuellen (){

        BufferedReader br = null;
        JSONParser parser = new JSONParser();

        try {

            String s;
            br = new BufferedReader(new FileReader("Vorratsliste.json"));

            //Wenn in der JSON-Datei eine leere Zeile ist bricht die Schleife ab.
            while ((s = br.readLine()) != null) {

                //Produkt muss in der Schleife initialisiert werden!
                Produkt produkt = new Produkt("", "", "", 0, LocalDate.of(1999, 9, 9));

                //Liest aus der Textdatei eine Zeile und speichert sie in einem JSON-Object.
                try {
                    produktJ = (JSONObject) parser.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Wadelt die Attribute aus einem JSON-Object um und speichert sie in neuen Variablen.
                //Die Variablen werden einem Objekt der Klasse Produkt hinzugefügt und dieses wird in einer ArrayList gespeichert.
                String name1 = (String) produktJ.get("name");
                String art1 = (String) produktJ.get("art");
                String einheit1 = (String) produktJ.get("einheit");
                if(produktJ.get("datum") != null) {
                    String datum1 = (String) produktJ.get("datum");
                    LocalDate datum2 = LocalDate.parse(datum1);
                    produkt.getDatum().setValue(datum2);
                } else {
                    produkt.getDatum().setValue(null);
                }
                int anzahl1 = (int) (long) produktJ.get("anzahl");
                produkt.setName(name1);
                produkt.setArt(art1);
                produkt.setEinheit(einheit1);
                produkt.getAnzahl().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, anzahl1));
                produkt.getAnzahl().setEditable(true);
                produkteAufVorratsliste.add(produkt);

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

    public void produktHinzufuegen (ActionEvent event) throws IOException {

        //neues Produkt-Objekt wird erstellt.
        Produkt produkt = new Produkt("Name", "Art","", 0,  LocalDate.of(1999,9,9));

        produkt.setName(nameEingabe.getText());
        produkt.setArt(artEingabe.getText());
        produkt.setEinheit((String) einheitChoiceBox.getValue());
        produkt.getDatum().setValue(datumEingabe.getValue());
        int anzahl2 = (int) anzahlEingabe.getValue();
        produkt.getAnzahl().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, anzahl2));

        if(nameEingabe.getText().isEmpty() || artEingabe.getText().isEmpty()){
            nameEingabe.setPromptText("Produktname benötigt");
            nameEingabe.setStyle("-fx-prompt-text-fill: red");
            artEingabe.setPromptText("Produktart benötigt");
            artEingabe.setStyle("-fx-prompt-text-fill: red");
        }else {
            produkteAufVorratsliste.add(produkt);

            //****JSONObject produktJ = new JSONObject();
            String name = nameEingabe.getText();
            String art = artEingabe.getText();
            String einheit = (String) einheitChoiceBox.getValue();
            int anzahl = (int) anzahlEingabe.getValue();
            if (produkt.getDatum().getValue() != null) {
                LocalDate datum = produkt.getDatum().getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String datum1 = datum.format(formatter);
                produktJ.put("datum", datum1);
            } else {
                produktJ.put("datum", null);
            }

            produktJ.put("name", name);
            produktJ.put("art", art);
            produktJ.put("anzahl", anzahl);
            produktJ.put("einheit", einheit);


            //Checkt ob schon eine JSON-Datei besteht. Wenn ja wird ein neues Produkt himzugefügt, wenn nicht wird ne neu Datei erzeugt und ein Produkt reingeschrieben.
            //Ist aber noch fehlerhaft!
            //??????????????????????????????????????????????????????????????????????????????????????????
            File f = new File("Vorratsliste.json");
            if (f.exists()) {

                FileWriter fw = new FileWriter("Vorratsliste.json", true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(produktJ.toJSONString());
                bw.newLine();
                bw.close();

            } else {

                FileWriter fw = new FileWriter("Vorratsliste.json", true);
                fw.write(produktJ.toJSONString());
                fw.close();

            }

            final ObservableList<Produkt> produkteObservableList = FXCollections.observableArrayList(produkteAufVorratsliste);
            tabelleVorrat.setItems(produkteObservableList);

            nameEingabe.setPromptText("Produktname");
            artEingabe.setPromptText("Produktart");
            nameEingabe.setStyle("-fx-prompt-text-fill: gray");
            artEingabe.setStyle("-fx-prompt-text-fill: gray");
        }
    }

    public void loeschen (ActionEvent event) throws IOException {

        ArrayList<Produkt> produkteAufTemporärerListe = new ArrayList<>();

        for (Produkt produkt: produkteAufVorratsliste){
            if (produkt.getAuswahl().isSelected()){
                produkteAufTemporärerListe.add(produkt);
                String name = produkt.getName();
                String art = produkt.getArt();
                String einheit = produkt.getEinheit();
                if (produkt.getDatum().getValue() != null) {
                    LocalDate datum = produkt.getDatum().getValue();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String datum1 = datum.format(formatter);
                    produktJ.put("datum", datum1);
                } else {
                    produktJ.put("datum", null);
                }
                int anzahl = (int) produkt.getAnzahl().getValue();
                produktJ.put("name", name);
                produktJ.put("art", art);
                produktJ.put("einheit", einheit);
                produktJ.put("anzahl", anzahl);
            }
        }

        produkteAufVorratsliste.removeAll(produkteAufTemporärerListe);

        PrintWriter writer = new PrintWriter("Vorratsliste.json");
        writer.print("");
        writer.close();

        for (Produkt produkt: produkteAufVorratsliste) {
            String name = produkt.getName();
            String art = produkt.getArt();
            if (produkt.getDatum().getValue() != null) {
                LocalDate datum = produkt.getDatum().getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String datum1 = datum.format(formatter);
                produktJ.put("datum", datum1);
            } else {
                produktJ.put("datum", null);
            }
            int anzahl = (int) produkt.getAnzahl().getValue();
            produktJ.put("name", name);
            produktJ.put("art", art);
            produktJ.put("anzahl", anzahl);

            FileWriter fw = new FileWriter("Vorratsliste.json");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(produktJ.toJSONString());
            bw.newLine();
            bw.close();
        }

        final ObservableList<Produkt> produkteObservableList = FXCollections.observableArrayList(produkteAufVorratsliste);
        tabelleVorrat.setItems(produkteObservableList);
    }

    public void produktInEinkaufslisteEinfuegen(ActionEvent event) throws IOException {

        for (Produkt produkt: produkteAufVorratsliste){
            if (produkt.getAuswahl().isSelected()){

                produkt.getAuswahl().setSelected(false);

                String name = produkt.getName();
                String art = produkt.getArt();
                String einheit = produkt.getEinheit();
                if (produkt.getDatum().getValue() != null) {
                    LocalDate datum = produkt.getDatum().getValue();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String datum1 = datum.format(formatter);
                    produktJ.put("datum", datum1);
                } else {
                    produktJ.put("datum", null);
                }
                int anzahl = (int) produkt.getAnzahl().getValue();
                produktJ.put("name", name);
                produktJ.put("art", art);
                produktJ.put("anzahl", anzahl);
                produktJ.put("einheit", einheit);

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

        }
    }

    public void zumHauptmenu (ActionEvent event) throws IOException {

        PrintWriter writer = new PrintWriter("Vorratsliste.json");
        writer.print("");
        writer.close();
        FileWriter fw = new FileWriter("Vorratsliste.json");
        BufferedWriter bw = new BufferedWriter(fw);

        for (Produkt produkt: produkteAufVorratsliste){
            String name = produkt.getName();
            String art = produkt.getArt();
            String einheit = produkt.getEinheit();
            if (produkt.getDatum().getValue() != null) {
                LocalDate datum = produkt.getDatum().getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String datum1 = datum.format(formatter);
                produktJ.put("datum", datum1);
            } else {
                produktJ.put("datum", null);
            }
            int anzahl = (int) produkt.getAnzahl().getValue();
            produktJ.put("name", name);
            produktJ.put("art", art);
            produktJ.put("anzahl", anzahl);
            produktJ.put("einheit", einheit);
            bw.write(produktJ.toJSONString());
            bw.newLine();

        }
        bw.close();

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/hauptmenu.fxml"));
        stage.setTitle("H.U.R.P");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/sample/styling.css");
        stage.setScene(scene);
        stage.show();
        stage = (Stage) zumHauptmenuButton.getScene().getWindow();
        stage.close();
    }
}

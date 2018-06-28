package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class VorratslisteController implements Initializable {

    Bubblesort bubblesort = new Bubblesort();

    public AnchorPane paneProduktliste;
    public TextField nameEingabe;
    public TextField artEingabe;
    public Spinner anzahlEingabe;
    public DatePicker datumEingabe;
    public Button hinzufuegen;
    public Button loeschen;
    public Button beenden;
    public Button sortierenName = new Button("Name");
    public Button sortierenArt = new Button("Art");
    public Button sortierenDatum = new Button("Datum");
    public Button sortierenAnzahl = new Button("Anzahl");
    public Button allesAuswählen = new Button("Alles");
    public Spinner anzahlerändern;
    public TableView tabelle;
    JSONObject produktJ = new JSONObject();
    public ArrayList<Produkt> vorratP = new ArrayList<>();;
    TableColumn nameColumn = new TableColumn("");
    TableColumn artColumn = new TableColumn("");
    TableColumn ablaufDatumColumn = new TableColumn("");
    TableColumn anzahlColumn = new TableColumn("");
    TableColumn auswahlColumn = new TableColumn("");
    //****Vielleicht reicht auch hier das Erstellen einer ObservableList.

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //tabelle.prefHeightProperty().isBound();
        //tabelle.prefWidthProperty().isBound();

        //Hier durch ist rechts keine weitere unbenutzte Kolumne/Spalte
        tabelle.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        anzahlEingabe.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99));

        //****Vielleicht müssen hier die TableColumns initialisiert werden.

        //Gibt der Tabelle die Spalten/Kolumnen.
        tabelle.getColumns().addAll( nameColumn, artColumn, ablaufDatumColumn, anzahlColumn, auswahlColumn);

        BufferedReader br = null;
        JSONParser parser = new JSONParser();


        try {

            String s;
            br = new BufferedReader(new FileReader("Vorratsliste.json"));

            //Wenn in der JSON-Datei eine leere Zeile ist bricht die Schleife ab.
            while ((s = br.readLine()) != null) {

                //Produkt muss in der Schleife initialisiert werden!
                Produkt produkt = new Produkt("", "", 0, LocalDate.of(1999, 9, 9));

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
                String datum1 = (String) produktJ.get("datum");
                LocalDate datum2 = LocalDate.parse(datum1);
                System.out.print(datum1);
                //LocalDate datum1 = LocalDate.of(1999, 9, 9);
                int anzahl1 = (int) (long) produktJ.get("anzahl");
                //System.out.print(datum1);
                produkt.setName(name1);
                produkt.setArt(art1);
                produkt.setDatum(datum2);
                //(System.out.print(produkt.getDatum());
                produkt.anzahl.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, anzahl1));
                vorratP.add(produkt);
                //System.out.print(produkt.getAnzahl());

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


        //Wenn der Vorrat(ArrayList) mehr als ein Produkt enthält, dann wird mit Bubblesort sortiert und der Vorrat wird in der Tabelle sortiert ausgegeben.
        //Damit der Vorrat eingelesen werden kann, wird aus Basis der ArrayList eine ObservableList erstellt. Da javafx-Elemente nur mit ObservableLists arbeiten können.
        if(vorratP.size() > 1) {
            bubblesort.namesort(vorratP);
            ArrayList<Produkt> vorrat02 = new ArrayList<>(vorratP);
            final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorrat02);
            tabelle.setItems(vorratO1);
        }

        ArrayList<Produkt> vorrat02 = new ArrayList<>(vorratP);
        final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorrat02);
        tabelle.setItems(vorratO1);

            //Fügt Kolumnen/Spalten hinzu.
            nameColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Name"));
            artColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Art"));
            anzahlColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Integer>("Anzahl"));
            ablaufDatumColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Date>("Datum"));
            auswahlColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Auswahl"));
            //testColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Integer>("Test"));

            //sortierenName-Button wird im Kolumnentitel eingesetzt. Dazu werden Grafikeinstellungen vorgenommen(unwichtig).
            //Der eigentlich Kolumnentitel, wird deaktiviert -> nameColumn.setSortable(false);
            sortierenName.setStyle("-fx-background-color: transparent");
            sortierenName.setPrefSize(nameColumn.getPrefWidth(), 2);
            nameColumn.setGraphic(sortierenName);
            nameColumn.setSortable(false);
            nameColumn.setStyle( "-fx-alignment: CENTER;");
            sortierenName.setMaxSize(2000,2000);

            //sortiereName-Button kriegt seine Funktion: Sortiert ArrayList aus Produkten mit Bubblesort-Methode.
            //Baut ObservableList auf Basis der ArrayList und füllt mit der die Tabelle.
            sortierenName.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    bubblesort.namesort(vorratP);
                    ArrayList <Produkt> vorrat02 = new ArrayList<>(vorratP);
                    final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorrat02);
                    tabelle.setItems(vorratO1);
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
                bubblesort.artsort(vorratP);
                ArrayList <Produkt> vorrat02 = new ArrayList<>(vorratP);
                final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorrat02);
                tabelle.setItems(vorratO1);
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
                bubblesort.datesort(vorratP);
                ArrayList <Produkt> vorrat02 = new ArrayList<>(vorratP);
                final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorrat02);
                tabelle.setItems(vorratO1);
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
                bubblesort.anzahlsort(vorratP);
                ArrayList <Produkt> vorrat02 = new ArrayList<>(vorratP);
                final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorrat02);
                tabelle.setItems(vorratO1);
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
                for(Produkt produktp1: vorratP) {
                    if (produktp1.getAuswahl().isSelected()){
                }else{
                        produktp1.getAuswahl().setSelected(true);
                    }
                }
            }
        });

    }

    public void produktHinzufuegen (ActionEvent event) throws IOException {

        //neues Produkt-Objekt wird erstellt.
        Produkt produkt = new Produkt("Name", "Art",0,  LocalDate.of(1999,9,9));
        //****Produkt muss in der Methode initialisiert werden!
        produkt.setName(nameEingabe.getText());
        produkt.setArt(artEingabe.getText());
        produkt.setDatum(datumEingabe.getValue());
        int anzahl2 = (int) anzahlEingabe.getValue();
        produkt.anzahl.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, anzahl2));
        vorratP.add(produkt);

        //****JSONObject produktJ = new JSONObject();
        String name = nameEingabe.getText();
        String art = artEingabe.getText();
        int anzahl = (int) anzahlEingabe.getValue();
        LocalDate datum = produkt.getDatum();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String datum1 = datum.format(formatter);
        System.out.print(datum);
        produktJ.put("name", name);
        produktJ.put("art", art);
        produktJ.put("datum", datum1);
        produktJ.put("anzahl", anzahl);


        //Checkt ob schon eine JSON-Datei besteht. Wenn ja wird ein neues Produkt himzugefügt, wenn nicht wird ne neu Datei erzeugt und ein Produkt reingeschrieben.
        //Ist aber noch fehlerhaft!
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

        final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorratP);
        tabelle.setItems(vorratO1);

    }

    //Methode wird ausgeführt wenn man den loeschen-Button anklickt.
    public void loeschen (ActionEvent event) throws IOException {

        ArrayList<Produkt> vorratP1 = new ArrayList<>();

        for (Produkt produkt: vorratP){
            if (produkt.getAuswahl().isSelected()){
                vorratP1.add(produkt);
                String name = produkt.getName();
                String art = produkt.getArt();
                LocalDate datum = produkt.getDatum();
                int anzahl = produkt.anzahl.getValue();
                produktJ.put("name", name);
                produktJ.put("art", art);
                produktJ.put("datum", datum);
                produktJ.put("anzahl", anzahl);
            }
        }

        vorratP.removeAll(vorratP1);

        PrintWriter writer = new PrintWriter("Vorratsliste.json");
        writer.print("");
        writer.close();

        for (Produkt produkt1: vorratP) {
            String name = produkt1.getName();
            String art = produkt1.getArt();
            LocalDate datum = produkt1.getDatum();
            int anzahl = produkt1.anzahl.getValue();
            produktJ.put("name", name);
            produktJ.put("art", art);
            produktJ.put("datum", datum);
            produktJ.put("anzahl", anzahl);

            FileWriter fw = new FileWriter("Vorratsliste.json");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(produktJ.toJSONString());
            bw.newLine();
            bw.close();
        }

        final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorratP);
        tabelle.setItems(vorratO1);
    }

    //Methode wird ausgeführt wenn man den beenden-Button anklickt.
    public void beenden (ActionEvent event) throws IOException {

        PrintWriter writer = new PrintWriter("Vorratsliste.json");
        writer.print("");
        writer.close();
        FileWriter fw = new FileWriter("Vorratsliste.json");
        BufferedWriter bw = new BufferedWriter(fw);

        System.out.println("****" + vorratP.size() + "****");

        for (Produkt produkt: vorratP){
            System.out.println(produkt.getAnzahl().getValue());
            String name = produkt.getName();
            String art = produkt.getArt();
            LocalDate datum = produkt.getDatum();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String datum1 = datum.format(formatter);
            int anzahl = (int) produkt.getAnzahl().getValue();
            produktJ.put("name", name);
            produktJ.put("art", art);
            produktJ.put("datum", datum1);
            produktJ.put("anzahl", anzahl);
            bw.write(produktJ.toJSONString());
            bw.newLine();

        }
        bw.close();

        System.exit(0);
    }
}

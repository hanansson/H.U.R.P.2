package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    Bubblesort bubblesort = new Bubblesort();

    public TextField nameEingabe;
    public TextField artEingabe;
    public Button hinzufuegen;
    public Button loeschen;
    public Button beenden;
    public Button sortierenName = new Button("Name");
    public Button sortierenArt = new Button("Art");
    public Button sortierenAnzahl = new Button("Anzahl");
    public Spinner anzahlerändern;
    public TableView tabelle;
    JSONObject produktJ = new JSONObject();
    public ArrayList<Produkt> vorratP = new ArrayList<>();;
    TableColumn nameColumn = new TableColumn("");
    TableColumn artColumn = new TableColumn("");
    TableColumn anzahlColumn = new TableColumn("");
    //**TableColumn auswahlColumn = new TableColumn("");
    //****Vielleicht reicht auch hier das Erstellen einer ObservableList.

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Hier durch ist rechts keine weitere unbenutzte Kolumne/Spalte
        tabelle.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //****Vielleicht müssen hier die TableColumns initialisiert werden.

        //Gibt der Tabelle die Spalten/Kolumnen.
        tabelle.getColumns().addAll( nameColumn, artColumn, anzahlColumn);

        BufferedReader br = null;
        JSONParser parser = new JSONParser();



        try {

            String s;
            br = new BufferedReader(new FileReader("Vorratsliste.json"));

            //Wenn in der JSON-Datei eine leere Zeile ist bricht die Schleife ab.
            while ((s = br.readLine()) != null) {

                //Produkt muss in der Schleife initialisiert werden!
                Produkt produkt = new Produkt("Name", "Art", 1);

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
                produkt.setName(name1);
                produkt.setArt(art1);
                //*Produkte bekommen zum testen zufällige Mengen.
                produkt.setAnzahl((int)(Math.random() * 10)+1);
                vorratP.add(produkt);
                System.out.print(produkt.getAnzahl());

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


            //Fügt Kolumnen/Spalten hinzu.
            nameColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Name"));
            artColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Art"));
            anzahlColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Integer>("Anzahl"));


        //Fügt Spinner zu jedem Produkteintrag hinzu.
        //****Die Werte aus den einzelnen Spinnerzellen müssen veränderbar sein und müssen sich irgendwie wieder in die ArrayList einlesen lassen.
            /*anzahlColumn.setCellFactory(new Callback<TableColumn<Produkt,Integer>,TableCell<Produkt,Integer>>(){
            @Override
            public TableCell<Produkt, Integer> call(TableColumn<Produkt, Integer> param) {
                TableCell<Produkt, Integer> cell = new TableCell<Produkt, Integer>(){
                    @Override
                    public void updateItem(Integer anzahl, boolean empty) {
                        if(anzahl!=null){
                            Spinner<Integer> menge = new Spinner<>();
                            menge.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, anzahl));
                            setGraphic(menge);
                        }
                    }
                };
                return cell;
            }
        });*/



            //sortierenName-Button wird im Kolumnentitel eingesetzt. Dazu werden Grafikeinstellungen vorgenommen(unwichtig).
            //Der eigentlich Kolumnentitel, wird deaktiviert -> nameColumn.setSortable(false);
            sortierenName.setStyle("-fx-background-color: transparent");
            sortierenName.setPrefSize(nameColumn.getPrefWidth(), 2);
            nameColumn.setGraphic(sortierenName);
            nameColumn.setSortable(false);
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
        sortierenArt.setMaxSize(2000,2000);


        //sortierenAnzahl-Button wird im Kolumnentitel eingesetzt.
        sortierenAnzahl.setStyle("-fx-background-color: transparent");
        sortierenAnzahl.setPrefSize(anzahlColumn.getPrefWidth(), 2);
        anzahlColumn.setGraphic(sortierenAnzahl);
        anzahlColumn.setSortable(false);
        sortierenAnzahl.setMaxSize(2000,2000);

    }

    public void produktHinzufuegen (ActionEvent event) throws IOException {

        //neues Produkt-Objekt wird erstellt. Das
        Produkt produkt = new Produkt("Name", "Art", 1);
        //****Produkt muss in der Methode initialisiert werden!
        produkt.setName(nameEingabe.getText());
        produkt.setArt(artEingabe.getText());
        produkt.setAnzahl(1);
        vorratP.add(produkt);

        //****JSONObject produktJ = new JSONObject();
        String name = nameEingabe.getText();
        String art = artEingabe.getText();
        Integer anzahl = 1;
        produktJ.put("name", name);
        produktJ.put("art", art);
        produktJ.put("anzahl", 1);


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
    public void loeschen (ActionEvent event) {

        /*tabelle.getItems().removeAll(tabelle.getSelectionModel().getSelectedItem());

        FileWriter fw;
        try {
            fw = new FileWriter("Vorratsliste.json");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    //Methode wird ausgeführt wenn man den beenden-Button anklickt.
    public void beenden (ActionEvent event) {

        /*for(int i = 0; i == vorratP.size(); i ++){
        }*/
        System.exit(0);
    }
}

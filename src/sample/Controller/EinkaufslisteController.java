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
import java.util.ResourceBundle;

public class EinkaufslisteController implements Initializable {

    public Button produkteVorratHinzufuegenButton;
    public Button hinzufuegenButton;
    public Button loeschenButton;
    public Button zumHauptmenuButton;

    public Button sortierenName = new Button("Name");
    public Button sortierenArt = new Button("Art");
    public Button sortierenAnzahl = new Button("Anzahl");
    public Button sortierenEinheit = new Button("Einheit");
    public Button allesAuswählen = new Button("Auswahl");

    public TableView tabelleEinkaufsliste;

    public TableColumn nameColumn = new TableColumn("");
    public TableColumn artColumn = new TableColumn("");
    public TableColumn anzahlColumn = new TableColumn("");
    public TableColumn einheitColumn = new TableColumn("");
    public TableColumn auswahlColumn = new TableColumn("");

    public ArrayList<Produkt> produkteAufEinkaufsliste = new ArrayList<>();
    public ArrayList<Produkt> produkteTemporärerListe = new ArrayList<>();

    Stage stage = new Stage();

    JSONParser parser = new JSONParser();
    JSONObject produktJ = new JSONObject();

    Quicksort quicksort = new Quicksort();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        anzahlColumn.setMinWidth(80);
        anzahlColumn.setMaxWidth(80);

        auswahlColumn.setMinWidth(70);
        auswahlColumn.setMaxWidth(70);

        einheitColumn.setMinWidth(60);
        einheitColumn.setMaxWidth(60);

        tabelleEinkaufsliste.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Name"));
        artColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Art"));
        anzahlColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Integer>("Anzahl"));
        einheitColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Einheit"));
        auswahlColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Auswahl"));

        tabelleEinkaufsliste.getColumns().addAll(nameColumn, artColumn, anzahlColumn, einheitColumn, auswahlColumn);

        sortierenName.setStyle("-fx-background-color: transparent");
        sortierenName.setPrefSize(nameColumn.getPrefWidth(), 2);
        nameColumn.setGraphic(sortierenName);
        nameColumn.setSortable(false);
        nameColumn.setStyle( "-fx-alignment: CENTER;");
        sortierenName.setMaxSize(2000,2000);

        sortierenName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                quicksort.namequicksort(produkteAufEinkaufsliste);
                final ObservableList<Produkt> produkteObservableList = FXCollections.observableArrayList(produkteAufEinkaufsliste);
                tabelleEinkaufsliste.setItems(produkteObservableList);
            }
        });

        sortierenArt.setStyle("-fx-background-color: transparent");
        sortierenArt.setPrefSize(artColumn.getPrefWidth(), 2);
        artColumn.setGraphic(sortierenArt);
        artColumn.setSortable(false);
        artColumn.setStyle( "-fx-alignment: CENTER;");
        sortierenArt.setMaxSize(2000,2000);

        sortierenArt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                quicksort.artquicksort(produkteAufEinkaufsliste);
                final ObservableList<Produkt> produktObservableList = FXCollections.observableArrayList(produkteAufEinkaufsliste);
                tabelleEinkaufsliste.setItems(produktObservableList);
            }
        });

        sortierenAnzahl.setStyle("-fx-background-color: transparent");
        sortierenAnzahl.setPrefSize(anzahlColumn.getPrefWidth(), 2);
        anzahlColumn.setGraphic(sortierenAnzahl);
        anzahlColumn.setSortable(false);
        anzahlColumn.setStyle( "-fx-alignment: CENTER;");
        sortierenAnzahl.setMaxSize(2000,2000);

        sortierenAnzahl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                quicksort.anzahlquicksort(produkteAufEinkaufsliste);
                final ObservableList<Produkt> produkteObservableList = FXCollections.observableArrayList(produkteAufEinkaufsliste);
                tabelleEinkaufsliste.setItems(produkteObservableList);
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
                quicksort.einheitquicksort(produkteAufEinkaufsliste);
                for(Produkt produkt : produkteAufEinkaufsliste) {
                    if(produkt.getEinheit() == "x"){
                        produkt.setEinheit(null);
                    }
                }
                final ObservableList<Produkt> produkteObservableList = FXCollections.observableArrayList(produkteAufEinkaufsliste);
                tabelleEinkaufsliste.setItems(produkteObservableList);
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
                for(Produkt produkt: produkteAufEinkaufsliste) {
                    if (produkt.getAuswahl().isSelected()){
                    }else{
                        produkt.getAuswahl().setSelected(true);
                    }
                }
            }
        });

        einkaufslisteFuellen();

        final ObservableList<Produkt> produkteObservableList = FXCollections.observableArrayList(produkteAufEinkaufsliste);
        tabelleEinkaufsliste.setItems(produkteObservableList);

    }

    public void einkaufslisteFuellen() {

        BufferedReader br1 = null;

        try {

            String s1;
            br1 = new BufferedReader(new FileReader("Einkaufsliste.json"));

            while ((s1 = br1.readLine()) != null) {

                Produkt produkt = new Produkt("", "", "", 0, LocalDate.of(1999, 9, 9));

                try {
                    produktJ = (JSONObject) parser.parse(s1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String name1 = (String) produktJ.get("name");
                String art1 = (String) produktJ.get("art");
                String einheit1 = (String) produktJ.get("einheit");
                int anzahl1 = (int) (long) produktJ.get("anzahl");
                produkt.setName(name1);
                produkt.setArt(art1);
                produkt.setEinheit(einheit1);
                produkt.getDatum().setValue(null);
                produkt.getAnzahl().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, anzahl1));
                produkt.getAnzahl().setEditable(true);
                produkteAufEinkaufsliste.add(produkt);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br1 != null) br1.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void produktHinzufuegen (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/einkaufsformular.fxml"));
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

        produkteTemporärerListe.clear();

        for (Produkt produkt: produkteAufEinkaufsliste){
            if (produkt.getAuswahl().isSelected()){
                produkteTemporärerListe.add(produkt);
                }
            }

        produkteAufEinkaufsliste.removeAll(produkteTemporärerListe);

        PrintWriter writer = new PrintWriter("Einkaufsliste.json");
        writer.print("");
        writer.close();

        for (Produkt produkt1: produkteAufEinkaufsliste) {
            String name = produkt1.getName();
            String art = produkt1.getArt();
            String einheit = produkt1.getEinheit();
            int anzahl = (int) produkt1.getAnzahl().getValue();
            produktJ.put("name", name);
            produktJ.put("art", art);
            produktJ.put("einheit", einheit);
            produktJ.put("anzahl", anzahl);
            produktJ.put("datum", null);

            FileWriter fw = new FileWriter("Einkaufsliste.json");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(produktJ.toJSONString());
            bw.newLine();
            bw.close();
        }

        final ObservableList<Produkt> produkteObservableList = FXCollections.observableArrayList(produkteAufEinkaufsliste);
        tabelleEinkaufsliste.setItems(produkteObservableList);
    }

    public void produkteVorratHinzufuegen (ActionEvent event) throws IOException {

        boolean ausgewaehlt = false;

        for (Produkt produktAufEinkaufsListe : produkteAufEinkaufsliste) {
            if (produktAufEinkaufsListe.getAuswahl().isSelected()) {
                ausgewaehlt = true;
                break;
            }
        }

        if (ausgewaehlt == true) {

            String s;
            BufferedReader br = new BufferedReader(new FileReader("Vorratsliste.json"));

            while ((s = br.readLine()) != null) {

                Produkt produktAusVorrat = new Produkt("", "", "", 0, LocalDate.of(1999, 9, 9));

                try {
                    produktJ = (JSONObject) parser.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String name1 = (String) produktJ.get("name");
                String art1 = (String) produktJ.get("art");
                String einheit1 = (String) produktJ.get("einheit");
                if (produktJ.get("datum") != null) {
                    String datum1 = (String) produktJ.get("datum");
                    LocalDate datum2 = LocalDate.parse(datum1);
                    produktAusVorrat.getDatum().setValue(datum2);
                    //System.out.println(produktJ.get("datum"));
                } else {
                    produktAusVorrat.getDatum().setValue(null);
                }
                int anzahl1 = (int) (long) produktJ.get("anzahl");
                produktAusVorrat.setName(name1);
                produktAusVorrat.setArt(art1);
                produktAusVorrat.setEinheit(einheit1);
                produktAusVorrat.getAnzahl().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, anzahl1));
                produktAusVorrat.getAnzahl().setEditable(true);

                produkteTemporärerListe.add(produktAusVorrat);

                for (Produkt produktAufEinkaufsliste : produkteAufEinkaufsliste) {
                    if (produktAufEinkaufsliste.getAuswahl().isSelected()) {
                        //&&
                        if ((produktAufEinkaufsliste.getName()).equals(produktAusVorrat.getName())) {
                            produkteTemporärerListe.remove(produktAusVorrat);

                            int anzahl = (int) produktAusVorrat.getAnzahl().getValue();
                            int eingekauft = (int) produktAufEinkaufsliste.getAnzahl().getValue();
                            int gesamtanzahl = anzahl + eingekauft;
                            //System.out.println(gesamtanzahl);
                            produktAusVorrat.getAnzahl().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, gesamtanzahl));
                            //System.out.println(produktAusVorrat.getAnzahl().getValue());

                            produkteTemporärerListe.add(produktAusVorrat);
                            produktAufEinkaufsliste.getAuswahl().setSelected(false);
                        }

                    }
                }
            }

            FileWriter fw = new FileWriter("Vorratsliste.json");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Produkt produktEingekauft : produkteAufEinkaufsliste) {
                if (produktEingekauft.getAuswahl().isSelected()) {
                    produkteTemporärerListe.add(produktEingekauft);
                    produktEingekauft.getAuswahl().setSelected(false);
                }
            }

            for (Produkt produktNeuerVorrat : produkteTemporärerListe) {

                String name = produktNeuerVorrat.getName();
                String art = produktNeuerVorrat.getArt();
                String einheit = produktNeuerVorrat.getEinheit();
                if (produktNeuerVorrat.getDatum().getValue() != null) {
                    LocalDate datum = produktNeuerVorrat.getDatum().getValue();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String datum1 = datum.format(formatter);
                    produktJ.put("datum", datum1);
                } else {
                    produktJ.put("datum", null);
                    System.out.println("test");
                }

                int anzahl = (int) produktNeuerVorrat.getAnzahl().getValue();
                produktJ.put("name", name);
                produktJ.put("art", art);
                produktJ.put("einheit", einheit);
                produktJ.put("anzahl", anzahl);

                //System.out.println(produktJ.get("name"));
                //System.out.println(produktJ.get("datum"));

                bw.write(produktJ.toJSONString());
                bw.newLine();
            }

            bw.close();

            produkteTemporärerListe.clear();

            System.out.println(produkteAufEinkaufsliste.size());

        } else {
            System.out.println("Es ist kein Produkt ausgewählt!");
        }
    }

    public void zumHauptmenu (ActionEvent event) throws IOException {

        PrintWriter writer = new PrintWriter("Einkaufsliste.json");
        writer.print("");
        writer.close();
        FileWriter fw = new FileWriter("Einkaufsliste.json");
        BufferedWriter bw = new BufferedWriter(fw);

        for (Produkt produkt: produkteAufEinkaufsliste){
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
            bw.write(produktJ.toJSONString());
            bw.newLine();
        }

        bw.close();

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
}


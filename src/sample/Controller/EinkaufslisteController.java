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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sample.Produkt;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EinkaufslisteController implements Initializable {

    public Button hinzufuegenButton;
    public Button loeschenButton;
    public TableView tabelleEinkauf;
    public ArrayList<Produkt> vorratP = new ArrayList<>();
    public ArrayList<Produkt> vorratP1 = new ArrayList<>();

    TableColumn nameColumn = new TableColumn("Name");
    TableColumn artColumn = new TableColumn("Art");
    TableColumn anzahlColumn = new TableColumn("Anzahl");
    TableColumn auswahlColumn = new TableColumn("Alles");

    Stage stage1 = new Stage();
    JSONParser parser = new JSONParser();
    JSONObject produktJ = new JSONObject();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabelleEinkauf.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //anzahlEingabe.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99));


        nameColumn.setStyle( "-fx-alignment: CENTER;");
        artColumn.setStyle( "-fx-alignment: CENTER;");
        anzahlColumn.setStyle( "-fx-alignment: CENTER;");
        auswahlColumn.setStyle( "-fx-alignment: CENTER;");

        nameColumn.setPrefWidth(100);
        artColumn.setPrefWidth(100);
        anzahlColumn.setPrefWidth(100);
        auswahlColumn.setPrefWidth(100);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Name"));
        artColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Art"));
        anzahlColumn.setCellValueFactory(new PropertyValueFactory<Produkt, Integer>("Anzahl"));
        auswahlColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Auswahl"));

        tabelleEinkauf.getColumns().addAll(nameColumn, artColumn, anzahlColumn, auswahlColumn);

        //einkaeufeEinfuegen();

        BufferedReader br1 = null;

        try {

            String s1;
            br1 = new BufferedReader(new FileReader("Einkaufsliste.json"));

            while ((s1 = br1.readLine()) != null) {

                Produkt produkt = new Produkt("", "", 0, LocalDate.of(1999, 9, 9));

                try {
                    produktJ = (JSONObject) parser.parse(s1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String name1 = (String) produktJ.get("name");
                String art1 = (String) produktJ.get("art");
                int anzahl1 = (int) (long) produktJ.get("anzahl");
                produkt.setName(name1);
                produkt.setArt(art1);
                produkt.setDatum(null);
                produkt.getAnzahl().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, anzahl1));
                vorratP.add(produkt);
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

        final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorratP);
        tabelleEinkauf.setItems(vorratO1);

    }

    public void hinzufuegen(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/einkaufsformular.fxml"));
        stage1.setScene(new Scene(root, 215, 215));
        stage1.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();


    }

    public void loeschen(ActionEvent event) throws IOException {

        ArrayList<Produkt> vorratP1 = new ArrayList<>();

        for (Produkt produkt : vorratP) {
            if (produkt.getAuswahl().isSelected()) {
                vorratP1.add(produkt);
            }
        }

        vorratP.removeAll(vorratP1);

        PrintWriter writer = new PrintWriter("Einkaufsliste.json");
        writer.print("");
        writer.close();

        for (Produkt produkt1 : vorratP) {
            String name = produkt1.getName();
            String art = produkt1.getArt();
            LocalDate datum = null;
            int anzahl = (int) produkt1.getAnzahl().getValue();
            produktJ.put("name", name);
            produktJ.put("art", art);
            produktJ.put("datum", datum);
            produktJ.put("anzahl", anzahl);

            FileWriter fw = new FileWriter("Einkaufsliste.json");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(produktJ.toJSONString());
            bw.newLine();
            bw.close();
        }

        final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorratP);
        tabelleEinkauf.setItems(vorratO1);

    }

    /*public void einkaeufeEinfuegen() {
        BufferedReader br1 = null;

        try {

            String s1;
            br1 = new BufferedReader(new FileReader("Einkaufsliste.json"));

            while ((s1 = br1.readLine()) != null) {

                Produkt produkt = new Produkt("", "", 0, LocalDate.of(1999, 9, 9));

                try {
                    produktJ = (JSONObject) parser.parse(s1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String name1 = (String) produktJ.get("name");
                String art1 = (String) produktJ.get("art");
                int anzahl1 = (int) (long) produktJ.get("anzahl");
                produkt.setName(name1);
                produkt.setArt(art1);
                produkt.setDatum(null);
                produkt.getAnzahl().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, anzahl1));
                vorratP.add(produkt);
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

        final ObservableList<Produkt> vorratO1 = FXCollections.observableArrayList(vorratP);
        tabelleEinkauf.setItems(vorratO1);

    }*/

    public void vorratHinzufuegen(ActionEvent event) throws IOException {

        String s;
        BufferedReader br = new BufferedReader(new FileReader("Vorratsliste.json"));

        while ((s = br.readLine()) != null) {

            Produkt produktVorrat = new Produkt("", "", 0, LocalDate.of(1999, 9, 9));

            try {
                produktJ = (JSONObject) parser.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String name1 = (String) produktJ.get("name");
            String art1 = (String) produktJ.get("art");
            if (produktJ.get("datum") != null) {
                String datum1 = (String) produktJ.get("datum");
                LocalDate datum2 = LocalDate.parse(datum1);
                produktVorrat.getDatum().setValue(datum2);
            } else {
                produktVorrat.getDatum().setValue(null);
            }
            int anzahl1 = (int) (long) produktJ.get("anzahl");
            produktVorrat.setName(name1);
            produktVorrat.setArt(art1);
            produktVorrat.getAnzahl().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, anzahl1));

            vorratP1.add(produktVorrat);

            for (Produkt produktEinkauf : vorratP) {
                if (produktEinkauf.getAuswahl().isSelected()) {
                    //&&
                    if ((produktEinkauf.getName()).equals(produktVorrat.getName())) {
                        vorratP1.remove(produktVorrat);

                        int anzahl = (int) produktVorrat.getAnzahl().getValue();
                        int eingekauft = (int) produktEinkauf.getAnzahl().getValue();
                        int gesamtanzahl = anzahl + eingekauft;
                        System.out.println(gesamtanzahl);
                        produktVorrat.getAnzahl().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, gesamtanzahl));
                        System.out.println(produktVorrat.getAnzahl().getValue());

                        vorratP1.add(produktVorrat);
                        produktEinkauf.getAuswahl().setSelected(false);
                    }

                }
            }
        }

        FileWriter fw = new FileWriter("Vorratsliste.json");
        BufferedWriter bw = new BufferedWriter(fw);

        //System.out.println(vorratP1.get(0).getName() + vorratP1.get(1).getName());

        for (Produkt produkt1 : vorratP) {
            if (produkt1.getAuswahl().isSelected()) {
                vorratP1.add(produkt1);
                produkt1.getAuswahl().setSelected(false);
            }
        }

        for (Produkt produkt1 : vorratP1) {

            Produkt produktVorrat = new Produkt("", "", 0, LocalDate.of(1999, 9, 9));

            String name = produkt1.getName();
            String art = produkt1.getArt();
            //macht keinen sinn.
            if (produktVorrat.getDatum().getValue() != null) {
                LocalDate datum = produkt1.getDatum().getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String datum1 = datum.format(formatter);
                produktJ.put("datum", datum1);
            } else {
                produktJ.put("datum", null);
            }

            int anzahl = (int) produkt1.getAnzahl().getValue();
            produktJ.put("name", name);
            produktJ.put("art", art);
            produktJ.put("anzahl", anzahl);

            System.out.println(produktJ.get("name"));

            bw.write(produktJ.toJSONString());
            bw.newLine();
        }

        bw.close();

        vorratP1.clear();
        vorratP.clear();

        }

    public void zurueck (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/hauptmenu.fxml"));
        stage1.setTitle("H.U.R.P");
        stage1.setScene(new Scene(root));
        stage1.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }
}


package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public TextField nameEingabe;
    public TextField artEingabe;
    public Button hinzufuegen;
    public Button loeschen;
    public Button beenden;
    public TableView tabelle;
    final ObservableList<Produkt> vorrat = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn nameRow = new TableColumn("Name");
        TableColumn artRow = new TableColumn("Art");
        tabelle.getColumns().addAll( nameRow, artRow);

        //Fügt Kolumnen mit Titel hinzu.


        nameRow.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Name"));
        artRow.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Art"));
    }

    public void produktHinzufuegen (ActionEvent event) {

        Produkt produkt = new Produkt("Name", "Art");
        produkt.setName(nameEingabe.getText());
        produkt.setArt(artEingabe.getText());
        vorrat.add(produkt);
        tabelle.setItems(vorrat);

    }

}

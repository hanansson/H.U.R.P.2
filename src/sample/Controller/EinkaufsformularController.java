package sample.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EinkaufsformularController implements Initializable {

    public Button produktHinzufuegenButton;
    public Button abbruchButton;
    public TextField nameEingabe;
    public TextField artEingabe;
    public Spinner anzahlEingabe;
    public ChoiceBox einheitChoiceBox;

    JSONObject produktJ = new JSONObject();

    Stage stage = new Stage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nameEingabe.setPromptText("Produktname");
        artEingabe.setPromptText("Produktart");

        anzahlEingabe.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000));
        anzahlEingabe.setEditable(true);
        einheitChoiceBox.setItems(FXCollections.observableArrayList("", new Separator(), "kg", "g", new Separator(), "l", "ml"));

    }

    public void produktHinzufuegen (ActionEvent event) throws IOException {

        if (nameEingabe.getText().isEmpty() || artEingabe.getText().isEmpty()) {
            nameEingabe.setPromptText("Produktname benötigt");
            nameEingabe.setStyle("-fx-prompt-text-fill: red");
            artEingabe.setPromptText("Produktart benötigt");
            artEingabe.setStyle("-fx-prompt-text-fill: red");
        } else {

            String name = nameEingabe.getText();
            String art = artEingabe.getText();
            String eimheit = (String) einheitChoiceBox.getValue();
            int anzahl = (int) anzahlEingabe.getValue();
            LocalDate datum = null;
            produktJ.put("name", name);
            produktJ.put("art", art);
            produktJ.put("einheit", eimheit);
            produktJ.put("datum", datum);
            produktJ.put("anzahl", anzahl);


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

            Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/einkaufsliste.fxml"));
            stage.setTitle("H.U.R.P");
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/sample/styling.css");
            stage.setScene(scene);
            stage.show();
            stage = (Stage) produktHinzufuegenButton.getScene().getWindow();
            stage.close();
        }
    }

    public void abbrechen (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/einkaufsliste.fxml"));
        stage.setTitle("H.U.R.P");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/sample/styling.css");
        stage.setScene(scene);
        stage.show();
        stage = (Stage) abbruchButton.getScene().getWindow();
        stage.close();
    }
}


package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HauptmenuController {

    public Button geheZuVorrat;
    public Button geheZuRezepte;
    public Button geheZuEinkaufszettel;
    public Button beenden;

    public void oeffneVorrat (ActionEvent event) throws IOException {
        Stage stage1 = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("vorratsliste.fxml"));
        stage1.setTitle("H.U.R.P");
        stage1.setScene(new Scene(root, 650, 500));
        stage1.show();
    }

    public void oeffneRezepte (ActionEvent event){
        System.out.print("test");
    }

    public void oeffneEinkaufszettel (ActionEvent event){
        System.out.print("test");
    }

    public void beenden (ActionEvent event){
        System.exit(0);
    }

}

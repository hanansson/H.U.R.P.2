package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    Stage stage1 = new Stage();


    public void oeffneVorrat(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/vorratsliste.fxml"));
        stage1.setTitle("H.U.R.P");
        stage1.setScene(new Scene(root));
        stage1.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void oeffneRezepte(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/rezeptliste.fxml"));
        stage1.setTitle("H.U.R.P");
        stage1.setScene(new Scene(root));
        stage1.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void oeffneEinkaufszettel (ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/einkaufsliste.fxml"));
        stage1.setTitle("H.U.R.P");
        stage1.setScene(new Scene(root));
        stage1.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void beenden (ActionEvent event){
        System.exit(0);
    }

}

package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HauptmenuController {

    public AnchorPane hauptmenuPane;
    public Button geheZuVorrat;
    public Button geheZuRezepte;
    public Button geheZuEinkauf;
    public Button beenden;
    Stage stage = new Stage();

    public void oeffneVorratsliste(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/vorratsliste.fxml"));
        stage.setTitle("H.U.R.P");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/sample/styling.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        stage = (Stage) geheZuVorrat.getScene().getWindow();
        stage.close();
    }

    public void oeffneRezeptliste(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/rezeptliste.fxml"));
        stage.setTitle("H.U.R.P");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/sample/styling.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        stage = (Stage) geheZuRezepte.getScene().getWindow();
        stage.close();
    }

    public void oeffneEinkaufsliste (ActionEvent event)throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/einkaufsliste.fxml"));
        stage.setTitle("H.U.R.P");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/sample/styling.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        stage = (Stage) geheZuEinkauf.getScene().getWindow();
        stage.close();
    }

    public void beenden (ActionEvent event){

        System.exit(0);
    }
}

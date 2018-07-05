package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    //Lädt die fxml Datei und macht das Programmfenster in angegebener größe sichtbar
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/hauptmenu.fxml"));
        primaryStage.setTitle("H.U.R.P");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

//Nur fürs starten verantwortlich.
    public static void main(String[] args) {
        launch(args);
    }
}

package sample.Controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RezepttextController implements Initializable {

    public TextArea infoText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setInfoText(String text){
        infoText.setText(text);
    }
}
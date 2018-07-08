package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RezepttextController implements Initializable {

    public TextArea infoText;
    public Button schließenButton;
    public Label rezeptNameLabel;

    Stage stage = new Stage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        infoText.setDisable(true);
        infoText.setOpacity(100);
        infoText.setWrapText(true);
    }

    public void setInfoText(String text){
        infoText.setText(text);
    }

    public void schließen (ActionEvent actionEvent){
        stage = (Stage) schließenButton.getScene().getWindow();
        stage.close();
    }
}
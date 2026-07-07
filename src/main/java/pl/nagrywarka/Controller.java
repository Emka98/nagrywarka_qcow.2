package pl.nagrywarka;
import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import pl.nagrywarka.discMenager.Disc;
import pl.nagrywarka.discMenager.DiscMenager;


public class Controller {

    @FXML
    private Button runButton;

    @FXML
    private ChoiceBox<String> distributionChoiceBox;

    @FXML
    private ChoiceBox<Disc> discChoiceBox;

    @FXML
    private CheckBox clearCheckBox;

    @FXML
    private CheckBox recordCheckBox;

    @FXML
    private ProgressIndicator progresBar;

    DiscMenager maganer = new DiscMenager();

    @FXML
    public void initialize() throws IOException, InterruptedException {
        clearCheckBox.setSelected(true);
        recordCheckBox.setSelected(true);
        progresBar.setProgress(0);
    }

    @FXML
    private void clikDistributionCheckBox() {

        distributionChoiceBox.getItems().clear();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik dystrybucyjny");

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Dystrybucja", "*.qcow2"),
            new FileChooser.ExtensionFilter("Wszystkie pliki", "*.*")
        );

        Window ownerWindow = distributionChoiceBox.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(ownerWindow);

        if(selectedFile != null){
           distributionChoiceBox.getItems().add(selectedFile.getName());
           distributionChoiceBox.setValue(selectedFile.getName());
        }
    }
    
    @FXML
    private void clikDiscChoiceBox() throws IOException, InterruptedException{
        discChoiceBox.getItems().setAll(maganer.creatDiscList());
    }

    @FXML
    private void handleRun() throws IOException{

    }
}




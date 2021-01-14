package cz.gjkt.view;

import cz.gjkt.application.Main;
import cz.gjkt.model.Predmet;
import cz.gjkt.model.PredmetyDAOJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import javax.swing.text.LabelView;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpravPredmetController implements Initializable {

    PredmetyController predmetyController;
    Scene predmetyScene;
    Predmet predmet;

    @FXML
    TextField nazev;
    @FXML
    TextField zkratka;
    @FXML
    TextArea popis;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void setUpravNazev(String nazev){this.nazev.setText(nazev);}
    private void setUpravZkratku(String zkratka){this.zkratka.setText(zkratka);}
    private void setUpravPopis(String popis){this.popis.setText(popis);}

    public void setUpravPredmetScene(Scene scene){predmetyScene = scene;}
    public void setUpravPredmetController(PredmetyController controller){predmetyController = controller;}

    public void setPredmet(Predmet predmet){
        this.predmet = predmet;
        setUpravNazev(predmet.getNazev());
        setUpravZkratku(predmet.getZkratka());
        setUpravPopis(predmet.getPopis());
    }

    public void handleCancelButton() {
        Main.getPrimaryStage().setScene(predmetyScene);
    }

    public void handleUlozButton(){
        PredmetyDAOJDBC predmetyDAO = new PredmetyDAOJDBC();
        predmet.setNazev(nazev.getText());
        predmet.setZkratka(zkratka.getText());
        predmet.setPopis(popis.getText());
        predmetyDAO.update(predmet);
        Main.getPrimaryStage().setScene(predmetyScene);
        predmetyController.refresh();
    }
}

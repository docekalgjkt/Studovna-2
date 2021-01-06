package cz.gjkt.view;

import cz.gjkt.application.Main;
import cz.gjkt.model.SkolniRokyDAOJDBC;
import cz.gjkt.model.SkolniRok;
import cz.gjkt.model.SkolniRok;
import cz.gjkt.model.SkolniRokyDAOJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static cz.gjkt.application.Main.getPrimaryStage;

public class SkolniRokController implements Initializable {

    @FXML
    TableView tableView;

    SkolniRokyDAOJDBC skolnirokyDao = new SkolniRokyDAOJDBC();
    List<SkolniRok> skolniroky;
    ObservableList<SkolniRok> items;
    ObservableList<SkolniRok> selectedItems = null;


    public void fillTable() {
        //skolniroky = skolnirokyDao.getAll();
        //items = FXCollections.observableList(skolniroky);
        //tableView.setItems(items);
    }

    public void initColumns() {

        TableColumn<String, SkolniRok> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<String, SkolniRok> nazevColumn = new TableColumn<>("Název");
        nazevColumn.setCellValueFactory(new PropertyValueFactory<>("nazev"));
        TableColumn<String, SkolniRok> odColumn = new TableColumn<>("Školní Rok od");
        odColumn.setCellValueFactory(new PropertyValueFactory<>("zacatek"));
        TableColumn<String, SkolniRok> doColumn = new TableColumn<>("Školní Rok do");
        doColumn.setCellValueFactory(new PropertyValueFactory<>("konec"));
        //predmetColumn.setEditable(true);

        tableView.getColumns().addAll(idColumn,nazevColumn,odColumn,doColumn);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initColumns();
        //fillTable();
        //handleSelection();
    }

    public void handleDomuButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view/Main.fxml"));
        AnchorPane rootLayout = null;
        try {
            rootLayout = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);

        getPrimaryStage().setScene(scene);

    }


}

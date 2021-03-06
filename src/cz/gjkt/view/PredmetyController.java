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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.text.LabelView;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static cz.gjkt.application.Main.getPrimaryStage;


public class PredmetyController implements Initializable {

    @FXML
    TableView tableView;

    PredmetyDAOJDBC predmetyDao = new PredmetyDAOJDBC();
    List<Predmet> predmety;
    ObservableList<Predmet> items;
    ObservableList<Predmet> selectedItems = null;


    public void fillTable(){
        predmety = predmetyDao.getAll();
        items = FXCollections.observableList(predmety);
        tableView.setItems(items);
    }

    public void initColumns() {

        TableColumn<String, Predmet> nazevColumn = new TableColumn<>("Název");
        nazevColumn.setCellValueFactory(new PropertyValueFactory<>("nazev"));
        TableColumn<String, Predmet> popisColumn = new TableColumn<>("Popis");
        popisColumn.setCellValueFactory(new PropertyValueFactory<>("popis"));
        TableColumn<String, Predmet> zkratkaColumn = new TableColumn<>("Zkratka");
        zkratkaColumn.setCellValueFactory(new PropertyValueFactory<>("zkratka"));
        zkratkaColumn.setEditable(true);

        tableView.getColumns().addAll(nazevColumn,popisColumn,zkratkaColumn);
    }

    public void handleSelection(){
        TableView.TableViewSelectionModel<Predmet> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        selectedItems = selectionModel.getSelectedItems();
    }

    public void handlePridejButton(){
        Dialog<Predmet> dialog = new Dialog<>();
        dialog.setTitle("Nový předmět");
        dialog.setWidth(400);
        dialog.setHeight(300);
        predmetDialog(dialog);

        final Optional<Predmet> vysledek = dialog.showAndWait();
        if(vysledek.isPresent()){
            Predmet novyPredmet = vysledek.get();
            predmety.add(novyPredmet);
            predmetyDao.insert(novyPredmet);
        }
        tableView.refresh();

    }

    private void predmetDialog(Dialog dialog){
        // Vytvoření "potvrzovacího" tlačítka pro potvrzení dialogu
        ButtonType createButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        // Nastavení tlačítek dialogu
        dialog.getDialogPane().getButtonTypes().setAll(createButtonType, ButtonType.CANCEL);

        // Mřížka
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Komponenty
        TextField nazevTextField = new TextField();
        Label nazevLabel = new Label("Název");
        TextArea popisTextArea = new TextArea();
        Label popisLabel = new Label("Popis");
        TextField zkratkaTextField = new TextField();
        Label zkratkaLabel = new Label("Zkratka");

        grid.add(nazevLabel, 0, 0);
        grid.add(nazevTextField, 1, 0);
        grid.add(popisLabel,0,1);
        grid.add(popisTextArea,1,1);
        grid.add(zkratkaLabel,0,2);
        grid.add(zkratkaTextField,1,2);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(new Callback<ButtonType, Predmet>() {
            @Override
            public Predmet call(ButtonType param) {
                    Predmet predmet = new Predmet();
                    predmet.setNazev(nazevTextField.getText());
                    predmet.setPopis(popisTextArea.getText());
                    predmet.setZkratka(zkratkaTextField.getText());
                    return  predmet;
            }
        }
        );
    }

    public void handleSmazButton(){

        Predmet item = (Predmet) tableView.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + item);
        predmety.remove(item);
        predmetyDao.delete(item);
        tableView.refresh();
    }

    public void handleUpravButton(){
        try {

            Predmet item = (Predmet) tableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/Predmet.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            UpravPredmetController controller = (UpravPredmetController) loader.getController();
            controller.setPredmet(item);
            controller.setUpravPredmetScene(tableView.getScene());
            controller.setUpravPredmetController(this);
            Scene scene = new Scene(root);
            Stage ps = Main.getPrimaryStage();
            ps.setScene(scene);


        }catch (IOException e){e.printStackTrace();}
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initColumns();
        fillTable();
        handleSelection();
    }

    public void refresh() {
        tableView.refresh();
    }
}

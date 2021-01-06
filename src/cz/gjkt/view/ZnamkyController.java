package cz.gjkt.view;

import cz.gjkt.application.Main;
import cz.gjkt.model.Znamka;
import cz.gjkt.model.ZnamkyDAOJDBC;
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

public class ZnamkyController implements Initializable {

    @FXML
    TableView tableView;

    ZnamkyDAOJDBC znamkyDao = new ZnamkyDAOJDBC();
    List<Znamka> znamky;
    ObservableList<Znamka> items;
    ObservableList<Znamka> selectedItems = null;


    public void fillTable(){
        znamky = znamkyDao.getAll();
        items = FXCollections.observableList(znamky);
        tableView.setItems(items);
    }

    public void initColumns() {

        TableColumn<String, Znamka> idColumn = new TableColumn<>("ID Předmětu");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<String, Znamka> druhColumn = new TableColumn<>("Druh Známky");
        druhColumn.setCellValueFactory(new PropertyValueFactory<>("druh"));
        TableColumn<String, Znamka> studentColumn = new TableColumn<>("Student");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("student"));
        TableColumn<String, Znamka> datumColumn = new TableColumn<>("Datum");
        datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        TableColumn<String, Znamka> hodnotaColumn = new TableColumn<>("Hodnota známky");
        hodnotaColumn.setCellValueFactory(new PropertyValueFactory<>("hodnota"));
        TableColumn<String, Znamka> popisColumn = new TableColumn<>("Popis známky");
        popisColumn.setCellValueFactory(new PropertyValueFactory<>("popis"));

        tableView.getColumns().addAll(idColumn,druhColumn,studentColumn,datumColumn,hodnotaColumn,popisColumn);
    }

    public void handleSelection(){
        TableView.TableViewSelectionModel<Znamka> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        selectedItems = selectionModel.getSelectedItems();

        /*selectedItems.addListener(new ListChangeListener<Kurz>() {
            @Override
            public void onChanged(Change<? extends Kurz> change) {
                System.out.println("Selection changed: " + change.getList());
                System.out.println("Selected: " + selectedItems.get(0));
            }
        });*/
    }

    public void handlePridejButton(){
        Dialog<Znamka> dialog = new Dialog<>();
        dialog.setTitle("Nová známka");
        dialog.setWidth(400);
        dialog.setHeight(300);
        znamkaDialog(dialog);

        final Optional<Znamka> vysledek = dialog.showAndWait();
        if(vysledek.isPresent()){
            Znamka novaZnamka = vysledek.get();
            znamky.add(novaZnamka);
            znamkyDao.insert(novaZnamka);
        }
        tableView.refresh();

    }

    private void znamkaDialog(Dialog dialog){
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
        TextField idTextField = new TextField();
        Label idLabel = new Label("ID Předmětu");
        TextField druhTextField = new TextField();
        Label druhLabel = new Label("Druh Známky");
        TextArea studentTextField = new TextArea();
        Label studentLabel = new Label("student");
        TextArea datumTextField = new TextArea();
        Label datumLabel = new Label("Datum");
        TextField hodnotaTextField = new TextField();
        Label hodnotaLabel = new Label("Hodnota známky");
        TextField popisTextField = new TextField();
        Label popisLabel = new Label("Popis Známky");

        grid.add(idLabel, 0, 0);
        grid.add(idTextField, 1, 0);
        grid.add(druhLabel, 0,1 );
        grid.add(druhTextField, 1, 1);
        grid.add(studentLabel,0,2);
        grid.add(studentTextField,1,2);
        grid.add(datumLabel,0,3);
        grid.add(datumTextField,1,3);
        grid.add(hodnotaLabel,0,4);
        grid.add(hodnotaTextField,1,4);
        grid.add(popisLabel,0,5);
        grid.add(popisTextField,1,5);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(new Callback<ButtonType, Znamka>() {
            @Override
            public Znamka call(ButtonType param) {
                Znamka znamka = new Znamka();
                znamka.setId(idTextField.getText());
                znamka.setDruhZnamky(druhTextField.getText());
                znamka.setStudent(studentTextField.getText());
                znamka.setDatum(datumTextField.getText());
                znamka.setStudent(hodnotaTextField.getText());
                znamka.setPopis(popisTextField.getText());

                return  znamka;
            }
        });
    }

    public void handleSmazButton(){

        Znamka item = (Znamka) tableView.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + item);
        znamky.remove(item);
        znamkyDao.delete(item);
        tableView.refresh();
    }

    public void handleUpravButton(){}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initColumns();
        fillTable();
        handleSelection();
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

        // dShow the scene containing the root layout.
        Scene scene = new Scene(rootLayout);

        getPrimaryStage().setScene(scene);

    }

}

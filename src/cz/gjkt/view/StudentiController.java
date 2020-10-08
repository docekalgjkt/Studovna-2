package cz.gjkt.view;

import cz.gjkt.model.Student;
import cz.gjkt.model.StudentiDAOJDBC;
import cz.gjkt.model.StudentiDAOJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import javax.swing.text.LabelView;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class StudentiController implements Initializable {

    @FXML
    TableView tableView;

    StudentiDAOJDBC studentiDao = new StudentiDAOJDBC();
    List<Student> studenti;
    ObservableList<Student> items;
    ObservableList<Student> selectedItems = null;


    public void fillTable(){
        studenti = studentiDao.getAll();
        items = FXCollections.observableList(studenti);
        tableView.setItems(items);
    }

    public void initColumns() {

        TableColumn<String, Student> prijmeniColumn = new TableColumn<>("Příjmení");
        prijmeniColumn.setCellValueFactory(new PropertyValueFactory<>("prijmeni"));
        TableColumn<String, Student> jmenoColumn = new TableColumn<>("Jméno");
        jmenoColumn.setCellValueFactory(new PropertyValueFactory<>("jmeno"));
        TableColumn<String, Student> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setEditable(true);

        tableView.getColumns().addAll(prijmeniColumn,jmenoColumn,emailColumn);
    }

    public void handleSelection(){
        TableView.TableViewSelectionModel<Student> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        selectedItems = selectionModel.getSelectedItems();

        /*selectedItems.addListener(new ListChangeListener<Predmet>() {
            @Override
            public void onChanged(Change<? extends Predmet> change) {
                System.out.println("Selection changed: " + change.getList());
                System.out.println("Selected: " + selectedItems.get(0));
            }
        });*/
    }

    public void handlePridejButton(){
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Nový student");
        dialog.setWidth(100);
        dialog.setHeight(100);
        studentDialog(dialog);

        final Optional<Student> vysledek = dialog.showAndWait();
        if(vysledek.isPresent()){
            Student novyStudent = vysledek.get();
            studenti.add(novyStudent);
            studentiDao.insert(novyStudent);
        }
        tableView.refresh();

    }

    private void studentDialog(Dialog dialog){
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
        TextField prijmeniTextField = new TextField();
        Label prijmeniLabel = new Label("Příjmení");
        TextArea jmenoTextArea = new TextArea();
        Label jmenoLabel = new Label("Jméno");
        TextField emailTextField = new TextField();
        Label emailLabel = new Label("Email");

        grid.add(prijmeniLabel, 0, 0);
        grid.add(prijmeniTextField, 1, 0);
        grid.add(jmenoLabel,0,1);
        grid.add(jmenoTextArea,1,1);
        grid.add(emailLabel,0,2);
        grid.add(emailTextField,1,2);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(new Callback<ButtonType, Student>() {
            @Override
            public Student call(ButtonType param) {
                Student student = new Student();
                student.setPrijmeni(prijmeniTextField.getText());
                student.setJmeno(jmenoTextArea.getText());
                student.setEmail(emailTextField.getText());
                return  student;
            }
        });
    }

    public void handleSmazButton(){

        Student item = (Student) tableView.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + item);
        studenti.remove(item);
        studentiDao.delete(item);
        tableView.refresh();
    }

    public void handleUpravButton(){

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initColumns();
        fillTable();
        handleSelection();
    }

    public void handleZpetButton(){



    }
}

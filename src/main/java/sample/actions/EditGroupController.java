package sample.actions;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import DataTables.SqlActions.SqlClient;
import TableViews.TableViewer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.AdminController;
import sample.Commands;

import static sample.AdminController.editMode.ADD;
import static sample.AdminController.editMode.DELETE;

public class EditGroupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> courseBox;

    @FXML
    private ComboBox<String> specialityBox;

    @FXML
    private ComboBox<String> cathedraBox;

    @FXML
    private Button editBtn;

    @FXML
    private TextField groupCodeField;

    @FXML
    private TextField amountOfStudentsField;

    @FXML
    private Label editLabel;

    private String currSpeciality = "";
    private String currCourse = "";
    private String currAbr = "";

    @FXML
    void initialize() {

        fillGroup();
        if(AdminController.currMode == ADD){
            editLabel.setText("Додати групу");
            editBtn.setText("Додати");
        } else if(AdminController.currMode == DELETE){
            editLabel.setText("Видалити групу");
            editBtn.setText("Видалити");
        }
        editBtn.setOnAction(event -> {
            registerGroup(AdminController.currMode);
            TableViewer.updateTable(AdminController.currTable);
            Commands.closeWindow(editBtn);
        });
    }

    private void registerGroup(AdminController.editMode currMode) {
        String groupCode = groupCodeField.getText();
        String amountOfStudents = amountOfStudentsField.getText();

        currCourse = courseBox.getSelectionModel().getSelectedItem();
        currAbr = cathedraBox.getSelectionModel().getSelectedItem();
        currSpeciality = specialityBox.getSelectionModel().getSelectedItem();

        if (currSpeciality.equals("") && currAbr.equals("") && currCourse.equals(""))
        {
            return;
        }

        int cathedraId = SqlClient.CreateCommand(String.format("select id from cathedra where abr = '%s'", currAbr)).ExecInt();

        SqlClient.CreateCommand(String.format("insert into [group] (specialityId, cathedraId, groupCode, amountOfStudents, course) " +
                "values(%d, %d, '%s', %d, %d)", Integer.parseInt(currSpeciality), cathedraId, groupCode, Integer.parseInt(amountOfStudents),
                Integer.parseInt(currCourse))).ExecNonQuarry();
    }

    private void fillGroup() {
        List<String> abrs = SqlClient.CreateCommand("select abr from cathedra").ExecStrings();
        List<String> specialityId = SqlClient.CreateCommand("select id from speciality").ExecStrings();

        courseBox.setItems(FXCollections.observableArrayList(new ArrayList<String>(Arrays.asList("1","2","3","4","5","6"))));
        cathedraBox.setItems(FXCollections.observableArrayList(abrs));
        specialityBox.setItems(FXCollections.observableArrayList(specialityId));
    }
}

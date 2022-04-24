package sample.actions;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import DataTables.SqlActions.SqlClient;
import TableViews.TableViewer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import sample.AdminController;
import sample.Commands;

import static sample.AdminController.editMode.ADD;
import static sample.AdminController.editMode.DELETE;

public class EditSubjTeacherController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button editButton;

    @FXML
    private Label editLabel;


    @FXML
    private ComboBox<String> teacherBox;

    @FXML
    private ComboBox<String> subjBox;

    @FXML
    void initialize() {
        fillSubjTeacher();
        if(AdminController.currMode == ADD){
            editLabel.setText("Додати зв'язок викладача з предметом");
            editButton.setText("Додати");
        } else if(AdminController.currMode == DELETE){
            editLabel.setText("Видалити зв'язок викладача з предметом");
            editButton.setText("Видалити");
        }
        editButton.setOnAction(event -> {
            registerSubjTeacher(AdminController.currMode);
            TableViewer.updateTable(AdminController.currTable);
            Commands.closeWindow(editButton);
        });

    }

    private String currTeacher = "";
    private String currSubject = "";

    private void registerSubjTeacher(AdminController.editMode currMode) {
        currSubject = subjBox.getSelectionModel().getSelectedItem().toString();
        currTeacher = teacherBox.getSelectionModel().getSelectedItem().toString();
        if (currSubject.equals("") && currTeacher.equals(""))
        {
            return;
        }

        int subjectId = SqlClient.CreateCommand(String.format("select id from [subject] where name = '%s'", currSubject)).ExecInt();
        int teacherId = SqlClient.CreateCommand(String.format("select id from teacher where fullname = '%s'", currTeacher)).ExecInt();

        SqlClient.CreateCommand(String.format("insert into %s (subjectid, teacherid) values (%d, %d)",
                SqlClient.Tables.SUBJECTTEACHER, subjectId, teacherId)).ExecNonQuarry();
    }

    private void fillSubjTeacher() {
        List<String> subjects = SqlClient.CreateCommand("select name from [subject]").ExecStrings();
        List<String> teachers = SqlClient.CreateCommand("select fullname from teacher").ExecStrings();

        subjBox.setItems(FXCollections.observableArrayList(subjects));
        teacherBox.setItems(FXCollections.observableArrayList(teachers));
    }
}

package sample.actions;

import java.net.URL;
import java.util.ResourceBundle;

import DataTables.Rows.TeacherRow;
import DataTables.SqlActions.SqlClient;
import TableViews.GraphView;
import TableViews.TableViewer;
import TableViews.TeacherView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.AdminController;
import sample.Commands;
import sample.RozklTables;
import sample.animations.Shake;

import static sample.AdminController.editMode.ADD;
import static sample.AdminController.editMode.DELETE;

public class EditTeacherController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private Button editButton;

    @FXML
    private Label editLabel;

    @FXML
    void initialize() {
        if(AdminController.currMode == ADD){
            editLabel.setText("Додати викладача");
            editButton.setText("Додати");
        } else if(AdminController.currMode == DELETE){
            editLabel.setText("Видалити викладача");
            editButton.setText("Видалити");
        }
        editButton.setOnAction(event -> {
            registerTeacher(AdminController.currMode);
            TableViewer.updateTable(AdminController.currTable);
            Commands.closeWindow(editButton);
        });
    }

    private void registerTeacher(AdminController.editMode mode){
        String fN = firstName.getText();
        String lN = lastName.getText();
        if (fN.equals("") || lN.equals(""))
        {
            return;
        }
        TeacherRow teacher = new TeacherRow();
        teacher.set_fullName(fN);
        teacher.set_position(lN);
        if(mode == ADD){
            SqlClient.CreateCommand(String.format("insert into %s values('%s','%s')", SqlClient.Tables.TEACHER, teacher.get_fullName(),teacher.get_position())).ExecNonQuarry();
        }
    }
}

package sample.actions;

import java.net.URL;
import java.util.ResourceBundle;

import DataTables.Rows.SubjectRow;
import DataTables.SqlActions.SqlClient;
import TableViews.TableViewer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.AdminController;
import sample.Commands;
import sample.animations.Shake;

import static sample.AdminController.editMode.ADD;
import static sample.AdminController.editMode.DELETE;

public class EditSubjectController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField subjNameField;

    @FXML
    private Button editButton;

    @FXML
    private Label editLabel;

    @FXML
    void initialize() {
        if(AdminController.currMode == ADD){
            editLabel.setText("Додати предмет");
            editButton.setText("Додати");
        } else if(AdminController.currMode == DELETE){
            editLabel.setText("Видалити предмет");
            editButton.setText("Видалити");
        }
        editButton.setOnAction(event -> {
            registerSubject(AdminController.currMode);
            TableViewer.updateTable(AdminController.currTable);
            Commands.closeWindow(editButton);
        });

    }

    private void registerSubject(AdminController.editMode mode) {
        String subject = subjNameField.getText();
        if (subject.equals(""))
        {
            return;
        }
        if (mode == ADD)
            SqlClient.CreateCommand(String.format("insert into %s values('%s')", SqlClient.Tables.SUBJECT, subject)).ExecNonQuarry();
    }

}

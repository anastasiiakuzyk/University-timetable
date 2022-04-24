package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import DataTables.SqlActions.SqlClient;
import DataTables.SqlActions.SqlCmd;
import TableViews.GraphView;
import TableViews.SubjectView;
import TableViews.TeacherView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.actions.ObserverForRozklad.RozkladBoxFiller;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button adminAuthButton;

    @FXML
    private TableView<?> data_table;

    @FXML
    private ToggleButton teachersButton;

    @FXML
    private ToggleButton subjTeachButton;

    @FXML
    private ToggleButton rozklButton;

    @FXML
    private ToggleButton groupButton;

    @FXML
    private ToggleButton graphButton;

    @FXML
    private ToggleButton subjButton;

    @FXML
    private TextField searchField;

    @FXML
    private MenuButton searchMenuButton;

    @FXML
    private MenuItem groupItem;

    @FXML
    private MenuItem teacherItem;

    private List<ToggleButton> toggleButtons = new ArrayList<>();

    @FXML
    void initialize() {
        toggleButtons.add(teachersButton);
        toggleButtons.add(subjButton);
        toggleButtons.add(subjTeachButton);
        toggleButtons.add(rozklButton);
        toggleButtons.add(graphButton);
        toggleButtons.add(groupButton);
        ToggleGroup group = new ToggleGroup();
        rozklButton.setSelected(true);
        RozklTables.showRozkl(data_table);
        for (ToggleButton btn : toggleButtons) {
            btn.setToggleGroup(group);
        }

        teachersButton.setOnAction(event -> {

            searchMenuButton.setVisible(false);
            searchField.setVisible(false);
            TeacherView.showTeachers(data_table);
        });
        subjTeachButton.setOnAction(event -> {

            searchMenuButton.setVisible(false);
            searchField.setVisible(false);
            RozklTables.showSubjTeach(data_table);
        });
        rozklButton.setOnAction(event -> {

            searchMenuButton.setVisible(true);
            searchField.setVisible(true);
            RozklTables.showRozkl(data_table);
        });
        graphButton.setOnAction(event ->{

            searchMenuButton.setVisible(false);
            searchField.setVisible(false);
            GraphView.showGraphs(data_table);
        });
        groupButton.setOnAction(event ->{

            searchMenuButton.setVisible(false);
            searchField.setVisible(false);
            RozklTables.showGroup(data_table);
        });
        subjButton.setOnAction(event -> {

            searchMenuButton.setVisible(false);
            searchField.setVisible(false);
            SubjectView.showSubjects(data_table);
        });

        groupItem.setOnAction(event -> {
            if (rozklButton.isSelected()){
                String groupCode = searchField.getText();
                RozklTables.findRozkladByGroup(data_table, groupCode);
            }
        });

        teacherItem.setOnAction(event -> {
            if (rozklButton.isSelected()){
                String teacher = searchField.getText();
                RozklTables.findRozkladByTeacher(data_table, teacher);
            }
        });

        adminAuthButton.setOnAction(event -> {
            Commands.openNewWindow(adminAuthButton, "/sample/signUser/auth.fxml");
        });
    }

    private void selectBtn(ToggleButton btn)
    {
        for (ToggleButton toggle : toggleButtons)
        {
            toggle.setSelected(false);
        }
        btn.setSelected(true);
    }
}

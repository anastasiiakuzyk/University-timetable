package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import DataTables.Rows.SubjectRow;
import DataTables.Rows.TeacherRow;
import DataTables.SqlActions.SqlClient;
import TableViews.GraphView;
import TableViews.SubjectView;
import TableViews.TableViewer;
import TableViews.TeacherView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.animations.Shake;

public class AdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ToggleButton groupButton;

    @FXML
    private ToggleButton graphButton;

    @FXML
    private TableView<?> data_table;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem addItem;

    @FXML
    private MenuItem deleteItem;

    @FXML
    private Button logoutButton;

    @FXML
    private ToggleButton teachersButton;

    @FXML
    private ToggleButton subjTeachButton;

    @FXML
    private ToggleButton rozklButton;

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

    public static TableView<?> dataTable;

    public enum editMode{ADD,DELETE}

    public static editMode currMode;
    public static editedTable currTable;

    public enum editedTable
    {
        TEACHER,
        SUBJTEACHER,
        GRAPH,
        GROUP,
        ROZKLAD,
        SUBJECT
    }

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
        dataTable = data_table;
        RozklTables.showRozkl(data_table);
        rozklButton.setSelected(true);

        for (ToggleButton btn : toggleButtons)
        {
            btn.setToggleGroup(group);
        }

        teachersButton.setOnAction(event -> {
            menuButton.setVisible(true);
            searchField.setVisible(false);
            searchMenuButton.setVisible(false);
            TeacherView.showTeachers(data_table);
        });
        subjTeachButton.setOnAction(event -> {
            menuButton.setVisible(true);
            searchField.setVisible(false);
            searchMenuButton.setVisible(false);
            RozklTables.showSubjTeach(data_table);
        });
        rozklButton.setOnAction(event -> {
            menuButton.setVisible(true);
            searchMenuButton.setVisible(true);
            searchField.setVisible(true);
            RozklTables.showRozkl(data_table);
        });
        graphButton.setOnAction(event ->{
            menuButton.setVisible(false);
            searchMenuButton.setVisible(false);
            searchField.setVisible(false);
            GraphView.showGraphs(data_table);
        });
        groupButton.setOnAction(event ->{
            menuButton.setVisible(true);
            searchMenuButton.setVisible(false);
            searchField.setVisible(false);
            RozklTables.showGroup(data_table);
        });
        subjButton.setOnAction(event -> {
            menuButton.setVisible(true);
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

        logoutButton.setOnAction(event -> {
            Commands.openNewWindow(logoutButton, "/sample/sample.fxml");
        });

        addItem.setOnAction(event -> {
            currMode = editMode.ADD;
            if (teachersButton.isSelected()) {
                currTable = editedTable.TEACHER;
                Commands.openWindowWithoutClosing("/sample/actions/editTeacher.fxml");
            }
            else if (rozklButton.isSelected())
            {
                currTable = editedTable.ROZKLAD;
                Commands.openWindowWithoutClosing("/sample/actions/editRozklad.fxml");
            }
            else if (subjButton.isSelected())
            {
                currTable = editedTable.SUBJECT;
                Commands.openWindowWithoutClosing("/sample/actions/editSubject.fxml");
                TableViewer.updateTable(currTable);
            }
            else if (subjTeachButton.isSelected())
            {
                currTable = editedTable.SUBJTEACHER;
                Commands.openWindowWithoutClosing("/sample/actions/editSubjTeacher.fxml");
                TableViewer.updateTable(currTable);
            }
            else if (groupButton.isSelected())
            {
                currTable = editedTable.GROUP;
                Commands.openWindowWithoutClosing("/sample/actions/editGroup.fxml");
                TableViewer.updateTable(currTable);
            }

        });
        deleteItem.setOnAction(event -> {
            currMode = editMode.DELETE;
            if (teachersButton.isSelected()) {
                currTable = editedTable.TEACHER;
                deleteTeacher();
                TableViewer.updateTable(currTable);
            }
            else if (rozklButton.isSelected())
            {
                currTable = editedTable.ROZKLAD;
                deleteRozklad();
                TableViewer.updateTable(currTable);
            }
            else if (subjButton.isSelected())
            {
                currTable = editedTable.SUBJECT;
                deleteSubject();
                TableViewer.updateTable(currTable);
            }
            else if (subjTeachButton.isSelected())
            {
                currTable = editedTable.SUBJTEACHER;
                deleteSubjTeacher();
                TableViewer.updateTable(currTable);
            }
            else if (groupButton.isSelected())
            {
                currTable = editedTable.GROUP;
                deleteGroup();
                TableViewer.updateTable(currTable);
            }
        });
    }

    private void deleteGroup() {
        if (AdminController.dataTable.getSelectionModel().isEmpty())
        {
            Shake.wrongEnter(menuButton);
            return;
        }
        ObservableList<?> selectedItems = AdminController.dataTable.getSelectionModel().getSelectedItems();
        StringBuilder selectedRowBuilder = new StringBuilder(selectedItems.get(0).toString());
        selectedRowBuilder.deleteCharAt(0);
        selectedRowBuilder.deleteCharAt(selectedRowBuilder.length()-1);
        String[] selectedRowItems = selectedRowBuilder.toString().split(", ");
        int cathedraID = SqlClient.CreateCommand(String.format("select id from cathedra where abr = '%s'", selectedRowItems[1])).ExecInt();

        SqlClient.CreateCommand(String.format("delete from [group] where groupCode = '%s' and amountOfStudents = %d and course = %d and " +
                "cathedraid = %d and specialityid = %d", selectedRowItems[0], Integer.parseInt(selectedRowItems[4]), Integer.parseInt(selectedRowItems[5]),
                cathedraID, Integer.parseInt(selectedRowItems[2]))).ExecNonQuarry();
    }

    private void deleteRozklad()
    {
        if (AdminController.dataTable.getSelectionModel().isEmpty())
        {
            Shake.wrongEnter(menuButton);
            return;
        }
        ObservableList<?> selectedItems = AdminController.dataTable.getSelectionModel().getSelectedItems();
        StringBuilder selectedRowBuilder = new StringBuilder(selectedItems.get(0).toString());
        selectedRowBuilder.deleteCharAt(0);
        selectedRowBuilder.deleteCharAt(selectedRowBuilder.length()-1);
        String[] selectedRowItems = selectedRowBuilder.toString().split(", ");
        int graphId = SqlClient.CreateCommand(String.format("select id from graph where tfrom = '%s'", selectedRowItems[1])).ExecInt();
        int groupId = SqlClient.CreateCommand(
                String.format("select id from [group] where groupcode = '%s'", selectedRowItems[4])).ExecInt();
        int subjectId = SqlClient.CreateCommand(
                String.format("select id from [subject] where name = '%s'", selectedRowItems[5])).ExecInt();
        int teacherId = SqlClient.CreateCommand(
                String.format("select id from [teacher] where fullname = '%s'", selectedRowItems[6])).ExecInt();

        int audienceId = SqlClient.CreateCommand(
                String.format("select id from %s where audienceNumber = %d",
                        SqlClient.Tables.AUDIENCE, Integer.parseInt(selectedRowItems[3]))).ExecInt();
        int loadId = SqlClient.CreateCommand(
                String.format("select id from %s where groupid = %d and subjectid = %d and teacherid = %d",
                        SqlClient.Tables.LOAD, groupId, subjectId, teacherId)).ExecInt();

        int counter = SqlClient.CreateCommand(String.format("select count(*) from timetable where loadid = %d", loadId)).ExecInt();

        SqlClient.CreateCommand(String.format("delete from timetable where graphId = %d and " +
                "loadId = %d and audienceId= %d and dayinweek= '%s'", graphId, loadId, audienceId, selectedRowItems[0])).ExecNonQuarry();
        if (counter == 1)
        {
            SqlClient.CreateCommand(String.format("delete from load where id = %d", loadId)).ExecNonQuarry();
        }
    }

    private void deleteSubject()
    {
        if (AdminController.dataTable.getSelectionModel().isEmpty())
        {
            Shake.wrongEnter(menuButton);
            return;
        }
        ObservableList<?> selectedItems = AdminController.dataTable.getSelectionModel().getSelectedItems();
        SubjectRow subjectRow = (SubjectRow) selectedItems.get(0);
        SqlClient.CreateCommand(String.format("delete from [subject] where name = '%s'", subjectRow.get_name())).ExecNonQuarry();
    }

    private void deleteTeacher(){
        if (AdminController.dataTable.getSelectionModel().isEmpty())
        {
            Shake.wrongEnter(menuButton);
            return;
        }
        ObservableList<?> selectedItems = AdminController.dataTable.getSelectionModel().getSelectedItems();
        TeacherRow teacherRow = (TeacherRow) selectedItems.get(0);

        SqlClient.CreateCommand(String.format("DELETE FROM %s WHERE fullName ='%s' AND position = '%s'"
                ,SqlClient.Tables.TEACHER,teacherRow.get_fullName(), teacherRow.get_position() )).ExecNonQuarry();
    }

    private void deleteSubjTeacher(){
        if (AdminController.dataTable.getSelectionModel().isEmpty())
        {
            Shake.wrongEnter(menuButton);
            return;
        }
        ObservableList<?> selectedItems = AdminController.dataTable.getSelectionModel().getSelectedItems();
        StringBuilder selectedRowBuilder = new StringBuilder(selectedItems.get(0).toString());
        selectedRowBuilder.deleteCharAt(0);
        selectedRowBuilder.deleteCharAt(selectedRowBuilder.length()-1);
        String[] selectedRowItems = selectedRowBuilder.toString().split(", ");
        int subjectId = SqlClient.CreateCommand(String.format("select id from [subject] where name = '%s'", selectedRowItems[0])).ExecInt();
        int teacherId = SqlClient.CreateCommand(String.format("select id from teacher where fullname = '%s'", selectedRowItems[1])).ExecInt();
        SqlClient.CreateCommand(String.format("delete from %s where subjectid = %d and teacherid = %d"
                , SqlClient.Tables.SUBJECTTEACHER,subjectId, teacherId)).ExecNonQuarry();
    }
}

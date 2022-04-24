package TableViews;

import DataTables.Rows.LoadRow;
import DataTables.SqlActions.SqlClient;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class LoadView implements ITableViewer{
    private TableColumn<LoadRow, Integer> idColumn = new TableColumn<>("ID");

    private TableColumn<LoadRow, Integer> groupColumn = new TableColumn<>("Група ID");

    private TableColumn<LoadRow, Integer> subjectIdColumn = new TableColumn<>("Предмет ID");

    private TableColumn<LoadRow, Integer> teacherIdColumn = new TableColumn<>("Викладач ID");

    private TableColumn<LoadRow, String> lessonTypeColumn = new TableColumn<>("Тип пари");

    private TableColumn<LoadRow, Integer> hoursColumn = new TableColumn<>("Години");


    public LoadView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<LoadRow, Integer>("_id"));

        groupColumn.setCellValueFactory(new PropertyValueFactory<LoadRow, Integer>("_groupId"));

        subjectIdColumn.setCellValueFactory(new PropertyValueFactory<LoadRow, Integer>("_subjectId"));

        teacherIdColumn.setCellValueFactory(new PropertyValueFactory<LoadRow, Integer>("_teacherId"));

        lessonTypeColumn.setCellValueFactory(new PropertyValueFactory<LoadRow, String>("_lessonType"));

        hoursColumn.setCellValueFactory(new PropertyValueFactory<LoadRow, Integer>("_hoursInWeek"));
    }

    @Override
    public void show(ViewParam param)  {
        param.getDataTable().setItems(param.getdata());

        param.getDataTable().getColumns().addAll(idColumn, subjectIdColumn, teacherIdColumn, groupColumn, lessonTypeColumn, hoursColumn);
    }
    public static void showLoads(TableView data_table){
        data_table.getColumns().clear();


        List<LoadRow> list = SqlClient.CreateCommand(
                String.format("select * from %s", SqlClient.Tables.LOAD)).ExecObjects(new LoadRow());

        ITableViewer tableViewer = new LoadView();

        tableViewer.show(new ViewParam<LoadRow>(FXCollections.observableArrayList(list), data_table));
    }
}

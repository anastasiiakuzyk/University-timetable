package TableViews;

import DataTables.Rows.TimeTableRow;
import DataTables.SqlActions.SqlClient;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TimeTableView implements ITableViewer{
    private TableColumn<TimeTableRow, Integer> idColumn = new TableColumn<>("ID");

    private TableColumn<TimeTableRow, Integer> graphIdColumn = new TableColumn<>("Графік ID");

    private TableColumn<TimeTableRow, Integer> loadIdColumn = new TableColumn<>("Навантаження ID");

    private TableColumn<TimeTableRow, Integer> audienceIdColumn = new TableColumn<>("Аудиторія ID");

    private TableColumn<TimeTableRow, Integer> weekNumberColumn = new TableColumn<>("Тиждень №");

    private TableColumn<TimeTableRow, String> weekDayColumn = new TableColumn<>("День тижня");


    public TimeTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<TimeTableRow, Integer>("_id"));

        graphIdColumn.setCellValueFactory(new PropertyValueFactory<TimeTableRow, Integer>("_graphId"));

        loadIdColumn.setCellValueFactory(new PropertyValueFactory<TimeTableRow, Integer>("_loadId"));

        audienceIdColumn.setCellValueFactory(new PropertyValueFactory<TimeTableRow, Integer>("_audienceId"));

        weekNumberColumn.setCellValueFactory(new PropertyValueFactory<TimeTableRow, Integer>("_weekNumber"));

        weekDayColumn.setCellValueFactory(new PropertyValueFactory<TimeTableRow, String>("_weekDay"));
    }

    @Override
    public void show(ViewParam param)  {
        param.getDataTable().setItems(param.getdata());

        param.getDataTable().getColumns().addAll(idColumn, graphIdColumn, loadIdColumn, audienceIdColumn, weekNumberColumn, weekDayColumn);
    }
    public static void showTimeTables(TableView data_table){
        data_table.getColumns().clear();


        List<TimeTableRow> list = SqlClient.CreateCommand(
                String.format("select * from %s", SqlClient.Tables.TIMETABLE)).ExecObjects(new TimeTableRow());

        ITableViewer tableViewer = new TimeTableView();

        tableViewer.show(new ViewParam<TimeTableRow>(FXCollections.observableArrayList(list), data_table));
    }
}

package TableViews;

import DataTables.Rows.TeacherRow;
import DataTables.SqlActions.SqlClient;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TeacherView implements ITableViewer{
    //private TableColumn<TeacherRow, Integer> idColumn = new TableColumn<>("ID");

    private TableColumn<TeacherRow, String> nameColumn = new TableColumn<>("Повне ім'я");

    private TableColumn<TeacherRow, String> positionColumn = new TableColumn<>("Позиція");


    public TeacherView() {
        //idColumn.setCellValueFactory(new PropertyValueFactory<TeacherRow, Integer>("_id"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<TeacherRow, String>("_fullName"));

        positionColumn.setCellValueFactory(new PropertyValueFactory<TeacherRow, String>("_position"));
    }

    @Override
    public void show(ViewParam param)  {
        param.getDataTable().setItems(param.getdata());

        param.getDataTable().getColumns().addAll(nameColumn, positionColumn);
    }
    public static void showTeachers(TableView data_table){

        data_table.getColumns().clear();

        List<TeacherRow> list = SqlClient.CreateCommand(
                String.format("select * from %s", SqlClient.Tables.TEACHER)).ExecObjects(new TeacherRow());

        ITableViewer tableViewer = new TeacherView();

        tableViewer.show(new ViewParam<TeacherRow>(FXCollections.observableArrayList(list), data_table));
    }
}

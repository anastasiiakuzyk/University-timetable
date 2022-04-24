package TableViews;

import DataTables.Rows.GroupRow;
import DataTables.SqlActions.SqlClient;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class GroupView implements ITableViewer{
    private TableColumn<GroupRow, Integer> idColumn = new TableColumn<>("ID");

    private TableColumn<GroupRow, Integer> specIdColumn = new TableColumn<>("Спец ID");

    private TableColumn<GroupRow, Integer> cathedraIdColumn = new TableColumn<>("Кафедра ID");

    private TableColumn<GroupRow, String> groupColumn = new TableColumn<>("Група");

    private TableColumn<GroupRow, Integer> amountColumn = new TableColumn<>("К-ть студентів");

    private TableColumn<GroupRow, Integer> courseColumn = new TableColumn<>("Курс");


    public GroupView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<GroupRow, Integer>("_id"));

        specIdColumn.setCellValueFactory(new PropertyValueFactory<GroupRow, Integer>("_specialityId"));

        cathedraIdColumn.setCellValueFactory(new PropertyValueFactory<GroupRow, Integer>("_cathedraId"));

        groupColumn.setCellValueFactory(new PropertyValueFactory<GroupRow, String>("_groupCode"));

        amountColumn.setCellValueFactory(new PropertyValueFactory<GroupRow, Integer>("_amountOfStudents"));

        courseColumn.setCellValueFactory(new PropertyValueFactory<GroupRow, Integer>("_course"));
    }

    @Override
    public void show(ViewParam param)  {
        param.getDataTable().setItems(param.getdata());

        param.getDataTable().getColumns().addAll(idColumn,specIdColumn,cathedraIdColumn,groupColumn,amountColumn,courseColumn);
    }
    public static void showGroups(TableView data_table){
        data_table.getColumns().clear();


        List<GroupRow> list = SqlClient.CreateCommand(
                String.format("select * from %s", SqlClient.Tables.GROUP)).ExecObjects(new GroupRow());

        ITableViewer tableViewer = new GroupView();

        tableViewer.show(new ViewParam<GroupRow>(FXCollections.observableArrayList(list), data_table));
    }
}

package TableViews;

import DataTables.Rows.FacultyRow;

import DataTables.SqlActions.SqlClient;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class FacultyView implements ITableViewer {
    private TableColumn<FacultyRow, Integer> idColumn = new TableColumn<>("ID");

    private TableColumn<FacultyRow, String> naimColumn = new TableColumn<>("Факультет");

    private TableColumn<FacultyRow, Integer> corpColumn = new TableColumn<>("Корпус");

    private TableColumn<FacultyRow, String> abrColumn = new TableColumn<>("Абревіатура");


    public FacultyView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<FacultyRow, Integer>("_id"));

        naimColumn.setCellValueFactory(new PropertyValueFactory<FacultyRow, String>("_name"));

        corpColumn.setCellValueFactory(new PropertyValueFactory<FacultyRow, Integer>("_corpusId"));

        abrColumn.setCellValueFactory(new PropertyValueFactory<FacultyRow, String>("_abr"));
    }

    @Override
    public void show(ViewParam param) {
        param.getDataTable().setItems(param.getdata());

        param.getDataTable().getColumns().addAll(idColumn, naimColumn,corpColumn, abrColumn);
    }

    public static void showFaculties(TableView data_table) {
        data_table.getColumns().clear();


        List<FacultyRow> list = SqlClient.CreateCommand(
                String.format("select * from %s", SqlClient.Tables.FACULTY)).ExecObjects(new FacultyRow());

        ITableViewer tableViewer = new FacultyView();

        tableViewer.show(new ViewParam<FacultyRow>(FXCollections.observableArrayList(list), data_table));
    }
}

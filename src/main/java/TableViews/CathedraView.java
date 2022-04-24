package TableViews;

import DataTables.Rows.CathedraRow;
import DataTables.SqlActions.SqlClient;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CathedraView implements ITableViewer{
    private TableColumn<CathedraRow, Integer> idColumn = new TableColumn<>("ID");

    private TableColumn<CathedraRow, String> naimColumn = new TableColumn<>("Кафедра");

    private TableColumn<CathedraRow, String> abrColumn = new TableColumn<>("Абревіатура");


    public CathedraView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<CathedraRow, Integer>("_id"));

        naimColumn.setCellValueFactory(new PropertyValueFactory<CathedraRow, String>("_name"));

        abrColumn.setCellValueFactory(new PropertyValueFactory<CathedraRow, String>("_abr"));
    }

    @Override
    public void show(ViewParam param)  {
        param.getDataTable().setItems(param.getdata());

        param.getDataTable().getColumns().addAll(idColumn, naimColumn, abrColumn);
    }
    public static void showCathedras(TableView data_table){

        data_table.getColumns().clear();


        List<CathedraRow> list = SqlClient.CreateCommand(
                String.format("select * from %s", SqlClient.Tables.CATHEDRA)).ExecObjects(new CathedraRow());

        ITableViewer tableViewer = new CathedraView();

        tableViewer.show(new ViewParam<CathedraRow>(FXCollections.observableArrayList(list), data_table));
    }
}

package TableViews;

import DataTables.Rows.SpecialityRow;
import DataTables.SqlActions.SqlClient;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SpecialityView implements ITableViewer{
    private TableColumn<SpecialityRow, Integer> idColumn = new TableColumn<>("ID");

    private TableColumn<SpecialityRow, String> nameColumn = new TableColumn<>("Назва спеціальності");

    private TableColumn<SpecialityRow, Integer> planIdColumn = new TableColumn<>("Навч план ID");


    public SpecialityView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<SpecialityRow, Integer>("_id"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<SpecialityRow, String>("_name"));

        planIdColumn.setCellValueFactory(new PropertyValueFactory<SpecialityRow, Integer>("_planId"));
    }

    @Override
    public void show(ViewParam param)  {
        param.getDataTable().setItems(param.getdata());

        param.getDataTable().getColumns().addAll(idColumn, nameColumn, planIdColumn);
    }
    public static void showSpecialities(TableView data_table){
        data_table.getColumns().clear();


        List<SpecialityRow> list = SqlClient.CreateCommand(
                String.format("select * from %s", SqlClient.Tables.SPECIALITY)).ExecObjects(new SpecialityRow());

        ITableViewer tableViewer = new SpecialityView();

        tableViewer.show(new ViewParam<SpecialityRow>(FXCollections.observableArrayList(list), data_table));
    }
}

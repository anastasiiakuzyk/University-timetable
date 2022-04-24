package TableViews;

import DataTables.Rows.GraphRow;
import DataTables.SqlActions.SqlClient;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Time;
import java.util.List;

public class GraphView implements ITableViewer{
    private TableColumn<GraphRow, Integer> idColumn = new TableColumn<>("Номер пари");

    private TableColumn<GraphRow, Time> toColumn = new TableColumn<>("Кінець");

    private TableColumn<GraphRow, Time> fromColumn = new TableColumn<>("Початок");


    public GraphView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<GraphRow, Integer>("_id"));

        toColumn.setCellValueFactory(new PropertyValueFactory<GraphRow, Time>("_tto"));

        fromColumn.setCellValueFactory(new PropertyValueFactory<GraphRow, Time>("_tfrom"));
    }

    @Override
    public void show(ViewParam param)  {
        param.getDataTable().setItems(param.getdata());

        param.getDataTable().getColumns().addAll(idColumn, toColumn, fromColumn);
    }
    public static void showGraphs(TableView data_table){
        data_table.getColumns().clear();


        List<GraphRow> list = SqlClient.CreateCommand(
                String.format("select * from %s", SqlClient.Tables.GRAPH)).ExecObjects(new GraphRow());

        ITableViewer tableViewer = new GraphView();

        tableViewer.show(new ViewParam<GraphRow>(FXCollections.observableArrayList(list), data_table));
    }
}

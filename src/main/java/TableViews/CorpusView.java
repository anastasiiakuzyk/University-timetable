package TableViews;

import DataTables.Rows.CorpusRow;
import DataTables.SqlActions.SqlClient;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CorpusView implements ITableViewer{
    private TableColumn<CorpusRow, Integer> idColumn = new TableColumn<>("ID");

    private TableColumn<CorpusRow, Integer> corpNumbColumn = new TableColumn<>("Корпус №");

    private TableColumn<CorpusRow, String> addressColumn = new TableColumn<>("Адреса");


    public CorpusView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<CorpusRow, Integer>("_id"));

        corpNumbColumn.setCellValueFactory(new PropertyValueFactory<CorpusRow, Integer>("_corpNumber"));

        addressColumn.setCellValueFactory(new PropertyValueFactory<CorpusRow, String>("_address"));
    }

    @Override
    public void show(ViewParam param)  {
        param.getDataTable().setItems(param.getdata());

        param.getDataTable().getColumns().addAll(idColumn, corpNumbColumn, addressColumn);
    }
    public static void showCorpuses(TableView data_table){
        data_table.getColumns().clear();


        List<CorpusRow> list = SqlClient.CreateCommand(
                String.format("select * from %s", SqlClient.Tables.CORPUS)).ExecObjects(new CorpusRow());

        ITableViewer tableViewer = new CorpusView();

        tableViewer.show(new ViewParam<CorpusRow>(FXCollections.observableArrayList(list), data_table));
    }
}

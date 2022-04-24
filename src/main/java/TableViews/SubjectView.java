package TableViews;

import DataTables.SqlActions.SqlClient;
import DataTables.Rows.SubjectRow;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SubjectView implements ITableViewer {
    private TableColumn<SubjectRow, String> naimColumn = new TableColumn<>("Предмет");


    public SubjectView() {

        naimColumn.setCellValueFactory(new PropertyValueFactory<SubjectRow, String>("_name"));
        naimColumn.setPrefWidth(80);
    }

    @Override
    public void show(ViewParam param) {
        param.getDataTable().setItems(param.getdata());

        param.getDataTable().getColumns().addAll(naimColumn);
    }

    public static void showSubjects(TableView data_table){
        data_table.getColumns().clear();


        List<SubjectRow> list = SqlClient.CreateCommand(
                String.format("select * from %s", SqlClient.Tables.SUBJECT)).ExecObjects(new SubjectRow());

        ITableViewer tableViewer = new SubjectView();

        tableViewer.show(new ViewParam<SubjectRow>(FXCollections.observableArrayList(list), data_table));
    }
}

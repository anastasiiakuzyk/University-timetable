package TableViews;

import DataTables.Rows.AudienceRow;
import DataTables.SqlActions.SqlClient;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AudienceView implements ITableViewer{
    private TableColumn<AudienceRow, Integer> idColumn = new TableColumn<>("ID");

    private TableColumn<AudienceRow, Integer> audNumbColumn = new TableColumn<>("Аудиторія №");

    private TableColumn<AudienceRow, String> abrColumn = new TableColumn<>("Тип пари");

    private TableColumn<AudienceRow, Integer> placesColumn = new TableColumn<>("К-ть місць");

    private TableColumn<AudienceRow, Integer> corpIdColumn = new TableColumn<>("ID Корпусу");


    public AudienceView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<AudienceRow, Integer>("_id"));

        audNumbColumn.setCellValueFactory(new PropertyValueFactory<AudienceRow, Integer>("_audienceNumber"));

        abrColumn.setCellValueFactory(new PropertyValueFactory<AudienceRow, String>("_lectureType"));

        placesColumn.setCellValueFactory(new PropertyValueFactory<AudienceRow, Integer>("_places"));

        corpIdColumn.setCellValueFactory(new PropertyValueFactory<AudienceRow, Integer>("_corpusId"));
    }

    @Override
    public void show(ViewParam param)  {
        param.getDataTable().setItems(param.getdata());

        param.getDataTable().getColumns().addAll(idColumn, audNumbColumn, abrColumn, placesColumn, corpIdColumn);
    }
    public static void showAudiences(TableView data_table){

        data_table.getColumns().clear();


        List<AudienceRow> list = SqlClient.CreateCommand(
                String.format("select * from %s", SqlClient.Tables.AUDIENCE)).ExecObjects(new AudienceRow());

        ITableViewer tableViewer = new AudienceView();

        tableViewer.show(new ViewParam<AudienceRow>(FXCollections.observableArrayList(list), data_table));
    }
}

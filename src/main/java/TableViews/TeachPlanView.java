package TableViews;

import DataTables.Rows.TeachPlanRow;
import DataTables.SqlActions.SqlClient;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TeachPlanView implements ITableViewer{
    private TableColumn<TeachPlanRow, Integer> specIdColumn = new TableColumn<>("Спец ID");

    private TableColumn<TeachPlanRow, Integer> semestrColumn = new TableColumn<>("Семестр");

    private TableColumn<TeachPlanRow, Integer> subjectIdColumn = new TableColumn<>("Предмет ID");

    private TableColumn<TeachPlanRow, Integer> wholeHoursColumn = new TableColumn<>("Всі години");

    private TableColumn<TeachPlanRow, Integer> lectureHoursColumn = new TableColumn<>("Лек години");

    private TableColumn<TeachPlanRow, Integer> labHoursColumn = new TableColumn<>("Лаб години");

    private TableColumn<TeachPlanRow, Integer> practHours = new TableColumn<>("Практ години");


    public TeachPlanView() {
        specIdColumn.setCellValueFactory(new PropertyValueFactory<TeachPlanRow, Integer>("_specialityId"));

        semestrColumn.setCellValueFactory(new PropertyValueFactory<TeachPlanRow, Integer>("_semestr"));

        subjectIdColumn.setCellValueFactory(new PropertyValueFactory<TeachPlanRow, Integer>("_subjectId"));

        wholeHoursColumn.setCellValueFactory(new PropertyValueFactory<TeachPlanRow, Integer>("_wholeHours"));

        lectureHoursColumn.setCellValueFactory(new PropertyValueFactory<TeachPlanRow, Integer>("_lectureHours"));

        labHoursColumn.setCellValueFactory(new PropertyValueFactory<TeachPlanRow, Integer>("_labHours"));

        practHours.setCellValueFactory(new PropertyValueFactory<TeachPlanRow, Integer>("_practHours"));
    }

    @Override
    public void show(ViewParam param)  {
        param.getDataTable().setItems(param.getdata());

        param.getDataTable().getColumns().addAll(semestrColumn,specIdColumn, subjectIdColumn, wholeHoursColumn, lectureHoursColumn, labHoursColumn, practHours);
    }
    public static void showTeachPlans(TableView data_table){
        data_table.getColumns().clear();


        List<TeachPlanRow> list = SqlClient.CreateCommand(
                String.format("select * from %s", SqlClient.Tables.TEACHPLAN)).ExecObjects(new TeachPlanRow());

        ITableViewer tableViewer = new TeachPlanView();

        tableViewer.show(new ViewParam<TeachPlanRow>(FXCollections.observableArrayList(list), data_table));
    }
}

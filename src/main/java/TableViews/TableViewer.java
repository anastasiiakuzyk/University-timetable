package TableViews;

import DataTables.SqlActions.SqlClient;
import DataTables.SqlActions.SqlCmd;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import sample.AdminController;
import sample.RozklTables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableViewer
{
    private static List<String> columnNames = new ArrayList<>();
    private static ObservableList<ObservableList> data;

    private static void findColumnNames(String quarry)
    {
        columnNames.clear();
        Pattern compile = Pattern.compile("\\[[А-Яа-яі \\.-]*\\]");
        Matcher mather = compile.matcher(quarry);

        while (mather.find()) {
            StringBuilder columnName = new StringBuilder(mather.group());

            columnName.deleteCharAt(0);
            columnName.deleteCharAt(columnName.length() - 1);

            columnNames.add(columnName.toString());
        }
    }
    public static void buildData(String quarry, TableView table) {
        Connection c;
        data = FXCollections.observableArrayList();
        try {
            c = SqlCmd.getConnection();
            ResultSet rs = c.createStatement().executeQuery(quarry);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(columnNames.get(i));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                table.getColumns().addAll(col);
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            table.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Show(String quarry, TableView table)
    {
        findColumnNames(quarry);
        table.getItems().clear();
        table.getColumns().clear();
        buildData(quarry,table);
    }

    public static void updateTable(AdminController.editedTable table){
        switch(table)
        {
            case TEACHER:
                TeacherView.showTeachers(AdminController.dataTable);
                break;
            case GRAPH:
                GraphView.showGraphs(AdminController.dataTable);
                break;
            case GROUP:
                RozklTables.showGroup(AdminController.dataTable);
                break;
            case ROZKLAD:
                RozklTables.showRozkl(AdminController.dataTable);
                break;
            case SUBJTEACHER:
                RozklTables.showSubjTeach(AdminController.dataTable);
                break;
            case SUBJECT:
                SubjectView.showSubjects(AdminController.dataTable);
                break;
        }
    }
}

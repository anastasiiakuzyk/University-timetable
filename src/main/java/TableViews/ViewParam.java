package TableViews;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ViewParam<type>
{
    private ObservableList<type> _data;

    private TableView<type> _dataTable;

    public ViewParam(ObservableList<type> data, TableView<type> data_table)
    {
        _data = data;
        _dataTable = data_table;
    }

    public ObservableList<type> getdata() {
        return _data;
    }

    public TableView<type> getDataTable() {
        return _dataTable;
    }
}

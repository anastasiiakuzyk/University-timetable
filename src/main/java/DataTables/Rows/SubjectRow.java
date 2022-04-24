package DataTables.Rows;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectRow implements IObject
{
    private int _id;

    private String _name;

    @Override
    public void FillObject(ResultSet resultSet) {
        try
        {
            _id = resultSet.getInt(1);
            _name = resultSet.getString(2);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public int get_id() { return _id; }

    public String get_name() { return _name; }
}

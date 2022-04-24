package DataTables.Rows;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CorpusRow implements IObject
{
    private int _id;

    private int _corpNumber;

    private String _address;

    @Override
    public void FillObject(ResultSet resultSet) {
        try {
            _id = resultSet.getInt(1);
            _corpNumber = resultSet.getInt(2);
            _address = resultSet.getString(3);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int get_id() {
        return _id;
    }

    public int get_corpNumber() {
        return _corpNumber;
    }

    public String get_address() {
        return _address;
    }
}

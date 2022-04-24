package DataTables.Rows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class GraphRow implements IObject
{
    private int _id;

    private Time _tto;

    private Time _tfrom;

    @Override
    public void FillObject(ResultSet resultSet) {
        try
        {
            _id = resultSet.getInt(1);
            _tto = resultSet.getTime(2);
            _tfrom = resultSet.getTime(3);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public int get_id() {
        return _id;
    }

    public Time get_tto() {
        return _tto;
    }

    public Time get_tfrom() {
        return _tfrom;
    }
}

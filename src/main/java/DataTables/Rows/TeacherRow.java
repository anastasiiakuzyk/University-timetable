package DataTables.Rows;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherRow implements  IObject
{
    private int _id;

    private String _fullName;

    private String _position;

    public TeacherRow() {
    }

    @Override
    public void FillObject(ResultSet resultSet) {
        try
        {
            _id = resultSet.getInt(1);
            _fullName = resultSet.getString(2);
            _position = resultSet.getString(3);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public int get_id() {
        return _id;
    }

    public String get_fullName() {
        return _fullName;
    }

    public String get_position() {
        return _position;
    }

    public void set_fullName(String _fullName) {
        this._fullName = _fullName;
    }

    public void set_position(String _position) {
        this._position = _position;
    }
}

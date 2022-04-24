package DataTables.Rows;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CathedraRow implements IObject
{
    // Колонка id таблицы Cathedra
    private int _id;
    // Колонка name таблицы Cathedra
    private String _name;
    // Колонка abr таблицы Cathedra
    private String _abr;

    @Override
    public void FillObject(ResultSet resultSet) {
        try {
            _id = resultSet.getInt(1);
            _name = resultSet.getString(2);
            _abr = resultSet.getString(3);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_abr() {
        return _abr;
    }
}

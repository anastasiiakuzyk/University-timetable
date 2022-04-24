package DataTables.Rows;

import DataTables.SqlActions.SqlClient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyCathedraRow implements IObject
{
    private int _facultyId;

    private int _cathedraId;

    @Override
    public void FillObject(ResultSet resultSet) {
        try
        {
            _facultyId = resultSet.getInt(1);
            _cathedraId = resultSet.getInt(2);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int get_facultyId() {
        return _facultyId;
    }

    public int get_cathedraId() {
        return _cathedraId;
    }

    public FacultyRow GetFaculty()
    {
        String quarry = String.format("select name from %s where id = %d",SqlClient.Tables.FACULTY, _facultyId);
        return SqlClient.CreateCommand(quarry).ExecObject(new FacultyRow());
    }

    public CathedraRow GetCathedra()
    {
        String quarry = String.format("select NAME from %s where id = %d",SqlClient.Tables.CATHEDRA, _cathedraId);
        return SqlClient.CreateCommand(quarry).ExecObject(new CathedraRow());
    }

}

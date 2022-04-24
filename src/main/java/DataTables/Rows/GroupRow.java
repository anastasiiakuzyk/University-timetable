package DataTables.Rows;

import DataTables.SqlActions.SqlClient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRow implements IObject
{
    private int _id;

    private int _specialityId;

    private int _cathedraId;

    private String _groupCode;

    private int _amountOfStudents;

    private int _course;

    @Override
    public void FillObject(ResultSet resultSet) {
        try {
            _id = resultSet.getInt(1);
            _specialityId = resultSet.getInt(2);
            _cathedraId = resultSet.getInt(3);
            _groupCode = resultSet.getString(4);
            _amountOfStudents = resultSet.getInt(5);
            _course = resultSet.getInt(6);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public int get_id() {
        return _id;
    }

    public int get_specialityId() {
        return _specialityId;
    }

    public int get_cathedraId() {
        return _cathedraId;
    }

    public String get_groupCode() {
        return _groupCode;
    }

    public int get_amountOfStudents() {
        return _amountOfStudents;
    }

    public int get_course() {
        return _course;
    }

    public SpecialityRow GetSpeciality()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.SPECIALITY, _specialityId);
        return SqlClient.CreateCommand(quarry).ExecObject(new SpecialityRow());
    }

    public CathedraRow GetCathedra()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.CATHEDRA, _cathedraId);
        return SqlClient.CreateCommand(quarry).ExecObject(new CathedraRow());
    }
}

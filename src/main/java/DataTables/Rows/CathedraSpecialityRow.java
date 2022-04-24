package DataTables.Rows;

import DataTables.SqlActions.SqlClient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CathedraSpecialityRow implements IObject
{
    private int _cathedraId;

    private int _specialityId;

    @Override
    public void FillObject(ResultSet resultSet) {
        try {
            _cathedraId = resultSet.getInt(1);
            _specialityId = resultSet.getInt(2);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public int get_cathedraId() {
        return _cathedraId;
    }

    public int get_specialityId() {
        return _specialityId;
    }

    public SpecialityRow GetSpeciality()
    {
        String quarry = String.format("select name from %s where id = %d",SqlClient.Tables.SPECIALITY, _specialityId);
        return SqlClient.CreateCommand(quarry).ExecObject(new SpecialityRow());
    }

    public CathedraRow GetCathedra()
    {
        String quarry = String.format("select name from %s where id = %d",SqlClient.Tables.CATHEDRA, _specialityId);
        return SqlClient.CreateCommand(quarry).ExecObject(new CathedraRow());
    }



}

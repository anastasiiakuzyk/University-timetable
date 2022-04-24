package DataTables.Rows;

import DataTables.SqlActions.SqlClient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecialityRow implements IObject
{
    private int _id;

    private String _name;

    private int _planId;

    @Override
    public void FillObject(ResultSet resultSet) {
        try
        {
            _id = resultSet.getInt(1);
            _name = resultSet.getString(2);
            _planId = resultSet.getInt(3);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public int get_planId() {
        return _planId;
    }

    public TeachPlanRow GetPlan()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.TEACHPLAN, _planId);

        return SqlClient.CreateCommand(quarry).ExecObject(new TeachPlanRow());
    }
}

package DataTables.Rows;

import DataTables.SqlActions.SqlClient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeTableRow implements IObject
{
    // Колонка ID таблицы TimeTable
    private int _id;
    // Колонка graghId таблицы TimeTable
    private int _graphId;
    // Колонка loadID таблицы TimeTable
    private int _loadId;
    // Колонка AudienceID таблицы TimeTable
    private int _audienceId;
    // Колонка weekNumber таблицы TimeTable
    private int _weekNumber;
    // Колонка weekDay таблицы TimeTable
    private String _weekDay;

    @Override
    public void FillObject(ResultSet resultSet) {
        try
        {
            _id = resultSet.getInt(1);
            _graphId = resultSet.getInt(2);
            _loadId = resultSet.getInt(3);
            _audienceId = resultSet.getInt(4);
            _weekNumber = resultSet.getInt(5);
            _weekDay = resultSet.getString(6);
        }
        catch (SQLException e)
        {
            e.getStackTrace();
        }
    }

    public int get_id() {
        return _id;
    }

    public int get_graphId() {
        return _graphId;
    }

    public int get_loadId() {
        return _loadId;
    }

    public int get_audienceId() {
        return _audienceId;
    }

    public int get_weekNumber() {
        return _weekNumber;
    }

    public String get_weekDay() {
        return _weekDay;
    }

    public GraphRow GetGraph()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.GRAPH, _graphId);
        return SqlClient.CreateCommand(quarry).ExecObject(new GraphRow());
    }

    public LoadRow GetLoad()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.LOAD, _loadId);
        return SqlClient.CreateCommand(quarry).ExecObject(new LoadRow());
    }

    public AudienceRow GetAudience()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.AUDIENCE, _audienceId);
        return SqlClient.CreateCommand(quarry).ExecObject(new AudienceRow());
    }
}

package DataTables.Rows;

import DataTables.SqlActions.SqlClient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AudienceRow implements IObject
{
    // Поле id с таблицы Audience
    private int _id;
    // Поле audienceNumber с таблицы Audience
    private int _audienceNumber;
    // Поле lectureType с таблицы Audience
    private String _lectureType;
    // Поле places с таблицы Audience
    private int _places;
    // Поле corpusId с таблицы Audience
    private int _corpusId;

    @Override
    public void FillObject(ResultSet resultSet) {
        try
        {
            _id = resultSet.getInt(1);
            _audienceNumber = resultSet.getInt(2);
            _lectureType = resultSet.getString(3);
            _places = resultSet.getInt(4);
            _corpusId = resultSet.getInt(5);
        }
        catch (SQLException e)
        {
            e.getStackTrace();
        }
    }

    public int get_id() {
        return _id;
    }

    public int get_audienceNumber() {
        return _audienceNumber;
    }

    public String get_lectureType() {
        return _lectureType;
    }

    public int get_places() {
        return _places;
    }

    public int get_corpusId() {
        return _corpusId;
    }

    public CorpusRow GetCorpus()
    {
        String quarry = String.format("select CorpNumber from %s where id = %d", SqlClient.Tables.CORPUS, _corpusId);
        return SqlClient.CreateCommand(quarry).ExecObject(new CorpusRow());
    }
}

package DataTables.Rows;

import DataTables.SqlActions.SqlClient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyRow implements IObject
{
    private int _id;

    private String _name;

    private int _corpusId;

    private String _abr;

    @Override
    public void FillObject(ResultSet resultSet) {
        try
        {
            _id = resultSet.getInt(1);
            _name = resultSet.getString(2);
            _corpusId = resultSet.getInt(3);
            _abr = resultSet.getString(4);
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

    public int get_corpusId() {
        return _corpusId;
    }

    public String get_abr() {
        return _abr;
    }

    public int GetCorpusNumber()
    {
        String quarry = String.format("select CorpusNumber from Corpus where id = %d", _corpusId);
        return SqlClient.CreateCommand(quarry).ExecInt();
    }

    public CorpusRow GetCorpus()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.CORPUS, _corpusId);
        return SqlClient.CreateCommand(quarry).ExecObject(new CorpusRow());
    }
}

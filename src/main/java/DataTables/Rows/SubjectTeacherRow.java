package DataTables.Rows;

import DataTables.SqlActions.SqlClient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectTeacherRow implements IObject
{
    private int _subjectId;

    private int _teacherId;

    @Override
    public void FillObject(ResultSet resultSet) {
        try
        {
            _subjectId = resultSet.getInt(1);
            _teacherId = resultSet.getInt(2);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public int get_subjectId() {
        return _subjectId;
    }

    public int get_teacherId() {
        return _teacherId;
    }

    public SubjectRow GetSubject()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.SUBJECT, _subjectId);
        return SqlClient.CreateCommand(quarry).ExecObject(new SubjectRow());
    }

    public SubjectRow GetTeacher()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.TEACHER, _teacherId);
        return SqlClient.CreateCommand(quarry).ExecObject(new TeacherRow());
    }
}

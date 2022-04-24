package DataTables.Rows;

import DataTables.SqlActions.SqlClient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadRow implements IObject
{
    private int _id;

    private int _groupId;

    private int _subjectId;

    private int _teacherId;

    private String _lessonType;

    private int _hoursInWeek;


    @Override
    public void FillObject(ResultSet resultSet) {
        try
        {
            _id = resultSet.getInt(1);
            _groupId = resultSet.getInt(2);
            _subjectId = resultSet.getInt(3);
            _teacherId = resultSet.getInt(4);
            _lessonType = resultSet.getString(5);
            _hoursInWeek = resultSet.getInt(6);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public int get_id() {
        return _id;
    }

    public int get_groupId() {
        return _groupId;
    }

    public int get_subjectId() {
        return _subjectId;
    }

    public int get_teacherId() {
        return _teacherId;
    }

    public String get_lessonType() {
        return _lessonType;
    }

    public int get_hoursInWeek() {
        return _hoursInWeek;
    }

    public GroupRow GetGroup()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.GROUP, _groupId);
        return SqlClient.CreateCommand(quarry).ExecObject(new GroupRow());
    }

    public SubjectRow GetSubject()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.SUBJECT, _subjectId);
        return SqlClient.CreateCommand(quarry).ExecObject(new SubjectRow());
    }

    public TeacherRow GetTeacher()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.TEACHER, _teacherId);
        return SqlClient.CreateCommand(quarry).ExecObject(new TeacherRow());
    }
}

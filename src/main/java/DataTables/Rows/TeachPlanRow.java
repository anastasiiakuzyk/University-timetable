package DataTables.Rows;

import DataTables.SqlActions.SqlClient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeachPlanRow implements IObject
{
    private int _specialityId;

    private int _semestr;

    private int _subjectId;

    private int _wholeHours;

    private int _lectureHours;

    private int _labHours;

    private int _practHours;


    @Override
    public void FillObject(ResultSet resultSet) {
        try {

            _specialityId = resultSet.getInt(1);
            _semestr = resultSet.getInt(2);
            _subjectId = resultSet.getInt(3);
            _wholeHours = resultSet.getInt(4);
            _lectureHours = resultSet.getInt(5);
            _labHours = resultSet.getInt(6);
            _practHours = resultSet.getInt(7);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public int get_specialityId() {
        return _specialityId;
    }

    public int get_semestr() {
        return _semestr;
    }

    public int get_subjectId() {
        return _subjectId;
    }

    public int get_wholeHours() {
        return _wholeHours;
    }

    public int get_lectureHours() {
        return _lectureHours;
    }

    public int get_labHours() {
        return _labHours;
    }

    public int get_practHours() {
        return _practHours;
    }

    public SpecialityRow GetSpeciality()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.SPECIALITY, _specialityId);
        return SqlClient.CreateCommand(quarry).ExecObject(new SpecialityRow());
    }

    public SubjectRow GetSubject()
    {
        String quarry = String.format("select * from %s where id = %d", SqlClient.Tables.SUBJECT, _subjectId);
        return SqlClient.CreateCommand(quarry).ExecObject(new SubjectRow());
    }

}

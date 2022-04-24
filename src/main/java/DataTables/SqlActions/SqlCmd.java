package DataTables.SqlActions;

import DataTables.Rows.IObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class SqlCmd {
    private static Connection _connection;
    private static Statement _statement;
    private static ResultSet _result;
    private String _quarry;

    public SqlCmd(String quarry)
    {
        _quarry = quarry;
    }

    public static Connection getConnection() { return _connection; }

    static
    {
        try
        {
            _connection = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;databaseName=rozkladDB;user=tester;password=pass;");
//                    "jdbc:sqlserver://localhost:1433;databaseName=rozklad;user=tester;password=pass;");

            _statement = _connection.createStatement();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public int ExecInt()
    {
        return Integer.parseInt(execQuarry());
    }

    public String ExecString()
    {
        return execQuarry();
    }

    public double ExecDouble()
    {
        return  Double.parseDouble(execQuarry());
    }

    public Date ExecDate()
    {
        return Date.valueOf(execQuarry());
    }

    public List<Integer> ExecIntegers()
    {
        List<Integer> list = new ArrayList<>();
        execQuarry();

        try
        {
            do
            {
                String currentRowValue = _result.getString(1);
                list.add(Integer.parseInt(currentRowValue));
            }
            while (_result.next());
        }
        catch (SQLException e)
        {
            e.getStackTrace();
        }

        return list;
    }

    public List<Double> ExecDoubles()
    {
        List<Double> list = new ArrayList<>();

        execQuarry();

        try
        {
            do
            {
                String currentRowValue = _result.getString(1);
                list.add(Double.parseDouble(currentRowValue));
            }
            while (_result.next());
        }
        catch (SQLException e)
        {
            e.getStackTrace();
        }

        return list;
    }

    public List<Date> ExecDates()
    {
        List<Date> list = new ArrayList<>();

        execQuarry();
        try
        {
            do
            {
                String currentRowValue = _result.getString(1);
                list.add(Date.valueOf(currentRowValue));
            }
            while (_result.next());
        }
        catch (SQLException e)
        {
            e.getStackTrace();
        }

        return list;
    }

    public List<String> ExecStrings()
    {
        List<String> list = new ArrayList<>();

        execQuarry();
        try
        {
            do
            {
                String currentRowValue = _result.getString(1);
                list.add(currentRowValue);
            }
            while (_result.next());
        }
        catch (SQLException e)
        {
            e.getStackTrace();
        }

        return list;
    }

    public <type extends IObject> type ExecObject(IObject object)
    {
        type res = null;
        try {
            res = (type) object.getClass().newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        execQuarry();
        res.FillObject(_result);

        return res;
    }

    public <type extends IObject> List<type> ExecObjects(IObject object)
    {
        List<type> list = new ArrayList<>();

        execQuarry();

        try
        {
            do
            {
                type tmp = (type) object.getClass().newInstance();
                tmp.FillObject(_result);

                list.add(tmp);
            }
            while(_result.next());
        }
        catch (SQLException e)
        {
            e.getStackTrace();
        }
        catch (IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace();
        }

        return list;
    }

    public void ExecNonQuarry()
    {
        try {

            _statement.executeUpdate(_quarry);
        }
        catch (SQLException e)
        {
            e.getStackTrace();
        }
    }

    private String execQuarry()
    {
        try
        {
            _result = _statement.executeQuery(_quarry);
            _result.next();
            return _result.getString(1);
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }

        return null;
    }

    public Dictionary<String, String> ExecObject()
    {
        Dictionary<String,String> result = new Hashtable<>();

        List<String> fields = FindFields();

        execQuarry();

        try
        {
            for (int i = 0; i < fields.size(); i++)
            {
                result.put(fields.get(i), _result.getString(i + 1));
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return result;
    }

    public List<Dictionary<String, String>> ExecObjects()
    {
        List<Dictionary<String,String>> result = new ArrayList<Dictionary<String,String>>();

        List<String> fields = FindFields();

        execQuarry();

        try
        {

            do
            {
                Dictionary<String, String> tmp = new Hashtable<>();
                for (int i = 0; i < fields.size(); i++) {
                    tmp.put(fields.get(i), _result.getString(i + 1));
                }
                result.add(tmp);
            }
            while(_result.next());
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return result;
    }

    public List<String> FindFields()
    {
        List<String> fields = new ArrayList<>();

        String[] quarryParts = _quarry.split("(\\s*,\\s*) | (\\s*)");

        for (String part : quarryParts)
        {
            part = part.toLowerCase();

            if (part.equals("select"))
            {
                continue;
            }
            if (part.equals("from"))
            {
                break;
            }

            fields.add(part);
        }

        return fields;
    }
}

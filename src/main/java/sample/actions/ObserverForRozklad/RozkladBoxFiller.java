package sample.actions.ObserverForRozklad;

import DataTables.Rows.GraphRow;
import DataTables.SqlActions.SqlClient;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import sample.AdminController;
import sample.Commands;
import sample.animations.Shake;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import static sample.AdminController.editMode.ADD;

public class RozkladBoxFiller
        implements IObserver {
    private IObservable observable;
    private String currTeacher = "";
    private String currSubject = "";
    private String currPair = "";
    private String currDay = "";
    private String currGroup = "";
    private String currAudience = "";

    private static List<ComboBox> comboBoxes;

    public RozkladBoxFiller(IObservable observable, List<ComboBox> comboBoxes) {
        observable.addObserver(this);
        this.comboBoxes = comboBoxes;
        this.observable = observable;
    }

    @Override
    public void saveChanges(ObserverParams params) {
        currAudience = params.getAudience();
        currDay = params.getDay();
        currSubject = params.getSubject();
        currTeacher = params.getTeacher();
        currGroup = params.getGroup();
        currPair = params.getPairNumber();
    }

    public void fill() {
        if (currSubject.equals("") || currTeacher.equals("")) {
            comboBoxes.get(0).setItems(fillSubjects());
            comboBoxes.get(1).setItems(fillTeachers());
        } else {
            List<String> check = SqlClient.CreateCommand(
                    String.format("select %s.name from %s join %s on subjectid = %s.id where teacherid = " +
                                    "(select teacher.id from teacher where teacher.fullname = '%s')",
                            SqlClient.Tables.SUBJECT, SqlClient.Tables.SUBJECTTEACHER, SqlClient.Tables.SUBJECT,
                            SqlClient.Tables.SUBJECT, currTeacher)).ExecStrings();
            boolean isChecked = true;
            for (String subj : check) {
                if (subj.equals(currSubject)) {
                    isChecked = false;
                    break;
                }
            }
            if (isChecked) {
                currTeacher = "";
                comboBoxes.get(1).getSelectionModel().clearSelection();
                comboBoxes.get(1).setItems(fillTeachers());
            } else {
                check.clear();
                check = SqlClient.CreateCommand(
                        String.format("select %s.fullname from %s join %s on teacherid = %s.id where subjectid = " +
                                        "(select [subject].id from [subject] where [subject].name = '%s')",
                                SqlClient.Tables.TEACHER, SqlClient.Tables.SUBJECTTEACHER, SqlClient.Tables.TEACHER,
                                SqlClient.Tables.TEACHER, currSubject)).ExecStrings();
                for (String teacher : check) {
                    if (teacher.equals(currSubject)) {
                        isChecked = false;
                        break;
                    }
                }
                if (isChecked) {
                    currSubject = "";
                    comboBoxes.get(0).getSelectionModel().clearSelection();
                    comboBoxes.get(0).setItems(fillSubjects());
                }
            }
        }
        if (currGroup.equals("") || currAudience.equals("")) {
            comboBoxes.get(5).setItems(fillAudience());
            comboBoxes.get(2).setItems(fillGroup());
        }
        else
            {
            List<String> audiences = SqlClient.CreateCommand(String.format("select audienceNumber from AUDIENCE, [group] " +
                    "where AmountOfStudents <= places and groupcode = '%s'", currGroup)).ExecStrings();

            boolean isValid = false;

            for (String audience : audiences) {
                if (currAudience.equals(audience)) {
                    isValid = true;
                    break;
                }
            }

            if (!isValid) {
                currAudience = "";
                comboBoxes.get(5).getSelectionModel().clearSelection();
                comboBoxes.get(5).setItems(fillAudience());
            } else {
                List<String> groups = SqlClient.CreateCommand(String.format("select groupcode from AUDIENCE, [group] " +
                                "where AmountOfStudents <= places and audienceNumber = %d",
                        Integer.parseInt(currAudience))).ExecStrings();

                for (String group : groups) {
                    if (currGroup.equals(group)) {
                        isValid = true;
                        break;
                    }
                }

                if (!isValid) {
                    currGroup = "";
                    comboBoxes.get(2).getSelectionModel().clearSelection();
                    comboBoxes.get(2).setItems(fillGroup());
                }
            }
        }
        if (currPair.equals("")) {
            comboBoxes.get(3).setItems(fillGraph());
        }
        if (currDay.equals("")) {
            comboBoxes.get(4).setItems(fillWeekDays());
        }
    }

    private ObservableList<String> fillSubjects() {
        String quarry = currTeacher.equals("") ? String.format("select name from %s", SqlClient.Tables.SUBJECT)
                : String.format("select %s.name from %s join %s on %s.id = subjectid" +
                        " join %s on %s.id = teacherid where %s.fullname = '%s'",
                SqlClient.Tables.SUBJECT, SqlClient.Tables.SUBJECTTEACHER, SqlClient.Tables.SUBJECT, SqlClient.Tables.SUBJECT,
                SqlClient.Tables.TEACHER, SqlClient.Tables.TEACHER, SqlClient.Tables.TEACHER, currTeacher);

        List<String> objects = SqlClient.CreateCommand(quarry).ExecStrings();

        return FXCollections.observableArrayList(objects);
    }

    private ObservableList<String> fillGraph() {
        List<Dictionary<String, String>> dictionaries = SqlClient.CreateCommand(String.format("select id from %s", SqlClient.Tables.GRAPH)).ExecObjects();

        List<String> items = new ArrayList<>();
        for (Dictionary<String, String> unit : dictionaries) {
            items.add(unit.get("id"));
        }

        return FXCollections.observableArrayList(items);
    }

    private ObservableList<String> fillTeachers() {
        List<String> items = new ArrayList<>();

        String quarry = currSubject.equals("") ? String.format("select fullname from %s", SqlClient.Tables.TEACHER)
                : String.format("select fullname from %s join %s on %s.id = subjectid" +
                        " join %s on %s.id = teacherid where %s.name = '%s'",
                SqlClient.Tables.SUBJECTTEACHER, SqlClient.Tables.SUBJECT, SqlClient.Tables.SUBJECT,
                SqlClient.Tables.TEACHER, SqlClient.Tables.TEACHER, SqlClient.Tables.SUBJECT, currSubject);

        List<String> objects = SqlClient.CreateCommand(quarry).ExecStrings();

        return FXCollections.observableArrayList(objects);
    }

    private ObservableList<String> fillAudience() {
        String quarry = currGroup.equals("") ? String.format("select audiencenumber from %s", SqlClient.Tables.AUDIENCE)
                : String.format("select audiencenumber from %s where places >= %d", SqlClient.Tables.AUDIENCE,
                SqlClient.CreateCommand(String.format("select amountofstudents from %s where groupcode = '%s'",
                        SqlClient.Tables.GROUP, currGroup)).ExecInt());

        List<String> objects = SqlClient.CreateCommand(quarry).ExecStrings();

        return FXCollections.observableArrayList(objects);
    }

    private ObservableList<String> fillGroup() {
        String quarry = currAudience.equals("") ? String.format("select groupcode from %s", SqlClient.Tables.GROUP)
                : String.format("select groupcode from %s where amountofstudents <= %d", SqlClient.Tables.GROUP,
                SqlClient.CreateCommand(String.format("select places from %s where audiencenumber = %d",
                        SqlClient.Tables.AUDIENCE, Integer.parseInt(currAudience))).ExecInt());

        List<String> objects = SqlClient.CreateCommand(quarry).ExecStrings();

        return FXCollections.observableArrayList(objects);
    }

    private ObservableList<String> fillWeekDays() {
        List<String> weekDays = new ArrayList<>();
        weekDays.add("Понеділок");
        weekDays.add("Вівторок");
        weekDays.add("Середа");
        weekDays.add("Четверг");
        weekDays.add("П`ятниця");

        return FXCollections.observableArrayList(weekDays);
    }

    public void register(AdminController.editMode mode) {
        if (currGroup.equals("") && currAudience.equals("") && currTeacher.equals("") &&
                currSubject.equals("") && currDay.equals("") && currPair.equals("")) {
            return;
        }

        if (mode == ADD) {
            int groupId = SqlClient.CreateCommand(
                    String.format("select id from [group] where groupcode = '%s'", currGroup)).ExecInt();
            int subjectId = SqlClient.CreateCommand(
                    String.format("select id from [subject] where name = '%s'", currSubject)).ExecInt();
            int teacherId = SqlClient.CreateCommand(
                    String.format("select id from [teacher] where fullname = '%s'", currTeacher)).ExecInt();

            int audienceId = SqlClient.CreateCommand(
                    String.format("select id from %s where audienceNumber = %d",
                            SqlClient.Tables.AUDIENCE, Integer.parseInt(currAudience))).ExecInt();

            String loadId = SqlClient.CreateCommand(String.format("select id form load " +
                    "where groupid = %d and subjectid = %d and teacherid = %d", groupId, subjectId, teacherId)).ExecString();
            if (loadId == null)
            {
                String quarry1 = String.format("insert into %s (groupid, subjectid, teacherid)" +
                        " values(%d,%d,%d)", SqlClient.Tables.LOAD, groupId, subjectId, teacherId);
                SqlClient.CreateCommand(quarry1).ExecNonQuarry();

                loadId = SqlClient.CreateCommand(String.format("select max(id) from %s", SqlClient.Tables.LOAD)).ExecString();
            }

            String quarry2 = String.format("insert into timetable (graphId, loadId, audienceId, dayinweek) " +
                    "values(%d,%d,%d, '%s')", Integer.parseInt(currPair), Integer.parseInt(loadId), audienceId, currDay);
            SqlClient.CreateCommand(quarry2).ExecNonQuarry();

        }
        else {

        }

    }
}



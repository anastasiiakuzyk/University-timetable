package sample;

import DataTables.SqlActions.SqlClient;
import DataTables.SqlActions.SqlCmd;
import TableViews.TableViewer;
import javafx.scene.control.TableView;

public class RozklTables {
    public static void showSubjTeach(TableView data_table){
        String q = String.format("select [subject].name as [Предмет], teacher.fullname as [Викладач], teacher.position as [Посада]" +
                        " from %s join [subject] on [subject].id = subjectid" +
                        " join teacher on teacher.id = teacherid",
                SqlClient.Tables.SUBJECTTEACHER);
        TableViewer.Show(q, data_table);
    }

    public static void showRozkl(TableView data_table){
        String q = String.format("SELECT dayInWeek as [День], Graph.TFROM AS [Початок], Graph.Tto as [Кінець], " +
                "AUDIENCE.audienceNumber as [Ауд.], [GROUP].GroupCode as [Група], [Subject].NAME as [Предмет], FullName as [Викладач] " +
                "FROM %s " +
                "JOIN Graph ON GraphID = Graph.ID " +
                "JOIN Load ON loadID = Load.ID " +
                "JOIN AUDIENCE ON audienceID = AUDIENCE.ID " +
                "JOIN [GROUP] ON Load.groupID = [GROUP].ID " +
                "JOIN [Subject] ON Load.subjectID = [Subject].ID " +
                "JOIN Teacher ON Load.teacherID = Teacher.ID ",SqlClient.Tables.TIMETABLE);
        TableViewer.Show(q, data_table);
    }

    public static void showGroup(TableView data_table)
    {
        String quarry = String.format(
                "select [group].groupcode as [Код групи], cathedra.abr as [Кафедра], speciality.id as [Код спеціальності], " +
                        "speciality.name as [Назва спеціальності], [group].amountofstudents as [К-сть студентів], [group].course as [Курс] " +
                        "from %s " +
                        "join speciality on speciality.id = specialityid " +
                        "join cathedra on cathedra.id = cathedraid", SqlClient.Tables.GROUP);
        TableViewer.Show(quarry, data_table);
    }
    public static void findRozkladByGroup(TableView data_table, String groupCode){
        String q = String.format("SELECT dayInWeek as [День], Graph.TFROM AS [Початок], Graph.Tto as [Кінець], " +
                "AUDIENCE.audienceNumber as [Ауд.], [GROUP].GroupCode as [Група], [Subject].NAME as [Предмет], FullName as [Викладач] " +
                "FROM %s " +
                "JOIN Graph ON GraphID = Graph.ID " +
                "JOIN Load ON loadID = Load.ID " +
                "JOIN AUDIENCE ON audienceID = AUDIENCE.ID " +
                "JOIN [GROUP] ON Load.groupID = [GROUP].ID " +
                "JOIN [Subject] ON Load.subjectID = [Subject].ID " +
                "JOIN Teacher ON Load.teacherID = Teacher.ID " +
                "WHERE [GROUP].GroupCode = '%s'",SqlClient.Tables.TIMETABLE, groupCode );
        TableViewer.Show(q, data_table);
    }
    public static void findRozkladByTeacher(TableView data_table, String groupCode){
        String q = String.format("SELECT dayInWeek as [День], Graph.TFROM AS [Початок], Graph.Tto as [Кінець], " +
                "AUDIENCE.audienceNumber as [Ауд.], [GROUP].GroupCode as [Група], [Subject].NAME as [Предмет], FullName as [Викладач] " +
                "FROM %s " +
                "JOIN Graph ON GraphID = Graph.ID " +
                "JOIN Load ON loadID = Load.ID " +
                "JOIN AUDIENCE ON audienceID = AUDIENCE.ID " +
                "JOIN [GROUP] ON Load.groupID = [GROUP].ID " +
                "JOIN [Subject] ON Load.subjectID = [Subject].ID " +
                "JOIN Teacher ON Load.teacherID = Teacher.ID " +
                "WHERE Teacher.fullname = '%s'",SqlClient.Tables.TIMETABLE, groupCode );
        TableViewer.Show(q, data_table);
    }
}

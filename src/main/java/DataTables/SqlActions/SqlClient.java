package DataTables.SqlActions;

public class SqlClient {
    public enum JoinType
    {
        LEFT,
        RIGHT,
        INNER
    }

    public class Tables
    {
        public static final String TEACHER = "TEACHER";
        public static final String SPECIALITY = "SPECIALITY";
        public static final String SUBJECT = "[SUBJECT]";
        public static final String SUBJECTTEACHER = "[SUBJECT/TEACHER]";
        public static final String TEACHPLAN = "TEACHPLAN";
        public static final String LOAD = "LOAD";
        public static final String GRAPH = "GRAPH";
        public static final String GROUP = "[GROUP]";
        public static final String TIMETABLE = "TIMETABLE";
        public static final String FACULTY = "FACULTY";
        public static final String FACULTYCATHEDRA = "[FACULTY/CATHEDRA]";
        public static final String CORPUS = "CORPUS";
        public static final String CATHEDRASPECIALITY = "[CATHEDRA/SPECIALITY]";
        public static final String CATHEDRA = "CATHEDRA";
        public static final String AUDIENCE = "AUDIENCE";
        public static final String LOGINS = "LOGINS";
    }

    public static SqlCmd CreateCommand(String quarry)
    {
        return new SqlCmd(quarry);
    }
}

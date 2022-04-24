package sample.actions.ObserverForRozklad;

public class ObserverParams
{
    private String teacher;
    private String group;
    private String subject;
    private String audience;
    private String day;
    private String pairNumber;

    public ObserverParams(String teacher, String group, String subject, String audience, String day, String pairNumber) {
        this.teacher = teacher;
        this.group = group;
        this.subject = subject;
        this.audience = audience;
        this.day = day;
        this.pairNumber = pairNumber;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getGroup() {
        return group;
    }

    public String getSubject() {
        return subject;
    }

    public String getAudience() {
        return audience;
    }

    public String getDay() {
        return day;
    }

    public String getPairNumber() {
        return pairNumber;
    }
}

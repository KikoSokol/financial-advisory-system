package sk.stu.fei.uim.bp.application.validarors.messages;

public class CalendarValidatorsMessages
{
    public static String MISSING_CLIENT =  "Klient nebol zadaný!";

    public static String END_BEFORE_START = "Čas ukončenia stretnutia nemôže byť pred začiatkom stretnutia!";
    public static String START_AFTER_END = "Čas začiatku stretnutia nemôže byť po čase ukončenia stretnutia!";

    public static String NOT_FREE_TIME_FOR_MEETING = "V tomto čase už je naplánované iné stretnutie";
}

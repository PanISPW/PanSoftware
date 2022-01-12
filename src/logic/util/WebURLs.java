package logic.util;

// @author Danilo D'Amico

public class WebURLs {

    private final static String ABOUT = "http://localhost:8081/PanSoftware/Pages/About.jsp";
    private final static String EVENTS = "http://localhost:8081/PanSoftware/Pages/Events.jsp";
    private final static String GIVEADVICE = "http://localhost:8081/PanSoftware/Pages/GiveAdvice.jsp";
    private final static String LOGIN = "http://localhost:8081/PanSoftware/Pages/Login.jsp";
    private final static String MANAGEEVENTPARTICIPATION = "http://localhost:8081/PanSoftware/Pages/ManageEventParticipations.jsp";
    private final static String NAVBAR = "http://localhost:8081/PanSoftware/Pages/Navbar.jsp";
    private final static String NEWGOAL = "http://localhost:8081/PanSoftware/Pages/NewGoal.jsp";
    private final static String PROFILE = "http://localhost:8081/PanSoftware/Pages/Profile.jsp";

    public static String getAbout() {
        return ABOUT;
    }

    public static String getEvents() {
        return EVENTS;
    }

    public static String getGiveAdvice() {
        return GIVEADVICE;
    }

    public static String getLogin() {
        return LOGIN;
    }

    public static String getManageEventParticipation() {
        return MANAGEEVENTPARTICIPATION;
    }

    public static String getNavbar() {
        return NAVBAR;
    }

    public static String getNewGoal() {
        return NEWGOAL;
    }

    public static String getProfile() {
        return PROFILE;
    }
}

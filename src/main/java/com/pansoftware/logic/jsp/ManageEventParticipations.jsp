<%@ page import="com.pansoftware.logic.LoginController" %>
<%@ page import="com.pansoftware.logic.ManageGoalController" %>
<%@ page import="com.pansoftware.logic.bean.EventGoalBean" %>
<%@ page import="com.pansoftware.logic.enumeration.UserRole" %>
<%@ page import="com.pansoftware.logic.exception.*" %>
<%@ page import="javax.security.auth.login.LoginException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%
if (request.getParameter("accept") != null | request.getParameter("reject") != null){

    try {
        final EventGoalBean bean = new EventGoalBean();
        bean.setId(Integer.parseInt(request.getParameter("goalid")));
        bean.setUser(request.getParameter("user"));

        if(request.getParameter("reject") != null)
            ManageGoalController.rejectEventGoal(bean);
        else
            ManageGoalController.acceptEventGoal(bean);

    } catch (final InvalidDataException | UserNotFoundException | LoginException | DatabaseException | NoTransitionException | EmptyResultSetException e) {
        e.printStackTrace();
    }

}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <meta charset="ISO-8859-1">
    <title>Manage Event Participations</title>
</head>

<!-- @author Danilo D'Amico -->

<body>
<%
    try {
       if(LoginController.getUserRole().equals(UserRole.BRANDMANAGER)){
%>
<jsp:include page="BrandNavbar.jsp"/>
<%
} else {
%>
<jsp:include page="Login.jsp"/>
<%
    }
} catch (final InvalidDataException e) {
%>
<jsp:forward page="Login.jsp"/>
<%
    } %>

<h1>Manage Events Participations Page</h1>

<div class=" d-flex justify-content-center">
    <div class="card-columns">
        <%
            List<EventGoalBean> goals = null;
            try {
                goals = ManageGoalController.getPendingEventGoalBeanList();
            } catch (final UserNotFoundException | EmptyResultSetException | LoginException | DatabaseException | InvalidDataException e) {
                e.printStackTrace();
            }
            if(goals != null){
            for (final EventGoalBean e  : goals) {
        %>
        <div class="card text-center mt-2 ">
            <div class="card-body">
                <form action="ManageEventParticipations.jsp" name="manageGoalForm" method="POST">

                    <h5 class="card-title"><%=e.getName() + " \nId: #" + e.getId()%></h5>
                    <h5 class="card-title text-muted"><%="Author: " + e.getUser()%></h5>
                    <input type="hidden" id="goalid" name="goalid" value="<%=e.getId()%>">
                    <input type="hidden" id="user" name="user" value="<%=e.getUser()%>">

                    <h6 class="card-duration mb-2 text-muted"><%="Deadline: " + e.getDeadline()%></h6>
                    <p class="card-duration mb-2 text-muted"><%=e.getDescription()%></p>
                    <p class="card-duration mb-2"><%="Steps: " + e.getStepsCompleted() + "/" + e.getNumberOfSteps()%></p>

                    <button class="w-100 btn btn-lg btn-primary half-top-buffer"
                            type="submit" name="accept" value="accept">Accept
                    </button>

                    <button class="w-100 btn btn-lg btn-primary half-top-buffer"
                            type="submit" name="reject" value="reject">Reject
                    </button>
                </form>
            </div>
        </div>
        <% } }%>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>
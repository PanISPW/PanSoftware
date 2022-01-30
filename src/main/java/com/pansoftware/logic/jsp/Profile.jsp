<%@ page import="com.pansoftware.logic.LoginController" %>
<%@ page import="com.pansoftware.logic.ManageGoalController" %>
<%@ page import="com.pansoftware.logic.bean.UpdateStepsBean" %>
<%@ page import="com.pansoftware.logic.enumeration.GoalType" %>
<%@ page import="com.pansoftware.logic.enumeration.UserRole" %>
<%@ page import="com.pansoftware.logic.exception.DatabaseException" %>
<%@ page import="com.pansoftware.logic.exception.EmptyResultSetException" %>
<%@ page import="com.pansoftware.logic.exception.InvalidDataException" %>
<%@ page import="com.pansoftware.logic.exception.UserNotFoundException" %>
<%@ page import="javax.security.auth.login.LoginException" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pansoftware.logic.bean.GoalBean" %>
<%@ page import="com.pansoftware.logic.bean.AdviceGoalBean" %>
<%@ page import="com.pansoftware.logic.bean.EventGoalBean" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%
    if (request.getParameter("goal") != null | request.getParameter("advicegoal") != null | request.getParameter("eventgoal") != null) {

        System.out.println("sono entrato");

        try {
            final UpdateStepsBean bean = new UpdateStepsBean();

            if (request.getParameter("eventgoal") != null)
                bean.setType(GoalType.EVENTGOAL);
            else if (request.getParameter("advicegoal") != null)
                bean.setType(GoalType.ADVICEGOAL);
            else
                bean.setType(GoalType.GOAL);

            bean.setUpdateId(Integer.parseInt(request.getParameter("goalid")));
            bean.setStepsCompleted(Integer.parseInt(request.getParameter("stepscompleted")));
            bean.setUpdateUser(LoginController.getCurrentUser());

            ManageGoalController.updateSteps(bean);

        } catch(final InvalidDataException | UserNotFoundException | EmptyResultSetException | DatabaseException |SQLException | LoginException e){
            e.printStackTrace();
        }

    } else
        System.out.println("non sono entrato");%>

<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <meta charset="ISO-8859-1">
    <title>Profilo</title>
</head>

<!-- @author Danilo D'Amico -->

<body>
<%
    try {
        if(LoginController.getUserRole().equals(UserRole.ACTIVIST)){
%>
<jsp:include page="ActivistNavbar.jsp"/>
<%
} else if(LoginController.getUserRole().equals(UserRole.BRANDMANAGER)){
%>
<jsp:include page="BrandNavbar.jsp"/>
<%
} else {
%>
<jsp:include page="UserNavbar.jsp"/>
<%
    }
} catch (final InvalidDataException e) {
%>
<jsp:forward page="Login.jsp"/>
<%
    } %>

<h1>Profile</h1>

<ul class="nav nav-tabs" id="myTab" role="tablist">
    <li class="nav-item" role="presentation">
        <button class="nav-link active" id="goal-tab" data-bs-toggle="tab" data-bs-target="#goal" type="button" role="tab" aria-controls="goal" aria-selected="true">My Goals</button>
    </li>
    <li class="nav-item" role="presentation">
        <button class="nav-link" id="advicegoal-tab" data-bs-toggle="tab" data-bs-target="#advicegoal" type="button" role="tab" aria-controls="advicegoal" aria-selected="false">My Advice Goals</button>
    </li>
    <li class="nav-item" role="presentation">
        <button class="nav-link" id="eventgoal-tab" data-bs-toggle="tab" data-bs-target="#eventgoal" type="button" role="tab" aria-controls="eventgoal" aria-selected="false">My Event Goals</button>
    </li>
</ul>
<div class="d-flex justify-content-center align-items-center tab-content" id="myTabContent">
    <div class="tab-pane fade show active" id="goal" role="tabpanel" aria-labelledby="goal-tab">

        <div class=" d-flex justify-content-center">
            <div class="card-columns">
                <%
                    List<GoalBean> goals = null;
                    try {
                        goals = ManageGoalController.getGoalBeanList();
                    } catch (final UserNotFoundException | EmptyResultSetException | LoginException | DatabaseException | SQLException | InvalidDataException e) {
                        e.printStackTrace();
                    }
                    if(goals != null){
                    for (final GoalBean e  : goals) {
                %>
                <div class="card text-center mt-2 ">
                    <div class="card-body">
                        <form action="Profile.jsp" name="updateGoalForm" method="POST">

                            <h5 class="card-title"><%=e.getName() + " \nId: #" + e.getId()%></h5>
                            <input type="hidden" id="goalid" name="goalid" value="<%=e.getId()%>">

                            <h6 class="card-duration mb-2 text-muted"><%="Deadline: " + e.getDeadline()%></h6>
                            <p class="card-duration mb-2 text-muted"><%=e.getDescription()%></p>
                            <p class="card-duration mb-2"><%="Steps: " + e.getStepsCompleted() + "/" + e.getNumberOfSteps()%></p>

                            <input type="number" id="stepscompleted" name="stepscompleted" class="form-control"
                                   placeholder="steps completed" required min="0" max="100">

                            <button class="w-100 btn btn-lg btn-primary half-top-buffer"
                                    type="submit" name="goal" value="goal">Update
                            </button>
                        </form>
                    </div>
                </div>
                <% } }%>
            </div>
        </div>

    </div>
    <div class="tab-pane fade" id="advicegoal" role="tabpanel" aria-labelledby="advicegoal-tab">

        <div class=" d-flex justify-content-center">
            <div class="card-columns">
                <%
                    List<AdviceGoalBean> adviceGoals = null;
                    try {
                        adviceGoals = ManageGoalController.getAdviceGoalBeanList();
                    } catch (final UserNotFoundException | EmptyResultSetException | LoginException | DatabaseException | SQLException | InvalidDataException e) {
                        e.printStackTrace();
                    }
                    if(adviceGoals != null){
                    for (final AdviceGoalBean e  : adviceGoals) {
                %>
                <div class="card text-center mt-2 ">
                    <div class="card-body">
                        <form action="Profile.jsp" name="updateAdviceGoalForm" method="POST">

                            <h5 class="card-title"><%=e.getName() + " \nId: #" + e.getId()%></h5>
                            <input type="hidden" id="advicegoalid" name="goalid" value="<%=e.getId()%>">

                            <h6 class="card-duration mb-2 text-muted"><%="Deadline: " + e.getDeadline()%></h6>
                            <p class="card-duration mb-2 text-muted"><%=e.getDescription()%></p>
                            <p class="card-duration mb-2"><%="Steps: " + e.getStepsCompleted() + "/" + e.getNumberOfSteps()%></p>

                            <input type="number" id="advicestepscompleted" name="stepscompleted" class="form-control"
                                   placeholder="steps completed" required min="0" max="100">

                            <button class="w-100 btn btn-lg btn-primary half-top-buffer"
                                    type="submit" name="advicegoal" value="advicegoal">Update
                            </button>

                            <p class="card-duration mb-2"><%="Product Type: " + e.getType()%></p>
                            <p class="card-duration mb-2"><%=e.getAdvice()%></p>
                        </form>
                    </div>
                </div>
                <% } }%>
            </div>
        </div>
    </div>
    <div class="tab-pane fade" id="eventgoal" role="tabpanel" aria-labelledby="eventgoal-tab">

        <div class=" d-flex justify-content-center">
            <div class="card-columns">
                <%
                    List<EventGoalBean> eventGoals = null;
                    try {
                        eventGoals = ManageGoalController.getEventGoalBeanList();
                    } catch (final UserNotFoundException | EmptyResultSetException | LoginException | DatabaseException | InvalidDataException e) {
                        e.printStackTrace();
                    }
                    if(eventGoals != null){
                    for (final EventGoalBean e  : eventGoals) {
                %>
                <div class="card text-center mt-2 ">
                    <div class="card-body">
                        <form action="Profile.jsp" name="updateEventGoalForm" method="POST">

                            <h5 class="card-title"><%=e.getName() + " \nId: #" + e.getId()%></h5>
                            <input type="hidden" id="eventgoalid" name="goalid" value="<%=e.getId()%>">

                            <h6 class="card-duration mb-2 text-muted"><%="Deadline: " + e.getDeadline()%></h6>
                            <p class="card-duration mb-2 text-muted"><%=e.getDescription()%></p>
                            <p class="card-duration mb-2"><%="Steps: " + e.getStepsCompleted() + "/" + e.getNumberOfSteps()%></p>

                            <input type="number" id="eventstepscompleted" name="stepscompleted" class="form-control"
                                   placeholder="steps completed" required min="0" max="100">

                            <button class="w-100 btn btn-lg btn-primary half-top-buffer"
                                    type="submit" name="goal" value="goal">Update
                            </button>

                            <p class="card-duration mb-2"><%="Event: " + e.getEventName() + " by " + e.getEventOrganizer()%></p>
                            <p class="card-duration mb-2"><%="Status: " + e.getState()%></p>
                        </form>
                    </div>
                </div>
                <% } }%>
            </div>
        </div>
    </div>
</div>




<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>
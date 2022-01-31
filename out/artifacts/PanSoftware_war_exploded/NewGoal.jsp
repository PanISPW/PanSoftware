<%@ page import="com.pansoftware.logic.LoginController" %>
<%@ page import="com.pansoftware.logic.ManageGoalController" %>
<%@ page import="com.pansoftware.logic.bean.AdviceGoalBean" %>
<%@ page import="com.pansoftware.logic.bean.GoalBean" %>
<%@ page import="com.pansoftware.logic.enumeration.UserRole" %>
<%@ page import="com.pansoftware.logic.exception.EmptyResultSetException" %>
<%@ page import="com.pansoftware.logic.exception.InvalidDataException" %>
<%@ page import="com.pansoftware.logic.exception.UserNotFoundException" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%
    if (request.getParameter("newgoal") != null) {
        try {
            final GoalBean bean = new GoalBean();

            bean.setName(request.getParameter("goalname"));
            bean.setDescription(request.getParameter("goaldescription"));
            bean.setNumberOfSteps(Integer.parseInt(request.getParameter("goalsteps")));
            bean.setStepsCompleted(0);
            bean.setNewDeadline(LocalDate.parse(request.getParameter("goaldeadline")));

            if (request.getParameter("goalreminder") != null) {
                bean.setReminder(true);
            }

            ManageGoalController.createGoal(bean);
        } catch(final InvalidDataException | SQLException | UserNotFoundException | EmptyResultSetException e){
            e.printStackTrace();
        }
    } %>

<%
    if (request.getParameter("advicegoal") != null) {
        try {
            final AdviceGoalBean bean = new AdviceGoalBean();

            bean.setName(request.getParameter("advicegoalname"));
            bean.setDescription(request.getParameter("advicegoaldescription"));
            bean.setNumberOfSteps(Integer.parseInt(request.getParameter("advicegoalsteps")));
            bean.setStepsCompleted(0);
            bean.setNewDeadline(LocalDate.parse(request.getParameter("advicegoaldeadline")));

            bean.setTypeInt(Integer.parseInt(request.getParameter("advicegoaltype")));

            if (request.getParameter("advicegoalreminder") != null) {
                bean.setReminder(true);
            }

            ManageGoalController.createGoal(bean);
        } catch(final InvalidDataException | SQLException | UserNotFoundException | EmptyResultSetException e){
            e.printStackTrace();
        }
    } %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <meta charset="ISO-8859-1">
    <title>Crea un nuovo Goal</title>

    <style>
        .half-top-buffer {
            margin-top: 50px;
        }

    </style>
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

<h1>New Goal Page</h1>

<ul class="nav nav-tabs" id="myTab" role="tablist">
    <li class="nav-item" role="presentation">
        <button class="nav-link active" id="goal-tab" data-bs-toggle="tab" data-bs-target="#goal" type="button" role="tab" aria-controls="goal" aria-selected="true">New Goal</button>
    </li>
    <li class="nav-item" role="presentation">
        <button class="nav-link" id="advicegoal-tab" data-bs-toggle="tab" data-bs-target="#advicegoal" type="button" role="tab" aria-controls="advicegoal" aria-selected="false">New Advice Goal</button>
    </li>
    <li class="nav-item" role="presentation">
        <button class="nav-link" id="eventgoal-tab" data-bs-toggle="tab" data-bs-target="#eventgoal" type="button" role="tab" aria-controls="eventgoal" aria-selected="false">New Event Goal</button>
    </li>
</ul>
<div class="d-flex justify-content-center align-items-center tab-content" id="myTabContent">
    <div class="tab-pane fade show active" id="goal" role="tabpanel" aria-labelledby="goal-tab">
        <form action="NewGoal.jsp" name="newGoalForm" method="POST">

            <input type="text" id="goalname" name="goalname" class="form-control"
                   placeholder="name" required autofocus>
            <input
                    type="text" id="goaldescription" name="goaldescription" class="form-control"
                    placeholder="description" required>
            <input type="number" id="goalsteps" name="goalsteps" class="form-control"
                   placeholder="number of steps" required min="0" max="100">

            <label for="goaldeadline">Deadline: </label>
            <input type="date" id="goaldeadline" name="goaldeadline">

            <div class="form-check">
                <input class="form-check-input" type="checkbox" value="" id="goalreminder">
                <label class="form-check-label" for="goalreminder">
                    Set Reminder
                </label>
            </div>

            <button class="w-100 btn btn-lg btn-primary half-top-buffer"
                    type="submit" name="newgoal" value="newgoal">Submit
            </button>
        </form>
    </div>
    <div class="tab-pane fade" id="advicegoal" role="tabpanel" aria-labelledby="advicegoal-tab">
        <form action="NewGoal.jsp" name="newGoalForm" method="POST">

            <input type="text" id="advicegoalname" name="advicegoalname" class="form-control"
                   placeholder="name" required autofocus>
            <input
                    type="text" id="advicegoaldescription" name="advicegoaldescription" class="form-control"
                    placeholder="description" required>
            <input type="number" id="advicegoalsteps" name="advicegoalsteps" class="form-control"
                   placeholder="number of steps" required min="0" max="100">

            <label for="advicegoaldeadline">Deadline: </label>
            <input type="date" id="advicegoaldeadline" name="advicegoaldeadline">

            <select class="form-select" aria-label="Default select example" name="advicegoaltype">
                <option selected>Product Type</option>
                <option value="0">Makeup</option>
                <option value="1">Food</option>
                <option value="2">Lifestyle</option>
                <option value="3">Other</option>
                <option value="4">Not Specified</option>
            </select>

            <div class="form-check">
                <input class="form-check-input" type="checkbox" value="" id="advicegoalreminder">
                <label class="form-check-label" for="advicegoalreminder">
                    Set Reminder
                </label>
            </div>

            <button class="w-100 btn btn-lg btn-primary half-top-buffer"
                    type="submit" name="advicegoal" value="advicegoal">Submit
            </button>
        </form>
    </div>
    <div class="tab-pane fade" id="eventgoal" role="tabpanel" aria-labelledby="eventgoal-tab">
        <form action="EventSelection.jsp" name="newGoalForm" method="POST">

            <input type="text" id="eventgoalname" name="eventgoalname" class="form-control"
                   placeholder="name" required autofocus>
            <input
                    type="text" id="eventgoaldescription" name="eventgoaldescription" class="form-control"
                    placeholder="description" required>
            <input type="number" id="eventgoalsteps" name="eventgoalsteps" class="form-control"
                   placeholder="number of steps" required min="0" max="100">

            <label for="eventgoaldeadline">Deadline: </label>
            <input type="date" id="eventgoaldeadline" name="eventgoaldeadline">

            <div class="form-check">
                <input class="form-check-input" type="checkbox" value="" id="eventgoalreminder">
                <label class="form-check-label" for="eventgoalreminder">
                    Set Reminder
                </label>
            </div>

            <button class="w-100 btn btn-lg btn-primary half-top-buffer"
                    type="submit" name="eventgoal" value="eventgoal">Continue to Event Selection
            </button>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>
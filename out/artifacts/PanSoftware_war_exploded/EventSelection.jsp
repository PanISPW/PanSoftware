<%@ page import="com.pansoftware.logic.ManageGoalController" %>
<%@ page import="com.pansoftware.logic.bean.EventBean" %>
<%@ page import="com.pansoftware.logic.bean.EventGoalBean" %>
<%@ page import="com.pansoftware.logic.exception.EmptyResultSetException" %>
<%@ page import="com.pansoftware.logic.exception.InvalidDataException" %>
<%@ page import="com.pansoftware.logic.exception.UserNotFoundException" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 30/01/2022
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    final EventGoalBean bean = new EventGoalBean();

    if (request.getParameter("eventgoal") != null) {
        //stub
    } else if(request.getParameter("selectevent") != null){
        try {
            bean.setName(request.getParameter("eventgoalname"));
            bean.setDescription(request.getParameter("eventgoaldescription"));
            bean.setNumberOfSteps(Integer.parseInt(request.getParameter("eventgoalsteps")));
            bean.setStepsCompleted(0);
            bean.setNewDeadline(LocalDate.parse(request.getParameter("eventgoaldeadline")));

            if (request.getParameter("eventgoalreminder") != null) {
                bean.setReminder(true);
            }

            bean.setEventId(Integer.parseInt(request.getParameter("eventid")));
            bean.setEventOrganizer(request.getParameter("eventorganizer"));

            ManageGoalController.createGoal(bean);
%>
<jsp:forward page="Profile.jsp"/>
<%

        } catch(final InvalidDataException | SQLException | UserNotFoundException | EmptyResultSetException e){
            e.printStackTrace();
        }
    }else{
%>
<jsp:forward page="Profile.jsp"/>
<% }%>

<!DOCTYPE html>
<html lang="en">

<head>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <meta charset="ISO-8859-1">
    <title>Event Selection</title>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</head>
<body>
<nav class="navbar navbar-expand navbar-dark bg-success"
     aria-label="PanLoginNavbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"> <img src="https://img.icons8.com/external-filled-outline-icons-pause-08/50/000000/external-flower-farm-and-garden-filled-outline-icons-pause-08.png" alt="logo" width="30" height="30"/> Pan
        </a>
    </div>
</nav>

    <div class="col-6 d-flex justify-content-center">
        <div class="card-columns">
            <%
                List<EventBean> events = null;
                try {
                    events = ManageGoalController.getEventBeanList();
                } catch (final UserNotFoundException | EmptyResultSetException | InvalidDataException e) {
                    e.printStackTrace();
                }
                if(events != null){
                for (final EventBean e  : events) {
            %>
            <div class="card text-center mt-2 ">
                <div class="card-body">

                    <form action="EventSelection.jsp" name="eventSelectionForm" method="POST">

                        <input type="hidden" id="eventgoalname" name="eventgoalname" value="<%=request.getParameter("eventgoalname")%>">
                        <input type="hidden" id="eventgoaldescription" name="eventgoaldescription" value="<%=request.getParameter("eventgoaldescription")%>">
                        <input type="hidden" id="eventgoalsteps" name="eventgoalsteps" value="<%=request.getParameter("eventgoalsteps")%>">
                        <input type="hidden" id="eventgoaldeadline" name="eventgoaldeadline" value="<%=request.getParameter("eventgoaldeadline")%>">

                        <% if (request.getParameter("eventgoalreminder") != null) { %>
                        <input type="hidden" id="eventgoalreminder" name="eventgoalreminder" value="true">
                        <% }%>

                        <h5 class="card-title"><%=e.getName()%></h5>
                        <h6 class="card-duration mb-2 text-muted"><%=e.getStartingDate() + " - " + e.getEndingDate()%></h6>
                        <p class="card-identifiers mb-2 text-muted"><%="Author: " + e.getOrganizer() + "   Id: #" + e.getId()%></p>
                        <p class="card-privacy" style="color: green"><%="Privacy: " + e.getType()%></p>

                        <input type="hidden" id="eventid" name="eventid" value="<%=e.getId()%>">
                        <input type="hidden" id="eventorganizer" name="eventorganizer" value="<%=e.getOrganizer()%>">

                        <button type="submit" class="btn btn-primary" name="selectevent" value="selectevent">Select Event</button>

                    </form>
                </div>
            </div>
            <% } } else{
            %>
            <jsp:forward page="Profile.jsp"/>
            <%
            }%>
        </div>
    </div>

</body>
</html>

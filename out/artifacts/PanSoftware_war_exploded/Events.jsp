        <%@ page import="com.pansoftware.logic.LoginController" %>
<%@ page import="com.pansoftware.logic.ManageGoalController" %>
<%@ page import="com.pansoftware.logic.bean.EventBean" %>
<%@ page import="com.pansoftware.logic.enumeration.UserRole" %>
<%@ page import="com.pansoftware.logic.exception.DatabaseException" %>
<%@ page import="com.pansoftware.logic.exception.EmptyResultSetException" %>
<%@ page import="com.pansoftware.logic.exception.InvalidDataException" %>
<%@ page import="com.pansoftware.logic.exception.UserNotFoundException" %>
<%@ page import="javax.security.auth.login.LoginException" %>
        <%@ page import="java.util.List" %>
        <%@ page contentType="text/html; charset=ISO-8859-1"
                 pageEncoding="ISO-8859-1" %>



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

<h1>Events Page</h1>
<p class="card-duration mb-2 text-muted"> Events:</p>

<div class="col-6 d-flex justify-content-center">
<div class="card-columns">
<%
    List<EventBean> events = null;
    try {
        events = ManageGoalController.getEventBeanList();
    } catch (final UserNotFoundException | EmptyResultSetException | LoginException | DatabaseException| InvalidDataException e) {
        e.printStackTrace();
    }
    assert events != null;
    for (final EventBean e  : events) {
%>
    <div class="card text-center mt-2 ">
        <div class="card-body">
            <h5 class="card-title"><%=e.getName()%></h5>
            <h6 class="card-duration mb-2 text-muted"><%=e.getStartingDate() + " - " + e.getEndingDate()%></h6>
            <p class="card-identifiers mb-2 text-muted"><%="Author: " + e.getOrganizer() + "   Id: #" + e.getId()%></p>
            <p class="card-privacy" style="color: green"><%="Privacy: " + e.getType()%></p>
        </div>
    </div>
<% } %>
</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>
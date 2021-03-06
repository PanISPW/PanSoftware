<%@ page import="com.pansoftware.logic.LoginController" %>
<%@ page import="com.pansoftware.logic.ManageGoalController" %>
<%@ page import="com.pansoftware.logic.bean.AdviceGoalBean" %>
<%@ page import="com.pansoftware.logic.bean.AnswerAdviceGoalBean" %>
<%@ page import="com.pansoftware.logic.enumeration.UserRole" %>
<%@ page import="com.pansoftware.logic.exception.EmptyResultSetException" %>
<%@ page import="com.pansoftware.logic.exception.InvalidDataException" %>
<%@ page import="com.pansoftware.logic.exception.NotEnoughPermissionsException" %>
<%@ page import="com.pansoftware.logic.exception.UserNotFoundException" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%
    if (request.getParameter("submitadvice") != null ){

        try {
            final AnswerAdviceGoalBean bean = new AnswerAdviceGoalBean();
            bean.setAnswerAdviceId(Integer.parseInt(request.getParameter("goalid")));
            bean.setGoalUser(request.getParameter("user"));
            bean.setAnswer(request.getParameter("advice"));

            ManageGoalController.answerAdviceGoal(bean);

        } catch (final InvalidDataException | UserNotFoundException | NotEnoughPermissionsException | SQLException e) {
            e.printStackTrace();
        }
    }
%>

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
    } else {
%>
<jsp:forward page="Login.jsp"/>
<%
    }
} catch (final InvalidDataException e) {
%>
<jsp:forward page="Login.jsp"/>
<%
    } %>

<h1>Give Advice Page</h1>

<ul class="nav nav-tabs" id="myTab" role="tablist">
    <li class="nav-item" role="presentation">
        <button class="nav-link active" id="makeup-tab" data-bs-toggle="tab" data-bs-target="#makeup" type="button" role="tab" aria-controls="makeup" aria-selected="true">Makeup</button>
    </li>
    <li class="nav-item" role="presentation">
        <button class="nav-link" id="food-tab" data-bs-toggle="tab" data-bs-target="#food" type="button" role="tab" aria-controls="food" aria-selected="false">Food</button>
    </li>
    <li class="nav-item" role="presentation">
        <button class="nav-link" id="lifestyle-tab" data-bs-toggle="tab" data-bs-target="#lifestyle" type="button" role="tab" aria-controls="lifestyle" aria-selected="false">Lifestyle</button>
    </li>
    <li class="nav-item" role="presentation">
        <button class="nav-link" id="other-tab" data-bs-toggle="tab" data-bs-target="#other" type="button" role="tab" aria-controls="other" aria-selected="false">Other</button>
    </li>
</ul>
<div class="tab-content" id="myTabContent">
    <div class="tab-pane fade show active" id="makeup" role="tabpanel" aria-labelledby="makeup-tab">

        <div class="col-6 d-flex justify-content-center">
            <div class="card-columns">
                <%
                    List<AdviceGoalBean> goals = null;
                    try {
                        goals = ManageGoalController.getUnansweredMakeupAdviceBean();
                    } catch (final UserNotFoundException | EmptyResultSetException | SQLException | InvalidDataException e) {
                        e.printStackTrace();
                    }
                    if(goals != null){
                    for (final AdviceGoalBean a  : goals) {
                %>
                <div class="card text-center mt-2 ">
                    <div class="card-body">

                        <form action="GiveAdvice.jsp" name="makeupadviceForm" method="POST">

                            <h5 class="card-title"><%=a.getName() + " (Id: #" + a.getId() + ")"%></h5>
                            <input type="hidden" id="goalid" name="goalid" value="<%=a.getId()%>">
                            <input type="hidden" id="user" name="user" value="<%=a.getUser()%>">
                            <h6 class="card-duration mb-2 text-muted"><%="Author: " + a.getUser()%></h6>
                            <h6 class="card-duration mb-2 text-muted"><%=a.getDeadline()%></h6>
                            <p class="card-identifiers mb-2 text-muted"><%=a.getDescription()%></p>
                            <p class="card-privacy" style="color: green"><%="Steps completed: " + a.getStepsCompleted() + "/" + a.getNumberOfSteps() %></p>


                            <input type="text" id="advicemakeup" name="advice" class="form-control"
                                   placeholder="Advice" required autofocus>

                            <button type="submit" class="btn btn-primary" name="submitadvice" value="submitadvice">Submit Advice</button>

                        </form>
                    </div>
                </div>
                <% } }%>
            </div>
        </div>

    </div>
    <div class="tab-pane fade" id="food" role="tabpanel" aria-labelledby="food-tab">

        <div class="col-6 d-flex justify-content-center">
            <div class="card-columns">
                <%
                    List<AdviceGoalBean> foodGoals = null;
                    try {
                        foodGoals = ManageGoalController.getUnansweredFoodAdviceBean();
                    } catch (final UserNotFoundException | EmptyResultSetException | SQLException | InvalidDataException e) {
                        e.printStackTrace();
                    }
                    if(foodGoals != null){
                        for (final AdviceGoalBean a  : foodGoals) {
                %>
                <div class="card text-center mt-2 ">
                    <div class="card-body">

                        <form action="GiveAdvice.jsp" name="foodadviceForm" method="POST">

                            <h5 class="card-title"><%=a.getName() + " (Id: #" + a.getId() + ")"%></h5>
                            <input type="hidden" id="foodgoalid" name="goalid" value="<%=a.getId()%>">
                            <input type="hidden" id="fooduser" name="user" value="<%=a.getUser()%>">
                            <h6 class="card-duration mb-2 text-muted"><%="Author: " + a.getUser()%></h6>
                            <h6 class="card-duration mb-2 text-muted"><%=a.getDeadline()%></h6>
                            <p class="card-identifiers mb-2 text-muted"><%=a.getDescription()%></p>
                            <p class="card-privacy" style="color: green"><%="Steps completed: " + a.getStepsCompleted() + "/" + a.getNumberOfSteps() %></p>

                            <input type="text" id="advicefood" name="advice" class="form-control"
                                   placeholder="Advice" required autofocus>

                            <button type="submit" class="btn btn-primary" name="submitadvice" value="submitadvice">Submit Advice</button>

                        </form>
                    </div>
                </div>
                <% } }%>
            </div>
        </div>

    </div>
    <div class="tab-pane fade" id="lifestyle" role="tabpanel" aria-labelledby="lifestyle-tab">

        <div class="col-6 d-flex justify-content-center">
            <div class="card-columns">
                <%
                    List<AdviceGoalBean> lifestyleGoals = null;
                    try {
                        lifestyleGoals = ManageGoalController.getUnansweredLifestyleAdviceBean();
                    } catch (final UserNotFoundException | EmptyResultSetException | SQLException | InvalidDataException e) {
                        e.printStackTrace();
                    }
                    if(lifestyleGoals != null){
                        for (final AdviceGoalBean a  : lifestyleGoals) {
                %>
                <div class="card text-center mt-2 ">
                    <div class="card-body">

                        <form action="GiveAdvice.jsp" name="foodadviceForm" method="POST">

                            <h5 class="card-title"><%=a.getName() + " (Id: #" + a.getId() + ")"%></h5>
                            <input type="hidden" id="lifestylegoalid" name="goalid" value="<%=a.getId()%>">
                            <input type="hidden" id="lifestyleuser" name="user" value="<%=a.getUser()%>">
                            <h6 class="card-duration mb-2 text-muted"><%="Author: " + a.getUser()%></h6>
                            <h6 class="card-duration mb-2 text-muted"><%=a.getDeadline()%></h6>
                            <p class="card-identifiers mb-2 text-muted"><%=a.getDescription()%></p>
                            <p class="card-privacy" style="color: green"><%="Steps completed: " + a.getStepsCompleted() + "/" + a.getNumberOfSteps() %></p>

                            <input type="text" id="advicelifestyle" name="advice" class="form-control"
                                   placeholder="Advice" required autofocus>

                            <button type="submit" class="btn btn-primary" name="submitadvice" value="submitadvice">Submit Advice</button>

                        </form>
                    </div>
                </div>
                <% } }%>
            </div>
        </div>
    </div>
    <div class="tab-pane fade" id="other" role="tabpanel" aria-labelledby="other-tab">

        <div class="col-6 d-flex justify-content-center">
            <div class="card-columns">
                <%
                    List<AdviceGoalBean> otherGoals = null;
                    try {
                        otherGoals = ManageGoalController.getUnansweredOtherAdviceBean();
                    } catch (final UserNotFoundException | EmptyResultSetException | SQLException e) {
                        e.printStackTrace();
                    }
                    if(otherGoals != null){
                        for (final AdviceGoalBean a  : otherGoals) {
                %>
                <div class="card text-center mt-2 ">
                    <div class="card-body">

                        <form action="GiveAdvice.jsp" name="foodadviceForm" method="POST">

                            <h5 class="card-title"><%=a.getName() + " (Id: #" + a.getId() + ")"%></h5>
                            <input type="hidden" id="othergoalid" name="goalid" value="<%=a.getId()%>">
                            <input type="hidden" id="otheruser" name="user" value="<%=a.getUser()%>">
                            <h6 class="card-duration mb-2 text-muted"><%="Author: " + a.getUser()%></h6>
                            <h6 class="card-duration mb-2 text-muted"><%=a.getDeadline()%></h6>
                            <p class="card-identifiers mb-2 text-muted"><%=a.getDescription()%></p>
                            <p class="card-privacy" style="color: green"><%="Steps completed: " + a.getStepsCompleted() + "/" + a.getNumberOfSteps() %></p>

                            <input type="text" id="adviceother" name="advice" class="form-control"
                                   placeholder="Advice" required autofocus>

                            <button type="submit" class="btn btn-primary" name="submitadvice" value="submitadvice">Submit Advice</button>

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
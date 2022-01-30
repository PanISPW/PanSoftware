<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@page import="com.pansoftware.logic.LoginController" %>
<%@page import="com.pansoftware.logic.bean.LoginBean" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.pansoftware.logic.exception.DatabaseException" %>
<%@ page import="com.pansoftware.logic.exception.UserNotFoundException" %>

<%
    if (request.getParameter("login") != null) {
        try {
            final LoginBean bean = new LoginBean();

            bean.setUsername(request.getParameter("username"));
            bean.setPassword(request.getParameter("password"));

            LoginController.loginUser(bean);

            %>
            <jsp:forward page="Profile.jsp"/>
            <%
        } catch(final SQLException | DatabaseException | UserNotFoundException e){
            %>
            <jsp:forward page="LoginFailure.jsp"/>
            <%
        }
    } %>

<%
    LoginController.invalidateSession();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <meta charset="ISO-8859-1">
    <title>Login</title>

    <style>
        .half-top-buffer {
            margin-top: 50px;
        }

        .form-signin {
            width: 100%;
            max-width: 330px;
            padding: 15px;
            margin: auto;
        }

        .center-block {
            display: block;
            margin-left: auto;
            margin-right: auto;
        }
    </style>

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

<main
        class="d-flex justify-content-center align-items-center form-signin">
    <form action="Login.jsp" name="loginForm" method="POST">
        <img class="img-fluid center-block" src="https://img.icons8.com/external-filled-outline-icons-pause-08/50/000000/external-flower-farm-and-garden-filled-outline-icons-pause-08.png"
             alt="logo" width="72" height="72"/>
        <h1 class="h3 mb-3 fw-normal text-center">Login</h1>

        <input type="text" id="username" name="username" class="form-control"
               placeholder="username" required autofocus>
        <input
                type="password" id="password" name="password" class="form-control"
                placeholder="password" required>

        <button class="w-100 btn btn-lg btn-primary half-top-buffer"
                type="submit" name="login" value="login">Login
        </button>
    </form>
</main>
</body>
</html>
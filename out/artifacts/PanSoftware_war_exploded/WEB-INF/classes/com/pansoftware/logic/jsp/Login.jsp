<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@page import="com.pansoftware.logic.LoginController" %>
<%@page import="com.pansoftware.logic.exception.UserNotFoundException" %>
<%@ page import="java.sql.SQLException" %>

<!-- dichiarazione e instanziazione di un loginBean !-->
<jsp:useBean id="LoginBean" scope="request" class="com.pansoftware.logic.bean.LoginBean"/>

<!-- mappare gli attributi di un oggetto sui campi della form -->
<jsp:setProperty name="LoginBean" property="*"/>

<!-- @author Danilo D'Amico -->

<!-- quando viene chiamata con il valore "login" -->
<%
    if (request.getParameter("login") != null) {
        try {
            LoginController.loginUser(LoginBean);
%>
<jsp:forward page="Profile.jsp"/>
<%
} catch (UserNotFoundException | SQLException e) {
%>
<jsp:forward page="LoginFailure.jsp"/>
<%
        }
    } %>

<!DOCTYPE html>
<html lang="en">
<head>
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

</head>



<body>
<!--  navbar -->
<nav class="navbar navbar-expand navbar-dark bg-success"
     aria-label="PanLoginNavbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"> <img src="jetbrains://idea/navigate/reference?project=PanSoftware&path=pictures/logo.png"
                                               width="30" height="30" alt=""> Pan
        </a>
    </div>
</nav>

<main
        class="d-flex justify-content-center align-items-center form-signin">
    <!-- action chiama Login.jsp -->
    <form action="Login.jsp" name="loginForm" method="POST">
        <img class="img-fluid center-block" src="logo.png"
             alt="Responsive image" width="72" height="72">
        <h1 class="h3 mb-3 fw-normal text-center">Login</h1>

        <input type="text" id="username" name="username" class="form-control"
               placeholder="username" required autofocus>
        <input
                type="password" id="password" name="password" class="form-control"
                placeholder="password" required>

        <!-- La classe LoginBean viene invocata quando viene ricevuto come valore login -->
        <button class="w-100 btn btn-lg btn-primary half-top-buffer"
                type="submit" name="login" value="login">Login
        </button>
    </form>
</main>
</body>
</html>
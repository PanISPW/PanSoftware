<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 20/01/2022
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <meta charset="ISO-8859-1">
    <title>Login Failure</title>

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
        <a class="navbar-brand" href="#"> <img src="../pictures/logo.png"
                                               width="30" height="30" alt=""> Pan
        </a>
    </div>

    <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav me-auto">
            <li class="nav-item"><a class="nav-link" href="Login.jsp">Login</a>
            </li>
        </ul>

    </div>
</nav>

<p style="color: red">Wrong username or password.</p>
<p style="color: red">Please return to the login page and retry</p>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>Navbar</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <style>
        .top-buffer {
            margin-top: 100px;
        }
    </style>

</head>

<!-- @author Danilo D'Amico -->

<body>
<nav class="navbar navbar-expand navbar-dark bg-success">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"> <img src="../pictures/logo.png"
                                               width="30" height="30" alt=""> Pan
        </a>

        <div class="collapse navbar-collapse" id="navbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="Events.jsp">Events</a>
                </li>
                <li class="nav-item"><a class="nav-link" href="GiveAdvice.jsp">Give Advice</a>
                </li>
                <li class="nav-item"><a class="nav-link" href="ManageEventParticipations.jsp">Manage Event
                    Participations</a>
                </li>
                <li class="nav-item"><a class="nav-link" href="NewGoal.jsp">Create New Goal</a>
                </li>
                <li class="nav-item"><a class="nav-link" href="Profile.jsp">Profile</a>
                </li>
            </ul>

        </div>
    </div>
</nav>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Navbar</title>

<link href="../css/bootstrap.min.css" rel="stylesheet">
<!-- versioni "minimizzate" di bootstrap -->
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<link href="../css/style.css" rel="stylesheet">

<style>
.top-buffer {
	margin-top: 100px;
}
</style>

</head>

<!-- @author Danilo D'Amico -->

<body>
	<nav class="navbar navbar-expand navbar-dark bg-success"
		aria-label="PanChiSiamoNavbar">
		<div class="container-fluid">
			<a class="navbar-brand" href="#"> <img src="../pictures/logo.png"
				width="30" height="30" alt=""> Pan
			</a>

			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#chiSiamoNavbar"
				aria-controls="chiSiamoNavbar" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="chiSiamoNavbar">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link" href="About.jsp">About</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="Events.jsp">Events</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="GiveAdvice.jsp">Give Advice</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="ManageEventParticipations.jsp">Manage Event Participation</a>
					</li>
				</ul>

				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="Domande.jsp">
							<!-- temp --> <img src="../pictures/Obiettivi.png" width="20"
							height="20" alt="">
					</a></li>
					<li class="nav-item"><a class="nav-link" href="Domande.jsp">
							<!-- temp --> <img src="../pictures/Nuovo.png" width="20"
							height="20" alt="">
					</a></li>
					<li class="nav-item"><a class="nav-link" href="Domande.jsp">
							<!-- temp --> <img src="../pictures/Notifications.png" width="20"
							height="20" alt="">
					</a></li>
					<li class="nav-item"><a class="nav-link" href="Domande.jsp">
							<!-- temp --> <img src="../pictures/Settings.png" width="20"
							height="20" alt="">
					</a></li>
					<li class="nav-item"><a class="nav-link" href="Login.jsp">Logout</a> <!-- svuotare session -->
					</li>
				</ul>

			</div>
		</div>
	</nav>
</body>
</html>
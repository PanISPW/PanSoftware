<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profilo</title>
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
					<li class="nav-item"><a class="nav-link" href="Domande.jsp">Domande</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="Profilo.jsp">Profilo</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="Eventi.jsp">Eventi</a>
					</li>
					<li class="nav-item active"><a class="nav-link"
						aria-current="page" href="ChiSiamo.jsp"><b>Chi Siamo</b></a></li>
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
					<li class="nav-item"><a class="nav-link" href="ChiSiamo.jsp">Logout</a>
					</li>
				</ul>

			</div>
		</div>
	</nav>

	<!-- Container per pagina Chi Siamo -->

	<div class="container top-buffer">

		<!-- Come aggiungo dello spazio? -->

		<div class="row featurette">
			<!-- 7 colonne medium screens -->
			<div class="col-md-7">
				<h2 class="featurette-heading top-buffer">Pan</h2>
				<p class="lead">Donec ullamcorper nulla non metus auctor
					fringilla. Vestibulum id ligula porta felis euismod semper.
					Praesent commodo cursus magna, vel scelerisque nisl consectetur.
					Fusce dapibus, tellus ac cursus commodo.</p>

			</div>
			<div class="col-md-5">
				<img src="../pictures/logo.png" width="500" height="500" alt="">
			</div>

		</div>

		<hr class="featurette-divider">


		<div class="row container">

			<div class="col-md-6 top-buffer">
				<svg class="bd-placeholder-img rounded-circle" width="140"
					height="140" xmlns="http://www.w3.org/2000/svg" role="img"
					aria-label="Placeholder: 140x140"
					preserveAspectRatio="xMidYMid slice" focusable="false">
					<title>Placeholder</title><rect width="100%" height="100%"
						fill="#777" />
					<text x="50%" y="50%" fill="#777" dy=".3em">140x140</text></svg>

				<h2>Francesca Smecca</h2>
				<p>Duis mollis, est non commodo luctus, nisi erat porttitor
					ligula, eget lacinia odio sem nec elit. Cras mattis consectetur
					purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo,
					tortor mauris condimentum nibh.</p>
			</div>

			<div class="col-md-6 top-buffer">
				<svg class="bd-placeholder-img rounded-circle" width="140"
					height="140" xmlns="http://www.w3.org/2000/svg" role="img"
					aria-label="Placeholder: 140x140"
					preserveAspectRatio="xMidYMid slice" focusable="false">
					<title>Placeholder</title><rect width="100%" height="100%"
						fill="#777" />
					<text x="50%" y="50%" fill="#777" dy=".3em">140x140</text></svg>

				<h2>Danilo D'Amico</h2>
				<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in,
					egestas eget quam. Vestibulum id ligula porta felis euismod semper.
					Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum
					nibh, ut fermentum massa justo sit amet risus.</p>
			</div>
		</div>

		<hr class="featurette-divider top-buffer">

	</div>

</body>
</html>
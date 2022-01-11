<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>

<!-- @author Danilo D'Amico -->

<body>
	<div class="container"> <!-- riquadro che contiene la pagina web -->
		<form action="index.jsp" name="loginForm" method="POST"> <!-- inizializza form con valori immessi dall'utente -->
			<div class="row">
				<div class="col-lg-4 form-group">
					<label for="username">Username</label>
					<input type="text" id="username" name="username"/>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-4 form-group">
					<label for="password">Password</label>
					<input type="text" id="password" name="password"/>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-4 form-group">
					
					<input type="submit" name="login" value="login"/>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
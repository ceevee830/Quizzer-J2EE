<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Quizzer Login</title>
	
	<meta charset="utf-8">
	<meta name="description" content="slick Login">
	<link rel="stylesheet" type="text/css" href="css/login.css" />
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="http://www.modernizr.com/downloads/modernizr-latest.js"></script>
	<script type="text/javascript" src="placeholder.js"></script>
	
</head>

<body>
	<form id="slick-login" action="topbanner.jsp">
		<label for="username">username</label><input type="text" name="username" class="placeholder" placeholder="me@tutsplus.com">
		<label for="password">password</label><input type="password" name="password" class="placeholder" placeholder="password">
		<input type="submit" value="Log In">
	</form>
</body>
</html>
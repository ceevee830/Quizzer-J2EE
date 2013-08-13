<%@ page import="com.obs.quizzer.dao.QuizzerDAO, com.obs.quizzer.pojo.Quiz, com.obs.quizzer.pojo.Question, com.obs.quizzer.pojo.Choice, java.util.List" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Quizzer TopBanner</title>
	<link rel="stylesheet" type="text/css" href="css/topbanner.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/foundation.min.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/demo.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/main.css" media="screen" />
</head>
<body>
<!-- container for positioning the banner -->

<div id="navigation_container">

<!-- the left side of the fork effect -->
   <div class="l-triangle-top"></div>
   <div class="l-triangle-bottom"></div>

<!-- the ribbon body -->

   <div class="rectangle">
   
<!-- the navigation links -->
   	
		<ul id="navigation">
	   	  <li><a href="#">&#10029; Home</a></li>
	      <li><a href="#">&#10029; About</a></li>
	      <li><a href="#">&#10029; Services</a></li>
	      <li><a href="#">&#10029; Contact</a></li>
	    </ul>
   
<!-- end the ribbon body -->
   </div>
   
<!-- the right side of the fork effect -->
   <div class="r-triangle-top"></div>
   <div class="r-triangle-bottom"></div>

<!-- end container -->
</div>

<div class="click-nav">
    <ul class="no-js">
        <li>
            <a href="#" class="clicker"><img src="img/i-1.png" alt="Icon">Quizzes</a>
            <ul>
            <%
            QuizzerDAO dao = new QuizzerDAO();
            List<Quiz> quizzes = dao.getAllQuizzes();
            for (Quiz quiz : quizzes)
            {
               %>
                <li><a href="#"><img src="img/i-2.png" alt="Icon"><%= quiz.getQuizName() %></a></li>
               <%
            }
            %>
<!-- 
                <li><a href="#"><img src="img/i-2.png" alt="Icon">Dashboard</a></li>
                <li><a href="#"><img src="img/i-3.png" alt="Icon">Settings</a></li>
                <li><a href="#"><img src="img/i-4.png" alt="Icon">Privacy</a></li>
                <li><a href="#"><img src="img/i-5.png" alt="Icon">Help</a></li>
                <li><a href="#"><img src="img/i-6.png" alt="Icon">Sign out</a></li>
 -->                
            </ul>
        </li>
    </ul>
</div>
  <script>
	  document.write('<script src=' + ('__proto__' in {} ? 'js/vendor/zepto' : 'js/vendor/jquery') + '.js><\/script>')
  </script>
  
  <script src="js/foundation.min.js"></script>


  <script>
    $(document).foundation();
  </script>


</body>
</html>
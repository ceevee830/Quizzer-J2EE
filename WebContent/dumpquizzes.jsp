<%@ page import="com.obs.quizzer.dao.QuizzerDAO, com.obs.quizzer.pojo.Quiz, com.obs.quizzer.pojo.Question, com.obs.quizzer.pojo.Choice, java.util.List" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quizzer J2EE</title>
<style type="text/css">
	@import url("style.css");
</style>
</head>
<body>

<% 
QuizzerDAO dao = new QuizzerDAO();
List<Quiz> quizzes = dao.getAllQuizzes();
for (Quiz quiz : quizzes)
{
%>
<h1><%= quiz.getQuizName() %>count=<%= quiz.getQuestions().size() %></h1>
<%
	for (Question question : quiz.getQuestions())
	{
	   %>
	   <h2>Question = <%= question.getQuestionText() %></h2>
	   <h2>Answer = <%= question.getAnswerText() %></h2>
	   <%
	   for (Choice choice : question.getChoices())
	   {
		   %>
		   <h3>Choice = <%= choice.getChoice() %></h3>
		   <%
	   }
	}
%>
<%   
}
%>

</body>
</html>
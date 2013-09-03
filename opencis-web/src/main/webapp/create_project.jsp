<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
hello
<form action="createProjectServlet" method="post"><br>
选择模板 <select name="template">
	<option value="1">opencis-jar</option>
	<option value="2">opencis-war</option>
	<option value="3">opencis-ejb</option>
</select> <br>
<hr>
录入组织信息<br>
名称：<input name="org_name"><br>
URL:<input name="org_url">
<hr>
录入开发人员信息<br>
</form>
</body>
</html>
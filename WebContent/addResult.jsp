<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		if (request.getAttribute("error") != null) {
	%>
	<h3>
		<%
			request.getAttribute("error");
		%>
	</h3>


	<%
		} else {
	%>
	<h3>
		<%
			request.getAttribute("success");
		%>
	</h3>

	<%
		}
	%>

</body>
</html>
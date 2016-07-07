<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Collections"%>

<%@ page import="com.tum.dbpra.model.bean.ShiftBean"%>
<%@ page import="com.tum.dbpra.ShiftServlet"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<jsp:useBean id="PartBean" class="com.tum.dbpra.model.bean.ShiftBean"
	scope="request" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Human Resources Management</title>
</head>
<body>
	<%
		List<ShiftBean> allShift = new ArrayList<ShiftBean>();
		String[] columns = { "Section Name", "Employee Name", "Date",
				"Start time", "End time", "Task" };
	%>
	<%
		if (request.getAttribute("error") != null) {
	%>
	<h1>Shifts not found</h1>
	<%
		request.getAttribute("error");
	%>

	<%
		} else {
			allShift = (List<ShiftBean>) request.getAttribute("allShift");
	%>
	<h1>Music Festival Shifts</h1>
	<form action=shift method="post">
		<div>
			Filter: <input type="text" size="55"
				placeholder="Enter the search parameter" name="searchParam"/> <select name="column">
				<%
					for (String temp : columns) {
				%>
				<option><%=temp%></option>
				<%
					}
				%>

			</select> <input type="submit" name="filter" value="filter" />

		</div>
	</form>

	<br>
	<table border="1" style="width: 100%">
		<tr>
			<th>Section Name</th>
			<th>Employee Name</th>
			<th>Start time</th>
			<th>End time</th>
			<th>Task</th>
		</tr>
		<%
			for (ShiftBean shift : allShift) {
		%>
		<tr>
			<td><%=shift.getSectionName()%></td>
			<td><%=shift.getEmployeeName()%></td>
			<td><%=shift.getStartTime()%></td>
			<td><%=shift.getEndTime()%></td>
			<td><%=shift.getTask()%></td>

		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>
	<form action="shift" method="post">
		<input type="submit" name="add" value="Add" />
	</form>



</body>
</html>
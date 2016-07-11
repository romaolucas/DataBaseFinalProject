<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Collections"%>

<%@ page import="tum.in.dbpra.bean.ShiftBean"%>
<%@ page import="tum.in.dbpra.servlet.ShiftServlet"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<jsp:useBean id="PartBean" class="tum.in.dbpra.bean.ShiftBean"
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
		List<String> allSectionName = new ArrayList<String>();
		List<String> allEmployeeName = new ArrayList<String>();
		String[] starttimepicker = { "12:00", "13:00", "14:00", "15:00",
				"16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
				"22:00" };
		String[] endtimepicker = { "13:30", "14:30", "15:30", "16:30",
				"17:30", "18:30", "19:30", "20:30", "21:30", "22:30" };

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
			allSectionName = (List<String>) request
					.getAttribute("allSectionName");
			allEmployeeName = (List<String>) request
					.getAttribute("allEmployeeName");
	%>
	<h1>Music Festival Shifts</h1>
	<form action=shift method="post" name="searchForm">
		Find a shift:
		<table>
			<tr>

				<td><select name="sectionName"
					onchange="document.searchForm.submit()">
						<option value="">Section Name</option>
						<option value="All">All</option>
						<%
							for (String temp : allSectionName) {
						%>

						<option value="<%=temp%>"><%=temp%></option>

						<%
							}
						%>
				</select></td>

				<td><select name="employeeName"
					onchange="document.searchForm.submit()">
						<option value="">Employee Name</option>
						<option value="All">All</option>
						<%
							for (String temp : allEmployeeName) {
						%>

						<option value="<%=temp%>"><%=temp%></option>

						<%
							}
						%>
				</select></td>

			</tr>
		</table>
		<div>
			Filter Task: <input type="text" size="55"
				placeholder="Enter the search pattern" name="searchPattern" /> <input
				type="submit" name="filter" value="filter" />

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
			<th>Operations</th>
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
			<td><input type="submit" value="delete" /></td>

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
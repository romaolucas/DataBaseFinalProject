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
<span class="right"> <a href="welcomeOrganizer.jsp"> <strong>Back
			to Organizer Dashboard</strong>
</a>
</span>
<title>Human Resources Management</title>
</head>

<%
	//Initialize some variables and lists usefull to display data on the page
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
<h1>Music Festival Shifts</h1>
<%
	if (request.getAttribute("error") != null) {
%>
<br>
<font color="red">Error: <%=request.getAttribute("error")%>
</font>

<%
	} else {
		//retrieve already existing shifts to display them in a table
		allShift = (List<ShiftBean>) request.getAttribute("allShift");
		//retrieve all section names to fill the dropdown list
		allSectionName = (List<String>) request
				.getAttribute("allSectionName");
		//retrieve all employee names to fill the dropdown list
		allEmployeeName = (List<String>) request
				.getAttribute("allEmployeeName");
%>


<body>
	<%
		// only if there are already some shifts then show the table containing the shifts and search/filter panel
			if (allShift != null) {
	%>
	<form action=shift method="post" name="searchForm">
		Find a shift by Section Name or Employee Name:
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
	<form action="shift" method="post">
		Add a new shift: <input type="submit" name="add" value="Add" />
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
			if (request.getAttribute("empty") != null) {
		%>
		<h4><%=request.getAttribute("empty")%></h4>
		<%
			}
		%>
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
		} else {
	%>
	<h4>There is no Shift yet</h4>
	<%
		}
		}
	%>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Collections"%>

<%@ page import="tum.in.dbpra.dbutils.Util"%>
<%@ page import="tum.in.dbpra.bean.ShiftBean"%>
<%@ page import="tum.in.dbpra.servlet.ShiftServlet"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<!--  <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 5 Transitional//EN" "http://www.w3.org/TR/html5/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <span>
                    <a href="welcomeOrganizer.jsp">
                        <strong>Back to Organizer Dashboard</strong>
                    </a>
                </span>
<title>Insert title here</title>
</head>
<body>
	<h1>Create a new entry in Shift</h1>
	<%
		List<String> allSectionName = new ArrayList<String>();
		List<String> allEmployeeName = new ArrayList<String>();
	%>
	<%
		allSectionName = (List<String>) request
				.getAttribute("allSectionName");
		allEmployeeName = (List<String>) request
				.getAttribute("allEmployeeName");
		String[] starttimepicker = { "12:00", "13:00", "14:00", "15:00",
				"16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
				"22:00" };
		String[] endtimepicker = { "13:30", "14:30", "15:30", "16:30",
				"17:30", "18:30", "19:30", "20:30", "21:30", "22:30" };
	%>



	<fieldset>
		<legend>Add Shift</legend>
		<form action="shiftAdd" method="post">
			<select name="sectionName">
				<%
					for (String temp : allSectionName) {
				%>
				<option><%=temp%></option>
				<%
					}
				%>

			</select> <select name="employeeName">
				<%
					for (String temp : allEmployeeName) {
				%>
				<option><%=temp%></option>
				<%
					}
				%>

			</select> <select name="date">
				<%
					for (String temp : new Util().getMonthFromNow()) {
				%>
				<option><%=temp%></option>
				<%
					}
				%>

			</select> <select name="starttime">
				<%
					for (String temp : starttimepicker) {
				%>
				<option><%=temp%></option>
				<%
					}
				%>

			</select> <select name="endtime">
				<%
					for (String temp : endtimepicker) {
				%>
				<option><%=temp%></option>
				<%
					}
				%>

			</select> <input type="text" name="task" size="80" required/> <input type=submit
				value="Add">
		</form>

	</fieldset>
	

</body>
</html>
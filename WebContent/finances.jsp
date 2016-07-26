<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="tum.in.dbpra.servlet.FinancesServlet"%>
<%@page import="java.util.Collections"%>

<%@ page import="tum.in.dbpra.bean.SponsorBean"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<jsp:useBean id="SponsorBean" class="tum.in.dbpra.bean.SponsorBean"
	scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<span> <a href="welcomeOrganizer.jsp"> <strong>Back
			to Organizer Dashboard</strong>
</a>
</span>
<title>Music Festival Finances</title>
</head>
<body>
	<%
		List<SponsorBean> allSponsor = new ArrayList<SponsorBean>();
		double totalAmount = 0.0;
	%>

	<%
		if (request.getAttribute("error") != null) {
	%>
	<h1>Problem while retrieving finances</h1>
	<%
		request.getAttribute("error");
	%>

	<%
		}
		//only if there are already some sponsors in the DB then show then in a form of a table

		if (request.getAttribute("allSponsor") != null) {
			allSponsor = (List<SponsorBean>) request
					.getAttribute("allSponsor");
	%>
	<h1>Music Festival Sponsors</h1>
	<table border="1" style="width: 100%">
		<tr>
			<th>Name</th>
			<th>Type</th>
			<th>Amount</th>
		</tr>

		<%
			for (SponsorBean sponsor : allSponsor) {
					totalAmount += sponsor.getAmount();
		%>


		<tr>
			<td><%=sponsor.getName()%></td>
			<td><%=sponsor.getType()%></td>
			<td><%=sponsor.getAmount()%></td>
		</tr>



		<%
			}
		%>
		<tr>

		</tr>

		<tr>
			<td colspan="2"><center>Total amount</center></td>
			<td><b><%=totalAmount%></b></td>
		</tr>

	</table>

	<%
		} else {
	%>

	<h4>There is no Sponsor yet</h4>

	<%
		}
	%>
</body>
</html>
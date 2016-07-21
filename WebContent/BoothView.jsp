	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*, tum.in.dbpra.bean.BoothBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="JavaScript" type="text/javascript">
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Part</title>
</head>
<body>
	<% if (request.getAttribute("error") != null) { %>
	<h1>Part not found!</h1>
	<%= request.getAttribute("error") %>

	<% } else { 
		if(pageContext.findAttribute("boothes")!= null && ((List)pageContext.findAttribute("boothes")).size() != 0) {
			   List<BoothBean> boothes = (List<BoothBean>)pageContext.findAttribute("boothes");
			   %>
		
	<%//interface of table %>	
	<table border="1">
	  <form name="boothesform" method="post">
	  <!-- <input type="hidden" name="part_column_sort" /> -->
		<tr>
				<td> Name</td>
				<td> Size in m^2</td>
				<td> Sponsor ID</td>
				
		</tr>
		<%
	    
		//Boothes themselfs
		for (BoothBean booth: boothes){
	%>
	 	<tr>
			<td><%=booth.getName()%></td>
			<td><%=booth.getMetersquarres()%></td>
			<td><%if(booth.getpID()>0)%><%=booth.getpID()%><%else{%>-<%}%></td>
		</tr>
	
        	<% } %>
	 </form>
	 					
	 </table>
	 
	 <% //button to navigate to "Assign Timeslot page"%>
	 <form action="boothToSponsor">
						<p>
							<input type="submit" value="Assign New" name="viewall">
						</p>
	</form>
        <%}
        }%>
    
	
		   
	
</body>
</html>
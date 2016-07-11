	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*, tum.in.dbpra.bean.BoothBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="JavaScript" type="text/javascript">

function getColumn ( selectedColumn )
{
  document.partsform.part_column_sort.value = selectedColumn ;
  document.partsform.submit() ;
}

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
		
	<table border="1">
	  <form name="boothesform" method="post">
	  <!-- <input type="hidden" name="part_column_sort" /> -->
		<tr>
				<td> <a href="javascript:getColumn('partkey')">Name</a></td>
				<td> <a href="javascript:getColumn('name')">Area name</a></td>
				<td> <a href="javascript:getColumn('type')">Size</a></td>
				<td> <a href="javascript:getColumn('size')">Type</a></td>
				<td> <a href="javascript:getColumn('container')">Service</a></td>
				<td> <a href="javascript:getColumn('retailprice')">Equipment</a></td>
				<td> <a href="javascript:getColumn('retailprice')">Sponsor</a></td>
				
		</tr>
		<%
	    
		for (BoothBean booth: boothes){
	%>
	 	<tr>
			<td><%=booth.getName()%></td>
			<td><%=booth.getAreaID()%></td>
			<td><%=booth.getMetersquarres()%></td>
			<td><%=booth.getType()%></td>
			<td><%=booth.getService()%></td>
			<td><%=booth.getEquipment()%></td>
			<td><%=booth.getpID()%></td>
		</tr>
	
        	<% } %>
	 </form>
	 </table>
        <%}
        }%>
    
	
		   
	
</body>
</html>
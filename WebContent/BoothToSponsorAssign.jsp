<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*, tum.in.dbpra.bean.BoothBean, tum.in.dbpra.bean.SponsorBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<span class="right"> <a href="welcomeOrganizer.jsp "> <strong>Back
			to Organizer Dashboard</strong>
</a>
</span>
<title>Assign Booth</title>
</head>
<body>
	<% if (request.getAttribute("error") != null) { %>
	<h1>Try again!</h1>
	<%= request.getAttribute("error") %>

	<% } else { 
		//retrive Boothes and Sponsors from db to fill the drop down menus
		List<BoothBean> boothes = new ArrayList<BoothBean>();
		List<SponsorBean> sponsors = new ArrayList<SponsorBean>();
		if(pageContext.findAttribute("boothes")!= null && ((List)pageContext.findAttribute("boothes")).size() != 0)
			  boothes = (List<BoothBean>)pageContext.findAttribute("boothes");
		if(pageContext.findAttribute("sponsors")!= null && ((List)pageContext.findAttribute("sponsors")).size() != 0)
			  sponsors = (List<SponsorBean>)pageContext.findAttribute("sponsors");
			   %>
		 <form name="advertisementform" method="post">
	  
		Sponsors:
			<select name="sponsors">
				<% //create drop down menu for Sponsor
					for (SponsorBean sponsor: sponsors){ %>
				 <option value="<%=sponsor.getId()%>"><%=sponsor.getName()%></option>
        		<% } %>
	        </select>
					
		Boothes:
			<select name="boothes">
				<%  //create drop down menu for Booth
					for (BoothBean booth: boothes){ %>
				 <option value="<%=booth.getSectionID()%>"><%=booth.getName()%></option>
        		<% } %>
	        </select>
	        
	    <%//Submit button%>    
		<input type="submit" value="Assign" name="assignBoothToSponsor"/>
		
		
      </form>
	<% //button for navigation to "View Boothes" page %>
	 					<form action="boothView">
						<p>
							<input type="submit" value="View All" name="viewall">
						</p>
						</form>
		<%}%>
	
</body>
</html>
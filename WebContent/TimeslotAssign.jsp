<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*, tum.in.dbpra.bean.BandBean, tum.in.dbpra.bean.Month, tum.in.dbpra.bean.StageBean, tum.in.dbpra.bean.TimeslotBean, tum.in.dbpra.bean.FestivalBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Timeslot assignment</title>
</head>
<body>

	<% if (request.getAttribute("error") != null) { %>
	<h1>Try again!</h1>
	<%= request.getAttribute("error") %>
	<% }
	else  {
	
		//retrieve Festival from db to fill From Date and Till Date drop down menus
		FestivalBean festival = new FestivalBean();
		if(pageContext.findAttribute("festival")!= null)
			festival =  (FestivalBean)pageContext.findAttribute("festival");
		Integer startYear = festival.getStartDate().getYear()+1900;
		Integer endYear = festival.getEndDate().getYear()+1900;
		Integer startMonth = festival.getStartDate().getMonth()+1;
		Integer endMonth = festival.getEndDate().getMonth()+1;
		Integer startDay = festival.getStartDate().getDate();
		Integer endDay = festival.getEndDate().getDate();
				
		//retrive Bands from db to fill the drop down menu
		List<BandBean> bands = new ArrayList<BandBean>();
		if(pageContext.findAttribute("bands")!= null && ((List)pageContext.findAttribute("bands")).size() != 0)
			bands = (List<BandBean>)pageContext.findAttribute("bands");
				
		//retrive Stages from db to fill the drop down menu
		List<StageBean> stages = new ArrayList<StageBean>();
		if(pageContext.findAttribute("stages")!= null && ((List)pageContext.findAttribute("stages")).size() != 0)
			stages = (List<StageBean>)pageContext.findAttribute("stages");
				
		//retrive booked Timeslots from db
		List<TimeslotBean> bookedTimeslots = new ArrayList<TimeslotBean>();
		if(pageContext.findAttribute("bookedTimeslots")!= null && ((List)pageContext.findAttribute("bookedTimeslots")).size() != 0)
			bookedTimeslots = (List<TimeslotBean>)pageContext.findAttribute("bookedTimeslots");
		
		//create array for visualization
		boolean booked[][] = new boolean[100][100]; 
			 for (TimeslotBean timeslotBean: bookedTimeslots){ 
				 	booked[timeslotBean.getTimeBuildUp().getHours()][timeslotBean.getTimeBuildUp().getDate()] = true;
			}	
		
		//set previously choosen attributes(if they exist)
		Integer prevStage = null;
		if(pageContext.findAttribute("prevStage")!= null)
			prevStage = Integer.parseInt(pageContext.findAttribute("prevStage").toString());
		Integer prevBand = null;
		if(pageContext.findAttribute("prevBand")!= null)
			prevBand = Integer.parseInt(pageContext.findAttribute("prevBand").toString());%>
		
	
	  <form name="timeslotform" method="post">
	  
		<table>
			  <tr>
			  		<td>Bands:</td>
			  		<td>Stages:</td>
			  		<td>Timeslots:</td>
			  </tr>
			  
			  <tr>
					<td>
						<select name="bands">
							<% //create drop down menu for Bands
							for (BandBean band: bands){ %>
							 <option <% if(prevBand==band.getpID()){ %>selected<% }%> value="<%=band.getpID()%>"><%=band.getName()%></option>
			        		<% } %>
				        </select>
					</td>
					<td>
						<select name="stages" onChange = "timeslotform.submit();">
							<%  //create drop down menu for Stages
							for (StageBean stage: stages){ %>
							 <option <% if(prevStage==stage.getSectionID()){ %>selected<% }%> value="<%=stage.getSectionID()%>"><%=stage.getName()%></option>
			        		<% } %>
				        </select>
					</td>
					<td>
						<table border="1">
							<tr>
								<td></td>
								<% //show festival dates
								for (int i = festival.getStartDate().getDate(); i <= festival.getEndDate().getDate(); i++){%>
									<td><%=i%> <%=Month.values()[startMonth-1]%></td><%}%>
							</tr>
						<%//show festival hours
						for (int i = festival.getStartTime().getHours(); i <= festival.getEndTime().getHours() - 1; i++) {%>
							<tr>
								<td><%=i%>:00</td>
								<%//show checkbox for each possible timeslot
								for (int j = festival.getStartDate().getDate(); j <= festival.getEndDate().getDate(); j++) {%>
								<td><input type="checkbox" <%if (booked[i][j]) {%> disabled <%}%> name="timecell" value="<%=i%>_<%=j%>"></td><%}%>
							</tr>
						<%}%>
						</table>
					</td>
			 </tr>
			 
		<%}%>
		        
		</table>
		<%//Submit button%>
		<input type="submit" value="Assign" name="Assign"/>
		<% //button for navigation to "Search timeslot" page%>
		</form>
						<form action="timeslotsearch">
						<p>
							<input type="submit" value="Search Timeslot" name="viewall">
						</p>
						</form>
					
     	</form>
	
       
        
		 
		 
		 
		 
		   
	
</body>
</html>
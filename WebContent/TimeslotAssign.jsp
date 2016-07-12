<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*, tum.in.dbpra.bean.BandBean, tum.in.dbpra.bean.Month, tum.in.dbpra.bean.StageBean, tum.in.dbpra.bean.TimeslotBean, tum.in.dbpra.bean.FestivalBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Part</title>
</head>
<body>

	<% if (request.getAttribute("error") != null) { %>
	<h1>Band not found!</h1>
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
		String prevStage = "";
		if(pageContext.findAttribute("prevStage")!= null && ((String)pageContext.findAttribute("prevStage")).length() != 0)
			prevStage = (String)pageContext.findAttribute("prevStage");
		String prevBand = "";
		if(pageContext.findAttribute("prevBand")!= null && ((String)pageContext.findAttribute("prevBand")).length() != 0)
			prevBand = (String)pageContext.findAttribute("prevBand");%>
		
	
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
							<% for (BandBean band: bands){ %>
							 <option <% if(prevBand.equals(band.getName())){ %>selected<% }%> value="<%=band.getName()%>"><%=band.getName()%></option>
			        		<% } %>
				        </select>
					</td>
					<td>
						<select name="stages" onChange = "timeslotform.submit();">
							<% for (StageBean stage: stages){ %>
							 <option <% if(prevStage.equals(stage.getName())){ %>selected<% }%> value="<%=stage.getName()%>"><%=stage.getName()%></option>
			        		<% } %>
				        </select>
					</td>
					<td>
						<table border="1">
							<tr>
								<td></td>
								<%for (int i = festival.getStartDate().getDate(); i <= festival.getEndDate().getDate(); i++){%>
									<td><%=i%> <%=Month.values()[startMonth-1]%></td><%}%>
							</tr>
						<%for (int i = festival.getStartTime().getHours(); i <= festival.getEndTime().getHours() - 1; i++) {%>
							<tr>
								<td><%=i%>:00</td>
								<%for (int j = festival.getStartDate().getDate(); j <= festival.getEndDate().getDate(); j++) {%>
								<td><input type="checkbox" <%if (booked[i][j]) {%> disabled <%}%> name="timecell" value="<%=i%>_<%=j%>"></td><%}%>
							</tr>
						<%}%>
						</table>
					</td>
			 </tr>
			 
		<%}%>
		        
		</table>
		
		<input type="submit" value="Assign" name="Assign"/>
		
		</form>
						<form action="timeslotviewinlines">
						<p>
							<input type="submit" value="View All" name="viewall">
						</p>
						</form>
						
						<form>
    					<input type="button" value="Back" name="back" onClick="javascript:history.back(1)">
						</form>
		
      </form>
	
       
        
		 
		 
		 
		 
		   
	
</body>
</html>
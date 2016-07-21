<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*, tum.in.dbpra.bean.BandBean, tum.in.dbpra.bean.StageBean, tum.in.dbpra.bean.TimeslotBean, tum.in.dbpra.bean.FestivalBean, tum.in.dbpra.bean.Month" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<span class="right"> <a href="welcomeOrganizer.jsp "> <strong>Back
			to Organizer Dashboard</strong>
</a>
</span>
<title>Timeslot search</title>
</head>
<body>

	<% if (request.getAttribute("error") != null) { %>
		<h1>Timeslots are not found!</h1> <%= request.getAttribute("error") %><% }
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
		
		//retrive Timeslots from db
		List<TimeslotBean> timeslots = new ArrayList<TimeslotBean>();
		if(pageContext.findAttribute("timeslots")!= null && ((List)pageContext.findAttribute("timeslots")).size() != 0)
			timeslots = (List<TimeslotBean>)pageContext.findAttribute("timeslots");
		
		//set previously choosen attributes(if they exist)
		Integer prevStage = null;
		if(pageContext.findAttribute("prevStage")!= null)
			prevStage = Integer.parseInt(pageContext.findAttribute("prevStage").toString());
		Integer prevBand = null;
		if(pageContext.findAttribute("prevBand")!= null)
			prevBand = Integer.parseInt(pageContext.findAttribute("prevBand").toString());
		Integer prevFromYear = 1;
		if(pageContext.findAttribute("prevFromYear")!= null)
			prevFromYear = (Integer)pageContext.findAttribute("prevFromYear");
		Integer prevTillYear = endYear;
		if(pageContext.findAttribute("prevTillYear")!= null)
			prevTillYear = (Integer)pageContext.findAttribute("prevTillYear");
		Integer prevFromMonth = 1;
		if(pageContext.findAttribute("prevFromMonth")!= null)
			prevFromMonth = (Integer)pageContext.findAttribute("prevFromMonth");
		Integer prevTillMonth = endMonth;
		if(pageContext.findAttribute("prevTillMonth")!= null)
			prevTillMonth = (Integer)pageContext.findAttribute("prevTillMonth");
		Integer prevFromDay = 1;
		if(pageContext.findAttribute("prevFromDay")!= null)
			prevFromDay = (Integer)pageContext.findAttribute("prevFromDay");
		Integer prevTillDay = endDay;
		if(pageContext.findAttribute("prevTillDay")!= null)
			prevTillDay = (Integer)pageContext.findAttribute("prevTillDay");
		
		%>
		<form name="timeslotviewform" method="post">
		<% //drop down menu for Bands %>
		Bands:
			 <select name="bands" onChange = "timeslotviewform.submit();">
			 	<option value="any">Any</option>
				<% for (BandBean band: bands){ %>
					<option <% if(prevBand==band.getpID()){ %>selected<% }%> value="<%=band.getpID()%>"><%=band.getName()%></option>
			    <% } %>
		    </select>
			  		
		<% //drop down menu for Stages %>
		Stages:
			<select name="stages" onChange = "timeslotviewform.submit();">
				<option value="any">Any</option>
				<% for (StageBean stage: stages){ %>
					 <option <% if(prevStage==stage.getSectionID()){ %>selected<% }%> value="<%=stage.getSectionID()%>"><%=stage.getName()%></option>
	       		<% } %>
			 </select>
		<br>
		
		<% //drop down menu for FROM year, month, day %>
		From:
			<select name="fromYear" onChange = "timeslotviewform.submit();">
				<% for (int i=startYear; i<=endYear; i++){ %>
					 <option <% if(prevFromYear==i){ %>selected<% }%> value="<%=i%>"><%=i%></option>
	       		<% } %>
			 </select>
			 <select name="fromMonth" onChange = "timeslotviewform.submit();">
				<% 	Integer currStartMonth = 1;//startMonth;
					Integer currEndMonth = 12;//endMonth;

					for (int i=currStartMonth; i<=currEndMonth; i++){ %>
					
					 <option <% if(prevFromMonth==i){ %>selected<% }%> value="<%=i%>"><%=Month.values()[i-1]%></option>
	       		<% } %>
			 </select>
			 <select name="fromDay" onChange = "timeslotviewform.submit();">
				<% 	Integer currStartDay = 1;//startDay;
					Integer currEndDay = 30;//endDay;
					
					if(request.getParameter("fromMonth")!=null)
						switch(Integer.parseInt(request.getParameter("fromMonth"))){
							case 1:
							case 3:
							case 5:
							case 7:
							case 8:
							case 10:
							case 12: currEndDay = 31; break;
							case 4:
							case 6:
							case 9:
							case 11: currEndDay = 30; break;
							case 2: {
								Integer currentYear = startYear;
								if(request.getParameter("fromYear")!=null){
										currentYear = Integer.parseInt((request.getParameter("fromYear")));
								}
								if((currentYear%4==0 && currentYear%100!=0)||currentYear%400==0)
									currEndDay = 29;
								else currEndDay = 28;
							}
						}
					
					for (int i=currStartDay; i<=currEndDay; i++){ %>
					 <option <% if(prevFromDay==i){ %>selected<% }%> value="<%=i%>"><%=i%></option>
	       		<% } %>
			 </select>
		<br>	 
		
		<% //drop down menu for TILL year, month, day %>
		Till:
			<select name="tillYear" onChange = "timeslotviewform.submit();">
				<% for (int i=startYear; i<=endYear; i++){ %>
					 <option <% if(prevTillYear==i){ %>selected<% }%> value="<%=i%>"><%=i%></option>
	       		<% } %>
			</select>	
			<select name="tillMonth" onChange = "timeslotviewform.submit();">
				<% 		currStartMonth = 1;
						currEndMonth = 12;
					
					for (int i=currStartMonth; i<=currEndMonth; i++){ %>
					 <option <% if(prevTillMonth==i){ %>selected<% }%> value="<%=i%>"><%=Month.values()[i-1]%></option>
	       		<% } %>
			 </select> 
			 <select name="tillDay" onChange = "timeslotviewform.submit();">
				<% 	currStartDay = 1;
					Integer currentMonth = 1;
					if(request.getParameter("tillMonth")==null){
						currentMonth = endMonth;
					}
					else currentMonth = Integer.parseInt(request.getParameter("tillMonth"));
					
					switch(currentMonth){
							case 1:
							case 3:
							case 5:
							case 7:
							case 8:
							case 10:
							case 12: currEndDay = 31; break;
							case 4:
							case 6:
							case 9:
							case 11: currEndDay = 30; break;
							case 2: {
								Integer currentYear = startYear;
								if(request.getParameter("tillYear")!=null){
										currentYear = Integer.parseInt((request.getParameter("tillYear")));
								}
								if((currentYear%4==0 && currentYear%100!=0)||currentYear%400==0)
									currEndDay = 29;
								else currEndDay = 28;
							}
						}
					
					for (int i=currStartDay; i<=currEndDay; i++){ %>
					 <option <% if(prevTillDay==i){ %>selected<% }%> value="<%=i%>"><%=i%></option>
	       		<% } %>
			 </select>
		 
	<%//interface of table %>	
	<table border="1">
	  <input type="hidden" name="timeslot_column_sort" />
		<!-- Headings of table -->
		<tr>
				<td> Date </td>
				<td> Time to Build Up </td>
				<td> Time to Play </td>
				<td> Type to Finish </td>
				<td> Time to Gone </td>
				<td> Band </td>
				<td> Stage </td>
		</tr>
		<%
		
	    //timeslots themselfs
		for (TimeslotBean timeslot: timeslots){%>
	 	<tr>
			<td>
				<%=timeslot.getTimeBuildUp().getDate()%>
				<%switch(timeslot.getTimeBuildUp().getMonth()){
					case 0: %> January <%; break;
					case 1: %> February <%; break;
					case 2: %> March <%; break;
					case 3: %> April <%; break;
					case 4: %> May <%; break;
					case 5: %> June <%; break;
					case 6: %> July <%; break;
					case 7: %> August <%; break;
					case 8: %> September <%; break;
					case 9: %> October <%; break;
					case 10: %> November <%; break;
					case 11: %> December <%; break;
				}%>
				<%=timeslot.getTimeBuildUp().getYear()+1900%>
			</td>
			<td><%=timeslot.getTimeBuildUp().getHours()%>:<%=timeslot.getTimeBuildUp().getMinutes()%></td>
			<td><%=timeslot.getTimePlay().getHours()%>:<%=timeslot.getTimePlay().getMinutes()%></td>
			<td><%=timeslot.getTimeFinish().getHours()%>:<%=timeslot.getTimeFinish().getMinutes()%></td>
			<td><%=timeslot.getTimeGone().getHours()%>:<%=timeslot.getTimeGone().getMinutes()%></td>
			<td><%=timeslot.getpID()%></td>
			<td><%=timeslot.getSectionID()%></td>

		</tr>
	
        	<% } %>
	 </table>
	 
	 <% //button to navigate to "Assign Timeslot page"%>
	 </form>
	 					<form action="timeslotAssign">
						<p>
							<input type="submit" value="Assign New" name="viewall">
						</p>
						</form>
	  </form>	
	
	<%}%>
          
	
</body>
</html>
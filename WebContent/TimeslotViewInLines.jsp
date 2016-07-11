<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*, tum.in.dbpra.bean.BandBean, tum.in.dbpra.bean.StageBean, tum.in.dbpra.bean.TimeslotBean, tum.in.dbpra.bean.FestivalBean, tum.in.dbpra.bean.Month" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Part</title>
</head>
<body>

	<% if (request.getAttribute("error") != null) { %>
		<h1>Band not found!</h1> <%= request.getAttribute("error") %><% }
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
		
		List<TimeslotBean> timeslots = new ArrayList<TimeslotBean>();
		if(pageContext.findAttribute("timeslots")!= null && ((List)pageContext.findAttribute("timeslots")).size() != 0)
			timeslots = (List<TimeslotBean>)pageContext.findAttribute("timeslots");
		
		//set previously choosen attributes(if they exist)
		String prevStage = "";
		if(pageContext.findAttribute("prevStage")!= null && ((String)pageContext.findAttribute("prevStage")).length() != 0)
			prevStage = (String)pageContext.findAttribute("prevStage");
		String prevBand = "";
		if(pageContext.findAttribute("prevBand")!= null && ((String)pageContext.findAttribute("prevBand")).length() != 0)
			prevBand = (String)pageContext.findAttribute("prevBand");
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
		Bands:
			 <select name="bands" onChange = "timeslotviewform.submit();">
			 	<option value="any">Any</option>
				<% for (BandBean band: bands){ %>
					<option <% if(prevBand.equals(band.getName())){ %>selected<% }%> value="<%=band.getName()%>"><%=band.getName()%></option>
			    <% } %>
		    </select>
			  		
		Stages:
			<select name="stages" onChange = "timeslotviewform.submit();">
				<option value="any">Any</option>
				<% for (StageBean stage: stages){ %>
					 <option <% if(prevStage.equals(stage.getName())){ %>selected<% }%> value="<%=stage.getName()%>"><%=stage.getName()%></option>
	       		<% } %>
			 </select>
		<br>
		From:
			<select name="fromYear" onChange = "timeslotviewform.submit();">
				<% for (int i=startYear; i<=endYear; i++){ %>
					 <option <% if(prevFromYear==i){ %>selected<% }%> value="<%=i%>"><%=i%></option>
	       		<% } %>
			 </select>
			 <select name="fromMonth" onChange = "timeslotviewform.submit();">
				<% 	Integer currStartMonth = startMonth;
					Integer currEndMonth = endMonth;
					
					if(request.getParameter("fromYear")!=null)
						if(Integer.parseInt(request.getParameter("fromYear"))==endYear)
							{if(startYear!=endYear) currStartMonth = 1;}
						else
							if(Integer.parseInt(request.getParameter("fromYear"))==startYear)
							{}
							else currStartMonth = 1;
					
					if(request.getParameter("fromYear")==null || (request.getParameter("fromYear")!=null && Integer.parseInt(request.getParameter("fromYear"))==startYear))
						{if(startYear!=endYear) currEndMonth = 12;}
					else
						if(request.getParameter("fromYear")!=null && Integer.parseInt(request.getParameter("fromYear"))==endYear){}
						else currEndMonth = 12;								
					
					for (int i=currStartMonth; i<=currEndMonth; i++){ %>
					 <option <% if(prevFromMonth==i){ %>selected<% }%> value="<%=i%>"><%=Month.values()[i-1]%></option>
	       		<% } %>
			 </select>
			 <select name="fromDay" onChange = "timeslotviewform.submit();">
				<% 	Integer currStartDay = startDay;
					
					if(request.getParameter("fromYear")!=null || request.getParameter("fromMonth")!=null)
						if(Integer.parseInt(request.getParameter("fromYear"))!=startYear || Integer.parseInt(request.getParameter("fromMonth"))!=startMonth)
							currStartDay = 1;
				
					Integer currEndDay = endDay;
					
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
		Till:
			<select name="tillYear" onChange = "timeslotviewform.submit();">
				<% for (int i=startYear; i<=endYear; i++){ %>
					 <option <% if(prevTillYear==i){ %>selected<% }%> value="<%=i%>"><%=i%></option>
	       		<% } %>
			</select>	
			<select name="tillMonth" onChange = "timeslotviewform.submit();">
				<% 	/*currStartMonth = startMonth;
					currEndMonth = endMonth;
					
					if(request.getParameter("tillYear")!=null)
						if(Integer.parseInt(request.getParameter("tillYear"))==endYear)
							{if(startYear!=endYear) currStartMonth = 1;}
						else
							if(Integer.parseInt(request.getParameter("tillYear"))==startYear)
							{}
							else currStartMonth = 1;
					
					if(request.getParameter("tillYear")==null || (request.getParameter("tillYear")!=null && Integer.parseInt(request.getParameter("tillYear"))==startYear))
						{if(startYear!=endYear) currEndMonth = 12;}
					else
						if(request.getParameter("tillYear")!=null && Integer.parseInt(request.getParameter("tillYear"))==endYear){}
						else currEndMonth = 12;		*/
						
						currStartMonth = 1;
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
		 
		
	<table border="1">
	  <input type="hidden" name="timeslot_column_sort" />
		<!-- Headings of table -->
		<tr>
				<td> <a href="javascript:getColumn('retailprice')">Date</a></td>
				<td> <a href="javascript:getColumn('partkey')">Time to Build Up</a></td>
				<td> <a href="javascript:getColumn('name')">Time to Play</a></td>
				<td> <a href="javascript:getColumn('type')">Type to Finish</a></td>
				<td> <a href="javascript:getColumn('size')">Time to Gone</a></td>
				<td> <a href="javascript:getColumn('retailprice')">Band</a></td>
				<td> <a href="javascript:getColumn('retailprice')">Stage</a></td>
		</tr>
		<%
	    
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
				<%=timeslot.getTimeBuildUp().getYear()%>
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
	  </form>	
	
	<%}%>
          
	
</body>
</html>
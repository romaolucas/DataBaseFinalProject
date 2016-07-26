<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@page import="tum.in.dbpra.dao.ProductPurchaseDAO"%>
<%@page import="tum.in.dbpra.bean.ProductPurchaseBean"%>
<%@page import="tum.in.dbpra.dao.TicketCategoryDAO"%>
<%@page import="tum.in.dbpra.bean.TicketBean"%>
<%@page import="tum.in.dbpra.bean.RFIDTicketBean"%>
<%@page import="tum.in.dbpra.dao.RFIDTicketDAO"%>
<%@page import="tum.in.dbpra.bean.TicketCategoryBean"%>
<%@page import="tum.in.dbpra.dao.TicketDAO"%>
<%@page import="tum.in.dbpra.dao.AccommodationDAO"%>
<%@page import="tum.in.dbpra.bean.AccommodationBean"%>
<%@page import="tum.in.dbpra.dao.TimetableDAO"%>
<%@page import="tum.in.dbpra.bean.TimetableBean"%>
<%@page import="tum.in.dbpra.bean.BandSelectionBean"%>
<%@page import="tum.in.dbpra.dao.ActivityLogDAO"%>
<%@page import="tum.in.dbpra.bean.ActivityLogBean"%>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/demo.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
	<script>$(function () {
	    $('.box li a').click(function (e) {
	        e.preventDefault();
	        $(this).closest('li').addClass('active').siblings().removeClass('active');
    	});
	});
	</script><link rel="stylesheet" type="text/css" href="css/animate-custom.css" />
</head>
<style type="text/css">
ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    width: 25%;
    background-color: #f1f1f1;
    position: fixed;
    height: 100%;
    overflow: auto;
}
li a {
    display: block;
    color: #000;
    padding: 8px 0 8px 16px;
    text-decoration: none;
}

li a.active {
    background-color: rgba(26,89,120,0.9);
    color: white;
}

li a:hover:not(.active) {
    background-color: #555;
    color: white;
}
table {
	border-collapse:collapse;
	border-spacing:0;
	border:1px solid black;
}

#cssTable td 
{
    text-align:center; 
    vertical-align:middle;
}
article, aside, details, figcaption, figure,
footer, header, hgroup, menu, nav, section {
	display: block;
}
</style>
<body>
<ul class="box">
	<li><a class="active" href="#home">View and Manage Rooms</a></li>
  	<li><a href="#viewTickets">View Tickets</a></li>
  	<li><a href="#RFID">RFID Details</a></li>
  	<li><a href="#Shopping">Shopping</a></li>
  	<li><a href="#timetable">Time Table</a></li>
  	<li><a href="#activity">Activity Log</a></li>
  	<li><a href="visitorLogOut.jsp">Logout</a></li>
</ul>

<div style="margin-left:25%;padding:1px 16px;height:1000px;"><br><br>
  	<h2 id="home" align="center"><b>Accommodation details of you!</b></h2><br><br>
  	<%
  	//Main jsp of visitor,set session and call different DAO
  	//to show data,DAO not moved to servlet as it need to change the whole structure
  	String vid=session.getAttribute("visitorid").toString();
  	if(vid!=null){
  		int visitorid=Integer.parseInt(vid); 
   		int rfid=0;
   	%>
  		<table border = 1 cellpadding="10" align="center">
		<% if (request.getAttribute("error") != null){ %>
 			<h3>No Room booked in your name!</h3>
			<%= request.getAttribute("error") %>
		<% }
		else 
		{ %>
			<tr>
			<th>Room No</th>
			<th>Address</th>
			<th>Rent</th>
			<th>Capacity</th>
			<th>pets</th>
			<th>Smoking</th>
			<th>Checkindate</th>
			<th>CheckoutDate</th>
			</tr>
			<%   
		    AccommodationDAO acDAO=new AccommodationDAO();
			List<AccommodationBean> AccoList = acDAO.getAccomodation(visitorid);
			if(AccoList!=null){
		        for (int i = 0; i< AccoList.size(); i++) {%>
		           <tr><td> <%= AccoList.get(i).getRoomno() %></td>
		           <td> <%= AccoList.get(i).getAddress() %> </td>
		           <td> <%= AccoList.get(i).getRent() %> </td>
		           <td> <%= AccoList.get(i).getCapacity() %> </td>
		           <td> <%= AccoList.get(i).isPets() %> </td>
		           <td> <%= AccoList.get(i).isSmoking() %></td>
		           <td> <%= AccoList.get(i).getStartdate() %> </td>
		           <td> <%= AccoList.get(i).getEndDate() %> </td>               
		           </tr>
		        <% }
			}else{
				%><tr><td colspan="8" align="center"> **You haven't booked any room!**</td></tr><%	
			}
		}  %>
		</table>
		<br><br>
		<h1 align="center"><b> Search room :</b></h1>
		<table id="mytab" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">
			<tr>
				<td><b>Search Room by Capacity</b></td>
				<td><form method="post" action="welcomeVisitor.jsp">
			    	<select name="sell">
			    		<option value="1">1</option>
				       	<option value="2">2</option>
				       	<option value="3">3</option>
				       	<option value="4">4</option>
			    	</select>
		    	</td>
		    	<td><input type="Submit" value="Submit"></td>
	    	</tr>
	    	</form>
    	</table>

		<%
		String st=request.getParameter("sell");
	 	if(st!=null){
			System.out.println("You have selected: "+st); %>
	  		<br><br>
	  		<table id="roomtab" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
			<tr class="tabletitle">
				<td class="tabletitle">Room No</td>
				<td class="tabletitle">Address</td>
				<td class="tabletitle">Rent</td>
				<td class="tabletitle">Capacity</td>
				<td class="tabletitle">Pets</td>
				<td class="tabletitle">Smoking</td>
				<td class="tabletitle">Booked From</td>
				<td class="tabletitle">Booked To</td>
			</tr>
			<%
			AccommodationDAO acDAO=new AccommodationDAO();
			List<AccommodationBean> AccoList = acDAO.getRoomByCapacity(Integer.parseInt(st));
			if(AccoList.size()>0){        
				for (int i = 0; i< AccoList.size(); i++) {%>
					<tr>  
					    <td><%=AccoList.get(i).getRoomno() %></td>
					    <td><%=AccoList.get(i).getAddress() %> </td>
					    <td><%=AccoList.get(i).getRent() %> </td>
					    <td><%=AccoList.get(i).getCapacity() %> </td>
					    <td><%=AccoList.get(i).isPets() %> </td>
					    <td><%=AccoList.get(i).isSmoking() %></td>
					    <td><%=AccoList.get(i).getCheckinDate() %></td>
					    <td><%=AccoList.get(i).getCheckoutDate() %></td>                   
				    </tr>
				<% }
			}else{
				%><tr><td colspan="8"> **No room found!**</td></tr><%
			}			
		}%>       
		</table><br><br>

		<div><h2 id="viewTickets" align="center"><b>Ticket Details</b></h2><br><br>
		<table id="roomtab" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
			<tr class="tabletitle">
				<td class="tabletitle">Name</td>
				<td class="tabletitle">Price</td>
				<td class="tabletitle">Start Date</td>
				<td class="tabletitle">End Date</td>
				<td class="tabletitle">Description</td>
				<td class="tabletitle">Duration(Hours)</td>
			</tr>
			<%
			TicketCategoryDAO tccDAO = new TicketCategoryDAO();
			List<TicketCategoryBean> tcList = tccDAO.getTCDetails();
			if(tcList.size()>0){        
				for (int i = 0; i<tcList.size(); i++) {%>
			        <tr>  
				        <td> <%= tcList.get(i).getName() %></td>
				        <td> <%= tcList.get(i).getPrice() %> </td>
				        <td> <%= tcList.get(i).getStartTime() %> </td>
				        <td> <%= tcList.get(i).getEndTime() %> </td>
				        <td> <%= tcList.get(i).getDescription() %> </td>    
				        <td> <%= tcList.get(i).getDuration() %> </td>              
			        </tr>
			    <% }
			}else{%>
	  			<tr><td>** No Ticket found ** </td></tr>      
	  		<%}%>
		</table><br><br>
		<h1 align="center"><b>Buy Ticket</b></h1><br>
		<table id="buyTicket" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
			<tr class="tabletitle">
				<form method="post" action="#">
					<td class="tabletitle">Select Ticket:</td>
					<td class="tabletitle">
						<select name="ticketName">
					        <% for (int i = 0; i< tcList.size(); i++){ %>
			                    <option value="<%=tcList.get(i).getId()%>"><%=tcList.get(i).getName()%></option>
			                <% } %>
		                </select>
		            </td>
					<td class="tabletitle">
					<select name="quantity">
		                <option value="">Select..</option>
		                <option value="1">1</option>
		                <option value="2">2</option>
		                <option value="3">3</option>
		                <option value="4">4</option>
		            </select>
		            </td>                    
					<td><input type="Submit" value="Buy"></td>
	  			</form>
	  		</tr>
	  		<tr>
			<%
				TicketDAO TicketDAO=new TicketDAO();
				String ticket=request.getParameter("ticketName");
				String quantity=request.getParameter("quantity");
				if((ticket!=null)&&(quantity!=""))
				{
				    if(TicketDAO.buyTickets(visitorid, Integer.parseInt(quantity), Integer.parseInt(ticket)))
				    {
					   	%><td class="tabletitle" colspan = "4"><h1 align="center" color="green">**You have Successfully bought the tickets </h1></td>					    	
					<%}else{
					    %><td class="tabletitle" colspan = "4"><h1 align="center" color="red">**Some error has occurred,try later! </h1></td><%
					}
				}
			%>   
			</tr>
	    </table> <br><br><br> 
		</div>

		<div><h2 id="RFID" align="center"><b>Your Wristband RFID Details</b></h2><br>
		<table id="rfidStatus" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
			<tr class="tabletitle">
				<td class="tabletitle">Status</td>
				<td class="tabletitle">Balance</td>
				<td class="tabletitle">Activation Date</td>
			</tr>
			
			<% RFIDTicketDAO RFIDDAO=new RFIDTicketDAO();
			List<RFIDTicketBean> rfList = RFIDDAO.getRFIDDetails(visitorid);
	        if(rfList!=null){
	    		for (int i = 0; i< rfList.size(); i++) {
	           		rfid=rfList.get(i).getRfid();%>
	           		<tr>  
			        	<td> <%= rfList.get(i).isStatus() %></td>
			           	<td> <%= rfList.get(i).getBalance() %> </td>
			           	<td> <%= rfList.get(i).getActivationDate() %></td>			           	               
	           		</tr>
	        	<% }%>
				<tr>
					<td>Report Lost:</td>
					<form method="post" action="welcomeVisitor.jsp#RFID">
				 	<td><input name="rfidComments" type="text"> </td>
				 	<td><input type="Submit" value="Submit"></td></tr> 
				 	</form>
				 	<% String comments=request.getParameter("rfidComments");		    
				 	if(comments!=null){
						comments="::"+comments;
					 	if(RFIDDAO.updateDisableComment(rfid, comments)){%>
					 		<tr><td colspan = "3"> **Your comment updated in database.</td></tr>					 		
				 		<%}else{%>
					  		<tr><td colspan = "3"> **Error occurred while updating.</td></tr> 
				  		<%} 
					}
				}else{%>
					<tr><td colspan = "3" align="center"> **No wristband associated with you.</td></tr>   
				<% }%>      
		</table><br><br>
		</div>

		<div><h2 id="RFID" align="center"><b>Your ticket buying details:</b></h2>
		<table id="rfidStatus" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
			<tr class="tabletitle">
				<td class="tabletitle">Ticket No</td>
				<td class="tabletitle">Ticket Name</td>
				<td class="tabletitle">Price</td>
				<td class="tabletitle">Purchase Date</td>
				<td class="tabletitle">Activation Time</td>	
			</tr>
			<% 
			List<TicketBean> ticketList1 = TicketDAO.getTicketbyVisitor(visitorid);
			if(ticketList1!=null){
				for (int i = 0; i< ticketList1.size(); i++) {%>
			    <tr>  
					<td> <%= ticketList1.get(i).getTickeID() %></td>
				    <td> <%= ticketList1.get(i).getName() %> </td>
				    <td> <%= ticketList1.get(i).getPrice() %> </td>                    
				    <td> <%= ticketList1.get(i).getPurchaseDate() %> </td>      
				    <td> <%= ticketList1.get(i).getActivationDate() %> </td>		           
			    </tr>
				<% }
			}else{%>
				<tr><td colspan="5" align="center">**You have not bought any ticket**</td></tr>			        	
			<%}%>
       
		</table><br><br>
		</div>

		<div><br><br><h2 id="Shopping" align="center"><b>Your Shopping and Purchase Details</b></h2><br><br>
		<h2 id="RFID" align="center"><b>Product List Table</b></h2>
		<table id="rfidStatus" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
		<tr class="tabletitle">
			<td class="tabletitle">Name</td>
			<td class="tabletitle">Category</td>
			<td class="tabletitle">Total Price</td>
			<td class="tabletitle">Purchase Date</td>
			<td class="tabletitle">Quantity</td>
		</tr>
		<%
		ProductPurchaseDAO ppDAO=new ProductPurchaseDAO();
		List<ProductPurchaseBean> purchaseList = ppDAO.getPurchaseList(visitorid);
		if(purchaseList!=null){
			for (int i = 0; i< purchaseList.size(); i++) {%>
		    <tr>  
		    	<td> <%= purchaseList.get(i).getProductName() %></td>
		        <td> <%= purchaseList.get(i).getProductCategory() %> </td>
		        <td> <%= purchaseList.get(i).getTotalPrice() %> </td>                    
		        <td> <%= purchaseList.get(i).getPaymentDate() %> </td>      
		        <td> <%= purchaseList.get(i).getQuantity() %> </td>		           
			</tr>
		    <% }
		}else{%>
			<tr><td colspan="5" align="center">**You have not bought any product**</td></tr>
		<%}%>
		</table><br><br>
		</div>

		<div><br><br><h2 id="timetable" align="center"><b>Timetable Details</b></h2><br><br>
		<h2 id="RFID" align="center"><b>Timetable</b></h2>
		<table id="rfidStatus" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
		<tr class="tabletitle">
			<td class="tabletitle">ID</td>
			<td class="tabletitle">Name</td>
			<td class="tabletitle">Style</td>
			<td class="tabletitle">Country</td>
			<td class="tabletitle">Charge</td>
			<td class="tabletitle">Section ID</td>
			<td class="tabletitle">Time Build-Up</td>
			<td class="tabletitle">Time Play</td>
			<td class="tabletitle">Time Finish</td>
			<td class="tabletitle">Time Gone</td>
			<td class="tabletitle">Select</td>
		</tr>
		<%
		TimetableDAO ttDAO=new TimetableDAO();
		List<TimetableBean> ttList = ttDAO.getTimetableDetails();
		if(ttList!=null){
			for (int i = 0; i<ttList.size(); i++) {%>
		    <tr>  
		    	<td> <%= ttList.get(i).getId() %></td>
		    	<td> <%= ttList.get(i).getName() %></td>
		    	<td> <%= ttList.get(i).getStyle() %></td>
		        <td> <%= ttList.get(i).getCountry() %> </td>
		        <td> <%= ttList.get(i).getCharge() %> </td>
		        <td> <%= ttList.get(i).getSectionid() %> </td>
		        <td> <%= ttList.get(i).getTimebuildup() %> </td>
		        <td> <%= ttList.get(i).getTimeplay() %> </td>
		        <td> <%= ttList.get(i).getTimefinish() %> </td>
		        <td> <%= ttList.get(i).getTimegone() %> </td>	
		        <td><form method="post" action="welcomeVisitor.jsp#timetable">
		        <input type="checkbox" name="check" value="<%=i%>"/>
		 	</tr>		 	
		    <% }%>
			<tr><td><input type="Submit" value="Submit"></td></tr>
			</form>
			<tr>
			<%
			String select[] = request.getParameterValues("check");
			TimetableDAO ttDao = new TimetableDAO();
			if (select != null && select.length != 0) {
					if(ttDao.custTimetable(visitorid, ttList, select, select.length)){
						%><td class="tabletitle" colspan = "9"><h1 align="center" color="green">**You have successfully updated your timetable!** </h1></td>					    	
						<%}else{
						%><td class="tabletitle" colspan = "9"><h1 align="center" color="red">**Some error has occurred,try later!** </h1></td><%
				 	}
				
			}
			%>
			</tr>
		<%	
		}else{%>
			<tr><td colspan="5" align="center">**There are no timetable available**</td></tr>
		<%}%>
		</table><br><br>
		
		
		</div>
		<div><br><br><h2 id="timetable" align="center"><b>Customized Timetable</b></h2><br><br>
		<h2 id="RFID" align="center"><b>Timetable</b></h2>
		<table id="rfidStatus" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
		<tr class="tabletitle">
			<td class="tabletitle">Visitor ID</td>
			<td class="tabletitle">Band ID</td>
			<td class="tabletitle">Name</td>
			<td class="tabletitle">Style</td>
			<td class="tabletitle">Country</td>
			<td class="tabletitle">Time Play</td>
			<td class="tabletitle">Time Finish</td>
			<td class="tabletitle">Select</td>
		</tr>

		<%
		
		TimetableDAO tt1DAO=new TimetableDAO();
		List<BandSelectionBean> bsList = tt1DAO.getBandSelection();
		List<BandSelectionBean> bsList1 = tt1DAO.getBandSelection();
		String select1[] = request.getParameterValues("check1");
		if(bsList!=null){
			if (select1 != null && select1.length != 0)
				for(int i=0;i<select1.length;i++)
					bsList.remove(Integer.parseInt(select1[i]));
			for (int i = 0; i<bsList.size(); i++) {%>
		    <tr>
		    	<td> <%= bsList.get(i).getVisitorID() %></td>  
		    	<td> <%= bsList.get(i).getBandID() %></td>
		    	<td> <%= bsList.get(i).getName() %></td>
		    	<td> <%= bsList.get(i).getStyle() %></td>
		        <td> <%= bsList.get(i).getCountry() %> </td>
		        <td> <%= bsList.get(i).getTimeplay() %> </td>
		        <td> <%= bsList.get(i).getTimefinish() %> </td>
		        <td><form method="post" action="welcomeVisitor.jsp#timetable">
		        <input type="checkbox" name="check1" value="<%=i%>"/>
		 	</tr>		 	
		    <%}%>
			<tr><td><input type="Submit" value=" Delete "></td></tr>
			</form>
			<tr>
			<%
			
			TimetableDAO ttDao1 = new TimetableDAO();
			if (select1 != null && select1.length != 0) {
					if(ttDao1.delRowTimetable(visitorid, bsList1, select1, select1.length)){
						%><td class="tabletitle" colspan = "12"><h1 align="center" color="green">**You have successfully updated your timetable!** </h1></td>					    	
						<%}else{
						%><td class="tabletitle" colspan = "12"><h1 align="center" color="red">**Some error has occurred,try later!** </h1></td><%
				 	}				
			}
			%>
			</tr>
			<%}else{%>
			<tr><td colspan="5" align="center">**There are no customized timetable available**</td></tr>
		<%}%>
		</table><br><br>
		</div>
		
		<div><br><br><h2 id="activity" align="center"><b>Activity Log</b></h2><br><br>
		<h2 id="RFID" align="center">Log</h2>
		<table id="rfidStatus" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
		<tr class="tabletitle">
			<td class="tabletitle">RF ID</td>
			<td class="tabletitle">Stage ID</td>
			<td class="tabletitle">Enter Time</td>
			<td class="tabletitle">Exit Time</td>
		</tr>
		<%
		ActivityLogDAO alDAO=new ActivityLogDAO();
		List<ActivityLogBean> alList = alDAO.getActivityLog(visitorid);
		if(alList!=null){
			for (int i = 0; i<alList.size(); i++) {%>
		    <tr>
		    	<td> <%= alList.get(i).getRfid() %></td>
		    	<td> <%= alList.get(i).getStageid() %></td>
		    	<td> <%= alList.get(i).getEntertime() %></td>
		    	<td> <%= alList.get(i).getExittime() %></td>
		 	</tr>		 	
		    <%}
			}%>
		</table><br><br><br>
		</div>
		
<% }%>
		
</div>
</body>
</html>


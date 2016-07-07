<%@page import="tum.in.dbpra.dao.TicketCategoryDAO"%>
<%@page import="tum.in.dbpra.bean.TicketBean"%>
<%@page import="tum.in.dbpra.bean.RFIDTicketBean"%>
<%@page import="tum.in.dbpra.dao.RFIDTicketDAO"%>
<%@page import="tum.in.dbpra.bean.TicketCategoryBean"%>
<%@page import="tum.in.dbpra.dao.TicketDAO"%>
<%@page import="tum.in.dbpra.dao.AccommodationDAO"%>
<%@page import="tum.in.dbpra.bean.AccommodationBean"%>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/style.css" />
		<link rel="stylesheet" type="text/css" href="css/animate-custom.css" />
<style>
body {
    margin: 0;
}

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
</style>
</head>
<body>

<ul>
  <li><a class="active" href="#home">View & Manage Rooms</a></li>
  <li><a href="#viewTickets">View Tickets</a></li>
  <li><a href="#RFID">RFID Details</a></li>
  <li><a href="#about">Shopping</a></li>
  <li><a href="#about">Time Table</a></li>
  <li><a href="#about">Activity Log</a></li>
  <li><a href="#about">Logout</a></li>
</ul>

<div style="margin-left:25%;padding:1px 16px;height:1000px;">
  <h2 id="home">Accomodation details of you!</h2>
  <%int visitorid=Integer.parseInt(session.getAttribute("visitorid").toString()); %>
  <table border = 1 cellpadding="10">
<% if (request.getAttribute("error") != null){ %>
 	<h3>No Room booked in your name!</h3>
	<%= request.getAttribute("error") %>
	<%} else { %>
	<tr>
	<th>Room No</th>
	<th>Address</th>
	<th>Rent</th>
	<th>Capasity</th>
	<th>pets</th>
	<th>Smoking</th>
	<th>Chekindate</th>
	<th>CheckoutDate</th>
	</tr>
	<%   
	    AccommodationDAO acDAO=new AccommodationDAO();
		List<AccommodationBean> AccoList = acDAO.getAccomodation(visitorid);
        for (int i = 0; i< AccoList.size(); i++) {%>
           <tr><td> <%= AccoList.get(i).getRoomno() %></td>
           <td> <%= AccoList.get(i).getAddress() %> </td>
           <td> <%= AccoList.get(i).getRent() %> </td>
           <td> <%= AccoList.get(i).getCapasity() %> </td>
           <td> <%= AccoList.get(i).isPets() %> </td>
           <td><%=AccoList.get(i).isSmoking() %></td>
           <td> <%= AccoList.get(i).getStartdate() %> </td>
           <td> <%= AccoList.get(i).getEndDate() %> </td>               
           </tr>
        <% } } %>
	</table>
	<br><br>
<p align="center"> Search room :</p>
<table id="mytab" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">
<tr><td>Search Room by Capacity</td>
<td><form method="post" action="welcomeVisitor.jsp">
    <select name="sell">    
       <option value="1">1</option>
       <option value="2">2</option>
       <option value="3">3</option>
       <option value="4">4</option>
    </select>
    </td>
    <td><input type="Submit" value="Submit">
</form></td></tr></table>

<%
  String st=request.getParameter("sell");
  if(st!=null){
    System.out.println("You have selected: "+st);
  %>
  <br><br>
  <table id="roomtab" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
<tr class="tabletitle">
<td class="tabletitle">Room No</td>
<td class="tabletitle">Address</td>
<td class="tabletitle">Rent</td>
<td class="tabletitle">Smoking</td>
<td class="tabletitle">Pets</td>
<td class="tabletitle">Capacity</td>
</tr>
<% AccommodationDAO acDAO=new AccommodationDAO();
List<AccommodationBean> AccoList = acDAO.getRoomByCapasity(Integer.parseInt(st));
        for (int i = 0; i< AccoList.size(); i++) {%>
           <tr>  
           <td> <%= AccoList.get(i).getRoomno() %></td>
           <td> <%= AccoList.get(i).getAddress() %> </td>
           <td> <%= AccoList.get(i).getRent() %> </td>
           <td> <%= AccoList.get(i).getCapasity() %> </td>
           <td> <%= AccoList.get(i).isPets() %> </td>
           <td><%=AccoList.get(i).isSmoking() %></td>                  
           </tr>
        <% }
        }%>
       
</table><br><br>
<div><h2 id="viewTickets" align="center">Ticket Details</h2>
<table id="roomtab" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
<tr class="tabletitle">
<td class="tabletitle">Name</td>
<td class="tabletitle">Price</td>
<td class="tabletitle">Start Date</td>
<td class="tabletitle">End Date</td>
<td class="tabletitle">Description</td>
</tr>
<% TicketCategoryDAO tccDAO=new TicketCategoryDAO();
List<TicketCategoryBean> tcList = tccDAO.getTCDetails();
        for (int i = 0; i< tcList.size(); i++) {%>
           <tr>  
           <td> <%= tcList.get(i).getName() %></td>
           <td> <%= tcList.get(i).getPrice() %> </td>
           <td> <%= tcList.get(i).getStartTime() %> </td>
           <td> <%= tcList.get(i).getEndTime() %> </td>
           <td> <%= tcList.get(i).getDescription() %> </td>                 
           </tr>
        <% }%>
       
</table><br><br>

<table id="buyTicket" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
<tr class="tabletitle"><form method="post" action="#">
<td class="tabletitle">Select Ticket:</td>
<td class="tabletitle">  <select name="ticketName">
                            <% for (int i = 0; i< tcList.size(); i++){ %>
                             <option value="<%=tcList.get(i).getName()%>"><%=tcList.get(i).getName()%></option>
                            <% } %>
                        </select>
                    </td>
<td class="tabletitle"><select name="quantity">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            </select></td>                    
<td><input type="Submit" value="Buy"></td>              
            
  </from> </tr>
     </table> 
  <%
  String ticket=request.getParameter("ticketName");
  String quantity=request.getParameter("quantity");
  if(ticket!=null){
    System.out.println("You have tickect: "+ticket);
    System.out.println("You have quantity: "+quantity);
  }
  %>   
     
</div>
<div><h2 id="RFID" align="center">Your Wristband RFID Details</h2>
<table id="rfidStatus" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
<tr class="tabletitle">
<td class="tabletitle"> Status</td>
<td class="tabletitle">Balance</td>
<td class="tabletitle">Activation Date</td>
</tr>
<% RFIDTicketDAO RFIDDAO=new RFIDTicketDAO();
List<RFIDTicketBean> rfList = RFIDDAO.getRFIDDetails(visitorid);
        for (int i = 0; i< rfList.size(); i++) {%>
           <tr>  
           <td> <%= rfList.get(i).isStatus() %></td>
           <td> <%= rfList.get(i).getBalance() %> </td>
           <td> <%= rfList.get(i).getActivationDate() %> </td>                    
           </tr>
        <% }%>
       
</table><br><br></div>
<div><h2 id="RFID" align="center">Your ticket buying details:</h2>
<table id="rfidStatus" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
<tr class="tabletitle">
<td class="tabletitle"> Ticket No</td>
<td class="tabletitle"> Ticket Name</td>
<td class="tabletitle">Purchase Date</td>
<td class="tabletitle">Balance</td>

</tr>
<% TicketDAO TicketDAO=new TicketDAO();
List<TicketBean> ticketList1 = TicketDAO.getTicketbyVisitor(visitorid);
        for (int i = 0; i< ticketList1.size(); i++) {%>
           <tr>  
           <td> <%= ticketList1.get(i).getTickeID() %></td>
           <td> <%= ticketList1.get(i).getName() %> </td>
           <td> <%= ticketList1.get(i).getPrice() %> </td>                    
           <td> <%= ticketList1.get(i).getPurchaseDate() %> </td>      
           
           </tr>
        <% }%>
       
</table><br><br></div>

</div>
</body>
</html>


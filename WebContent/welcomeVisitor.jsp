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
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

});</script>		<link rel="stylesheet" type="text/css" href="css/animate-custom.css" />
</head>
<body>
<ul  class="box">
  <li><a class="active" href="#home">View & Manage Rooms</a></li>
  <li><a  href="#viewTickets">View Tickets</a></li>
  <li><a  href="#RFID">RFID Details</a></li>
  <li><a href="#Shopping">Shopping</a></li>
  <li><a href="#about">Time Table</a></li>
  <li><a href="#about">Activity Log</a></li>
  <li><a href="visitorLogOut.jsp">Logout</a></li>
</ul>

<div style="margin-left:25%;padding:1px 16px;height:1000px;"><br><br>
  <h2 id="home" align="center">Accomodation details of you!</h2><br><br>
  <%String vid=session.getAttribute("visitorid").toString();
  System.out.println("Test 1st ok");
  if(vid!=null){
  int visitorid=Integer.parseInt(vid); 
   int rfid=0;%>
  <table border = 1 cellpadding="10" align="center">
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
		if(AccoList!=null){
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
        <% }
		}else{
			%><tr><td colspan="8" align="center"> **You haven't booked any room!**</td></tr><%	
		}
        	}  %>
	</table>
	<br><br>
<h1 align="center"> Search room :</h1>
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
<td class="tabletitle">Capacity</td>
<td class="tabletitle">Pets</td>
<td class="tabletitle">Smoking</td>
<td class="tabletitle">Booked From</td>
<td class="tabletitle">Booked To</td>
</tr>
<%  System.out.println("Test 2nd ok");
AccommodationDAO acDAO=new AccommodationDAO();
List<AccommodationBean> AccoList = acDAO.getRoomByCapasity(Integer.parseInt(st));
if(AccoList.size()>0){        
for (int i = 0; i< AccoList.size(); i++) {%>
           <tr>  
           <td> <%= AccoList.get(i).getRoomno() %></td>
           <td> <%= AccoList.get(i).getAddress() %> </td>
           <td> <%= AccoList.get(i).getRent() %> </td>
           <td> <%= AccoList.get(i).getCapasity() %> </td>
           <td> <%= AccoList.get(i).isPets() %> </td>
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
<div><h2 id="viewTickets" align="center">Ticket Details</h2><br><br>
<table id="roomtab" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
<tr class="tabletitle">
<td class="tabletitle">Name</td>
<td class="tabletitle">Price</td>
<td class="tabletitle">Start Date</td>
<td class="tabletitle">End Date</td>
<td class="tabletitle">Description</td>
<td class="tabletitle">Duration(Hours)</td>
</tr>
<% System.out.println("Test 3rd ok");
TicketCategoryDAO tccDAO=new TicketCategoryDAO();
List<TicketCategoryBean> tcList = tccDAO.getTCDetails();
if(tcList.size()>0){        
for (int i = 0; i< tcList.size(); i++) {%>
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
<h1 align="center">Buy Ticket</h1><br>
<table id="buyTicket" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
<tr class="tabletitle"><form method="post" action="#">
<td class="tabletitle">Select Ticket:</td>
<td class="tabletitle">  <select name="ticketName">
                            <% for (int i = 0; i< tcList.size(); i++){ %>
                             <option value="<%=tcList.get(i).getId()%>"><%=tcList.get(i).getName()%></option>
                            <% } %>
                        </select>
                    </td>
<td class="tabletitle"><select name="quantity">
                            <option value="">Select..</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            </select></td>                    
<td><input type="Submit" value="Buy"></td>              
            
  </from> </tr><tr>
  <%System.out.println("Test 4th ok");
  TicketDAO TicketDAO=new TicketDAO();
  String ticket=request.getParameter("ticketName");
  String quantity=request.getParameter("quantity");
 
  if((ticket!=null)&&(quantity!=""))	{
      if(TicketDAO.buyTickets(visitorid, Integer.parseInt(quantity), Integer.parseInt(ticket))){
    	%><td class="tabletitle" colspan = "4"><h1 align="center" color="green">**You have Sucessfully brought the tickets </h1></td>       <%	
    }else{
    	%><td class="tabletitle" colspan = "4"><h1 align="center" color="red">**Some error has occured,try later! </h1></td><%
    }
  }
  %>   
 </tr>
     </table> <br><br><br>
 
</div>
<div><h2 id="RFID" align="center">Your Wristband RFID Details</h2><br>
<table id="rfidStatus" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
<tr class="tabletitle">
<td class="tabletitle"> Status</td>
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
           <td> <%= rfList.get(i).getActivationDate() %> </td>                    
           </tr>
        <% }%>
 <tr><td>Report Lost:</td>
 <td><input name="rfidComments" type="text"> </td>
 <td><input type="Submit" value="Submit"></td></tr> 
 <% String comments=request.getParameter("rfidComments");
    
 if(comments!=null){
	 comments="::"+comments;
	 if(RFIDDAO.updateDisableComment(rfid, comments)){%>
 <tr><td colspan = "3"> **Your comment updated in database.</td></tr>      
  <%
 }else{%>
	  <tr><td colspan = "3"> **Error occured while updating.</td></tr> 
  <% } }
  }else{%>
	<tr><td colspan = "3" align="center"> **No Wristband associated with you.</td></tr>   
  <% }%>      
</table><br><br></div>
<div><h2 id="RFID" align="center">Your ticket buying details:</h2>
<table id="rfidStatus" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
<tr class="tabletitle">
<td class="tabletitle"> Ticket No</td>
<td class="tabletitle"> Ticket Name</td>
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
<tr><td colspan="5" align="center">**You have not buy any ticket**</td></tr>       	
        	
        <%}%>
       
</table><br><br></div>
<div><br><br><h2 id="Shopping" align="center">Your Shopping and Purchase Details</h2><br><br>
<h2 id="RFID" align="center">Product List Table</h2>
<table id="rfidStatus" align="center" border="1" width="50%" cellspacing="0" cellpadding="4">  
<tr class="tabletitle">
<td class="tabletitle"> Name</td>
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
<tr><td colspan="5" align="center">**You have not buy any product**</td></tr>       	
        	
        <%}%></table><br><br></div>
</div>
</div>
<% }else{
	response.sendRedirect("/index.jsp");
	request.setAttribute("loginMessage", "Session Expired");

}%>
</body>
</html>


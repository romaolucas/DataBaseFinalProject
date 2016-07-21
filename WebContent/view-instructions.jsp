<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.List, tum.in.dbpra.bean.InstructionBean" %>
 <!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="UTF-8" />
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
        <title>Database Final Project</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <link rel="shortcut icon" href="../favicon.ico"> 
        <link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/style.css" />
		<link rel="stylesheet" type="text/css" href="css/animate-custom.css" />
	<style>
	.instructionclass {
	    border-radius: 5px;
	    background-color: #f2f2f2;
	    padding: 20px;
	}
	table {
	    border-collapse: collapse;
	    width: 100%;
	}
	th, td {
	    padding: 8px;
	    text-align: center;
	    border-bottom: 1px solid #ddd;
	}
	tr{background-color:white}
	tr:hover{background-color:#f5f5f5}
	</style>
	</head>
	<body>
	    <%
	    //allow access only if session exists
	    String user = null;
	    if(session.getAttribute("email") == null){
	    	response.sendRedirect("login-provider.jsp");
	    }else{
	    	user = (String) session.getAttribute("email");
	    }
	    String userName = null;
	    String sessionID = null;
	    Cookie[] cookies = request.getCookies();
	    if(cookies != null){
	    	for(Cookie cookie : cookies){
	    		if(cookie.getName().equals("email")){
	    			userName = cookie.getValue();
	    		}
	    		if(cookie.getName().equals("JSESSIONID")){
	    			sessionID = cookie.getValue();
	    		}
	    	}
	    }
	    %>
	<div class="container">
            <!-- Codrops top bar -->
	    <div class="codrops-top">
	    <header>
        <h1><strong>Database Practical Final Project</strong></h1>
        </header>			
        	<span class="right">
                    <!-- <a href="checkout-provider.jsp"> -->
                    <!--    <strong>Logout</strong> -->
                    </a>
                    <form action="byeProvider" method="post">
                    <input type="submit" value="Logout">
                    </form>
            </span>
            <div class="clr">
            </div>
        </div><!--/ Codrops top bar -->
	    <header>
                <h1 style="font-size: 300%;">Instructions Page <!-- <%=userName%> --></h1>
                <!-- <h2 style="font-size: 300%;">Your session ID = <%=sessionID%></h2> -->
                <!-- <h3 style="font-size: 300%;">User = <%=user%></h3> -->
		</header>
		<form>
		<div class="instructionclass">
			<%String noinstructions = (String) request.getAttribute("noinstructions");%>
			<%if(noinstructions.equals("yes")){%>
				<strong>You have no instructions</strong>
				<br>
				Add one now
				<br>
				Click 
				<a href="add-instruction.jsp"><u>here</u></a> to add another instruction
		        <br>
			<%}else{%>
				<strong>This is the list of your previous instructions:</strong>
				<br>
				<% List<InstructionBean> instructions = (List<InstructionBean>) request.getAttribute("instructions");%>
				<table border="2" style="margin-left: auto; margin-right: auto;">
					<tr>
		                <th align="center">Instruction ID</th>
		                <th align="center">Title</th>
		                <th align="center">Description</th>
		                <th align="center">Status</th>
		                <th align="center">Creation Date</th>
		                <th align="center">Employee In Charge</th>
		                <th align="center">Assigned Date</th>
		            </tr>
					<%for(int i=0; i<instructions.size(); i++){%>
						<tr>
			                <td> <%= instructions.get(i).getId() %></td>
			                <td> <%= instructions.get(i).getTitle() %></td>
			                <td> <%= instructions.get(i).getDescription() %></td>
			                <td> <%= instructions.get(i).getStatus() %></td>
			                <td> <%= instructions.get(i).getCreationdate() %></td>
			                <%if(instructions.get(i).getEmpfirstname()!=null){%>
				                <td> <%= instructions.get(i).getEmpfirstname() %>
				                <%= instructions.get(i).getEmplastname() %>
				                </td>
				                <td> <%= instructions.get(i).getAssigneddate() %></td>
			                <%}else{%>
			                	<td> Not assigned yet</td>
				                <td> Not assigned yet</td>
			                <%}%>
			            </tr>
					<%}%>
				</table>
				</div>
				<br><br>
				<div class="instructionclass">
				<strong>Would you like to add a new instruction?</strong><br>
				Click <a href="add-instruction.jsp"><u>here</u></a>
		        </div>
		        <br>
			<%}%>
		</form>
	</div>
    </body>
</html>


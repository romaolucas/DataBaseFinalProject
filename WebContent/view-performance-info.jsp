<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.List, tum.in.dbpra.bean.TimeSlotBean" %>
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
            <div class="codrops-top"><header>
              <h1><strong>Database Practical Final Project</strong></h1></header>			
                  <span class="right">
                    <!-- <a href="checkout-provider.jsp"> -->
                    <!--    <strong>Logout</strong> -->
                    </a>
                    <form action="byeProvider" method="post">
                    <input type="submit" value="Logout">
                    </form>
                </span>
                <div class="clr"></div>
            </div><!--/ Codrops top bar -->
            <header>
                <h1 style="font-size: 300%;">Performance Information Page <!-- <%=userName%> --></h1>
                <!-- <h2 style="font-size: 300%;">Your session ID = <%=sessionID%></h2> -->
                <!-- <h3 style="font-size: 300%;">User = <%=user%></h3> -->
			</header>
            <section>	
            <div id="container_demo" >
                    	<!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
                    	<div id="wrapper">
                    	<div id="login" class="animate form">
                    	
                    	
                    		<form>
                    			<% List<TimeSlotBean> timeslots = (List<TimeSlotBean>) request.getAttribute("timeslots");%>
                    			
                    			<%for(int i=0; i<timeslots.size(); i++){%>
                    				<strong>Time Slot N <%=i+1%>:</strong><br>
	                    			<strong>Time to be on stage: </strong>
	                    			<%=timeslots.get(i).getTimebuildup()%>
	                    			<br>
	                    			<strong>Time to start performance: </strong>
	                    			<%=timeslots.get(i).getTimeplay()%>
	                    			<br>
	                    			<strong>Time to finish performance: </strong>
	                    			<%=timeslots.get(i).getTimefinish()%>
	                    			<br>
	                    			<strong>Time to leave stage: </strong>
	                    			<%=timeslots.get(i).getTimegone()%>
	                    			<br>
	                    			<strong>Stage: </strong>
	                    			<%=timeslots.get(i).getStageName()%>
	                    			<br>
	                    			<strong>Address: </strong>
	                    			<%=timeslots.get(i).getStageAddress()%>
	                    			<br>
	                    			<br>
                    			<%}%>
                    		</form>
                    	
                    	
                    	</div>
                    	</div>
            </div>  
            </section>
    </div>
    </body>
</html>

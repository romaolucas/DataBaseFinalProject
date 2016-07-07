<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.List, tum.in.dbpra.bean.ProviderBean" %>
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
    	<script type="text/javascript">
                function bandOrSponsor() {
                    if (document.getElementById("band").checked) {
                        document.getElementById("ifBand").style.display = "block";
                        document.getElementById("ifSponsor").style.display = "none";
                        document.getElementById("style").required = true;
                        document.getElementById("charge").required = true;
                        document.getElementById("type").required = false;
                        document.getElementById("amount").required = false;
                    }
                    else if (document.getElementById("sponsor").checked) {
                        document.getElementById("ifSponsor").style.display = "block";
                        document.getElementById("ifBand").style.display = "none";
                        document.getElementById("type").required = true;
                        document.getElementById("amount").required = true;
                        document.getElementById("style").required = false;
                        document.getElementById("charge").required = false;
                    }   
                }

        </script>
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
                <h1 style="font-size: 300%;">Welcome Provider <!-- <%=userName%> --></h1>
                <!-- <h2 style="font-size: 300%;">Your session ID = <%=sessionID%></h2> -->
                <!-- <h3 style="font-size: 300%;">User = <%=user%></h3> -->
			</header>
            <section>				
                <div id="container_demo" >
                    	<!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
                       	<% if ((Boolean) request.getAttribute("application")) {%>
	                    	<div id="wrapper">
                    	<div id="login" class="animate form">
                         		
		                            	<% List<ProviderBean> providers = (List<ProviderBean>) request.getAttribute("providers");
		                            	 %>
		                            	<strong> Name: </strong>
		                            	<%= providers.get(0).getName() %>
		                            	<br>
		                            	<strong> Phone number: </strong>
		                            	<%= providers.get(0).getPhone() %>
		                            	<br>
		                            	<strong> Email: </strong>
		                            	<%= providers.get(0).getEmail() %>
		                            	<br>
		                            	<strong> Website: </strong>
		                            	<%= providers.get(0).getWebsite() %>
		                            	<br>
		                            	<strong> Address: </strong>
		                            	<%= providers.get(0).getAddress() %>
		                            	<br>
		                            	<strong> Application Status: </strong>
		                            	<%= providers.get(0).getStatus() %>
		                            	<br>
		                            	<strong> Provider Category: </strong>
		                            	<%= providers.get(0).getCategory() %>
		                            	<br>
		                            	<%if(providers.get(0).getStatus().equals("Approved") && providers.get(0).getCategory().equals("Band")){%>
		                            	<form action="ViewPerformanceInfoServlet" method="post">
		                            	<input type="submit" value="View Performance Information"/>
		                            	</form>
		                            	<form action="ManageInstructionsServlet" method="post">
		                            	<input type="submit" value="Manage Instructions"/>
		                            	</form>
		                            	<%}if(providers.get(0).getStatus().equals("Approved")){ %> 
		                                <form action="productManagement" method="get">
                                        <input type="hidden" name="pid" value="<%= providers.get(0).getId() %>"/>
                                        <input type="submit" value="Manage Products"/>
		                            	</form>
		                            	<%}%>
	                            	<% } else { %>
                                    <div id="wrapper">
                                        <div id="login" class="animate form">
	                            	<strong>Submit your application
<% List<ProviderBean> providers = (List<ProviderBean>) request.getAttribute("providers");%>           	
<%= providers.get(0).getName() %>:
</strong>     
<form method="post" action="submitApplication" id="appForm">
	<p>
		<input type="hidden" name="pid" id="providerID" value="<%=providers.get(0).getId()%>"/>
		<label for="band" class="style" >
			<input id="band" name="category" required="required" value="band" type="radio" checked="checked" onclick="javascript:bandOrSponsor();"/> 
			Band
		</label>
		<label for="sponsor" class="style" >
			<input id="sponsor" name="category" required="required" value="sponsor" type="radio" onclick="javascript:bandOrSponsor();"/> 
		 	Sponsor
		 	</label>
	</p>
	<div id="ifBand" style="display:block">
		<p> 
	    	<label for="style" class="style" > Style </label>
	    	<input id="style" name="style" type="text" placeholder="eg. Rock, Jazz" />
		</p>
		<p>
	    	<label for="charge" class="style" > Charge</label>
	    	<input id="charge" name="charge" type="text" placeholder="eg. 10000.00" />
	    </p>
		<p>
	    	<label for="country" class="country" > Country</label>
	      	<select name="country">
	        	<option value="France" id="fr"> France </option>
	        	<option value="Germany" id="ge"> Germany </option>
	        	<option value="Italy" id="it"> Italy </option>
	        	<option value="Austria" id="au"> Austria </option>
	        	<option value="Switzerland" id="ch"> Switzerland </option>
	        	<option value="Hungary" id="hu"> Hungary </option>
	    	</select>
		</p>
	</div>
	<div id="ifSponsor" style="display:none">
		<p>
			<label for="type" class="type" > Type</label>
			<input id="type" name="type" type="text" placeholder="eg. Private Business" />
		</p>
		<p>
			<label for="amount" class="amount" > Amount</label>
			<input id="amount" name="amount" class="amount" placeholder="eg. 1000.00" />
		</p>
	</div>
	<p>
		<label for="comment" class="comment"> Comment</label>
		<textarea name="comment" form="appForm"> </textarea>
	</p>
	<p class="signin button">
		<input type="submit" value="Send"/>
	</p>
</form>
                                        </div>
                                    </div>
                    				<% } %>
                    </div>
                    </div>
                </div>  
            </section>
        </div>
    </body>
</html>

<%@ include file="header.jsp" %>
<body>
 <%
    //allow access only if session exists
    String user = null;
    if(session.getAttribute("user") == null){
    	response.sendRedirect("login-organizer.jsp");
    }else{
    	user = (String) session.getAttribute("user");
    }
    String userName = null;
    String sessionID = null;
    Cookie[] cookies = request.getCookies();
    if(cookies != null){
    	for(Cookie cookie : cookies){
    		if(cookie.getName().equals("user")){
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
				<h1>
					<strong>Database Practical Final Project</strong>
				</h1>
			</header>
			<span class="right"> <a href="index.html"> <strong>Back to
						the Main Page</strong>
			</a>
			</span>
			<div class="clr"></div>
		</div>
		<!--/ Codrops top bar -->
		<header>
			<h1 style="font-size: 300%;">WELCOME ORGANIZER - <%=request.getAttribute("organizer") %></h1>

		</header>
		<section>
			<div id="container_demo">
				<!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
				<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
					id="tologin"></a>
				<div id="wrapper">
					<div id="login" class="animate form">

						<h1>Log in</h1>
						<p style="text-align:center">Internal management:</p>
						<form action="shift">
							<p>
								<input type="submit" value="Manage Human resources" name="human">
							</p>
						</form>
						<form action="finances">
							<p>
								<input type="submit" value="Manage Finances" name="finances">
							</p>
						</form>
						<br>
						<p style="text-align:center">Bands management:</p>
						<form action="timeslotAssign">
							<p>
								<input type="submit" value="Assign Timeslots" name="manageBands">
							</p>
						</form>
						<form action="timeslotsearch">
							<p>
								<input type="submit" value="View Timeslots" name="manageBands">
							</p>
						</form>
						<br>
						<p style="text-align:center">Providers management:</p>
						<form action="boothToSponsor">
							<p>
								<input type="submit" value="Assign Booth to Sponsor" name="manageBands">
							</p>
						</form>
						<form action="boothView">
							<p>
								<input type="submit" value="View Booths" name="manageBands">
							</p>
						</form>

					</div>
				</div>
			</div>
		</section>
	</div>
<%@ include file="footer.jsp" %>


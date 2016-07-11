<%@ include file="header.jsp" %>

<body>
	<div class="container">
		<!-- Codrops top bar -->
		<div class="codrops-top">
			<header>
				<h1>
					<strong>Database Practical Final Project</strong>
				</h1>
			</header>
			<span class="right"> <a href="index.html "> <strong>Back to
						the Main Page</strong>
			</a>
			</span>
			<div class="clr"></div>
		</div>
		<!--/ Codrops top bar -->
		<header>
			<h1 style="font-size: 300%;">ORGANIZER LOGIN</h1>
		</header>
		<section>
			<div id="container_demo">
				<!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
				<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
					id="tologin"></a>
				<div id="wrapper">
					<div id="login" class="animate form">
						<form method=post action="welcomeOrganizer" autocomplete="on">
							<h1>Log in</h1>
							<p>
								<label for="username" class="uname" data-icon="u"> Your
									email or username </label> <input id="username" name="username"
									required="required" type="text"
									placeholder="myusername or mymail@mail.com" />
							</p>
							<p>
								<label for="password" class="youpasswd" data-icon="p">
									Your password </label> <input id="password" name="password"
									required="required" type="password" placeholder="eg. X8df!90EO" />
							</p>
							<p class="keeplogin">
								<input type="checkbox" name="loginkeeping" id="loginkeeping"
									value="loginkeeping" /> <label for="loginkeeping">Keep
									me logged in</label>
							</p>
							<%
								if (request.getAttribute("loginMessage") != null) {
							%>
							<p class="loginStatus">
								<label for="loginmessage">Login failed,please check
									username/password</label>
							</p>
							<%
								}
							%>
							<p class="login button">
								<input type="submit" value="Login" />
							</p>

						</form>
					</div>
				</div>
			</div>
		</section>
	</div>
<%@ include file="footer.jsp" %>

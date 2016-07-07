

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
        <div class="container">
            <!-- Codrops top bar -->
            <div class="codrops-top"><header>
              <h1><strong>Database Practical Final Project</strong></h1></header>			
                  <span class="right">
                    <a href=" ">
                        <strong>Back to the Main Page</strong>
                    </a>
                </span>
                <div class="clr"></div>
            </div><!--/ Codrops top bar -->
            <header>
                <h1 style="font-size: 300%;">Provider Registration Form </h1>
			</header>

    <div id="login" class ="animate form">

                             <form method="post" action="providerRegistration" autocomplete="on"> 
                                <h1> Sign up </h1> 
                                <p> 
                                    <label for="namesignup" class="name" data-icon="u">Name</label>
                                    <input id="namesignup" name="name" required="required" type="text" placeholder="myName" />
                                </p>
                                <p> 
                                    <label for="phonenumber" class="phone" data-icon="u">Phonenumber</label>
                                    <input id="phonenumber" name="phone" required="required" type="text" placeholder="+xx00000000" />
                                </p>
                                <p> 
                                    <label for="emailsignup" class="youmail" data-icon="e" >Email</label>
                                    <input id="emailsignup" name="email" required="required" type="email" placeholder="mysupermail@mail.com"/> 
                                </p>
                                <p> 
                                    <label for="passwordsignup" class="youpasswd" data-icon="p">Password </label>
                                    <input id="passwordsignup" name="password" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p> 
                                    <label for="passwordsignup_confirm" class="youpasswd" data-icon="p">Please confirm your password </label>
                                    <input id="passwordsignup_confirm" name="password_confirm" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p> 
                                    <label for="addresssignup" class="addresssignup" data-icon="&#9978;">Address</label>
                                    <input id="addresssignup" name="address" required="required" type="text" placeholder="eg. H.197,East Nattunpally,Burdwan"/>
                                </p>
                                <p> 
                                    <label for="phonesignup" class="website" data-icon="&#9742;">Website</label>
                                    <input id="phonesignup" name="website" required="required" type="text" placeholder="eg. www.mysite.com"/>
                                </p>
                                <% if(request.getAttribute("loginMessage") != null){%>
								<p class="loginStatus"> 
									<label for="loginmessage">The passwords don't match!</label>
								</p>
								<%}%>
 
                                <p class="signin button"> 
									<input type="submit" value="Sign up"/> 
								</p>
                                <p class="change_link">  
									Already a member ?
									<a href="login-provider.jsp" class="to_register"> Go and log in </a>
								</p>
                            </form>
                        </div>
        </div>
    </body>
</html>

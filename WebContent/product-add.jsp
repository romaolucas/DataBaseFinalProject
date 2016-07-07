<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.List, tum.in.dbpra.bean.ProductBean" %>

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
		<link rel="stylesheet" type="text/css" href="css/angtimate-custom.css" />
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
                    <a href=" ">
                        <strong>Back to the Main Page</strong>
                    </a>
                </span>
                <div class="clr"></div>
            </div><!--/ Codrops top bar -->
            <header>
                <h1 style="font-size: 300%;">Product Registration</h1>
			</header>
            <section>				
                <div id="container_demo" >
                    <!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <form method="post" action="addProduct" autocomplete="on"> 
                                <h1> Submit a product </h1> 
                                <p> 
                                    <label for="namesignup" class="name" data-icon="u">Product name</label>
                                    <input id="namesignup" name="name" required="required" type="text" placeholder="Coke" />
                                    <input id="pid" type="hidden" name="pid" value="<%= request.getParameter("pid") %>"/>
                                </p>
                                <p> 
                                    <label for="price" class="price" data-icon="u">Price</label>
                                    <input id="price" name="price" required="required" type="text" placeholder="13.00" />
                                </p>
                                <p> 
                                    <label for="category" class="category" data-icon="e" >Category</label>
                                    <input id="category" name="category" required="required" type="category" placeholder="Soft Drink"/> 
                                </p>
                                <p> 
                                    <label for="quantity" class="quantity" data-icon="p">Quantity </label>
                                    <input id="quantity" name="quantity" required="required" type="text" placeholder="77"/>
                                </p>
                               <p class="signin button"> 
									<input type="submit" value="Submit"/> 
								</p>
                            </form>
 
                        </div>	
                    </div>
                </div>  
            </section>
        </div>
    </body>
</html>
